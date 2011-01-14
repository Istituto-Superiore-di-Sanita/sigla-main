package it.cnr.contab.pdg00.action;
import it.cnr.contab.pdg00.bulk.*;
import it.cnr.contab.reports.bp.*;
import it.cnr.jada.action.*;
import it.cnr.jada.util.action.*;

/**
 * <!-- @TODO: da completare -->
 */

public class PdGStampePreventivoAction extends it.cnr.jada.util.action.BulkAction {
public PdGStampePreventivoAction() {
	super();
}

/**
 * Gestione funzione stampa PDG
 *
 * @param context	L'ActionContext della richiesta
 * @return Il Forward alla pagina di risposta
 */
public Forward doStampaCapitolo(ActionContext context) {
	try {
		BulkBP bp = (BulkBP)context.getBusinessProcess();
		OfflineReportPrintBP printbp = (OfflineReportPrintBP)context.createBusinessProcess(bp.getPrintbp());
		printbp.setReportName("/preventivo/pdg/pdgdett_voce_funzione_natura.rpt");
		Pdg_preventivoBulk preventivo = (Pdg_preventivoBulk)bp.getModel();
		printbp.setReportParameter(0,"C");
		printbp.setReportParameter(1,preventivo.getEsercizio().toString());
		printbp.setReportParameter(2,preventivo.getCentro_responsabilita().getUnita_padre().getCd_unita_padre());
		printbp.setReportParameter(3,preventivo.getCentro_responsabilita().getCd_unita_organizzativa());
		printbp.setReportParameter(4,preventivo.getCd_centro_responsabilita());
		return context.addBusinessProcess(printbp);
	} catch(BusinessProcessException e) {
		throw new ActionPerformingError(e);
	}
}

/**
 * Gestione funzione stampa PDG
 *
 * @param context	L'ActionContext della richiesta
 * @return Il Forward alla pagina di risposta
 */
public Forward doStampaDettagliata(ActionContext context) {
	try {
		BulkBP bp = (BulkBP)context.getBusinessProcess();
		OfflineReportPrintBP printbp = (OfflineReportPrintBP)context.createBusinessProcess(bp.getPrintbp());
		printbp.setReportName("/preventivo/pdg/pdgdett_voce_funzione_natura.rpt");
		Pdg_preventivoBulk preventivo = (Pdg_preventivoBulk)bp.getModel();
		printbp.setReportParameter(0,"D");
		printbp.setReportParameter(1,preventivo.getEsercizio().toString());
		printbp.setReportParameter(2,preventivo.getCentro_responsabilita().getUnita_padre().getCd_unita_padre());
		printbp.setReportParameter(3,preventivo.getCentro_responsabilita().getCd_unita_organizzativa());
		printbp.setReportParameter(4,preventivo.getCd_centro_responsabilita());
		return context.addBusinessProcess(printbp);
	} catch(BusinessProcessException e) {
		throw new ActionPerformingError(e);
	}
}

/**
 * Stampa aggregata per unit� organizzativa per capitolo
 * Permessa solo su CDR responsabili di UO
 */
public Forward doStampaDiscrepanzeInsieme(ActionContext context) {
	try {
		BulkBP bp = (BulkBP)context.getBusinessProcess();
		OfflineReportPrintBP printbp = (OfflineReportPrintBP)context.createBusinessProcess(bp.getPrintbp());
		printbp.setReportName("/preventivo/pdg/discrepanze_insieme.rpt");
		Pdg_preventivoBulk preventivo = (Pdg_preventivoBulk)bp.getModel();
		printbp.setReportParameter(0,preventivo.getEsercizio().toString());
		printbp.setReportParameter(1,preventivo.getCentro_responsabilita().getCd_centro_responsabilita());
    	return context.addBusinessProcess(printbp);
	} catch(BusinessProcessException e) {
		throw new ActionPerformingError(e);
	}
}

/**
 * Gestione funzione stampa PDG
 *
 * @param context	L'ActionContext della richiesta
 * @return Il Forward alla pagina di risposta
 */
public Forward doStampaFunzione(ActionContext context) {
	try {
		BulkBP bp = (BulkBP)context.getBusinessProcess();
		OfflineReportPrintBP printbp = (OfflineReportPrintBP)context.createBusinessProcess(bp.getPrintbp());
		printbp.setReportName("/preventivo/pdg/pdgdett_voce_funzione_natura.rpt");
		Pdg_preventivoBulk preventivo = (Pdg_preventivoBulk)bp.getModel();
		printbp.setReportParameter(0,"F");
		printbp.setReportParameter(1,preventivo.getEsercizio().toString());
		printbp.setReportParameter(2,preventivo.getCentro_responsabilita().getUnita_padre().getCd_unita_padre());
		printbp.setReportParameter(3,preventivo.getCentro_responsabilita().getCd_unita_organizzativa());
		printbp.setReportParameter(4,preventivo.getCd_centro_responsabilita());
		return context.addBusinessProcess(printbp);
	} catch(BusinessProcessException e) {
		throw new ActionPerformingError(e);
	}
}

/**
 * Gestione funzione stampa PDG per linea di attivit�
 *
 * @param context	L'ActionContext della richiesta
 * @return Il Forward alla pagina di risposta
 */
public Forward doStampaLA(ActionContext context) {
	try {
		BulkBP bp = (BulkBP)context.getBusinessProcess();
		OfflineReportPrintBP printbp = (OfflineReportPrintBP)context.createBusinessProcess(bp.getPrintbp());
		printbp.setReportName("/preventivo/pdg/pdgdett_la_funzione_natura.rpt");
		Pdg_preventivoBulk preventivo = (Pdg_preventivoBulk)bp.getModel();
		printbp.setReportParameter(0,preventivo.getEsercizio().toString());
		printbp.setReportParameter(1,preventivo.getCentro_responsabilita().getUnita_padre().getCd_unita_padre());
		printbp.setReportParameter(2,preventivo.getCentro_responsabilita().getCd_unita_organizzativa());
		printbp.setReportParameter(3,preventivo.getCd_centro_responsabilita());
		return context.addBusinessProcess(printbp);
	} catch(BusinessProcessException e) {
		throw new ActionPerformingError(e);
	}
}

/**
 * Gestione funzione stampa PDG
 *
 * @param context	L'ActionContext della richiesta
 * @return Il Forward alla pagina di risposta
 */
public Forward doStampaNatura(ActionContext context) {
	try {
		BulkBP bp = (BulkBP)context.getBusinessProcess();
		OfflineReportPrintBP printbp = (OfflineReportPrintBP)context.createBusinessProcess(bp.getPrintbp());
		printbp.setReportName("/preventivo/pdg/pdgdett_voce_funzione_natura.rpt");
		Pdg_preventivoBulk preventivo = (Pdg_preventivoBulk)bp.getModel();
		printbp.setReportParameter(0,"N");
		printbp.setReportParameter(1,preventivo.getEsercizio().toString());
		printbp.setReportParameter(2,preventivo.getCentro_responsabilita().getUnita_padre().getCd_unita_padre());
		printbp.setReportParameter(3,preventivo.getCentro_responsabilita().getCd_unita_organizzativa());
		printbp.setReportParameter(4,preventivo.getCd_centro_responsabilita());
		return context.addBusinessProcess(printbp);
	} catch(BusinessProcessException e) {
		throw new ActionPerformingError(e);
	}
}

/**
 * Gestione funzione stampa PDG
 *
 * @param context	L'ActionContext della richiesta
 * @return Il Forward alla pagina di risposta
 */
public Forward doStampaCDRNaturaC(ActionContext context) {
	try {
		BulkBP bp = (BulkBP)context.getBusinessProcess();
		OfflineReportPrintBP printbp = (OfflineReportPrintBP)context.createBusinessProcess(bp.getPrintbp());
		printbp.setReportName("/preventivo/pdg/pdgdett_commessa.rpt");
		Pdg_preventivoBulk preventivo = (Pdg_preventivoBulk)bp.getModel();
		printbp.setReportParameter(0,"RN");
		printbp.setReportParameter(1,preventivo.getEsercizio().toString());
		printbp.setReportParameter(2,preventivo.getCentro_responsabilita().getUnita_padre().getCd_unita_padre());
		printbp.setReportParameter(3,preventivo.getCentro_responsabilita().getCd_unita_organizzativa());
		printbp.setReportParameter(4,preventivo.getCd_centro_responsabilita());
		return context.addBusinessProcess(printbp);
	} catch(BusinessProcessException e) {
		throw new ActionPerformingError(e);
	}
}

/**
 * Gestione funzione stampa PDG
 *
 * @param context	L'ActionContext della richiesta
 * @return Il Forward alla pagina di risposta
 */
public Forward doStampaCDRCapitoloC(ActionContext context) {
	try {
		BulkBP bp = (BulkBP)context.getBusinessProcess();
		OfflineReportPrintBP printbp = (OfflineReportPrintBP)context.createBusinessProcess(bp.getPrintbp());
		printbp.setReportName("/preventivo/pdg/pdgdett_commessa.rpt");
		Pdg_preventivoBulk preventivo = (Pdg_preventivoBulk)bp.getModel();
		printbp.setReportParameter(0,"RC");
		printbp.setReportParameter(1,preventivo.getEsercizio().toString());
		printbp.setReportParameter(2,preventivo.getCentro_responsabilita().getUnita_padre().getCd_unita_padre());
		printbp.setReportParameter(3,preventivo.getCentro_responsabilita().getCd_unita_organizzativa());
		printbp.setReportParameter(4,preventivo.getCd_centro_responsabilita());
		return context.addBusinessProcess(printbp);
	} catch(BusinessProcessException e) {
		throw new ActionPerformingError(e);
	}
}

/**
 * Gestione funzione stampa PDG
 *
 * @param context	L'ActionContext della richiesta
 * @return Il Forward alla pagina di risposta
 */
public Forward doStampaCDRDettagliataC(ActionContext context) {
	try {
		BulkBP bp = (BulkBP)context.getBusinessProcess();
		OfflineReportPrintBP printbp = (OfflineReportPrintBP)context.createBusinessProcess(bp.getPrintbp());
		printbp.setReportName("/preventivo/pdg/pdgdett_commessa.rpt");
		Pdg_preventivoBulk preventivo = (Pdg_preventivoBulk)bp.getModel();
		printbp.setReportParameter(0,"RD");
		printbp.setReportParameter(1,preventivo.getEsercizio().toString());
		printbp.setReportParameter(2,preventivo.getCentro_responsabilita().getUnita_padre().getCd_unita_padre());
		printbp.setReportParameter(3,preventivo.getCentro_responsabilita().getCd_unita_organizzativa());
		printbp.setReportParameter(4,preventivo.getCd_centro_responsabilita());
		return context.addBusinessProcess(printbp);
	} catch(BusinessProcessException e) {
		throw new ActionPerformingError(e);
	}
}

/**
 * Gestione funzione stampa PDG
 *
 * @param context	L'ActionContext della richiesta
 * @return Il Forward alla pagina di risposta
 */
public Forward doStampaUONaturaC(ActionContext context) {
	try {
		BulkBP bp = (BulkBP)context.getBusinessProcess();
		OfflineReportPrintBP printbp = (OfflineReportPrintBP)context.createBusinessProcess(bp.getPrintbp());
		printbp.setReportName("/preventivo/pdg/pdgdett_commessa.rpt");
		Pdg_preventivoBulk preventivo = (Pdg_preventivoBulk)bp.getModel();
		printbp.setReportParameter(0,"UN");
		printbp.setReportParameter(1,preventivo.getEsercizio().toString());
		printbp.setReportParameter(2,preventivo.getCentro_responsabilita().getUnita_padre().getCd_unita_padre());
		printbp.setReportParameter(3,preventivo.getCentro_responsabilita().getCd_unita_organizzativa());
		printbp.setReportParameter(4,"*");
		return context.addBusinessProcess(printbp);
	} catch(BusinessProcessException e) {
		throw new ActionPerformingError(e);
	}
}

/**
 * Gestione funzione stampa PDG
 *
 * @param context	L'ActionContext della richiesta
 * @return Il Forward alla pagina di risposta
 */
public Forward doStampaUOCapitoloC(ActionContext context) {
	try {
		BulkBP bp = (BulkBP)context.getBusinessProcess();
		OfflineReportPrintBP printbp = (OfflineReportPrintBP)context.createBusinessProcess(bp.getPrintbp());
		printbp.setReportName("/preventivo/pdg/pdgdett_commessa.rpt");
		Pdg_preventivoBulk preventivo = (Pdg_preventivoBulk)bp.getModel();
		printbp.setReportParameter(0,"UC");
		printbp.setReportParameter(1,preventivo.getEsercizio().toString());
		printbp.setReportParameter(2,preventivo.getCentro_responsabilita().getUnita_padre().getCd_unita_padre());
		printbp.setReportParameter(3,preventivo.getCentro_responsabilita().getCd_unita_organizzativa());
		printbp.setReportParameter(4,"*");
		return context.addBusinessProcess(printbp);
	} catch(BusinessProcessException e) {
		throw new ActionPerformingError(e);
	}
}


/**
 * Gestione funzione stampa PDG
 *
 * @param context	L'ActionContext della richiesta
 * @return Il Forward alla pagina di risposta
 */
public Forward doStampaCDSNaturaC(ActionContext context) {
	try {
		BulkBP bp = (BulkBP)context.getBusinessProcess();
		OfflineReportPrintBP printbp = (OfflineReportPrintBP)context.createBusinessProcess(bp.getPrintbp());
		printbp.setReportName("/preventivo/pdg/pdgdett_commessa.rpt");
		Pdg_preventivoBulk preventivo = (Pdg_preventivoBulk)bp.getModel();
		printbp.setReportParameter(0,"SN");
		printbp.setReportParameter(1,preventivo.getEsercizio().toString());
		printbp.setReportParameter(2,preventivo.getCentro_responsabilita().getUnita_padre().getCd_unita_padre());
		printbp.setReportParameter(3,"*");
		printbp.setReportParameter(4,"*");
		return context.addBusinessProcess(printbp);
	} catch(BusinessProcessException e) {
		throw new ActionPerformingError(e);
	}
}


/**
 * Gestione funzione stampa PDG
 *
 * @param context	L'ActionContext della richiesta
 * @return Il Forward alla pagina di risposta
 */
public Forward doStampaCDSCapitoloC(ActionContext context) {
	try {
		BulkBP bp = (BulkBP)context.getBusinessProcess();
		OfflineReportPrintBP printbp = (OfflineReportPrintBP)context.createBusinessProcess(bp.getPrintbp());
		printbp.setReportName("/preventivo/pdg/pdgdett_commessa.rpt");
		Pdg_preventivoBulk preventivo = (Pdg_preventivoBulk)bp.getModel();
		printbp.setReportParameter(0,"SC");
		printbp.setReportParameter(1,preventivo.getEsercizio().toString());
		printbp.setReportParameter(2,preventivo.getCentro_responsabilita().getUnita_padre().getCd_unita_padre());
		printbp.setReportParameter(3,"*");
		printbp.setReportParameter(4,"*");
		return context.addBusinessProcess(printbp);
	} catch(BusinessProcessException e) {
		throw new ActionPerformingError(e);
	}
}


/**
 * Stampa aggregata per unit� organizzativa per capitolo
 * Permessa solo su CDR responsabili di UO
 */
public Forward doStampaUOCapitolo(ActionContext context) {
	try {
		BulkBP bp = (BulkBP)context.getBusinessProcess();
		OfflineReportPrintBP printbp = (OfflineReportPrintBP)context.createBusinessProcess(bp.getPrintbp());
		printbp.setReportName("/preventivo/pdg/pdguo_voce_funzione_natura.rpt");
		Pdg_preventivoBulk preventivo = (Pdg_preventivoBulk)bp.getModel();
        if((new Integer(preventivo.getCentro_responsabilita().getCd_proprio_cdr())).intValue() != 0) {
		 setMessage(context,0, "Stampa non permessa sul CDR selezionato");
	     return context.findDefaultForward();
	    } else {
		 printbp.setReportParameter(0,"C");
		 printbp.setReportParameter(1,preventivo.getEsercizio().toString());
		 printbp.setReportParameter(2,preventivo.getCentro_responsabilita().getUnita_padre().getCd_unita_padre());
		 printbp.setReportParameter(3,preventivo.getCentro_responsabilita().getCd_unita_organizzativa());
    	 return context.addBusinessProcess(printbp);
		}
	} catch(BusinessProcessException e) {
		throw new ActionPerformingError(e);
	}
}

/**
 * Stampa aggregata per unit� organizzativa per funzione
 * Permessa solo su CDR responsabili di UO
 */
public Forward doStampaUOFunzione(ActionContext context) {
	try {
		BulkBP bp = (BulkBP)context.getBusinessProcess();
		OfflineReportPrintBP printbp = (OfflineReportPrintBP)context.createBusinessProcess(bp.getPrintbp());
		printbp.setReportName("/preventivo/pdg/pdguo_voce_funzione_natura.rpt");
		Pdg_preventivoBulk preventivo = (Pdg_preventivoBulk)bp.getModel();
        if((new Integer(preventivo.getCentro_responsabilita().getCd_proprio_cdr())).intValue() != 0) {
		 setMessage(context,0, "Stampa non permessa sul CDR selezionato");
	     return context.findDefaultForward();
	    } else {
		 printbp.setReportParameter(0,"F");
		 printbp.setReportParameter(1,preventivo.getEsercizio().toString());
		 printbp.setReportParameter(2,preventivo.getCentro_responsabilita().getUnita_padre().getCd_unita_padre());
		 printbp.setReportParameter(3,preventivo.getCentro_responsabilita().getCd_unita_organizzativa());
		 return context.addBusinessProcess(printbp);
	    }
	} catch(BusinessProcessException e) {
		throw new ActionPerformingError(e);
	}
}

/**
 * Stampa aggregata per unit� organizzativa per natura
 * Permessa solo su CDR responsabili di UO
 */
public Forward doStampaUONatura(ActionContext context) {
	try {
		BulkBP bp = (BulkBP)context.getBusinessProcess();
		OfflineReportPrintBP printbp = (OfflineReportPrintBP)context.createBusinessProcess(bp.getPrintbp());
		printbp.setReportName("/preventivo/pdg/pdguo_voce_funzione_natura.rpt");
		Pdg_preventivoBulk preventivo = (Pdg_preventivoBulk)bp.getModel();
        if((new Integer(preventivo.getCentro_responsabilita().getCd_proprio_cdr())).intValue() != 0) {
		 setMessage(context,0, "Stampa non permessa sul CDR selezionato");
	     return context.findDefaultForward();
	    } else {
		 printbp.setReportParameter(0,"N");
		 printbp.setReportParameter(1,preventivo.getEsercizio().toString());
		 printbp.setReportParameter(2,preventivo.getCentro_responsabilita().getUnita_padre().getCd_unita_padre());
		 printbp.setReportParameter(3,preventivo.getCentro_responsabilita().getCd_unita_organizzativa());
		 return context.addBusinessProcess(printbp);
	    }
	} catch(BusinessProcessException e) {
		throw new ActionPerformingError(e);
	}
}
}