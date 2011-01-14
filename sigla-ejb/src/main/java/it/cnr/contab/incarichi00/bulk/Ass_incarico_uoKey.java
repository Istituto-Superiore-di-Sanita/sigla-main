/*
* Creted by Generator 1.0
* Date 13/04/2005
*/
package it.cnr.contab.incarichi00.bulk;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.persistency.KeyedPersistent;
public class Ass_incarico_uoKey extends OggettoBulk implements KeyedPersistent {
	private java.lang.Integer esercizio;
	private java.lang.Long pg_repertorio;
	private java.lang.String cd_unita_organizzativa;
	public Ass_incarico_uoKey() {
		super();
	}
	public Ass_incarico_uoKey(java.lang.Integer esercizio, java.lang.Long pg_repertorio, java.lang.String cd_unita_organizzativa) {
		super();
		this.esercizio=esercizio;
		this.pg_repertorio=pg_repertorio;
		this.cd_unita_organizzativa=cd_unita_organizzativa;
	}
	public boolean equalsByPrimaryKey(Object o) {
		if (this== o) return true;
		if (!(o instanceof Ass_incarico_uoKey)) return false;
		Ass_incarico_uoKey k = (Ass_incarico_uoKey) o;
		if (!compareKey(getEsercizio(), k.getEsercizio())) return false;
		if (!compareKey(getPg_repertorio(), k.getPg_repertorio())) return false;
		if (!compareKey(getCd_unita_organizzativa(), k.getCd_unita_organizzativa())) return false;
		return true;
	}
	public int primaryKeyHashCode() {
		int i = 0;
		i = i + calculateKeyHashCode(getEsercizio());
		i = i + calculateKeyHashCode(getPg_repertorio());
		i = i + calculateKeyHashCode(getCd_unita_organizzativa());
		return i;
	}
	public void setEsercizio(java.lang.Integer esercizio)  {
		this.esercizio=esercizio;
	}
	public java.lang.Integer getEsercizio () {
		return esercizio;
	}
	public void setPg_repertorio(java.lang.Long pg_repertorio)  {
		this.pg_repertorio=pg_repertorio;
	}
	public java.lang.Long getPg_repertorio () {
		return pg_repertorio;
	}
	public void setCd_unita_organizzativa(java.lang.String cd_unita_organizzativa)  {
		this.cd_unita_organizzativa=cd_unita_organizzativa;
	}
	public java.lang.String getCd_unita_organizzativa () {
		return cd_unita_organizzativa;
	}
}