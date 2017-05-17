/*
 * Created by BulkGenerator 2.0 [07/12/2009]
 * Date 12/05/2017
 */
package it.cnr.contab.ordmag.richieste.bulk;
import it.cnr.jada.persistency.Keyed;
public class RichiestaUopBase extends RichiestaUopKey implements Keyed {
//    DATA_RICHIESTA TIMESTAMP(7) NOT NULL
	private java.sql.Timestamp dataRichiesta;
 
//    STATO DECIMAL(3,0) NOT NULL
	private java.lang.Integer stato;
 
//    DS_RICHIESTA VARCHAR(300)
	private java.lang.String dsRichiesta;
 
//    NOTA VARCHAR(2000)
	private java.lang.String nota;
 
//    CD_UNITA_OPERATIVA_DEST VARCHAR(30) NOT NULL
	private java.lang.String cdUnitaOperativaDest;
 
//    DATA_INVIO TIMESTAMP(7)
	private java.sql.Timestamp dataInvio;
 
//    DT_CANCELLAZIONE TIMESTAMP(7)
	private java.sql.Timestamp dtCancellazione;
 
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Table name: RICHIESTA_UOP
	 **/
	public RichiestaUopBase() {
		super();
	}
	public RichiestaUopBase(java.lang.String cdCds, java.lang.String cdUnitaOperativa, java.lang.Integer esercizio, java.lang.String cdNumeratore, java.lang.Integer numero) {
		super(cdCds, cdUnitaOperativa, esercizio, cdNumeratore, numero);
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [dataRichiesta]
	 **/
	public java.sql.Timestamp getDataRichiesta() {
		return dataRichiesta;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [dataRichiesta]
	 **/
	public void setDataRichiesta(java.sql.Timestamp dataRichiesta)  {
		this.dataRichiesta=dataRichiesta;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [stato]
	 **/
	public java.lang.Integer getStato() {
		return stato;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [stato]
	 **/
	public void setStato(java.lang.Integer stato)  {
		this.stato=stato;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [dsRichiesta]
	 **/
	public java.lang.String getDsRichiesta() {
		return dsRichiesta;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [dsRichiesta]
	 **/
	public void setDsRichiesta(java.lang.String dsRichiesta)  {
		this.dsRichiesta=dsRichiesta;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [nota]
	 **/
	public java.lang.String getNota() {
		return nota;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [nota]
	 **/
	public void setNota(java.lang.String nota)  {
		this.nota=nota;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdUnitaOperativaDest]
	 **/
	public java.lang.String getCdUnitaOperativaDest() {
		return cdUnitaOperativaDest;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdUnitaOperativaDest]
	 **/
	public void setCdUnitaOperativaDest(java.lang.String cdUnitaOperativaDest)  {
		this.cdUnitaOperativaDest=cdUnitaOperativaDest;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [dataInvio]
	 **/
	public java.sql.Timestamp getDataInvio() {
		return dataInvio;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [dataInvio]
	 **/
	public void setDataInvio(java.sql.Timestamp dataInvio)  {
		this.dataInvio=dataInvio;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [dtCancellazione]
	 **/
	public java.sql.Timestamp getDtCancellazione() {
		return dtCancellazione;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [dtCancellazione]
	 **/
	public void setDtCancellazione(java.sql.Timestamp dtCancellazione)  {
		this.dtCancellazione=dtCancellazione;
	}
}