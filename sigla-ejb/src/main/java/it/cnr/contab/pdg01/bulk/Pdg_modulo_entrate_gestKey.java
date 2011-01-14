/*
* Created by Generator 1.0
* Date 23/11/2005
*/
package it.cnr.contab.pdg01.bulk;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.persistency.KeyedPersistent;
public class Pdg_modulo_entrate_gestKey extends OggettoBulk implements KeyedPersistent {
	private java.lang.Integer esercizio;
	private java.lang.String cd_centro_responsabilita;
	private java.lang.Integer pg_progetto;
	private java.lang.String cd_natura;
	private java.lang.Integer id_classificazione;
	private java.lang.String cd_cds_area;
	private java.lang.Long pg_dettaglio;
	private java.lang.String cd_cdr_assegnatario;
	private java.lang.String cd_linea_attivita;
	private java.lang.String ti_appartenenza;
	private java.lang.String ti_gestione;
	private java.lang.String cd_elemento_voce;
	public Pdg_modulo_entrate_gestKey() {
		super();
	}
	public Pdg_modulo_entrate_gestKey(java.lang.Integer esercizio, java.lang.String cd_centro_responsabilita, java.lang.Integer pg_progetto, java.lang.String cd_natura, java.lang.Integer id_classificazione, java.lang.String cd_cds_area, java.lang.Long pg_dettaglio, java.lang.String cd_cdr_assegnatario, java.lang.String cd_linea_attivita, java.lang.String ti_appartenenza, java.lang.String ti_gestione, java.lang.String cd_elemento_voce) {
		super();
		this.esercizio=esercizio;
		this.cd_centro_responsabilita=cd_centro_responsabilita;
		this.pg_progetto=pg_progetto;
		this.cd_natura=cd_natura;
		this.id_classificazione=id_classificazione;
		this.cd_cds_area=cd_cds_area;
		this.pg_dettaglio=pg_dettaglio;
		this.cd_cdr_assegnatario=cd_cdr_assegnatario;
		this.cd_linea_attivita=cd_linea_attivita;
		this.ti_appartenenza=ti_appartenenza;
		this.ti_gestione=ti_gestione;
		this.cd_elemento_voce=cd_elemento_voce;
	}
	public boolean equalsByPrimaryKey(Object o) {
		if (this== o) return true;
		if (!(o instanceof Pdg_modulo_entrate_gestKey)) return false;
		Pdg_modulo_entrate_gestKey k = (Pdg_modulo_entrate_gestKey) o;
		if (!compareKey(getEsercizio(), k.getEsercizio())) return false;
		if (!compareKey(getCd_centro_responsabilita(), k.getCd_centro_responsabilita())) return false;
		if (!compareKey(getPg_progetto(), k.getPg_progetto())) return false;
		if (!compareKey(getCd_natura(), k.getCd_natura())) return false;
		if (!compareKey(getId_classificazione(), k.getId_classificazione())) return false;
		if (!compareKey(getCd_cds_area(), k.getCd_cds_area())) return false;
		if (!compareKey(getPg_dettaglio(), k.getPg_dettaglio())) return false;
		if (!compareKey(getCd_cdr_assegnatario(), k.getCd_cdr_assegnatario())) return false;
		if (!compareKey(getCd_linea_attivita(), k.getCd_linea_attivita())) return false;
		if (!compareKey(getTi_appartenenza(), k.getTi_appartenenza())) return false;
		if (!compareKey(getTi_gestione(), k.getTi_gestione())) return false;
		if (!compareKey(getCd_elemento_voce(), k.getCd_elemento_voce())) return false;
		return true;
	}
	public int primaryKeyHashCode() {
		int i = 0;
		i = i + calculateKeyHashCode(getEsercizio());
		i = i + calculateKeyHashCode(getCd_centro_responsabilita());
		i = i + calculateKeyHashCode(getPg_progetto());
		i = i + calculateKeyHashCode(getCd_natura());
		i = i + calculateKeyHashCode(getId_classificazione());
		i = i + calculateKeyHashCode(getCd_cds_area());
		i = i + calculateKeyHashCode(getPg_dettaglio());
		i = i + calculateKeyHashCode(getCd_cdr_assegnatario());
		i = i + calculateKeyHashCode(getCd_linea_attivita());
		i = i + calculateKeyHashCode(getTi_appartenenza());
		i = i + calculateKeyHashCode(getTi_gestione());
		i = i + calculateKeyHashCode(getCd_elemento_voce());
		return i;
	}
	public void setEsercizio(java.lang.Integer esercizio)  {
		this.esercizio=esercizio;
	}
	public java.lang.Integer getEsercizio () {
		return esercizio;
	}
	public void setCd_centro_responsabilita(java.lang.String cd_centro_responsabilita)  {
		this.cd_centro_responsabilita=cd_centro_responsabilita;
	}
	public java.lang.String getCd_centro_responsabilita () {
		return cd_centro_responsabilita;
	}
	public void setPg_progetto(java.lang.Integer pg_progetto)  {
		this.pg_progetto=pg_progetto;
	}
	public java.lang.Integer getPg_progetto () {
		return pg_progetto;
	}
	public void setCd_natura(java.lang.String cd_natura)  {
		this.cd_natura=cd_natura;
	}
	public java.lang.String getCd_natura () {
		return cd_natura;
	}
	public void setId_classificazione(java.lang.Integer id_classificazione)  {
		this.id_classificazione=id_classificazione;
	}
	public java.lang.Integer getId_classificazione () {
		return id_classificazione;
	}
	public void setCd_cds_area(java.lang.String cd_cds_area)  {
		this.cd_cds_area=cd_cds_area;
	}
	public java.lang.String getCd_cds_area () {
		return cd_cds_area;
	}
	public void setPg_dettaglio(java.lang.Long pg_dettaglio)  {
		this.pg_dettaglio=pg_dettaglio;
	}
	public java.lang.Long getPg_dettaglio () {
		return pg_dettaglio;
	}
	public void setCd_cdr_assegnatario(java.lang.String cd_cdr_assegnatario)  {
		this.cd_cdr_assegnatario=cd_cdr_assegnatario;
	}
	public java.lang.String getCd_cdr_assegnatario () {
		return cd_cdr_assegnatario;
	}
	public void setCd_linea_attivita(java.lang.String cd_linea_attivita)  {
		this.cd_linea_attivita=cd_linea_attivita;
	}
	public java.lang.String getCd_linea_attivita () {
		return cd_linea_attivita;
	}
	public void setTi_appartenenza(java.lang.String ti_appartenenza)  {
		this.ti_appartenenza=ti_appartenenza;
	}
	public java.lang.String getTi_appartenenza () {
		return ti_appartenenza;
	}
	public void setTi_gestione(java.lang.String ti_gestione)  {
		this.ti_gestione=ti_gestione;
	}
	public java.lang.String getTi_gestione () {
		return ti_gestione;
	}
	public void setCd_elemento_voce(java.lang.String cd_elemento_voce)  {
		this.cd_elemento_voce=cd_elemento_voce;
	}
	public java.lang.String getCd_elemento_voce () {
		return cd_elemento_voce;
	}
}