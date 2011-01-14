package it.cnr.contab.doccont00.core.bulk;

import java.util.Dictionary;

import com.sun.mail.imap.Utility;

import it.cnr.contab.config00.contratto.bulk.ContrattoBulk;
import 	it.cnr.contab.config00.pdcfin.bulk.*;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.bulk.ValidationException;
import it.cnr.jada.util.action.CRUDBP;

/**
 * Insert the method's description here.
 * Creation date: (12/10/2005 16.00.00)
 * @author: Raffaele Pagano
 */
public class ObbligazioneResBulk extends ObbligazioneBulk {
	Voce_fBulk voce = new Voce_fBulk();
	private Obbligazione_modificaBulk obbligazione_modifica = new Obbligazione_modificaBulk();	
	private boolean saldiDaAggiornare = false;
	
	public final static int LUNGHEZZA_NUMERO_IMPEGNO = 6;

	/**
	 * ObbligazioneResBulk constructor comment.
	 */
	public ObbligazioneResBulk() {
		super();
		initialize();	
	}
	/**
	 * ObbligazioneResBulk constructor comment.
	 * @param cd_cds java.lang.String
	 * @param esercizio java.lang.Integer
	 * @param esercizio_originale java.lang.Integer
	 * @param pg_obbligazione java.lang.Long
	 */
	public ObbligazioneResBulk(String cd_cds, Integer esercizio, Integer esercizio_originale, Long pg_obbligazione) {
		super(cd_cds, esercizio, esercizio_originale, pg_obbligazione);
		initialize();	
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/10/2005 16.00.00)
	 * @return it.cnr.contab.config00.pdcfin.bulk.Voce_fBulk
	 */
	public it.cnr.contab.config00.pdcfin.bulk.Voce_fBulk getVoce() {
		return voce;
	}
	// metodo per inizializzare l'oggetto bulk
	private void initialize () {
		setCd_tipo_documento_cont( Numerazione_doc_contBulk.TIPO_OBB_RES );
		setFl_pgiro( new Boolean( false ));
	}
	/* (non-Javadoc)
	 * @see it.cnr.contab.doccont00.core.bulk.ObbligazioneBulk#initializeForInsert(it.cnr.jada.util.action.CRUDBP, it.cnr.jada.action.ActionContext)
	 */
	public OggettoBulk initializeForInsert(CRUDBP bp, ActionContext context) {
		// TODO Auto-generated method stub
		super.initializeForInsert(bp, context);
		setEsercizio_originale(null);
		return this; 
	}
	protected OggettoBulk initialize(CRUDBP crudbp,	ActionContext actioncontext) {
		super.initialize(crudbp, actioncontext);
		caricaAnniResidui(actioncontext);
		return this; 
	}
	public boolean isROVoce() {
		return voce == null || voce.getCrudStatus() == NORMAL;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (20/06/2003 13.43.47)
	 * @param newVoce it.cnr.contab.config00.pdcfin.bulk.Voce_fBulk
	 */
	public void setVoce(it.cnr.contab.config00.pdcfin.bulk.Voce_fBulk newVoce) {
		voce = newVoce;
	}
	public void validate() throws ValidationException {
		if ( getIm_obbligazione() == null )
			throw new ValidationException( "Il campo IMPORTO � obbligatorio." );
		// controllo su campo ESERCIZIO_ORIGINALE
		if ( getEsercizio_originale() == null || getEsercizio_originale().equals("") )
			throw new ValidationException( "Il campo ESERCIZIO IMPEGNO � obbligatorio." );
		if (getEsercizio_originale().compareTo(getEsercizio())!=-1)
			throw new ValidationException("L'esercizio dell'impegno residuo deve essere inferiore al " + getEsercizio());

		// controllo su campo PG_OBBLIGAZIONE
		if (getPg_obbligazione()==null)
			throw new ValidationException("Il campo NUMERO IMPEGNO � obbligatorio.");

		super.validate();
	}
	public Obbligazione_modificaBulk getObbligazione_modifica() {
		return obbligazione_modifica;
	}
	public void setObbligazione_modifica(
			Obbligazione_modificaBulk obbligazione_modifica) {
		this.obbligazione_modifica = obbligazione_modifica;
	}
	/**
	 * se sono da aggiornare i saldi in modifica perch�
	 * l'obbligazione non proviene da modifiche in documenti
	 * amministrativi, dato che i saldi verrebbero aggiornati
	 * attraverso "deferredSaldi"
	 * 
	 * @return
	 */
	public boolean isSaldiDaAggiornare() {
		return saldiDaAggiornare;
	}
	/**
	 * imposta che sono da aggiornare i saldi in modifica perch�
	 * l'obbligazione non proviene da modifiche in documenti
	 * amministrativi, dato che i saldi verrebbero aggiornati
	 * attraverso "deferredSaldi"
	 * 
	 */
	public void setSaldiDaAggiornare(boolean saldiDaAggiornare) {
		this.saldiDaAggiornare = saldiDaAggiornare;
	}
}