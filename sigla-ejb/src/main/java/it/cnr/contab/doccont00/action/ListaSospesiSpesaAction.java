/*
 * Created on Jan 3, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.cnr.contab.doccont00.action;

import it.cnr.contab.doccont00.core.bulk.*;
import it.cnr.contab.doccont00.bp.*;
import it.cnr.contab.doccont00.bp.ListaSospesiBP;
import it.cnr.jada.action.*;
import it.cnr.jada.bulk.*;
import it.cnr.jada.util.action.*;
/**
 * @author mspasiano
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ListaSospesiSpesaAction extends SelezionatoreListaAction {
	/**
	 * Gestisce un comando "ricerca libera".
	 */
	public Forward doRicercaLibera(ActionContext context) {
		try {
			ListaSospesiSpesaBP bp = (ListaSospesiSpesaBP)context.getBusinessProcess();
			RicercaLiberaBP ricercaLiberaBP = (RicercaLiberaBP)context.createBusinessProcess("RicercaLibera");
			ricercaLiberaBP.setSearchProvider(bp);
			ricercaLiberaBP.setFreeSearchSet(null);
			ricercaLiberaBP.setShowSearchResult(false);
			ricercaLiberaBP.setCanPerformSearchWithoutClauses(false);
			ricercaLiberaBP.setPrototype( bp.createEmptyModelForFreeSearch(context));
			context.addHookForward("seleziona",this,"doRiportaSelezioneSospesi");
			context.addHookForward("searchResult",this,"doAddToCRUDMain_SospesiSelezionati");			
			return context.addBusinessProcess(ricercaLiberaBP);
		} catch(Throwable e) {
			return handleException(context,e);
		}
	}
	
	public Forward doAddToCRUDMain_SospesiSelezionati(ActionContext context)
	{
	
		try 
		{
			ListaSospesiSpesaBP bp = (ListaSospesiSpesaBP)context.getBusinessProcess();
			HookForward hook = (HookForward)context.getCaller();
			bp.setIterator(context,(it.cnr.jada.util.RemoteIterator)hook.getParameter("remoteIterator"));
			BulkInfo bulkInfo = BulkInfo.getBulkInfo(SospesoBulk.class);
			bp.setColumns( bulkInfo.getColumnFieldPropertyDictionary("SospesiMandato"));
			return context.findDefaultForward();
		} catch(Throwable e) {
			return handleException(context,e);
		}
	}	
	/**
	 * Gestisce la selezione dei sospesi
	 *
	 */
	public Forward doRiportaSelezioneSospesi(ActionContext context)
	{
	
		try 
		{
			if (context.getBusinessProcess() instanceof ListaSospesiSpesaBP)
			   context.closeBusinessProcess();
			CRUDAbstractMandatoBP bp = (CRUDAbstractMandatoBP)context.getBusinessProcess();
			bp.aggiungiSospesi( context );
			return context.findDefaultForward();
		} catch(Throwable e) 
		{
			return handleException(context,e);
		}
	
	}		
}
