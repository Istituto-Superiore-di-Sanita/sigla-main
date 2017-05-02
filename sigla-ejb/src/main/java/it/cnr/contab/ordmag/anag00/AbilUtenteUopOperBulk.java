/*
 * Created by BulkGenerator 2.0 [07/12/2009]
 * Date 26/04/2017
 */
package it.cnr.contab.ordmag.anag00;
import it.cnr.contab.utenze00.bulk.UtenteBulk;
public class AbilUtenteUopOperBulk extends AbilUtenteUopOperBase {
	/**
	 * [UTENTE Contiene gli utenti dell'applicazione.]
	 **/
	private UtenteBulk utente =  new UtenteBulk();
	/**
	 * [UNITA_OPERATIVA_ORD Rappresenta le unit� operative utilizzate in gestione ordine e magazzino.]
	 **/
	private UnitaOperativaOrdBulk unitaOperativaOrd =  new UnitaOperativaOrdBulk();
	/**
	 * [TIPO_OPERAZIONE_ORD Rappresenta l'anagrafica dei tipi operazione degli ordini.]
	 **/
	private TipoOperazioneOrdBulk tipoOperazioneOrd =  new TipoOperazioneOrdBulk();
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Table name: ABIL_UTENTE_UOP_OPER
	 **/
	public AbilUtenteUopOperBulk() {
		super();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Table name: ABIL_UTENTE_UOP_OPER
	 **/
	public AbilUtenteUopOperBulk(java.lang.String cdUtente, java.lang.String cdUnitaOperativa, java.lang.Integer esercizio, java.lang.String cdTipoOperazione) {
		super(cdUtente, cdUnitaOperativa, esercizio, cdTipoOperazione);
		setUtente( new UtenteBulk(cdUtente) );
		setUnitaOperativaOrd( new UnitaOperativaOrdBulk(cdUnitaOperativa) );
		setTipoOperazioneOrd( new TipoOperazioneOrdBulk(cdTipoOperazione) );
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [Contiene gli utenti dell'applicazione.]
	 **/
	public UtenteBulk getUtente() {
		return utente;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [Contiene gli utenti dell'applicazione.]
	 **/
	public void setUtente(UtenteBulk utente)  {
		this.utente=utente;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [Rappresenta le unit� operative utilizzate in gestione ordine e magazzino.]
	 **/
	public UnitaOperativaOrdBulk getUnitaOperativaOrd() {
		return unitaOperativaOrd;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [Rappresenta le unit� operative utilizzate in gestione ordine e magazzino.]
	 **/
	public void setUnitaOperativaOrd(UnitaOperativaOrdBulk unitaOperativaOrd)  {
		this.unitaOperativaOrd=unitaOperativaOrd;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [Rappresenta l'anagrafica dei tipi operazione degli ordini.]
	 **/
	public TipoOperazioneOrdBulk getTipoOperazioneOrd() {
		return tipoOperazioneOrd;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [Rappresenta l'anagrafica dei tipi operazione degli ordini.]
	 **/
	public void setTipoOperazioneOrd(TipoOperazioneOrdBulk tipoOperazioneOrd)  {
		this.tipoOperazioneOrd=tipoOperazioneOrd;
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdUtente]
	 **/
	public java.lang.String getCdUtente() {
		UtenteBulk utente = this.getUtente();
		if (utente == null)
			return null;
		return getUtente().getCd_utente();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdUtente]
	 **/
	public void setCdUtente(java.lang.String cdUtente)  {
		this.getUtente().setCd_utente(cdUtente);
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdUnitaOperativa]
	 **/
	public java.lang.String getCdUnitaOperativa() {
		UnitaOperativaOrdBulk unitaOperativaOrd = this.getUnitaOperativaOrd();
		if (unitaOperativaOrd == null)
			return null;
		return getUnitaOperativaOrd().getCdUnitaOperativa();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdUnitaOperativa]
	 **/
	public void setCdUnitaOperativa(java.lang.String cdUnitaOperativa)  {
		this.getUnitaOperativaOrd().setCdUnitaOperativa(cdUnitaOperativa);
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Restituisce il valore di: [cdTipoOperazione]
	 **/
	public java.lang.String getCdTipoOperazione() {
		TipoOperazioneOrdBulk tipoOperazioneOrd = this.getTipoOperazioneOrd();
		if (tipoOperazioneOrd == null)
			return null;
		return getTipoOperazioneOrd().getCdTipoOperazione();
	}
	/**
	 * Created by BulkGenerator 2.0 [07/12/2009]
	 * Setta il valore di: [cdTipoOperazione]
	 **/
	public void setCdTipoOperazione(java.lang.String cdTipoOperazione)  {
		this.getTipoOperazioneOrd().setCdTipoOperazione(cdTipoOperazione);
	}
}