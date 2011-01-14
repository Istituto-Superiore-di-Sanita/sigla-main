package it.cnr.contab.doccont00.core.bulk;

import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class Sospeso_det_uscKey extends OggettoBulk implements KeyedPersistent {
	// CD_CDS VARCHAR(30) NOT NULL (PK)
	private java.lang.String cd_cds;

	// ESERCIZIO DECIMAL(4,0) NOT NULL (PK)
	private java.lang.Integer esercizio;

	// PG_MANDATO DECIMAL(10,0) NOT NULL (PK)
	private java.lang.Long pg_mandato;

	// TI_SOSPESO_RISCONTRO CHAR(1) NOT NULL (PK)
	private java.lang.String ti_sospeso_riscontro;

	// TI_ENTRATA_SPESA CHAR(1) NOT NULL (PK)
	private java.lang.String ti_entrata_spesa;

	// CD_SOSPESO VARCHAR(20) NOT NULL (PK)
	private java.lang.String cd_sospeso;

public Sospeso_det_uscKey() {
	super();
}
public Sospeso_det_uscKey(java.lang.String cd_cds,java.lang.String cd_sospeso,java.lang.Integer esercizio,java.lang.Long pg_mandato,java.lang.String ti_entrata_spesa,java.lang.String ti_sospeso_riscontro) {
	this.cd_cds = cd_cds;
	this.cd_sospeso = cd_sospeso;
	this.esercizio = esercizio;
	this.pg_mandato = pg_mandato;
	this.ti_entrata_spesa = ti_entrata_spesa;
	this.ti_sospeso_riscontro = ti_sospeso_riscontro;
}
public boolean equalsByPrimaryKey(Object o) {
	if (this == o) return true;
	if (!(o instanceof Sospeso_det_uscKey)) return false;
	Sospeso_det_uscKey k = (Sospeso_det_uscKey)o;
	if(!compareKey(getCd_cds(),k.getCd_cds())) return false;
	if(!compareKey(getCd_sospeso(),k.getCd_sospeso())) return false;
	if(!compareKey(getEsercizio(),k.getEsercizio())) return false;
	if(!compareKey(getPg_mandato(),k.getPg_mandato())) return false;
	if(!compareKey(getTi_entrata_spesa(),k.getTi_entrata_spesa())) return false;
	if(!compareKey(getTi_sospeso_riscontro(),k.getTi_sospeso_riscontro())) return false;
	return true;
}
/* 
 * Getter dell'attributo cd_cds
 */
public java.lang.String getCd_cds() {
	return cd_cds;
}
/* 
 * Getter dell'attributo cd_sospeso
 */
public java.lang.String getCd_sospeso() {
	return cd_sospeso;
}
/* 
 * Getter dell'attributo esercizio
 */
public java.lang.Integer getEsercizio() {
	return esercizio;
}
/* 
 * Getter dell'attributo pg_mandato
 */
public java.lang.Long getPg_mandato() {
	return pg_mandato;
}
/* 
 * Getter dell'attributo ti_entrata_spesa
 */
public java.lang.String getTi_entrata_spesa() {
	return ti_entrata_spesa;
}
/* 
 * Getter dell'attributo ti_sospeso_riscontro
 */
public java.lang.String getTi_sospeso_riscontro() {
	return ti_sospeso_riscontro;
}
public int primaryKeyHashCode() {
	return
		calculateKeyHashCode(getCd_cds())+
		calculateKeyHashCode(getCd_sospeso())+
		calculateKeyHashCode(getEsercizio())+
		calculateKeyHashCode(getPg_mandato())+
		calculateKeyHashCode(getTi_entrata_spesa())+
		calculateKeyHashCode(getTi_sospeso_riscontro());
}
/* 
 * Setter dell'attributo cd_cds
 */
public void setCd_cds(java.lang.String cd_cds) {
	this.cd_cds = cd_cds;
}
/* 
 * Setter dell'attributo cd_sospeso
 */
public void setCd_sospeso(java.lang.String cd_sospeso) {
	this.cd_sospeso = cd_sospeso;
}
/* 
 * Setter dell'attributo esercizio
 */
public void setEsercizio(java.lang.Integer esercizio) {
	this.esercizio = esercizio;
}
/* 
 * Setter dell'attributo pg_mandato
 */
public void setPg_mandato(java.lang.Long pg_mandato) {
	this.pg_mandato = pg_mandato;
}
/* 
 * Setter dell'attributo ti_entrata_spesa
 */
public void setTi_entrata_spesa(java.lang.String ti_entrata_spesa) {
	this.ti_entrata_spesa = ti_entrata_spesa;
}
/* 
 * Setter dell'attributo ti_sospeso_riscontro
 */
public void setTi_sospeso_riscontro(java.lang.String ti_sospeso_riscontro) {
	this.ti_sospeso_riscontro = ti_sospeso_riscontro;
}
}
