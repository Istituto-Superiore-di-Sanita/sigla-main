package it.cnr.contab.pdg00.action;

import java.rmi.RemoteException;

import javax.ejb.RemoveException;

import it.cnr.contab.pdg00.bp.ArchiviaStampaPdgVariazioneBP;
import it.cnr.contab.pdg00.bp.FirmaDigitalePdgVariazioniBP;
import it.cnr.contab.pdg00.bulk.ArchiviaStampaPdgVariazioneBulk;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.Forward;
import it.cnr.jada.bulk.BulkInfo;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.util.RemoteIterator;
import it.cnr.jada.util.action.CRUDAction;
import it.cnr.jada.util.action.CRUDBP;
import it.cnr.jada.util.action.SelezionatoreListaBP;
import it.cnr.jada.util.ejb.EJBCommonServices;

public class ArchiviaStampaPdgVariazioneAction extends CRUDAction {
    public Forward doCerca(ActionContext actioncontext) throws RemoteException, InstantiationException, RemoveException{
	    try{
	        fillModel(actioncontext);
	        CRUDBP crudbp = getBusinessProcess(actioncontext);
	        OggettoBulk oggettobulk = crudbp.getModel();
	        RemoteIterator remoteiterator = crudbp.find(actioncontext, null, oggettobulk);
	        if(remoteiterator == null || remoteiterator.countElements() == 0){
	            EJBCommonServices.closeRemoteIterator(remoteiterator);
	            crudbp.setMessage("La ricerca non ha fornito alcun risultato.");
	            return actioncontext.findDefaultForward();
	        }
	        if(remoteiterator.countElements() == 1){
	            OggettoBulk oggettobulk1 = (OggettoBulk)remoteiterator.nextElement();
	            EJBCommonServices.closeRemoteIterator(remoteiterator);
	            crudbp.setMessage("La ricerca ha fornito un solo risultato.");
	            return doRiportaSelezione(actioncontext, oggettobulk1);
	        }else{
	            crudbp.setModel(actioncontext, oggettobulk);
	            SelezionatoreListaBP selezionatorelistabp = (SelezionatoreListaBP)actioncontext.createBusinessProcess("SelezionatorePdgVariazioniBP");
	            selezionatorelistabp.setIterator(actioncontext, remoteiterator);
	            selezionatorelistabp.setBulkInfo(BulkInfo.getBulkInfo(ArchiviaStampaPdgVariazioneBulk.class));
	            selezionatorelistabp.setColumns(getBusinessProcess(actioncontext).getSearchResultColumns());
	    		ArchiviaStampaPdgVariazioneBulk bulk = new ArchiviaStampaPdgVariazioneBulk();
	    		bulk.setTiSigned(ArchiviaStampaPdgVariazioneBulk.VIEW_ALL);
	            selezionatorelistabp.setModel(actioncontext, bulk);
	            actioncontext.addHookForward("seleziona", this, "doRiportaSelezione");
	            return actioncontext.addBusinessProcess(selezionatorelistabp);
	        }
	    }catch(Throwable throwable){
	        return handleException(actioncontext, throwable);
	    }
    }	
}
