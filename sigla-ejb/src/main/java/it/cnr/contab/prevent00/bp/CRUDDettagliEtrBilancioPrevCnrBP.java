package it.cnr.contab.prevent00.bp;
import it.cnr.contab.prevent00.bulk.*;
import it.cnr.contab.prevent00.ejb.*;

/**
 * Business Process di gestione del dei dettagli di entrata del bilancio preventivo CNR
 */

public class CRUDDettagliEtrBilancioPrevCnrBP extends it.cnr.jada.util.action.SimpleCRUDBP 
{
public CRUDDettagliEtrBilancioPrevCnrBP() 
{
	super();
}

public CRUDDettagliEtrBilancioPrevCnrBP(String function) 
{
	super(function);
		
    resetTab();

}

/**
 * Crea il riferimento alla remote interface del session bean CNRPREVENT00_EJB_BilancioPreventivoComponentSession
 * 
 * @return La remote interface
 * @throws EJBException	Se si verifica qualche eccezione applicativa per cui non � possibile effettuare l'operazione
 * @throws RemoteException	Se si verifica qualche eccezione di sistema per cui non � possibile effettuare l'operazione
 */
public it.cnr.contab.prevent00.ejb.BilancioPreventivoComponentSession createBilancioPreventivoComponentSession() throws javax.ejb.EJBException,java.rmi.RemoteException 
{
	return (BilancioPreventivoComponentSession)it.cnr.jada.util.ejb.EJBCommonServices.createEJB("CNRPREVENT00_EJB_BilancioPreventivoComponentSession", BilancioPreventivoComponentSession.class);
}

/**
 * Invocato per creare un modello vuoto da usare su una nuova richiesta di inserimento.
 * L'implementazione :
 *	- crea un nuovo modello con createNewBulk()</code>
 *	- invoca initializeForInsertEntrataCnr sul nuovo oggetto 
 *	- invoca inizializzaBulkPerInserimento sulla CRUDComponentSession del ricevente
 */
public it.cnr.jada.bulk.OggettoBulk createEmptyModel(it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException 
{
	try 
	{
		return createComponentSession().inizializzaBulkPerInserimento(context.getUserContext(),((Voce_f_saldi_cmpBulk)createNewBulk(context)).initializeForInsertEntrataCnr(this,context));
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
 *	- invoca initializeForFreeSearchEntrataCnr sul nuovo oggetto 
 *	- invoca inizializzaBulkPerRicercaLibera sulla CRUDComponentSession del ricevente
 */
 
public it.cnr.jada.bulk.OggettoBulk createEmptyModelForFreeSearch(it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException 
{
	try 
	{
		return createComponentSession().inizializzaBulkPerRicercaLibera(context.getUserContext(),((Voce_f_saldi_cmpBulk)createNewBulk(context)).initializeForFreeSearchEntrataCnr(this,context));
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
 *	- invoca initializeForSearchEntrataCnr sul nuovo oggetto 
 *	- invoca inizializzaBulkPerRicercaLibera sulla CRUDComponentSession del ricevente
 */
 
public it.cnr.jada.bulk.OggettoBulk createEmptyModelForSearch(it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException 
{
	try 
	{
		return createComponentSession().inizializzaBulkPerRicerca(context.getUserContext(),((Voce_f_saldi_cmpBulk)createNewBulk(context)).initializeForSearchEntrataCnr(this,context));
	} 
	catch(Exception e) 
	{
		throw handleException(e);
	}
}

/**
 * Ritorna il CDS a cui appartiene l'UO di scrivania
 *
 * @param voceSaldo	
 * @param context	L'ActionContext della richiesta
 * @return codice del CDS
 * @throws BusinessProcessException	
 */
public String getCds(Voce_f_saldi_cmpBulk voceSaldo, it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException 
{
	try
	{
		// Cerco il CDS di tipo ENTE
		it.cnr.jada.UserContext userContext = context.getUserContext();			
		BilancioPreventivoComponentSession aComponent =	createBilancioPreventivoComponentSession();
		it.cnr.contab.config00.sto.bulk.EnteBulk cds = (it.cnr.contab.config00.sto.bulk.EnteBulk)aComponent.cercaCdsEnte(userContext, voceSaldo);

		if( cds == null)
 		    throw new it.cnr.jada.comp.ApplicationException("Cds ENTE non presente in tabella!");
 		    	
		return cds.getCd_unita_organizzativa();
	}		
	catch(Exception e) 
	{
		throw handleException(e);
	}	
}

protected void init(it.cnr.jada.action.Config config,it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException 
{
	Voce_f_saldi_Etr_CnrBulk voceSaldo = new Voce_f_saldi_Etr_CnrBulk();

	// Inizializzo i campi fissi che nessuno modifichera'
	voceSaldo.setEsercizio( it.cnr.contab.utenze00.bulk.CNRUserInfo.getEsercizio(context) );		
	voceSaldo.setTi_appartenenza(Voce_f_saldi_cmpBulk.tipo_appartenenza_cnr);
	voceSaldo.setTi_gestione(Voce_f_saldi_cmpBulk.tipo_gestione_entrata);

	try
	{
		voceSaldo.setCd_cds(getCds(voceSaldo, context));
	}		
	catch(it.cnr.jada.action.BusinessProcessException e) 
	{
		throw new it.cnr.jada.action.BusinessProcessException(e);
	} 
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

	public void reset(it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException {
		super.reset(context);
		resetTab();
	}

/**
 * Reset tabs
 * 
 *
 */
public void resetTab() {
		setTab("tabDettaglioEntrata","tabEsercizio");			
		setTab("tabEsercizio", "tabCompetenza");
	}
}