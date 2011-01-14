package it.cnr.contab.docamm00.tabrif.bulk;

import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class SezionaleKey extends OggettoBulk {
	// CD_CDS VARCHAR(30) NOT NULL (PK)
	protected java.lang.String cd_cds;

	// CD_UNITA_ORGANIZZATIVA VARCHAR(30) NOT NULL (PK)
	protected java.lang.String cd_unita_organizzativa;

	// CD_TIPO_SEZIONALE VARCHAR(10) NOT NULL (PK)
	protected java.lang.String cd_tipo_sezionale;

	// ESERCIZIO DECIMAL(4,0) NOT NULL (PK)
	protected java.lang.Integer esercizio;

	// TI_FATTURA CHAR(1) NOT NULL (PK)
	protected java.lang.String ti_fattura;

public SezionaleKey() {
	super();
}
public SezionaleKey(java.lang.String cd_cds,java.lang.String cd_tipo_sezionale,java.lang.String cd_unita_organizzativa,java.lang.Integer esercizio,java.lang.String ti_fattura) {
	super();
	this.cd_cds = cd_cds;
	this.cd_tipo_sezionale = cd_tipo_sezionale;
	this.cd_unita_organizzativa = cd_unita_organizzativa;
	this.esercizio = esercizio;
	this.ti_fattura = ti_fattura;
}
public boolean equalsByPrimaryKey(Object o) {
	if (this == o) return true;
	if (!(o instanceof SezionaleKey)) return false;
	SezionaleKey k = (SezionaleKey)o;
	if(!compareKey(getCd_cds(),k.getCd_cds())) return false;
	if(!compareKey(getCd_tipo_sezionale(),k.getCd_tipo_sezionale())) return false;
	if(!compareKey(getCd_unita_organizzativa(),k.getCd_unita_organizzativa())) return false;
	if(!compareKey(getEsercizio(),k.getEsercizio())) return false;
	if(!compareKey(getTi_fattura(),k.getTi_fattura())) return false;
	return true;
}
/* 
 * Getter dell'attributo cd_cds
 */
public java.lang.String getCd_cds() {
	return cd_cds;
}
/* 
 * Getter dell'attributo cd_tipo_sezionale
 */
public java.lang.String getCd_tipo_sezionale() {
	return cd_tipo_sezionale;
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
 * Getter dell'attributo ti_fattura
 */
public java.lang.String getTi_fattura() {
	return ti_fattura;
}
public int primaryKeyHashCode() {
	return
		calculateKeyHashCode(getCd_cds())+
		calculateKeyHashCode(getCd_tipo_sezionale())+
		calculateKeyHashCode(getCd_unita_organizzativa())+
		calculateKeyHashCode(getEsercizio())+
		calculateKeyHashCode(getTi_fattura());
}
/* 
 * Setter dell'attributo cd_cds
 */
public void setCd_cds(java.lang.String cd_cds) {
	this.cd_cds = cd_cds;
}
/* 
 * Setter dell'attributo cd_tipo_sezionale
 */
public void setCd_tipo_sezionale(java.lang.String cd_tipo_sezionale) {
	this.cd_tipo_sezionale = cd_tipo_sezionale;
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
 * Setter dell'attributo ti_fattura
 */
public void setTi_fattura(java.lang.String ti_fattura) {
	this.ti_fattura = ti_fattura;
}
}
