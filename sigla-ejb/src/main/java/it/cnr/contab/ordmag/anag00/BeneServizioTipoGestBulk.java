/*
 * Created by BulkGenerator 2.0 [07/12/2009]
 * Date 26/04/2017
 */
package it.cnr.contab.ordmag.anag00;
import it.cnr.contab.docamm00.tabrif.bulk.Bene_servizioBulk;
public class BeneServizioTipoGestBulk extends BeneServizioTipoGestBase {
	/**
	 * [MAGAZZINO Rappresenta i magazzini utilizzati in gestione ordine e magazzino.]
	 **/
	private MagazzinoBulk magazzino =  new MagazzinoBulk();
	/**
	 * [BENE_SERVIZIO Rappresenta la classificazione di beni e servizi il cui dettaglio � esposto in sede di registrazione delle righe fattura passiva.

Da questa gestione sono ricavati gli elementi per la gestione di magazziono e di inventario dalla registrazione di fatture passive]
	 **/
	private Bene_servizioBulk beneServizio =  new Bene_servizioBulk();
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Table name: BENE_SERVIZIO_TIPO_GEST
	 **/
	public BeneServizioTipoGestBulk() {
		super();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Table name: BENE_SERVIZIO_TIPO_GEST
	 **/
	public BeneServizioTipoGestBulk(java.lang.String cdCds, java.lang.String cdMagazzino, java.lang.String cdBeneServizio, java.sql.Timestamp dtIniValidita) {
		super(cdCds, cdMagazzino, cdBeneServizio, dtIniValidita);
		setMagazzino( new MagazzinoBulk(cdCds,cdMagazzino) );
		setBeneServizio( new Bene_servizioBulk(cdBeneServizio) );
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [Rappresenta i magazzini utilizzati in gestione ordine e magazzino.]
	 **/
	public MagazzinoBulk getMagazzino() {
		return magazzino;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [Rappresenta i magazzini utilizzati in gestione ordine e magazzino.]
	 **/
	public void setMagazzino(MagazzinoBulk magazzino)  {
		this.magazzino=magazzino;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [Rappresenta la classificazione di beni e servizi il cui dettaglio � esposto in sede di registrazione delle righe fattura passiva.

Da questa gestione sono ricavati gli elementi per la gestione di magazziono e di inventario dalla registrazione di fatture passive]
	 **/
	public Bene_servizioBulk getBeneServizio() {
		return beneServizio;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [Rappresenta la classificazione di beni e servizi il cui dettaglio � esposto in sede di registrazione delle righe fattura passiva.

Da questa gestione sono ricavati gli elementi per la gestione di magazziono e di inventario dalla registrazione di fatture passive]
	 **/
	public void setBeneServizio(Bene_servizioBulk beneServizio)  {
		this.beneServizio=beneServizio;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdCds]
	 **/
	public java.lang.String getCdCds() {
		MagazzinoBulk magazzino = this.getMagazzino();
		if (magazzino == null)
			return null;
		return getMagazzino().getCdCds();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdCds]
	 **/
	public void setCdCds(java.lang.String cdCds)  {
		this.getMagazzino().setCdCds(cdCds);
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdMagazzino]
	 **/
	public java.lang.String getCdMagazzino() {
		MagazzinoBulk magazzino = this.getMagazzino();
		if (magazzino == null)
			return null;
		return getMagazzino().getCdMagazzino();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdMagazzino]
	 **/
	public void setCdMagazzino(java.lang.String cdMagazzino)  {
		this.getMagazzino().setCdMagazzino(cdMagazzino);
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdBeneServizio]
	 **/
	public java.lang.String getCdBeneServizio() {
		Bene_servizioBulk beneServizio = this.getBeneServizio();
		if (beneServizio == null)
			return null;
		return getBeneServizio().getCd_bene_servizio();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdBeneServizio]
	 **/
	public void setCdBeneServizio(java.lang.String cdBeneServizio)  {
		this.getBeneServizio().setCd_bene_servizio(cdBeneServizio);
	}
}