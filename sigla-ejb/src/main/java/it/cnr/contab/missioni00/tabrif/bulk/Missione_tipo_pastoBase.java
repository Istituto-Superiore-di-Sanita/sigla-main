package it.cnr.contab.missioni00.tabrif.bulk;

import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class Missione_tipo_pastoBase extends Missione_tipo_pastoKey implements Keyed {
	// CD_DIVISA VARCHAR(10) NOT NULL
	private java.lang.String cd_divisa;

	// DT_CANCELLAZIONE TIMESTAMP
	private java.sql.Timestamp dt_cancellazione;

	// DT_FINE_VALIDITA TIMESTAMP NOT NULL
	private java.sql.Timestamp dt_fine_validita;

	// LIMITE_MAX_PASTO DECIMAL(15,2) NOT NULL
	private java.math.BigDecimal limite_max_pasto;

public Missione_tipo_pastoBase() {
	super();
}
public Missione_tipo_pastoBase(java.lang.String cd_ti_pasto,java.sql.Timestamp dt_inizio_validita,java.lang.Long pg_nazione,java.lang.Long pg_rif_inquadramento,java.lang.String ti_area_geografica) {
	super(cd_ti_pasto,dt_inizio_validita,pg_nazione,pg_rif_inquadramento,ti_area_geografica);
}
/* 
 * Getter dell'attributo cd_divisa
 */
public java.lang.String getCd_divisa() {
	return cd_divisa;
}
/* 
 * Getter dell'attributo dt_cancellazione
 */
public java.sql.Timestamp getDt_cancellazione() {
	return dt_cancellazione;
}
/* 
 * Getter dell'attributo dt_fine_validita
 */
public java.sql.Timestamp getDt_fine_validita() {
	return dt_fine_validita;
}
/* 
 * Getter dell'attributo limite_max_pasto
 */
public java.math.BigDecimal getLimite_max_pasto() {
	return limite_max_pasto;
}
/* 
 * Setter dell'attributo cd_divisa
 */
public void setCd_divisa(java.lang.String cd_divisa) {
	this.cd_divisa = cd_divisa;
}
/* 
 * Setter dell'attributo dt_cancellazione
 */
public void setDt_cancellazione(java.sql.Timestamp dt_cancellazione) {
	this.dt_cancellazione = dt_cancellazione;
}
/* 
 * Setter dell'attributo dt_fine_validita
 */
public void setDt_fine_validita(java.sql.Timestamp dt_fine_validita) {
	this.dt_fine_validita = dt_fine_validita;
}
/* 
 * Setter dell'attributo limite_max_pasto
 */
public void setLimite_max_pasto(java.math.BigDecimal limite_max_pasto) {
	this.limite_max_pasto = limite_max_pasto;
}
}
