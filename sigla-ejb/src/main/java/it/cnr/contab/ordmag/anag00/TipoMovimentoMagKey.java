/*
 * Created by BulkGenerator 2.0 [07/12/2009]
 * Date 26/04/2017
 */
package it.cnr.contab.ordmag.anag00;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.persistency.KeyedPersistent;
public class TipoMovimentoMagKey extends OggettoBulk implements KeyedPersistent {
	private java.lang.String cdCds;
	private java.lang.String cdTipoMovimento;
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Table name: TIPO_MOVIMENTO_MAG
	 **/
	public TipoMovimentoMagKey() {
		super();
	}
	public TipoMovimentoMagKey(java.lang.String cdCds, java.lang.String cdTipoMovimento) {
		super();
		this.cdCds=cdCds;
		this.cdTipoMovimento=cdTipoMovimento;
	}
	public boolean equalsByPrimaryKey(Object o) {
		if (this== o) return true;
		if (!(o instanceof TipoMovimentoMagKey)) return false;
		TipoMovimentoMagKey k = (TipoMovimentoMagKey) o;
		if (!compareKey(getCdCds(), k.getCdCds())) return false;
		if (!compareKey(getCdTipoMovimento(), k.getCdTipoMovimento())) return false;
		return true;
	}
	public int primaryKeyHashCode() {
		int i = 0;
		i = i + calculateKeyHashCode(getCdCds());
		i = i + calculateKeyHashCode(getCdTipoMovimento());
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
	 * Restituisce il valore di: [cdTipoMovimento]
	 **/
	public void setCdTipoMovimento(java.lang.String cdTipoMovimento)  {
		this.cdTipoMovimento=cdTipoMovimento;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdTipoMovimento]
	 **/
	public java.lang.String getCdTipoMovimento() {
		return cdTipoMovimento;
	}
}