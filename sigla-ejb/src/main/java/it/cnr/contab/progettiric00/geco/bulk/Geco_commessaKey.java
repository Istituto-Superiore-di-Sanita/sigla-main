/*
 * Created by Aurelio's BulkGenerator 1.0
 * Date 27/11/2006
 */
package it.cnr.contab.progettiric00.geco.bulk;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.persistency.KeyedPersistent;
public class Geco_commessaKey extends OggettoBulk implements KeyedPersistent {
	private java.lang.Long id_comm;
	private java.lang.Long esercizio;
	private java.lang.String fase;
	public Geco_commessaKey() {
		super();
	}
	public Geco_commessaKey(java.lang.Long id_comm, java.lang.Long esercizio, java.lang.String fase) {
		super();
		this.id_comm=id_comm;
		this.esercizio=esercizio;
		this.fase=fase;
	}
	public boolean equalsByPrimaryKey(Object o) {
		if (this== o) return true;
		if (!(o instanceof Geco_commessaKey)) return false;
		Geco_commessaKey k = (Geco_commessaKey) o;
		if (!compareKey(getId_comm(), k.getId_comm())) return false;
		if (!compareKey(getEsercizio(), k.getEsercizio())) return false;
		if (!compareKey(getFase(), k.getFase())) return false;
		return true;
	}
	public int primaryKeyHashCode() {
		int i = 0;
		i = i + calculateKeyHashCode(getId_comm());
		i = i + calculateKeyHashCode(getEsercizio());
		i = i + calculateKeyHashCode(getFase());
		return i;
	}
	public void setId_comm(java.lang.Long id_comm)  {
		this.id_comm=id_comm;
	}
	public java.lang.Long getId_comm() {
		return id_comm;
	}
	public void setEsercizio(java.lang.Long esercizio)  {
		this.esercizio=esercizio;
	}
	public java.lang.Long getEsercizio() {
		return esercizio;
	}
	public void setFase(java.lang.String fase)  {
		this.fase=fase;
	}
	public java.lang.String getFase() {
		return fase;
	}
}