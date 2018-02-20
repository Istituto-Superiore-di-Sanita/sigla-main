package it.cnr.contab.gestiva00.actions;

import java.sql.*;
import it.cnr.contab.gestiva00.ejb.*;
import it.cnr.contab.gestiva00.core.bulk.*;
import it.cnr.contab.gestiva00.bp.*;
import it.cnr.contab.reports.bp.OfflineReportPrintBP;
import it.cnr.contab.docamm00.tabrif.bulk.Tipo_sezionaleBulk;
import it.cnr.jada.action.*;
import it.cnr.jada.bulk.*;

/**
 * Stampa generica
 */

public class StampaAction extends it.cnr.jada.util.action.BulkAction{
public StampaAction() {
	super();
}
protected void aggiornaRegistriStampati(ActionContext context)
	throws Throwable {

	BusinessProcess bulkBp = context.getBusinessProcess();
	StampaRegistriIvaBP bp = null;
	if (bulkBp instanceof StampaRegistriIvaBP)
		bp = (StampaRegistriIvaBP)bulkBp;
	else {
		BusinessProcess parent = bulkBp.getParent();
		if (parent != null && parent instanceof StampaRegistriIvaBP)
			bp = (StampaRegistriIvaBP)bulkBp.getParent();
	}

	if (bp != null)
		bp.aggiornaRegistriStampati(context);
}
/**
 * gestisce la creazione dei registri iva
 * 
 *
 * @param context	L'ActionContext della richiesta
 * @return Il Forward alla pagina di risposta
 */
protected Forward basicDoCerca(
	ActionContext context)
	throws Throwable {

	StampaRegistriIvaBP bp= (StampaRegistriIvaBP) context.getBusinessProcess();
	Stampa_registri_ivaVBulk bulk = (Stampa_registri_ivaVBulk)bp.getModel();

	if (bp.isBulkPrintable())
		((IPrintable)bulk).setId_report(null);
	bulk.setRistampa(false);
       	
	MTUWrapper wrapper = manageStampa(context, bulk);

	Stampa_registri_ivaVBulk stampaBulk= (Stampa_registri_ivaVBulk) wrapper.getBulk();
	bp.setModel(context, stampaBulk);
	
	if (bp.isBulkPrintable())
		return doStampa(context, (IPrintable)bp.getModel(), wrapper);
	else {
		bp.commitUserTransaction();
		
       	String message = getMessageFrom(wrapper);
		if (message != null)
            bp.setMessage(message);
        else
            bp.setMessage("Operazione effettuata in modo corretto");

        return context.findDefaultForward();
	}
}
protected Forward basicDoRistampa(ActionContext context) 
	throws Throwable {

	StampaRegistriIvaBP bp= (StampaRegistriIvaBP) context.getBusinessProcess();
	Stampa_registri_ivaVBulk bulk = (Stampa_registri_ivaVBulk)bp.getModel().clone();

	if (bp.isBulkReprintable()) {
		
		Report_statoBulk reportStato = (Report_statoBulk)bp.getRegistri_stampati().getModel();

		if (reportStato == null)
			throw new it.cnr.jada.comp.ApplicationException("Selezionare il registro da ristampare!");

		bulk.completeFrom(reportStato);
		bulk.setRistampa(true);
		((IPrintable)bulk).setId_report(null);

		it.cnr.jada.bulk.MTUWrapper wrapper = manageStampa(context, bulk);

		bulk = (Stampa_registri_ivaVBulk) wrapper.getBulk();
		//bp.setModel(context, bulk);
		bp.getRegistri_stampati().reset(context);

		return doStampa(context, (IPrintable)bulk, wrapper);
	}
	
	bp.rollbackUserTransaction();
	bp.setMessage(
				it.cnr.jada.util.action.OptionBP.ERROR_MESSAGE, 
				"Questo tipo di registro non è ristampabile");
	return context.findDefaultForward();
}
/**
 * Gestisce la selezione dopo una richiesta di ricerca.
 *
 * L'implementazione di default utilizza il metodo astratto <code>read</code>
 * di <code>CRUDBusinessProcess</code>.
 * Se la ricerca fornisce più di un risultato viene creato un
 * nuovo <code>SelezionatoreListaBP</code> per la selezione di un elemento.
 * Al business process viene anche chiesto l'elenco delle colonne da
 * visualizzare.
 */
protected void basicDoStampaAnnullata(
	ActionContext context)
	throws Throwable {

	context.getBusinessProcess().rollbackUserTransaction();

	aggiornaRegistriStampati(context);
}
/**
 * gestisce la creazione dei registri iva
 * 
 *
 * @param context	L'ActionContext della richiesta
 * @return Il Forward alla pagina di risposta
 */
public final Forward doCerca(ActionContext context) {

    try {
        fillModel(context);
		Forward fwd = basicDoCerca(context);
		
		aggiornaRegistriStampati(context);
		
		return fwd;
    } catch (Throwable e) {
	    try {
		    context.getBusinessProcess().rollbackUserTransaction();
	    } catch (BusinessProcessException ex) {
		    return handleException(context, ex);
	    }
        return handleException(context, e);
    }
}
/**
 * Gestisce il cambiamento del mese impostando le relative dati inizio e fine
 * 
 *
 * @param context	L'ActionContext della richiesta
 * @return Il Forward alla pagina di risposta
 */
public it.cnr.jada.action.Forward doOnMeseChange(ActionContext context) {

    StampaRegistriIvaBP bp= (StampaRegistriIvaBP) context.getBusinessProcess();
    try {
        bp.fillModel(context);
        if (bp.isBulkReprintable())
      	  bp.getRegistri_stampati().reset(context);
    } catch (Exception e) {

    }
	return setDataDaA(context, (Stampa_registri_ivaVBulk) bp.getModel());
}
public Forward doOnTipoSezionaleChange(ActionContext context) {

	try {
		fillModel(context);
		
		aggiornaRegistriStampati(context);
		
		return context.findDefaultForward();
	} catch (Throwable t) {
		return handleException(context, t);
	}
}
public final Forward doRistampa(ActionContext context) {

    try {
        fillModel(context);
        return basicDoRistampa(context);
        
    } catch (Throwable e) {
	    try {
		    context.getBusinessProcess().rollbackUserTransaction();
	    } catch (BusinessProcessException ex) {
		    return handleException(context, ex);
	    }
        return handleException(context, e);
    }
}
/**
 * Gestisce la selezione dopo una richiesta di ricerca.
 *
 * L'implementazione di default utilizza il metodo astratto <code>read</code>
 * di <code>CRUDBusinessProcess</code>.
 * Se la ricerca fornisce più di un risultato viene creato un
 * nuovo <code>SelezionatoreListaBP</code> per la selezione di un elemento.
 * Al business process viene anche chiesto l'elenco delle colonne da
 * visualizzare.
 */

protected Forward doStampa(
	ActionContext context,
	IPrintable stampaBulk,
	it.cnr.jada.bulk.MTUWrapper wrapper)
	throws BusinessProcessException {

	String message = "L'operazione è stata eseguita correttamente. Per confermare eseguire la stampa del report.";

	String s = getMessageFrom(wrapper);
	if (s != null) {
		StampaRegistriIvaBP bp = (StampaRegistriIvaBP)context.getBusinessProcess();
		bp.commitUserTransaction();
		bp.setMessage(s);
		return context.findDefaultForward();
	}

	return doStampa(context, stampaBulk, message);
}
/**
 * Gestisce la selezione dopo una richiesta di ricerca.
 *
 * L'implementazione di default utilizza il metodo astratto <code>read</code>
 * di <code>CRUDBusinessProcess</code>.
 * Se la ricerca fornisce più di un risultato viene creato un
 * nuovo <code>SelezionatoreListaBP</code> per la selezione di un elemento.
 * Al business process viene anche chiesto l'elenco delle colonne da
 * visualizzare.
 */

protected Forward doStampa(
	ActionContext context,
	IPrintable stampaBulk,
	String message)
	throws BusinessProcessException {

	StampaRegistriIvaBP bp = (StampaRegistriIvaBP)context.getBusinessProcess();
	if (bp.getUserTransaction() == null)
		throw new BusinessProcessException("Impossibile stampare! Contesto NON transazionale");
	OfflineReportPrintBP printbp = (OfflineReportPrintBP)context.createBusinessProcess(bp.getPrintbp(), new Object[] { "Th" });
	printbp.setId_report_generico(stampaBulk.getId_report());

	printbp.setReportName(stampaBulk.getReportName());
	if (printbp.getReportName().endsWith("jasper")){
		printbp.setPrintSpoolerParam(stampaBulk.getPrintSpoolerParam());
	}else{
		java.util.Vector parameters = stampaBulk.getReportParameters();
		if (parameters != null && !parameters.isEmpty())
			for (int i = 0; i < parameters.size(); i++) {
				Object param = parameters.get(i);
				if (param instanceof java.sql.Timestamp)
					printbp.setReportParameter(i, (java.sql.Timestamp)param);
				else
					printbp.setReportParameter(i, (String)param);
			}
	}
	
	context.addHookForward("close", this, "doStampaAnnullata");

	printbp.setMessage(
			it.cnr.jada.util.action.OptionBP.MESSAGE,
			message);

	return context.addBusinessProcess(printbp);
}
/**
 * Gestisce la selezione dopo una richiesta di ricerca.
 *
 * L'implementazione di default utilizza il metodo astratto <code>read</code>
 * di <code>CRUDBusinessProcess</code>.
 * Se la ricerca fornisce più di un risultato viene creato un
 * nuovo <code>SelezionatoreListaBP</code> per la selezione di un elemento.
 * Al business process viene anche chiesto l'elenco delle colonne da
 * visualizzare.
 */
public final Forward doStampaAnnullata(ActionContext context) {

	try {
		basicDoStampaAnnullata(context);

		return context.findDefaultForward();
	} catch(Throwable e) {
		return handleException(context,e);
	}
}
protected java.util.GregorianCalendar getGregorianCalendar() {

	java.util.GregorianCalendar gc = (java.util.GregorianCalendar)java.util.GregorianCalendar.getInstance();
	
	gc.set(java.util.Calendar.HOUR, 0);
	gc.set(java.util.Calendar.MINUTE, 0);
	gc.set(java.util.Calendar.SECOND, 0);
	gc.set(java.util.Calendar.MILLISECOND, 0);
	gc.set(java.util.Calendar.AM_PM, java.util.Calendar.AM);
	
	return gc;
}
/**
 * Gestisce la selezione dopo una richiesta di ricerca.
 *
 * L'implementazione di default utilizza il metodo astratto <code>read</code>
 * di <code>CRUDBusinessProcess</code>.
 * Se la ricerca fornisce più di un risultato viene creato un
 * nuovo <code>SelezionatoreListaBP</code> per la selezione di un elemento.
 * Al business process viene anche chiesto l'elenco delle colonne da
 * visualizzare.
 */

protected String getMessageFrom(it.cnr.jada.bulk.MTUWrapper wrapper) {

	if (wrapper == null ||
		wrapper.getMtu() == null ||
		wrapper.getMtu().getMessage() == null ||
		wrapper.getMtu().getMessage().equals(""))
		return null;
		
	return wrapper.getMtu().getMessage();
}
/**
 * Effettua i controlli sui sezionali e il periodo e
 * richiama il metodo della component che gestisce le varie operazioni
 *
 * @param context	L'ActionContext della richiesta
 * @return 
 * @throws Throwable	
 */
protected MTUWrapper manageStampa(
	ActionContext context,
	Stampa_registri_ivaVBulk stampaBulk) 
	throws Throwable {

        StampaRegistriIvaBP bp= (StampaRegistriIvaBP) context.getBusinessProcess();

        stampaBulk.validate();

        StampaRegistriIvaComponentSession component= null;
        component= (StampaRegistriIvaComponentSession) bp.createComponentSession();

        java.math.BigDecimal pg_report= new java.math.BigDecimal(0);
        it.cnr.jada.bulk.MTUWrapper wrapper= new it.cnr.jada.bulk.MTUWrapper();
        wrapper= component.callStampeIva(context.getUserContext(), (Stampa_registri_ivaVBulk) stampaBulk);
        

        return wrapper;
}
protected it.cnr.jada.action.Forward setDataDaA(
	ActionContext context,
	Stampa_registri_ivaVBulk stampaBulk) {

    try {
	    int esercizio = stampaBulk.getEsercizio().intValue();
	    int meseIndex = ((Integer)stampaBulk.getMesi_int().get(stampaBulk.getMese())).intValue();
		java.util.GregorianCalendar gc = getGregorianCalendar();
		gc.set(java.util.Calendar.DAY_OF_MONTH, 1);
		gc.set(java.util.Calendar.YEAR, esercizio);

		gc.set(java.util.Calendar.MONTH, meseIndex-1);
		stampaBulk.setData_da(new Timestamp(gc.getTime().getTime()));
		gc.set(java.util.Calendar.MONTH, meseIndex);
		gc.add(java.util.Calendar.DAY_OF_MONTH, -1);
        stampaBulk.setData_a(new Timestamp(gc.getTime().getTime()));

    } catch (Exception e) {
		return handleException(context, e);
    }
    return context.findDefaultForward();
}
}
