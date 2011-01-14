package it.cnr.contab.compensi00.bp;

import it.cnr.contab.compensi00.docs.bulk.MinicarrieraBulk;
import it.cnr.contab.compensi00.docs.bulk.Minicarriera_rataBulk;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.bulk.ValidationException;

/**
 * Insert the type's description here.
 * Creation date: (6/24/2002 12:04:47 PM)
 * @author: Roberto Peli
 */
public class MinicarrieraRataCRUDController extends it.cnr.jada.util.action.SimpleDetailCRUDController {
/**
 * MinicarrieraRataCRUDController constructor comment.
 * @param name java.lang.String
 * @param modelClass java.lang.Class
 * @param listPropertyName java.lang.String
 * @param parent it.cnr.jada.util.action.FormController
 */
public MinicarrieraRataCRUDController(String name, Class modelClass, String listPropertyName, it.cnr.jada.util.action.FormController parent) {
	super(name, modelClass, listPropertyName, parent);
}
/**
 * MinicarrieraRataCRUDController constructor comment.
 * @param name java.lang.String
 * @param modelClass java.lang.Class
 * @param listPropertyName java.lang.String
 * @param parent it.cnr.jada.util.action.FormController
 * @param multiSelection boolean
 */
public MinicarrieraRataCRUDController(String name, Class modelClass, String listPropertyName, it.cnr.jada.util.action.FormController parent, boolean multiSelection) {
	super(name, modelClass, listPropertyName, parent, multiSelection);
}
/**
 * Restituisce true se � possibile aggiungere nuovi elementi
 */
public boolean isGrowable() {
	
	MinicarrieraBulk carriera = (MinicarrieraBulk)getParentModel();
	return	super.isGrowable() && 
			!((it.cnr.jada.util.action.CRUDBP)getParentController()).isSearching() &&
			carriera.isAttiva() &&
			!carriera.getMinicarriera_rate().isEmpty();
}
public boolean isInputReadonly() {
	Minicarriera_rataBulk rata = (Minicarriera_rataBulk)getModel();
	return	super.isInputReadonly() ||
			!((MinicarrieraBulk)getParentModel()).isAttiva() ||
			(rata != null && rata.isAssociataACompenso());
}
/**
 * Restituisce true se � possibile aggiungere nuovi elementi
 */
public boolean isShrinkable() {

	MinicarrieraBulk carriera = (MinicarrieraBulk)getParentModel();
	return	super.isShrinkable() && 
			!((it.cnr.jada.util.action.CRUDBP)getParentController()).isSearching() &&
			carriera.isAttiva() &&
			!carriera.getMinicarriera_rate().isEmpty();
}
public void validate(ActionContext context,OggettoBulk model) throws ValidationException {

	//Non dovrebbe + servire: se non attiva si disabilita il controller.
	//if (!((MinicarrieraBulk)getParentModel()).isAttiva())
		//throw new ValidationException("La minicarriera non � attiva! Impossibile effettuare modifiche.");

	Minicarriera_rataBulk rata = (Minicarriera_rataBulk)model;
	if (rata.getIm_rata() == null)
		throw new ValidationException("Specificare l'importo della rata \"" + rata.getPg_rata().longValue() + "\".");
	if (new java.math.BigDecimal(0).setScale(2, java.math.BigDecimal.ROUND_HALF_EVEN).compareTo(rata.getIm_rata()) == 0)
		throw new ValidationException("L'importo della rata \"" + rata.getPg_rata().longValue() + "\" non � valido.");

	if (rata.getDt_inizio_rata() == null || rata.getDt_fine_rata() == null)
		throw new ValidationException("Inserire le date di validit� della rata \"" + rata.getPg_rata().longValue() + "\".");
	if (rata.getDt_scadenza() == null)
		throw new ValidationException("Inserire la data di scadenza della rata \"" + rata.getPg_rata().longValue() + "\".");
	
	if (rata.getDt_fine_rata().before(rata.getDt_inizio_rata()))
		throw new ValidationException("Date di validit� della rata \"" + rata.getPg_rata().longValue() + "\" non corrette. Verificare i periodi.");

	//E' stata data una notizia falsa e tendenziosa su questo controllo. Lo commento per il momento.	
	//if (rata.getDt_scadenza().before(rata.getDt_inizio_rata()))
		//throw new ValidationException("La data di scadenza non puo' essere precedente alla data inizio validit� della rata \"" + rata.getPg_rata().longValue() + "\".");
	if (((MinicarrieraBulk)getParentModel()).getDt_fine_minicarriera().before(rata.getDt_fine_rata()))
		throw new ValidationException("La rata \"" + rata.getPg_rata().longValue() + "\" ha una data fine validit� posteriore alla data fine validit� della minicarriera.");	

	//try {
		//((FatturaPassivaComponentSession)(((SimpleCRUDBP)getParentController()).createComponentSession())).validaRiga(context.getUserContext(), (Fattura_passiva_rigaBulk)model);
	//} catch (it.cnr.jada.comp.ApplicationException e) {
		//throw new ValidationException(e.getMessage());
	//} catch (Throwable e) {
		//throw new it.cnr.jada.DetailedRuntimeException(e);
	//}
}
public void validateForDelete(ActionContext context, OggettoBulk detail) throws ValidationException {

	Minicarriera_rataBulk rata = (Minicarriera_rataBulk)detail;

	if (!((MinicarrieraBulk)getParentModel()).isAttiva())
		throw new ValidationException("La minicarriera non � attiva! Impossibile effettuare modifiche.");
		
	if (rata.isAssociataACompenso())
		throw new ValidationException("La rata \"" + rata.getPg_rata().longValue() + "\" � associata a compenso. Impossibile eliminarla.");

}
public void writeHTMLToolbar(
		javax.servlet.jsp.PageContext context,
		boolean reset,
		boolean find,
		boolean delete) throws java.io.IOException, javax.servlet.ServletException {

		MinicarrieraBulk carriera = (MinicarrieraBulk)getParentModel();
		CRUDMinicarrieraBP parentController = (CRUDMinicarrieraBP)getParentController();
		it.cnr.jada.util.jsp.JSPUtils.toolbarButton(
			context,
			"img/history16.gif",
			(!(isInputReadonly() ||
				parentController.isSearching() ||
				!(carriera.isNonAssociataACompenso() &&
				carriera.isAttiva()) )? "javascript:submitForm('doGeneraRate')" : null),
			true,
			"Crea rate");
		super.writeHTMLToolbar(context, reset, find, delete);
		it.cnr.jada.util.jsp.JSPUtils.toolbarButton(
			context,
			"img/properties16.gif",
			((!(//isInputReadonly() ||
				parentController.isSearching() ||
				!carriera.isAttiva()) )? "javascript:submitForm('doCreaCompenso')" : null),
			true,
			"Crea compenso");
		it.cnr.jada.util.jsp.JSPUtils.toolbarButton(
			context,
			"img/folderopen16.gif",
			(!parentController.isSearching() && 
				getModel() != null && 
				((Minicarriera_rataBulk)getModel()).isAssociataACompenso()) ? "javascript:submitForm('doVisualizzaCompenso')" : null,
			false,
			"Visualizza compenso");
}
}
