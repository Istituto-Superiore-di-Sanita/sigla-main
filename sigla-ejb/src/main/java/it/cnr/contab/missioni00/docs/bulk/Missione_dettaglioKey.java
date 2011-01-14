package it.cnr.contab.missioni00.docs.bulk;

import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class Missione_dettaglioKey extends OggettoBulk implements KeyedPersistent {
	// CD_CDS VARCHAR(30) NOT NULL (PK)
	private java.lang.String cd_cds;

	// CD_UNITA_ORGANIZZATIVA VARCHAR(30) NOT NULL (PK)
	private java.lang.String cd_unita_organizzativa;

	// PG_MISSIONE DECIMAL(10,0) NOT NULL (PK)
	private java.lang.Long pg_missione;

	// DT_INIZIO_TAPPA TIMESTAMP NOT NULL (PK)
	private java.sql.Timestamp dt_inizio_tappa;

	// ESERCIZIO DECIMAL(4,0) NOT NULL (PK)
	private java.lang.Integer esercizio;

	// PG_RIGA DECIMAL(10,0) NOT NULL (PK)
	private java.lang.Long pg_riga;

public Missione_dettaglioKey() {
	super();
}
public Missione_dettaglioKey(java.lang.String cd_cds,java.lang.String cd_unita_organizzativa,java.sql.Timestamp dt_inizio_tappa,java.lang.Integer esercizio,java.lang.Long pg_missione,java.lang.Long pg_riga) {
	super();
	this.cd_cds = cd_cds;
	this.cd_unita_organizzativa = cd_unita_organizzativa;
	this.dt_inizio_tappa = dt_inizio_tappa;
	this.esercizio = esercizio;
	this.pg_missione = pg_missione;
	this.pg_riga = pg_riga;
}
public boolean equalsByPrimaryKey(Object o) {
	if (this == o) return true;
	if (!(o instanceof Missione_dettaglioKey)) return false;
	Missione_dettaglioKey k = (Missione_dettaglioKey)o;
	if(!compareKey(getCd_cds(),k.getCd_cds())) return false;
	if(!compareKey(getCd_unita_organizzativa(),k.getCd_unita_organizzativa())) return false;
	if(!compareKey(getDt_inizio_tappa(),k.getDt_inizio_tappa())) return false;
	if(!compareKey(getEsercizio(),k.getEsercizio())) return false;
	if(!compareKey(getPg_missione(),k.getPg_missione())) return false;
	if(!compareKey(getPg_riga(),k.getPg_riga())) return false;
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
 * Getter dell'attributo dt_inizio_tappa
 */
public java.sql.Timestamp getDt_inizio_tappa() {
	return dt_inizio_tappa;
}
/* 
 * Getter dell'attributo esercizio
 */
public java.lang.Integer getEsercizio() {
	return esercizio;
}
/* 
 * Getter dell'attributo pg_missione
 */
public java.lang.Long getPg_missione() {
	return pg_missione;
}
/* 
 * Getter dell'attributo pg_riga
 */
public java.lang.Long getPg_riga() {
	return pg_riga;
}
public int primaryKeyHashCode() {
	return
		calculateKeyHashCode(getCd_cds())+
		calculateKeyHashCode(getCd_unita_organizzativa())+
		calculateKeyHashCode(getDt_inizio_tappa())+
		calculateKeyHashCode(getEsercizio())+
		calculateKeyHashCode(getPg_missione())+
		calculateKeyHashCode(getPg_riga());
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
 * Setter dell'attributo dt_inizio_tappa
 */
public void setDt_inizio_tappa(java.sql.Timestamp dt_inizio_tappa) {
	this.dt_inizio_tappa = dt_inizio_tappa;
}
/* 
 * Setter dell'attributo esercizio
 */
public void setEsercizio(java.lang.Integer esercizio) {
	this.esercizio = esercizio;
}
/* 
 * Setter dell'attributo pg_missione
 */
public void setPg_missione(java.lang.Long pg_missione) {
	this.pg_missione = pg_missione;
}
/* 
 * Setter dell'attributo pg_riga
 */
public void setPg_riga(java.lang.Long pg_riga) {
	this.pg_riga = pg_riga;
}
}
