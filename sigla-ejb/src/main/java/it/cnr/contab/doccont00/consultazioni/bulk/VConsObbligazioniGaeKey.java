/*
 * Created by BulkGenerator 2.0 [07/12/2009]
 * Date 27/01/2015
 */
package it.cnr.contab.doccont00.consultazioni.bulk;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.persistency.KeyedPersistent;
public class VConsObbligazioniGaeKey extends OggettoBulk implements KeyedPersistent {
	private java.lang.String cdLineaAttivita;
	private java.lang.String cdCds;
	private java.lang.Integer esercizio;
	private java.lang.Integer esercizioOriginale;
	private java.lang.Long pgObbligazione;
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Table name: CONS_OBBLIGAZIONI
	 **/
	public VConsObbligazioniGaeKey() {
		super();
	}
	public VConsObbligazioniGaeKey(java.lang.String cdCds, java.lang.String cdLineaAttivita, java.lang.Integer esercizio, java.lang.Integer esercizioOriginale, java.lang.Long pgObbligazione) {
		super();
		this.cdCds=cdCds;
		this.cdLineaAttivita=cdLineaAttivita;
		this.esercizio=esercizio;
		this.esercizioOriginale=esercizioOriginale;
		this.pgObbligazione=pgObbligazione;
	}
	public boolean equalsByPrimaryKey(Object o) {
		if (this== o) return true;
		if (!(o instanceof VConsObbligazioniGaeKey)) return false;
		VConsObbligazioniGaeKey k = (VConsObbligazioniGaeKey) o;
		if (!compareKey(getCdCds(), k.getCdCds())) return false;
		if (!compareKey(getCdLineaAttivita(), k.getCdLineaAttivita())) return false;
		if (!compareKey(getEsercizio(), k.getEsercizio())) return false;
		if (!compareKey(getEsercizioOriginale(), k.getEsercizioOriginale())) return false;
		if (!compareKey(getPgObbligazione(), k.getPgObbligazione())) return false;
		return true;
	}
	public int primaryKeyHashCode() {
		int i = 0;
		i = i + calculateKeyHashCode(getCdCds());
		i = i + calculateKeyHashCode(getCdLineaAttivita());
		i = i + calculateKeyHashCode(getEsercizio());
		i = i + calculateKeyHashCode(getEsercizioOriginale());
		i = i + calculateKeyHashCode(getPgObbligazione());
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
	 * Restituisce il valore di: [esercizioOriginale]
	 **/
	public void setEsercizioOriginale(java.lang.Integer esercizioOriginale)  {
		this.esercizioOriginale=esercizioOriginale;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [esercizioOriginale]
	 **/
	public java.lang.Integer getEsercizioOriginale() {
		return esercizioOriginale;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [pgObbligazione]
	 **/
	public void setPgObbligazione(java.lang.Long pgObbligazione)  {
		this.pgObbligazione=pgObbligazione;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [pgObbligazione]
	 **/
	public java.lang.Long getPgObbligazione() {
		return pgObbligazione;
	}
	public java.lang.String getCdLineaAttivita() {
		return cdLineaAttivita;
	}
	public void setCdLineaAttivita(java.lang.String cdLineaAttivita) {
		this.cdLineaAttivita = cdLineaAttivita;
	}
}