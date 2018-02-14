package it.cnr.contab.docamm00.tabrif.bulk;

import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.bulk.ValidationException;

import java.math.BigDecimal;
import java.util.Dictionary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(value=Include.NON_NULL)
public class Voce_ivaBulk extends Voce_ivaBase {
	protected Voce_ivaBulk voce_iva;
	protected Gruppo_ivaBulk gruppo_iva;
	protected final static java.lang.Boolean TRUE=new java.lang.Boolean (true) ;
	protected final static java.lang.Boolean FALSE=new java.lang.Boolean (false) ;

	/*
	Tipologia di applicazione del codice IVA
	Dominio:
	A  =  Solo Acquisti
	V  =  Solo Vendite
	B  =  Entrambe
	*/
	public final static Dictionary TAPPLICAZIONE;
	public final static String ACQUISTI       = "A";
	public final static String VENDITE        = "V";
	public final static String ENTRAMBE       = "E";

	static {
		TAPPLICAZIONE = new it.cnr.jada.util.OrderedHashtable();
		TAPPLICAZIONE.put(ACQUISTI,"Acquisti");
		TAPPLICAZIONE.put(VENDITE,"Vendite");
		TAPPLICAZIONE.put(ENTRAMBE,"Entrambe");
	}


	public final static String SOGGETTO_BOLLO = "S";
	public final static String NON_SOGGETTO_BOLLO = "N";
	public final static String SPECIFICA_DICITURA_BOLLO = "D";
	public final static String BENE_SERVIZIO = "*";
	public final static String SERVIZIO = "S";
	public final static String BENE = "B";

	public final static Dictionary BENI_SERVIZI;

	public final static Dictionary BOLLO;

	static {
		BOLLO = new it.cnr.jada.util.OrderedHashtable();
		BOLLO.put(NON_SOGGETTO_BOLLO,"No");
		BOLLO.put(SOGGETTO_BOLLO,"S�");
		BOLLO.put(SPECIFICA_DICITURA_BOLLO,"Specifica Dicitura");
	}
	
	static {
		BENI_SERVIZI = new it.cnr.jada.util.OrderedHashtable();
		BENI_SERVIZI.put(BENE,"Bene");
		BENI_SERVIZI.put(SERVIZIO,"Servizio");
		BENI_SERVIZI.put(BENE_SERVIZIO,"Entrambi");
	}
	
	public final static String ESCLUSE_ART_15   = "N1";
	public final static String NON_SOGGETTE    	= "N2";
	public final static String NON_IMPONIBILI  	= "N3";
	public final static String ESENTI		  	= "N4";
	public final static String REGIME_MARGINE  	= "N5";
	public final static String REVERSE_CHARGE  	= "N6";
	public final static String IVA_ALTRO_STATO  = "N7";

	
	public final static Dictionary naturaOperazioniNonImponibiliKeys;
	static {
		naturaOperazioniNonImponibiliKeys = new it.cnr.jada.util.OrderedHashtable();
		naturaOperazioniNonImponibiliKeys.put(ESCLUSE_ART_15,"Escluse ex art. 15");
		naturaOperazioniNonImponibiliKeys.put(NON_SOGGETTE,"Non Soggette");
		naturaOperazioniNonImponibiliKeys.put(NON_IMPONIBILI,"Non Imponibili");	
		naturaOperazioniNonImponibiliKeys.put(ESENTI,"Esenti");	
		naturaOperazioniNonImponibiliKeys.put(REGIME_MARGINE,"Regime del Margine");	
		naturaOperazioniNonImponibiliKeys.put(REVERSE_CHARGE,"Reverse charge");
		naturaOperazioniNonImponibiliKeys.put(IVA_ALTRO_STATO,"IVA assolta in altro stato UE (vendite a distanza ex art. 40 c. 3 e 4 e art. 41 c. 1 lett. b,  DL 331/93; prestazione di servizi di telecomunicazioni, tele-radiodiffusione ed elettronici ex art. 7-sexies lett. f, g, art. 74-sexies DPR 633/72)");
	};

 public Voce_ivaBulk() {
	super();
}
public Voce_ivaBulk(java.lang.String cd_voce_iva) {
	super(cd_voce_iva);
}
public java.lang.String getCd_gruppo_iva() {
	it.cnr.contab.docamm00.tabrif.bulk.Gruppo_ivaBulk gruppo_iva = this.getGruppo_iva();
	if (gruppo_iva == null)
		return null;
	return gruppo_iva.getCd_gruppo_iva();
}
/**
 * return Gruppo_ivaBulk
 */
public Gruppo_ivaBulk getGruppo_iva() {
	return gruppo_iva;
}
	/**
	 * Restituisce il <code>Dictionary</code> per la gestione dei tipi di applicazione.
	 *
	 * @return java.util.Dictionary
	 */

	public Dictionary getTi_applicazioneKeys() {
		return TAPPLICAZIONE;
	}
/**
 * Inizializza il ricevente per la visualizzazione in un <code>FormController</code>.
 * Questo metodo viene invocato da {@link #initializeForEdit}, {@link #initializeForInsert},
 * {@link #initializeForSearch} e {@link #initializeForFreeSearch} e pu� contenere
 * inizializzazioni comuni ai 4 stati del <code>FormController</code>
 */
protected OggettoBulk initialize(it.cnr.jada.util.action.CRUDBP bp,it.cnr.jada.action.ActionContext context) {
	voce_iva = new Voce_ivaBulk();
	gruppo_iva = new Gruppo_ivaBulk();

	/* default x la select
	if (ti_applicazione==null)
		ti_applicazione=ACQUISTI;
	*/
	if (getFl_autofattura()==null)
		setFl_autofattura(FALSE);
	if (getFl_bolla_doganale()==null)
		setFl_bolla_doganale(FALSE);
	if (getFl_default_istituzionale()==null)
		setFl_default_istituzionale(FALSE);
	if (getFl_detraibile()==null)
		setFl_detraibile(FALSE);
	if (getFl_escluso()==null)
		setFl_escluso(FALSE);
	if (getFl_esente()==null)
		setFl_esente(FALSE);
	if (getFl_intra()==null)
		setFl_intra(FALSE);
	if (getFl_non_imponibile()==null)
		setFl_non_imponibile(FALSE);
	if (getFl_non_soggetto()==null)
		setFl_non_soggetto(FALSE);
	if (getFl_iva_non_recuperabile()==null)
		setFl_iva_non_recuperabile(FALSE);
	if (getTi_bene_servizio()==null)
		setTi_bene_servizio(BENE_SERVIZIO);
	if (getFl_solo_italia()==null)
		setFl_solo_italia(FALSE);
	if (getFl_obb_dichiarazione_intento()==null)
		setFl_obb_dichiarazione_intento(FALSE);
	return this;
	
}
/**
 * Restituisce <code>true</code> se voce iva e' il default istituzionale
 *
 * @return boolean
 */
 
public boolean isDefault_istituzionale() {
	if (getFl_default_istituzionale()!=null)
		return getFl_default_istituzionale().equals(TRUE);
	else
		return false;
}
/**
 * Restituisce <code>true</code> se voce iva e' detraibile
 *
 * @return boolean
 */
 
public boolean isDetraibile() {
	if (getFl_detraibile()!=null)
		return getFl_detraibile().equals(TRUE);
	else
		return false;
}
/**
 * Insert the method's description here.
 * Creation date: (27/03/2002 11.58.38)
 * @return boolean
 */
@JsonIgnore
public boolean isROCd_gruppo_iva() {
	return getGruppo_iva().getCrudStatus() == OggettoBulk.NORMAL;
}
public void setCd_gruppo_iva(java.lang.String cd_gruppo_iva) {
	this.getGruppo_iva().setCd_gruppo_iva(cd_gruppo_iva);
}
/**
 * @param newUnita_padre it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk
 */
public void setGruppo_iva(Gruppo_ivaBulk newGruppoiva) {
	gruppo_iva = newGruppoiva;
	if (newGruppoiva!=null)
		setCd_gruppo_iva(gruppo_iva.getCd_gruppo_iva());
}

public Boolean isOperazioneNonImponibile(){
	if (getPercentuale() == null || getPercentuale().compareTo(BigDecimal.ZERO) != 0){
		return false;
	}
	return true;
}
public void validate() throws ValidationException {
    super.validate();

    if (getCd_voce_iva() == null)
        throw new ValidationException("Inserire un codice per la voce iva.");
    if (getCd_gruppo_iva() == null)
        throw new ValidationException("Selezionare un gruppo iva.");
    if (getPercentuale() == null)
    	setPercentuale(new java.math.BigDecimal(0));

	try {
	    if (getDt_inizio_validita() != null
	        && getDt_inizio_validita().after(it.cnr.jada.util.ejb.EJBCommonServices.getServerDate()))
	        throw new ValidationException("La data di inizio validit� deve essere non successiva a quella attuale");
	} catch (javax.ejb.EJBException e) {
		throw new it.cnr.jada.DetailedRuntimeException(e);
	}	
        
    // Flag attualmente non utilizzati da chiarire la gestione
    setFl_intra(FALSE);
    setFl_autofattura(FALSE);
    setFl_bolla_doganale(FALSE);
    if (getPercentuale() == null)
        setPercentuale(new java.math.BigDecimal("0"));
    if (getPercentuale_detraibilita() == null)
        setPercentuale_detraibilita(new java.math.BigDecimal("0"));
    if (getFl_default_istituzionale() == null)
        setFl_default_istituzionale(FALSE);
    try {
        if (getPercentuale_detraibilita() != null
            && (getPercentuale_detraibilita().intValue() < 0
                || getPercentuale_detraibilita().intValue() > 100))
            throw new ValidationException("Valore per la percentuale detraibilita non ammesso");
    } catch (java.lang.NumberFormatException nfe) {
        throw new ValidationException("Valore per la percentuale detraibilita non ammesso");
    }
    if (getFl_default_istituzionale().equals(TRUE)) {
        setFl_detraibile(FALSE);
        setFl_escluso(FALSE);
        setFl_esente(FALSE);
        setFl_non_soggetto(FALSE);
        setFl_non_imponibile(FALSE);
    }
    if (getPercentuale() != null
        && (getPercentuale().intValue() < 0 || getPercentuale().intValue() > 100))
        throw new ValidationException("Il campo percentuale non e' valido");
    if (getFl_detraibile().equals(FALSE))
        setPercentuale_detraibilita(java.math.BigDecimal.valueOf(0));
    if (getPercentuale() != null
        && getPercentuale().compareTo(java.math.BigDecimal.valueOf(0)) == 0
        && getFl_detraibile().equals(FALSE)
        && getFl_non_imponibile().equals(FALSE)
        && getFl_esente().equals(FALSE)
        && getFl_non_soggetto().equals(FALSE)
        && getFl_escluso().equals(FALSE)
        && getFl_default_istituzionale().equals(FALSE))
        throw new ValidationException("Non � possibile immettere una percentuale pari a 0\nsenza specificare un motivo di esenzione");
    if ((getFl_esente().equals(TRUE)
        || getFl_non_soggetto().equals(TRUE)
        || getFl_non_imponibile().equals(TRUE))
        && getPercentuale() != null
        && getPercentuale().compareTo(java.math.BigDecimal.valueOf(0)) != 0)
        throw new ValidationException("Non � possibile immettere una percentuale diversa da 0\nse si � specificato un motivo di esenzione");
    if (getFl_detraibile().equals(TRUE)
        && ((getPercentuale() != null
            && getPercentuale().compareTo(java.math.BigDecimal.valueOf(0)) == 0)
            || (getPercentuale_detraibilita() != null
                && getPercentuale_detraibilita().compareTo(java.math.BigDecimal.valueOf(0))
                    == 0)))
        throw new ValidationException("Immettere una percentuale diversa da 0");

}
public Dictionary getTi_bene_servizioKeys() {
	
	return BENI_SERVIZI;
}
public Dictionary getTi_bolloKeys() {
	
	return BOLLO;
}
public Boolean isCodiceIvaSoggettoBollo(){
	if (SOGGETTO_BOLLO.equals(getTi_bollo())){
		return true;
	}
	return false;
}
}
