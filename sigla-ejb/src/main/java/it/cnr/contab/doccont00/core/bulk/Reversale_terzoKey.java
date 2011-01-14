package it.cnr.contab.doccont00.core.bulk;

import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class Reversale_terzoKey extends OggettoBulk implements KeyedPersistent {
	// CD_CDS VARCHAR(30) NOT NULL (PK)
	private java.lang.String cd_cds;

	// ESERCIZIO DECIMAL(4,0) NOT NULL (PK)
	private java.lang.Integer esercizio;

	// PG_REVERSALE DECIMAL(10,0) NOT NULL (PK)
	private java.lang.Long pg_reversale;

	// CD_TERZO DECIMAL(8,0) NOT NULL (PK)
	private java.lang.Integer cd_terzo;

public Reversale_terzoKey() {
	super();
}
public Reversale_terzoKey(java.lang.String cd_cds,java.lang.Integer cd_terzo,java.lang.Integer esercizio,java.lang.Long pg_reversale) {
	super();
	this.cd_cds = cd_cds;
	this.cd_terzo = cd_terzo;
	this.esercizio = esercizio;
	this.pg_reversale = pg_reversale;
}
public boolean equals(Object o) {
	if (this == o) return true;
	if (!(o instanceof Reversale_terzoKey)) return false;
	Reversale_terzoKey k = (Reversale_terzoKey)o;
	if(!compareKey(getCd_cds(),k.getCd_cds())) return false;
	if(!compareKey(getCd_terzo(),k.getCd_terzo())) return false;
	if(!compareKey(getEsercizio(),k.getEsercizio())) return false;
	if(!compareKey(getPg_reversale(),k.getPg_reversale())) return false;
	return true;
}
public boolean equalsByPrimaryKey(Object o) {
	if (this == o) return true;
	if (!(o instanceof Reversale_terzoKey)) return false;
	Reversale_terzoKey k = (Reversale_terzoKey)o;
	if(!compareKey(getCd_cds(),k.getCd_cds())) return false;
	if(!compareKey(getCd_terzo(),k.getCd_terzo())) return false;
	if(!compareKey(getEsercizio(),k.getEsercizio())) return false;
	if(!compareKey(getPg_reversale(),k.getPg_reversale())) return false;
	return true;
}
/* 
 * Getter dell'attributo cd_cds
 */
public java.lang.String getCd_cds() {
	return cd_cds;
}
/* 
 * Getter dell'attributo cd_terzo
 */
public java.lang.Integer getCd_terzo() {
	return cd_terzo;
}
/* 
 * Getter dell'attributo esercizio
 */
public java.lang.Integer getEsercizio() {
	return esercizio;
}
/* 
 * Getter dell'attributo pg_reversale
 */
public java.lang.Long getPg_reversale() {
	return pg_reversale;
}
public int hashCode() {
	return
		calculateKeyHashCode(getCd_cds())+
		calculateKeyHashCode(getCd_terzo())+
		calculateKeyHashCode(getEsercizio())+
		calculateKeyHashCode(getPg_reversale());
}
public int primaryKeyHashCode() {
	return
		calculateKeyHashCode(getCd_cds())+
		calculateKeyHashCode(getCd_terzo())+
		calculateKeyHashCode(getEsercizio())+
		calculateKeyHashCode(getPg_reversale());
}
/* 
 * Setter dell'attributo cd_cds
 */
public void setCd_cds(java.lang.String cd_cds) {
	this.cd_cds = cd_cds;
}
/* 
 * Setter dell'attributo cd_terzo
 */
public void setCd_terzo(java.lang.Integer cd_terzo) {
	this.cd_terzo = cd_terzo;
}
/* 
 * Setter dell'attributo esercizio
 */
public void setEsercizio(java.lang.Integer esercizio) {
	this.esercizio = esercizio;
}
/* 
 * Setter dell'attributo pg_reversale
 */
public void setPg_reversale(java.lang.Long pg_reversale) {
	this.pg_reversale = pg_reversale;
}
}
