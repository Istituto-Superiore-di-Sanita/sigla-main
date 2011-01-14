package it.cnr.contab.doccont00.ordine.bulk;

import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class OrdineKey extends OggettoBulk implements KeyedPersistent {
	// CD_CDS VARCHAR(30) NOT NULL (PK)
	private java.lang.String cd_cds;

	// ESERCIZIO DECIMAL(4,0) NOT NULL (PK)
	private java.lang.Integer esercizio;

	// PG_ORDINE DECIMAL(10,0) NOT NULL (PK)
	private java.lang.Long pg_ordine;

public OrdineKey() {
	super();
}
public OrdineKey(java.lang.String cd_cds,java.lang.Integer esercizio,java.lang.Long pg_ordine) {
	this.cd_cds = cd_cds;
	this.esercizio = esercizio;
	this.pg_ordine = pg_ordine;
}
public boolean equalsByPrimaryKey(Object o) {
	if (this == o) return true;
	if (!(o instanceof OrdineKey)) return false;
	OrdineKey k = (OrdineKey)o;
	if(!compareKey(getCd_cds(),k.getCd_cds())) return false;
	if(!compareKey(getEsercizio(),k.getEsercizio())) return false;
	if(!compareKey(getPg_ordine(),k.getPg_ordine())) return false;
	return true;
}
/* 
 * Getter dell'attributo cd_cds
 */
public java.lang.String getCd_cds() {
	return cd_cds;
}
/* 
 * Getter dell'attributo esercizio
 */
public java.lang.Integer getEsercizio() {
	return esercizio;
}
/* 
 * Getter dell'attributo pg_ordine
 */
public java.lang.Long getPg_ordine() {
	return pg_ordine;
}
public int primaryKeyHashCode() {
	return
		calculateKeyHashCode(getCd_cds())+
		calculateKeyHashCode(getEsercizio())+
		calculateKeyHashCode(getPg_ordine());
}
/* 
 * Setter dell'attributo cd_cds
 */
public void setCd_cds(java.lang.String cd_cds) {
	this.cd_cds = cd_cds;
}
/* 
 * Setter dell'attributo esercizio
 */
public void setEsercizio(java.lang.Integer esercizio) {
	this.esercizio = esercizio;
}
/* 
 * Setter dell'attributo pg_ordine
 */
public void setPg_ordine(java.lang.Long pg_ordine) {
	this.pg_ordine = pg_ordine;
}
}
