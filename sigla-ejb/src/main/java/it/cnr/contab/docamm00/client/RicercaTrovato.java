package it.cnr.contab.docamm00.client;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.BasicAuthentication;

import it.cnr.contab.docamm00.docs.bulk.TrovatoBulk;
import it.cnr.contab.service.SpringUtil;
import it.cnr.jada.comp.ApplicationException;

public class RicercaTrovato {

	private static String targetEndpoint; //="http://siglaas4.cedrc.cnr.it:8480/";
	private static String siglaRestClientUser;
	private static String siglaRestClientPassword;
	public RicercaTrovato() throws FileNotFoundException, IOException {
		super();
			loadProperties();
	}

	private TrovatoProperties recuperoTrovatoProperties() {
		TrovatoProperties trovatoProperties = SpringUtil.getBean("trovatoProperties",TrovatoProperties.class);
		return trovatoProperties;
	}


	public TrovatoBulk ricercaDatiTrovato(it.cnr.jada.UserContext userContext,Integer trovato, Boolean soloValidi) throws Exception {
		TrovatoBulk trovatoBulk = new TrovatoBulk();
		if (trovato == null){
			throw new ApplicationException("Identificativo del trovato non indicato.");
		} else {
			trovatoBulk = cerca(trovato, soloValidi);
		}
		return trovatoBulk;
	}

	public TrovatoBulk ricercaDatiTrovato(it.cnr.jada.UserContext userContext,Integer trovato) throws Exception {
		return ricercaDatiTrovato(userContext, trovato, false);
	}

	private TrovatoBulk cerca(Integer pgTrovato, Boolean soloValidi) throws Exception {

    	TrovatoBulk trovato = null;
        String url = "";
        url = getTargetEndpoint()+"/brevetti/rest/trovato/";
        if (soloValidi){
        	url+="valido/";
        }
        url+=pgTrovato;
    	String username = getSiglaRestClientUser(), 
    			password = getSiglaRestClientPassword();
        	
        	ClientBuilder clientBuilder = ClientBuilder.newBuilder();
        	Client client = clientBuilder.register(new BasicAuthentication(username,  password)).build();
        	WebTarget target = client.target(url);
        	Invocation.Builder request = target.request();
        	Response response = request.get();
        	TrovatoRest trovatoRest = response.readEntity(TrovatoRest.class);
        
        	if (trovatoRest == null ){
    			throw new ApplicationException("Identificativo del trovato indicato non esiste.");
        	}  else {
        		trovato = caricaTrovato(trovatoRest);
        	}
		
	    return trovato;
	}
	
	private TrovatoBulk caricaTrovato(TrovatoRest trovato){
		TrovatoBulk trovatoBulk = new TrovatoBulk();
		trovatoBulk.setInventore(trovato.getInventore());
		trovatoBulk.setTitolo(trovato.getTitolo());
		trovatoBulk.setNsrif(trovato.getNsrif());
		trovatoBulk.setPg_trovato(new Long(trovato.getNsrif()));
		return trovatoBulk;
	}
	public synchronized void loadProperties() throws FileNotFoundException, IOException {
		TrovatoProperties trovatoProperties = recuperoTrovatoProperties();
		setTargetEndpoint(trovatoProperties.getTrovatoTargetEndpoint());
		setSiglaRestClientUser(trovatoProperties.getTrovatoSiglaRestClientUser());
		setSiglaRestClientPassword(trovatoProperties.getTrovatoSiglaRestClientPassword());
	}

	public static String getTargetEndpoint() {
		return targetEndpoint;
	}


	public static void setTargetEndpoint(String targetEndpoint) {
		RicercaTrovato.targetEndpoint = targetEndpoint;
	}

	public static String getSiglaRestClientUser() {
		return siglaRestClientUser;
	}

	public static void setSiglaRestClientUser(String siglaRestClientUser) {
		RicercaTrovato.siglaRestClientUser = siglaRestClientUser;
	}

	public static String getSiglaRestClientPassword() {
		return siglaRestClientPassword;
	}

	public static void setSiglaRestClientPassword(String siglaRestClientPassword) {
		RicercaTrovato.siglaRestClientPassword = siglaRestClientPassword;
	}
}
