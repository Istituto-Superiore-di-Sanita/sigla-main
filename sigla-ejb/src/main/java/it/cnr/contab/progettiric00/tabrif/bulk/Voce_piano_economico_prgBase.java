/*
 * Copyright (C) 2019  Consiglio Nazionale delle Ricerche
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package it.cnr.contab.progettiric00.tabrif.bulk;

import it.cnr.jada.persistency.Keyed;

public class Voce_piano_economico_prgBase extends Voce_piano_economico_prgKey implements Keyed {
	// DS_VOCE_PIANO VARCHAR(100) NOT NULL
	private java.lang.String ds_voce_piano;

	// TIPOLOGIA CHAR(3 BYTE) NULL
	private java.lang.String tipologia;

	// FL_LINK_VOCI_BILANCIO_ASSOCIATE CHAR(1 BYTE) DEFAULT 'N' NOT NULL
	private java.lang.Boolean fl_link_vocibil_associate;

	// FL_ADD_VOCIBIL CHAR(1 BYTE) DEFAULT 'N' NOT NULL
	private java.lang.Boolean fl_add_vocibil;

	// FL_ALL_PREV_FIN CHAR(1) NOT NULL
	private Boolean flAllPrevFin;
	
	// FL_VALIDO CHAR(1 BYTE) DEFAULT 'Y' NOT NULL
	private java.lang.Boolean fl_valido;

	public Voce_piano_economico_prgBase() {
		super();
	}
	
	public Voce_piano_economico_prgBase(java.lang.String cd_unita_organizzativa, java.lang.String cd_voce_piano) {
		super(cd_unita_organizzativa, cd_voce_piano);
	}
	
	/* 
	 * Getter dell'attributo ds_voce_piano
	 */
	public java.lang.String getDs_voce_piano() {
		return ds_voce_piano;
	}
	
	/* 
	 * Setter dell'attributo ds_voce_piano
	 */
	public void setDs_voce_piano(java.lang.String ds_voce_piano) {
		this.ds_voce_piano = ds_voce_piano;
	}

	public java.lang.String getTipologia() {
		return tipologia;
	}
	
	public void setTipologia(java.lang.String tipologia) {
		this.tipologia = tipologia;
	}
	
	public java.lang.Boolean getFl_link_vocibil_associate() {
		return fl_link_vocibil_associate;
	}
	
	public void setFl_link_vocibil_associate(java.lang.Boolean fl_link_vocibil_associate) {
		this.fl_link_vocibil_associate = fl_link_vocibil_associate;
	}
	
	public java.lang.Boolean getFl_add_vocibil() {
		return fl_add_vocibil;
	}
	
	public void setFl_add_vocibil(java.lang.Boolean fl_add_vocibil) {
		this.fl_add_vocibil = fl_add_vocibil;
	}
	
	public Boolean getFlAllPrevFin() {
		return flAllPrevFin;
	}
	
	public void setFlAllPrevFin(Boolean flAllPrevFin) {
		this.flAllPrevFin = flAllPrevFin;
	}
	
	public java.lang.Boolean getFl_valido() {
		return fl_valido;
	}
	
	public void setFl_valido(java.lang.Boolean fl_valido) {
		this.fl_valido = fl_valido;
	}
}
