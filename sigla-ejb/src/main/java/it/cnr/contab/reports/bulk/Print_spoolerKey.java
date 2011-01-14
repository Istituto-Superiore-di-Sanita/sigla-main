package it.cnr.contab.reports.bulk;

import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.persistency.KeyedPersistent;

public class Print_spoolerKey extends OggettoBulk implements KeyedPersistent {
	// PG_STAMPA DECIMAL(10,0) NOT NULL (PK)
	private java.lang.Long pgStampa;

	public Print_spoolerKey() {
		super();
	}

	public Print_spoolerKey(java.lang.Long pg_stampa) {
		super();
		this.pgStampa = pg_stampa;
	}

	public boolean equalsByPrimaryKey(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Print_spoolerKey))
			return false;
		Print_spoolerKey k = (Print_spoolerKey) o;
		if (!compareKey(getPgStampa(), k.getPgStampa()))
			return false;
		return true;
	}

	/*
	 * Getter dell'attributo pg_stampa
	 */
	public java.lang.Long getPgStampa() {
		return pgStampa;
	}

	public int primaryKeyHashCode() {
		return calculateKeyHashCode(getPgStampa());
	}

	/*
	 * Setter dell'attributo pg_stampa
	 */
	public void setPgStampa(java.lang.Long pg_stampa) {
		this.pgStampa = pg_stampa;
	}
}