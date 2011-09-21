package it.cnr.contab.docamm00.consultazioni.bp;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.Vector;

import javax.ejb.EJBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import it.cnr.contab.config00.bulk.Parametri_cnrBulk;
import it.cnr.contab.config00.bulk.Parametri_enteBulk;
import it.cnr.contab.config00.ejb.Parametri_enteComponentSession;
import it.cnr.contab.config00.sto.bulk.CdsBulk;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.docamm00.consultazioni.bulk.ReferenteAmministrativoBulk;
import it.cnr.contab.docamm00.consultazioni.bulk.V_terzi_da_conguagliareBulk;
import it.cnr.contab.docamm00.consultazioni.ejb.CdSDaConguagliareComponentSession;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.util.Utility;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.action.Config;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.ejb.CRUDComponentSession;
import it.cnr.jada.util.Introspector;
import it.cnr.jada.util.SendMail;
import it.cnr.jada.util.XMLObjectFiller;
import it.cnr.jada.util.action.ConsultazioniBP;
import it.cnr.jada.util.jsp.Button;

public class ConsCdSDaConguagliareBP extends ConsultazioniBP {
	private boolean uoEnte = false;
	public static final String STRING_TO_BE_REPLACE = "{0}";
	public static final String XML_REFERENTI_AMMINISTRATIVI = "http://www.cnr.it/sitocnr/referentiamministrativi.xml";
	private boolean testMode = false;
	private List<ReferenteAmministrativoBulk> referentiAmministrativi = new ArrayList<ReferenteAmministrativoBulk>();
	@SuppressWarnings("unchecked")
	@Override
	public Vector addButtonsToToolbar(Vector listButton) {
		if (isUoEnte()){		
			Button eMail = new Button();
			eMail.setImg("img/Email.gif");
			eMail.setDisabledImg("img/Email.gif");
			eMail.setLabel("<u>E</u>-Mail");
			eMail.setHref("javascript:submitForm('doEMail')");
			eMail.setStyle("width:90px;");
			eMail.setTitle("Comunica ai segretari dei CdS selezionati i terzi ancora da conguagliare.");
			eMail.setAccessKey("E");
			eMail.setSeparator(true);
			listButton.add(eMail);
		}
		return super.addButtonsToToolbar(listButton);
	}

	@Override
	public CRUDComponentSession createComponentSession() throws EJBException, RemoteException, BusinessProcessException {
		return (CdSDaConguagliareComponentSession)createComponentSession(getComponentSessioneName());
	}
	
	@SuppressWarnings("unchecked")
	public void inviaEMail(ActionContext actioncontext, List<CdsBulk> cdsSelezionati) throws BusinessProcessException{
		try {
			String EMAIL1,EMAIL2,EMAIL3,EMAIL4=null;
			for (Iterator<CdsBulk> iterator = cdsSelezionati.iterator(); iterator.hasNext();) {
				CdsBulk cds = iterator.next();
				List<V_terzi_da_conguagliareBulk> terziDaConguagliare = ((CdSDaConguagliareComponentSession)createComponentSession(getComponentSessioneName())).findTerzi(actioncontext.getUserContext(), cds);
				Parametri_cnrBulk parametriCNR = (Parametri_cnrBulk) createComponentSession().findByPrimaryKey(actioncontext.getUserContext(), new Parametri_cnrBulk(CNRUserContext.getEsercizio(actioncontext.getUserContext())));
				String subject = parametriCNR.getOggettoEmailTerziCongua();
				String corpo = parametriCNR.getCorpoEmailTerziCongua();				
				String messTerzi = componiMesaggio(actioncontext, terziDaConguagliare);
				if (corpo != null && corpo.contains(STRING_TO_BE_REPLACE)){
					corpo.replaceAll(STRING_TO_BE_REPLACE, messTerzi);
				}else{
					if (corpo == null)
						corpo = new String();
					corpo += messTerzi;
				}
				java.util.List<String> addressTO = new ArrayList<String>();
				java.util.List<String> addressCCN = new ArrayList<String>();
				String eMailReferente = null;
				for (Iterator<ReferenteAmministrativoBulk> iterator2 = referentiAmministrativi.iterator(); iterator2 .hasNext();) {
					ReferenteAmministrativoBulk dett = iterator2.next();
					if (dett.getCds().equalsIgnoreCase(cds.getCd_unita_organizzativa()) && dett.getSegr_refe().startsWith("S"))
						eMailReferente = dett.getEmail();
				}
				
				
				it.cnr.contab.config00.bulk.Configurazione_cnrBulk config = null;
				try {
					config = Utility.createConfigurazioneCnrComponentSession().getConfigurazione( actioncontext.getUserContext(),it.cnr.contab.utenze00.bp.CNRUserContext.getEsercizio( actioncontext.getUserContext()) , null, "INVIO_MAIL_AUTO","TERZI_DA_CONGUAGLIARE");
					EMAIL1=config.getVal01();
					EMAIL2=config.getVal02();
					EMAIL3=config.getVal03();
					EMAIL4=config.getVal04();
				} catch (RemoteException e) {
					throw new ComponentException(e);
				} catch (EJBException e) {
					throw new ComponentException(e);
				}
				Parametri_enteBulk parametriEnte = ((Parametri_enteComponentSession)it.cnr.jada.util.ejb.EJBCommonServices.createEJB("CNRCONFIG00_EJB_Parametri_enteComponentSession")).getParametriEnte(actioncontext.getUserContext());
				
				if (!parametriEnte.getTipo_db().equals(Parametri_enteBulk.DB_PRODUZIONE)){
					testMode=true;
				}
				if (testMode){
					addressTO.add("matilde.durso@cnr.it");
					subject+= " Referente :"+eMailReferente;
				}else{
					if (eMailReferente != null){
						addressTO.add(eMailReferente);
					}else{
						//addressTO.add("rosangela.pucciarelli@cnr.it");
						addressTO.add(EMAIL1);
						subject = "Non � stato possibile inviare l'E-Mail poich� non si conosce il riferimento del referente amministartivo del CdS :"+cds.getCd_unita_organizzativa();
					}
				
					if(EMAIL1!=null)
						addressCCN.add(EMAIL1);
					if(EMAIL2!=null)
						addressCCN.add(EMAIL2);
					if(EMAIL3!=null)
						addressCCN.add(EMAIL3);
					if(EMAIL4!=null)
						addressCCN.add(EMAIL4);
				}
				SendMail.sendMail(subject, corpo, addressTO, null, addressCCN);			
			}
		} catch (ComponentException e) {
			handleException(e);
		} catch (EJBException e) {
			handleException(e);
		} catch (RemoteException e) {
			handleException(e);
		} 			
	}
		
	private String componiMesaggio(ActionContext actioncontext, List<V_terzi_da_conguagliareBulk> terzi) throws ComponentException, RemoteException, EJBException, BusinessProcessException{
		StringBuffer mess = new StringBuffer("<table cellspacing=\"2\" cellpadding=\"2\" style=\" \">");
		String motivazione=null;
		TreeMap<String, List<V_terzi_da_conguagliareBulk>> treeUO = new TreeMap<String, List<V_terzi_da_conguagliareBulk>>(); 
		for (Iterator<V_terzi_da_conguagliareBulk> iterator = terzi.iterator(); iterator.hasNext();) {
			V_terzi_da_conguagliareBulk terzo = iterator.next();
			if (treeUO.get(terzo.getCd_unita_organizzativa()) == null){
				ArrayList<V_terzi_da_conguagliareBulk> listaTerzi = new ArrayList<V_terzi_da_conguagliareBulk>();
				listaTerzi.add(terzo);
				treeUO.put(terzo.getCd_unita_organizzativa(), listaTerzi);
			}else{
				treeUO.get(terzo.getCd_unita_organizzativa()).add(terzo);
			}
		}		
		for (Iterator<String> iterator = treeUO.keySet().iterator(); iterator.hasNext();) {
			String cdUO = iterator.next();
			Unita_organizzativaBulk uo = (Unita_organizzativaBulk) createComponentSession().findByPrimaryKey(actioncontext.getUserContext(), new Unita_organizzativaBulk(cdUO));
			mess.append("<tr><td colspan=\"2\" style=\"font-weight : bold\">");
			mess.append("Unit� Organizzativa: "+uo.getCd_unita_organizzativa() + " " + uo.getDs_unita_organizzativa());
			mess.append("</td></tr>");
			mess.append("<tr><td style=\"font-weight : bold\">Codice Terzo</td><td style=\"font-weight : bold\">Denominazione</td><td style=\"font-weight : bold\">Motivazione</td></tr>");		
			for (Iterator<V_terzi_da_conguagliareBulk> iteratorTerzi = treeUO.get(cdUO).iterator(); iteratorTerzi.hasNext();) {
				V_terzi_da_conguagliareBulk terzo = iteratorTerzi.next();
				if (terzo.getTipologia().compareTo(V_terzi_da_conguagliareBulk.TIPO_A)==0)
					motivazione = "Conguaglio da effettuare";
				else
					motivazione = "Conguaglio da contabilizzare";
				mess.append("<tr>");
				mess.append("<td>");
				mess.append(terzo.getCd_terzo());
				mess.append("</td>");
				mess.append("<td>");
				mess.append(terzo.getDenominazione());
				mess.append("</td>");
				mess.append("<td>");
				mess.append(motivazione);
				mess.append("</td>");
				mess.append("</tr>");
			}
			mess.append("<tr><td colspan=\"2\"></td></tr>");
		}
		mess.append("</table>");		
		return mess.toString();
	}
	
	@Override
	protected void init(Config config, ActionContext context) throws BusinessProcessException {
		super.init(config, context);
		setUoEnte(context);
		readXMLReferentiAmministrativi();
		openIterator(context);
	}
	public void setUoEnte(ActionContext context){	
		Unita_organizzativaBulk uo = it.cnr.contab.utenze00.bulk.CNRUserInfo.getUnita_organizzativa(context);
		if (uo.getCd_tipo_unita().equals(it.cnr.contab.config00.sto.bulk.Tipo_unita_organizzativaHome.TIPO_UO_ENTE))
			setUoEnte(true);	
	}

	public boolean isUoEnte() {
		return uoEnte;
	}

	public void setUoEnte(boolean uoEnte) {
		this.uoEnte = uoEnte;
	}	
	public void addToReferentiAmministrativi(ReferenteAmministrativoBulk referenteAmministrativo){
		referentiAmministrativi.add(referenteAmministrativo);
	}
	
	private void caricaOggetto(Element fstElmnt, String property, ReferenteAmministrativoBulk referenteAmministrativo) throws DOMException, IntrospectionException, InvocationTargetException, ParseException{
		NodeList fstNmElmntLst = fstElmnt.getElementsByTagName(property);
		Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
		NodeList fstNm = fstNmElmnt.getChildNodes();
		Introspector.setPropertyValue(referenteAmministrativo, property, ((Node) fstNm.item(0)).getNodeValue());
	}
	
	private void readXMLReferentiAmministrativi() throws BusinessProcessException{
		try {
			  URL url = new URL(XML_REFERENTI_AMMINISTRATIVI);
			  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			  DocumentBuilder db = dbf.newDocumentBuilder();
			  Document doc = db.parse(url.openStream());
			  doc.getDocumentElement().normalize();
			  NodeList nodeLst = doc.getElementsByTagName("record");
			  for (int s = 0; s < nodeLst.getLength(); s++) {
				  Node fstNode = nodeLst.item(s);
				  ReferenteAmministrativoBulk referenteAmministrativo = new ReferenteAmministrativoBulk();
				  if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
					  Element fstElmnt = (Element) fstNode;
					  caricaOggetto(fstElmnt,"cds",referenteAmministrativo);
					  caricaOggetto(fstElmnt,"uo",referenteAmministrativo);
					  caricaOggetto(fstElmnt,"cd_terzo",referenteAmministrativo);
					  caricaOggetto(fstElmnt,"cognome",referenteAmministrativo);
					  caricaOggetto(fstElmnt,"nome",referenteAmministrativo);
					  caricaOggetto(fstElmnt,"email",referenteAmministrativo);
					  caricaOggetto(fstElmnt,"telefono",referenteAmministrativo);
					  caricaOggetto(fstElmnt,"segr_refe",referenteAmministrativo);
					  caricaOggetto(fstElmnt,"descrizione",referenteAmministrativo);
				  }
				  addToReferentiAmministrativi(referenteAmministrativo);
			  }
		} catch (Exception e) {
			throw handleException(e);
		}
	}
}
