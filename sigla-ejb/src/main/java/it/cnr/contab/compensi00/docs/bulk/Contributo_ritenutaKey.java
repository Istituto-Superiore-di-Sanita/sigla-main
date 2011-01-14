package it.cnr.contab.compensi00.docs.bulk;

import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class Contributo_ritenutaKey extends OggettoBulk implements KeyedPersistent {
	// CD_CDS VARCHAR(30) NOT NULL (PK)
	private java.lang.String cd_cds;

	// CD_UNITA_ORGANIZZATIVA VARCHAR(30) NOT NULL (PK)
	private java.lang.String cd_unita_organizzativa;

	// CD_CONTRIBUTO_RITENUTA VARCHAR(10) NOT NULL (PK)
	private java.lang.String cd_contributo_ritenuta;

	// ESERCIZIO DECIMAL(4,0) NOT NULL (PK)
	private java.lang.Integer esercizio;

	// PG_COMPENSO DECIMAL(10,0) NOT NULL (PK)
	private java.lang.Long pg_compenso;

	// TI_ENTE_PERCIPIENTE CHAR(1) NOT NULL (PK)
	private java.lang.String ti_ente_percipiente;

public Contributo_ritenutaKey() {
	super();
}
public Contributo_ritenutaKey(java.lang.String cd_cds,java.lang.String cd_contributo_ritenuta,java.lang.String cd_unita_organizzativa,java.lang.Integer esercizio,java.lang.Long pg_compenso,java.lang.String ti_ente_percipiente) {
	super();
	this.cd_cds = cd_cds;
	this.cd_contributo_ritenuta = cd_contributo_ritenuta;
	this.cd_unita_organizzativa = cd_unita_organizzativa;
	this.esercizio = esercizio;
	this.pg_compenso = pg_compenso;
	this.ti_ente_percipiente = ti_ente_percipiente;
}
public boolean equalsByPrimaryKey(Object o) {
	if (this == o) return true;
	if (!(o instanceof Contributo_ritenutaKey)) return false;
	Contributo_ritenutaKey k = (Contributo_ritenutaKey)o;
	if(!compareKey(getCd_cds(),k.getCd_cds())) return false;
	if(!compareKey(getCd_contributo_ritenuta(),k.getCd_contributo_ritenuta())) return false;
	if(!compareKey(getCd_unita_organizzativa(),k.getCd_unita_organizzativa())) return false;
	if(!compareKey(getEsercizio(),k.getEsercizio())) return false;
	if(!compareKey(getPg_compenso(),k.getPg_compenso())) return false;
	if(!compareKey(getTi_ente_percipiente(),k.getTi_ente_percipiente())) return false;
	return true;
}
/* 
 * Getter dell'attributo cd_cds
 */
public java.lang.String getCd_cds() {
	return cd_cds;
}
/* 
 * Getter dell'attributo cd_contributo_ritenuta
 */
public java.lang.String getCd_contributo_ritenuta() {
	return cd_contributo_ritenuta;
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
 * Getter dell'attributo pg_compenso
 */
public java.lang.Long getPg_compenso() {
	return pg_compenso;
}
/* 
 * Getter dell'attributo ti_ente_percipiente
 */
public java.lang.String getTi_ente_percipiente() {
	return ti_ente_percipiente;
}
public int primaryKeyHashCode() {
	return
		calculateKeyHashCode(getCd_cds())+
		calculateKeyHashCode(getCd_contributo_ritenuta())+
		calculateKeyHashCode(getCd_unita_organizzativa())+
		calculateKeyHashCode(getEsercizio())+
		calculateKeyHashCode(getPg_compenso())+
		calculateKeyHashCode(getTi_ente_percipiente());
}
/* 
 * Setter dell'attributo cd_cds
 */
public void setCd_cds(java.lang.String cd_cds) {
	this.cd_cds = cd_cds;
}
/* 
 * Setter dell'attributo cd_contributo_ritenuta
 */
public void setCd_contributo_ritenuta(java.lang.String cd_contributo_ritenuta) {
	this.cd_contributo_ritenuta = cd_contributo_ritenuta;
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
 * Setter dell'attributo pg_compenso
 */
public void setPg_compenso(java.lang.Long pg_compenso) {
	this.pg_compenso = pg_compenso;
}
/* 
 * Setter dell'attributo ti_ente_percipiente
 */
public void setTi_ente_percipiente(java.lang.String ti_ente_percipiente) {
	this.ti_ente_percipiente = ti_ente_percipiente;
}
}
