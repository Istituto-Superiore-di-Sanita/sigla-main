/*
 * Created by Aurelio's BulkGenerator 1.0
 * Date 18/07/2007
 */
package it.cnr.contab.incarichi00.tabrif.bulk;
import it.cnr.jada.persistency.Keyed;
public class Tipo_attivitaBase extends Tipo_attivitaKey implements Keyed {
//    DS_TIPO_ATTIVITA VARCHAR(200) NOT NULL
	private java.lang.String ds_tipo_attivita;
 
//    FL_CANCELLATO CHAR(1) NOT NULL
	private java.lang.Boolean fl_cancellato;
 
//    FL_PUBBLICA_CONTRATTO CHAR(1) NOT NULL
	private java.lang.Boolean fl_pubblica_contratto;

//    TIPO_ASSOCIAZIONE VARCHAR(3) NOT NULL
	private java.lang.String tipo_associazione;

//  TIPOLOGIA CHAR(3) NOT NULL
	private java.lang.String tipologia;

	public Tipo_attivitaBase() {
		super();
	}
	public Tipo_attivitaBase(java.lang.String cd_tipo_attivita) {
		super(cd_tipo_attivita);
	}
	public java.lang.String getDs_tipo_attivita() {
		return ds_tipo_attivita;
	}
	public void setDs_tipo_attivita(java.lang.String ds_tipo_attivita)  {
		this.ds_tipo_attivita=ds_tipo_attivita;
	}
	public java.lang.Boolean getFl_cancellato() {
		return fl_cancellato;
	}
	public void setFl_cancellato(java.lang.Boolean fl_cancellato)  {
		this.fl_cancellato=fl_cancellato;
	}
	public java.lang.Boolean getFl_pubblica_contratto() {
		return fl_pubblica_contratto;
	}
	public void setFl_pubblica_contratto(java.lang.Boolean fl_pubblica_contratto) {
		this.fl_pubblica_contratto = fl_pubblica_contratto;
	}
	public java.lang.String getTipo_associazione() {
		return tipo_associazione;
	}
	public void setTipo_associazione(java.lang.String tipo_associazione) {
		this.tipo_associazione = tipo_associazione;
	}
	public java.lang.String getTipologia() {
		return tipologia;
	}
	public void setTipologia(java.lang.String tipologia) {
		this.tipologia = tipologia;
	}
}