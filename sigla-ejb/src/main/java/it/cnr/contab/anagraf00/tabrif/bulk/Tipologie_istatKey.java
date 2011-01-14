/*
 * Created by Aurelio's BulkGenerator 1.0
 * Date 23/04/2007
 */
package it.cnr.contab.anagraf00.tabrif.bulk;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.persistency.KeyedPersistent;
public class Tipologie_istatKey extends OggettoBulk implements KeyedPersistent {
	private java.lang.Integer pg_tipologia;
	public Tipologie_istatKey() {
		super();
	}
	public Tipologie_istatKey(java.lang.Integer pg_tipologia) {
		super();
		this.pg_tipologia=pg_tipologia;
	}
	public boolean equalsByPrimaryKey(Object o) {
		if (this== o) return true;
		if (!(o instanceof Tipologie_istatKey)) return false;
		Tipologie_istatKey k = (Tipologie_istatKey) o;
		if (!compareKey(getPg_tipologia(), k.getPg_tipologia())) return false;
		return true;
	}
	public int primaryKeyHashCode() {
		int i = 0;
		i = i + calculateKeyHashCode(getPg_tipologia());
		return i;
	}
	public void setPg_tipologia(java.lang.Integer pg_tipologia)  {
		this.pg_tipologia=pg_tipologia;
	}
	public java.lang.Integer getPg_tipologia() {
		return pg_tipologia;
	}
}