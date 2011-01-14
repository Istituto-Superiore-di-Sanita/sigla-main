package it.cnr.contab.config00.bp;

import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.jsp.PageContext;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;
import org.w3c.dom.*;

import it.cnr.contab.anagraf00.core.bulk.AnagraficoBulk;
import it.cnr.contab.anagraf00.core.bulk.TerzoBulk;
import it.cnr.contab.anagraf00.core.bulk.V_terzo_anagrafico_sipBulk;
import it.cnr.contab.anagraf00.core.bulk.V_terzo_sipBulk;
import it.cnr.contab.anagraf00.ejb.AnagraficoComponentSession;
import it.cnr.contab.anagraf00.ejb.TerzoComponentSession;
import it.cnr.contab.anagraf00.tabter.bulk.ComuneBulk;
import it.cnr.contab.anagraf00.tabter.bulk.NazioneBulk;
import it.cnr.contab.anagraf00.util.TerzoNonPresenteSIPException;
import it.cnr.contab.config00.util.Constants;
import it.cnr.jada.action.BusinessProcess;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.bulk.BulkList;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.ejb.CRUDComponentSession;
import it.cnr.jada.persistency.sql.DuplicateKeyException;
import it.cnr.jada.util.RemoteIterator;
import it.cnr.jada.util.XmlWriter;
import it.cnr.jada.util.ejb.EJBCommonServices;

public class RicercaTerziBP extends BusinessProcess implements ResponseXMLBP{
	private String query;
	private String dominio;
	private String servizio;
	private String tipoterzo;
	private Integer codiceErrore;
	private Integer numMax;
	private String user;
	private List terzi;
	private String ricerca;
	//Parametri per l'inserimento di un nuovo terzo 	
	private String via;
	private String civico;
	private String cap;
	private String nazione;
	private String comune;
	//Parametri per l'inserimento di un nuovo terzo (Persona Giuridica)
	private String ragione_sociale;
	private String partita_iva;
	//Parametri per l'inserimento di un nuovo terzo (Persona Fisica)
	private String cognome;
	private String nome;
	private String codice_fiscale;
	private String data_nascita;
	private String nazione_nascita;
	private String comune_nascita;
	private String sesso;
	private String dip;
	private String dt_inizio_rend;
	private String dt_fine_rend;
	//Parametri per l'eliminazione di un terzo 	
	private String cd_terzo;
	
	public RicercaTerziBP() {
		super();
	}

	public RicercaTerziBP(String s) {
		super(s);
	}
	private Element generaErrore(Document xmldoc){
		Element e = xmldoc.createElement("cercaterzi:errore");
		e.setAttribute("codice",codiceErrore.toString());
		Node n = xmldoc.createTextNode(Constants.erroriSIP.get(codiceErrore));
		e.appendChild(n);
		return e;
	}
	private Element generaNumeroTerzi(Document xmldoc){
		Element e =null;
		if (this.getServizio().compareTo("rendicontazione")!=0)
			 e = xmldoc.createElement("cercaterzi:numris");
			else
			 e = xmldoc.createElement("numris");
		Node n = xmldoc.createTextNode(new Integer(getTerzi().size()).toString());
    	e.appendChild(n);
    	return e;	
	}
	private Element generaDettaglioTerziGiuridica(Document xmldoc, Integer codice, String denominazione, String partitaiva){
		Element elementTerzo = xmldoc.createElement("cercaterzi:terzo");
		Element elementCodice = xmldoc.createElement("cercaterzi:codice");
		Node nodeCodice = xmldoc.createTextNode(codice.toString());
		elementCodice.appendChild(nodeCodice);
		elementTerzo.appendChild(elementCodice);

		Element elementDenominazione = xmldoc.createElement("cercaterzi:denominazione");
		Node nodeDenominazione = xmldoc.createTextNode(denominazione==null?"":denominazione);
		elementDenominazione.appendChild(nodeDenominazione);
		elementTerzo.appendChild(elementDenominazione);
		
		Element elementPartitaiva = xmldoc.createElement("cercaterzi:partitaiva");
		Node nodePartitaiva;
		if (partitaiva!= null)
			nodePartitaiva = xmldoc.createTextNode(partitaiva);
		else
			nodePartitaiva = xmldoc.createTextNode("");
		elementPartitaiva.appendChild(nodePartitaiva);
		elementTerzo.appendChild(elementPartitaiva);
		
		return elementTerzo;
	}
	private Element generaDettaglioTerziFisica(Document xmldoc, Integer codice, String cognome, String nome, String codicefiscale){
		Element elementTerzo = xmldoc.createElement("cercaterzi:terzo");
		Element elementCodice = xmldoc.createElement("cercaterzi:codice");
		Node nodeCodice = xmldoc.createTextNode(codice.toString());
		elementCodice.appendChild(nodeCodice);
		elementTerzo.appendChild(elementCodice);

		Element elementCognome = xmldoc.createElement("cercaterzi:cognome");
		Node nodeCognome = xmldoc.createTextNode(cognome==null?"":cognome);
		elementCognome.appendChild(nodeCognome);
		elementTerzo.appendChild(elementCognome);

		Element elementNome = xmldoc.createElement("cercaterzi:nome");
		Node nodeNome;
		if (nome!= null)
			nodeNome = xmldoc.createTextNode(nome);
		else
			nodeNome = xmldoc.createTextNode("");
		elementNome.appendChild(nodeNome);
		elementTerzo.appendChild(elementNome);
		
		Element elementCodicefiscale = xmldoc.createElement("cercaterzi:codicefiscale");
		Node nodeCodicefiscale;
		if (codicefiscale!= null)
			nodeCodicefiscale = xmldoc.createTextNode(codicefiscale);
		else
			nodeCodicefiscale = xmldoc.createTextNode("");
		elementCodicefiscale.appendChild(nodeCodicefiscale);
		elementTerzo.appendChild(elementCodicefiscale);
		
		return elementTerzo;
	}
    public void generaXML(PageContext pagecontext) throws IOException, ServletException{
		try {

				if (getNumMax()==null)
					setNumMax(new Integer(20));
		    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    	DocumentBuilder builder = factory.newDocumentBuilder();
		    	DOMImplementation impl = builder.getDOMImplementation();
		    	Document xmldoc =null;
		    	Element root =null;
		    	if (this.getServizio().compareTo("rendicontazione")!=0){
		    		xmldoc=impl.createDocument("http://gestioneistituti.cnr.it/cercaterzi", "cercaterzi:root", null);
		    		root = xmldoc.getDocumentElement();
		    	}
		    	else{
		    		xmldoc = impl.createDocument(null, "root", null);
		    		root = xmldoc.getDocumentElement();
		    		root.setAttributeNS("http://www.w3.org/2001/XMLSchema-instance", "xsi:noNamespaceSchemaLocation", "http://gestioneistituti.cnr.it/cercaterzi");		    			
		    	}
		    	if (codiceErrore!= null){
		    		root.appendChild(generaErrore(xmldoc));
		    	}else{
		    		root.appendChild(generaNumeroTerzi(xmldoc));
		    		int num = 0;
		    		if (getTerzi() != null && !getTerzi().isEmpty()){
		    			if (this.getServizio().compareTo("rendicontazione")!=0){
		    				
				    		for (Iterator i = getTerzi().iterator();i.hasNext()&&num<getNumMax().intValue();){
				    			V_terzo_anagrafico_sipBulk terzo = (V_terzo_anagrafico_sipBulk)i.next();
				    			if (tipoterzo.equalsIgnoreCase("fisica")){
				    				root.appendChild(generaDettaglioTerziFisica(xmldoc,terzo.getCd_terzo(),terzo.getCognome(),terzo.getNome(),terzo.getCodice_fiscale_pariva()));
				    			}else if (tipoterzo.equalsIgnoreCase("giuridica")){
				    				root.appendChild(generaDettaglioTerziGiuridica(xmldoc,terzo.getCd_terzo(),terzo.getDenominazione_sede(),terzo.getCodice_fiscale_pariva()));
				    			}
				    			num++;
				    		}
		    			}else if  (this.getServizio().compareTo("rendicontazione")==0)
		    			{
		    				for (Iterator i = getTerzi().iterator();i.hasNext()&&num<getNumMax().intValue();){
		    					V_terzo_sipBulk terzo = (V_terzo_sipBulk)i.next();
		    					if (terzo.getMatricola()!=null){
		    						root.appendChild(generaDettaglioTerzoDip(xmldoc,terzo.getCd_terzo(),terzo.getCognome(),terzo.getNome(),terzo.getCodice_fiscale(),terzo.getMatricola().toString(),terzo.getQualifica(),terzo.getDesc_qualifica(),terzo.getTipo_rapporto(),terzo.getUo(),terzo.getAnno_rif().toString(),terzo.getMese_rif().toString(),(terzo.getData_cessazione()==null?null:terzo.getData_cessazione()),terzo.getLivello_1(),(terzo.getFascia()==null?null:terzo.getFascia().toString()),(terzo.getPerc_part_time()==null?new java.math.BigDecimal(100):terzo.getPerc_part_time()).toString(),terzo.getCosto()==null?null:terzo.getCosto().toString()));
		    					}else{
		    						root.appendChild(generaDettaglioTerzoNoDip(xmldoc,terzo.getCd_terzo(),terzo.getTi_entita(),terzo.getCognome(),terzo.getNome(),terzo.getDenominazione_sede(),terzo.getCodice_fiscale(),terzo.getPartita_iva(),(terzo.getData_cessazione()==null?null:terzo.getData_cessazione())));
		    					}
		    					num++;
		    				}
		    			}
		    		}
		    	}
				DOMSource domSource = new DOMSource(xmldoc);
		    	StreamResult streamResult = new StreamResult(pagecontext.getOut());
		    	TransformerFactory tf = TransformerFactory.newInstance();
		    	Transformer serializer = tf.newTransformer();
		    	serializer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
		    	if (this.getServizio()!=null && this.getServizio().compareTo("rendicontazione")!=0){
		    	   serializer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,"https://contab.cnr.it/SIGLA/schema/cercaterzi.dtd");
		    	   serializer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,"cercaterzi");
		    	}
		    	serializer.setOutputProperty(OutputKeys.INDENT,"yes");
		    	serializer.setOutputProperty(OutputKeys.STANDALONE,"no");
		    	serializer.transform(domSource, streamResult); 
		} catch (ParserConfigurationException e) {
		} catch (TransformerConfigurationException e) {
		} catch (TransformerException e) {
		}
    }
	
	private Node generaDettaglioTerzoNoDip(Document xmldoc, Integer codice,
			String ti_entita, String cognome, String nome,
			String denominazione, String codicefiscale,
			String partita_iva, java.util.Date data) {
		
		Element elementTerzo = xmldoc.createElement("terzo");
		Element element = xmldoc.createElement("codice");
		Node node = xmldoc.createTextNode(codice.toString());
		element.appendChild(node);
		elementTerzo.appendChild(element);
		
        if (ti_entita.compareTo("F")==0){
			element = xmldoc.createElement("cognome");
			node = xmldoc.createTextNode(cognome==null?"":cognome);
			element.appendChild(node);
			elementTerzo.appendChild(element);
	
			element = xmldoc.createElement("nome");
			node = xmldoc.createTextNode(nome==null?"":nome);
			element.appendChild(node);
			elementTerzo.appendChild(element);
			
        }else{
        	element= xmldoc.createElement("denominazione");
			node = xmldoc.createTextNode(denominazione==null?"":denominazione);
			element.appendChild(node);
			elementTerzo.appendChild(element);
        }	
		element= xmldoc.createElement("partitaiva");
		node = xmldoc.createTextNode(partita_iva==null?"":partita_iva);
		element.appendChild(node);
		elementTerzo.appendChild(element);
    
        element = xmldoc.createElement("codicefiscale");
		node = xmldoc.createTextNode(codicefiscale==null?"":codicefiscale);
		element.appendChild(node);
		elementTerzo.appendChild(element);
		
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
		element = xmldoc.createElement("data_cessazione");
		node = xmldoc.createTextNode(data==null?"":formatter.format(data));
		element.appendChild(node);
		elementTerzo.appendChild(element);
		
		return elementTerzo;
	}

	private Node generaDettaglioTerzoDip(Document xmldoc, Integer codice,
			String cognome, String nome, String codice_fiscale,
			String matricola, String qualifica, String desc_qualifica,String tipo_rapporto, String uo,
			String anno_rif, String mese_rif,java.util.Date data_cessazione,String livello_1,String fascia,String perc_part_time,String costo) {
		Element elementTerzo = xmldoc.createElement("terzo");
		Element element = xmldoc.createElement("codice");
		Node node = xmldoc.createTextNode(codice.toString());
		element.appendChild(node);
		elementTerzo.appendChild(element);
		
   		element = xmldoc.createElement("cognome");
		node = xmldoc.createTextNode(cognome==null?"":cognome);
		element.appendChild(node);
		elementTerzo.appendChild(element);

		element = xmldoc.createElement("nome");
		node = xmldoc.createTextNode(nome==null?"":nome);
		element.appendChild(node);
		elementTerzo.appendChild(element);
		
        element = xmldoc.createElement("codicefiscale");
		node = xmldoc.createTextNode(codice_fiscale==null?"":codice_fiscale);
		element.appendChild(node);
		elementTerzo.appendChild(element);
		
		element = xmldoc.createElement("matricola");
		node = xmldoc.createTextNode(matricola==null?"":matricola);
		element.appendChild(node);
		elementTerzo.appendChild(element);
		
		element = xmldoc.createElement("qualifica");
		node = xmldoc.createTextNode(qualifica==null?"":qualifica);
		element.appendChild(node);
		elementTerzo.appendChild(element);
		
		element = xmldoc.createElement("desc_qualifica");
		node = xmldoc.createTextNode(desc_qualifica==null?"":desc_qualifica);
		element.appendChild(node);
		elementTerzo.appendChild(element);
		
		element = xmldoc.createElement("livello_1");
		node = xmldoc.createTextNode(livello_1==null?"":livello_1);
		element.appendChild(node);
		elementTerzo.appendChild(element);
		
		element = xmldoc.createElement("tiporapporto");
		node = xmldoc.createTextNode(tipo_rapporto==null?"":tipo_rapporto);
		element.appendChild(node);
		elementTerzo.appendChild(element);
		
		element = xmldoc.createElement("uo");
		node = xmldoc.createTextNode(uo==null?"":uo);
		element.appendChild(node);
		elementTerzo.appendChild(element);
		
		element = xmldoc.createElement("annorif");
		node = xmldoc.createTextNode(anno_rif==null?"":anno_rif);
		element.appendChild(node);
		elementTerzo.appendChild(element);
		
		element = xmldoc.createElement("meserif");
		node = xmldoc.createTextNode(mese_rif==null?"":mese_rif);
		element.appendChild(node);
		elementTerzo.appendChild(element);
		
		java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("dd/MM/yyyy");
		element = xmldoc.createElement("data_cessazione");
		node = xmldoc.createTextNode(data_cessazione==null?"":formatter.format(data_cessazione));
		element.appendChild(node);	
		elementTerzo.appendChild(element);
		
		element = xmldoc.createElement("fascia");
		node = xmldoc.createTextNode(fascia==null?"":fascia);
		element.appendChild(node);
		elementTerzo.appendChild(element);
		
		element = xmldoc.createElement("perc_part_time");
		node = xmldoc.createTextNode(perc_part_time==null?"":perc_part_time);
		element.appendChild(node);
		elementTerzo.appendChild(element);
		
		element = xmldoc.createElement("costo");
		node = xmldoc.createTextNode(costo==null?"":costo);
		element.appendChild(node);
		elementTerzo.appendChild(element);
		return elementTerzo;
	}

	public void eseguiRicerca(it.cnr.jada.action.ActionContext context)throws BusinessProcessException{
		if(getQuery()== null){
			codiceErrore = Constants.ERRORE_SIP_101;
			return;
		}else if(getDominio()== null||(!getDominio().equalsIgnoreCase("cd_terzo")&&!getDominio().equalsIgnoreCase("denominazione"))){
			codiceErrore = Constants.ERRORE_SIP_102;
			return;
		}else if(getTipoterzo()== null||(!getTipoterzo().equalsIgnoreCase("fisica")&&!getTipoterzo().equalsIgnoreCase("giuridica"))){
			codiceErrore = Constants.ERRORE_SIP_104;
			return;
		}else{
			try {
				setTerzi(((TerzoComponentSession)createComponentSession("CNRANAGRAF00_EJB_TerzoComponentSession",TerzoComponentSession.class)).findListaTerziSIP(context.getUserContext(),getQuery(),getDominio(),getTipoterzo(),getRicerca()));
			} catch (ComponentException e) {
				codiceErrore = Constants.ERRORE_SIP_100;
			} catch (RemoteException e) {
				codiceErrore = Constants.ERRORE_SIP_100;
			}
		}
	}
	public void eseguiRicerca_rendicontazione(it.cnr.jada.action.ActionContext context)throws BusinessProcessException{
		if(getQuery()== null){
			codiceErrore = Constants.ERRORE_SIP_101;
			return;
		}else if(getDominio()== null||(!getDominio().equalsIgnoreCase("cd_terzo")&&!getDominio().equalsIgnoreCase("denominazione")&&!getDominio().equalsIgnoreCase("matricola"))){
			codiceErrore = Constants.ERRORE_SIP_102;
			return;
		}else if(getTipoterzo()== null||(!getTipoterzo().equalsIgnoreCase("fisica")&&!getTipoterzo().equalsIgnoreCase("giuridica"))){
			codiceErrore = Constants.ERRORE_SIP_104;
			return;
		}else if (getDt_inizio_rend()==null || getDt_fine_rend()==null){
			codiceErrore = Constants.ERRORE_SIP_115;
			return;
		}else{
			if (getDominio().equalsIgnoreCase("matricola")){
				try{
				Long l=new Long(query);
				}catch (NumberFormatException e){
					codiceErrore = Constants.ERRORE_SIP_119;
					return;
				}
			}
			try {
				setTerzi(((TerzoComponentSession)createComponentSession("CNRANAGRAF00_EJB_TerzoComponentSession",TerzoComponentSession.class)).findListaTerziSIP_rendicontazione(context.getUserContext(),getQuery(),getDominio(),getTipoterzo(),getRicerca(),new Timestamp(new SimpleDateFormat("dd/MM/yyyy").parse(getDt_inizio_rend()).getTime()),new Timestamp(new SimpleDateFormat("dd/MM/yyyy").parse(getDt_fine_rend()).getTime()),getDip()));
			} catch (ComponentException e) {
				codiceErrore = Constants.ERRORE_SIP_100;
			} catch (RemoteException e) {
				codiceErrore = Constants.ERRORE_SIP_100;
			} catch (ParseException e) {
				codiceErrore = Constants.ERRORE_SIP_116;
			}
		}
	}
	public void eliminaTerzo(it.cnr.jada.action.ActionContext context)throws BusinessProcessException{
		if(getTipoterzo()== null||(!getTipoterzo().equalsIgnoreCase("fisica")&&!getTipoterzo().equalsIgnoreCase("giuridica"))){
			codiceErrore = Constants.ERRORE_SIP_104;
			return;
		}		
		if(getCd_terzo()==null){
			codiceErrore = Constants.ERRORE_SIP_107;
			return;
		}
		try {
			((AnagraficoComponentSession)createComponentSession("CNRANAGRAF00_EJB_AnagraficoComponentSession",AnagraficoComponentSession.class)).eliminaBulkForSIP(context.getUserContext(),getCd_terzo());
			setTerzi(new BulkList());
		} catch (ComponentException e) {
			if (e instanceof TerzoNonPresenteSIPException)
				codiceErrore = Constants.ERRORE_SIP_108;
			else
				codiceErrore = Constants.ERRORE_SIP_109;
		} catch (RemoteException e) {
			codiceErrore = Constants.ERRORE_SIP_100;
		}	
	}
	
	public void inserisciTerzo(it.cnr.jada.action.ActionContext context)throws BusinessProcessException, ParseException{
		if(getUser()== null){
			codiceErrore = Constants.ERRORE_SIP_110;
			return;
		}
		if(getTipoterzo()== null||(!getTipoterzo().equalsIgnoreCase("fisica")&&!getTipoterzo().equalsIgnoreCase("giuridica"))){
			codiceErrore = Constants.ERRORE_SIP_104;
			return;
		}		
		if(getVia()==null || getCivico()== null || getCap() == null ||
				getNazione() == null || getComune() == null){
			codiceErrore = Constants.ERRORE_SIP_107;
			return;
		}
		if (getTipoterzo().equalsIgnoreCase("fisica")){
			if(getCognome()==null || getNome()== null || getCodice_fiscale() == null ||
					getData_nascita() == null || getNazione_nascita()==null ||
					getComune_nascita() == null||getSesso() == null){
				codiceErrore = Constants.ERRORE_SIP_107;
				return;
			}
		}
		if (getTipoterzo().equalsIgnoreCase("giuridica")){
			if(getRagione_sociale()==null || getPartita_iva()== null){
				codiceErrore = Constants.ERRORE_SIP_107;
				return;
			}
		}
		AnagraficoBulk anagrafico = new AnagraficoBulk();
		try {
			anagrafico = (AnagraficoBulk)((AnagraficoComponentSession)createComponentSession("CNRANAGRAF00_EJB_AnagraficoComponentSession",AnagraficoComponentSession.class)).inizializzaBulkPerInserimento(context.getUserContext(),anagrafico);
		} catch (ComponentException e1) {
			codiceErrore = Constants.ERRORE_SIP_100;
		} catch (RemoteException e1) {
			codiceErrore = Constants.ERRORE_SIP_100;
		}
		anagrafico.setVia_fiscale(getVia());
		anagrafico.setNum_civico_fiscale(getCivico());
		anagrafico.setCap_comune_fiscale(getCap());
		anagrafico.setNazionalita(new NazioneBulk(new Long(getNazione())));
		anagrafico.setComune_fiscale(new ComuneBulk(new Long(getComune())));
		anagrafico.setFl_occasionale(Boolean.FALSE);
		anagrafico.setFl_fatturazione_differita(Boolean.FALSE);
		anagrafico.setTi_entita_persona_struttura(AnagraficoBulk.ENTITA_PERSONA);
		anagrafico.setTi_entita(getTipoterzo().equalsIgnoreCase("fisica")?AnagraficoBulk.FISICA:AnagraficoBulk.GIURIDICA);
		if (getTipoterzo().equalsIgnoreCase("fisica")){
			anagrafico.setTi_entita_fisica(AnagraficoBulk.ALTRO);
			anagrafico.setFl_soggetto_iva(Boolean.FALSE);
			anagrafico.setCognome(getCognome());
			anagrafico.setNome(getNome());
			anagrafico.setCodice_fiscale(getCodice_fiscale().toUpperCase());
			anagrafico.setDt_nascita(new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse(getData_nascita()).getTime()));
			anagrafico.setComune_nascita(new ComuneBulk(new Long(getComune_nascita())));
			anagrafico.setTi_sesso(getSesso().toUpperCase());			
		}else if (getTipoterzo().equalsIgnoreCase("giuridica")){
			anagrafico.setTi_entita_giuridica(AnagraficoBulk.ALTRO);
			anagrafico.setFl_soggetto_iva(Boolean.TRUE);
			anagrafico.setRagione_sociale(getRagione_sociale());
			anagrafico.setPartita_iva(getPartita_iva());
		}
		anagrafico.setToBeCreated();
		try {
			setTerzi(((AnagraficoComponentSession)createComponentSession("CNRANAGRAF00_EJB_AnagraficoComponentSession",AnagraficoComponentSession.class)).bulkForSIP(context.getUserContext(),anagrafico));
		} catch (ComponentException e) {
			if (e.getDetail() instanceof DuplicateKeyException)
				codiceErrore = Constants.ERRORE_SIP_106;
			else
				codiceErrore = Constants.ERRORE_SIP_105;
		} catch (RemoteException e) {
			codiceErrore = Constants.ERRORE_SIP_100;
		} 		
	}	
	public void modificaTerzo(it.cnr.jada.action.ActionContext context)throws BusinessProcessException, ParseException{
		if(getTipoterzo()== null||(!getTipoterzo().equalsIgnoreCase("fisica")&&!getTipoterzo().equalsIgnoreCase("giuridica"))){
			codiceErrore = Constants.ERRORE_SIP_104;
			return;
		}		
		if(getCd_terzo()==null){
			codiceErrore = Constants.ERRORE_SIP_107;
			return;
		}
		TerzoBulk terzo = new TerzoBulk(new Integer(getCd_terzo()));
		try {
			RemoteIterator iterator = EJBCommonServices.openRemoteIterator(context, ((TerzoComponentSession)createComponentSession("CNRANAGRAF00_EJB_TerzoComponentSession",TerzoComponentSession.class)).cerca(context.getUserContext(),null,terzo));
			if (iterator == null ||iterator.countElements() != 1){
				codiceErrore = Constants.ERRORE_SIP_108;
				return;
			}
			terzo = (TerzoBulk)iterator.nextElement();
		} catch (ComponentException e) {
			codiceErrore = Constants.ERRORE_SIP_100;
		} catch (RemoteException e) {
			codiceErrore = Constants.ERRORE_SIP_100;
		}
		if(getVia()==null || getCivico()== null || getCap() == null ||
				getNazione() == null || getComune() == null){
			codiceErrore = Constants.ERRORE_SIP_107;
			return;
		}
		if (getTipoterzo().equalsIgnoreCase("fisica")){
			if(getCognome()==null || getNome()== null || getCodice_fiscale() == null ||
					getData_nascita() == null || getNazione_nascita()==null ||
					getComune_nascita() == null||getSesso() == null){
				codiceErrore = Constants.ERRORE_SIP_107;
				return;
			}
		}
		if (getTipoterzo().equalsIgnoreCase("giuridica")){
			if(getRagione_sociale()==null || getPartita_iva()== null){
				codiceErrore = Constants.ERRORE_SIP_107;
				return;
			}
		}
		AnagraficoBulk anagrafico = terzo.getAnagrafico();
		try {
			anagrafico = (AnagraficoBulk)((AnagraficoComponentSession)createComponentSession("CNRANAGRAF00_EJB_AnagraficoComponentSession",AnagraficoComponentSession.class)).inizializzaBulkPerModifica(context.getUserContext(),anagrafico);
		} catch (ComponentException e1) {
			codiceErrore = Constants.ERRORE_SIP_100;
		} catch (RemoteException e1) {
			codiceErrore = Constants.ERRORE_SIP_100;
		}
		anagrafico.setVia_fiscale(getVia());
		anagrafico.setNum_civico_fiscale(getCivico());
		anagrafico.setCap_comune_fiscale(getCap());
		anagrafico.setNazionalita(new NazioneBulk(new Long(getNazione())));
		anagrafico.setComune_fiscale(new ComuneBulk(new Long(getComune())));
		anagrafico.getComune_fiscale().setNazione(anagrafico.getNazionalita());
		anagrafico.setFl_occasionale(Boolean.FALSE);
		anagrafico.setFl_fatturazione_differita(Boolean.FALSE);
		anagrafico.setTi_entita_persona_struttura(AnagraficoBulk.ENTITA_PERSONA);
		anagrafico.setTi_entita(getTipoterzo().equalsIgnoreCase("fisica")?AnagraficoBulk.FISICA:AnagraficoBulk.GIURIDICA);
		if (getTipoterzo().equalsIgnoreCase("fisica")){
			anagrafico.setTi_entita_fisica(AnagraficoBulk.ALTRO);
			anagrafico.setFl_soggetto_iva(Boolean.FALSE);
			anagrafico.setCognome(getCognome());
			anagrafico.setNome(getNome());
			anagrafico.setCodice_fiscale(getCodice_fiscale().toUpperCase());
			anagrafico.setDt_nascita(new Timestamp(new SimpleDateFormat("yyyy/MM/dd").parse(getData_nascita()).getTime()));
			anagrafico.setComune_nascita(new ComuneBulk(new Long(getComune_nascita())));
			anagrafico.setTi_sesso(getSesso().toUpperCase());			
		}else if (getTipoterzo().equalsIgnoreCase("giuridica")){
			anagrafico.setTi_entita_giuridica(AnagraficoBulk.ALTRO);
			anagrafico.setFl_soggetto_iva(Boolean.TRUE);
			anagrafico.setRagione_sociale(getRagione_sociale());
			anagrafico.setPartita_iva(getPartita_iva());
		}
		anagrafico.setToBeUpdated();
		
		try {
			setTerzi(((AnagraficoComponentSession)createComponentSession("CNRANAGRAF00_EJB_AnagraficoComponentSession",AnagraficoComponentSession.class)).bulkForSIP(context.getUserContext(),anagrafico));
		} catch (ComponentException e) {
			if (e.getDetail() instanceof DuplicateKeyException)
				codiceErrore = Constants.ERRORE_SIP_106;
			else
				codiceErrore = Constants.ERRORE_SIP_105;
		} catch (RemoteException e) {
			codiceErrore = Constants.ERRORE_SIP_100;
		} 		
	}	
	
	public String getDominio() {
		return dominio;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getServizio() {
		return servizio;
	}

	public void setServizio(String servizio) {
		this.servizio = servizio;
	}

	public String getTipoterzo() {
		return tipoterzo;
	}

	public void setTipoterzo(String tipoterzo) {
		this.tipoterzo = tipoterzo;
	}

	public List getTerzi() {
		return terzi;
	}

	public void setTerzi(List terzi) {
		this.terzi = terzi;
	}

	public Integer getNumMax() {
		return numMax;
	}

	public void setNumMax(Integer numMax) {
		this.numMax = numMax;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCd_terzo() {
		return cd_terzo;
	}

	public void setCd_terzo(String cd_terzo) {
		this.cd_terzo = cd_terzo;
	}

	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	public String getCodice_fiscale() {
		return codice_fiscale;
	}

	public void setCodice_fiscale(String codice_fiscale) {
		this.codice_fiscale = codice_fiscale;
	}

	public Integer getCodiceErrore() {
		return codiceErrore;
	}

	public void setCodiceErrore(Integer codiceErrore) {
		this.codiceErrore = codiceErrore;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getComune_nascita() {
		return comune_nascita;
	}

	public void setComune_nascita(String comune_nascita) {
		this.comune_nascita = comune_nascita;
	}

	public String getData_nascita() {
		return data_nascita;
	}

	public void setData_nascita(String data_nascita) {
		this.data_nascita = data_nascita;
	}

	public String getNazione() {
		return nazione;
	}

	public void setNazione(String nazione) {
		this.nazione = nazione;
	}

	public String getNazione_nascita() {
		return nazione_nascita;
	}

	public void setNazione_nascita(String nazione_nascita) {
		this.nazione_nascita = nazione_nascita;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPartita_iva() {
		return partita_iva;
	}

	public void setPartita_iva(String partita_iva) {
		this.partita_iva = partita_iva;
	}

	public String getRagione_sociale() {
		return ragione_sociale;
	}

	public void setRagione_sociale(String ragione_sociale) {
		this.ragione_sociale = ragione_sociale;
	}

	public String getSesso() {
		return sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getVia() {
		return via;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getRicerca() {
		return ricerca;
	}

	public void setRicerca(String ricerca) {
		this.ricerca = ricerca;
	}

	public String getDt_inizio_rend() {
		return dt_inizio_rend;
	}

	public void setDt_inizio_rend(String dt_inizio_rend) {
		this.dt_inizio_rend = dt_inizio_rend;
	}

	public String getDt_fine_rend() {
		return dt_fine_rend;
	}

	public void setDt_fine_rend(String dt_fine_rend) {
		this.dt_fine_rend = dt_fine_rend;
	}

	public String getDip() {
		return dip;
	}

	public void setDip(String dip) {
		this.dip = dip;
	}
}
