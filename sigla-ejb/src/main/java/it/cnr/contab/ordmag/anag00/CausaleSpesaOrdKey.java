/*
 * Created by BulkGenerator 2.0 [07/12/2009]
 * Date 26/04/2017
 */
package it.cnr.contab.ordmag.anag00;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.persistency.KeyedPersistent;
public class CausaleSpesaOrdKey extends OggettoBulk implements KeyedPersistent {
	private java.lang.String cdCds;
	private java.lang.Integer esercizio;
	private java.lang.String cdCausaleSpesa;
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Table name: CAUSALE_SPESA_ORD
	 **/
	public CausaleSpesaOrdKey() {
		super();
	}
	public CausaleSpesaOrdKey(java.lang.String cdCds, java.lang.Integer esercizio, java.lang.String cdCausaleSpesa) {
		super();
		this.cdCds=cdCds;
		this.esercizio=esercizio;
		this.cdCausaleSpesa=cdCausaleSpesa;
	}
	public boolean equalsByPrimaryKey(Object o) {
		if (this== o) return true;
		if (!(o instanceof CausaleSpesaOrdKey)) return false;
		CausaleSpesaOrdKey k = (CausaleSpesaOrdKey) o;
		if (!compareKey(getCdCds(), k.getCdCds())) return false;
		if (!compareKey(getEsercizio(), k.getEsercizio())) return false;
		if (!compareKey(getCdCausaleSpesa(), k.getCdCausaleSpesa())) return false;
		return true;
	}
	public int primaryKeyHashCode() {
		int i = 0;
		i = i + calculateKeyHashCode(getCdCds());
		i = i + calculateKeyHashCode(getEsercizio());
		i = i + calculateKeyHashCode(getCdCausaleSpesa());
		return i;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdCds]
	 **/
	public void setCdCds(java.lang.String cdCds)  {
		this.cdCds=cdCds;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdCds]
	 **/
	public java.lang.String getCdCds() {
		return cdCds;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [esercizio]
	 **/
	public void setEsercizio(java.lang.Integer esercizio)  {
		this.esercizio=esercizio;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [esercizio]
	 **/
	public java.lang.Integer getEsercizio() {
		return esercizio;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdCausaleSpesa]
	 **/
	public void setCdCausaleSpesa(java.lang.String cdCausaleSpesa)  {
		this.cdCausaleSpesa=cdCausaleSpesa;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdCausaleSpesa]
	 **/
	public java.lang.String getCdCausaleSpesa() {
		return cdCausaleSpesa;
	}
}