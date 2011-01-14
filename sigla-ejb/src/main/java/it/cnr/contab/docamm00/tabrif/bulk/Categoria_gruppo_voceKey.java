package it.cnr.contab.docamm00.tabrif.bulk;
import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class Categoria_gruppo_voceKey extends OggettoBulk implements KeyedPersistent {
	// TI_APPARTENENZA CHAR(1) NOT NULL (PK)
	private java.lang.String ti_appartenenza;

/* 
 * Getter dell'attributo ti_appartenenza
 */
public java.lang.String getTi_appartenenza() {
	return ti_appartenenza;
}

/* 
 * Setter dell'attributo ti_appartenenza
 */
public void setTi_appartenenza(java.lang.String ti_appartenenza) {
	this.ti_appartenenza = ti_appartenenza;
}
	// CD_ELEMENTO_VOCE VARCHAR(20) NOT NULL (PK)
	private java.lang.String cd_elemento_voce;

/* 
 * Getter dell'attributo cd_elemento_voce
 */
public java.lang.String getCd_elemento_voce() {
	return cd_elemento_voce;
}

/* 
 * Setter dell'attributo cd_elemento_voce
 */
public void setCd_elemento_voce(java.lang.String cd_elemento_voce) {
	this.cd_elemento_voce = cd_elemento_voce;
}
	// CD_CATEGORIA_GRUPPO VARCHAR(10) NOT NULL (PK)
	private java.lang.String cd_categoria_gruppo;

/* 
 * Getter dell'attributo cd_categoria_gruppo
 */
public java.lang.String getCd_categoria_gruppo() {
	return cd_categoria_gruppo;
}

/* 
 * Setter dell'attributo cd_categoria_gruppo
 */
public void setCd_categoria_gruppo(java.lang.String cd_categoria_gruppo) {
	this.cd_categoria_gruppo = cd_categoria_gruppo;
}
	// ESERCIZIO DECIMAL(4,0) NOT NULL (PK)
	private java.lang.Integer esercizio;

/* 
 * Getter dell'attributo esercizio
 */
public java.lang.Integer getEsercizio() {
	return esercizio;
}

/* 
 * Setter dell'attributo esercizio
 */
public void setEsercizio(java.lang.Integer esercizio) {
	this.esercizio = esercizio;
}
	// TI_GESTIONE CHAR(1) NOT NULL (PK)
	private java.lang.String ti_gestione;

/* 
 * Getter dell'attributo ti_gestione
 */
public java.lang.String getTi_gestione() {
	return ti_gestione;
}

/* 
 * Setter dell'attributo ti_gestione
 */
public void setTi_gestione(java.lang.String ti_gestione) {
	this.ti_gestione = ti_gestione;
}

public Categoria_gruppo_voceKey() {
	super();
}


public Categoria_gruppo_voceKey(java.lang.String cd_categoria_gruppo,java.lang.String cd_elemento_voce,java.lang.Integer esercizio,java.lang.String ti_appartenenza,java.lang.String ti_gestione) {
	this.cd_categoria_gruppo = cd_categoria_gruppo;
	this.cd_elemento_voce = cd_elemento_voce;
	this.esercizio = esercizio;
	this.ti_appartenenza = ti_appartenenza;
	this.ti_gestione = ti_gestione;
}

public boolean equalsByPrimaryKey(Object o) {
	if (this == o) return true;
	if (!(o instanceof Categoria_gruppo_voceKey)) return false;
	Categoria_gruppo_voceKey k = (Categoria_gruppo_voceKey)o;
	if(!compareKey(getCd_categoria_gruppo(),k.getCd_categoria_gruppo())) return false;
	if(!compareKey(getCd_elemento_voce(),k.getCd_elemento_voce())) return false;
	if(!compareKey(getEsercizio(),k.getEsercizio())) return false;
	if(!compareKey(getTi_appartenenza(),k.getTi_appartenenza())) return false;
	if(!compareKey(getTi_gestione(),k.getTi_gestione())) return false;
	return true;
}

public int primaryKeyHashCode() {
	return
		calculateKeyHashCode(getCd_categoria_gruppo())+
		calculateKeyHashCode(getCd_elemento_voce())+
		calculateKeyHashCode(getEsercizio())+
		calculateKeyHashCode(getTi_appartenenza())+
		calculateKeyHashCode(getTi_gestione());
}

}
