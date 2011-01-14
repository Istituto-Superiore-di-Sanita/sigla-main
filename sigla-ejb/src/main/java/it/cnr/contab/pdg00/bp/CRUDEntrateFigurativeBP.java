package it.cnr.contab.pdg00.bp;
/**
 * Business Process per la gestione dei Ricavi caricati da altro CDR
 */

public class CRUDEntrateFigurativeBP extends it.cnr.jada.util.action.CRUDListaBP {

	private it.cnr.contab.config00.sto.bulk.CdrBulk centro_responsabilita;

public CRUDEntrateFigurativeBP() {
	super();
}

public CRUDEntrateFigurativeBP(String function) {
	super(function);
}

/**
 * <!-- @TODO: da completare -->
 * 
 *
 * @param function	La funzione con cui � stato creato il BusinessProcess
 * @param cdr	
 */
public CRUDEntrateFigurativeBP(String function, it.cnr.contab.config00.sto.bulk.CdrBulk cdr) {
	super(function);
	setCentro_responsabilita(cdr);
}

public it.cnr.jada.bulk.OggettoBulk createEmptyModelForFreeSearch(it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException {
	try {
		it.cnr.contab.pdg00.bulk.Pdg_preventivo_etr_detBulk o = (it.cnr.contab.pdg00.bulk.Pdg_preventivo_etr_detBulk)super.createEmptyModelForSearch(context);
		o.setEsercizio( it.cnr.contab.utenze00.bulk.CNRUserInfo.getEsercizio(context) );
		o.setCentro_responsabilita(new it.cnr.contab.config00.sto.bulk.CdrBulk( centro_responsabilita.getCd_centro_responsabilita() ));
		return o;
	} catch(Exception e) {
		throw handleException(e);
	}
}

public it.cnr.jada.bulk.OggettoBulk createEmptyModelForSearch(it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException {
	try {
		it.cnr.contab.pdg00.bulk.Pdg_preventivo_etr_detBulk o = (it.cnr.contab.pdg00.bulk.Pdg_preventivo_etr_detBulk)super.createEmptyModelForSearch(context);
		o.setEsercizio( it.cnr.contab.utenze00.bulk.CNRUserInfo.getEsercizio(context) );
		o.setCentro_responsabilita(new it.cnr.contab.config00.sto.bulk.CdrBulk( centro_responsabilita.getCd_centro_responsabilita() ));
		return o;
	} catch(Exception e) {
		throw handleException(e);
	}
}

/**
 * <!-- @TODO: da completare -->
 * Restituisce il valore della propriet� 'centro_responsabilita'
 *
 * @return Il valore della propriet� 'centro_responsabilita'
 */
public it.cnr.contab.config00.sto.bulk.CdrBulk getCentro_responsabilita() {
		return centro_responsabilita;
	}

protected void init(it.cnr.jada.action.Config config,it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException {
	super.init(config,context);
	setColumns(getBulkInfo().getColumnFieldPropertyDictionary("entrateCaricate"));
}

/**
 * Reimplementato per forzare il ricaricamento delle righe in caso di errore di validazione
 */ 
protected void save(it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException,it.cnr.jada.bulk.ValidationException {
	try {
		super.save(context);
	} catch(it.cnr.jada.action.BusinessProcessException e) {
		if (e.getDetail() instanceof it.cnr.jada.comp.ApplicationException) {
			refetchPage(context);
		}
		throw e;
	}
}

/**
 * <!-- @TODO: da completare -->
 * Imposta il valore della propriet� 'centro_responsabilita'
 *
 * @param newCentro_responsabilita	Il valore da assegnare a 'centro_responsabilita'
 */
public void setCentro_responsabilita(it.cnr.contab.config00.sto.bulk.CdrBulk newCentro_responsabilita) {
		centro_responsabilita = newCentro_responsabilita;
	}
}