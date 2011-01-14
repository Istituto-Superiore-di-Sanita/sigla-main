package it.cnr.contab.incarichi00.action;

import java.rmi.RemoteException;
import java.util.Calendar;

import it.cnr.contab.anagraf00.core.bulk.TerzoBulk;
import it.cnr.contab.compensi00.docs.bulk.V_terzo_per_compensoBulk;
import it.cnr.contab.incarichi00.bp.CRUDIncarichiProceduraBP;
import it.cnr.contab.incarichi00.bp.CRUDIncarichiRepertorioBP;
import it.cnr.contab.incarichi00.bulk.Incarichi_repertorioBulk;
import it.cnr.jada.action.*;
import it.cnr.jada.bulk.ValidationException;
import it.cnr.jada.util.DateUtils;
import it.cnr.jada.util.action.CRUDBP;
import it.cnr.jada.util.action.OptionBP;
import it.cnr.jada.util.ejb.EJBCommonServices;

public class IncarichiRepertorioAction extends it.cnr.jada.util.action.CRUDAction{
public IncarichiRepertorioAction() {
	super();
}
public Forward doBringBackSearchTerzo(ActionContext context, Incarichi_repertorioBulk incarico, V_terzo_per_compensoBulk terzo) {
	try {

		if(terzo != null) {
			doBlankSearchTerzo(context, incarico);
			CRUDIncarichiRepertorioBP bp = (CRUDIncarichiRepertorioBP) getBusinessProcess(context);

			bp.completaTerzo(context, incarico, terzo);
		} 
		return context.findDefaultForward();

	} catch(BusinessProcessException e) {
		return handleException(context,e);
	}
}
public Forward doBringBackSearchTerzoSearch(ActionContext context, Incarichi_repertorioBulk incarico, V_terzo_per_compensoBulk terzo) {
	return doBringBackSearchTerzo(context,incarico,terzo);
}
public Forward doBlankSearchTerzo(ActionContext context, Incarichi_repertorioBulk incarico) {

	if (incarico!=null){
		V_terzo_per_compensoBulk v_terzo = new V_terzo_per_compensoBulk();
		v_terzo.setTerzo(new TerzoBulk());
		
		incarico.setV_terzo(v_terzo);
		incarico.setCd_terzo(null);

		incarico.setTipiRapporto(null);
		incarico.setTipo_rapporto(null);
		incarico.setTipiTrattamento(null);
		incarico.setTipo_trattamento(null);
	}
	return context.findDefaultForward();
}
public Forward doBlankSearchTerzoSearch(ActionContext context, Incarichi_repertorioBulk incarico) {
	return doBlankSearchTerzo(context, incarico);
}
/**
 * Alla selezione del Tipo Rapporto vengono caricati i relativi tipi trattamento
 */  

public Forward doOnTipoRapportoChange(ActionContext context) {

	try {
		fillModel(context);
		CRUDIncarichiRepertorioBP bp = (CRUDIncarichiRepertorioBP)getBusinessProcess(context);
		Incarichi_repertorioBulk incarico = (Incarichi_repertorioBulk)bp.getModel();
		
		doAzzeraTipoTrattamento(context, incarico);
		bp.findTipiTrattamento(context);

		return context.findDefaultForward();

	}catch (Throwable ex) {
		return handleException(context, ex);
	}
}
public Forward doOnTipoIstituzCommercChange(ActionContext context) {

	try {
		fillModel(context);
		CRUDIncarichiRepertorioBP bp = (CRUDIncarichiRepertorioBP)getBusinessProcess(context);
		Incarichi_repertorioBulk incarico = (Incarichi_repertorioBulk)bp.getModel();

		if (bp.isSearching())
			return context.findDefaultForward();

		doAzzeraTipoTrattamento(context, incarico);
		bp.findTipiTrattamento(context);

		return context.findDefaultForward();

	}catch (Throwable ex) {
		return handleException(context, ex);
	}
}
public Forward doOnDtInizioValiditaChange(ActionContext context) {
	CRUDIncarichiRepertorioBP bp = (CRUDIncarichiRepertorioBP)getBusinessProcess(context);
	Incarichi_repertorioBulk incarico = (Incarichi_repertorioBulk)bp.getModel();

	java.sql.Timestamp oldDate=null;
	if (incarico.getDt_inizio_validita()!=null)
		oldDate = (java.sql.Timestamp)incarico.getDt_inizio_validita().clone();

	try {
		fillModel(context);
//		if (incarico.getTerzo()!=null && incarico.getTerzo().getCd_terzo()!=null)
//		    throw new ValidationException( "Non \350 possibile modificare la \"Data di inizio validit\340\". Cancellare il campo \"Contraente\" e ripetere l'operazione.");
		incarico.validaDateContratto();
		return context.findDefaultForward();
	}
	catch (Throwable ex) {
		// In caso di errore ripropongo la data precedente
		incarico.setDt_inizio_validita(oldDate);
		try
		{
			bp.setModel(context, incarico);
			return handleException(context, ex);			
		}
		catch (Throwable e) 
		{
			return handleException(context, e);
		}
	}
}
public Forward doOnDtFineValiditaChange(ActionContext context) {
	CRUDIncarichiRepertorioBP bp = (CRUDIncarichiRepertorioBP)getBusinessProcess(context);
	Incarichi_repertorioBulk incarico = (Incarichi_repertorioBulk)bp.getModel();

	java.sql.Timestamp oldDate=null;
	if (incarico.getDt_fine_validita()!=null)
		oldDate = (java.sql.Timestamp)incarico.getDt_fine_validita().clone();

	try {
		fillModel(context);
//		if (incarico.getTerzo()!=null && incarico.getTerzo().getCd_terzo()!=null)
//		    throw new ValidationException( "Non \350 possibile modificare la \"Data di fine validit\340\". Cancellare il campo \"Contraente\" e ripetere l'operazione.");
		incarico.validaDateContratto();
		return context.findDefaultForward();
	}
	catch (Throwable ex) {
		// In caso di errore ripropongo la data precedente
		incarico.setDt_fine_validita(oldDate);
		try
		{
			bp.setModel(context, incarico);
			return handleException(context, ex);			
		}
		catch (Throwable e) 
		{
			return handleException(context, e);
		}
	}
}
private void doAzzeraTipoTrattamento(ActionContext context, Incarichi_repertorioBulk incarico) {
	if (incarico!=null){
		incarico.setTipiTrattamento(null);
		incarico.setTipo_trattamento(null);
	}
}
public Forward doElimina(ActionContext actioncontext) throws RemoteException {
	try
	{
	    fillModel(actioncontext);
	    CRUDBP crudbp = getBusinessProcess(actioncontext);
    	if (((Incarichi_repertorioBulk)crudbp.getModel()).isIncaricoProvvisorio())
	    	return super.doElimina(actioncontext);
	    else
	        return doStornaIncarico(actioncontext);
	}
	catch(Throwable throwable)
	{
	    return handleException(actioncontext, throwable);
	}
}
public Forward doStornaIncarico(ActionContext actioncontext) throws RemoteException {
	try
	{
	    fillModel(actioncontext);
	    CRUDIncarichiRepertorioBP crudbp = (CRUDIncarichiRepertorioBP)getBusinessProcess(actioncontext);
	    if(!crudbp.isEditing())
	    {
	        crudbp.setMessage("Non \350 possibile stornare l'incarico in questo momento");
	    } else
	    {
	    	if (((Incarichi_repertorioBulk)crudbp.getModel()).isIncaricoDefinitivo()) {
	    		if (!crudbp.isUoEnte()&&!crudbp.isUtenteAbilitatoFunzioniIncarichi())
   				    throw new ValidationException( "Eliminazione consentita solo ad utenti con l'abilitazione alle funzioni di direttore di istituto.");
	    		if (crudbp.isIncaricoUtilizzato(actioncontext))
	    			return openConfirm(actioncontext, "Attenzione! L'incarico, in quanto gi� utilizzato, non sar� eliminato/stornato ma chiuso. \n" +
	    					"Dopo l'operazione non sar� piu' possibile utilizzare l'incarico. \n" +
	    					"Vuoi procedere?", OptionBP.CONFIRM_YES_NO, "doConfirmChiudiIncarico");
	    	}
			return openConfirm(actioncontext, "Attenzione! \n" +
					"Dopo l'operazione non sar� piu' possibile utilizzare l'incarico. \n" +
					"Vuoi procedere?", OptionBP.CONFIRM_YES_NO, "doConfirmStornaIncarico");
	    }
	    return actioncontext.findDefaultForward();
	}
	catch(Throwable throwable)
	{
	    return handleException(actioncontext, throwable);
	}
}
public Forward doConfirmChiudiIncarico(ActionContext context,int option) {
	try 
	{
		if ( option == OptionBP.YES_BUTTON) 
		{
			CRUDIncarichiRepertorioBP bp = (CRUDIncarichiRepertorioBP)getBusinessProcess(context);
			bp.chiudiIncarico(context);
			bp.edit(context,bp.getModel());
		}
		return context.findDefaultForward();
	}		
	catch(Throwable e) 
	{
		return handleException(context,e);
	}
}
public Forward doConfirmStornaIncarico(ActionContext context,int option) {
	try 
	{
		if ( option == OptionBP.YES_BUTTON) 
		{
			CRUDIncarichiRepertorioBP bp = (CRUDIncarichiRepertorioBP)getBusinessProcess(context);
			bp.stornaIncarico(context);
			bp.edit(context,bp.getModel());
		}
		return context.findDefaultForward();
	}		
	catch(Throwable e) 
	{
		return handleException(context,e);
	}
}
public Forward doSalvaDefinitivo(ActionContext context){
	try 
	{
		fillModel( context );
		CRUDIncarichiRepertorioBP bp = (CRUDIncarichiRepertorioBP)getBusinessProcess(context);
		bp.completeSearchTools(context, bp);
        bp.validate(context);
		return openConfirm(context, "Attenzione! Dopo il salvataggio definitivo non sar� pi� possibile modificare l'incarico di collaborazione. Si vuole procedere?", OptionBP.CONFIRM_YES_NO, "doConfirmSalvaDefinitivo");
	}		
	catch(Throwable e) 
	{
		return handleException(context,e);
	}
}
public Forward doConfirmSalvaDefinitivo(ActionContext context,int option) {
	try 
	{
		if ( option == OptionBP.YES_BUTTON) 
		{
			CRUDIncarichiRepertorioBP bp = (CRUDIncarichiRepertorioBP)getBusinessProcess(context);
			bp.save(context);
			bp.salvaDefinitivo(context);
			bp.edit(context,bp.getModel());
		}
		return context.findDefaultForward();
	}		
	catch(Throwable e) 
	{
		return handleException(context,e);
	}
}
public Forward doAnnullaDefinitivo(ActionContext context){
	try 
	{
		fillModel( context );
		return openConfirm(context, "Attenzione! Si desidera annullare la definitivit� dell'incarico di collaborazione?", OptionBP.CONFIRM_YES_NO, "doConfirmAnnullaDefinitivo");
	}		
	catch(Throwable e) 
	{
		return handleException(context,e);
	}
}
public Forward doConfirmAnnullaDefinitivo(ActionContext context,OptionBP option) {
	try 
	{
		if ( option.getOption() == OptionBP.YES_BUTTON) 
		{
			CRUDIncarichiRepertorioBP bp = (CRUDIncarichiRepertorioBP)getBusinessProcess(context);
			bp.annullaDefinitivo(context);
			bp.edit(context,bp.getModel());
		}
		return context.findDefaultForward();
	}		
	catch(Throwable e) 
	{
		return handleException(context,e);
	}
}
public Forward doApriIncarichiProcedura(ActionContext context) {
	try {
		CRUDIncarichiRepertorioBP bp = (CRUDIncarichiRepertorioBP)context.getBusinessProcess();
		Incarichi_repertorioBulk incarico = (Incarichi_repertorioBulk)bp.getModel();

		if (incarico==null || incarico.getCrudStatus()==CRUDBP.SEARCH){
			bp.setMessage("Non � stato selezionato alcun incarico.");
			return context.findDefaultForward();
		}
		else if (incarico.getIncarichi_procedura()==null) {
			bp.setMessage("L'incarico selezionato non risulta collegato ad alcuna procedura di conferimento incarichi.");
			return context.findDefaultForward();
		}

		CRUDBP newBP = (CRUDBP)context.getUserInfo().createBusinessProcess(
				context,
				"CRUDIncarichiProceduraBP",
				new Object[] {
					"M",
					bp.getModel()
				}
			);

		newBP.edit(context, incarico.getIncarichi_procedura());
		context.addHookForward("close",this,"doBringBackApriIncarichiProcedura");

		return context.addBusinessProcess(newBP);
	} catch(Throwable e) {
		return handleException(context,e);
	}
}
public Forward doBringBackApriIncarichiProcedura(ActionContext context) {
	try {
		CRUDBP bp = (CRUDBP)context.getBusinessProcess();
		bp.edit(context, bp.getModel());
		return context.findDefaultForward();
	} catch(Throwable e) {
		return handleException(context,e);
	}
}
public Forward doOnDtStipulaChange(ActionContext context) {
	CRUDIncarichiRepertorioBP bp = (CRUDIncarichiRepertorioBP)getBusinessProcess(context);
	Incarichi_repertorioBulk incarico = (Incarichi_repertorioBulk)bp.getModel();

	java.sql.Timestamp oldDate=null;
	if (incarico.getDt_stipula()!=null)
		oldDate = (java.sql.Timestamp)incarico.getDt_stipula().clone();

	try {
		fillModel(context);

		if (incarico.getDt_stipula()!=null && incarico.getDt_stipula().after(EJBCommonServices.getServerDate())) 
		    throw new ValidationException( "Non \350 possibile inserire una data di stipula superiore alla data odierna.");
		else if (incarico.getDt_stipula()!=null && !incarico.getFl_inviato_corte_conti() && DateUtils.daysBetweenDates(incarico.getDt_stipula(), EJBCommonServices.getServerDate())<5)
			throw new ValidationException( "Non \350 possibile inserire una data di stipula inferiore di 5 giorni rispetto alla data odierna.");

		if (incarico.getTerzo()!=null && incarico.getTerzo().getCd_terzo()!=null)
		    throw new ValidationException( "Non \350 possibile modificare la \"Data di Stipula\". Cancellare il campo \"Contraente\" e ripetere l'operazione.");
		incarico.validaDateContratto();
		return context.findDefaultForward();
	}
	catch (Throwable ex) {
		// In caso di errore ripropongo la data precedente
		incarico.setDt_stipula(oldDate);
		try
		{
			return handleException(context, ex);			
		}
		catch (Throwable e) 
		{
			return handleException(context, e);
		}
	}
}
}
