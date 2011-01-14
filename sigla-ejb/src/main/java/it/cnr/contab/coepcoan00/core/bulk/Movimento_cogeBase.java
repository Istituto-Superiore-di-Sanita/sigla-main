package it.cnr.contab.coepcoan00.core.bulk;

import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class Movimento_cogeBase extends Movimento_cogeKey implements Keyed {
	// CD_TERZO DECIMAL(8,0)
	private java.lang.Integer cd_terzo;

	// CD_VOCE_EP VARCHAR(45)
	private java.lang.String cd_voce_ep;

	// DT_A_COMPETENZA_COGE TIMESTAMP
	private java.sql.Timestamp dt_a_competenza_coge;

	// DT_DA_COMPETENZA_COGE TIMESTAMP
	private java.sql.Timestamp dt_da_competenza_coge;

	// IM_MOVIMENTO DECIMAL(15,2)
	private java.math.BigDecimal im_movimento;

	// SEZIONE VARCHAR(1) NOT NULL
	private java.lang.String sezione;

	// STATO VARCHAR(1)
	private java.lang.String stato;

	// TI_ISTITUZ_COMMERC CHAR(1) NOT NULL
	private java.lang.String ti_istituz_commerc;

public Movimento_cogeBase() {
	super();
}
public Movimento_cogeBase(java.lang.String cd_cds,java.lang.Integer esercizio,java.lang.Long pg_movimento,java.lang.Long pg_scrittura) {
	super(cd_cds,esercizio,pg_movimento,pg_scrittura);
}
public Movimento_cogeBase(java.lang.String cd_cds,java.lang.String cd_unita_organizzativa,java.lang.Integer esercizio,java.lang.Long pg_movimento,java.lang.Long pg_scrittura) {
	super(cd_cds,cd_unita_organizzativa,esercizio,pg_movimento,pg_scrittura);
}
/* 
 * Getter dell'attributo cd_terzo
 */
public java.lang.Integer getCd_terzo() {
	return cd_terzo;
}
/* 
 * Getter dell'attributo cd_voce_ep
 */
public java.lang.String getCd_voce_ep() {
	return cd_voce_ep;
}
/* 
 * Getter dell'attributo dt_a_competenza_coge
 */
public java.sql.Timestamp getDt_a_competenza_coge() {
	return dt_a_competenza_coge;
}
/* 
 * Getter dell'attributo dt_da_competenza_coge
 */
public java.sql.Timestamp getDt_da_competenza_coge() {
	return dt_da_competenza_coge;
}
/* 
 * Getter dell'attributo im_movimento
 */
public java.math.BigDecimal getIm_movimento() {
	return im_movimento;
}
/* 
 * Getter dell'attributo sezione
 */
public java.lang.String getSezione() {
	return sezione;
}
/* 
 * Getter dell'attributo stato
 */
public java.lang.String getStato() {
	return stato;
}
/* 
 * Getter dell'attributo ti_istituz_commerc
 */
public java.lang.String getTi_istituz_commerc() {
	return ti_istituz_commerc;
}
/* 
 * Setter dell'attributo cd_terzo
 */
public void setCd_terzo(java.lang.Integer cd_terzo) {
	this.cd_terzo = cd_terzo;
}
/* 
 * Setter dell'attributo cd_voce_ep
 */
public void setCd_voce_ep(java.lang.String cd_voce_ep) {
	this.cd_voce_ep = cd_voce_ep;
}
/* 
 * Setter dell'attributo dt_a_competenza_coge
 */
public void setDt_a_competenza_coge(java.sql.Timestamp dt_a_competenza_coge) {
	this.dt_a_competenza_coge = dt_a_competenza_coge;
}
/* 
 * Setter dell'attributo dt_da_competenza_coge
 */
public void setDt_da_competenza_coge(java.sql.Timestamp dt_da_competenza_coge) {
	this.dt_da_competenza_coge = dt_da_competenza_coge;
}
/* 
 * Setter dell'attributo im_movimento
 */
public void setIm_movimento(java.math.BigDecimal im_movimento) {
	this.im_movimento = im_movimento;
}
/* 
 * Setter dell'attributo sezione
 */
public void setSezione(java.lang.String sezione) {
	this.sezione = sezione;
}
/* 
 * Setter dell'attributo stato
 */
public void setStato(java.lang.String stato) {
	this.stato = stato;
}
/* 
 * Setter dell'attributo ti_istituz_commerc
 */
public void setTi_istituz_commerc(java.lang.String ti_istituz_commerc) {
	this.ti_istituz_commerc = ti_istituz_commerc;
}
}
