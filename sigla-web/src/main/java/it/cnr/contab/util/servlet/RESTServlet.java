package it.cnr.contab.util.servlet;


import it.cnr.contab.config00.ejb.Unita_organizzativaComponentSession;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.utente00.nav.ejb.GestioneLoginComponentSession;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.utenze00.bp.RESTUserContext;
import it.cnr.contab.utenze00.bulk.AssBpAccessoBulk;
import it.cnr.contab.utenze00.bulk.CNRUserInfo;
import it.cnr.contab.utenze00.bulk.UtenteBulk;
import it.cnr.contab.utenze00.ejb.AssBpAccessoComponentSession;
import it.cnr.contab.util.servlet.JSONRequest.Clause;
import it.cnr.contab.util.servlet.JSONRequest.OrderBy;
import it.cnr.jada.action.ActionMapping;
import it.cnr.jada.action.ActionMappings;
import it.cnr.jada.action.ActionMappingsConfigurationException;
import it.cnr.jada.action.ActionPerformingError;
import it.cnr.jada.action.ActionUtil;
import it.cnr.jada.action.AdminUserContext;
import it.cnr.jada.action.BusinessProcess;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.action.HttpActionContext;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.bulk.UserInfo;
import it.cnr.jada.comp.ApplicationException;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.persistency.sql.CompoundFindClause;
import it.cnr.jada.util.OrderConstants;
import it.cnr.jada.util.action.ConsultazioniBP;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.ejb.EJBException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

public class RESTServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private List<String> restExtension;
    private File actionDirFile;    
    private ActionMappings mappings;
    private String COMMAND_POST = "doRestResponse", COMMAND_GET = "doRestInfo", ACTION_INFO = "/info";
	private static final Logger logger = LoggerFactory.getLogger(RESTServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		execute(req, resp, COMMAND_POST);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		execute(req, resp, COMMAND_GET);
	}

	protected void execute(HttpServletRequest req, HttpServletResponse resp, String command)
			throws ServletException, IOException {
		
		resp.setContentType("application/json");
        String s = req.getServletPath();
        String authorization = req.getHeader("Authorization");
        logger.info("RequestedSessionId: "+req.getRequestedSessionId() + ". RemoteAddr: "+req.getRemoteAddr() + ". RemoteHost: "+req.getRemoteHost()+ ". RemotePort: "+req.getRemotePort());
        logger.info("RequestedSessionId: "+req.getRequestedSessionId() + ". Action: "+s + ". Command: "+command + ". Authorization: "+authorization);
        logger.info("RequestedSessionId: "+req.getRequestedSessionId() + ". ContentType: "+req.getContentType() + ". Encoding: "+req.getCharacterEncoding()+ ". QueryString: "+req.getQueryString());
        logger.info("RequestedSessionId: "+req.getRequestedSessionId() + ". ServerName: "+req.getServerName()+". ServerPort: "+req.getServerPort()+". URI: "+req.getRequestURI());
        String extension = s.substring(s.lastIndexOf("."));
        if(!restExtension.contains(extension))
            throw new ServletException("Le actions devono terminare con \""+ restExtension +"\"");
        
        s = s.substring(0, s.length() - extension.length());
        if (s.equals(ACTION_INFO)){
        	if (command.equals(COMMAND_GET)) {
                searchForInfo(req, resp); 
        	} else {
                throw new ServletException("Non � possibile avere le informazioni sui servizi con il comando POST");
        	}
        } else {
            ActionMapping actionmapping = mappings.findActionMapping(s);
            if(actionmapping == null)
                throw new ServletException("Action not found ["+s+"]");
            UtenteBulk utente = null;
    		try {
    			if (actionmapping.needExistingSession())
    				utente = authenticate(req, resp);
    			if (utente != null || !actionmapping.needExistingSession()) {
    				JSONRequest jsonRequest = null;
    	            HttpActionContext httpactioncontext = new HttpActionContext(this, req, resp);
    	            httpactioncontext.setActionMapping(actionmapping);
    	            if (command.equals(COMMAND_POST)) {
    	            	jsonRequest = new Gson().fromJson(new JsonParser().parse(req.getReader()), JSONRequest.class);
    	            	if (actionmapping.needExistingSession()) {
    			            httpactioncontext.setUserContext(getContextFromRequest(jsonRequest, utente.getCd_utente(), httpactioncontext.getSessionId(), req));
    			            httpactioncontext.setUserInfo(getUserInfo(utente, (CNRUserContext) httpactioncontext.getUserContext()));	            		            		
    	            	} else {
    	            		httpactioncontext.setUserContext(new RESTUserContext());
    			            httpactioncontext.setUserInfo(getUserInfo(utente, (CNRUserContext) httpactioncontext.getUserContext()));
    	            	}
    	            }
    	            try{
    	            	
    	            	BusinessProcess businessProcess;
    	            	if (req.getParameter("bpName") != null)
    	            		businessProcess = mappings.createBusinessProcess(req.getParameter("bpName"), httpactioncontext);
    	            	else
    	            		businessProcess = mappings.createBusinessProcess(actionmapping, httpactioncontext);

    	                logger.info("RequestedSessionId: "+req.getRequestedSessionId() + ". Business Process: "+businessProcess.getName());
    	            	if (command.equals(COMMAND_POST)) {
    	            		Boolean isEnableBP = false;
    	            		if (actionmapping.needExistingSession())
    	            			isEnableBP = loginComponentSession().isBPEnableForUser(httpactioncontext.getUserContext(), utente, 
    		            			CNRUserContext.getCd_unita_organizzativa(httpactioncontext.getUserContext()), businessProcess.getName());
    		            	if ((actionmapping.needExistingSession() && !isEnableBP) || !(businessProcess instanceof ConsultazioniBP)) {
    		            		resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    		        			resp.getWriter().append("{\"message\" : \"Utente non abilitato ad eseguire la richiesta!\"}");
    		        			return;
    		            	}
    		            	ConsultazioniBP consBP = ((ConsultazioniBP)businessProcess);
    		        		if (jsonRequest != null && jsonRequest.getClauses() != null) {
    		        			CompoundFindClause compoundFindClause = new CompoundFindClause();
    		        			for (Clause clause : jsonRequest.getClauses()) {
    		    	                logger.info("RequestedSessionId: "+req.getRequestedSessionId() + ". Clause. Condition: "+clause.getCondition()+", fieldName: "+clause.getFieldName()+", Operator: "+clause.getOperator()+", fieldValue: "+clause.getFieldValue());
    		        				compoundFindClause.addClause(clause.getCondition(), clause.getFieldName(), clause.getSQLOperator(), clause.getFieldValue());
    		        			}
    		        			consBP.setFindclause(compoundFindClause);
    		        		}
    		            	consBP.setIterator(httpactioncontext, 
    		            			consBP.search(httpactioncontext,
    		            			consBP.getFindclause(),
    		        				(OggettoBulk) consBP.getBulkInfo().getBulkClass().newInstance()));
    		            	parseRequestParameter(req, httpactioncontext, jsonRequest, consBP);		            	
    	            	}
    	            	httpactioncontext.setBusinessProcess(businessProcess);
    	                req.setAttribute(it.cnr.jada.action.BusinessProcess.class.getName(), businessProcess);
    	            	httpactioncontext.perform(null, actionmapping, command);
    	            }catch(ActionPerformingError actionperformingerror)        {
    	            	throw new ComponentException(actionperformingerror.getDetail());
    	            }catch(RuntimeException runtimeexception){
    	            	logger.error("RuntimeException", runtimeexception);
    	            	throw new ComponentException(runtimeexception);
    	            } catch (BusinessProcessException e) {
    	            	logger.error("BusinessProcessException", e);
    	                throw new ComponentException(e);
    	    		} catch (InstantiationException e) {
    	            	logger.error("InstantiationException", e);
    	                throw new ComponentException(e);
    				} catch (IllegalAccessException e) {
    	            	logger.error("IllegalAccessException", e);
    	                throw new ComponentException(e);
    				}
    			} else {
    	            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    	            resp.setHeader("WWW-Authenticate", "Basic realm=\"SIGLA\"");   
    			}
    	        logger.info("RequestedSessionId: "+req.getRequestedSessionId() + ". End");
    		} catch (ComponentException e) {
    			logger.error("ComponentException", e);
    			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);			
    			Gson gson = new Gson();			
    		    Map<String, String> exc_map = new HashMap<String, String>();
    		    exc_map.put("message", e.toString());
    		    exc_map.put("stacktrace", getStackTrace(e));
    			resp.getWriter().append(gson.toJson(exc_map));
    		}
        }
	}

	private void searchForInfo(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
		Hashtable<String, ActionMapping> actionMappings = mappings.getActions();
		Hashtable<String, AssBpAccessoBulk> actionsConAccesso = new Hashtable<String, AssBpAccessoBulk>(); 
		Hashtable<String, ActionMapping> actionsSenzaAccesso = new Hashtable<String, ActionMapping>(); 
		searchActionsForInfo(req, resp, actionMappings, actionsConAccesso, actionsSenzaAccesso);
		createResponseForInfo(req, resp, actionsConAccesso, actionsSenzaAccesso);
	}

	private void createResponseForInfo(HttpServletRequest req,
			HttpServletResponse resp,
			Hashtable<String, AssBpAccessoBulk> actionsConAccesso,
			Hashtable<String, ActionMapping> actionsSenzaAccesso)
			throws ServletException {
		HttpActionContext httpactioncontextResp = new HttpActionContext(this, req, resp);
		HttpServletResponse response = httpactioncontextResp.getResponse();
		response.setContentType("application/json");
		try {
			JsonGenerator jGenerator = writeHeaderJsonForInfo(actionsConAccesso, actionsSenzaAccesso, response);
			writeElementsJsonForInfo(actionsConAccesso, actionsSenzaAccesso, jGenerator);
			jGenerator.writeEndArray();
			jGenerator.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void writeElementsJsonForInfo(
			Hashtable<String, AssBpAccessoBulk> actionsConAccesso,
			Hashtable<String, ActionMapping> actionsSenzaAccesso,
			JsonGenerator jGenerator) throws IOException,
			JsonGenerationException, JsonMappingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

		for (String key : actionsConAccesso.keySet()) {
			AssBpAccessoBulk accesso = actionsConAccesso.get(key);
			if (accesso.getAccesso() != null && accesso.getAccesso().getTi_accesso() != null){
				if (accesso.getAccesso().getTi_accesso().equals("D")){
					writeJsonForInfoConAccesso(jGenerator, mapper, key, accesso);
				} 
			} 
		} 
							
		for (String key : actionsSenzaAccesso.keySet()) {
			ActionMapping actionMapping = actionsSenzaAccesso.get(key);
			if (!actionMapping.needExistingSession()){
				writeJsonForInfoSenzaAccesso(jGenerator, mapper, key, actionMapping);
			}
		}
	}

	private void writeJsonForInfoSenzaAccesso(JsonGenerator jGenerator,
			ObjectMapper mapper, String key, ActionMapping actionMapping)
			throws IOException, JsonGenerationException, JsonMappingException {
		jGenerator.writeStartObject();
		jGenerator.writeFieldName("action");
		jGenerator.writeRawValue(mapper.writeValueAsString(key));
		jGenerator.writeFieldName("authentication");
		jGenerator.writeRawValue(mapper.writeValueAsString(Boolean.toString(actionMapping.needExistingSession())));
		jGenerator.writeEndObject();
	}

	private void writeJsonForInfoConAccesso(JsonGenerator jGenerator,
			ObjectMapper mapper, String key, AssBpAccessoBulk accesso)
			throws IOException, JsonGenerationException, JsonMappingException {
		jGenerator.writeStartObject();
		jGenerator.writeFieldName("action");
		jGenerator.writeRawValue(mapper.writeValueAsString(key));
		jGenerator.writeFieldName("descrizione");
		jGenerator.writeRawValue(mapper.writeValueAsString(accesso.getAccesso().getDs_accesso()));
		jGenerator.writeFieldName("accesso");
		jGenerator.writeRawValue(mapper.writeValueAsString(accesso.getCdAccesso()));
		jGenerator.writeFieldName("authentication");
		jGenerator.writeRawValue(mapper.writeValueAsString(Boolean.toString(Boolean.TRUE)));
		jGenerator.writeEndObject();
	}

	private JsonGenerator writeHeaderJsonForInfo(
			Hashtable<String, AssBpAccessoBulk> actionsConAccesso,
			Hashtable<String, ActionMapping> actionsSenzaAccesso,
			HttpServletResponse response) throws IOException,
			JsonGenerationException {
		JsonFactory jfactory = new JsonFactory();
		JsonGenerator jGenerator = jfactory.createJsonGenerator(response.getWriter());
		jGenerator.writeStartObject();
		jGenerator.writeNumberField("totalNumItems", actionsConAccesso.size() + actionsSenzaAccesso.size());
		jGenerator.writeNumberField("maxItemsPerPage", 0);
		jGenerator.writeNumberField("activePage", 0);
		jGenerator.writeArrayFieldStart("elements");
		return jGenerator;
	}

	private void searchActionsForInfo(HttpServletRequest req,
			HttpServletResponse resp,
			Hashtable<String, ActionMapping> actionMappings,
			Hashtable<String, AssBpAccessoBulk> actionsConAccesso,
			Hashtable<String, ActionMapping> actionsSenzaAccesso)
			throws ServletException {
		for (String key : actionMappings.keySet()) {
			ActionMapping actionMapping = actionMappings.get(key);
		    HttpActionContext httpactioncontext = new HttpActionContext(this, req, resp);
			if (isConsultazione(actionMapping, httpactioncontext, key)){
				if (!actionMapping.needExistingSession()){
					actionsSenzaAccesso.put(key, actionMapping);
				} else {
					searchActionsConAccesso(actionsConAccesso, key, actionMapping, httpactioncontext);
				}
			}
		}
	}

	private void searchActionsConAccesso(
			Hashtable<String, AssBpAccessoBulk> actionsConAccesso, String key,
			ActionMapping actionMapping, HttpActionContext httpactioncontext) {
		try {
			String bpName = mappings.getBusinessProcessName(actionMapping, httpactioncontext);
			if (bpName != null){
				List listaAccessi = assBpAccessoComponentSession().findAccessoByBP(httpactioncontext.getUserContext(), bpName);
				if (listaAccessi != null && !listaAccessi.isEmpty()){
					for (Iterator<Object> i= listaAccessi.iterator(); i.hasNext();) {
						AssBpAccessoBulk accesso= (AssBpAccessoBulk) i.next();
						if (accesso.getAccesso() != null && accesso.getAccesso().getTi_accesso() != null){
							if (accesso.getAccesso().getTi_accesso().equals("D")){
								actionsConAccesso.put(key, accesso);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Boolean isConsultazione(ActionMapping actionMapping,
			HttpActionContext httpactioncontext, String key) throws ServletException {
		String bpName = "";
		try {
			bpName = mappings.getBusinessProcessName(actionMapping, httpactioncontext);
			if (bpName != null){
				return mappings.isSubclassOf(bpName, ConsultazioniBP.class);
			}
			return false;
		} catch (BusinessProcessException e) {
			throw new ServletException("Errore nella creazione del businessProcess per il mapping \""+bpName + " "+e.getMessage());
		}
	}

	private String getStackTrace(final Throwable throwable) {
		final StringWriter sw = new StringWriter();
		final PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}
	
	private void parseRequestParameter(HttpServletRequest req, HttpActionContext actioncontext,
			JSONRequest jsonRequest, ConsultazioniBP consBP) throws RemoteException, BusinessProcessException {
		if (jsonRequest != null) {			
			if (jsonRequest.getMaxItemsPerPage() != null && jsonRequest.getMaxItemsPerPage().compareTo(0) > 0){
                logger.info("RequestedSessionId: "+req.getRequestedSessionId() + ". MaxItemsPerPage: "+jsonRequest.getMaxItemsPerPage());
	    		consBP.setPageSize(jsonRequest.getMaxItemsPerPage());
	    		consBP.refresh(actioncontext);
			}
			if (jsonRequest.getActivePage() != null && jsonRequest.getActivePage().compareTo(0) > 0){
                logger.info("RequestedSessionId: "+req.getRequestedSessionId() + ". ActivePage: "+jsonRequest.getActivePage());
	    		consBP.goToPage(actioncontext, jsonRequest.getActivePage());
			}
			if (jsonRequest.getOrderBy() != null) {
				for (OrderBy orderBy : jsonRequest.getOrderBy()) {
	                int orderType = orderBy.getType() == null || orderBy.getType().equalsIgnoreCase("ASC")?OrderConstants.ORDER_ASC: OrderConstants.ORDER_DESC; 
	                logger.info("RequestedSessionId: "+req.getRequestedSessionId() + ". OrderBy: "+orderBy.name+" "+orderBy.getType());
					consBP.setOrderBy(actioncontext, orderBy.name, orderType);
				}
			}			
		}		
	}

	private UserInfo getUserInfo(UtenteBulk utente, CNRUserContext userContext) throws ComponentException {
		CNRUserInfo cnrUserInfo  = new CNRUserInfo();
		cnrUserInfo.setUtente(utente);
		try {
			cnrUserInfo.setUnita_organizzativa((Unita_organizzativaBulk) uoComponentSession().findUOByCodice(userContext, userContext.getCd_unita_organizzativa()));
			if (cnrUserInfo.getUnita_organizzativa() != null)
				cnrUserInfo.setCdr(loginComponentSession().cdrDaUo(userContext, cnrUserInfo.getUnita_organizzativa()));
		} catch (RemoteException e) {
			throw new ComponentException(e);
		} catch (EJBException e) {
			throw new ComponentException(e);
		}
		return cnrUserInfo;
	}
	private CNRUserContext getContextFromRequest(JSONRequest jsonRequest, String user, String sessionId, HttpServletRequest req) throws IOException, ApplicationException {
		if (jsonRequest != null && jsonRequest.getContext() != null) {
			if (jsonRequest.getContext().getEsercizio() == null)
				throw new ApplicationException("Esercizio non puo essere vuoto");
			if (jsonRequest.getContext().getCd_cds() == null)
				throw new ApplicationException("Il codice del CdS non puo essere vuoto");
			if (jsonRequest.getContext().getCd_unita_organizzativa() == null)
				throw new ApplicationException("Il codice della UO non puo essere vuoto");
			if (jsonRequest.getContext().getCd_cdr() == null)
				throw new ApplicationException("Il codice del CdR non puo essere vuoto");

            logger.info("RequestedSessionId: "+req.getRequestedSessionId() + ". Context: Anno:" + jsonRequest.getContext().getEsercizio() + ", CDS:"+ jsonRequest.getContext().getCd_cds() + ", UO:"+ jsonRequest.getContext().getCd_unita_organizzativa() + ", CDR:"+ jsonRequest.getContext().getCd_cdr());
			return new CNRUserContext(user, sessionId, jsonRequest.getContext().getEsercizio(), 
					jsonRequest.getContext().getCd_unita_organizzativa(), 
					jsonRequest.getContext().getCd_cds(), 
					jsonRequest.getContext().getCd_cdr());			
		} else {
			throw new ApplicationException("E' necessario valorizzare il contesto utente.");
		}
	}
    
	@Override
	public void init() throws ServletException {
		super.init();
		restExtension = new ArrayList<String>();
		String extension = getServletConfig().getInitParameter("extension");
		if (extension == null)
			restExtension.add(".json");
		else {
			StringTokenizer tokens = new StringTokenizer(extension, ",");
			while (tokens.hasMoreTokens()) {
				restExtension.add(tokens.nextToken());
			}			
		}
		actionDirFile = new File(getServletContext().getRealPath("/actions/"));
        try{
            mappings = ActionUtil.reloadActions(actionDirFile);
        }catch(ActionMappingsConfigurationException actionmappingsconfigurationexception){
            throw new ServletException("Action mappings configuration exception", actionmappingsconfigurationexception);
        }		
	}
	public UtenteBulk authenticate(HttpServletRequest req, HttpServletResponse res) throws ComponentException{
        boolean authorized = false;
		UtenteBulk utente = new UtenteBulk();
        String authorization = req.getHeader("Authorization");
        // authenticate as specified by HTTP Basic Authentication
        if (authorization != null && authorization.length() != 0)
        {
            String[] authorizationParts = authorization.split(" ");
            if (!authorizationParts[0].equalsIgnoreCase("basic"))
            {
                throw new ApplicationException("Authorization '" + authorizationParts[0] + "' not supported.");
            }
            String decodedAuthorisation = new String(DatatypeConverter.parseBase64Binary(authorizationParts[1]));
            logger.info("RequestedSessionId: "+req.getRequestedSessionId() + ". Decoded Authorisation: " + decodedAuthorisation);
            String[] parts = decodedAuthorisation.split(":");
            
            if (parts.length == 2)
            {
                // assume username and password passed as the parts
                String username = parts[0];
                String password = parts[1];
                
                logger.info("RequestedSessionId: "+req.getRequestedSessionId() + ". Username: " + username + ". Password: "+password);
                
				utente = new UtenteBulk();
				utente.setCd_utente(username.toUpperCase());
				utente.setLdap_password(password);
				utente.setPasswordInChiaro(password.toUpperCase());
				try {
					utente = loginComponentSession().validaUtente(AdminUserContext.getInstance(), utente);
					if (utente != null)
						authorized = true;
				} catch (RemoteException e) {
					throw new ApplicationException(e.getMessage());
				} catch (EJBException e) {
					throw new ApplicationException(e.getMessage());				
				}
            }
        }
        
        // request credentials if not authorized
        if (!authorized)
        {
           	logger.info("RequestedSessionId: "+req.getRequestedSessionId() + ". Requesting authorization credentials");
            return null;
        }
        return utente;
	}
	public static AssBpAccessoComponentSession assBpAccessoComponentSession() throws javax.ejb.EJBException, java.rmi.RemoteException {
		return (AssBpAccessoComponentSession)it.cnr.jada.util.ejb.EJBCommonServices.createEJB("CNRUTENZE00_EJB_AssBpAccessoComponentSession");
	}
	public static GestioneLoginComponentSession loginComponentSession() throws javax.ejb.EJBException, java.rmi.RemoteException {
		return (GestioneLoginComponentSession)it.cnr.jada.util.ejb.EJBCommonServices.createEJB("CNRUTENZE00_NAV_EJB_GestioneLoginComponentSession");
	}
	public static Unita_organizzativaComponentSession uoComponentSession() throws javax.ejb.EJBException, java.rmi.RemoteException {
		return (Unita_organizzativaComponentSession)it.cnr.jada.util.ejb.EJBCommonServices.createEJB("CNRCONFIG00_EJB_Unita_organizzativaComponentSession");
	}
}