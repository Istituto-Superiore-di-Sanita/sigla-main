package it.cnr.contab.doccont00.core.bulk;

import it.cnr.jada.bulk.*;
/**
 * Insert the type's description here.
 * Creation date: (20/01/2003 16.20.43)
 * @author: Simonetta Costa
 */
public class Stampa_giornale_reversaliBulk extends ReversaleBulk {

	private java.sql.Timestamp dataInizio;
	private java.sql.Timestamp dataFine;
	
	private java.lang.Long pgInizio;
	private java.lang.Long pgFine;
	
	private Boolean accreditamento= new Boolean(false);
	private Boolean reversale= new Boolean(false);
	private Boolean sospeso= new Boolean(false);
	private Boolean regolarizzazione= new Boolean(false);
	
	// Tipologie
	public final static String TIPO_TUTTI = "%";
	public final static java.util.Dictionary tipoReversaleKeys;

	// Stati
	public final static String STATO_REVERSALE_TUTTI = "%";
	public final static java.util.Dictionary statoReversaleKeys;

	// Stati trasmissione
	public final static String STATO_TRASMISSIONE_TUTTI = "%";
	public final static java.util.Dictionary statoTrasmissioneKeys;

	// Flag che indica se abilitare il tool di ricerca sulla UO Emittente
    private boolean findUOForPrintEnabled;
    
	static 
	{
		statoReversaleKeys = new it.cnr.jada.util.OrderedHashtable();
		statoReversaleKeys.put(STATO_REVERSALE_ANNULLATO,	"Annullata");
		statoReversaleKeys.put(STATO_REVERSALE_EMESSO,		"Emessa");
		statoReversaleKeys.put(STATO_REVERSALE_INCASSATO,	"Incassata");
		statoReversaleKeys.put(STATO_REVERSALE_TUTTI,		"Tutti");

		statoTrasmissioneKeys = new it.cnr.jada.util.OrderedHashtable();
		statoTrasmissioneKeys.put(STATO_TRASMISSIONE_NON_INSERITO, "Non inserita in distinta");
		statoTrasmissioneKeys.put(STATO_TRASMISSIONE_INSERITO,	   "Inserita in distinta");
		statoTrasmissioneKeys.put(STATO_TRASMISSIONE_TRASMESSO,	   "Trasmessa");
		statoTrasmissioneKeys.put(STATO_TRASMISSIONE_TUTTI,		   "Tutti");

		tipoReversaleKeys = new it.cnr.jada.util.OrderedHashtable();
		tipoReversaleKeys.put(TIPO_TRASFERIMENTO, 	"Accreditamento");
		tipoReversaleKeys.put(TIPO_INCASSO, 		"Reversale di Incasso");
		tipoReversaleKeys.put(TIPO_REGOLARIZZAZIONE,"Regolarizzazione");
		tipoReversaleKeys.put(TIPO_REGOLAM_SOSPESO,	"Sospeso");
		tipoReversaleKeys.put(TIPO_TUTTI,			"Tutti");
	}
/**
 * Stampa_giornale_reversaliBulk constructor comment.
 */
public Stampa_giornale_reversaliBulk() {
	super();
}
/**
 * Stampa_giornale_reversaliBulk constructor comment.
 * @param cd_cds java.lang.String
 * @param esercizio java.lang.Integer
 * @param pg_reversale java.lang.Long
 */
public Stampa_giornale_reversaliBulk(String cd_cds, Integer esercizio, Long pg_reversale) {
	super(cd_cds, esercizio, pg_reversale);
}
/**
 * Insert the method's description here.
 * Creation date: (20/01/2003 16.50.12)
 * @return java.sql.Timestamp
 */
public String getCdUOCRForPrint() {
	
	if (getUoEmittenteForPrint()==null)
		return "%";
	if (getUoEmittenteForPrint().getCd_unita_organizzativa()==null)
		return "%";

	return getUoEmittenteForPrint().getCd_unita_organizzativa().toString();
}
/**
 * Insert the method's description here.
 * Creation date: (20/01/2003 16.50.12)
 * @return java.sql.Timestamp
 */
public java.sql.Timestamp getDataFine() {
	return dataFine;
}
/**
 * Insert the method's description here.
 * Creation date: (20/01/2003 16.50.12)
 * @return java.sql.Timestamp
 */
public java.sql.Timestamp getDataInizio() {
	return dataInizio;
}
/**
 * Insert the method's description here.
 * Creation date: (10/06/2003 11.10.29)
 * @return java.lang.Long
 */
public java.lang.Long getPgFine() {
	return pgFine;
}
/**
 * Insert the method's description here.
 * Creation date: (10/06/2003 11.10.29)
 * @return java.lang.Long
 */
public java.lang.Long getPgInizio() {
	return pgInizio;
}
/**
 * Insert the method's description here.
 * Creation date: (20/01/2003 16.53.37)
 * @return java.util.Dictionary
 */
public java.util.Dictionary getStato_trasmissioneKeys() {
	return statoTrasmissioneKeys;
}
/**
 * Insert the method's description here.
 * Creation date: (20/01/2003 16.53.37)
 * @return java.util.Dictionary
 */
public java.util.Dictionary getStatoKeys() {
	return statoReversaleKeys;
}
/**
 * Insert the method's description here.
 * Creation date: (20/01/2003 16.53.37)
 * @return java.util.Dictionary
 */
public java.util.Dictionary getTipoReversaleKeys() {
	return tipoReversaleKeys;
}
/**
 * Insert the method's description here.
 * Creation date: (20/01/2003 16.50.12)
 * @return java.sql.Timestamp
 */
public it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk getUoEmittenteForPrint() {
	return super.getUnita_organizzativa();
}
/**
 * Insert the method's description here.
 * Creation date: (20/12/2002 10.47.40)
 * @param newCdUOEmittente java.lang.String
 */
public boolean isROCdUOEmittenteForPrint() {
	return getUoEmittenteForPrint()==null || getUoEmittenteForPrint().getCrudStatus()==NORMAL;
}
/**
 * Insert the method's description here.
 * Creation date: (28/03/2003 10.47.13)
 * @return boolean
 */
public boolean isUOForPrintEnabled() {
	return findUOForPrintEnabled;
}
/**
 * Insert the method's description here.
 * Creation date: (20/01/2003 16.50.12)
 * @param newDataFine java.sql.Timestamp
 */
public void setDataFine(java.sql.Timestamp newDataFine) {
	dataFine = newDataFine;
}
/**
 * Insert the method's description here.
 * Creation date: (20/01/2003 16.50.12)
 * @param newDataInizio java.sql.Timestamp
 */
public void setDataInizio(java.sql.Timestamp newDataInizio) {
	dataInizio = newDataInizio;
}
/**
 * Insert the method's description here.
 * Creation date: (28/03/2003 10.47.13)
 * @param newFindUOForPrintEnabled boolean
 */
public void setFindUOForPrintEnabled(boolean newFindUOForPrintEnabled) {
	findUOForPrintEnabled = newFindUOForPrintEnabled;
}
/**
 * Insert the method's description here.
 * Creation date: (10/06/2003 11.10.29)
 * @param newPgFine java.lang.Long
 */
public void setPgFine(java.lang.Long newPgFine) {
	pgFine = newPgFine;
}
/**
 * Insert the method's description here.
 * Creation date: (10/06/2003 11.10.29)
 * @param newPgInizio java.lang.Long
 */
public void setPgInizio(java.lang.Long newPgInizio) {
	pgInizio = newPgInizio;
}
/**
 * Insert the method's description here.
 * Creation date: (20/01/2003 16.50.12)
 * @return java.sql.Timestamp
 */
public void setUoEmittenteForPrint(it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk newUO) {
	super.setUnita_organizzativa(newUO);
}
/**
 * Metodo con cui si verifica la validit� di alcuni campi, mediante un 
 * controllo sintattico o contestuale.
 */

public void validate() throws it.cnr.jada.bulk.ValidationException {
	if(!accreditamento.booleanValue() && !reversale.booleanValue()&&
	   !regolarizzazione.booleanValue() && !sospeso.booleanValue()	)
		throw new it.cnr.jada.bulk.ValidationException("Specificare un tipo mandato!");
}
public Boolean getAccreditamento() {
	return accreditamento;
}
public void setAccreditamento(Boolean accreditamento) {
	this.accreditamento = accreditamento;
}
public Boolean getReversale() {
	return reversale;
}
public void setReversale(Boolean reversale) {
	this.reversale = reversale;
}
public Boolean getRegolarizzazione() {
	return regolarizzazione;
}
public void setRegolarizzazione(Boolean regolarizzazione) {
	this.regolarizzazione = regolarizzazione;
}
public Boolean getSospeso() {
	return sospeso;
}
public void setSospeso(Boolean sospeso) {
	this.sospeso = sospeso;
}
}
