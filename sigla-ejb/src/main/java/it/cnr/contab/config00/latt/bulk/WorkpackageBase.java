package it.cnr.contab.config00.latt.bulk;

import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class WorkpackageBase extends WorkpackageKey implements Keyed {
	// CD_CDR_COLLEGATO VARCHAR(30)
	private java.lang.String cd_cdr_collegato;

	// CD_FUNZIONE VARCHAR(2)
	private java.lang.String cd_funzione;

	// CD_GRUPPO_LINEA_ATTIVITA VARCHAR(10)
	private java.lang.String cd_gruppo_linea_attivita;

	// CD_INSIEME_LA VARCHAR(10)
	private java.lang.String cd_insieme_la;

	// CD_LA_COLLEGATO VARCHAR(10)
	private java.lang.String cd_la_collegato;

	// CD_NATURA VARCHAR(1) NOT NULL
	private java.lang.String cd_natura;

	// CD_TIPO_LINEA_ATTIVITA VARCHAR(10)
	private java.lang.String cd_tipo_linea_attivita;

	// DENOMINAZIONE VARCHAR(300)
	private java.lang.String denominazione;

	// DS_LINEA_ATTIVITA VARCHAR(300)
	private java.lang.String ds_linea_attivita;

	// ESERCIZIO_FINE DECIMAL(4,0) NOT NULL
	private java.lang.Integer esercizio_fine;

	// ESERCIZIO_INIZIO DECIMAL(4,0) NOT NULL
	private java.lang.Integer esercizio_inizio;

	// TI_GESTIONE CHAR(1) NOT NULL
	private java.lang.String ti_gestione;
	
	// PG_PROGETTO DECIMAL(10,0) NOT NULL
	private java.lang.Integer pg_progetto;
	
	/*Angelo 18/11/2004*/
	// CD_RESPONSABILE_TERZO(8,0)
	private java.lang.Integer cd_responsabile_terzo;
	
    //	FL_LIMITE_ASS_OBB CHAR(1)
    private Boolean fl_limite_ass_obblig;
public WorkpackageBase() {
	super();
}
public WorkpackageBase(java.lang.String cd_centro_responsabilita,java.lang.String cd_linea_attivita) {
	super(cd_centro_responsabilita,cd_linea_attivita);
}
/* 
 * Getter dell'attributo cd_cdr_collegato
 */
public java.lang.String getCd_cdr_collegato() {
	return cd_cdr_collegato;
}
/* 
 * Getter dell'attributo cd_funzione
 */
public java.lang.String getCd_funzione() {
	return cd_funzione;
}
/* 
 * Getter dell'attributo cd_gruppo_linea_attivita
 */
public java.lang.String getCd_gruppo_linea_attivita() {
	return cd_gruppo_linea_attivita;
}
/* 
 * Getter dell'attributo cd_insieme_la
 */
public java.lang.String getCd_insieme_la() {
	return cd_insieme_la;
}
/* 
 * Getter dell'attributo cd_la_collegato
 */
public java.lang.String getCd_la_collegato() {
	return cd_la_collegato;
}
/* 
 * Getter dell'attributo cd_natura
 */
public java.lang.String getCd_natura() {
	return cd_natura;
}
/* 
 * Getter dell'attributo cd_tipo_linea_attivita
 */
public java.lang.String getCd_tipo_linea_attivita() {
	return cd_tipo_linea_attivita;
}
/* 
 * Getter dell'attributo denominazione
 */
public java.lang.String getDenominazione() {
	return denominazione;
}
/* 
 * Getter dell'attributo ds_linea_attivita
 */
public java.lang.String getDs_linea_attivita() {
	return ds_linea_attivita;
}
/* 
 * Getter dell'attributo esercizio_fine
 */
public java.lang.Integer getEsercizio_fine() {
	return esercizio_fine;
}
/* 
 * Getter dell'attributo esercizio_inizio
 */
public java.lang.Integer getEsercizio_inizio() {
	return esercizio_inizio;
}
/* 
 * Getter dell'attributo ti_gestione
 */
public java.lang.String getTi_gestione() {
	return ti_gestione;
}
/* 
 * Setter dell'attributo cd_cdr_collegato
 */
public void setCd_cdr_collegato(java.lang.String cd_cdr_collegato) {
	this.cd_cdr_collegato = cd_cdr_collegato;
}
/* 
 * Setter dell'attributo cd_funzione
 */
public void setCd_funzione(java.lang.String cd_funzione) {
	this.cd_funzione = cd_funzione;
}
/* 
 * Setter dell'attributo cd_gruppo_linea_attivita
 */
public void setCd_gruppo_linea_attivita(java.lang.String cd_gruppo_linea_attivita) {
	this.cd_gruppo_linea_attivita = cd_gruppo_linea_attivita;
}
/* 
 * Setter dell'attributo cd_insieme_la
 */
public void setCd_insieme_la(java.lang.String cd_insieme_la) {
	this.cd_insieme_la = cd_insieme_la;
}
/* 
 * Setter dell'attributo cd_la_collegato
 */
public void setCd_la_collegato(java.lang.String cd_la_collegato) {
	this.cd_la_collegato = cd_la_collegato;
}
/* 
 * Setter dell'attributo cd_natura
 */
public void setCd_natura(java.lang.String cd_natura) {
	this.cd_natura = cd_natura;
}
/* 
 * Setter dell'attributo cd_tipo_linea_attivita
 */
public void setCd_tipo_linea_attivita(java.lang.String cd_tipo_linea_attivita) {
	this.cd_tipo_linea_attivita = cd_tipo_linea_attivita;
}
/* 
 * Setter dell'attributo denominazione
 */
public void setDenominazione(java.lang.String denominazione) {
	this.denominazione = denominazione;
}
/* 
 * Setter dell'attributo ds_linea_attivita
 */
public void setDs_linea_attivita(java.lang.String ds_linea_attivita) {
	this.ds_linea_attivita = ds_linea_attivita;
}
/* 
 * Setter dell'attributo esercizio_fine
 */
public void setEsercizio_fine(java.lang.Integer esercizio_fine) {
	this.esercizio_fine = esercizio_fine;
}
/* 
 * Setter dell'attributo esercizio_inizio
 */
public void setEsercizio_inizio(java.lang.Integer esercizio_inizio) {
	this.esercizio_inizio = esercizio_inizio;
}
/* 
 * Setter dell'attributo ti_gestione
 */
public void setTi_gestione(java.lang.String ti_gestione) {
	this.ti_gestione = ti_gestione;
}
	/**
	 * Returns the pg_progetto.
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getPg_progetto() {
		return pg_progetto;
	}

	/**
	 * Sets the pg_progetto.
	 * @param pg_progetto The pg_progetto to set
	 */
	public void setPg_progetto(java.lang.Integer pg_progetto) {
		this.pg_progetto = pg_progetto;
	}

	/**
	 * Ritorna il valore della propriet� cd_responsabile_terzo
	 * 
	 * @return Il valore della propriet� cd_responsabile_terzo
	 */
	public java.lang.Integer getCd_responsabile_terzo() {
		return cd_responsabile_terzo;
	}

	/**
	 * Setta il valore della propriet� cd_responsabile_terzo
	 * 
	 * @param val
	 */
	public void setCd_responsabile_terzo(java.lang.Integer val) {
		this.cd_responsabile_terzo = val;
	}

	/**
	 * @return
	 */
	public Boolean getFl_limite_ass_obblig() {
		return fl_limite_ass_obblig;
	}

	/**
	 * @param boolean1
	 */
	public void setFl_limite_ass_obblig(Boolean boolean1) {
		fl_limite_ass_obblig = boolean1;
	}

}
