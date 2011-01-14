/*
 * Created on Aug 31, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.cnr.contab.config00.action;

import java.rmi.RemoteException;

import it.cnr.contab.config00.bp.CRUDClassificazioneVociBP;
import it.cnr.contab.config00.pdcfin.cla.bulk.*;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.action.Forward;
import it.cnr.jada.bulk.FillException;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.bulk.ValidationException;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.util.action.CRUDAction;
import it.cnr.jada.util.action.OptionBP;
import it.cnr.jada.util.action.Selection;

public class CRUDClassificazioneVociAction extends CRUDAction {

	/**
 	 * Construtor from superclass
 	 *
 	 */
	public CRUDClassificazioneVociAction() {
		super();
	}

	public Forward doTab(ActionContext actioncontext, String tabName,String pageName) {
		CRUDClassificazioneVociBP bp = (CRUDClassificazioneVociBP)actioncontext.getBusinessProcess();
		Classificazione_vociBulk classModelNew = null;
		int intTabOld = (new Integer(bp.getTab(tabName).substring("tabLivello".length()))).intValue();
		int intTabNew = (new Integer(pageName.substring("tabLivello".length()))).intValue();

		/* 
		 * Non � possibile cambiare Tab se esistono dati non confermati.
		 */
		try {
			fillModel(actioncontext);
			if (bp.isDirty()){
				setErrorMessage(actioncontext,"Confermare le modifiche prima di proseguire.");
				return actioncontext.findDefaultForward();
			}
		} catch (FillException e1) {
			e1.printStackTrace();
		}

		/*
		 * Se la navigazione tra le Tab � in avanti deve essere selezionato sul Bulk corrente
		 * almeno un livello successivo da utilizzare per il caricamento delle Bulk successive.
		 * Regola non valida se si naviga dalla Tab1
		 */
		if ((new Integer(intTabNew)).compareTo(new Integer(intTabOld))>0 && 
			((intTabOld==1 && bp.getModel().getCrudStatus()!=OggettoBulk.NORMAL)||
			 (intTabOld!=1 && bp.getCrudAssLivelli().getSelection().getFocus()<0))) {  
			bp.setMessage("E' necessario selezionare un livello.");
			return actioncontext.findDefaultForward();
		}
		
		/*
		 * Se � stato selezionato un livello diverso da quello precedentemente memorizzato (il focus del 
		 * controller � diverso da quello memorizzato nel Bulk) vengono annullati tutti i riferimenti 
		 * memorizzati nel BP ai Bulk e focus dei livelli successivi.
		 * Se si naviga dalla Tab1 il processo di annullamento avviene se cambia il model del BP
		 */
		bp.allineaFocusBulkTab(intTabOld);

		/*
		 * Se la navigazione tra le Tab � in avanti � possibile navigare solo fino alla Tab immediatamente
		 * successiva all'ultima Tab caricata.
		 */
		if ((new Integer(intTabNew)).compareTo(new Integer(intTabOld))>0 && 
			bp.getBulkTab(intTabNew-1) == null && intTabNew!=intTabOld+1) {
			bp.setMessage("E' necessario caricare i livelli nella sequenza corretta.");
			return actioncontext.findDefaultForward();
		} 
		else if ((new Integer(intTabNew)).compareTo(new Integer(intTabOld))>0 && 
				 intTabNew!=2 && bp.getBulkTab(intTabNew-1) != null && 
				 bp.getFocusTab(intTabNew-1) == -1 && intTabNew!=intTabOld+1) {
			bp.setMessage("E' necessario selezionare un dettaglio del livello precedente.");
			return actioncontext.findDefaultForward();
		}

		/*
		 * Individuazione del model da associare al BP
		 */
		if (bp.getBulkTab(intTabNew) != null)
			classModelNew = bp.getBulkTab(intTabNew);
		else if (intTabOld==1)
			classModelNew = (Classificazione_vociBulk)bp.getModel();
		else if (intTabNew==intTabOld+1 && !(bp.getCrudAssLivelli().getSelection().getFocus()<0))
			classModelNew = (Classificazione_vociBulk)((Classificazione_vociBulk)bp.getModel()).getClassVociAssociate().get(bp.getCrudAssLivelli().getSelection().getFocus());
		else if (intTabNew!=intTabOld+1 && !(bp.getFocusTab(intTabNew-1)<0))
			classModelNew = (Classificazione_vociBulk)((Classificazione_vociBulk)bp.getBulkTab(intTabNew-1)).getClassVociAssociate().get(bp.getFocusTab(intTabNew-1));
		
		Forward fw = super.doTab(actioncontext, tabName, pageName);		
	
		try {
			if (bp.getBulkTab(intTabNew) == null)
				bp.setModel(actioncontext, bp.initializeModelForEdit(actioncontext, classModelNew));
			else
				bp.setModel(actioncontext, classModelNew);
		} catch (BusinessProcessException e) {
			e.printStackTrace();
		}
		
		bp.allineaDescriptionTab(intTabNew);

		/*
		 * Settaggio del focus sulle Classificazioni associate al livello visualizzato
		 */
		try {
			Selection selCopy = new Selection();
			if (bp.getBulkTab(intTabNew) == null && 
			   !((Classificazione_vociBulk)bp.getModel()).getClassVociAssociate().isEmpty())
				bp.getCrudAssLivelli().setSelection(actioncontext, selCopy);
			else if (bp.getBulkTab(intTabNew) != null){
				selCopy.setFocus(bp.getFocusTab(intTabNew)); 
				bp.getCrudAssLivelli().setSelection(actioncontext, selCopy);
			}
		} catch (BusinessProcessException e2) {
			e2.printStackTrace();
		} catch (ValidationException e2) {
			e2.printStackTrace();
		}
		return fw;
	}

	public Forward doElimina(ActionContext actioncontext) throws RemoteException {
		return eliminaClassificazione(actioncontext, false);
	}

    public Forward eliminaClassificazione(ActionContext actioncontext, boolean eliminaDaController) {
		CRUDClassificazioneVociBP bp = (CRUDClassificazioneVociBP)actioncontext.getBusinessProcess();
		try {
			boolean esistono_figli = false;
			if (eliminaDaController) {
				java.util.List selected_cla = bp.getCrudAssLivelli().getSelectedModels(actioncontext);
				it.cnr.jada.util.action.Selection selection= bp.getCrudAssLivelli().getSelection();
				java.util.List classificazioni= bp.getCrudAssLivelli().getDetails();

				if (selection.isEmpty() && bp.getCrudAssLivelli().getModel() == null) {
					setErrorMessage(actioncontext,"Attenzione! Selezionare le classificazioni da eliminare!");
					return actioncontext.findDefaultForward();
				}		
				if (!selection.isEmpty()) {
					for (it.cnr.jada.util.action.SelectionIterator i= bp.getCrudAssLivelli().getSelection().iterator(); i.hasNext();) {
						Classificazione_vociBulk cur_cla = (Classificazione_vociBulk) classificazioni.get(i.nextIndex());
						if (!(bp.getClassificazioniTree(actioncontext).isLeaf(actioncontext, cur_cla)))
							esistono_figli = true;
					}
				}
				else
				{
					Classificazione_vociBulk cur_cla = (Classificazione_vociBulk)bp.getCrudAssLivelli().getModel();
					if (!(bp.getClassificazioniTree(actioncontext).isLeaf(actioncontext, cur_cla)))
						esistono_figli = true;
				}
			}
			else
			{
				Classificazione_vociBulk cur_cla = (Classificazione_vociBulk)bp.getModel();
				if (!(bp.getClassificazioniTree(actioncontext).isLeaf(actioncontext, cur_cla)))
					esistono_figli = true;
			}

			if (esistono_figli)
				if (eliminaDaController) 
					return openConfirm(actioncontext,"Attenzione! Verranno eliminate anche tutte le classificazioni associate. Proseguire ?",OptionBP.CONFIRM_YES_NO,"doCancellaClassificazioniAssociate");
				else
					return openConfirm(actioncontext,"Attenzione! Verranno eliminate anche tutte le classificazioni associate. Proseguire ?",OptionBP.CONFIRM_YES_NO,"doCancellaClassificazionePrincipale");

			if (eliminaDaController)
				bp.getCrudAssLivelli().remove(actioncontext);
			else
				return super.doElimina(actioncontext);
		} catch (BusinessProcessException bpe) {
			return handleException(actioncontext, bpe);
		} catch (ValidationException ve) {
			return handleException(actioncontext, ve);
		} catch (RemoteException re) {
			return handleException(actioncontext, re);
		} catch (ComponentException ce) {
			return handleException(actioncontext, ce);
		}
		return actioncontext.findDefaultForward();
    }
    
	public Forward doRemoveFromCRUDMain_ClassVociAssociate1(ActionContext actioncontext){
		return eliminaClassificazione(actioncontext, true);
	}

	public Forward doRemoveFromCRUDMain_ClassVociAssociate2(ActionContext actioncontext){
		return eliminaClassificazione(actioncontext, true);
	}

	public Forward doRemoveFromCRUDMain_ClassVociAssociate3(ActionContext actioncontext){
		return eliminaClassificazione(actioncontext, true);
	}

	public Forward doRemoveFromCRUDMain_ClassVociAssociate4(ActionContext actioncontext){
		return eliminaClassificazione(actioncontext, true);
	}

	public Forward doRemoveFromCRUDMain_ClassVociAssociate5(ActionContext actioncontext){
		return eliminaClassificazione(actioncontext, true);
	}

	public Forward doRemoveFromCRUDMain_ClassVociAssociate6(ActionContext actioncontext){
		return eliminaClassificazione(actioncontext, true);
	}

	public Forward doRemoveFromCRUDMain_ClassVociAssociate7(ActionContext actioncontext){
		return eliminaClassificazione(actioncontext, true);
	}
	
	/**
	  * Il metodo effettua una cancellazione di classificazioni associate
	  * L'eliminazione della classificazione associata principale e di tutte quelle associate a se stessa 
	  * avviene tramite l'eliminazione della principale che genera, tramite la foreignKey Cascade constraints 
	  * presente sul DB, anche l'eliminazione di tutte le classificazioni associate
	  */
	public Forward doCancellaClassificazioniAssociate(ActionContext context,OptionBP option) throws BusinessProcessException, ValidationException
	{
		CRUDClassificazioneVociBP bp = (CRUDClassificazioneVociBP)getBusinessProcess(context);
	
		if(option.getOption() == OptionBP.YES_BUTTON)  
		{
			bp.getCrudAssLivelli().remove(context);
		}
		return context.findDefaultForward();
	}

	/**
	  * Il metodo effettua una cancellazione della classificazione principale
 	  * L'eliminazione della classificazione principale e di tutte quelle associate avviene tramite l'eliminazione
	  * della principale che genera, tramite la foreignKey Cascade constraints presente sul DB, 
	  * anche l'eliminazione di tutte le classificazioni associate
	  */
	public Forward doCancellaClassificazionePrincipale(ActionContext context,OptionBP option) throws BusinessProcessException, ValidationException
	{
		try {
			if(option.getOption() == OptionBP.YES_BUTTON)
				return super.doElimina(context);
			return context.findDefaultForward();
		} catch (RemoteException e) {
			return handleException(context, e);
		}
	}
	public Forward doOnFlAccentrato(ActionContext actioncontext) throws RemoteException {
		try {
			fillModel(actioncontext);
			CRUDClassificazioneVociBP bp = (CRUDClassificazioneVociBP)getBusinessProcess(actioncontext);
			Classificazione_vociBulk cla = (Classificazione_vociBulk)bp.getModel();
			if (cla.getFl_accentrato() == null || cla.getFl_accentrato().equals(Boolean.FALSE)) 
				cla.setCentro_responsabilita(null);
			return actioncontext.findDefaultForward();
		}	
		catch(Throwable e) 
		{
			return handleException(actioncontext,e);
		}	
	}
}
