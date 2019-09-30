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

/*
* Creted by Generator 1.0
* Date 09/04/2005
*/
package it.cnr.contab.config00.contratto.bulk;
import it.cnr.si.spring.storage.annotation.StoragePolicy;
import it.cnr.si.spring.storage.annotation.StorageProperty;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.persistency.KeyedPersistent;
public class ContrattoKey extends OggettoBulk implements KeyedPersistent {
	@StoragePolicy(name="P:sigla_contratti_aspect:appalti", property=@StorageProperty(name="sigla_contratti_aspect_appalti:esercizio"))
	private java.lang.Integer esercizio;
	@StoragePolicy(name="P:sigla_contratti_aspect:appalti", property=@StorageProperty(name="sigla_contratti_aspect_appalti:stato"))
    private java.lang.String stato;	
	@StoragePolicy(name="P:sigla_contratti_aspect:appalti", 
			property=@StorageProperty(name="sigla_contratti_aspect_appalti:progressivo", converterBeanName="cmis.converter.longToIntegerConverter"))
	private java.lang.Long pg_contratto;
	public ContrattoKey() {
		super();
	}
	public ContrattoKey(java.lang.Integer esercizio, java.lang.String stato, java.lang.Long pg_contratto) {
		super();
		this.esercizio=esercizio;
		this.stato=stato;
		this.pg_contratto=pg_contratto;
	}
	public boolean equalsByPrimaryKey(Object o) {
		if (this== o) return true;
		if (!(o instanceof ContrattoKey)) return false;
		ContrattoKey k = (ContrattoKey) o;
		if (!compareKey(getEsercizio(), k.getEsercizio())) return false;
		if (!compareKey(getStato(), k.getStato())) return false;
		if (!compareKey(getPg_contratto(), k.getPg_contratto())) return false;
		return true;
	}
	public int primaryKeyHashCode() {
		int i = 0;
		i = i + calculateKeyHashCode(getEsercizio());
		i = i + calculateKeyHashCode(getStato());
		i = i + calculateKeyHashCode(getPg_contratto());
		return i;
	}
	public void setEsercizio(java.lang.Integer esercizio)  {
		this.esercizio=esercizio;
	}
	public java.lang.Integer getEsercizio () {
		return esercizio;
	}
	public void setPg_contratto(java.lang.Long pg_contratto)  {
		this.pg_contratto=pg_contratto;
	}
	public java.lang.Long getPg_contratto () {
		return pg_contratto;
	}
	/**
	 * @return
	 */
	public java.lang.String getStato() {
		return stato;
	}

	/**
	 * @param string
	 */
	public void setStato(java.lang.String string) {
		stato = string;
	}

}