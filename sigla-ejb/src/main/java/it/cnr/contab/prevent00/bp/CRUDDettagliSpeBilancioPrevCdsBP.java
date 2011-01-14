package it.cnr.contab.prevent00.bp;
import it.cnr.contab.prevent00.bulk.*;

/**
 * Business Process di gestione del dei dettagli di spesa del bilancio preventivo CDS
 */

public class CRUDDettagliSpeBilancioPrevCdsBP extends it.cnr.jada.util.action.SimpleCRUDBP {
public CRUDDettagliSpeBilancioPrevCdsBP() 
{
	super();
	
}

public CRUDDettagliSpeBilancioPrevCdsBP(String function) 
{
	super(function);
	
}

/**
 * Invocato per creare un modello vuoto da usare su una nuova richiesta di inserimento.
 * L'implementazione :
 *	- crea un nuovo modello con createNewBulk()</code>
 *	- invoca initializeForInsertSpesaCds sul nuovo oggetto 
 *	- invoca inizializzaBulkPerInserimento sulla CRUDComponentSession del ricevente
 */
public it.cnr.jada.bulk.OggettoBulk createEmptyModel(it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException 
{
	try 
	{
		return createComponentSession().inizializzaBulkPerInserimento(context.getUserContext(),((Voce_f_saldi_cmpBulk)createNewBulk(context)).initializeForInsertSpesaCds(this,context));
	} 
	catch(Exception e) 
	{
		throw handleException(e);
	}
}

/**
 * Invocato per creare un modello vuoto da usare su una nuova richiesta di ricerca libera.
 * L'implementazione :
 *	- crea un nuovo modello con createNewBulk()</code>
 *	- invoca initializeForFreeSearchSpesaCds sul nuovo oggetto 
 *	- invoca inizializzaBulkPerRicercaLibera sulla CRUDComponentSession del ricevente
 */
 
public it.cnr.jada.bulk.OggettoBulk createEmptyModelForFreeSearch(it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException 
{
	try 
	{
		return createComponentSession().inizializzaBulkPerRicercaLibera(context.getUserContext(),((Voce_f_saldi_cmpBulk)createNewBulk(context)).initializeForFreeSearchSpesaCds(this,context));
	} 
	catch(Exception e) 
	{
		throw handleException(e);
	}
}

/**
 * Invocato per creare un modello vuoto da usare su una nuova richiesta di ricerca.
 * L'implementazione :
 *	- crea un nuovo modello con createNewBulk()</code>
 *	- invoca initializeForSearchSpesaCds sul nuovo oggetto 
 *	- invoca inizializzaBulkPerRicercaLibera sulla CRUDComponentSession del ricevente
 */
 

public it.cnr.jada.bulk.OggettoBulk createEmptyModelForSearch(it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException 
{
	try 
	{
		return createComponentSession().inizializzaBulkPerRicerca(context.getUserContext(),((Voce_f_saldi_cmpBulk)createNewBulk(context)).initializeForSearchSpesaCds(this,context));
	} 
	catch(Exception e) 
	{
		throw handleException(e);
	}
}

/**
 * Ritorna il CDS a cui appartiene l'UO di scrivania
 *
 * @param context	L'ActionContext della richiesta
 * @return codice del CDS
 * @throws BusinessProcessException	
 */
public String getCds(it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException 
{
	it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk unita_organizzativa;		
	unita_organizzativa = it.cnr.contab.utenze00.bulk.CNRUserInfo.getUnita_organizzativa(context);
	return(unita_organizzativa.getUnita_padre().getCd_unita_organizzativa());
}

protected void init(it.cnr.jada.action.Config config,it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException 
{
	Voce_f_saldi_Spe_CdsBulk voceSaldo = new Voce_f_saldi_Spe_CdsBulk();

	// Inizializzo i campi fissi che nessuno modifichera'
	voceSaldo.setEsercizio( it.cnr.contab.utenze00.bulk.CNRUserInfo.getEsercizio(context) );		
	voceSaldo.setTi_appartenenza(Voce_f_saldi_cmpBulk.tipo_appartenenza_cds);
	voceSaldo.setTi_gestione(Voce_f_saldi_cmpBulk.tipo_gestione_spesa);
	voceSaldo.setCd_cds(getCds(context));

	setModel(context, voceSaldo );
	
	super.init(config,context);
}

/**
 * Disabilita sempre l'eliminazione
 *
 * @return boolean
 */
 
public boolean isDeleteButtonHidden() {

	return true;
}

/**
  * Se il dettaglio e' in sola lettura vengono disabilitati tutti i campi
  *
  * @return boolean
  */
  
public boolean isInputReadonly() 
{
	if (getModel() == null) return super.isInputReadonly();
	
	Boolean fl_sola_lettura = ((Voce_f_saldi_cmpBulk)getModel()).getFl_sola_lettura();
	return fl_sola_lettura != null && fl_sola_lettura.booleanValue();
}
}