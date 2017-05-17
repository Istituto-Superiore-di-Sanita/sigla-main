package it.cnr.contab.doccont00.bp;

import java.util.*;

import it.cnr.contab.doccont00.core.bulk.*;
import it.cnr.jada.action.*;
import it.cnr.jada.bulk.*;
import it.cnr.jada.util.action.*;

public class CRUDMandatoRigaController extends it.cnr.jada.util.action.SimpleDetailCRUDController {
public CRUDMandatoRigaController(String name, Class modelClass, String listPropertyName, it.cnr.jada.util.action.FormController parent) {
	super(name, modelClass, listPropertyName, parent);
}
public void remove(it.cnr.jada.action.ActionContext context) 	throws ValidationException, it.cnr.jada.action.BusinessProcessException 
{
	super.remove( context );

	int len = getDetails().size();
	Mandato_rigaIBulk row;
	for ( int i = len - 1; i >= 0 ; i--)
	{
		row = (Mandato_rigaIBulk) getDetails().get(i);
		if ( row.getCrudStatus() == row.UNDEFINED )
			((MandatoBulk)getParentModel()).removeFromMandato_rigaColl( i );
	}		
	((MandatoBulk)getParentModel()).refreshImporto();
	getParentController().setDirty(true);
	reset(context);
	

}
/*
caso 1 - riga non ancora inserita nel db: se l'utente ne richiede la cancellazione viene effettivamente rimossa
         se viene richiesta la cancellazione di una fattura con associate note a debito/credito, anche questi doc. amm
         devono essere rimossi
caso 2 - NON USATO - riga gi� salvata nel db: se l'utente ne richiede la cancellazione viene annullata
*/
public OggettoBulk removeDetail(int index) 
{
	Mandato_rigaIBulk rigaDaCancellare = (Mandato_rigaIBulk) getDetails().get( index );
	Mandato_rigaIBulk row;
	// la riga non e' ancora stata inserita nel db --> la cancello
	if ( rigaDaCancellare != null && rigaDaCancellare.isToBeCreated() && rigaDaCancellare.isFlCancellazione())
	{
		int len = getDetails().size();
		for ( int i = len - 1; i >= 0 ; i--)
		{
			row = (Mandato_rigaIBulk) getDetails().get(i);
			if ( !row.isFlCancellazione() &&
				 row.getEsercizio_obbligazione().equals( rigaDaCancellare.getEsercizio_obbligazione()) &&
				 row.getCd_cds().equals( rigaDaCancellare.getCd_cds()) &&
				 row.getEsercizio_ori_obbligazione().equals( rigaDaCancellare.getEsercizio_ori_obbligazione()) &&
				 row.getPg_obbligazione().equals( rigaDaCancellare.getPg_obbligazione()) &&
				 row.getPg_obbligazione_scadenzario().equals( rigaDaCancellare.getPg_obbligazione_scadenzario()))
			{
//				row = (Mandato_rigaIBulk) ((MandatoBulk)getParentModel()).removeFromMandato_rigaColl( i );
				row.setToBeDeleted();
			}
		}
		rigaDaCancellare.setToBeDeleted();
		((MandatoBulk)getParentModel()).removeFromMandato_rigaColl( index );		
		return rigaDaCancellare;
	}
	// la riga e' gia� stata inserita nel db e il suo stato e' != da STATO_ANNULLATO --> aggiorno lo stato
	else if ( rigaDaCancellare != null && !rigaDaCancellare.isToBeCreated() && !rigaDaCancellare.getStato().equals( rigaDaCancellare.STATO_ANNULLATO))
	{
		rigaDaCancellare.annulla();
		return rigaDaCancellare;
	}
	else 
		return rigaDaCancellare;
}
/* Non e' possibile cancellare note a credito / debito; e' necessario cancellare la fattura passiva da cui dipendono*/
public void validateForDelete(ActionContext context, OggettoBulk detail) throws ValidationException 
{
	if ( detail instanceof Mandato_rigaIBulk && !((Mandato_rigaIBulk)detail).isFlCancellazione())
	{
		Mandato_rigaIBulk rigaDaCancellare = (Mandato_rigaIBulk) detail;
		Mandato_rigaIBulk row = null ;
		int len = getDetails().size();
		Long pg_fattura_passiva = new Long(0);
		//ricerco il progressivo della fattura passiva da cui la nota di debito/credito dipende
		int index; 
		for ( index = len - 1; index >= 0 ; index--)
		{
			row = (Mandato_rigaIBulk) getDetails().get(index);
			if ( row.getEsercizio_obbligazione().equals( rigaDaCancellare.getEsercizio_obbligazione()) &&
				 row.getCd_cds().equals( rigaDaCancellare.getCd_cds()) &&
				 row.getEsercizio_ori_obbligazione().equals( rigaDaCancellare.getEsercizio_ori_obbligazione()) &&
				 row.getPg_obbligazione().equals( rigaDaCancellare.getPg_obbligazione()) &&
				 row.getPg_obbligazione_scadenzario().equals( rigaDaCancellare.getPg_obbligazione_scadenzario()) &&
				 row.isFlCancellazione())
			{
				pg_fattura_passiva = row.getPg_doc_amm();
				break;
			}
		}
		//se la fattura passiva non e' stata selezionata per essere cancellata segnalo l'errore
		if ( row != null && !getSelection().isSelected(index) )
			throw new ValidationException( "Cancellazione impossibile: e' necessario rimuovere la fattura passiva " + pg_fattura_passiva + " da cui " +
				"il documento amministrativo " + ((Mandato_rigaIBulk)detail).getPg_doc_amm() + " dipende");
	}	
}
/**
 * Metodo per aggiungere alla toolbar del Controller un tasto necessario all'utente
 * per la ricerca rapida della riga da quadrare con il SIOPE
 * @param context Il contesto dell'azione
 * @param scadenza La scadenza dell'oggetto bulk in uso
 */
public void writeHTMLToolbar(
		javax.servlet.jsp.PageContext context,
		boolean reset,
		boolean find,
		boolean delete) throws java.io.IOException, javax.servlet.ServletException {

		super.writeHTMLToolbar(context, reset, find, delete);

		if (getParentController() instanceof CRUDMandatoBP) {
			CRUDMandatoBP bp = (CRUDMandatoBP)getParentController();
			if (bp.isSiope_attiva() && !((MandatoBulk)getParentModel()).isSiopeTotalmenteAssociato()){
				String command = "javascript:submitForm('doSelezionaRigaSiopeDaCompletare')";
				it.cnr.jada.util.jsp.JSPUtils.toolbarButton(
					context,
					"img/find16.gif",
					!(isInputReadonly() || getDetails().isEmpty() || ((CRUDMandatoBP)getParentController()).isSearching())? command : null,
					true,"SIOPE - Vai a riga successiva da completare",
					HttpActionContext.isFromBootstrap(context));
			}
		}
}
}
