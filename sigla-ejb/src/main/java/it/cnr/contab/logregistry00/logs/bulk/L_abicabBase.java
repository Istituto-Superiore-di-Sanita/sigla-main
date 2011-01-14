package it.cnr.contab.logregistry00.logs.bulk;

import it.cnr.contab.logregistry00.core.bulk.OggettoLogBulk;
import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class L_abicabBase extends L_abicabKey implements Keyed {
	// CAP VARCHAR(5)
	private java.lang.String cap;

	// DS_ABICAB VARCHAR(150) NOT NULL
	private java.lang.String ds_abicab;

	// FL_CANCELLATO CHAR(1) NOT NULL
	private java.lang.Boolean fl_cancellato;

	// FRAZIONE VARCHAR(100)
	private java.lang.String frazione;

	// PG_COMUNE DECIMAL(10,0)
	private java.lang.Long pg_comune;

	// VIA VARCHAR(100)
	private java.lang.String via;

public L_abicabBase() {
	super();
}
public L_abicabBase(java.lang.String abi,java.lang.String cab,java.math.BigDecimal pg_storico_) {
	super(abi,cab,pg_storico_);
}
/* 
 * Getter dell'attributo cap
 */
public java.lang.String getCap() {
	return cap;
}
/* 
 * Getter dell'attributo ds_abicab
 */
public java.lang.String getDs_abicab() {
	return ds_abicab;
}
/* 
 * Getter dell'attributo fl_cancellato
 */
public java.lang.Boolean getFl_cancellato() {
	return fl_cancellato;
}
/* 
 * Getter dell'attributo frazione
 */
public java.lang.String getFrazione() {
	return frazione;
}
/* 
 * Getter dell'attributo pg_comune
 */
public java.lang.Long getPg_comune() {
	return pg_comune;
}
/* 
 * Getter dell'attributo via
 */
public java.lang.String getVia() {
	return via;
}
/* 
 * Setter dell'attributo cap
 */
public void setCap(java.lang.String cap) {
	this.cap = cap;
}
/* 
 * Setter dell'attributo ds_abicab
 */
public void setDs_abicab(java.lang.String ds_abicab) {
	this.ds_abicab = ds_abicab;
}
/* 
 * Setter dell'attributo fl_cancellato
 */
public void setFl_cancellato(java.lang.Boolean fl_cancellato) {
	this.fl_cancellato = fl_cancellato;
}
/* 
 * Setter dell'attributo frazione
 */
public void setFrazione(java.lang.String frazione) {
	this.frazione = frazione;
}
/* 
 * Setter dell'attributo pg_comune
 */
public void setPg_comune(java.lang.Long pg_comune) {
	this.pg_comune = pg_comune;
}
/* 
 * Setter dell'attributo via
 */
public void setVia(java.lang.String via) {
	this.via = via;
}
}
