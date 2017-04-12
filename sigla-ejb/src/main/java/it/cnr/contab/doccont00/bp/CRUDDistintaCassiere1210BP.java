package it.cnr.contab.doccont00.bp;

import it.cnr.contab.cmis.MimeTypes;
import it.cnr.contab.cmis.service.CMISPath;
import it.cnr.contab.config00.ejb.Configurazione_cnrComponentSession;
import it.cnr.contab.config00.tabnum.ejb.Numerazione_baseComponentSession;
import it.cnr.contab.docamm00.docs.bulk.Lettera_pagam_esteroBulk;
import it.cnr.contab.doccont00.core.bulk.MandatoBulk;
import it.cnr.contab.doccont00.intcass.bulk.Apparence;
import it.cnr.contab.doccont00.intcass.bulk.DistintaCassiere1210Bulk;
import it.cnr.contab.doccont00.intcass.bulk.PdfSignApparence;
import it.cnr.contab.doccont00.service.DocumentiContabiliService;
import it.cnr.contab.firma.bulk.FirmaOTPBulk;
import it.cnr.contab.reports.bp.OfflineReportPrintBP;
import it.cnr.contab.reports.bulk.Print_spoolerBulk;
import it.cnr.contab.reports.bulk.Print_spooler_paramBulk;
import it.cnr.contab.reports.bulk.Report;
import it.cnr.contab.reports.service.PrintService;
import it.cnr.contab.service.SpringUtil;
import it.cnr.contab.utente00.ejb.UtenteComponentSession;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.utenze00.bulk.AbilitatoFirma;
import it.cnr.contab.utenze00.bulk.CNRUserInfo;
import it.cnr.contab.utenze00.bulk.UtenteBulk;
import it.cnr.contab.utenze00.bulk.UtenteFirmaDettaglioBulk;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcess;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.action.Config;
import it.cnr.jada.action.HttpActionContext;
import it.cnr.jada.bulk.BusyResourceException;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.bulk.ValidationException;
import it.cnr.jada.comp.ApplicationException;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.util.Log;
import it.cnr.jada.util.action.AbstractPrintBP;
import it.cnr.jada.util.action.FormController;
import it.cnr.jada.util.action.RemoteDetailCRUDController;
import it.cnr.jada.util.action.Selection;
import it.cnr.jada.util.action.SimpleCRUDBP;
import it.cnr.jada.util.ejb.EJBCommonServices;
import it.cnr.jada.util.jsp.Button;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.bindings.spi.http.Response;
import org.apache.chemistry.opencmis.commons.impl.UrlBuilder;
import org.apache.http.HttpStatus;
import org.apache.http.client.utils.URIBuilder;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.google.gson.GsonBuilder;

public class CRUDDistintaCassiere1210BP extends SimpleCRUDBP {
	private static final long serialVersionUID = 1L;
	protected DocumentiContabiliService documentiContabiliService;
	protected String controlloCodiceFiscale;
	private UtenteFirmaDettaglioBulk firmatario;
	private static final Log log = Log.getInstance(CRUDDistintaCassiere1210BP.class);
	
	private final LetteraRemoteDetailCRUDController distintaCassiere1210LettereDaCollegare = new LetteraRemoteDetailCRUDController("DistintaCassiere1210LettereDaCollegare", Lettera_pagam_esteroBulk.class,
			"distintaCassiere1210LettereDaCollegare","CNRDOCCONT00_EJB_DistintaCassiereComponentSession",this);
	private final LetteraRemoteDetailCRUDController distintaCassiere1210LettereCollegate = new LetteraRemoteDetailCRUDController("DistintaCassiere1210LettereCollegate", Lettera_pagam_esteroBulk.class,
			"distintaCassiere1210LettereCollegate","CNRDOCCONT00_EJB_DistintaCassiereComponentSession",this);
	
	class LetteraRemoteDetailCRUDController extends RemoteDetailCRUDController {
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("rawtypes")
		public LetteraRemoteDetailCRUDController(String s, Class class1,
				String s1, String s2, FormController formcontroller) {
			super(s, class1, s1, s2, formcontroller);
			setPageSize(1000);
		}
		public Lettera_pagam_esteroBulk getLettera(int index) {
			return (Lettera_pagam_esteroBulk) getDetailsPage().get(index);
		}		
		@Override
		public void basicReset(ActionContext actioncontext) {
			if (!isSearching())
				super.basicReset(actioncontext);
		}
	}
	public CRUDDistintaCassiere1210BP() {
		super("Tn");
	}
	public CRUDDistintaCassiere1210BP(String function) {
		super(function+"Tn");
	}	
	
	public RemoteDetailCRUDController getDistintaCassiere1210LettereDaCollegare() {
		return distintaCassiere1210LettereDaCollegare;
	}
	public RemoteDetailCRUDController getDistintaCassiere1210LettereCollegate() {
		return distintaCassiere1210LettereCollegate;
	}
	@Override
	public boolean isDeleteButtonEnabled() {
		return super.isDeleteButtonEnabled() && getModel() != null && ((DistintaCassiere1210Bulk)getModel()).getDtInvio() == null;
	}
	public boolean isRimuoviButtonEnabled() {
		return isEditable() && isEditing();
	}
	public boolean isAssociaButtonEnabled() {
		return getModel() != null && ((DistintaCassiere1210Bulk)getModel()).getDtInvio() == null;
	}
	@Override
	public boolean isPrintButtonHidden() {
		if (isSearching())
			return true;
		if (isDirty())
			return true;
		if (getModel() != null && ((DistintaCassiere1210Bulk)getModel()).getDtInvio() != null)
			return true;		
		return super.isPrintButtonHidden();
	}
	public boolean isInviaButtonHidden() {
		if (isSearching() || isViewing() || isDirty())
			return true;		
		if (firmatario == null)
			return true;
		if (!(getModel() != null && ((DistintaCassiere1210Bulk)getModel()).getDtInvio() == null))
			return true;
		return false;
	}
	
	/* inizializza il BP delle stampe impostando il nome del report da stampare e i suoi parametri */
	protected void initializePrintBP(AbstractPrintBP bp) {
		OfflineReportPrintBP printbp = (OfflineReportPrintBP) bp;
		DistintaCassiere1210Bulk distinta = (DistintaCassiere1210Bulk)getModel();

		printbp.setReportName("/doccont/doccont/distinta_cassiere_1210.jasper");
		Print_spooler_paramBulk param = new Print_spooler_paramBulk();
		param.setNomeParam("esercizio");
		param.setValoreParam(distinta.getEsercizio().toString());
		param.setParamType("java.lang.String");
		printbp.addToPrintSpoolerParam(param);
		param = new Print_spooler_paramBulk();
		param.setNomeParam("pg_distinta");
		param.setValoreParam(distinta.getPgDistinta().toString());
		param.setParamType("java.lang.String");
		printbp.addToPrintSpoolerParam(param);

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		param = new Print_spooler_paramBulk();
		param.setNomeParam("DT_EMISSIONE");
		param.setValoreParam(format.format(distinta.getDtEmissione()));
		param.setParamType("java.lang.String");
		printbp.addToPrintSpoolerParam(param);

		param = new Print_spooler_paramBulk();
		param.setNomeParam("DT_INVIO");
		param.setValoreParam(distinta.getDtInvio() != null ? format.format(distinta.getDtInvio()) : "");
		param.setParamType("java.lang.String");
		printbp.addToPrintSpoolerParam(param);
	
	}

	@Override
	protected Button[] createToolbar() {
		Button[] baseToolbar = super.createToolbar();
		Button[] toolbar = new Button[baseToolbar.length + 1];
		int i = 0;
		for (Button button : baseToolbar) {
			toolbar[i++] = button;
		}		
		toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config
				.getHandler().getProperties(getClass()), "Toolbar.invia");
		toolbar[i-1].setSeparator(true);				
		return toolbar;
	}
	
	@Override
	public void save(ActionContext actioncontext) throws ValidationException,
			BusinessProcessException {
		if(getMessage() == null)
            setMessage("Salvataggio eseguito in modo corretto.");
		try {
			setModel(actioncontext, createComponentSession().inizializzaBulkPerModifica(actioncontext.getUserContext(), getModel()));
		} catch (ComponentException e) {
			throw handleException(e);
		} catch (RemoteException e) {
			throw handleException(e);
		}
        setDirty(false);
        commitUserTransaction();
	}
	
	@Override
	public void basicEdit(ActionContext actioncontext,
			OggettoBulk oggettobulk, boolean flag)
			throws BusinessProcessException {
		super.basicEdit(actioncontext, oggettobulk, flag);
		if (((DistintaCassiere1210Bulk)oggettobulk).getDtInvio() != null)
			setStatus(VIEW);
		
	}

	@Override
	protected void init(Config config, ActionContext actioncontext)
			throws BusinessProcessException {
		super.init(config, actioncontext);
		try {
			documentiContabiliService = SpringUtil.getBean("documentiContabiliService", DocumentiContabiliService.class);
			firmatario = ((UtenteComponentSession)createComponentSession("CNRUTENZE00_EJB_UtenteComponentSession",UtenteComponentSession.class)).
					isUtenteAbilitatoFirma(actioncontext.getUserContext(), AbilitatoFirma.DIST);
			Configurazione_cnrComponentSession sess = (Configurazione_cnrComponentSession)it.cnr.jada.util.ejb.EJBCommonServices.createEJB("CNRCONFIG00_EJB_Configurazione_cnrComponentSession");
			controlloCodiceFiscale = sess.getVal01(actioncontext.getUserContext(), "CONTROLLO_CF_FIRMA_DOCCONT");
			if (isEditing())
				setDirty(true);
		} catch (ComponentException e) {
			throw handleException(e);
		} catch (RemoteException e) {
			throw handleException(e);
		}
	}
	
	public void scaricaDocumento(ActionContext actioncontext) throws Exception {
		Integer esercizio = Integer.valueOf(((HttpActionContext)actioncontext).getParameter("esercizio"));
		String cds = ((HttpActionContext)actioncontext).getParameter("cds");
		Long numero_documento = Long.valueOf(((HttpActionContext)actioncontext).getParameter("numero_documento"));
		String tipo = ((HttpActionContext)actioncontext).getParameter("tipo");
		InputStream is = documentiContabiliService.getStreamDocumento(esercizio, cds, numero_documento, tipo);
		if (is == null) {
			log.error("CMIS Object not found: " + esercizio + cds + numero_documento + tipo);
			is = this.getClass().getResourceAsStream("/cmis/404.pdf");
		}		
		((HttpActionContext)actioncontext).getResponse().setContentType("application/pdf");
		OutputStream os = ((HttpActionContext)actioncontext).getResponse().getOutputStream();
		((HttpActionContext)actioncontext).getResponse().setDateHeader("Expires", 0);
		byte[] buffer = new byte[((HttpActionContext)actioncontext).getResponse().getBufferSize()];
		int buflength;
		while ((buflength = is.read(buffer)) > 0) {
			os.write(buffer,0,buflength);
		}
		is.close();
		os.flush();
	}	
	
	public boolean isDistintaInviata() {
		DistintaCassiere1210Bulk distintaCassiere1210Bulk = (DistintaCassiere1210Bulk)getModel();		
		return distintaCassiere1210Bulk != null && distintaCassiere1210Bulk.getDtInvio() != null;
	}
	
	public void scaricaDistinta(ActionContext actioncontext) throws Exception {
		DistintaCassiere1210Bulk distintaCassiere1210Bulk = (DistintaCassiere1210Bulk)getModel();		
		InputStream is = documentiContabiliService.getStreamDocumento(distintaCassiere1210Bulk.getEsercizio(), 
				null, distintaCassiere1210Bulk.getPgDistinta(), distintaCassiere1210Bulk.getTipo());
		if (is != null){
			((HttpActionContext)actioncontext).getResponse().setContentType("application/pdf");
			OutputStream os = ((HttpActionContext)actioncontext).getResponse().getOutputStream();
			((HttpActionContext)actioncontext).getResponse().setDateHeader("Expires", 0);
			byte[] buffer = new byte[((HttpActionContext)actioncontext).getResponse().getBufferSize()];
			int buflength;
			while ((buflength = is.read(buffer)) > 0) {
				os.write(buffer,0,buflength);
			}
			is.close();
			os.flush();
		}
	}	

	@Override
	public OggettoBulk initializeModelForInsert(ActionContext actioncontext,
			OggettoBulk oggettobulk) throws BusinessProcessException {
		oggettobulk = super.initializeModelForInsert(actioncontext, oggettobulk);
		DistintaCassiere1210Bulk distintaCassiere1210Bulk = (DistintaCassiere1210Bulk)oggettobulk;
		distintaCassiere1210Bulk.setEsercizio(CNRUserContext.getEsercizio(actioncontext.getUserContext()));
		distintaCassiere1210Bulk.setDtEmissione(EJBCommonServices.getServerTimestamp());
		Numerazione_baseComponentSession numerazione = 
				(Numerazione_baseComponentSession)createComponentSession("CNRCONFIG00_TABNUM_EJB_TREQUIRED_Numerazione_baseComponentSession");		
		try {			
			distintaCassiere1210Bulk.setPgDistinta(
					numerazione.creaNuovoProgressivo(
							actioncontext.getUserContext(),
							CNRUserContext.getEsercizio(actioncontext.getUserContext()), 
							"DISTINTA_CASSIERE_1210", 
							"PG_DISTINTA", 
							actioncontext.getUserContext().getUser()
					)
				);
			setModel(actioncontext, oggettobulk);
			create(actioncontext);
			setStatus(EDIT);
			setDirty(true);
		} catch (ComponentException e) {
			throw handleException(e);
		} catch (RemoteException e) {
			if (e.getCause() instanceof BusyResourceException)
				throw new BusinessProcessException(new ApplicationException(e.getCause().getMessage()));
			throw handleException(e);
		} catch (BusyResourceException e) {			
			throw handleException(e);
		}
		return oggettobulk;
	}
	@SuppressWarnings("unchecked")
	public void rimuoviDocumento(ActionContext context) throws BusinessProcessException {
		try {
			Selection selection = distintaCassiere1210LettereCollegate.getSelection(context);
			if (selection.isEmpty())
				throw new ValidationException("Selezionare almeno un elemento!");
			for (Iterator<Integer> iterator = selection.iterator(); iterator.hasNext();) {
				Lettera_pagam_esteroBulk lettera_pagam_esteroBulk = (Lettera_pagam_esteroBulk) distintaCassiere1210LettereCollegate.getLettera(iterator.next());
				lettera_pagam_esteroBulk.setDistintaCassiere(null);
				lettera_pagam_esteroBulk.setStato_trasmissione(MandatoBulk.STATO_TRASMISSIONE_PRIMA_FIRMA);
				lettera_pagam_esteroBulk.setToBeUpdated();
				createComponentSession().modificaConBulk(context.getUserContext(), lettera_pagam_esteroBulk);				
			}
			distintaCassiere1210LettereDaCollegare.reset(context);
			distintaCassiere1210LettereCollegate.reset(context);			
		} catch (ValidationException e) {
			throw handleException(e);
		} catch (ComponentException e) {
			throw handleException(e);
		} catch (RemoteException e) {
			throw handleException(e);
		}		
	}
	@SuppressWarnings("unchecked")
	public void associaDocumento(ActionContext context) throws BusinessProcessException {
		try {
			Selection selection = distintaCassiere1210LettereDaCollegare.getSelection(context);
			if (selection.isEmpty())
				throw new ValidationException("Selezionare almeno un elemento!");
			for (Iterator<Integer> iterator = selection.iterator(); iterator.hasNext();) {
				Lettera_pagam_esteroBulk lettera_pagam_esteroBulk = (Lettera_pagam_esteroBulk) distintaCassiere1210LettereDaCollegare.getLettera(iterator.next());
				lettera_pagam_esteroBulk.setDistintaCassiere((DistintaCassiere1210Bulk) getModel());
				lettera_pagam_esteroBulk.setStato_trasmissione(MandatoBulk.STATO_TRASMISSIONE_INSERITO);
				lettera_pagam_esteroBulk.setToBeUpdated();
				createComponentSession().modificaConBulk(context.getUserContext(), lettera_pagam_esteroBulk);				
			}
			distintaCassiere1210LettereDaCollegare.reset(context);
			distintaCassiere1210LettereCollegate.reset(context);			
		} catch (ValidationException e) {
			throw handleException(e);
		} catch (ComponentException e) {
			throw handleException(e);
		} catch (RemoteException e) {
			throw handleException(e);
		}		
	}
	public void signDocuments(ActionContext context, FirmaOTPBulk firmaOTPBulk) throws Exception {
		List<String> nodes = new ArrayList<String>();
		for (int i = 0; i < distintaCassiere1210LettereCollegate.countDetails(); i++) {
			Lettera_pagam_esteroBulk lettera_pagam_esteroBulk = distintaCassiere1210LettereCollegate.getLettera(i);
			if (lettera_pagam_esteroBulk.getFl_seconda_firma_apposta() == null || !lettera_pagam_esteroBulk.getFl_seconda_firma_apposta())
				nodes.addAll(documentiContabiliService.getNodeRefDocumento(lettera_pagam_esteroBulk, true));
		}
		Apparence apparence = new Apparence(
				null, 
				"Rome", "Firma documento contabile",
				"per invio all'Istituto cassiere\nFirmato da\n", 
				300, 40, 1, 550, 80);		
		signDocuments(context, firmaOTPBulk, nodes, apparence);
	}
	
	public void signDocuments(ActionContext context, FirmaOTPBulk firmaOTPBulk, List<String> nodes, Apparence apparence) throws Exception {
		String webScriptURL = documentiContabiliService.getRepositoyURL().concat("service/sigla/firma/doccont");
		Map<String, String> subjectDN = documentiContabiliService.getCertSubjectDN(firmaOTPBulk.getUserName(), firmaOTPBulk.getPassword());
		if (subjectDN == null)
			throw new ApplicationException("Errore nella lettura dei certificati!\nVerificare Nome Utente e Password!");
		String codiceFiscale = subjectDN.get("SERIALNUMBER").substring(3);
		UtenteBulk utente = ((CNRUserInfo)context.getUserInfo()).getUtente();
		if (controlloCodiceFiscale != null && controlloCodiceFiscale.equalsIgnoreCase("Y") && utente.getCodiceFiscaleLDAP() != null && !utente.getCodiceFiscaleLDAP().equalsIgnoreCase(codiceFiscale)) {
			throw new ApplicationException("Il codice fiscale \"" + codiceFiscale + "\" presente sul certicato di Firma, " +
					"� diverso da quello dell'utente collegato \"" + utente.getCodiceFiscaleLDAP() +"\"!");
		}
		PdfSignApparence pdfSignApparence = new PdfSignApparence();
		pdfSignApparence.setNodes(nodes);
		pdfSignApparence.setUsername(firmaOTPBulk.getUserName());
		pdfSignApparence.setPassword(firmaOTPBulk.getPassword());
		pdfSignApparence.setOtp(firmaOTPBulk.getOtp());
		
		apparence.setTesto(apparence.getTesto() + subjectDN.get("GIVENNAME") + " " + subjectDN.get("SURNAME"));
		pdfSignApparence.setApparence(apparence);
		String json = new GsonBuilder().create().toJson(pdfSignApparence);
		try {		
			UrlBuilder url = new UrlBuilder(new URIBuilder(webScriptURL).build().toString());
			Response response = documentiContabiliService.invokePOST(url, MimeTypes.JSON, json.getBytes("UTF-8"));
			int status = response.getResponseCode();
			if (status == HttpStatus.SC_NOT_FOUND
					|| status == HttpStatus.SC_INTERNAL_SERVER_ERROR
					|| status == HttpStatus.SC_UNAUTHORIZED
					|| status == HttpStatus.SC_BAD_REQUEST) {
				JSONTokener tokenizer = new JSONTokener(new StringReader(response.getErrorContent()));
			    JSONObject jsonObject = new JSONObject(tokenizer);
			    String jsonMessage = jsonObject.getString("message");
				throw new ApplicationException(FirmaOTPBulk.errorMessage(jsonMessage));
			}
		} catch (IOException e) {
			throw new BusinessProcessException(e);
		} catch (Exception e) {
			throw new BusinessProcessException(e);
		}
		for (int i = 0; i < distintaCassiere1210LettereCollegate.countDetails(); i++) {
			Lettera_pagam_esteroBulk lettera_pagam_esteroBulk = distintaCassiere1210LettereCollegate.getLettera(i);
			lettera_pagam_esteroBulk.setFl_seconda_firma_apposta(true);
			lettera_pagam_esteroBulk.setToBeUpdated();
			lettera_pagam_esteroBulk = (Lettera_pagam_esteroBulk) createComponentSession().modificaConBulk(context.getUserContext(), lettera_pagam_esteroBulk);
		}
	}
	
	public void invia(ActionContext context, FirmaOTPBulk firmaOTPBulk) throws Exception {
		DistintaCassiere1210Bulk distintaCassiere1210Bulk = (DistintaCassiere1210Bulk)getModel();
		Timestamp currentTimestamp = EJBCommonServices.getServerTimestamp();
		List<String> nodes = new ArrayList<String>();
		for (int i = 0; i < distintaCassiere1210LettereCollegate.countDetails(); i++) {
			Lettera_pagam_esteroBulk lettera_pagam_esteroBulk = distintaCassiere1210LettereCollegate.getLettera(i);
			nodes.addAll(documentiContabiliService.getNodeRefDocumento(lettera_pagam_esteroBulk, true));			
		}
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Print_spoolerBulk print = new Print_spoolerBulk();
		print.setPgStampa(UUID.randomUUID().getLeastSignificantBits());
		print.setFlEmail(false);
		print.setReport("/doccont/doccont/distinta_cassiere_1210.jasper");
		print.setNomeFile("Distinta 1210 n. "
				+ distintaCassiere1210Bulk.getPgDistinta() + ".pdf");
		print.setUtcr(context.getUserContext().getUser());
		print.addParam("esercizio", String.valueOf(distintaCassiere1210Bulk.getEsercizio()), String.class);
		print.addParam("pg_distinta", String.valueOf(distintaCassiere1210Bulk.getPgDistinta()), String.class);
		print.addParam("DT_EMISSIONE", format.format(distintaCassiere1210Bulk.getDtEmissione()), String.class);
		print.addParam("DT_INVIO", format.format(currentTimestamp), String.class);
		
		Report report = SpringUtil.getBean("printService",
				PrintService.class).executeReport(context.getUserContext(),
				print);
		CMISPath cmisPath = distintaCassiere1210Bulk.getCMISPath(documentiContabiliService);
		Document node = documentiContabiliService.restoreSimpleDocument(distintaCassiere1210Bulk, report.getInputStream(), report.getContentType(), report.getName(), cmisPath);
		String nodo = (String) node.getPropertyValue("alfcmis:nodeRef");
		nodes.add(nodo);
		Apparence apparence = new Apparence(
				null, 
				"Rome", "Firma documento contabile",
				"per invio all'Istituto cassiere\nFirmato da\n", 
				300, 40, 1, 550, 80);		
		signDocuments(context, firmaOTPBulk,nodes, apparence);		
		for (int i = 0; i < distintaCassiere1210LettereCollegate.countDetails(); i++) {
			Lettera_pagam_esteroBulk lettera_pagam_esteroBulk = distintaCassiere1210LettereCollegate.getLettera(i);
			lettera_pagam_esteroBulk = ((Lettera_pagam_esteroBulk) createComponentSession().findByPrimaryKey(context.getUserContext(), lettera_pagam_esteroBulk));
			lettera_pagam_esteroBulk.setStato_trasmissione(MandatoBulk.STATO_TRASMISSIONE_TRASMESSO);
			lettera_pagam_esteroBulk.setToBeUpdated();
			lettera_pagam_esteroBulk = (Lettera_pagam_esteroBulk) createComponentSession().modificaConBulk(context.getUserContext(), lettera_pagam_esteroBulk);
		}
		distintaCassiere1210Bulk.setDtInvio(currentTimestamp);
		distintaCassiere1210Bulk.setToBeUpdated();
		distintaCassiere1210Bulk = (DistintaCassiere1210Bulk) createComponentSession().modificaConBulk(context.getUserContext(), distintaCassiere1210Bulk);
		//nodes.add(nodo);
		if(distintaCassiere1210Bulk.getEsercizio()!=null && distintaCassiere1210Bulk.getPgDistinta()!=null)
			documentiContabiliService.inviaDistintaPEC1210(nodes,true,distintaCassiere1210Bulk.getEsercizio()+"/"+distintaCassiere1210Bulk.getPgDistinta());
		else
			documentiContabiliService.inviaDistintaPEC1210(nodes);
		commitUserTransaction();
		setMessage("Invio effettuato correttamente.");
		setStatus(VIEW);
	}
	
	private Integer getLastPagePDF(InputStream stream) throws IOException {
		PDDocument document = PDDocument.load(stream);
		int lastPage = document.getNumberOfPages();
		document.close();
		return lastPage;
	}
	
	@Override
	protected void closed(ActionContext context)
			throws BusinessProcessException {
		try {
			distintaCassiere1210LettereCollegate.closed(context);
			distintaCassiere1210LettereDaCollegare.closed(context);
		} catch (RemoteException e) {
			throw handleException(e);
		}
		super.closed(context);
	}
	
}