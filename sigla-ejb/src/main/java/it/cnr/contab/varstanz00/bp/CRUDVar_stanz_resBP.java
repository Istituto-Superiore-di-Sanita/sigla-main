/*
 * Created on Feb 16, 2006
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.cnr.contab.varstanz00.bp;

import java.rmi.RemoteException;

import javax.ejb.RemoveException;

import it.cnr.contab.config00.sto.bulk.CdrBulk;
import it.cnr.contab.config00.sto.bulk.CdsBulk;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.doccont00.core.bulk.Accertamento_modificaBulk;
import it.cnr.contab.utenze00.bulk.UtenteBulk;
import it.cnr.contab.util.Utility;
import it.cnr.contab.varstanz00.bulk.Ass_var_stanz_res_cdrBulk;
import it.cnr.contab.varstanz00.bulk.Var_stanz_resBulk;
import it.cnr.contab.varstanz00.ejb.VariazioniStanziamentoResiduoComponentSession;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.bulk.ValidationException;
import it.cnr.jada.comp.ApplicationException;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.util.RemoteIterator;
import it.cnr.jada.util.action.SimpleCRUDBP;
import it.cnr.jada.util.action.SimpleDetailCRUDController;
import it.cnr.jada.util.ejb.EJBCommonServices;

/**
 * @author mspasiano
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CRUDVar_stanz_resBP extends SimpleCRUDBP {
	private it.cnr.contab.config00.sto.bulk.CdrBulk centro_responsabilita_scrivania;
	private it.cnr.contab.config00.sto.bulk.CdsBulk centro_di_spesa_scrivania;	
	private Unita_organizzativaBulk uoSrivania;
	private Accertamento_modificaBulk acrMod;
	
	private SimpleDetailCRUDController crudAssCDR = new SimpleDetailCRUDController( "AssociazioneCDR", Ass_var_stanz_res_cdrBulk.class, "associazioneCDR", this) {
		public void validateForDelete(ActionContext context, OggettoBulk detail) throws ValidationException {
			validaAssociazioneCDRPerCancellazione(context, (Ass_var_stanz_res_cdrBulk)detail);
		}
	};
	public void validaAssociazioneCDRPerCancellazione(ActionContext context, Ass_var_stanz_res_cdrBulk assBulk) throws ValidationException {
		/*try {
			PdGVariazioniComponentSession comp = (PdGVariazioniComponentSession)createComponentSession();
			comp.validaAssociazioneCDRPerCancellazione(context.getUserContext(), assBulk);
		} catch (Throwable e) {
			throw new ValidationException(e.getMessage());
		}*/
	}

	/**
	 * 
	 */
	public CRUDVar_stanz_resBP() {
		super();
	}

	/**
	 * @param s
	 */
	public CRUDVar_stanz_resBP(String s) {
		super(s);
	}
	
	public CRUDVar_stanz_resBP(String s, Accertamento_modificaBulk acrMod) {
		super(s);
		setAcrMod(acrMod);
	}

	protected void resetTabs(it.cnr.jada.action.ActionContext context) {
		setTab("tab","tabTestataVarStanzRes");
	}
	/**
	 * @return
	 */
	public SimpleDetailCRUDController getCrudAssCDR() {
		return crudAssCDR;
	}

	/**
	 * @param controller
	 */
	public void setCrudAssCDR(SimpleDetailCRUDController controller) {
		crudAssCDR = controller;
	}
	/**
	 * Gestione del salvataggio come definitiva di una variazione
	 *
	 * @param context	L'ActionContext della richiesta
	 * @throws BusinessProcessException	
	 */
	public void salvaDefinitivo(ActionContext context) throws it.cnr.jada.action.BusinessProcessException{
		try {
			VariazioniStanziamentoResiduoComponentSession comp = (VariazioniStanziamentoResiduoComponentSession)createComponentSession();
			edit(context,comp.salvaDefinitivo(context.getUserContext(), getModel()));
		}catch(it.cnr.jada.comp.ComponentException ex){
			throw handleException(ex);
		}catch(java.rmi.RemoteException ex){
			throw handleException(ex);
		}
	}
	
	public void validaOrigineFontiPerAnnoResiduo(ActionContext context) throws it.cnr.jada.action.BusinessProcessException{
		Var_stanz_resBulk var = (Var_stanz_resBulk)getModel();
		VariazioniStanziamentoResiduoComponentSession comp = (VariazioniStanziamentoResiduoComponentSession)createComponentSession();
		try {
			if (var.getEsercizio_res() == null)
				throw new ApplicationException("Valorizzare l'esercizio residuo.");
			comp.validaOrigineFontiPerAnnoResiduo(context.getUserContext(), var.getEsercizio_residuo(), var.getTipologia_fin());
		} catch (ComponentException e) {
			throw handleException(e);
		} catch (RemoteException e) {
			throw handleException(e);
		}
	}
	/**
	 * Gestione del salvataggio come approvata di una variazione
	 *
	 * @param context	L'ActionContext della richiesta
	 * @throws BusinessProcessException	
	 */
	public void approva(ActionContext context) throws it.cnr.jada.action.BusinessProcessException{
		try {
			VariazioniStanziamentoResiduoComponentSession comp = (VariazioniStanziamentoResiduoComponentSession)createComponentSession();
			Var_stanz_resBulk var_stanz_res = null;
			if (isDaAccertamentoModifica()) {
				var_stanz_res = (Var_stanz_resBulk)comp.controllaApprova(context.getUserContext(), getModel());
			}
			else {
				var_stanz_res = (Var_stanz_resBulk)comp.approva(context.getUserContext(), getModel());
				var_stanz_res = (Var_stanz_resBulk)comp.esitaVariazioneBilancio(context.getUserContext(), var_stanz_res);
				edit(context,var_stanz_res);
			}
		}catch(it.cnr.jada.comp.ComponentException ex){
			throw handleException(ex);
		}catch(java.rmi.RemoteException ex){
			throw handleException(ex);
		}
	}
	public void controllaApprova(ActionContext context) throws it.cnr.jada.action.BusinessProcessException{
		try {
			VariazioniStanziamentoResiduoComponentSession comp = (VariazioniStanziamentoResiduoComponentSession)createComponentSession();
			Var_stanz_resBulk var_stanz_res = (Var_stanz_resBulk) comp.controllaApprova(context.getUserContext(), getModel());
			var_stanz_res.setApprovazioneControllata(true);
			setModel(context,var_stanz_res);
		}catch(it.cnr.jada.comp.ComponentException ex){
			throw handleException(ex);
		}catch(java.rmi.RemoteException ex){
			throw handleException(ex);
		}
	}
	/**
	 * Gestione del salvataggio come respinta di una variazione
	 *
	 * @param context	L'ActionContext della richiesta
	 * @throws BusinessProcessException	
	 */
	public void respingi(ActionContext context) throws it.cnr.jada.action.BusinessProcessException{
		try {
			VariazioniStanziamentoResiduoComponentSession comp = (VariazioniStanziamentoResiduoComponentSession)createComponentSession();
			edit(context,comp.respingi(context.getUserContext(), getModel()));
		}catch(it.cnr.jada.comp.ComponentException ex){
			throw handleException(ex);
		}catch(java.rmi.RemoteException ex){
			throw handleException(ex);
		}
	}
	public void statoPrecedente(ActionContext context) throws it.cnr.jada.action.BusinessProcessException{
		try {
			VariazioniStanziamentoResiduoComponentSession comp = (VariazioniStanziamentoResiduoComponentSession)createComponentSession();
			edit(context,comp.statoPrecedente(context.getUserContext(), getModel()));
		}catch(it.cnr.jada.comp.ComponentException ex){
			throw handleException(ex);
		}catch(java.rmi.RemoteException ex){
			throw handleException(ex);
		}
	}		
	public void basicEdit(it.cnr.jada.action.ActionContext context,it.cnr.jada.bulk.OggettoBulk bulk, boolean doInitializeForEdit) throws it.cnr.jada.action.BusinessProcessException {
	
		super.basicEdit(context, bulk, doInitializeForEdit);

		if (getStatus()!=VIEW){
			Var_stanz_resBulk var_stanz_res = (Var_stanz_resBulk)getModel();
			if (var_stanz_res!=null && var_stanz_res.isCancellatoLogicamente() || 
					var_stanz_res.isPropostaDefinitiva() || 
//					var_stanz_res.isApprovata() || 
					var_stanz_res.isRespinta()) {
				setStatus(VIEW);
			}	
		}

		try
        {
    		if (isDaAccertamentoModifica()) {
    			Var_stanz_resBulk var = (Var_stanz_resBulk)getModel();
    			if (var !=null && var.isPropostaDefinitiva() && !var.isApprovazioneControllata())
    				controllaApprova(context);
            }
        }
        catch(Throwable ex)
        {
        	setMessage(ex.getMessage());
        }
        
      
	}		
	
	protected void initialize(it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException {
		super.initialize(context);
		VariazioniStanziamentoResiduoComponentSession comp = (VariazioniStanziamentoResiduoComponentSession)createComponentSession();
		try {
			setCentro_responsabilita_scrivania(Utility.createCdrComponentSession().cdrFromUserContext(context.getUserContext()));
			setAbilitatoModificaDescVariazioni(UtenteBulk.isAbilitatoModificaDescVariazioni(context.getUserContext()));
		} catch (ComponentException e) {
			throw handleException(e);
		} catch (RemoteException e) {
			throw handleException(e);
		}
		
		setUoSrivania(it.cnr.contab.utenze00.bulk.CNRUserInfo.getUnita_organizzativa(context));

		try {
			if (isDaAccertamentoModifica() && getAccMod().getVariazione()!=null) {
				if (getAccMod().getVariazione().getPg_variazione()!=null) {
					setModel(context, getAccMod().getVariazione());
					cerca(context);
				}
			}
		} catch(Exception e) {
			throw handleException(e);
		}
	}
	public OggettoBulk initializeModelForInsert(ActionContext context,OggettoBulk bulk) throws BusinessProcessException {
		Var_stanz_resBulk var = (Var_stanz_resBulk)super.initializeModelForInsert(context,bulk);
		if (isDaAccertamentoModifica()) {
			var.setTipologia(Var_stanz_resBulk.TIPOLOGIA_ECO);
			var.setAccMod(getAccMod());
		}
		return var;
	}
	public OggettoBulk initializeModelForEdit(ActionContext context,OggettoBulk bulk) throws BusinessProcessException {
		Var_stanz_resBulk var = (Var_stanz_resBulk)super.initializeModelForEdit(context,bulk);
		if (isDaAccertamentoModifica()) {
			var.setAccMod(getAccMod());
		}
		return var;
	}
	/**
	 * Verifica che il CDR della variazione PDG sia uguale al CDR di scrivania 
	 */
	public boolean isCdrScrivania() {
		if (getStatus() == SEARCH)
		  return true;
		try{
			Var_stanz_resBulk var_stanz_res = (Var_stanz_resBulk)getModel();
			if(var_stanz_res.getCentroDiResponsabilita().equalsByPrimaryKey(getCentro_responsabilita_scrivania()))
			  return true;
			return false;
		}catch(NullPointerException ex){
			return false;
		}catch(java.lang.ArrayIndexOutOfBoundsException ex){
			return false;
		}
	}	
	public boolean isAnnullabile() {
		if (getStatus() == SEARCH)
		  return true;
		return isApprovaButtonEnabled();
	}
	public boolean isSaveButtonEnabled()
	{
		if (!isAbilitatoModificaDescVariazioni() && ((Var_stanz_resBulk)getModel()).isApprovata())
			return false;
		else
		return super.isSaveButtonEnabled() && (isCdrScrivania() || isUoEnte());
	}	
	public boolean isDeleteButtonEnabled()
	{
		return super.isDeleteButtonEnabled() && (isCdrScrivania() || isUoEnte()) && !((Var_stanz_resBulk)getModel()).isApprovata();
	}
	/**
	 * Restituisce il valore della propriet� 'salvaDefinitivoButtonEnabled'
	 * Il bottone di SalvaDefinitivo � disponibile solo se:
	 * - la proposta � provvisoria
	 * - il CDR � di 1� Livello
	 *
	 * @return Il valore della propriet� 'salvaDefinitivoButtonEnabled'
	 */
	public boolean isSalvaDefinitivoButtonEnabled() {
		try{
			return (isSaveButtonEnabled()||(super.isSaveButtonEnabled()&&((Var_stanz_resBulk)getModel()).isPropostaProvvisoria()))&& 
					((Var_stanz_resBulk)getModel()).isPropostaProvvisoria() && 
					((Var_stanz_resBulk)getModel()).isNotNew() &&
					(controllaCdrDaAccMod() || isUoArea()|| isUoSac())&&
					controllaBP() &&
					((Var_stanz_resBulk)getModel()).getCentroDiResponsabilita().getCd_cds().equals(getCentro_responsabilita_scrivania().getCd_cds());
		}catch(NullPointerException e){
			return false;
		}			
	}
	private boolean controllaBP() {
		return (!isDirty() || !isDaAccertamentoModifica());
	}

	public boolean isStatoPrecedenteButtonEnabled() {
		try{
			return (isSaveButtonEnabled()||(((Var_stanz_resBulk)getModel()).isPropostaDefinitiva()))&& 
					((Var_stanz_resBulk)getModel()).isPropostaDefinitiva() && 
					((Var_stanz_resBulk)getModel()).isNotNew() &&
					(controllaCdrDaAccMod() || isUoArea()||isUoSac())&&
					((Var_stanz_resBulk)getModel()).getCentroDiResponsabilita().getCd_cds().equals(getCentro_responsabilita_scrivania().getCd_cds());
		}catch(NullPointerException e){
			return false;
		}
		
	}
	
	/**
	 * Restituisce il valore della propriet� 'approvaButtonEnabled'
	 * Il bottone di Approva � disponibile solo se:
	 * - � attivo il bottone di salvataggio
	 * - la proposta di variazione PDG � definitiva
	 * - la UO che sta effettuando l'operazione � di tipo ENTE
	 *
	 * @return Il valore della propriet� 'approvaButtonEnabled'
	 */
	public boolean isApprovaButtonEnabled() {
		try{
			return ((Var_stanz_resBulk)getModel()).isPropostaDefinitiva() && 
			   ((isUoEnte()&& ((Var_stanz_resBulk)getModel()).isEnteAbilitatoAdApprovare())||
				((controllaCdrDaAccMod() || isUoArea())&&
				((Var_stanz_resBulk)getModel()).getCentroDiResponsabilita().getCd_cds().equals(getCentro_responsabilita_scrivania().getCd_cds())&&
				((Var_stanz_resBulk)getModel()).isCdsAbilitatoAdApprovare()));
		}catch(NullPointerException e){
			return false;
		}
			
	}
	/**
	 * ritorna true se proviene dalla mappa degli accertamenti modifica, nel senso
	 * che la variazione � legata ad una modifica ad accertamento residuo, in
	 * tal caso non � necessario che il cdr sia di primo livello
	 * @return
	 */
	public boolean controllaCdrDaAccMod() {
		try{
			return (getCentro_responsabilita_scrivania().getLivello().intValue() == 1 || isDaAccertamentoModifica());
		}catch(NullPointerException e){
			return false;
		}
			
	}
	public boolean isAssestatoResiduoButtonHidden() {
		Var_stanz_resBulk var_stanz_res = (Var_stanz_resBulk)getModel();
		if (!(isEditable() && isEditing() && !isDirty()) || var_stanz_res.isApprovata())
		   return true;
		if (var_stanz_res == null)
		  return true;
		if (var_stanz_res.getTipologia() == null)
		  return true;
		//Se non ho selezionato alcun CDR associato allora  
		if (getCrudAssCDR().getSelection().getFocus() ==-1){
			if (var_stanz_res.getCentroDiResponsabilita().equalsByPrimaryKey(getCentro_responsabilita_scrivania()))
			  return false;
			else
			  return true;
		}else{
			Ass_var_stanz_res_cdrBulk ass_var_stanz_res_cdr = (Ass_var_stanz_res_cdrBulk)(var_stanz_res.getAssociazioneCDR().get(getCrudAssCDR().getSelection().getFocus()));
			boolean isCdrDiScrivania = ass_var_stanz_res_cdr.getCentro_di_responsabilita().equalsByPrimaryKey(getCentro_responsabilita_scrivania());
			boolean tipoView =  var_stanz_res.getTipologia().equalsIgnoreCase(Var_stanz_resBulk.TIPOLOGIA_ECO)?
				controllaCdrDaAccMod()? 
				!ass_var_stanz_res_cdr.getCentro_di_responsabilita().getCd_cds().equalsIgnoreCase(getCentro_responsabilita_scrivania().getCd_cds())
			  :!isCdrDiScrivania			
			:!isCdrDiScrivania;
			return tipoView;			
		}
	}
	public boolean isUoEnte(){
		return (getUoSrivania().getCd_tipo_unita().compareTo(it.cnr.contab.config00.sto.bulk.Tipo_unita_organizzativaHome.TIPO_UO_ENTE)==0);
	}
	public boolean isUoSac(){
		return (getUoSrivania().getCd_tipo_unita().compareTo(it.cnr.contab.config00.sto.bulk.Tipo_unita_organizzativaHome.TIPO_UO_SAC)==0);
	}
	public boolean isUoArea(){
		return (getUoSrivania().getCd_tipo_unita().compareTo(it.cnr.contab.config00.sto.bulk.Tipo_unita_organizzativaHome.TIPO_UO_AREA)==0);
	}
	
	/**
	 * 
	 * Restituisce il valore della propriet� 'nonApprovaButtonEnabled'
	 * Il bottone di NonApprova � disponibile solo se:
	 * - � attivo il bottone di salvataggio
	 * - la proposta di variazione PDG � definitiva
	 * - la UO che sta effettuando l'operazione � di tipo ENTE
	 *
	 * @return Il valore della propriet� 'nonApprovaButtonEnabled'
	 */
	public boolean isNonApprovaButtonEnabled() {
		return isApprovaButtonEnabled();
	}
	
	/**
	 * @return
	 */
	public CdsBulk getCentro_di_spesa_scrivania() {
		return centro_di_spesa_scrivania;
	}

	/**
	 * @return
	 */
	public CdrBulk getCentro_responsabilita_scrivania() {
		return centro_responsabilita_scrivania;
	}

	/**
	 * @return
	 */
	public Unita_organizzativaBulk getUoSrivania() {
		return uoSrivania;
	}

	/**
	 * @param bulk
	 */
	public void setCentro_di_spesa_scrivania(CdsBulk bulk) {
		centro_di_spesa_scrivania = bulk;
	}

	/**
	 * @param bulk
	 */
	public void setCentro_responsabilita_scrivania(CdrBulk bulk) {
		centro_responsabilita_scrivania = bulk;
	}

	/**
	 * @param bulk
	 */
	public void setUoSrivania(Unita_organizzativaBulk bulk) {
		uoSrivania = bulk;
	}
	/**
	 * Metodo utilizzato per creare una toolbar applicativa personalizzata.
	 * @return toolbar Toolbar in uso
	 */
	protected it.cnr.jada.util.jsp.Button[] createToolbar() {
		it.cnr.jada.util.jsp.Button[] toolbar = new it.cnr.jada.util.jsp.Button[14];
		int i = 0;
		toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.search");
		toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.startSearch");
		toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.freeSearch");
		toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.new");
		toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.save");
		toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.delete");
		toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.bringBack");
		toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.print");
		toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.undoBringBack");
		toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.definitiveSave");
		toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.statoPrecedente");	
		toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.approva");
		toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.nonApprova");
		toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.assestatoResiduo");		
		return toolbar;
	}

	public Accertamento_modificaBulk getAccMod() {
		return acrMod;
	}

	public void setAcrMod(Accertamento_modificaBulk acrMod) {
		this.acrMod = acrMod;
	}

	/**
	 * se true la variazione � stata richiamata da una modifica
	 * all'accertamento residuo, in tal caso il processo deve essere
	 * transazionale in modo da creare una modifica e una variazione
	 * in contemporanea
	 * 
	 * @return
	 */
	public boolean isDaAccertamentoModifica() {
		if (getAccMod()!=null)
			return true;
		return false;
	}

	public void cerca(ActionContext actioncontext) throws RemoteException, InstantiationException, RemoveException, BusinessProcessException
	{
		try
		{
			fillModel(actioncontext);
			OggettoBulk oggettobulk = getModel();
			RemoteIterator remoteiterator = find(actioncontext, null, oggettobulk);
			if(remoteiterator == null || remoteiterator.countElements() == 0)
			{
				EJBCommonServices.closeRemoteIterator(remoteiterator);
				return;
			}
			if(remoteiterator.countElements() == 1)
			{
				OggettoBulk oggettobulk1 = (OggettoBulk)remoteiterator.nextElement();
				EJBCommonServices.closeRemoteIterator(remoteiterator);
				if(oggettobulk1 != null) {
					edit(actioncontext, oggettobulk1);
				}
				return;
			}
			else {
				EJBCommonServices.closeRemoteIterator(remoteiterator);
				//reset(actioncontext);
				setStatus(SEARCH);
			}
		}
		catch(Throwable throwable)
		{
			throw handleException(throwable);
		}
	}
	public boolean isBringbackButtonEnabled() {
		return super.isBringbackButtonEnabled() || (isDaAccertamentoModifica() && isViewing());
	}
	public boolean isUndoBringBackButtonEnabled() {
		return super.isUndoBringBackButtonEnabled() || (isDaAccertamentoModifica() && isViewing());
	}
	public boolean isNonApprovaButtonHidden() {
		if (isDaAccertamentoModifica())
			return true;
		return false;
	}
	public boolean isStatoPrecedenteButtonHidden() {
		if (isDaAccertamentoModifica())
			return isSalvaDefinitivoButtonEnabled();
		return false;
	}
	public boolean isApprovaButtonHidden() {
		if (isDaAccertamentoModifica())
			return true;
		return false;
	}
	public boolean isROTipologia(){
    	return isDaAccertamentoModifica();
	}    
	
	private boolean abilitatoModificaDescVariazioni;
	
	public boolean isAbilitatoModificaDescVariazioni() {
		return abilitatoModificaDescVariazioni;
	}
	public void setAbilitatoModificaDescVariazioni(boolean abilitatoModificaDescVariazioni) {
		this.abilitatoModificaDescVariazioni = abilitatoModificaDescVariazioni;
	}	
}
