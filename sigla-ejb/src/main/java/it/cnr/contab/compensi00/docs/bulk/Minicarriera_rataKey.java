package it.cnr.contab.compensi00.docs.bulk;

import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class Minicarriera_rataKey extends OggettoBulk implements KeyedPersistent {
	// CD_CDS VARCHAR(30) NOT NULL (PK)
	private java.lang.String cd_cds;

	// CD_UNITA_ORGANIZZATIVA VARCHAR(30) NOT NULL (PK)
	private java.lang.String cd_unita_organizzativa;

	// ESERCIZIO DECIMAL(4,0) NOT NULL (PK)
	private java.lang.Integer esercizio;

	// PG_MINICARRIERA DECIMAL(10,0) NOT NULL (PK)
	private java.lang.Long pg_minicarriera;

	// PG_RATA DECIMAL(10,0) NOT NULL (PK)
	private java.lang.Long pg_rata;

public Minicarriera_rataKey() {
	super();
}
public Minicarriera_rataKey(java.lang.String cd_cds,java.lang.String cd_unita_organizzativa,java.lang.Integer esercizio,java.lang.Long pg_minicarriera,java.lang.Long pg_rata) {
	this.cd_cds = cd_cds;
	this.cd_unita_organizzativa = cd_unita_organizzativa;
	this.esercizio = esercizio;
	this.pg_minicarriera = pg_minicarriera;
	this.pg_rata = pg_rata;
}
public boolean equalsByPrimaryKey(Object o) {
	if (this == o) return true;
	if (!(o instanceof Minicarriera_rataKey)) return false;
	Minicarriera_rataKey k = (Minicarriera_rataKey)o;
	if(!compareKey(getCd_cds(),k.getCd_cds())) return false;
	if(!compareKey(getCd_unita_organizzativa(),k.getCd_unita_organizzativa())) return false;
	if(!compareKey(getEsercizio(),k.getEsercizio())) return false;
	if(!compareKey(getPg_minicarriera(),k.getPg_minicarriera())) return false;
	if(!compareKey(getPg_rata(),k.getPg_rata())) return false;
	return true;
}
/* 
 * Getter dell'attributo cd_cds
 */
public java.lang.String getCd_cds() {
	return cd_cds;
}
/* 
 * Getter dell'attributo cd_unita_organizzativa
 */
public java.lang.String getCd_unita_organizzativa() {
	return cd_unita_organizzativa;
}
/* 
 * Getter dell'attributo esercizio
 */
public java.lang.Integer getEsercizio() {
	return esercizio;
}
/* 
 * Getter dell'attributo pg_minicarriera
 */
public java.lang.Long getPg_minicarriera() {
	return pg_minicarriera;
}
/* 
 * Getter dell'attributo pg_rata
 */
public java.lang.Long getPg_rata() {
	return pg_rata;
}
public int primaryKeyHashCode() {
	return
		calculateKeyHashCode(getCd_cds())+
		calculateKeyHashCode(getCd_unita_organizzativa())+
		calculateKeyHashCode(getEsercizio())+
		calculateKeyHashCode(getPg_minicarriera())+
		calculateKeyHashCode(getPg_rata());
}
/* 
 * Setter dell'attributo cd_cds
 */
public void setCd_cds(java.lang.String cd_cds) {
	this.cd_cds = cd_cds;
}
/* 
 * Setter dell'attributo cd_unita_organizzativa
 */
public void setCd_unita_organizzativa(java.lang.String cd_unita_organizzativa) {
	this.cd_unita_organizzativa = cd_unita_organizzativa;
}
/* 
 * Setter dell'attributo esercizio
 */
public void setEsercizio(java.lang.Integer esercizio) {
	this.esercizio = esercizio;
}
/* 
 * Setter dell'attributo pg_minicarriera
 */
public void setPg_minicarriera(java.lang.Long pg_minicarriera) {
	this.pg_minicarriera = pg_minicarriera;
}
/* 
 * Setter dell'attributo pg_rata
 */
public void setPg_rata(java.lang.Long pg_rata) {
	this.pg_rata = pg_rata;
}
}
