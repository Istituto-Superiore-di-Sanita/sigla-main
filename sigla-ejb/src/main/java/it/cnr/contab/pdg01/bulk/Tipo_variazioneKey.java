/*
* Created by Generator 1.0
* Date 14/06/2006
*/
package it.cnr.contab.pdg01.bulk;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.persistency.KeyedPersistent;
public class Tipo_variazioneKey extends OggettoBulk implements KeyedPersistent {
	private java.lang.Integer esercizio;
	private java.lang.String cd_tipo_variazione;
	public Tipo_variazioneKey() {
		super();
	}
	public Tipo_variazioneKey(java.lang.Integer esercizio, java.lang.String cd_tipo_variazione) {
		super();
		this.esercizio=esercizio;
		this.cd_tipo_variazione=cd_tipo_variazione;
	}
	public boolean equalsByPrimaryKey(Object o) {
		if (this== o) return true;
		if (!(o instanceof Tipo_variazioneKey)) return false;
		Tipo_variazioneKey k = (Tipo_variazioneKey) o;
		if (!compareKey(getEsercizio(), k.getEsercizio())) return false;
		if (!compareKey(getCd_tipo_variazione(), k.getCd_tipo_variazione())) return false;
		return true;
	}
	public int primaryKeyHashCode() {
		int i = 0;
		i = i + calculateKeyHashCode(getEsercizio());
		i = i + calculateKeyHashCode(getCd_tipo_variazione());
		return i;
	}
	public void setEsercizio(java.lang.Integer esercizio)  {
		this.esercizio=esercizio;
	}
	public java.lang.Integer getEsercizio () {
		return esercizio;
	}
	public void setCd_tipo_variazione(java.lang.String cd_tipo_variazione)  {
		this.cd_tipo_variazione=cd_tipo_variazione;
	}
	public java.lang.String getCd_tipo_variazione () {
		return cd_tipo_variazione;
	}
}