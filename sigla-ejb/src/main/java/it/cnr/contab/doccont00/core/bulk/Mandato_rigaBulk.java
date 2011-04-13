package it.cnr.contab.doccont00.core.bulk;

import it.cnr.contab.anagraf00.core.bulk.*;
import it.cnr.contab.anagraf00.tabrif.bulk.Rif_termini_pagamentoBulk;

import java.math.BigDecimal;
import java.util.*;

import it.cnr.contab.config00.bulk.Codici_siopeBulk;
import it.cnr.contab.config00.pdcfin.bulk.Elemento_voceBulk;
import it.cnr.contab.docamm00.docs.bulk.*;
import it.cnr.contab.util.Utility;
import it.cnr.jada.bulk.BulkCollection;
import it.cnr.jada.bulk.BulkList;

public abstract class Mandato_rigaBulk extends Mandato_rigaBase {

	protected BancaBulk banca = new BancaBulk();
	protected Modalita_pagamentoBulk modalita_pagamento = new Modalita_pagamentoBulk();
	protected List modalita_pagamentoOptions;
	protected List bancaOptions;
	protected String ti_fattura;
	protected TerzoBulk terzo_cedente;
	protected Elemento_voceBulk elemento_voce;
	protected BulkList mandato_siopeColl = new BulkList();
	protected BulkList codici_siopeColl = new BulkList();
	protected BulkList mandatoCupColl = new BulkList();
	static private java.util.Hashtable fatturaPassivaKeys;
	static private java.util.Hashtable fatturaAttivaKeys;		
	
	static 
	{
		fatturaPassivaKeys = new Hashtable();
		fatturaPassivaKeys.put(it.cnr.contab.docamm00.docs.bulk.Fattura_passivaBulk.TIPO_FATTURA_PASSIVA,	"Fattura Passiva");
		fatturaPassivaKeys.put(it.cnr.contab.docamm00.docs.bulk.Fattura_passivaBulk.TIPO_NOTA_DI_CREDITO,	"Nota di credito");
		fatturaPassivaKeys.put(it.cnr.contab.docamm00.docs.bulk.Fattura_passivaBulk.TIPO_NOTA_DI_DEBITO,	"Nota di debito");		
	};

	static 
	{
		fatturaAttivaKeys = new Hashtable();
		fatturaAttivaKeys.put(it.cnr.contab.docamm00.docs.bulk.Fattura_attivaBulk.TIPO_FATTURA_ATTIVA,	"Fattura Attiva");
		fatturaAttivaKeys.put(it.cnr.contab.docamm00.docs.bulk.Fattura_attivaBulk.TIPO_NOTA_DI_CREDITO,	"Nota di credito");
		fatturaAttivaKeys.put(it.cnr.contab.docamm00.docs.bulk.Fattura_attivaBulk.TIPO_NOTA_DI_DEBITO,	"Nota di debito");		
	};

	public final static String SIOPE_TOTALMENTE_ASSOCIATO = "T";
	public final static String SIOPE_PARZIALMENTE_ASSOCIATO = "P";
	public final static String SIOPE_NON_ASSOCIATO = "N";
	
	public final static String STATO_INIZIALE 		= "E";
	public final static String STATO_ANNULLATO 		= "A";
	public final static String STATO_PARZ_PAGATO 	= "L";
	public final static String STATO_PAGATO 		= "P";		
	
	final static public java.util.Dictionary classeDiPagamentoKeys = it.cnr.contab.anagraf00.tabrif.bulk.Rif_modalita_pagamentoBulk.TI_PAGAMENTO_KEYS;
	final static public java.util.Dictionary statoKeys;

	static 
	{
		statoKeys = new it.cnr.jada.util.OrderedHashtable();
		statoKeys.put(STATO_ANNULLATO,	"Annullato");
		statoKeys.put(STATO_INIZIALE,	"Emesso");
		statoKeys.put(STATO_PARZ_PAGATO,"Parz.Pagato");
		statoKeys.put(STATO_PAGATO,		"Pagato");
	};	

	static private java.util.Hashtable fl_pgiro_Keys;
	private boolean fl_aggiorna_saldi_per_annullamento = false;
	private Long pg_ver_rec_doc_amm;
	
/**
 * Mandato_rigaBulk constructor comment.
 */
public Mandato_rigaBulk() {
	super();
}
public Mandato_rigaBulk(java.lang.String cd_cds,java.lang.String cd_cds_doc_amm,java.lang.String cd_tipo_documento_amm,java.lang.String cd_uo_doc_amm,java.lang.Integer esercizio,java.lang.Integer esercizio_doc_amm,java.lang.Integer esercizio_obbligazione,java.lang.Long pg_doc_amm,java.lang.Long pg_mandato,java.lang.Integer esercizio_ori_obbligazione,java.lang.Long pg_obbligazione,java.lang.Long pg_obbligazione_scadenzario) {
	super(cd_cds,cd_cds_doc_amm,cd_tipo_documento_amm,cd_uo_doc_amm,esercizio,esercizio_doc_amm,esercizio_obbligazione,pg_doc_amm,pg_mandato,esercizio_ori_obbligazione,pg_obbligazione,pg_obbligazione_scadenzario);
}
public void annulla() 
{
	if ( STATO_ANNULLATO.equals( getStato()))
		return;
	setStato( STATO_ANNULLATO );
	setToBeUpdated();
//	setIm_mandato_riga(new java.math.BigDecimal(0));
	setFl_aggiorna_saldi_per_annullamento( true );
}
/**
 * @return it.cnr.contab.anagraf00.core.bulk.BancaBulk
 */
public it.cnr.contab.anagraf00.core.bulk.BancaBulk getBanca() {
	return banca;
}
/**
 * @return java.util.List
 */
public java.util.List getBancaOptions() {
	return bancaOptions;
}
public java.lang.String getCd_cds() {
	it.cnr.contab.doccont00.core.bulk.MandatoBulk mandato = this.getMandato();
	if (mandato == null)
		return null;
	it.cnr.contab.config00.sto.bulk.CdsBulk cds = mandato.getCds();
	if (cds == null)
		return null;
	return cds.getCd_unita_organizzativa();
}
public java.lang.String getCd_modalita_pag() {
	it.cnr.contab.anagraf00.core.bulk.Modalita_pagamentoBulk modalita_pagamento = this.getModalita_pagamento();
	if (modalita_pagamento == null)
		return null;
	it.cnr.contab.anagraf00.tabrif.bulk.Rif_modalita_pagamentoBulk rif_modalita_pagamento = modalita_pagamento.getRif_modalita_pagamento();
	if (rif_modalita_pagamento == null)
		return null;
	return rif_modalita_pagamento.getCd_modalita_pag();
}
public java.lang.Integer getCd_terzo() {
	it.cnr.contab.anagraf00.core.bulk.BancaBulk banca = this.getBanca();
	if (banca == null)
		return null;
	it.cnr.contab.anagraf00.core.bulk.TerzoBulk terzo = banca.getTerzo();
	if (terzo == null)
		return null;
	return terzo.getCd_terzo();
}
public java.lang.Integer getCd_terzo_cedente() {
	it.cnr.contab.anagraf00.core.bulk.TerzoBulk terzo_cedente = this.getTerzo_cedente();
	if (terzo_cedente == null)
		return null;
	return terzo_cedente.getCd_terzo();
}
/**
 * Metodo con cui si ottiene il valore della variabile <code>classeDiPagamentoKeys</code>
 * di tipo <code>Hashtable</code>.
 * In particolare, questo metodo carica in una Hashtable l'elenco dei possibili valori 
 * che pu� assumere il campo <code>ti_pagamento</code>.
 * @return java.util.Hashtable classeDiPagamentoKeys I valori del campo <code>ti_pagamento</code>.
 */
public java.util.Dictionary getClasseDiPagamentoKeys() 
{
	return classeDiPagamentoKeys;
}
public java.lang.String getDs_tipo_documento_amm() 
{
	if ( Numerazione_doc_ammBulk.TIPO_FATTURA_PASSIVA.equals( getCd_tipo_documento_amm()))
		return (String) getFatturaPassivaKeys().get( getTi_fattura() );
	else if ( Numerazione_doc_ammBulk.TIPO_FATTURA_ATTIVA.equals( getCd_tipo_documento_amm()))	
		return (String) getFatturaAttivaKeys().get( getTi_fattura() );
	return (String) getTipoDocumentoKeys().get( getCd_tipo_documento_amm() );
}
public java.lang.Integer getEsercizio() {
	it.cnr.contab.doccont00.core.bulk.MandatoBulk mandato = this.getMandato();
	if (mandato == null)
		return null;
	return mandato.getEsercizio();
}
/**
 * Insert the method's description here.
 * Creation date: (22/07/2002 12.00.35)
 * @return java.util.Hashtable
 */
public static java.util.Hashtable getFatturaAttivaKeys() {
	return fatturaAttivaKeys;
}
/**
 * Insert the method's description here.
 * Creation date: (22/07/2002 12.00.35)
 * @return java.util.Hashtable
 */
public static java.util.Hashtable getFatturaPassivaKeys() {
	return fatturaPassivaKeys;
}
/**
 * Metodo con cui si ottiene il valore della variabile <code>fl_pgiro_Keys</code>
 * di tipo <code>Hashtable</code>.
 * In particolare, questo metodo carica in una Hashtable l'elenco dei possibili valori 
 * che pu� assumere il flag <code>fl_pgiro</code>.
 * @return java.util.Hashtable fl_pgiro_Keys I valori del flag <code>fl_pgiro</code>.
 */
public java.util.Hashtable getFl_pgiro_Keys() {
	if ( fl_pgiro_Keys == null )
	{
		fl_pgiro_Keys = new Hashtable();
		fl_pgiro_Keys.put(new Boolean(true), "Y");
		fl_pgiro_Keys.put(new Boolean(false), "N");
	}	
	return fl_pgiro_Keys;
}
/**
 * @return it.cnr.contab.doccont00.core.bulk.MandatoBulk
 */
public abstract MandatoBulk getMandato() ;
/**
 * @return it.cnr.contab.anagraf00.core.bulk.Modalita_pagamentoBulk
 */
public it.cnr.contab.anagraf00.core.bulk.Modalita_pagamentoBulk getModalita_pagamento() {
	return modalita_pagamento;
}
/**
 * @return java.util.List
 */
public java.util.List getModalita_pagamentoOptions() {
	return modalita_pagamentoOptions;
}
public java.lang.Long getPg_banca() {
	it.cnr.contab.anagraf00.core.bulk.BancaBulk banca = this.getBanca();
	if (banca == null)
		return null;
	return banca.getPg_banca();
}
public java.lang.Long getPg_mandato() {
	it.cnr.contab.doccont00.core.bulk.MandatoBulk mandato = this.getMandato();
	if (mandato == null)
		return null;
	return mandato.getPg_mandato();
}
/**
 * Insert the method's description here.
 * Creation date: (03/06/2003 20:49:41)
 * @return java.lang.Long
 */
public java.lang.Long getPg_ver_rec_doc_amm() {
	return pg_ver_rec_doc_amm;
}
public java.util.Dictionary getStatoKeys() {
	return statoKeys;
}
/**
 * Insert the method's description here.
 * Creation date: (27/02/2003 10.14.13)
 * @return it.cnr.contab.anagraf00.core.bulk.TerzoBulk
 */
public it.cnr.contab.anagraf00.core.bulk.TerzoBulk getTerzo_cedente() {
	return terzo_cedente;
}
/**
 * Insert the method's description here.
 * Creation date: (22/07/2002 11.12.03)
 * @return java.lang.String
 */
public java.lang.String getTi_fattura() {
	return ti_fattura;
}
/**
 * Metodo con cui si ottiene il valore della variabile <code>tipoDocumentoKeys</code>
 * di tipo <code>Hashtable</code>.
 * @return java.util.Hashtable tipoDocumentoKeys
 */
public java.util.Dictionary getTipoDocumentoKeys() {
//	return V_doc_passivo_obbligazioneBulk.tipoDocumentoKeys;
	if ( getMandato() != null )
		return getMandato().getTipoDocumentoKeys();
	return null;
}
public boolean isFl_aggiorna_saldi_per_annullamento() {
	return fl_aggiorna_saldi_per_annullamento;
}
public boolean isGestioneAConsumo() 
{
	return getFl_pgiro().booleanValue() ||  getMandato().isMandatoAccreditamentoBulk();
}
public boolean isROBanca() {
	return !(STATO_INIZIALE.equals(getStato()));
}
public boolean isROIm_mandato_riga() {
	return !(STATO_INIZIALE.equals(getStato()));
}
/**
 * @param newBanca it.cnr.contab.anagraf00.core.bulk.BancaBulk
 */
public void setBanca(it.cnr.contab.anagraf00.core.bulk.BancaBulk newBanca) {
	banca = newBanca;
}
/**
 * @param newBancaOptions java.util.List
 */
public void setBancaOptions(java.util.List newBancaOptions) {
	bancaOptions = newBancaOptions;
}
public void setCd_cds(java.lang.String cd_cds) {
	this.getMandato().getCds().setCd_unita_organizzativa(cd_cds);
}
public void setCd_modalita_pag(java.lang.String cd_modalita_pag) {
	this.getModalita_pagamento().getRif_modalita_pagamento().setCd_modalita_pag(cd_modalita_pag);
}
public void setCd_terzo(java.lang.Integer cd_terzo) {
	this.getBanca().getTerzo().setCd_terzo(cd_terzo);
}
public void setCd_terzo_cedente(java.lang.Integer cd_terzo_cedente) {
	this.getTerzo_cedente().setCd_terzo(cd_terzo_cedente);
}
public void setEsercizio(java.lang.Integer esercizio) {
	this.getMandato().setEsercizio(esercizio);
}
public void setFl_aggiorna_saldi_per_annullamento(boolean newFl_aggiorna_saldi_per_annullamento) {
	fl_aggiorna_saldi_per_annullamento = newFl_aggiorna_saldi_per_annullamento;
}
/**
 * @param newMandato it.cnr.contab.doccont00.core.bulk.MandatoBulk
 */
public abstract void setMandato(MandatoBulk newMandato) ;
/**
 * @param newModalita_pagamento it.cnr.contab.anagraf00.core.bulk.Modalita_pagamentoBulk
 */
public void setModalita_pagamento(it.cnr.contab.anagraf00.core.bulk.Modalita_pagamentoBulk newModalita_pagamento) {
	modalita_pagamento = newModalita_pagamento;
}
/**
 * @param newModalita_pagamentoOptions java.util.List
 */
public void setModalita_pagamentoOptions(java.util.List newModalita_pagamentoOptions) {
	modalita_pagamentoOptions = newModalita_pagamentoOptions;
}
public void setPg_banca(java.lang.Long pg_banca) {
	this.getBanca().setPg_banca(pg_banca);
}
public void setPg_mandato(java.lang.Long pg_mandato) {
	this.getMandato().setPg_mandato(pg_mandato);
}
/**
 * Insert the method's description here.
 * Creation date: (03/06/2003 20:49:41)
 * @param newPg_ver_rec_doc_amm java.lang.Long
 */
public void setPg_ver_rec_doc_amm(java.lang.Long newPg_ver_rec_doc_amm) {
	pg_ver_rec_doc_amm = newPg_ver_rec_doc_amm;
}
/**
 * Insert the method's description here.
 * Creation date: (27/02/2003 10.14.13)
 * @param newTerzo_cedente it.cnr.contab.anagraf00.core.bulk.TerzoBulk
 */
public void setTerzo_cedente(it.cnr.contab.anagraf00.core.bulk.TerzoBulk newTerzo_cedente) {
	terzo_cedente = newTerzo_cedente;
}
/**
 * Insert the method's description here.
 * Creation date: (22/07/2002 11.12.03)
 * @param newTi_fattura java.lang.String
 */
public void setTi_fattura(java.lang.String newTi_fattura) {
	ti_fattura = newTi_fattura;
}
/**
 * @return it.cnr.jada.bulk.BulkList
 */
public it.cnr.jada.bulk.BulkList getMandato_siopeColl() {
	return mandato_siopeColl;
}
/**
 * @param newMandato_siopeColl it.cnr.jada.bulk.BulkList
 */
public void setMandato_siopeColl(it.cnr.jada.bulk.BulkList newMandato_siopeColl) {
	mandato_siopeColl = newMandato_siopeColl;
}
/**
 * @return it.cnr.jada.bulk.BulkList
 */
public it.cnr.jada.bulk.BulkList getCodici_siopeColl() {
	return codici_siopeColl;
}
/**
 * @param newCodici_siopeColl it.cnr.jada.bulk.BulkList
 */
public void setCodici_siopeColl(it.cnr.jada.bulk.BulkList newCodici_siopeColl) {
	codici_siopeColl = newCodici_siopeColl;
}
/**
 * Restituisce un array di <code>BulkCollection</code> contenenti oggetti
 * bulk da rendere persistenti insieme al ricevente.
 * L'implementazione standard restituisce <code>null</code>.
 * @see it.cnr.jada.comp.GenericComponent#makeBulkPersistent
 */ 
public BulkCollection[] getBulkLists() {
	 return new it.cnr.jada.bulk.BulkCollection[] { mandato_siopeColl,mandatoCupColl };

}
/**
 * Aggiunge un nuovo dettaglio (Mandato_rigaBulk) alla lista di dettagli definiti per il mandato
 * inizializzandone alcuni campi
 * @param mr dettaglio da aggiungere alla lista
 * @return int
 */
public int addToMandato_siopeColl( Mandato_siopeBulk mandato_siope ) 
{
	mandato_siopeColl.add(mandato_siope);
	mandato_siope.setMandato_riga(this);
	if (mandato_siope.getImporto()==null)
		mandato_siope.setImporto(new java.math.BigDecimal(0));
	return mandato_siopeColl.size()-1;
}
/**
 * Metodo per l'eliminazione di un elemento <code>Mandato_siopeBulk</code> dalla <code>Collection</code>
 * delle righe del mandato.
 * @param index L'indice per scorrere la collezione dei codici siope legati alla riga del mandato.
 * @return Mandato_siopeBulk La riga da rimuovere.
 */
public Mandato_siopeBulk removeFromMandato_siopeColl(int index) 
{
	Mandato_siopeBulk mandato_siope = (Mandato_siopeBulk)mandato_siopeColl.remove(index);
	addToCodici_siopeColl(mandato_siope.getCodice_siope());
	return mandato_siope;
}
/**
 * Aggiunge un nuovo dettaglio (Codici_siopeBulk) alla lista dei codici siope collegabili alla riga del mandato
 * @param mr dettaglio da aggiungere alla lista
 * @return int
 */
public int addToCodici_siopeColl( Codici_siopeBulk codice_siope ) 
{
	codici_siopeColl.add(codice_siope);
	return codici_siopeColl.size()-1;
}
/**
 * Metodo per l'eliminazione di un elemento <code>Codici_siopeBulk</code> dalla <code>Collection</code>
 * dei codici siope collegabili alla riga del mandato.
 * @param index L'indice per scorrere la collezione dei codici siope legati alla riga del mandato.
 * @return Codici_siopeBulk La riga da rimuovere.
 */
public Codici_siopeBulk removeFromCodici_siopeColl(int index) 
{
	Codici_siopeBulk codice_siope = (Codici_siopeBulk)codici_siopeColl.remove(index);
	Mandato_siopeBulk mandato_siope = new Mandato_siopeIBulk();
	mandato_siope.setCodice_siope(codice_siope);
	mandato_siope.setToBeCreated();
	addToMandato_siopeColl(mandato_siope);
	return codice_siope;
}
public BigDecimal getIm_associato_siope(){
	BigDecimal totale = Utility.ZERO;
	for (Iterator i = getMandato_siopeColl().iterator(); i.hasNext();) totale = totale.add(((Mandato_siopeBulk)i.next()).getImporto());
	return Utility.nvl(totale);
}
public BigDecimal getIm_da_associare_siope(){
	return Utility.nvl(getIm_mandato_riga()).subtract(Utility.nvl(getIm_associato_siope()));
}
public Elemento_voceBulk getElemento_voce() {
	return elemento_voce;
}
public void setElemento_voce(Elemento_voceBulk elemento_voce) {
	this.elemento_voce = elemento_voce;
}
/*
 * Ritorna l'informazione circa la totale o parziale associazione della
 * riga a codici SIOPE.
 * 
 * return
 * 	"T" = SIOPE_TOTALMENTE_ASSOCIATO
 *  "P" = SIOPE_PARZIALMENTE_ASSOCIATO
 *  "N" = SIOPE_NON_ASSOCIATO
 * 		
 */
public String getTipoAssociazioneSiope() {
	BigDecimal totSiope = getIm_associato_siope();
	if (getIm_da_associare_siope().compareTo(Utility.ZERO)==0) return SIOPE_TOTALMENTE_ASSOCIATO;
	if (totSiope.compareTo(Utility.ZERO)==0) return SIOPE_NON_ASSOCIATO;
	return SIOPE_PARZIALMENTE_ASSOCIATO;
}
public BulkList getMandatoCupColl() {
	return mandatoCupColl;
}
public void setMandatoCupColl(BulkList mandatoCupColl) {
	this.mandatoCupColl = mandatoCupColl;
}
/**
 * Aggiunge un nuovo dettaglio (MandatoCupBulk) alla lista di dettagli definiti per il mandato
 * inizializzandone alcuni campi
 * @param mr dettaglio da aggiungere alla lista
 * @return int
 */
public int addToMandatoCupColl( MandatoCupBulk mandato_cup ) 
{
	mandatoCupColl.add(mandato_cup);
	mandato_cup.setMandato_riga(this);
	if (mandato_cup.getImporto()==null) 
		mandato_cup.setImporto(this.getIm_mandato_riga());
	return mandatoCupColl.size()-1;
}
/**
 * Metodo per l'eliminazione di un elemento <code>MandatoCupBulk</code> dalla <code>Collection</code>
 * delle righe del mandato.
 * @param index L'indice per scorrere la collezione dei codici cup legati alla riga del mandato.
 * @return MandatoCupBulk La riga da rimuovere.
 */
public MandatoCupBulk removeFromMandatoCupColl(int index) 
{
	MandatoCupBulk mandato_cup = (MandatoCupBulk)mandatoCupColl.remove(index);
	
	return mandato_cup;
}
public BigDecimal getIm_associato_cup(){
	BigDecimal totale = Utility.ZERO;
	for (Iterator i = getMandatoCupColl().iterator(); i.hasNext();) totale = totale.add(((MandatoCupBulk)i.next()).getImporto());
	return Utility.nvl(totale);
}
public BigDecimal getIm_da_associare_cup(){
	return Utility.nvl(getIm_mandato_riga()).subtract(Utility.nvl(getIm_associato_cup()));
}
/*
 * Ritorna l'informazione circa la totale o parziale associazione della
 * riga a codici CUP.
 * 
 * return
 * 	"T" = SIOPE_TOTALMENTE_ASSOCIATO
 *  "P" = SIOPE_PARZIALMENTE_ASSOCIATO
 *  "N" = SIOPE_NON_ASSOCIATO
 * 		
 */
public String getTipoAssociazioneCup() {
	BigDecimal totCup = getIm_associato_cup();
	if (getIm_da_associare_cup().compareTo(Utility.ZERO)==0) return SIOPE_TOTALMENTE_ASSOCIATO;
	if (totCup.compareTo(Utility.ZERO)==0) return SIOPE_NON_ASSOCIATO;
	return SIOPE_PARZIALMENTE_ASSOCIATO;
}
@Override
public String getDs_mandato_riga() {
	return super.getDs_mandato_riga();
}
}
