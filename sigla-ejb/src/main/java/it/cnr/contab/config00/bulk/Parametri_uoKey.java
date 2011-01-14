/*
* Created by Generator 1.0
* Date 17/11/2005
*/
package it.cnr.contab.config00.bulk;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.persistency.KeyedPersistent;
public class Parametri_uoKey extends OggettoBulk implements KeyedPersistent {
	private java.lang.String cd_unita_organizzativa;
	private java.lang.Integer esercizio;
	public Parametri_uoKey() {
		super();
	}
	public Parametri_uoKey(java.lang.String cd_unita_organizzativa, java.lang.Integer esercizio) {
		super();
		this.cd_unita_organizzativa=cd_unita_organizzativa;
		this.esercizio=esercizio;
	}
	public boolean equalsByPrimaryKey(Object o) {
		if (this== o) return true;
		if (!(o instanceof Parametri_uoKey)) return false;
		Parametri_uoKey k = (Parametri_uoKey) o;
		if (!compareKey(getCd_unita_organizzativa(), k.getCd_unita_organizzativa())) return false;
		if (!compareKey(getEsercizio(), k.getEsercizio())) return false;
		return true;
	}
	public int primaryKeyHashCode() {
		int i = 0;
		i = i + calculateKeyHashCode(getCd_unita_organizzativa());
		i = i + calculateKeyHashCode(getEsercizio());
		return i;
	}
	public void setCd_unita_organizzativa(java.lang.String cd_unita_organizzativa)  {
		this.cd_unita_organizzativa=cd_unita_organizzativa;
	}
	public java.lang.String getCd_unita_organizzativa () {
		return cd_unita_organizzativa;
	}
	public void setEsercizio(java.lang.Integer esercizio)  {
		this.esercizio=esercizio;
	}
	public java.lang.Integer getEsercizio () {
		return esercizio;
	}
}