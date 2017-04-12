package it.cnr.contab.progettiric00.action;

import java.util.*;
import it.cnr.contab.progettiric00.core.bulk.*;
import it.cnr.contab.progettiric00.bp.*;
import it.cnr.contab.progettiric00.comp.*;
import it.cnr.contab.config00.blob.bulk.*;
import it.cnr.jada.action.*;
import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.PersistencyException;
import it.cnr.jada.persistency.sql.PersistentHome;
import it.cnr.jada.persistency.sql.SQLBuilder;
import it.cnr.jada.util.action.*;
import it.cnr.jada.util.upload.UploadedFile;

import java.io.File;
import java.io.FileInputStream;
/**
 * Azione che gestisce le richieste relative alla Gestione Progetto Risorse
 * (Progetto)
 */
public class CRUDProgettoAction extends CRUDAbstractProgettoAction {
	
	//Dimensione massima ammessa per il File
	private static final long lunghezzaMax=0x1000000;
	
public CRUDProgettoAction() {
	super();
}
/**
 * Gestisce un comando di cancellazione.
 */
public Forward doElimina(ActionContext context) throws java.rmi.RemoteException {
	try {
		fillModel(context);

		CRUDBP bp = getBusinessProcess(context);
		if (bp instanceof TestataProgettiRicercaBP && ((TestataProgettiRicercaBP)bp).isFlNuovoPdg())
			return doConfirmElimina(context, OptionBP.YES_BUTTON);	
		return openConfirm(context,"Attenzione i Finanziatori del progetto, le UO partecipanti ed i Post-It saranno persi, vuoi continuare?",OptionBP.CONFIRM_YES_NO,"doConfirmElimina");
	} catch(Throwable e) {
		return handleException(context,e);
	}
}
public Forward doConfirmElimina(ActionContext context,int option) throws java.rmi.RemoteException {
	try
	{
		if (option == OptionBP.YES_BUTTON) 
		{
		  return super.doElimina(context);
		}
		return context.findDefaultForward();
	} catch(Throwable e) {
		return handleException(context,e);
	}
}
/**
  *  E' stata generata la richiesta di cercare un Progetto che sia padre del Progetto
  *	che si sta creando.
  *	Il metodo antepone alla descrizione specificata dall'utente, quella del Progetto selezionato
  *	come padre.
  *	In caso di modifica di un Progetto esistente sul DB, il sistema controlla che il Progetto
  *	selezionato dall'utente non sia la stesso che sta modificando.
  *
  * @param context il <code>ActionContext</code> che contiene le informazioni relative alla richiesta
  *
  * @return forward <code>Forward</code>
**/
 
public Forward doBringBackSearchFind_nodo_padre(ActionContext context, ProgettoBulk progetto, ProgettoBulk progetto_padre) throws java.rmi.RemoteException {

	if(progetto_padre != null){
		// L'utente ha selezionato come Progetto padre il Progetto che sta modificando
		if (progetto_padre.getCd_progetto().equals(progetto.getCd_progetto())){
			setErrorMessage(context,"Attenzione: non � possibile selezionare come padre il Progetto stesso");
			return context.findDefaultForward();
		}
		/* riporto le informazioni ereditate dal progetto padre */
		CRUDBP bp = getBusinessProcess(context);
		if (((TestataProgettiRicercaBP)bp).getLivelloProgetto()!=((progetto_padre.getLivello()).intValue()+1)) {
			setErrorMessage(context,"Attenzione: il codice inserito non � del tipo richiesto");
			return context.findDefaultForward();
		}
		if(bp.getStatus() == bp.INSERT || bp.getStatus() == bp.EDIT){
		  if(progetto.getDs_progetto() != null) {
		  	 if (progetto.getDs_progetto().indexOf(progetto_padre.getDs_progetto()) == -1)
				progetto.setDs_progetto(progetto_padre.getDs_progetto()+" - "+progetto.getDs_progetto());
		  }
		  else 
			progetto.setDs_progetto(progetto_padre.getDs_progetto());

		  progetto.setTipo(progetto_padre.getTipo());
		  progetto.setStato(progetto_padre.getStato());
		  progetto.setDt_inizio(progetto_padre.getDt_inizio());
		  progetto.setLivello(new Integer(progetto_padre.getLivello().intValue() + 1));
		  // se il padre � una commessa proponiamo anche i seguenti:
		  if (progetto_padre.getLivello().equals(new Integer(2))) {
			progetto.setDt_fine(progetto_padre.getDt_fine());
			progetto.setDurata_progetto(progetto_padre.getDurata_progetto());
		  }
		}  
		progetto.setProgettopadre(progetto_padre);
	}

	return context.findDefaultForward();
}
public Forward doFreeSearchFind_nodo_padre(ActionContext context) {
		TestataProgettiRicercaBP bp = (TestataProgettiRicercaBP)getBusinessProcess(context);
		ProgettoBulk progetto = (ProgettoBulk)bp.getModel();
		progetto.setProgettopadre(new ProgettoBulk());	
		return freeSearch(context, getFormField(context, "main.find_nodo_padre"), progetto);
}
/**
  *  E' stata generata la richiesta di cercare un Progetto che sia padre della Progetto
  *	che si sta creando.
  *	Il metodo controlla se l'utente ha indicato nel campo codice del Progetto padre un 
  *	valore: in caso affermativo, esegue una ricerca mirata per trovare esattamente il codice 
  *	indicato; altrimenti, apre un <code>SelezionatoreListaAlberoBP</code> che permette all'utente 
  *	di cercare il nodo padre scorrendo i Progetti secondo i vari livelli.
  *
  * @param context il <code>ActionContext</code> che contiene le informazioni relative alla richiesta
  *
  * @return forward <code>Forward</code>
**/
public it.cnr.jada.action.Forward doSearchFind_nodo_padre(ActionContext context) {

	try{
		
		TestataProgettiRicercaBP bp = (TestataProgettiRicercaBP)getBusinessProcess(context);
		ProgettoBulk progetto = (ProgettoBulk)bp.getModel();
		
		String cd = null;

		if (progetto.getProgettopadre() != null)
			cd = progetto.getProgettopadre().getCd_progetto();
			
		if (cd != null){
			if (cd.equals(progetto.getCd_progetto())){
				return handleException(context, new it.cnr.jada.comp.ApplicationException("Attenzione: non � possibile indicare come nodo padre il progetto corrente"));
			} else{
				// L'utente ha indicato un codice da cercare: esegue una ricerca mirata.
				return search(context, getFormField(context, "main.find_nodo_padre"),null);
			}
		}
		
		it.cnr.jada.util.RemoteIterator roots = bp.getProgettiTree(context).getChildren(context,null);
		// Non ci sono Progetti disponibili ad essere utiilzzati come nodo padre
		if (roots.countElements()==0){
			it.cnr.jada.util.ejb.EJBCommonServices.closeRemoteIterator(context, roots);
			setErrorMessage(context,"Attenzione: non sono stati trovati Progetti disponibili");
			return context.findDefaultForward();
		}else {
			// Apre un Selezionatore ad Albero per cercare i Progetti selezionando i vari livelli
			ProgettoAlberoBP slaBP = (ProgettoAlberoBP)context.createBusinessProcess("ProgettoAlberoBP");
			slaBP.setBulkInfo(it.cnr.jada.bulk.BulkInfo.getBulkInfo(ProgettoBulk.class));
			slaBP.setRemoteBulkTree(context,bp.getProgettiTree(context),roots);
			HookForward hook = (HookForward)context.addHookForward("seleziona",this,"doBringBackSearchResult");
			hook.addParameter("field",getFormField(context,"main.find_nodo_padre"));
			context.addBusinessProcess(slaBP);
			return slaBP;
		}
	} catch(Throwable e){
		return handleException(context, e);
	}
}
/**
  *  E' stata generata la richiesta di cercare un Progetto che sia padre del Progetto
  *	che si sta creando.
  *	Il metodo antepone alla descrizione specificata dall'utente, quella del Progetto selezionato
  *	come padre.
  *	In caso di modifica di una Progetto esistente sul DB, il sistema controlla che il Progetto
  *	selezionato dall'utente non sia la stesso che sta modificando.
  *
  * @param context il <code>ActionContext</code> che contiene le informazioni relative alla richiesta
  *
  * @return forward <code>Forward</code>
**/
 
public it.cnr.jada.action.Forward OLDdoBringBackSearchResult(ActionContext context) throws java.rmi.RemoteException {

	HookForward caller = (HookForward)context.getCaller();
	ProgettoBulk ubi = (ProgettoBulk)((TestataProgettiRicercaBP)getBusinessProcess(context)).getModel();
	ProgettoBulk ubiPadre = (ProgettoBulk)caller.getParameter("focusedElement");

	if(ubiPadre != null){
		// L'utente ha selezionato come Progetto padre il Progetto che sta modificando
		if (ubiPadre.getCd_progetto().equals(ubi.getCd_progetto())){
			setErrorMessage(context,"Attenzione: non � possibile selezionare come padre il Progetto stesso");
			return context.findDefaultForward();
		}
		if (ubi.isToBeCreated())
		 ubi.setDs_progetto(ubiPadre.getDs_progetto()+" - "+ubi.getDs_progetto());
	}

	return super.doBringBackSearchResult(
		context,
		(FormField)caller.getParameter("field"),
		ubiPadre);
}

public Forward doSalva(ActionContext context) {
	it.cnr.jada.action.HttpActionContext httpContext = (it.cnr.jada.action.HttpActionContext)context;
	TestataProgettiRicercaBP bp = (TestataProgettiRicercaBP)httpContext.getBusinessProcess();
	
	
	// Ciclo sui PostIt legati al ProgettoBulk
	for(int i = 0; ((ProgettoBulk)bp.getModel()).getDettagliPostIt().size() > i; i++) {

		// Se esiste un PostIt in inserimento  
		if (((PostItBulk) ((ProgettoBulk)bp.getModel()).getDettagliPostIt().get(i)).getCrudStatus() == OggettoBulk.TO_BE_CREATED )
		{
		  //Recupero eventuale File del PostIt da inserire 	
			UploadedFile file =httpContext.getMultipartParameter("main.DettagliPostIt.blob");
		  	
		  // Inizio codice per l'inserimento modifica della colonna BDATA
		  try 
		  {
			  if (file == null || file.getName().equals("")){
				  throw new it.cnr.jada.comp.ApplicationException("Attenzione: selezionare un File da caricare.");
			  }
			  if (file.length() > lunghezzaMax){
				  throw new it.cnr.jada.comp.ApplicationException("Attenzione: la dimensione del file � superiore alla massima consentita (10 Mb).");
			  }  
		
			  /* Nome (compreso di Path) del file selezionato*/
			  String fileName = file.getName();	
			
				
			  /* Valorizzazione del nome_file sul PostIt con il valore impostato decurtato del path */
			  if (((PostItBulk) ((ProgettoBulk)bp.getModel()).getDettagliPostIt().get(i)).getNome_file()== null )
				    ((PostItBulk) ((ProgettoBulk)bp.getModel()).getDettagliPostIt().get(i)).setNome_file(PostItBulk.parseFilename(fileName));
   
 		      /*Salvataggio record PostIt*/
			  super.doSalva(context);
			  ProgettoBulk progetto = (ProgettoBulk)bp.getModel();
			  if (progetto.getPg_progetto()==null)
				  throw new it.cnr.jada.comp.ApplicationException("Il campo ''Codice commessa'' non pu� essere vuoto.");			  
			  /*Inserimento nella colonna BLOB*/
			  ((it.cnr.contab.config00.ejb.Linea_attivitaComponentSession)it.cnr.jada.util.ejb.EJBCommonServices.createEJB("CNRCONFIG00_EJB_Linea_attivitaComponentSession", it.cnr.contab.config00.ejb.Linea_attivitaComponentSession.class)).Inserimento_BLOB(context.getUserContext(), progetto, file.getFile());
				  
			  /*Fine Inserimento nella colonna BLOB*/	
		  }
		  catch(Throwable e) 
		  {
				  if (file != null) 
					file.getFile().delete();
				  return handleException(context,e);
		  }

		 // Fine if PostIt in inserimento
		}
		
	} // Fine loop postit
	

	
	
		try 
		{	
			super.doSalva(context);
		}
		catch(Throwable ex)
		{
			return handleException(context, ex);
		}	 	
	 return context.findDefaultForward();
         
}


}

