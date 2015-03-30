/*
 * Created by BulkGenerator 2.0 [07/12/2009]
 * Date 23/02/2015
 */
package it.cnr.contab.docamm00.fatturapa.bulk;
import it.cnr.contab.docamm00.tabrif.bulk.Voce_ivaBulk;
public class DocumentoEleIvaBulk extends DocumentoEleIvaBase {
	/**
	 * [DOCUMENTO_ELE_TESTATA Documento elettronico di trasmissione testata]
	 **/
	private DocumentoEleTestataBulk documentoEleTestata =  new DocumentoEleTestataBulk();
	/**
	 * [VOCE_IVA La tabella iva contiene i codici e le aliquote dell'iva, commerciale o istituzionale, registrata nei dettagli della fattura attiva e passiva. Questa entità si riferisce alla normativa vigente sull'iva.]
	 **/
	private Voce_ivaBulk voceIva =  new Voce_ivaBulk();
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Table name: DOCUMENTO_ELE_IVA
	 **/
	public DocumentoEleIvaBulk() {
		super();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Table name: DOCUMENTO_ELE_IVA
	 **/
	public DocumentoEleIvaBulk(java.lang.String idPaese, java.lang.String idCodice, java.lang.Long identificativoSdi, java.lang.Long progressivo, java.lang.Long progressivoIva) {
		super(idPaese, idCodice, identificativoSdi, progressivo, progressivoIva);
		setDocumentoEleTestata( new DocumentoEleTestataBulk(idPaese,idCodice,identificativoSdi,progressivo) );
	}
	public DocumentoEleIvaBulk(DocumentoEleTestataBulk documentoEleTestata) {
		setDocumentoEleTestata(documentoEleTestata);
	}

	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [La tabella iva contiene i codici e le aliquote dell'iva, commerciale o istituzionale, registrata nei dettagli della fattura attiva e passiva. Questa entità si riferisce alla normativa vigente sull'iva.]
	 **/
	public Voce_ivaBulk getVoceIva() {
		return voceIva;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [La tabella iva contiene i codici e le aliquote dell'iva, commerciale o istituzionale, registrata nei dettagli della fattura attiva e passiva. Questa entità si riferisce alla normativa vigente sull'iva.]
	 **/
	public void setVoceIva(Voce_ivaBulk voceIva)  {
		this.voceIva=voceIva;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdVoceIva]
	 **/
	public java.lang.String getCdVoceIva() {
		Voce_ivaBulk voceIva = this.getVoceIva();
		if (voceIva == null)
			return null;
		return getVoceIva().getCd_voce_iva();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdVoceIva]
	 **/
	public void setCdVoceIva(java.lang.String cdVoceIva)  {
		this.getVoceIva().setCd_voce_iva(cdVoceIva);
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [Documento elettronico di trasmissione testata]
	 **/
	public DocumentoEleTestataBulk getDocumentoEleTestata() {
		return documentoEleTestata;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [Documento elettronico di trasmissione testata]
	 **/
	public void setDocumentoEleTestata(DocumentoEleTestataBulk documentoEleTestata)  {
		this.documentoEleTestata=documentoEleTestata;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [idPaese]
	 **/
	public java.lang.String getIdPaese() {
		DocumentoEleTestataBulk documentoEleTestata = this.getDocumentoEleTestata();
		if (documentoEleTestata == null)
			return null;
		return getDocumentoEleTestata().getIdPaese();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [idPaese]
	 **/
	public void setIdPaese(java.lang.String idPaese)  {
		this.getDocumentoEleTestata().setIdPaese(idPaese);
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [idCodice]
	 **/
	public java.lang.String getIdCodice() {
		DocumentoEleTestataBulk documentoEleTestata = this.getDocumentoEleTestata();
		if (documentoEleTestata == null)
			return null;
		return getDocumentoEleTestata().getIdCodice();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [idCodice]
	 **/
	public void setIdCodice(java.lang.String idCodice)  {
		this.getDocumentoEleTestata().setIdCodice(idCodice);
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [identificativoSdi]
	 **/
	public java.lang.Long getIdentificativoSdi() {
		DocumentoEleTestataBulk documentoEleTestata = this.getDocumentoEleTestata();
		if (documentoEleTestata == null)
			return null;
		return getDocumentoEleTestata().getIdentificativoSdi();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [identificativoSdi]
	 **/
	public void setIdentificativoSdi(java.lang.Long identificativoSdi)  {
		this.getDocumentoEleTestata().setIdentificativoSdi(identificativoSdi);
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [progressivo]
	 **/
	public java.lang.Long getProgressivo() {
		DocumentoEleTestataBulk documentoEleTestata = this.getDocumentoEleTestata();
		if (documentoEleTestata == null)
			return null;
		return getDocumentoEleTestata().getProgressivo();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [progressivo]
	 **/
	public void setProgressivo(java.lang.Long progressivo)  {
		this.getDocumentoEleTestata().setProgressivo(progressivo);
	}	
}