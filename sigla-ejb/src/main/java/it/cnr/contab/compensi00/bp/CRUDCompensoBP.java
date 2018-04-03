package it.cnr.contab.compensi00.bp;

import it.cnr.contab.anagraf00.tabrif.bulk.Tipo_rapportoBulk;
import it.cnr.contab.chiusura00.ejb.RicercaDocContComponentSession;
import it.cnr.contab.compensi00.docs.bulk.CompensoBulk;
import it.cnr.contab.compensi00.docs.bulk.Compenso_rigaBulk;
import it.cnr.contab.compensi00.docs.bulk.ConguaglioBulk;
import it.cnr.contab.compensi00.docs.bulk.Contributo_ritenutaBulk;
import it.cnr.contab.compensi00.docs.bulk.V_doc_cont_compBulk;
import it.cnr.contab.compensi00.docs.bulk.V_terzo_per_compensoBulk;
import it.cnr.contab.compensi00.ejb.CompensoComponentSession;
import it.cnr.contab.compensi00.tabrif.bulk.AddizionaliBulk;
import it.cnr.contab.compensi00.tabrif.bulk.Tipo_trattamentoBulk;
import it.cnr.contab.config00.esercizio.bulk.EsercizioBulk;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.docamm00.bp.IDocumentoAmministrativoBP;
import it.cnr.contab.docamm00.bp.IDocumentoAmministrativoSpesaBP;
import it.cnr.contab.docamm00.bp.VoidableBP;
import it.cnr.contab.docamm00.docs.bulk.Documento_genericoBulk;
import it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_IBulk;
import it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_rigaBulk;
import it.cnr.contab.docamm00.docs.bulk.IDocumentoAmministrativoBulk;
import it.cnr.contab.docamm00.docs.bulk.TrovatoBulk;
import it.cnr.contab.docamm00.ejb.DocumentoGenericoComponentSession;
import it.cnr.contab.docamm00.ejb.FatturaPassivaComponentSession;
import it.cnr.contab.docamm00.ejb.RiportoDocAmmComponentSession;
import it.cnr.contab.docamm00.fatturapa.bulk.DocumentoEleAllegatiBulk;
import it.cnr.contab.doccont00.bp.IDefferedUpdateSaldiBP;
import it.cnr.contab.doccont00.bp.IValidaDocContBP;
import it.cnr.contab.doccont00.core.bulk.ObbligazioneBulk;
import it.cnr.contab.doccont00.core.bulk.ObbligazioneResBulk;
import it.cnr.contab.doccont00.core.bulk.Obbligazione_scad_voceBulk;
import it.cnr.contab.doccont00.core.bulk.Obbligazione_scadenzarioBulk;
import it.cnr.contab.incarichi00.bulk.Incarichi_repertorio_annoBulk;
import it.cnr.contab.ordmag.ordini.bulk.EvasioneOrdineBulk;
import it.cnr.contab.reports.bp.OfflineReportPrintBP;
import it.cnr.contab.reports.bulk.Print_spooler_paramBulk;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.utenze00.bp.GestioneUtenteBP;
import it.cnr.contab.util.Utility;
import it.cnr.jada.UserContext;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.action.Config;
import it.cnr.jada.action.HttpActionContext;
import it.cnr.jada.bulk.BulkList;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.bulk.ValidationException;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.util.action.AbstractPrintBP;
import it.cnr.jada.util.action.SimpleDetailCRUDController;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Stream;

/**
 * Insert the type's description here.
 * Creation date: (21/02/2002 16.12.44)
 * @author: Roberto Fantino
 */
public class CRUDCompensoBP extends it.cnr.jada.util.action.SimpleCRUDBP implements IDefferedUpdateSaldiBP, IDocumentoAmministrativoSpesaBP, IValidaDocContBP
{
	private final SimpleDetailCRUDController compensoRigheController = new SimpleDetailCRUDController("CompensoRighe",Compenso_rigaBulk.class,"compensoRigaColl",this) {
	    @Override
	    public void writeHTMLToolbar(
	            javax.servlet.jsp.PageContext context,
	            boolean reset,
	            boolean find,
	            boolean delete, boolean closedToolbar) throws java.io.IOException, javax.servlet.ServletException {

	        super.writeHTMLToolbar(context, isBottoneCreaObbligazioneEnabled(), find, isBottoneEliminaObbligazioneEnabled(), false);

			boolean isFromBootstrap = HttpActionContext.isFromBootstrap(context);
	        it.cnr.jada.util.jsp.JSPUtils.toolbarButton(
	                context,
	                HttpActionContext.isFromBootstrap(context) ? "fa fa-fw fa-edit" : "img/redo16.gif",
	                isBottoneModificaManualeObbligazioneEnabled() ? "javascript:submitForm('doModificaManualeObbligazione')" : null,
	                true,
	                "Aggiorna in manuale",
	                "btn btn-outline-primary  btn-sm btn-title",
	                isFromBootstrap);
	        it.cnr.jada.util.jsp.JSPUtils.toolbarButton(
	                context,
	                HttpActionContext.isFromBootstrap(context) ? "fa fa-fw fa-pencil" : "img/refresh16.gif",
	                isBottoneModificaAutomaticaObbligazioneEnabled() ? "javascript:submitForm('doModificaAutomaticaObbligazione')" : null,
	                false,
	                "Aggiorna in automatico",
	                "btn btn-outline-info btn-sm btn-title",
	                isFromBootstrap);
	        super.closeButtonGROUPToolbar(context);
	    }
	    
		public void validateForDelete(ActionContext actioncontext, OggettoBulk oggettobulk) throws ValidationException {
			CompensoBulk compenso = (CompensoBulk)this.getParentModel();
			if (compenso.isStatoCompensoEseguiCalcolo())
				throw new ValidationException("E' necessario eseguire il calcolo prima di eliminare l'impegno");
	    };

	    public OggettoBulk removeDetail(OggettoBulk oggettobulk, int i) {
			Compenso_rigaBulk compensoRiga=(Compenso_rigaBulk)oggettobulk;
			compensoRiga.setToBeDeleted();
			if (compensoRiga.getObbligazioneScadenzario()!=null) {
				CompensoBulk compenso = (CompensoBulk)this.getParentModel();
				compenso.addToDocumentiContabiliCancellati(compensoRiga.getObbligazioneScadenzario());
			}
			return removeDetail(i);
	    };

	    public void remove(ActionContext actioncontext) throws ValidationException ,BusinessProcessException {
			super.remove(actioncontext);
			CompensoBulk compenso = (CompensoBulk)this.getParentModel();
			if (this.getDetails().isEmpty())
				compenso.setStato_cofi(CompensoBulk.STATO_INIZIALE);
			if (compenso.getImportoObbligazione().compareTo(new java.math.BigDecimal(0))<=0)
				compenso.setStatoCompensoToObbligazioneSincronizzata();
			else
				compenso.setStatoCompensoToSincronizzaObbligazione();

			((CRUDCompensoBP)this.getParentController()).setMessage(it.cnr.jada.util.action.FormBP.WARNING_MESSAGE, "Impegni scollegati.");
	    };
	};
	
	private final SimpleDetailCRUDController contributiCRUDController = new SimpleDetailCRUDController("contributiCRUDController",Contributo_ritenutaBulk.class,"contributi",this, false){
		@Override
		public void writeHTMLToolbar(javax.servlet.jsp.PageContext context,	boolean reset, boolean find, boolean delete, boolean closedToolbar) throws java.io.IOException, javax.servlet.ServletException
		{
			super.writeHTMLToolbar(context, reset, find, delete, false);

			// Aggiungo alla table dei contributi per visualizzare ulteriori dettagli per ogni riga
			boolean isFromBootstrap = HttpActionContext.isFromBootstrap(context);
			if (getModelIndex()==-1)
				it.cnr.jada.util.jsp.JSPUtils.toolbarButton(context, 
						isFromBootstrap?"fa fa-fw fa-list text-primary":"img/uncheckall16.gif",
						null,
						false,
						"Visualizza dettagli",
						isFromBootstrap);
			else
				it.cnr.jada.util.jsp.JSPUtils.toolbarButton(context, 
						isFromBootstrap?"fa fa-fw fa-list text-primary":"img/uncheckall16.gif",
						"javascript:submitForm('doVisualizzaDettagli')",
						false,
						"Visualizza dettagli",
						isFromBootstrap);
			super.closeButtonGROUPToolbar(context);
				
		}
	};
	private it.cnr.contab.doccont00.core.bulk.OptionRequestParameter userConfirm = null;
	private final SimpleDetailCRUDController docContAssociatiCRUDController = new SimpleDetailCRUDController("docContAssociatiCRUDController",V_doc_cont_compBulk.class,"docContAssociati",this, false){
		@Override
		public void writeHTMLToolbar(javax.servlet.jsp.PageContext context,	boolean reset, boolean find, boolean delete, boolean closedToolbar) throws java.io.IOException, javax.servlet.ServletException
		{

			super.writeHTMLToolbar(context, reset, find, delete, false);

			// Aggiungo un bottone alla toolbar dei documenti associati per aprire in visualizzazione il documento contabile associato
			boolean isFromBootstrap = HttpActionContext.isFromBootstrap(context);
			if (getModelIndex()==-1)
				it.cnr.jada.util.jsp.JSPUtils.toolbarButton(context, 
						isFromBootstrap?"fa fa-fw fa-list text-primary":"img/uncheckall16.gif",
						null,
						false,
						"Visualizza documento",
						isFromBootstrap);
			else
				it.cnr.jada.util.jsp.JSPUtils.toolbarButton(context, 
						isFromBootstrap?"fa fa-fw fa-list text-primary":"img/uncheckall16.gif",
						"javascript:submitForm('doVisualizzaDocumentoContabile')",
						false,
						"Visualizza documento",
						isFromBootstrap);
			super.closeButtonGROUPToolbar(context);
		}
	};

	//	Variabili usate per la gestione del "RIPORTA" documento ad esercizio precedente/successivo
	private boolean annoSolareInScrivania = true;
	private boolean riportaAvantiIndietro = false;
	private boolean carryingThrough = false;	
	private boolean ribaltato;
	private boolean nocompenso = true;
	private Boolean isGestioneIncarichiEnabled=null; 
	
	//private Boolean isGestionePrestazioneCompensoEnabled = null;
	
/**
 * CRUDCompensoBP constructor comment.
 */
public CRUDCompensoBP() {
	super("Tr");
}
/**
 * CRUDCompensoBP constructor comment.
 * @param function java.lang.String
 */
public CRUDCompensoBP(String function) {
	super(function+"Tr");
}
/**
 * Il compenso viene messo in Visualizzazione se :
 *	-	compenso da stipendi
 * 	-	compenso pagato (contabilizzato in cofi o registrato in fondo eco)
 * 		oppure annullato oppure è legato ad obbligazione riportata
 *
**/
private void aggiornaStatoBP() {

	CompensoBulk compenso = (CompensoBulk)getModel();

	if (!isViewing())
	{
		if (Boolean.TRUE.equals(compenso.getFl_compenso_stipendi()))
			setStatus(VIEW);
		else if (compenso!=null && (compenso.isPagato() || compenso.isAnnullato()))
			setStatus(VIEW);
	}
}
public void basicEdit(it.cnr.jada.action.ActionContext context,it.cnr.jada.bulk.OggettoBulk bulk, boolean doInitializeForEdit) throws it.cnr.jada.action.BusinessProcessException {
	
	super.basicEdit(context, bulk, doInitializeForEdit);
	try {
		isCompensoValidoXStampa(context, (CompensoBulk) bulk);
	} catch (BusinessProcessException e) {
		e.printStackTrace();
	} catch (ComponentException e) {
		e.printStackTrace();
	} catch (RemoteException e) {
		e.printStackTrace();
	}
	aggiornaStatoBP();
}
/**
  * isAnnoSolareInScrivania = TRUE --> se l'anno di scrivania e' uguale a quello solare
  *	isRiportaAvantiIndietro = TRUE --> se sia'esercizio di scrivania che quello successivo sono aperti
  */

protected boolean basicRiportaButtonHidden() 
{
	CompensoBulk compenso = (CompensoBulk) getModel();
	
	return	isAnnoSolareInScrivania() || !isRiportaAvantiIndietro() ||			
			isDeleting() ||	!isEditing() || compenso.isSenzaCalcoli() || 
			compenso.isPagato() || compenso.isAnnullato() || !compenso.isObbligazioneObbligatoria() ||
			(getParent()!=null && (getParent() instanceof IDocumentoAmministrativoBP));
}
/**
 * Insert the method's description here.
 * Creation date: (25/02/2002 12.56.44)
 * @return it.cnr.contab.compensi00.docs.bulk.CompensoBulk
 * @param userContext it.cnr.jada.UserContext
 * @param compenso it.cnr.contab.compensi00.docs.bulk.CompensoBulk
 * @param aTerzo it.cnr.contab.compensi00.docs.bulk.V_terzo_per_compensoBulk
 */
public void completaTerzo(ActionContext context, CompensoBulk compenso, V_terzo_per_compensoBulk vTerzo) throws BusinessProcessException {

	try {

		CompensoComponentSession component = (CompensoComponentSession)createComponentSession();
		CompensoBulk compensoClone = component.completaTerzo(context.getUserContext(), compenso, vTerzo);

		setModel(context, compensoClone);

	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}

}
public ConguaglioBulk conguaglioAssociatoACompenso(ActionContext context) throws BusinessProcessException{

	try{
		CompensoComponentSession session = (CompensoComponentSession)createComponentSession();
		return session.conguaglioAssociatoACompenso(context.getUserContext(), (CompensoBulk)getModel());
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}
}
/**
 * Insert the method's description here.
 * Creation date: (25/02/2002 12.56.44)
 * @return it.cnr.contab.compensi00.docs.bulk.CompensoBulk
 * @param userContext it.cnr.jada.UserContext
 * @param compenso it.cnr.contab.compensi00.docs.bulk.CompensoBulk
 * @param aTerzo it.cnr.contab.compensi00.docs.bulk.V_terzo_per_compensoBulk
 */
public void contabilizzaCompensoCOFI(ActionContext context) throws BusinessProcessException {

	try {

		CompensoComponentSession comp = (CompensoComponentSession)createComponentSession();
		CompensoBulk compenso = comp.doContabilizzaCompensoCofi(context.getUserContext(), (CompensoBulk)getModel());
		setModel(context, compenso);
		commitUserTransaction();

		aggiornaStatoBP();
		resetTabs(context);

	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}

}
public void create(it.cnr.jada.action.ActionContext context)
	throws	it.cnr.jada.action.BusinessProcessException {
		
	try {
		getModel().setToBeCreated();
		setModel(
				context,
				((CompensoComponentSession)createComponentSession()).creaConBulk(
																			context.getUserContext(),
																			getModel(),
																			getUserConfirm()));
		setUserConfirm(null);
	} catch(Throwable e) {
		throw handleException(e);
	}
}
/**
  *	Inizializzazione della Toolbar del Compenso
  */
public it.cnr.jada.util.jsp.Button[] createToolbar() {

	it.cnr.jada.util.jsp.Button[] toolbar = new it.cnr.jada.util.jsp.Button[12];
	int i = 0;
	toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.search");
	toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.startSearch");
	toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.freeSearch");
	toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.new");
	toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.save");
	toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.delete");
	toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.bringBack");
	toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.undoBringBack");
	toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.contabilizza");
	toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.print");
	toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.riportaAvanti");
	toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.riportaIndietro");		

	return toolbar;
}
public void doConfermaModificaCORI(ActionContext context) throws BusinessProcessException{

	try{
		CompensoComponentSession session = (CompensoComponentSession)createComponentSession();
		CompensoBulk compenso = session.confermaModificaCORI(context.getUserContext(), (CompensoBulk)getModel(), (Contributo_ritenutaBulk)getContributiCRUDController().getModel());

		getContributiCRUDController().setModelIndex(context, -1);
		setModel(context, compenso);

	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}
/**
  *	Gestisce la richiesta di eliminazione dell'associazione compenso/scadenza obbligazione
 */
public CompensoBulk doEliminaObbligazioni(ActionContext context) throws BusinessProcessException {
	try{
		CompensoBulk compenso = (CompensoBulk)getModel();
		this.getCompensoRigheController().removeAll(context);
		return compenso;
	}catch(ValidationException ex){
		throw handleException(ex);
	}
}
public void edit(ActionContext context, it.cnr.jada.bulk.OggettoBulk bulk, boolean doInitializeForEdit) throws it.cnr.jada.action.BusinessProcessException 
{
	setCarryingThrough(false);
	super.edit(context, bulk, doInitializeForEdit);
}
public void elaboraScadenza(ActionContext context, Obbligazione_scadenzarioBulk newScad, boolean isModifica) throws BusinessProcessException{
	try{
		CompensoBulk compenso = (CompensoBulk)getModel();

		if (newScad.getObbligazione().getObbligazione_scadenzarioColl().isEmpty()) {
			ObbligazioneBulk obb = (ObbligazioneBulk)createComponentSession().findByPrimaryKey(context.getUserContext(), newScad.getObbligazione());
			newScad.setObbligazione(obb);
			newScad.getObbligazione().setObbligazione_scadenzarioColl(
					new BulkList(((CompensoComponentSession)createComponentSession()).find(context.getUserContext(), 
							ObbligazioneBulk.class, "findObbligazione_scadenzarioList", newScad.getObbligazione())));
		}
		
		validaScadenze(compenso, newScad, isModifica);
		compenso.sincronizzaScadenzeCancellate(newScad);

		if (isModifica) {
			Compenso_rigaBulk riga = (Compenso_rigaBulk)this.getCompensoRigheController().getModel();
			if (!riga.getObbligazioneScadenzario().equalsByPrimaryKey(newScad))
				compenso.addToDocumentiContabiliCancellati(riga.getObbligazioneScadenzario());
			riga.setObbligazioneScadenzario(newScad);
			riga.setToBeUpdated();
		} else {
			Compenso_rigaBulk riga = new Compenso_rigaBulk();
			riga.setObbligazioneScadenzario(newScad);
			riga.setIm_totale_riga_compenso(compenso.getIm_totale_da_impegnare());
			this.getCompensoRigheController().add(context,riga);
		}
		
		compenso.removeFromDocumentiContabiliCancellati(newScad);
		compenso.setStato_cofi(CompensoBulk.STATO_CONTABILIZZATO);

		((CompensoComponentSession)createComponentSession()).lockScadenza(context.getUserContext(), newScad);

		if (!isObbligazioneValida(context, newScad))
			compenso.setStatoCompensoToSincronizzaObbligazione();
		else
			compenso.setStatoCompensoToObbligazioneSincronizzata();

		setModel(context, compenso);
		setDirty(true);

	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}
/**
  *	Gestisce la richiesta di "esecuzione del calcolo" del compenso
 */
public void eseguiCalcolo(ActionContext context) throws BusinessProcessException {

	try {

		validaDatiLiquidazione(context);

		CompensoComponentSession comp = (CompensoComponentSession)createComponentSession();
		CompensoBulk compenso = comp.eseguiCalcolo(context.getUserContext(), (CompensoBulk)getModel());

		setModel(context, compenso);

	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}
public void findListaBanche(ActionContext context) throws BusinessProcessException{

	try{
		CompensoBulk compenso = (CompensoBulk)getModel();
		if (compenso.getModalitaPagamento() != null) {
			CompensoComponentSession component = (CompensoComponentSession)createComponentSession();
			java.util.List coll = component.findListaBanche(context.getUserContext(), compenso);

			//	Assegno di default la prima banca tra quelle selezionate
			if(coll == null || coll.isEmpty()){
				compenso.setBanca(null);
//				throw new it.cnr.jada.comp.ApplicationException("Non esistono banche associate alla modalità selezionata");
			}
			else
				compenso.setBanca((it.cnr.contab.anagraf00.core.bulk.BancaBulk)new java.util.Vector(coll).firstElement());
		}else
			compenso.setBanca(null);

	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}
/**
 * Effettua una operazione di ricerca per un attributo di un modello.
 *
 * @param userContext	lo userContext che ha generato la richiesta
 * @param filtro		modello che fa da contesto alla ricerca (il modello del FormController padre del
 * 						controller che ha scatenato la ricerca)
 * @return un RemoteIterator sul risultato della ricerca o null se la ricerca non ha ottenuto nessun risultato
 *
**/
public it.cnr.jada.util.RemoteIterator findObbligazioni(it.cnr.jada.UserContext userContext, it.cnr.contab.docamm00.docs.bulk.Filtro_ricerca_obbligazioniVBulk filtro) throws it.cnr.jada.action.BusinessProcessException {

	try {

		CompensoComponentSession sess = (CompensoComponentSession)createComponentSession();
		return sess.cercaObbligazioni(userContext, filtro);

	} catch (it.cnr.jada.comp.ComponentException e) {
		throw handleException(e);
	} catch (java.rmi.RemoteException e) {
		throw handleException(e);
	}
}
/**
 * Effettua una operazione di ricerca per un attributo di un modello.
 *
 * @param actionContext contesto dell'azione in corso
 * @param clauses Albero di clausole da utilizzare per la ricerca
 * @param bulk prototipo del modello di cui si effettua la ricerca
 * @param context modello che fa da contesto alla ricerca (il modello del FormController padre del
 * 			controller che ha scatenato la ricerca)
 * @return un RemoteIterator sul risultato della ricerca o null se la ricerca non ha ottenuto nessun risultato
 *
**/
public it.cnr.jada.util.RemoteIterator findObbligazioniAttributes(it.cnr.jada.action.ActionContext actionContext, it.cnr.jada.persistency.sql.CompoundFindClause clauses, it.cnr.jada.bulk.OggettoBulk bulk, it.cnr.jada.bulk.OggettoBulk context, java.lang.String property) throws it.cnr.jada.action.BusinessProcessException {

	try {

		CompensoComponentSession sess = (CompensoComponentSession )createComponentSession();
		return it.cnr.jada.util.ejb.EJBCommonServices.openRemoteIterator(
												actionContext,
												sess.cerca(
														actionContext.getUserContext(),
														clauses,
														bulk,
														context,
														property));
	} catch (it.cnr.jada.comp.ComponentException e) {
		throw handleException(e);
	} catch (java.rmi.RemoteException e) {
		throw handleException(e);
	}
}
public void findTipiRapporto(ActionContext context) throws BusinessProcessException{

	try{
		CompensoBulk compenso = (CompensoBulk)getModel();
		if (compenso.getTerzo()!= null) {
			CompensoComponentSession component = (CompensoComponentSession)createComponentSession();
			java.util.Collection coll = component.findTipiRapporto(context.getUserContext(), compenso);
			compenso.setTipiRapporto(coll);

			if(coll == null || coll.isEmpty()){
				compenso.setTipoRapporto(null);
				throw new it.cnr.jada.comp.ApplicationException("Non esistono Tipi Rapporto validi associati al terzo selezionato");
			}
		}else
			compenso.setTipoRapporto(null);

	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}
public void findTipiTrattamento(ActionContext context) throws BusinessProcessException{

	try{
		CompensoBulk compenso = (CompensoBulk)getModel();
		if (compenso.getTipoRapporto()!= null) {
			CompensoComponentSession component = (CompensoComponentSession)createComponentSession();
			java.util.Collection coll = component.findTipiTrattamento(context.getUserContext(), compenso);
			compenso.setTipiTrattamento(coll);

			if(coll == null || coll.isEmpty()){
				compenso.setTipoTrattamento(null);
				throw new it.cnr.jada.comp.ApplicationException("Non esistono Tipi Trattamento associati al Tipo di Rapporto selezionato");
			}
		}else
			compenso.setTipoTrattamento(null);
			
	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}
public void findTipiPrestazioneCompenso(ActionContext context) throws BusinessProcessException{

	try{
		CompensoBulk compenso = (CompensoBulk)getModel();
		if (compenso.getTipoRapporto()!= null) {
			CompensoComponentSession component = (CompensoComponentSession)createComponentSession();
			java.util.Collection coll = component.findTipiPrestazioneCompenso(context.getUserContext(), compenso);
			compenso.setTipiPrestazioneCompenso(coll);

			if((coll == null || coll.isEmpty()) && compenso.isPrestazioneCompensoEnabled()){
				compenso.setTipoPrestazioneCompenso(null);
				throw new it.cnr.jada.comp.ApplicationException("Non esistono Tipi di prestazione associati al Tipo di Rapporto selezionato");
			}
		}else
			compenso.setTipoPrestazioneCompenso(null);
			
	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}
/**
 * Insert the method's description here.
 * Creation date: (27/05/2002 14.40.04)
 * @return it.cnr.contab.doccont00.core.bulk.Obbligazione_scadenzarioBulk
 */
public it.cnr.contab.doccont00.core.bulk.Accertamento_scadenzarioBulk getAccertamento_scadenziario_corrente() {
	return null;
}
/**
 * Insert the method's description here.
 * Creation date: (27/05/2002 14.40.04)
 * @return it.cnr.contab.doccont00.core.bulk.Obbligazione_scadenzarioBulk
 */
public IDocumentoAmministrativoBulk getBringBackDocAmm() {

	return getDocumentoAmministrativoCorrente();
}
/**
 * @param context Il contesto dell'azione
 * @param accertamento it.cnr.contab.doccont00.core.bulk.AccertamentoBulk
 * @param mode java.lang.String
 * @return it.cnr.jada.util.action.CRUDBP
 */
public static CRUDCompensoBP getBusinessProcessFor(it.cnr.jada.action.ActionContext context, CompensoBulk compenso, String mode) throws it.cnr.jada.action.BusinessProcessException 
{
	if(compenso == null) 
		return null;
	else
		return (CRUDCompensoBP)context.getUserInfo().createBusinessProcess(context,"CRUDCompensoBP",new Object[] { mode });
}
/**
 * Insert the method's description here.
 * Creation date: (04/03/2002 14.59.58)
 * @return it.cnr.jada.util.action.SimpleDetailCRUDController
 */
public final SimpleDetailCRUDController getContributiCRUDController() {
	return contributiCRUDController;
}
/**
 * Insert the method's description here.
 * Creation date: (24/05/2002 13.22.57)
 * @return it.cnr.contab.doccont00.core.bulk.IDefferUpdateSaldi
 */
public it.cnr.contab.doccont00.core.bulk.IDefferUpdateSaldi getDefferedUpdateSaldiBulk() {

	if (getParent() != null && getParent() instanceof IDefferedUpdateSaldiBP)
		return getDefferedUpdateSaldiParentBP().getDefferedUpdateSaldiBulk();
	return (it.cnr.contab.doccont00.core.bulk.IDefferUpdateSaldi)getModel();
}
/**
 * Insert the method's description here.
 * Creation date: (24/05/2002 13.17.50)
 * @return it.cnr.contab.doccont00.bp.IDefferedUpdateSaldiBP
 */
public IDefferedUpdateSaldiBP getDefferedUpdateSaldiParentBP() {

	if (getParent() != null && getParent() instanceof IDefferedUpdateSaldiBP)
		return ((IDefferedUpdateSaldiBP)getParent()).getDefferedUpdateSaldiParentBP();
	return (IDefferedUpdateSaldiBP)this;
}
/**
 * Insert the method's description here.
 * Creation date: (27/05/2002 14.40.04)
 * @return it.cnr.contab.doccont00.core.bulk.Obbligazione_scadenzarioBulk
 */
public it.cnr.contab.docamm00.docs.bulk.Risultato_eliminazioneVBulk getDeleteManager() {
	return null;
}
/**
 * Insert the method's description here.
 * Creation date: (27/05/2002 17.28.12)
 * @return it.cnr.jada.bulk.OggettoBulk
 */
public it.cnr.jada.bulk.OggettoBulk getDocAmmModel() {
	return getModel();
}
/**
 * Insert the method's description here.
 * Creation date: (04/03/2002 14.59.58)
 * @return it.cnr.jada.util.action.SimpleDetailCRUDController
 */
public final SimpleDetailCRUDController getDocContAssociatiCRUDController() {
	return docContAssociatiCRUDController;
}
/**
 * Insert the method's description here.
 * Creation date: (27/05/2002 14.40.05)
 * @return it.cnr.contab.doccont00.core.bulk.Obbligazione_scadenzarioBulk
 */
public IDocumentoAmministrativoBulk getDocumentoAmministrativoCorrente() {
	return (IDocumentoAmministrativoBulk)getModel();
}
/**
 * Insert the method's description here.
 * Creation date: (27/05/2002 14.40.04)
 * @return it.cnr.contab.doccont00.core.bulk.Obbligazione_scadenzarioBulk
 */
public it.cnr.contab.doccont00.core.bulk.Obbligazione_scadenzarioBulk getObbligazione_scadenziario_corrente() {
	return Optional.ofNullable(this.getCompensoRigheController())
				.map(SimpleDetailCRUDController::getModel)
				.filter(Compenso_rigaBulk.class::isInstance)
				.map(Compenso_rigaBulk.class::cast)
				.map(Compenso_rigaBulk::getObbligazioneScadenzario)
				.orElse(null);
}
/**
 * Insert the method's description here.
 * Creation date: (19/07/2002 12.53.56)
 * @return it.cnr.contab.doccont00.core.bulk.OptionRequestParameter
 */
public it.cnr.contab.doccont00.core.bulk.OptionRequestParameter getUserConfirm() {
	return userConfirm;
}
protected void init(Config config, ActionContext context) throws BusinessProcessException 
{
	// Se NULL allora non sono in un contesto transazionale
	// Imposto il compenso NON EDITABILE
	if (getUserTransaction()==null)
		setEditable(false);

	super.init(config,context);

	try {
		setGestioneIncarichiEnabled(Utility.createParametriCnrComponentSession().getParametriCnr(context.getUserContext(), CNRUserContext.getEsercizio(context.getUserContext())).getFl_incarico());
/*
		String attivaPrestazione = ((it.cnr.contab.config00.ejb.Configurazione_cnrComponentSession)it.cnr.jada.util.ejb.EJBCommonServices.createEJB("CNRCONFIG00_EJB_Configurazione_cnrComponentSession")).getVal01(context.getUserContext(), CNRUserContext.getEsercizio(context.getUserContext()), "*", "GESTIONE_COMPENSI", "ATTIVA_PRESTAZIONE");
		if (attivaPrestazione==null)
			throw new ApplicationException("Configurazione CNR: non sono stati impostati i valori per GESTIONE_COMPENSI - ATTIVA_PRESTAZIONE");
		if (attivaPrestazione.compareTo(new String("Y"))==0)
		    setGestionePrestazioneCompensoEnabled(true);
		else
			setGestionePrestazioneCompensoEnabled(false);
*/
	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
		
	resetTabs(context);
}
public it.cnr.jada.bulk.OggettoBulk initializeModelForEdit(ActionContext context,it.cnr.jada.bulk.OggettoBulk bulk) throws BusinessProcessException 
{
	CompensoBulk compenso = (CompensoBulk)super.initializeModelForEdit(context,bulk);	
	
	return initializePerRiporta(context, compenso);
}
public it.cnr.jada.bulk.OggettoBulk initializeModelForFreeSearch(ActionContext context,it.cnr.jada.bulk.OggettoBulk bulk) throws BusinessProcessException 
{
	CompensoBulk compenso = (CompensoBulk)super.initializeModelForFreeSearch(context,bulk);	

	return initializePerRiporta(context, compenso);
}
public it.cnr.jada.bulk.OggettoBulk initializeModelForInsert(ActionContext context,it.cnr.jada.bulk.OggettoBulk bulk) throws BusinessProcessException 
{
	CompensoBulk compenso = (CompensoBulk)super.initializeModelForInsert(context,bulk);	
	
	return initializePerRiporta(context, compenso);
}
public it.cnr.jada.bulk.OggettoBulk initializeModelForSearch(ActionContext context,it.cnr.jada.bulk.OggettoBulk bulk) throws BusinessProcessException 
{
	CompensoBulk compenso = (CompensoBulk)super.initializeModelForSearch(context,bulk);	

	return initializePerRiporta(context, compenso);
}
public it.cnr.jada.bulk.OggettoBulk initializePerRiporta(ActionContext context, CompensoBulk compenso) throws BusinessProcessException 
{
	try 
	{
		java.sql.Timestamp tsOdierno = it.cnr.jada.util.ejb.EJBCommonServices.getServerDate();
	   	java.util.GregorianCalendar tsOdiernoGregorian = (java.util.GregorianCalendar) java.util.GregorianCalendar.getInstance();
	   	tsOdiernoGregorian.setTime(tsOdierno);
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
		compenso.setAnnoSolare(tsOdiernoGregorian.get(java.util.GregorianCalendar.YEAR));
		compenso.setEsercizioScrivania(it.cnr.contab.utenze00.bp.CNRUserContext.getEsercizio(context.getUserContext()).intValue());

		setAnnoSolareInScrivania(compenso.getAnnoSolare() == compenso.getEsercizioScrivania());
		
		setRibaltato(initRibaltato(context));

		if (!isAnnoSolareInScrivania()) 
		{
			String cds = it.cnr.contab.utenze00.bp.CNRUserContext.getCd_cds(context.getUserContext());
			try 
			{
				DocumentoGenericoComponentSession session = (DocumentoGenericoComponentSession)createComponentSession("CNRDOCAMM00_EJB_DocumentoGenericoComponentSession", DocumentoGenericoComponentSession.class );
				boolean esercizioScrivaniaAperto = session.verificaStatoEsercizio(context.getUserContext(), new EsercizioBulk(cds, new Integer(compenso.getEsercizioScrivania())));
				boolean esercizioSuccessivoAperto = session.verificaStatoEsercizio(context.getUserContext(), new EsercizioBulk(cds, new Integer(compenso.getEsercizioScrivania()+1)));
				setRiportaAvantiIndietro(esercizioScrivaniaAperto && esercizioSuccessivoAperto && isRibaltato());
			} 
			catch (Throwable t) 
			{
				// handleException(t);
				throw new BusinessProcessException(t);				
			}
		} 
		else
			setRiportaAvantiIndietro(false);
	} 
	catch (javax.ejb.EJBException e) 
	{
		setAnnoSolareInScrivania(false);
		throw new BusinessProcessException(e);		
	}
	return compenso;
}
protected void initializePrintBP(ActionContext context, AbstractPrintBP bp) {

	OfflineReportPrintBP printbp = (OfflineReportPrintBP)bp;
	CompensoBulk compenso = (CompensoBulk)getModel();
//	printbp.setReportName("/docamm/docamm/compenso.rpt");
//	printbp.setReportParameter(0, compenso.getCd_cds());
//	printbp.setReportParameter(1, compenso.getCd_unita_organizzativa());
//	printbp.setReportParameter(2, compenso.getEsercizio().toString());
//	printbp.setReportParameter(3, compenso.getPg_compenso().toString());
	
	printbp.setReportName("/docamm/docamm/compenso.jasper");
	
	Print_spooler_paramBulk param;
	param = new Print_spooler_paramBulk();
	param.setNomeParam("Esercizio");
	param.setValoreParam(compenso.getEsercizio().toString());
	param.setParamType("java.lang.Integer");
	printbp.addToPrintSpoolerParam(param);

	param = new Print_spooler_paramBulk();
	param.setNomeParam("CDS");
	param.setValoreParam(compenso.getCd_cds());
	param.setParamType("java.lang.String");
	printbp.addToPrintSpoolerParam(param);
	
	param = new Print_spooler_paramBulk();
	param.setNomeParam("UO");
	param.setValoreParam(compenso.getCd_unita_organizzativa());
	param.setParamType("java.lang.String");
	printbp.addToPrintSpoolerParam(param);
	
	param = new Print_spooler_paramBulk();
	param.setNomeParam("Pg_compenso");
	param.setValoreParam(compenso.getPg_compenso().toString());
	param.setParamType("java.lang.Integer");
	printbp.addToPrintSpoolerParam(param);
	
}
public void inizializzaCompensoPerConguaglio(ActionContext context, ConguaglioBulk conguaglio) throws BusinessProcessException{

	CompensoBulk compenso = (CompensoBulk)getModel();
	
	if (compenso.getImportoObbligazione().compareTo(new java.math.BigDecimal(0))<=0){
		compenso.setStatoCompensoToObbligazioneSincronizzata();
		compenso.setStato_cofi(compenso.STATO_CONTABILIZZATO);
	}else{
		compenso.setStatoCompensoToSincronizzaObbligazione();
		setTab("tab", "tabCompensoObbligazioni");
	}
}
/**
 * Insert the method's description here.
 * Creation date: (10/07/2003 10.56.37)
 * @return boolean
 */
public boolean isAnnoSolareInScrivania() {
	return annoSolareInScrivania;
}
/**
 * Insert the method's description here.
 * Creation date: (27/05/2002 14.40.04)
 * @return it.cnr.contab.doccont00.core.bulk.Obbligazione_scadenzarioBulk
 */
public boolean isAutoGenerated() {
	return false;
}
public boolean isBottoneAnnullaModificaCORIEnabled() {

	if (this.isViewing() || this.isSearching())
		return false;

	return (getContributiCRUDController().getModelIndex()!=-1||
		!getContributiCRUDController().getSelection().isEmpty());
}
public boolean isBottoneConfermaModificaCORIEnabled() {

	if (this.isViewing() || this.isSearching())
		return false;

	return (getContributiCRUDController().getModelIndex()!=-1||
		!getContributiCRUDController().getSelection().isEmpty());
}
public boolean isBottoneCreaObbligazioneEnabled() {

	if (this.isViewing() || this.isSearching())
		return false;

	CompensoBulk compenso = (CompensoBulk)getModel();
	return (compenso!=null &&
			compenso.isStatoCompensoSincronizzaObbligazione() &&
			!compenso.isROPerChiusura());
}
public boolean isBottoneEliminaObbligazioneEnabled() {

	if (this.isViewing() || this.isSearching())
		return false;

	CompensoBulk compenso = (CompensoBulk)getModel();

	if(compenso!=null && compenso.isROPerChiusura())
		return false;
		
	if(compenso!=null && compenso.isStatoCompensoEseguiCalcolo())
		return false;

	return Optional.ofNullable(compenso)
			   .map(CompensoBulk::getObbligazione_scadenzarioColl)
			   .map(list->!list.isEmpty())
			   .orElse(Boolean.FALSE);
}
public boolean isBottoneEseguiCalcoloEnabled() {

	if (this.isViewing() || this.isSearching())
		return false;

	CompensoBulk compenso = (CompensoBulk)getModel();

	return compenso!=null && compenso.isStatoCompensoEseguiCalcolo() && !compenso.isROPerChiusura();
}
public boolean isBottoneModificaAutomaticaObbligazioneEnabled() {

	if (this.isViewing() || this.isSearching())
		return false;

	CompensoBulk compenso = (CompensoBulk)getModel();

	return (compenso!=null &&
			compenso.getObbligazione_scadenzarioColl()!=null && 
			!compenso.getObbligazione_scadenzarioColl().isEmpty() &&
			compenso.isStatoCompensoSincronizzaObbligazione() &&
			!compenso.isROPerChiusura());
}
public boolean isBottoneModificaManualeObbligazioneEnabled() {

	if (this.isSearching())
		return false;

	CompensoBulk compenso = (CompensoBulk)getModel();
	if(compenso!=null && compenso.isStatoCompensoEseguiCalcolo())
		return false;

	return Optional.ofNullable(compenso)
			   .map(CompensoBulk::getObbligazione_scadenzarioColl)
			   .map(list->!list.isEmpty())
			   .orElse(Boolean.FALSE);
}
public boolean isBottoneVisualizzaDocContPrincipaleEnabled() {

	return ((CompensoBulk)getModel()).getDocContPrincipale()!=null;
}
/**
 * Insert the method's description here.
 * Creation date: (10/07/2003 10.56.37)
 * @return boolean
 */
public boolean isCarryingThrough() {
	return carryingThrough;
}
public boolean isContabilizzaButtonEnabled() 
{
	if (isSearching() || isInputReadonly())
		return false;

	CompensoBulk compenso = (CompensoBulk)getModel();
	boolean flg = false;

	if(compenso!=null &&
		compenso.isStatoCompensoContabilizzaCofi() &&
		compenso.STATO_CONTABILIZZATO.equals(compenso.getStato_cofi()) &&
		compenso.LIBERO_FONDO_ECO.equals(compenso.getStato_pagamento_fondo_eco()))
		flg=true;

	//	Chiusura : se carico un compenso con esercizio precedente a quello solare :
	//	- esercizio scrivania != anno solare e obbligazione riportata --> disabilito
	//	- esercizio scrivania != anno solare e obbligazione non riportata --> abilito
	//	- esercizio scrivania = anno solare e obbligazione riportata --> abilito
	//	- esercizio scrivania = anno solare e obbligazione non riportata --> disabilito
	//if(	flg && compenso.getEsercizio().intValue()!=compenso.getAnnoSolare())
	//{
		//if(compenso.getEsercizioScrivania()==compenso.getAnnoSolare())
			//return compenso.isRiportataInScrivania();
		//else
			//return !compenso.isRiportata();			
	//}	


	// Gennaro Borriello - (04/11/2004 19.42.55)
	// Modif. relativa alla nuova gestione di isRiportata()
	if (flg){
		if (compenso.getEsercizio().intValue() == compenso.getEsercizioScrivania())
			return !compenso.isRiportata();
		else
			return compenso.isRiportataInScrivania();
	}
	
	return flg;
}
public boolean isContabilizzaButtonHidden() {
	
	return isBringBack();
}
public boolean isDeleteButtonEnabled() 
{
	CompensoBulk compenso = (CompensoBulk) getModel();
	
	boolean flg = 	compenso != null &&
					compenso.getDt_cancellazione() == null &&
					!compenso.isStatoCompensoEseguiCalcolo() &&
					!isInputReadonly() &&
					!isSearching() &&
					!isInserting() &&
					!compenso.isDaFatturaPassiva();

	//	Chiusura : se carico un compenso con esercizio precedente a quello solare :
	//	- esercizio scrivania != anno solare e obbligazione riportata --> disabilito
	//	- esercizio scrivania != anno solare e obbligazione non riportata --> abilito
	//	- esercizio scrivania = anno solare e obbligazione riportata --> abilito
	//	- esercizio scrivania = anno solare e obbligazione non riportata --> disabilito					
	//if(flg && compenso.getEsercizio().intValue()!=compenso.getAnnoSolare())
	//{
		//if(compenso.getEsercizioScrivania()==compenso.getAnnoSolare())
			//return compenso.isRiportata();
		//else
			//return !compenso.isRiportata();
	//}


	// Gennaro Borriello - (05/11/2004 12.23.28)
	// Modif. relativa alla nuova gestione di isRiportata()
	if (flg){
		if (compenso.getEsercizio().intValue() == compenso.getEsercizioScrivania())
			return !compenso.isRiportata();
		else
			return compenso.isRiportataInScrivania();
	}
	
	return flg;
}
/**
 * Insert the method's description here.
 * Creation date: (27/05/2002 14.40.05)
 * @return it.cnr.contab.doccont00.core.bulk.Obbligazione_scadenzarioBulk
 */
public boolean isDeleting() {
	return false;
}
public boolean isFreeSearchButtonHidden() {
	
	return super.isFreeSearchButtonHidden() || isSpesaBP();
}
/**
 * Insert the method's description here.
 * Creation date: (16/07/2002 12.39.40)
 * @return it.cnr.contab.doccont00.core.bulk.Obbligazione_scadenzarioBulk
 */
public boolean isManualModify() {
	return true;
}
/**
 * Insert the method's description here.
 * Creation date: (03/09/2002 14.45.43)
 * @return boolean
 */
public boolean isObbligazioneValida(ActionContext context, it.cnr.jada.bulk.OggettoBulk bulk) throws BusinessProcessException {
	try{
		try{
			CompensoComponentSession sess = (CompensoComponentSession)createComponentSession();
			sess.validaObbligazione(context.getUserContext(), (Obbligazione_scadenzarioBulk)bulk, (CompensoBulk)getModel());
		}catch(it.cnr.jada.comp.ComponentException ex){
			return false;
		}
		return true;
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}
//inserire il metodo verificaEsistenzaCompenso in isPrintButton
public void isCompensoValidoXStampa(ActionContext context, CompensoBulk compenso) throws BusinessProcessException, ComponentException, RemoteException{
		CompensoComponentSession sess = (CompensoComponentSession)createComponentSession();
		setNocompenso(!sess.verificaEsistenzaCompenso(context.getUserContext(),compenso));
}
public boolean isCompensoValidoXContabil(ActionContext context, CompensoBulk compenso) throws BusinessProcessException, ComponentException, RemoteException{
	CompensoComponentSession sess = (CompensoComponentSession)createComponentSession();
	return(sess.isCompensoValido(context.getUserContext(),compenso));
}

public boolean isPrintButtonHidden() {
		//	return true;
	return super.isPrintButtonHidden() || isInserting() || isSearching() || isNocompenso();
}

public boolean isRiportaAvantiButtonEnabled() 
{
	CompensoBulk compenso = (CompensoBulk)getModel();
	return	isCarryingThrough() || !compenso.isRiportata();
}
public boolean isRiportaAvantiButtonHidden() 
{
	return	basicRiportaButtonHidden();
}
/**
 * Insert the method's description here.
 * Creation date: (10/07/2003 10.56.37)
 * @return boolean
 */
public boolean isRiportaAvantiIndietro() {
	return riportaAvantiIndietro;
}
public boolean isRiportaIndietroButtonEnabled() 
{
    return 	isEditing() && !isDeleting() && !isDirty() && !isCarryingThrough();
}
public boolean isRiportaIndietroButtonHidden() 
{
	CompensoBulk compenso = (CompensoBulk)getModel();
	
	return basicRiportaButtonHidden() || (compenso != null && !(compenso.isRiportata() || isCarryingThrough()));
}
/**
  * Se l'esercizio della scrivania è precedente a quello solare
  * non posso creare una fattura da compenso
  */
public boolean isRODatiFatturaPerChiusura(CompensoBulk compenso)
{
	if(compenso==null)
		return false;
		
	if(compenso.getEsercizio()==null)
		return false;
		
	if(compenso.getAnnoSolare() > compenso.getEsercizio().intValue())
		return true;
		
	return false;
}
public boolean isROFindDatiLiquidazione() throws BusinessProcessException{

	CompensoBulk compenso = (CompensoBulk)getModel();
	if (compenso!=null && !isViewing()){
		if ((getParent() instanceof GestioneUtenteBP) && (compenso.isAssociatoADocumento()) ){
			return true;
		}
	}

	return false;
}
public boolean isSaveButtonEnabled() 
{
	CompensoBulk compenso = (CompensoBulk) getModel();

	if(compenso==null)
		return super.isSaveButtonEnabled();
		
	return	super.isSaveButtonEnabled() &&
		//!anticipo.isROPerChiusura()
		// GB-LF-MB (08/11/2004 11.35.04)
		// Modif. relativa alla nuova gestione di isRiportata()	
		(!compenso.isROPerChiusura() 
		 ||
		 carryingThrough);

}
/**
 * Insert the method's description here.
 * Creation date: (27/05/2002 14.40.05)
 * @return boolean
 */
public boolean isSpesaBP() {

	return getParent() != null && (getParent() instanceof it.cnr.contab.fondecon00.bp.FondoSpesaBP);
}
/**
 *	Abilito il bottone undo Bring Back sempre
 */

public boolean isUndoBringBackButtonEnabled() {
	
	return super.isUndoBringBackButtonEnabled() || isViewing();
}
/**
 * Carica tutti i mandati e le reversali associati al compenso in esame
 * 
 * @return it.cnr.contab.compensi00.docs.bulk.CompensoBulk
 * @param context il Context che ha generato la richiesta
 * @param compenso it.cnr.contab.compensi00.docs.bulk.CompensoBulk
 * @param aTerzo it.cnr.contab.compensi00.docs.bulk.V_terzo_per_compensoBulk
 */
public void loadDocContAssociati(ActionContext context) throws BusinessProcessException {

	try {

		CompensoBulk compenso = (CompensoBulk)getModel();

		CompensoComponentSession sess = (CompensoComponentSession)createComponentSession();
		java.util.List l = sess.loadDocContAssociati(context.getUserContext(), compenso);
		compenso.setDocContAssociati(l);
		
	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}
/**
  *	Gestisce la selezione di un Tipo Trattamento
  */
public void onTipoTrattamentoChange(ActionContext context) throws BusinessProcessException {

	try {

		CompensoComponentSession comp = (CompensoComponentSession)createComponentSession();
		CompensoBulk compenso = comp.onTipoTrattamentoChange(context.getUserContext(), (CompensoBulk)getModel());
		setModel(context, compenso);
	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}
public void onTipoRapportoInpsChange(ActionContext context) throws BusinessProcessException {

	try {

		CompensoComponentSession comp = (CompensoComponentSession)createComponentSession();
		CompensoBulk compenso = comp.onTipoRapportoInpsChange(context.getUserContext(), (CompensoBulk)getModel());

		setModel(context, compenso);

	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}

public void reset(ActionContext context) throws BusinessProcessException 
{
    super.reset(context);
	setCarryingThrough(false);
}
public void resetForSearch(ActionContext context) throws BusinessProcessException 
{
	setCarryingThrough(false);
	super.resetForSearch(context);
}
/**
 * Insert the method's description here.
 * Creation date: (22/02/2002 18.28.25)
 */
public void resetTabs(ActionContext context) {

	setTab("tab","tabCompenso");
}
/**
 *	Il metodo gestisce il 'riporto avanti' del documento contabile del compenso
 */
public void riportaAvanti(ActionContext context)throws it.cnr.jada.bulk.ValidationException,BusinessProcessException
{
	try 
	{
		RiportoDocAmmComponentSession session = (RiportoDocAmmComponentSession)createComponentSession("CNRDOCAMM00_EJB_RiportoDocAmmComponentSession",	RiportoDocAmmComponentSession.class);
		CompensoBulk compensoRiportato = (CompensoBulk)session.riportaAvanti(context.getUserContext(),
																				(IDocumentoAmministrativoBulk)getModel(),
																				getUserConfirm());
		setModel(context, compensoRiportato);
	} 
	catch(Throwable e) 
	{
		throw handleException(e);
	} 
	finally 
	{
		setUserConfirm(null);
	}
}
/**
 *	Il metodo gestisce il 'riporto indietro' del documento contabile del compenso
 */
public void riportaIndietro(ActionContext context)throws it.cnr.jada.action.BusinessProcessException 
{
	if (isDirty()) 
	{
		setMessage("Il documento è stato modificato! Operazione annullata.");
		return;
	}
	
	rollbackUserTransaction();
	setCarryingThrough(true);

	try 
	{
		RiportoDocAmmComponentSession session = (RiportoDocAmmComponentSession)createComponentSession("CNRDOCAMM00_EJB_RiportoDocAmmComponentSession",	RiportoDocAmmComponentSession.class);
		CompensoBulk compensoRiportato = (CompensoBulk)session.riportaIndietro(	context.getUserContext(),
																				(IDocumentoAmministrativoBulk)getModel());
		basicEdit(context, compensoRiportato, true);
		setDirty(true);
	} 
	catch (Throwable t) 
	{
		setCarryingThrough(false);
		rollbackUserTransaction();
		throw handleException(t);
	}
}
/**
 * Ripristina la selezione del vecchio Tipo Rapporto
 *
**/
public void ripristinaSelezioneTipoRapporto(){
	
	CompensoBulk compenso = (CompensoBulk)getModel();
	Tipo_rapportoBulk tipoRapporto = compenso.getTipoRapporto();

	// ripristino la selezione del Tipo Rapporto
	if (tipoRapporto!=null){
		for (java.util.Iterator i = compenso.getTipiRapporto().iterator();i.hasNext();){
			Tipo_rapportoBulk tipoRapp = (Tipo_rapportoBulk)i.next();
			if (tipoRapp.getCd_tipo_rapporto().equals(tipoRapporto.getCd_tipo_rapporto()))
				compenso.setTipoRapporto(tipoRapp);
		}
	}
}
/**
 * Ripristina la selezione del vecchio Tipo Trattamento
 * 
**/
public void ripristinaSelezioneTipoTrattamento(ActionContext context) throws BusinessProcessException{

	CompensoBulk compenso = (CompensoBulk)getModel();
	Tipo_trattamentoBulk tipoTrattamento = compenso.getTipoTrattamento();

	// ripristino la selezione del Tipo Trattamento
	if (tipoTrattamento!=null){
		for (java.util.Iterator i = compenso.getTipiTrattamento().iterator();i.hasNext();){
			Tipo_trattamentoBulk tratt = (Tipo_trattamentoBulk)i.next();
			if (tratt.getCd_trattamento().equals(tipoTrattamento.getCd_trattamento())){
				compenso.setTipoTrattamento(tratt);
//				onTipoTrattamentoChange(context);
			}
		}
	}
}
/**
 * Annulla le modifiche apportate al compenso e ritorna al savepoint impostato in precedenza
 *
 * Pre-post-conditions:
 *
 * Nome: Rollback to savePoint
 * Pre:  Una richiesta di annullare tutte le modifiche apportate e di ritornare al savepoint e' stata generata 
 * Post: Tutte le modifiche effettuate sul compenso vengono annullate, mentre rimangono valide le
 *       modifiche apportate al doc. amministrativo che ha aperto il compenso
 * @param	context			il Context che ha generato la richiesta
 * @param	savePointName	il nome del savePoint
 *
 */	
public void rollbackToSavePoint(ActionContext context, String savePointName) throws BusinessProcessException{

	try {

		CompensoComponentSession sess = (CompensoComponentSession)createComponentSession();
		sess.rollbackToSavePoint(context.getUserContext(), savePointName);

	} catch (java.rmi.RemoteException e) {
			throw handleException(e);
	} catch (it.cnr.jada.comp.ComponentException e) {
			throw handleException(e);
	}
}
/**
 * Il metodo gestisce il 'riporto avanti' del documento contabile del compenso.
 * Il metodo salva il compenso, invoca la procedura per riportare avanti il documento contabile e committa
 */
 
public void salvaRiportandoAvanti(ActionContext context) throws it.cnr.jada.bulk.ValidationException,BusinessProcessException 
{
	CompensoBulk compensoTemp = (CompensoBulk)getModel();	
	try
	{
		setSavePoint(context, "RIPORTA_AVANTI");
		
		completeSearchTools(context,this);
		validate(context);
		saveChildren(context);

		update(context);
		riportaAvanti(context); 
	}
	catch(Throwable e)
	{
		rollbackToSavePoint(context,"RIPORTA_AVANTI");
		setModel(context, compensoTemp);
		
		throw handleException(e);
	}
		
	if (getMessage() == null)
		setMessage("Salvataggio e riporto all'esercizio successivo eseguito in modo corretto.");

	commitUserTransaction();
	setCarryingThrough(false);

	try 
	{
		basicEdit(context,getModel(),true);
	} 
	catch (BusinessProcessException e) 
	{
		setModel(context,null);
		setDirty(false);
		
		throw e;
	}
}
public void save(it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException,it.cnr.jada.bulk.ValidationException 
{
	super.save(context);
	setCarryingThrough(false);
}
/**
 * Insert the method's description here.
 * Creation date: (10/07/2003 10.56.37)
 * @param newAnnoSolareInScrivania boolean
 */
public void setAnnoSolareInScrivania(boolean newAnnoSolareInScrivania) {
	annoSolareInScrivania = newAnnoSolareInScrivania;
}
/**
 * Insert the method's description here.
 * Creation date: (10/07/2003 10.56.37)
 * @param newCarryingThrough boolean
 */
public void setCarryingThrough(boolean newCarryingThrough) {
	carryingThrough = newCarryingThrough;
}
/**
 * Insert the method's description here.
 * Creation date: (27/05/2002 14.40.05)
 * @return it.cnr.contab.doccont00.core.bulk.Obbligazione_scadenzarioBulk
 */
public void setIsDeleting(boolean newIsDeleting) {}
/**
 * Insert the method's description here.
 * Creation date: (10/07/2003 10.56.37)
 * @param newRiportaAvantiIndietro boolean
 */
public void setRiportaAvantiIndietro(boolean newRiportaAvantiIndietro) {
	riportaAvantiIndietro = newRiportaAvantiIndietro;
}
/**
 * Imposta un savepoint che consente di salvare le modifiche apportate al doc. amministrativo fino a quel momento
 * in modo che se gli aggiornamenti apportati al compenso non venissero confermati (rollback), comunque non verrebbero persi
 * anche quelli del documento amministrativo.
 *
 * Pre-post-conditions:
 *
 * Nome: Imposta savePoint
 * Pre:  Una richiesta di impostare un savepoint e' stata generata 
 * Post: Un savepoint e' stato impostato in modo che le modifiche apportate al doc. amministrativo vengono consolidate
 *
 * @param	context			il Context che ha generato la richiesta
 * @param	savePointName	il nome del savePoint
 *
 */	
public void setSavePoint(ActionContext context, String savePointName) throws BusinessProcessException{

	try {

		CompensoComponentSession sess = (CompensoComponentSession)createComponentSession();
		sess.setSavePoint(context.getUserContext(), savePointName);

	} catch (java.rmi.RemoteException e) {
			throw handleException(e);
	} catch (it.cnr.jada.comp.ComponentException e) {
			throw handleException(e);
	}
}
/**
 * Insert the method's description here.
 * Creation date: (19/07/2002 12.53.56)
 * @param newUserConfirm it.cnr.contab.doccont00.core.bulk.OptionRequestParameter
 */
public void setUserConfirm(it.cnr.contab.doccont00.core.bulk.OptionRequestParameter newUserConfirm) {
	userConfirm = newUserConfirm;
}
public void update(ActionContext context)
	throws it.cnr.jada.action.BusinessProcessException {
		
	try 
	{
		getModel().setToBeUpdated();
		setModel(
				context,
				((CompensoComponentSession)createComponentSession()).modificaConBulk(
																			context.getUserContext(),
																			getModel(),
																			getUserConfirm()));
		setUserConfirm(null);
	} catch(Throwable e) {
		
		throw handleException(e);
	}
}
/**
 * Insert the method's description here.
 * Creation date: (25/02/2002 12.56.44)
 * @return it.cnr.contab.compensi00.docs.bulk.CompensoBulk
 * @param userContext it.cnr.jada.UserContext
 * @param compenso it.cnr.contab.compensi00.docs.bulk.CompensoBulk
 * @param aTerzo it.cnr.contab.compensi00.docs.bulk.V_terzo_per_compensoBulk
 */
private void validaDatiLiquidazione(ActionContext context) throws BusinessProcessException{

	try{
		CompensoBulk compenso = (CompensoBulk)getModel();
		//Controllo su Regione IRAP, Voce IVA e Tipologia Rischio
		if (compenso.isVisualizzaRegioneIrap())
			if(compenso.getCd_regione_irap()==null)
				throw new it.cnr.jada.bulk.ValidationException("Inserire la Regione IRAP");
			else
				completeSearchTool(context, getModel(), getBulkInfo().getFieldProperty("find_regione_irap"));

		if (compenso.isVisualizzaVoceIva())
			if(compenso.getCd_voce_iva()==null)
				throw new it.cnr.jada.bulk.ValidationException("Inserire la Voce IVA");
			else
				completeSearchTool(context, getModel(), getBulkInfo().getFieldProperty("find_voce_iva"));

		if (compenso.isVisualizzaTipologiaRischio())
			if (compenso.getTipologiaRischio()==null)
				throw new it.cnr.jada.bulk.ValidationException("Inserire la Tipologia di Rischio");
			else
				completeSearchTool(context, getModel(), getBulkInfo().getFieldProperty("find_tipologia_rischio"));


	}catch(it.cnr.jada.bulk.ValidationException ex){
		throw handleException(ex);
	}
}
/**
  * Validazione dell'obbligazione in fase di creazione di una nuova obbligazione
  * o modifica di una già creata.
  * Il metodo viene chiamato sul riporta dell'Obbligazione in modo da validare
  * istantaneamente l'oggetto creato.
  * Chi non ne ha bisogno lo lasci vuoto.
  *
**/
public void validaObbligazionePerDocAmm(ActionContext context, it.cnr.jada.bulk.OggettoBulk bulk) throws BusinessProcessException {
}
/**
  * Significato codici di errore:
  *	-> 0 nessun errore
  * -> 1 non è selezionato il TERZO
  * -> 2 il TERZO non è valido
  * -> 3 non è stato selezionato il TIPO RAPPORTO 
  * -> 4 il TIPO RAPPORTO non è valido
  * -> 5 non è stato selezionato il TIPO TRATTAMENTO
  * -> 6 il TIPO TRATTAMENTO non è valido
  *
**/
public int validaTerzo(ActionContext context, boolean aBool) throws BusinessProcessException {

	try{

		CompensoComponentSession sess = (CompensoComponentSession)createComponentSession();
		return sess.validaTerzo(context.getUserContext(), (CompensoBulk)getModel(), aBool);

	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}
public boolean initRibaltato(it.cnr.jada.action.ActionContext context)  throws it.cnr.jada.action.BusinessProcessException
{
	try 
	{
		return (((RicercaDocContComponentSession)createComponentSession("CNRCHIUSURA00_EJB_RicercaDocContComponentSession", RicercaDocContComponentSession.class)).isRibaltato(context.getUserContext()));
	} catch(Exception e) 
	{
		throw handleException(e);
	} 
}
public boolean isRibaltato() {
	return ribaltato;
}
public void setRibaltato(boolean b) {
	ribaltato = b;
}
public boolean isNocompenso() {
	return nocompenso;
}
public void setNocompenso(boolean nocompenso) {
	this.nocompenso = nocompenso;
}
public boolean isTerzoCervellone(UserContext userContext, CompensoBulk compenso) throws BusinessProcessException {

	try{

		CompensoComponentSession sess = (CompensoComponentSession)createComponentSession();
		return sess.isTerzoCervellone(userContext, compenso);

	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}
public boolean isAccontoAddComOkPerContabil(UserContext userContext, CompensoBulk compenso) throws BusinessProcessException {
	try{
		CompensoComponentSession sess = (CompensoComponentSession)createComponentSession();
		return sess.isAccontoAddComOkPerContabil(userContext, compenso);

	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}
public boolean isGestiteDeduzioniIrpef(UserContext userContext) throws BusinessProcessException {
	try{
		CompensoComponentSession sess = (CompensoComponentSession)createComponentSession();
		return sess.isGestiteDeduzioniIrpef(userContext);
	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}
public void completaIncarico(ActionContext context, CompensoBulk compenso, Incarichi_repertorio_annoBulk incarico_anno) throws BusinessProcessException {

	try {
		CompensoComponentSession component = (CompensoComponentSession)createComponentSession();
		CompensoBulk compensoClone = component.completaIncarico(context.getUserContext(), compenso, incarico_anno);
		
		setModel(context, compensoClone);

	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}
public BigDecimal prendiUtilizzato(ActionContext context, CompensoBulk compenso, Incarichi_repertorio_annoBulk incarico_anno) throws BusinessProcessException {

	try {
		CompensoComponentSession component = (CompensoComponentSession)createComponentSession();
		BigDecimal utilizzato = component.prendiUtilizzato(context.getUserContext(), incarico_anno);
		return utilizzato;
		//setModel(context, compensoClone);

	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}
/**
 *	Gestisce l'obbligatorietà dell'inserimento o meno dell'incarico sul compenso
 */
public boolean isIncaricoRequired(ActionContext context) throws BusinessProcessException {
	return isIncaricoRequired(context, (CompensoBulk)getModel());
}
/**
 *	Gestisce l'obbligatorietà dell'inserimento o meno dell'incarico sul compenso
 */
public boolean isIncaricoRequired(ActionContext context, CompensoBulk compenso) throws BusinessProcessException {
	try {
		if (isGestioneIncarichiEnabled==null)
			setGestioneIncarichiEnabled(Utility.createParametriCnrComponentSession().getParametriCnr(context.getUserContext(), CNRUserContext.getEsercizio(context.getUserContext())).getFl_incarico());
	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
	return this.isGestioneIncarichiEnabled() && compenso!=null &&
		   compenso.isIncaricoEnabled();
}
public Boolean isGestioneIncarichiEnabled() {
	return isGestioneIncarichiEnabled;
}
private void setGestioneIncarichiEnabled(Boolean isGestioneIncarichiEnabled) {
	this.isGestioneIncarichiEnabled = isGestioneIncarichiEnabled;
}
public void validaIncaricoAnno(ActionContext context, Incarichi_repertorio_annoBulk incAnno)  throws BusinessProcessException, ValidationException {
	try {
		if (incAnno!=null){
			if (Utility.createIncarichiRepertorioComponentSession().hasVariazioneIntegrazioneIncaricoProvvisoria(context.getUserContext(), incAnno.getIncarichi_repertorio())) {
				throw new it.cnr.jada.bulk.ValidationException("Contratto "+incAnno.getEsercizio()+"-"+incAnno.getPg_repertorio()+
						" non utilizzabile in quanto risulta associata una variazione di " +
						"tipo \n\"Periodo transitorio - Adeguamento alla durata del progetto\" in stato \"Provvisorio\".\n"+
						"Rendere \"Definitiva\" la variazione del contratto e successivamente registrare il compenso.");
			}
		}
	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}
public boolean isSospensioneIrpefOkPerContabil(UserContext userContext, CompensoBulk compenso) throws BusinessProcessException {
	try{
		CompensoComponentSession sess = (CompensoComponentSession)createComponentSession();
		return sess.isSospensioneIrpefOkPerContabil(userContext, compenso);

	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}

public void ricercaDatiTrovato(ActionContext context)  throws Exception {
	FatturaPassivaComponentSession h;
	Compenso_rigaBulk riga = (Compenso_rigaBulk)getCompensoRigheController().getModel();
	try {
		h = (FatturaPassivaComponentSession)createComponentSession("CNRDOCAMM00_EJB_FatturaPassivaComponentSession", FatturaPassivaComponentSession.class) ;
		TrovatoBulk trovato = h.ricercaDatiTrovatoValido(context.getUserContext(), riga.getPg_trovato());
		riga.setTrovato(trovato);
	} catch (java.rmi.RemoteException e) {
		riga.setTrovato(new TrovatoBulk());
		handleException(e);
	} catch (BusinessProcessException e) {
		riga.setTrovato(new TrovatoBulk());
		handleException(e);
	} catch (Exception e) {
		riga.setTrovato(new TrovatoBulk());
		throw e;
	}
}
/*
public Boolean isGestionePrestazioneCompensoEnabled() {
	return isGestionePrestazioneCompensoEnabled;
}
private void setGestionePrestazioneCompensoEnabled(Boolean isGestionePrestazioneCompensoEnabled) {
	this.isGestionePrestazioneCompensoEnabled = isGestionePrestazioneCompensoEnabled;
}
*/
public void valorizzaInfoDocEle(ActionContext context, CompensoBulk compenso) throws BusinessProcessException {

	try {

		CompensoComponentSession comp = (CompensoComponentSession)createComponentSession();
		compenso = comp.valorizzaInfoDocEle(context.getUserContext(), compenso);

		setModel(context, compenso);

	}catch(it.cnr.jada.comp.ComponentException ex){
		throw handleException(ex);
	}catch(java.rmi.RemoteException ex){
		throw handleException(ex);
	}
}
public SimpleDetailCRUDController getCompensoRigheController() {
	return compensoRigheController;
}
private void validaScadenze(CompensoBulk compenso, Obbligazione_scadenzarioBulk newScad, boolean isModifica) throws ComponentException {
    if (!isModifica && Stream.of(this.getCompensoRigheController().getDetails().stream().toArray())
            .filter(Compenso_rigaBulk.class::isInstance)
            .map(Compenso_rigaBulk.class::cast)
            .map(Compenso_rigaBulk::getObbligazioneScadenzario)
            .anyMatch(s -> s.equalsByPrimaryKey(newScad))) {
		throw new it.cnr.jada.comp.ApplicationException("Scadenza Impegno già associata al compenso. Nuova associazione non possibile.");
    }

	Vector scadCanc = compenso.getDocumentiContabiliCancellati();
	if (scadCanc != null) {
		Iterator it = scadCanc.iterator();

		while (it.hasNext()) {
			Obbligazione_scadenzarioBulk scad = (Obbligazione_scadenzarioBulk) it.next();
			if (scad.getObbligazione() instanceof ObbligazioneResBulk) {
				if (scad.getObbligazione().equalsByPrimaryKey(newScad.getObbligazione())
						&& ((ObbligazioneResBulk) scad.getObbligazione()).getObbligazione_modifica() != null
						&& ((ObbligazioneResBulk) scad.getObbligazione()).getObbligazione_modifica().getPg_modifica() != null) {
					throw new it.cnr.jada.comp.ApplicationException("Impossibile collegare una scadenza dell'impegno residuo "
											+ scad.getPg_obbligazione()
											+ " poiché è stata effettuata una modifica in questo documento amministrativo!");
				}
			}
		}
	}
}
}
