/*
* Created by Generator 1.0
* Date 25/05/2005
*/
package it.cnr.contab.pdg00.bulk;
import it.cnr.jada.persistency.Keyed;
public class Pdg_variazioneBase extends Pdg_variazioneKey implements Keyed {
//	CD_CENTRO_RESPONSABILITA VARCHAR(30) NOT NULL	
	private java.lang.String cd_centro_responsabilita;
	
//    DT_APERTURA TIMESTAMP(7) NOT NULL
	private java.sql.Timestamp dt_apertura;
 
//    DT_CHIUSURA TIMESTAMP(7)
	private java.sql.Timestamp dt_chiusura;
 
//    DT_APPROVAZIONE TIMESTAMP(7)
	private java.sql.Timestamp dt_approvazione;
 
//    DT_ANNULLAMENTO TIMESTAMP(7)
	private java.sql.Timestamp dt_annullamento;
 
//    DS_VARIAZIONE VARCHAR(300) NOT NULL
	private java.lang.String ds_variazione;
 
//    DS_DELIBERA VARCHAR(200) NOT NULL
	private java.lang.String ds_delibera;
 
//  TIPOLOGIA VARCHAR(15) NOT NULL
	private java.lang.String tipologia;

//	TIPOLOGIA_FIN VARCHAR(3) NOT NULL
	private java.lang.String tipologia_fin;

//    STATO CHAR(3) NOT NULL
	private java.lang.String stato;
	
//  STATO_INVIO CHAR(3) NOT NULL
	private java.lang.String stato_invio;	
 
//	  RIFERIMENTI VARCHAR(200) NULL
    private java.lang.String riferimenti;

//	  CD_CAUSALE_RESPINTA VARCHAR(200) NULL
    private java.lang.String cd_causale_respinta;

//	  DS_CAUSALE_RESPINTA VARCHAR(200) NULL
    private java.lang.String ds_causale_respinta;

//    DT_APPROVAZIONE TIMESTAMP(7)
	private java.sql.Timestamp dt_app_formale;

	// TI_APPARTENENZA CHAR(1)
	private java.lang.String ti_appartenenza;

	// TI_GESTIONE CHAR(1)
	private java.lang.String ti_gestione;

	// CD_ELEMENTO_VOCE VARCHAR(20)
	private java.lang.String cd_elemento_voce;

	//  FL_VISTO_DIP_VARIAZIONI VARCHAR(1)
	private Boolean fl_visto_dip_variazioni;

	public Pdg_variazioneBase() {
		super();
	}
	public Pdg_variazioneBase(java.lang.Integer esercizio, java.lang.Long pg_variazione_pdg) {
		super(esercizio, pg_variazione_pdg);
	}
	public java.sql.Timestamp getDt_apertura () {
		return dt_apertura;
	}
	public void setDt_apertura(java.sql.Timestamp dt_apertura)  {
		this.dt_apertura=dt_apertura;
	}
	public java.sql.Timestamp getDt_chiusura () {
		return dt_chiusura;
	}
	public void setDt_chiusura(java.sql.Timestamp dt_chiusura)  {
		this.dt_chiusura=dt_chiusura;
	}
	public java.sql.Timestamp getDt_approvazione () {
		return dt_approvazione;
	}
	public void setDt_approvazione(java.sql.Timestamp dt_approvazione)  {
		this.dt_approvazione=dt_approvazione;
	}
	public java.sql.Timestamp getDt_annullamento () {
		return dt_annullamento;
	}
	public void setDt_annullamento(java.sql.Timestamp dt_annullamento)  {
		this.dt_annullamento=dt_annullamento;
	}
	public java.lang.String getDs_variazione () {
		return ds_variazione;
	}
	public void setDs_variazione(java.lang.String ds_variazione)  {
		this.ds_variazione=ds_variazione;
	}
	public java.lang.String getDs_delibera () {
		return ds_delibera;
	}
	public void setDs_delibera(java.lang.String ds_delibera)  {
		this.ds_delibera=ds_delibera;
	}
	public java.lang.String getStato () {
		return stato;
	}
	public void setStato(java.lang.String stato)  {
		this.stato=stato;
	}
	public java.lang.String getRiferimenti () {
		return riferimenti;
	}
	public void setRiferimenti(java.lang.String riferimenti)  {
		this.riferimenti=riferimenti;
	}
	public java.lang.String getCd_causale_respinta () {
		return cd_causale_respinta;
	}
	public void setCd_causale_respinta(java.lang.String cd_causale_respinta)  {
		this.cd_causale_respinta=cd_causale_respinta;
	}
	public java.lang.String getDs_causale_respinta () {
		return ds_causale_respinta;
	}
	public void setDs_causale_respinta(java.lang.String ds_causale_respinta)  {
		this.ds_causale_respinta=ds_causale_respinta;
	}
/**
 * @return
 */
public java.lang.String getCd_centro_responsabilita() {
	return cd_centro_responsabilita;
}

/**
 * @param string
 */
public void setCd_centro_responsabilita(java.lang.String string) {
	cd_centro_responsabilita = string;
}

public java.sql.Timestamp getDt_app_formale () {
	return dt_app_formale;
}
public void setDt_app_formale(java.sql.Timestamp dt_app_formale)  {
	this.dt_app_formale=dt_app_formale;
}
public java.lang.String getTipologia () {
	return tipologia;
}
public void setTipologia(java.lang.String tipologia)  {
	this.tipologia=tipologia;
}
public java.lang.String getTipologia_fin() {
	return tipologia_fin;
}
public void setTipologia_fin(java.lang.String string) {
	tipologia_fin = string;
}
public java.lang.String getCd_elemento_voce() {
	return cd_elemento_voce;
}

public void setCd_elemento_voce(java.lang.String cd_elemento_voce) {
	this.cd_elemento_voce = cd_elemento_voce;
}

public java.lang.String getTi_appartenenza() {
	return ti_appartenenza;
}

public void setTi_appartenenza(java.lang.String ti_appartenenza) {
	this.ti_appartenenza = ti_appartenenza;
}

public java.lang.String getTi_gestione() {
	return ti_gestione;
}

public void setTi_gestione(java.lang.String ti_gestione) {
	this.ti_gestione = ti_gestione;
}
public Boolean getFl_visto_dip_variazioni() {
	return fl_visto_dip_variazioni;
}
public void setFl_visto_dip_variazioni(Boolean fl_visto_dip_variazioni) {
	this.fl_visto_dip_variazioni = fl_visto_dip_variazioni;
}
public java.lang.String getStato_invio() {
	return stato_invio;
}
public void setStato_invio(java.lang.String statoInvio) {
	stato_invio = statoInvio;
}


}