/*
* Created by Generator 1.0
* Date 25/05/2005
*/
package it.cnr.contab.pdg00.bulk;
import java.math.BigDecimal;

import it.cnr.contab.config00.pdcfin.bulk.Elemento_voceBulk;
import it.cnr.contab.config00.pdcfin.bulk.NaturaBulk;
import it.cnr.contab.config00.sto.bulk.CdrBulk;
import it.cnr.contab.config00.sto.bulk.Tipo_unita_organizzativaHome;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.doccont00.core.bulk.Numerazione_doc_contBulk;
import it.cnr.contab.pdg00.bp.PdGVariazioneBP;
import it.cnr.contab.pdg00.cdip.bulk.Ass_pdg_variazione_cdrBulk;
import it.cnr.contab.pdg01.bulk.Tipo_variazioneBulk;
import it.cnr.contab.preventvar00.bulk.Var_bilancioBulk;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.utenze00.bulk.CNRUserInfo;
import it.cnr.contab.util.ICancellatoLogicamente;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.bulk.BulkList;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.bulk.SimpleBulkList;
import it.cnr.jada.bulk.ValidationException;
import it.cnr.jada.persistency.IntrospectionException;
import it.cnr.jada.persistency.PersistencyException;
import it.cnr.jada.util.DateUtils;
import it.cnr.jada.util.action.CRUDBP;
import it.cnr.jada.util.action.SimpleCRUDBP;
import it.cnr.jada.util.ejb.EJBCommonServices;

public class Pdg_variazioneBulk extends Pdg_variazioneBase implements ICancellatoLogicamente{
	private static final java.util.Dictionary ti_statoKeys = new it.cnr.jada.util.OrderedHashtable();
	private static final java.util.Dictionary ti_tipologia_finKeys = new it.cnr.jada.util.OrderedHashtable();
	private static final java.util.Dictionary stato_invioKeys = new it.cnr.jada.util.OrderedHashtable();
	
	final public static String STATO_PROPOSTA_PROVVISORIA = "PRP";
	final public static String STATO_PROPOSTA_DEFINITIVA = "PRD";
	final public static String STATO_APPROVATA = "APP";
	final public static String STATO_APPROVAZIONE_FORMALE = "APF";
	final public static String STATO_RESPINTA = "RES";
	final public static String STATO_ANNULLATA = "ANN";

	final public static String STATO_DA_INVIARE = "DAI";
	final public static String STATO_INVIATA = "INV";

	static {
		ti_statoKeys.put(STATO_PROPOSTA_PROVVISORIA,"Proposta Provvisoria");
		ti_statoKeys.put(STATO_PROPOSTA_DEFINITIVA,"Proposta Definitiva");
		ti_statoKeys.put(STATO_APPROVATA,"Approvata");
		ti_statoKeys.put(STATO_APPROVAZIONE_FORMALE,"Approvazione Formale");
		ti_statoKeys.put(STATO_RESPINTA,"Respinta");
		ti_statoKeys.put(STATO_ANNULLATA,"Annullata");

		ti_tipologia_finKeys.put(NaturaBulk.TIPO_NATURA_FONTI_INTERNE,"Fonti Interne");
		ti_tipologia_finKeys.put(NaturaBulk.TIPO_NATURA_FONTI_ESTERNE,"Fonti Esterne");
		
		stato_invioKeys.put(STATO_DA_INVIARE,"Da inviare");
		stato_invioKeys.put(STATO_INVIATA,"Inviata");

	}

	private BulkList associazioneCDR = new BulkList();
	private BulkList archivioConsultazioni = new BulkList();
	private CdrBulk centro_responsabilita;
	private Elemento_voceBulk elemento_voce;
	private Tipo_variazioneBulk tipo_variazione;
	private java.util.Dictionary ti_causale_respintaKeys = new it.cnr.jada.util.OrderedHashtable();
	protected java.util.Collection tipologie_variazione;
	private boolean isBulkforSearch = false; 
	private BigDecimal somma_spesa_var_piu;
	private BigDecimal somma_spesa_var_meno;
	private BigDecimal somma_spesa_diff;
	private BigDecimal somma_costi_var_piu;
	private BigDecimal somma_costi_var_meno;
	private BigDecimal somma_costi_diff;
	private BigDecimal somma_entrata_var_piu;
	private BigDecimal somma_entrata_var_meno;
	private BigDecimal somma_entrata_diff;
	private BigDecimal somma_ricavi_var_piu;
	private BigDecimal somma_ricavi_var_meno;
	private BigDecimal somma_ricavi_diff;
	private boolean isCdsAbilitatoAdApprovare = false;
	private boolean erroreEsitaVariazioneBilancio = false;
	private boolean checkDispAssestatoCdrGAEVoceEseguito = false;
	
	private String cds_var_bil;
	private Integer es_var_bil;
	private Character ti_app_var_bil;
	private Integer pg_var_bil;

	private Var_bilancioBulk var_bilancio;

	private String desTipoVariazione;
	private String ds_cdr;
	private BulkList riepilogoSpese = new BulkList();
	private BulkList riepilogoEntrate = new BulkList();
    private String statoDocumentale;
    	   
	public String getStatoDocumentale() {
		return statoDocumentale;
	}
	public void setStatoDocumentale(String statoDocumentale) {
		this.statoDocumentale = statoDocumentale;
	}
	public Pdg_variazioneBulk() {
		super();
	}
	public boolean isPropostaProvvisoria(){
		return getStato()!=null && getStato().equals(STATO_PROPOSTA_PROVVISORIA);
	}
	public boolean isPropostaDefinitiva(){
		return getStato()!=null && getStato().equals(STATO_PROPOSTA_DEFINITIVA);
	}
	public boolean isApprovazioneFormale(){
		return getStato()!=null && getStato().equals(STATO_APPROVAZIONE_FORMALE);
	}
	public boolean isApprovata(){
		return getStato()!=null && getStato().equals(STATO_APPROVATA);		
	}
	public boolean isAnnullata(){
		return getStato()!=null && getStato().equals(STATO_ANNULLATA);		
	}
	public boolean isRespinta(){
		return getStato()!=null && getStato().equals(STATO_RESPINTA);		
	}
	/**
	 * Serve per gestire la disabilitazione dei tasti di consultazione assestato entrate/ricavi/spese/costi
	 * Ritorna TRUE se la variazione al PDG non � caricata (numero variazione pdg non assegnato) 
	 *
	 * @return Il valore della propriet� 'consultazioneAssestatoDisabled'
	 */
	public boolean isConsultazioneAssestatoDisabled() 
	{
		return getPg_variazione_pdg() == null;
	}
	public Pdg_variazioneBulk(java.lang.Integer esercizio, java.lang.Long pg_variazione_pdg) {
		super(esercizio, pg_variazione_pdg);
	}
	// metodo per inizializzare l'oggetto bulk
	private void initialize () {
		setFl_visto_dip_variazioni(new Boolean(false));
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (11/04/2005 12:34:48)
	 * @return java.util.Dictionary
	 */
	public final java.util.Dictionary getTi_statoKeys() {
		return ti_statoKeys;
	}	
	public final java.util.Dictionary getStato_invioKeys() {
		return stato_invioKeys;
	}	
	/**
	 * @return
	 */
	public final java.util.Dictionary getTi_causale_respintaKeys() {
		return ti_causale_respintaKeys;
	}
	/**
	 * @return
	 */
	public BulkList getAssociazioneCDR() {
		return associazioneCDR;
	}

	/**
	 * @param list
	 */
	public void setAssociazioneCDR(BulkList list) {
		associazioneCDR = list;
	}
	/**
	 * @return
	 */
	public BulkList getArchivioConsultazioni() {
		return archivioConsultazioni;
	}
	/**
	 * @param list
	 */
	public void setArchivioConsultazioni(BulkList list) {
		archivioConsultazioni = list;
	}
	public int addToAssociazioneCDR(Ass_pdg_variazione_cdrBulk dett) {
		dett.setPdg_variazione(this);
		getAssociazioneCDR().add(dett);
		return getAssociazioneCDR().size()-1;
	}	
	public int addToArchivioConsultazioni(Pdg_variazione_archivioBulk dett) {
		dett.setPdg_variazione(this);
		getArchivioConsultazioni().add(dett);
		return getArchivioConsultazioni().size()-1;
	}	
	public it.cnr.jada.bulk.BulkCollection[] getBulkLists() {
		return new it.cnr.jada.bulk.BulkCollection[] {getAssociazioneCDR(), getArchivioConsultazioni(), getRiepilogoSpese(), getRiepilogoEntrate()};
	}
	public Ass_pdg_variazione_cdrBulk removeFromAssociazioneCDR(int index) {
		Ass_pdg_variazione_cdrBulk dett = (Ass_pdg_variazione_cdrBulk)getAssociazioneCDR().remove(index);
		return dett;
	}
	public Pdg_variazione_archivioBulk removeFromArchivioConsultazioni(int index) {
		Pdg_variazione_archivioBulk dett = (Pdg_variazione_archivioBulk)getArchivioConsultazioni().remove(index);
		return dett;
	}
	/**
	 * Inizializza per l'inserimento i flag
	 */
	public OggettoBulk initializeForInsert(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {
		if (bp != null && bp instanceof PdGVariazioneBP && ((PdGVariazioneBP)bp).getCentro_responsabilita_scrivania() != null)
		  setCentro_responsabilita(((PdGVariazioneBP)bp).getCentro_responsabilita_scrivania());
		setEsercizio(CNRUserContext.getEsercizio(context.getUserContext()));
		return super.initializeForInsert(bp,context);
	}

	/**
	 * @return
	 */
	public CdrBulk getCentro_responsabilita() {
		return centro_responsabilita;
	}

	/**
	 * @param bulk
	 */
	public void setCentro_responsabilita(CdrBulk bulk) {
		centro_responsabilita = bulk;
	}
	/*
	 *  (non-Javadoc)
	 * @see it.cnr.contab.pdg00.bulk.Pdg_variazioneBase#setCd_centro_responsabilita(java.lang.String)
	 */
	public void setCd_centro_responsabilita(java.lang.String cd_centro_responsabilita)  {
		getCentro_responsabilita().setCd_centro_responsabilita(cd_centro_responsabilita);
	}
	/*
	 *  (non-Javadoc)
	 * @see it.cnr.contab.pdg00.bulk.Pdg_variazioneBase#getCd_centro_responsabilita()
	 */
	public java.lang.String getCd_centro_responsabilita () {
		return getCentro_responsabilita().getCd_centro_responsabilita();
	}
	/* (non-Javadoc)
	 * @see it.cnr.contab.util.ICancellatoLogicamente#isCancellatoLogicamente()
	 */
	public boolean isCancellatoLogicamente() {
		return getStato() != null && isAnnullata();
	}
	/* (non-Javadoc)
	 * @see it.cnr.contab.util.ICancellatoLogicamente#cancellaLogicamente()
	 */
	public void cancellaLogicamente() {
		if (getEsercizio()!=null)
			setDt_annullamento(DateUtils.dataContabile(EJBCommonServices.getServerDate(), getEsercizio()));
		else
			setDt_annullamento(EJBCommonServices.getServerDate());
		setStato(STATO_ANNULLATA);
	}
	/**
	 * @return
	 */
	public BigDecimal getSomma_costi_diff() {
		return somma_costi_diff;
	}

	/**
	 * @return
	 */
	public BigDecimal getSomma_costi_var_meno() {
		return somma_costi_var_meno;
	}

	/**
	 * @return
	 */
	public BigDecimal getSomma_costi_var_piu() {
		return somma_costi_var_piu;
	}

	/**
	 * @return
	 */
	public BigDecimal getSomma_entrata_diff() {
		return somma_entrata_diff;
	}

	/**
	 * @return
	 */
	public BigDecimal getSomma_entrata_var_meno() {
		return somma_entrata_var_meno;
	}

	/**
	 * @return
	 */
	public BigDecimal getSomma_entrata_var_piu() {
		return somma_entrata_var_piu;
	}

	/**
	 * @return
	 */
	public BigDecimal getSomma_ricavi_diff() {
		return somma_ricavi_diff;
	}

	/**
	 * @return
	 */
	public BigDecimal getSomma_ricavi_var_meno() {
		return somma_ricavi_var_meno;
	}

	/**
	 * @return
	 */
	public BigDecimal getSomma_ricavi_var_piu() {
		return somma_ricavi_var_piu;
	}

	/**
	 * @return
	 */
	public BigDecimal getSomma_spesa_diff() {
		return somma_spesa_diff;
	}

	/**
	 * @return
	 */
	public BigDecimal getSomma_spesa_var_meno() {
		return somma_spesa_var_meno;
	}

	/**
	 * @return
	 */
	public BigDecimal getSomma_spesa_var_piu() {
		return somma_spesa_var_piu;
	}

	/**
	 * @param decimal
	 */
	public void setSomma_costi_diff(BigDecimal decimal) {
		somma_costi_diff = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setSomma_costi_var_meno(BigDecimal decimal) {
		somma_costi_var_meno = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setSomma_costi_var_piu(BigDecimal decimal) {
		somma_costi_var_piu = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setSomma_entrata_diff(BigDecimal decimal) {
		somma_entrata_diff = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setSomma_entrata_var_meno(BigDecimal decimal) {
		somma_entrata_var_meno = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setSomma_entrata_var_piu(BigDecimal decimal) {
		somma_entrata_var_piu = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setSomma_ricavi_diff(BigDecimal decimal) {
		somma_ricavi_diff = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setSomma_ricavi_var_meno(BigDecimal decimal) {
		somma_ricavi_var_meno = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setSomma_ricavi_var_piu(BigDecimal decimal) {
		somma_ricavi_var_piu = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setSomma_spesa_diff(BigDecimal decimal) {
		somma_spesa_diff = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setSomma_spesa_var_meno(BigDecimal decimal) {
		somma_spesa_var_meno = decimal;
	}

	/**
	 * @param decimal
	 */
	public void setSomma_spesa_var_piu(BigDecimal decimal) {
		somma_spesa_var_piu = decimal;
	}
	/**
	 * @param dictionary
	 */
	public void setTi_causale_respintaKeys(java.util.Dictionary dictionary) {
		ti_causale_respintaKeys = dictionary;
	}

	/**
	 * @return
	 */
	public boolean isCdsAbilitatoAdApprovare() {
		return isCdsAbilitatoAdApprovare;
	}

	/**
	 * @param b
	 */
	public void setCdsAbilitatoAdApprovare(boolean b) {
		isCdsAbilitatoAdApprovare = b;
	}

	/**
	 * Ritorna la descrizione della variazione
	 * @return String   
	 */
	public String getDesTipoVariazione() {
		return desTipoVariazione;
	}

	/**
	 * Setta la descrizione della variazione
	 * @return String   
	 */
	public void setDesTipoVariazione(String string) {
		desTipoVariazione = string;
	}

	public final java.util.Dictionary getTi_tipologia_finKeys() {
		return ti_tipologia_finKeys;
	}	
	
	public boolean isROTipologia(){
		return !getAssociazioneCDR().isEmpty();
	}

	public boolean isROFondo_spesa(){
		return !isPropostaProvvisoria();
	}

	/*
	 * Serve per sapere se la variazione � di tipo Interna all'Istituto
	 * Ritorna un boolean con valore true se la tipologia della variazione �:
	 * 		TIPO_STORNO_SPESA_STESSO_ISTITUTO 
	 * 	    TIPO_STORNO_ENTRATA_STESSO_ISTITUTO
	 *		TIPO_VARIAZIONE_POSITIVA_STESSO_ISTITUTO
	 *      TIPO_VARIAZIONE_NEGATIVA_STESSO_ISTITUTO
	 */
	public boolean isVariazioneInternaIstituto(){
		return getTipo_variazione() != null && 
		       getTipo_variazione().isVariazioneInternaIstituto();
	}
	/*
	 * Serve per sapere se la variazione � di tipo Storno
	 * Ritorna un boolean con valore true se la tipologia della variazione �:
	 * 		TIPO_STORNO_SPESA_STESSO_ISTITUTO 
	 * 	    TIPO_STORNO_ENTRATA_STESSO_ISTITUTO
	 * 		TIPO_STORNO_SPESA_ISTITUTI_DIVERSI 
	 * 	    TIPO_STORNO_ENTRATA_ISTITUTI_DIVERSI
	 */
	public boolean isStorno(){
		return getTipo_variazione() != null && 
		       getTipo_variazione().isStorno();
	}
	/*
	 * Serve per sapere se la variazione consente di effettuare interventi sulle voci di entrata
	 * Ritorna un boolean con valore true se la tipologia della variazione �:
	 * 	    TIPO_STORNO_ENTRATA_STESSO_ISTITUTO
	 * 	    TIPO_STORNO_ENTRATA_ISTITUTI_DIVERSI
	 *		TIPO_VARIAZIONE_POSITIVA_STESSO_ISTITUTO
	 *		TIPO_VARIAZIONE_NEGATIVA_STESSO_ISTITUTO
	 *		TIPO_VARIAZIONE_POSITIVA_ISTITUTI_DIVERSI
	 *		TIPO_VARIAZIONE_NEGATIVA_ISTITUTI_DIVERSI
	 */
	public boolean isGestioneEntrateEnable(){
		return getTipo_variazione() != null && 
        	   getTipo_variazione().isTipoVariazioneEntrata();
	}

	/*
	 * Serve per sapere se la variazione consente di effettuare interventi sulle voci di spesa
	 * Ritorna un boolean con valore true se la tipologia della variazione �:
	 * 	    TIPO_STORNO_SPESA_STESSO_ISTITUTO
	 * 	    TIPO_STORNO_SPESA_ISTITUTI_DIVERSI
	 *		TIPO_VARIAZIONE_POSITIVA_STESSO_ISTITUTO
	 *		TIPO_VARIAZIONE_NEGATIVA_STESSO_ISTITUTO
	 *		TIPO_VARIAZIONE_POSITIVA_ISTITUTI_DIVERSI
	 *		TIPO_VARIAZIONE_NEGATIVA_ISTITUTI_DIVERSI
	 *		TIPO_PRELIEVO_FONDI
	 */
	public boolean isGestioneSpeseEnable(){
		return getTipo_variazione() != null && 
 	           getTipo_variazione().isTipoVariazioneSpesa();
	}

	public String getCds_var_bil() {
		return cds_var_bil;
	}
	
	public void setCds_var_bil(String cds_var_bil) {
		this.cds_var_bil = cds_var_bil;
	}
	
	public Integer getEs_var_bil() {
		return es_var_bil;
	}
	
	public void setEs_var_bil(Integer es_var_bil) {
		this.es_var_bil = es_var_bil;
	}
	
	public Integer getPg_var_bil() {
		return pg_var_bil;
	}
	
	public void setPg_var_bil(Integer pg_var_bil) {
		this.pg_var_bil = pg_var_bil;
	}
	
	public Character getTi_app_var_bil() {
		return ti_app_var_bil;
	}
	
	public void setTi_app_var_bil(Character ti_app_var_bil) {
		this.ti_app_var_bil = ti_app_var_bil;
	}
	
	public boolean isErroreEsitaVariazioneBilancio() {
		return erroreEsitaVariazioneBilancio;
	}
	
	public void setErroreEsitaVariazioneBilancio(boolean erroreEsitaVariazioneBilancio) {
		this.erroreEsitaVariazioneBilancio = erroreEsitaVariazioneBilancio;
	}

	public boolean isCheckDispAssestatoCdrGAEVoceEseguito() {
		return checkDispAssestatoCdrGAEVoceEseguito;
	}
	
	public void setCheckDispAssestatoCdrGAEVoceEseguito(
			boolean checkDispAssestatoCdrGAEVoceEseguito) {
		this.checkDispAssestatoCdrGAEVoceEseguito = checkDispAssestatoCdrGAEVoceEseguito;
	}

	public Var_bilancioBulk getVar_bilancio() {
		return var_bilancio;
	}
	
	public void setVar_bilancio(Var_bilancioBulk var_bilancio) {
		this.var_bilancio = var_bilancio;
	}
	public Elemento_voceBulk getElemento_voce() {
		return elemento_voce;
	}
	public void setElemento_voce(Elemento_voceBulk elemento_voce) {
		this.elemento_voce = elemento_voce;
	}
	public String getTi_appartenenza() {
		if (getElemento_voce()==null) return null;
		return getElemento_voce().getTi_appartenenza();
	}	
	public void setTi_appartenenza(String ti_appartenenza) {
		getElemento_voce().setTi_appartenenza(ti_appartenenza);
	}
	public String getTi_gestione() {
		if (getElemento_voce()==null) return null;
		return getElemento_voce().getTi_gestione();
	}
	public void setTi_gestione(String ti_gestione) {
		getElemento_voce().setTi_gestione(ti_gestione);
	}
	public String getCd_elemento_voce() {
		if (getElemento_voce()==null) return null;
		return getElemento_voce().getCd_elemento_voce();
	}
	public void setCd_elemento_voce(String cd_elemento_voce) {
		getElemento_voce().setCd_elemento_voce(cd_elemento_voce);
	}
	public void validate() throws ValidationException {
		super.validate();
		if (getTipo_variazione()!=null &&
			getTipo_variazione().isMovimentoSuFondi() && 
		    (getElemento_voce()==null || getElemento_voce().getCd_elemento_voce()==null))
			throw new ValidationException("Occorre valorizzare la voce di tipo 'Fondo' da utilizzare per il prelievo.");
	}
	
	public Tipo_variazioneBulk getTipo_variazione() {
		return tipo_variazione;
	}
	
	public void setTipo_variazione(Tipo_variazioneBulk tipo_variazione) {
		this.tipo_variazione = tipo_variazione;
	}

	public String getTipologia() {
		if (getTipo_variazione()==null) return null;
		return getTipo_variazione().getCd_tipo_variazione();
	}

	public void setTipologia(String tipologia) {
    	getTipo_variazione().setCd_tipo_variazione(tipologia);
    }
	public java.util.Collection getTipologie_variazione() {
		return tipologie_variazione;
	}
	
	public void setTipologie_variazione(java.util.Collection tipologie_variazione) {
		this.tipologie_variazione = tipologie_variazione;
	}
	/*
	 * Metodo che serve per conservare l'informazione, utile al component, 
	 * se la mappa � in modalit� ricerca
	 */
	public boolean isBulkforSearch() {
		return isBulkforSearch;
	}
	public void setBulkforSearch(boolean isBulkforSearch) {
		this.isBulkforSearch = isBulkforSearch;
	}
	public String getDs_cdr() {
		return ds_cdr;
	}
	public void setDs_cdr(String ds_centro_responsabilita) {
		this.ds_cdr = ds_centro_responsabilita;
	}
	public it.cnr.jada.bulk.BulkList getRiepilogoSpese() {
		return riepilogoSpese;
	}
	public void setRiepilogoSpese(it.cnr.jada.bulk.BulkList newSpeseRipartite) {
		riepilogoSpese = newSpeseRipartite;
	}

	public int addToSpeseRipartite(V_pdg_variazione_riepilogoBulk dett) {
		riepilogoSpese.add(dett);
		return riepilogoSpese.size()-1;
	}
    public V_pdg_variazione_riepilogoBulk removeFromSpeseRipartite(int index) {
    	V_pdg_variazione_riepilogoBulk dett = (V_pdg_variazione_riepilogoBulk)riepilogoSpese.remove(index);
    	return dett;
    }
	public it.cnr.jada.bulk.BulkList getRiepilogoEntrate() {
		return riepilogoEntrate;
	}
	public void setRiepilogoEntrate(it.cnr.jada.bulk.BulkList newEntrateRipartite) {
		riepilogoEntrate = newEntrateRipartite;
	}

	public int addToEntrateRipartite(V_pdg_variazione_riepilogoBulk dett) {
		riepilogoEntrate.add(dett);
		return riepilogoEntrate.size()-1;
	}
    public V_pdg_variazione_riepilogoBulk removeFromEntrateRipartite(int index) {
    	V_pdg_variazione_riepilogoBulk dett = (V_pdg_variazione_riepilogoBulk)riepilogoEntrate.remove(index);
    	return dett;
    }
}
	