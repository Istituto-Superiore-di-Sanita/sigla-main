/*
 * Created by BulkGenerator 2.0 [07/12/2009]
 * Date 06/12/2012
 */
package it.cnr.contab.config00.bulk;
import it.cnr.jada.persistency.Keyed;
public class CigBase extends CigKey implements Keyed {
//    CD_TERZO_RUP DECIMAL(9,0) NOT NULL
	private java.lang.Integer cdTerzoRup;
 
//    CD_UNITA_ORGANIZZATIVA VARCHAR(30) NOT NULL
	private java.lang.String cdUnitaOrganizzativa;
 
//    DS_CIG VARCHAR(500) NOT NULL
	private java.lang.String dsCig;
 
//    FL_VALIDO CHAR(1)
	private java.lang.Boolean flValido;
 
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Table name: CIG
	 **/
	public CigBase() {
		super();
	}
	public CigBase(java.lang.String cdCig) {
		super(cdCig);
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdTerzoRup]
	 **/
	public java.lang.Integer getCdTerzoRup() {
		return cdTerzoRup;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdTerzoRup]
	 **/
	public void setCdTerzoRup(java.lang.Integer cdTerzoRup)  {
		this.cdTerzoRup=cdTerzoRup;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdUnitaOrganizzativa]
	 **/
	public java.lang.String getCdUnitaOrganizzativa() {
		return cdUnitaOrganizzativa;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdUnitaOrganizzativa]
	 **/
	public void setCdUnitaOrganizzativa(java.lang.String cdUnitaOrganizzativa)  {
		this.cdUnitaOrganizzativa=cdUnitaOrganizzativa;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [dsCig]
	 **/
	public java.lang.String getDsCig() {
		return dsCig;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [dsCig]
	 **/
	public void setDsCig(java.lang.String dsCig)  {
		this.dsCig=dsCig;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [flValido]
	 **/
	public java.lang.Boolean getFlValido() {
		return flValido;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [flValido]
	 **/
	public void setFlValido(java.lang.Boolean flValido)  {
		this.flValido=flValido;
	}
}