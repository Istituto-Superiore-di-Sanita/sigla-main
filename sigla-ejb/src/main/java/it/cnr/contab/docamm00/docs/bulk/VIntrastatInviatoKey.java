/*
 * Created by BulkGenerator 2.0 [07/12/2009]
 * Date 17/02/2011
 */
package it.cnr.contab.docamm00.docs.bulk;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.persistency.KeyedPersistent;
public class VIntrastatInviatoKey extends OggettoBulk implements KeyedPersistent {
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Table name: V_INTRASTAT_INVIATO
	 **/
	
	private java.lang.Integer esercizio;
	
//  UO VARCHAR(30)
	private java.lang.String uo;
 
//    PROGRESSIVO DECIMAL(10,0)
	private java.lang.Long progressivo;
	
//  PROTOCOLLO_INVIO DECIMAL(6,0)
	private java.lang.Long protocollo_invio;
 
//    RIGA_INVIO DECIMAL(5,0)
	private java.lang.Long riga_invio;

	public java.lang.Integer getEsercizio() {
		return esercizio;
	}
	public void setEsercizio(java.lang.Integer esercizio) {
		this.esercizio = esercizio;
	}
	public java.lang.String getUo() {
		return uo;
	}
	public void setUo(java.lang.String uo) {
		this.uo = uo;
	}
	public java.lang.Long getProgressivo() {
		return progressivo;
	}
	public void setProgressivo(java.lang.Long progressivo) {
		this.progressivo = progressivo;
	}
	public java.lang.Long getProtocollo_invio() {
		return protocollo_invio;
	}
	public void setProtocollo_invio(java.lang.Long protocolloInvio) {
		protocollo_invio = protocolloInvio;
	}
	public java.lang.Long getRiga_invio() {
		return riga_invio;
	}
	public void setRiga_invio(java.lang.Long rigaInvio) {
		riga_invio = rigaInvio;
	}
	
	public VIntrastatInviatoKey() {
		super();
	}
	public VIntrastatInviatoKey(java.lang.String uo,java.lang.Integer esercizio,java.lang.Long progressivo,java.lang.Long protocollo_invio,java.lang.Long riga_invio) {
		super();
		this.uo = uo;
		this.esercizio = esercizio;
		this.progressivo = progressivo;
		this.protocollo_invio = protocollo_invio;
		this.riga_invio =riga_invio;
	}
	public boolean equalsByPrimaryKey(Object o) {
		if (this== o) return true;
		if (!(o instanceof VIntrastatInviatoKey)) return false;
		VIntrastatInviatoKey k = (VIntrastatInviatoKey) o;
		if(!compareKey(getUo(),k.getUo())) return false;
		if(!compareKey(getEsercizio(),k.getEsercizio())) return false;
		if(!compareKey(getProgressivo(),k.getProgressivo())) return false;
		if(!compareKey(getProtocollo_invio(),k.getProtocollo_invio())) return false;
		if(!compareKey(getRiga_invio(),k.getRiga_invio())) return false;
		return true;
	}
	public int primaryKeyHashCode() {
		return calculateKeyHashCode(getUo())+
			calculateKeyHashCode(getEsercizio())+
			calculateKeyHashCode(getProgressivo())+
			calculateKeyHashCode(getProtocollo_invio())+
			calculateKeyHashCode(getRiga_invio());	
	}
}