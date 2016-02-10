/*
 * Created by BulkGenerator 2.0 [07/12/2009]
 * Date 02/02/2012
 */
package it.cnr.contab.bilaterali00.bulk;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.persistency.KeyedPersistent;
public class Blt_autorizzatiKey extends OggettoBulk implements KeyedPersistent {
	private java.lang.String cdAccordo;
	private java.lang.String cdProgetto;
	private java.lang.Integer cdTerzo;
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Table name: BLT_AUTORIZZATI
	 **/
	public Blt_autorizzatiKey() {
		super();
	}
	public Blt_autorizzatiKey(java.lang.String cdAccordo, java.lang.String cdProgetto, java.lang.Integer cdTerzo) {
		super();
		this.cdAccordo=cdAccordo;
		this.cdProgetto=cdProgetto;
		this.cdTerzo=cdTerzo;
	}
	public boolean equalsByPrimaryKey(Object o) {
		if (this== o) return true;
		if (!(o instanceof Blt_autorizzatiKey)) return false;
		Blt_autorizzatiKey k = (Blt_autorizzatiKey) o;
		if (!compareKey(getCdAccordo(), k.getCdAccordo())) return false;
		if (!compareKey(getCdProgetto(), k.getCdProgetto())) return false;
		if (!compareKey(getCdTerzo(), k.getCdTerzo())) return false;
		return true;
	}
	public int primaryKeyHashCode() {
		int i = 0;
		i = i + calculateKeyHashCode(getCdAccordo());
		i = i + calculateKeyHashCode(getCdProgetto());
		i = i + calculateKeyHashCode(getCdTerzo());
		return i;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdAccordo]
	 **/
	public void setCdAccordo(java.lang.String cdAccordo)  {
		this.cdAccordo=cdAccordo;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdAccordo]
	 **/
	public java.lang.String getCdAccordo() {
		return cdAccordo;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdProgetto]
	 **/
	public void setCdProgetto(java.lang.String cdProgetto)  {
		this.cdProgetto=cdProgetto;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdProgetto]
	 **/
	public java.lang.String getCdProgetto() {
		return cdProgetto;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdTerzo]
	 **/
	public void setCdTerzo(java.lang.Integer cdTerzo)  {
		this.cdTerzo=cdTerzo;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdTerzo]
	 **/
	public java.lang.Integer getCdTerzo() {
		return cdTerzo;
	}
}