package it.cnr.contab.doccont00.bp;

import it.cnr.contab.config00.sto.bulk.*;
import it.cnr.contab.doccont00.ejb.*;
import java.util.*;
import it.cnr.contab.doccont00.core.bulk.*;
import it.cnr.jada.util.action.SimpleDetailCRUDController;
/**
 * Business process che gestisce attivit� relative al prospetto delle spese del Cdr.
 */
public class ProspettoSpeseCdrBP extends it.cnr.jada.util.action.SimpleCRUDBP {
	private final SimpleDetailCRUDController speseCdr = new SimpleDetailCRUDController("speseCdr", V_obblig_pdg_saldo_laBulk.class,"speseCdrColl",this);
	private String codiceCdr;
	private String descrizioneCdr;
public ProspettoSpeseCdrBP() {
	super();
}
public ProspettoSpeseCdrBP(String function) {
	super(function);
}
/**
 * Metodo utilizzato per creare una toolbar applicativa personalizzata.
 * @return null In questo caso la toolbar � vuota
 */
protected it.cnr.jada.util.jsp.Button[] createToolbar() {
	return null;
}
/**
 * <!-- @TODO: da completare -->
 * Restituisce il valore della propriet� 'codiceCdr'
 *
 * @return Il valore della propriet� 'codiceCdr'
 */
public java.lang.String getCodiceCdr() {
	return codiceCdr;
}
/**
 * <!-- @TODO: da completare -->
 * Restituisce il valore della propriet� 'descrizioneCdr'
 *
 * @return Il valore della propriet� 'descrizioneCdr'
 */
public java.lang.String getDescrizioneCdr() {
	return descrizioneCdr;
}
/**
 * @return it.cnr.jada.util.action.SimpleDetailCRUDController
 */
public final it.cnr.jada.util.action.SimpleDetailCRUDController getSpeseCdr() {
	return speseCdr;
}
/**
 * Serve per aggiornare le spese del Cdr
 * @param context Il contesto dell'azione
 */
public void  refreshSpeseCdr( it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException
{
	try
	{
		ProspettoSpeseCdrBulk prospetto = (ProspettoSpeseCdrBulk)getModel();
		List input = new LinkedList();
		input.add( prospetto.getCdr() );
		List result = ((ObbligazioneComponentSession)createComponentSession()).generaProspettoSpeseObbligazione(context.getUserContext(), input );
		prospetto.setSpeseCdrColl( result );
		prospetto.refreshTotali();
	}
	catch (Exception e )
	{
		throw handleException(e)	;
	}	
	
}
/**
 * <!-- @TODO: da completare -->
 * Imposta il valore della propriet� 'codiceCdr'
 *
 * @param newCodiceCdr	Il valore da assegnare a 'codiceCdr'
 */
public void setCodiceCdr(java.lang.String newCodiceCdr) {
	codiceCdr = newCodiceCdr;
}
/**
 * <!-- @TODO: da completare -->
 * Imposta il valore della propriet� 'descrizioneCdr'
 *
 * @param newDescrizioneCdr	Il valore da assegnare a 'descrizioneCdr'
 */
public void setDescrizioneCdr(java.lang.String newDescrizioneCdr) {
	descrizioneCdr = newDescrizioneCdr;
}
}
