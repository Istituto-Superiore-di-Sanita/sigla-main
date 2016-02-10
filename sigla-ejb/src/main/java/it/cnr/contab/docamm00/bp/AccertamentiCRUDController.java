package it.cnr.contab.docamm00.bp;

import it.cnr.contab.docamm00.docs.bulk.*;
import it.cnr.contab.docamm00.ejb.FatturaPassivaComponentSession;
import it.cnr.contab.docamm00.docs.bulk.Fattura_passiva_rigaBulk;
import it.cnr.contab.docamm00.docs.bulk.Fattura_passivaBulk;
import it.cnr.jada.action.*;
import it.cnr.jada.bulk.*;
import it.cnr.jada.util.action.SimpleCRUDBP;

/**
 * Insert the type's description here.
 * Creation date: (10/16/2001 11:32:54 AM)
 * @author: Roberto Peli
 */
public class AccertamentiCRUDController extends it.cnr.jada.util.action.SimpleDetailCRUDController {
/**
 * FatturaPassivaRigaCRUDController constructor comment.
 * @param name java.lang.String
 * @param modelClass java.lang.Class
 * @param listPropertyName java.lang.String
 * @param parent it.cnr.jada.util.action.FormController
 */
public AccertamentiCRUDController(String name, Class modelClass, String listPropertyName, it.cnr.jada.util.action.FormController parent) {
	super(name, modelClass, listPropertyName, parent);
}
private java.math.BigDecimal calcolaTotaleSelezionati(java.util.List selectedModels) {

	java.math.BigDecimal importo = new java.math.BigDecimal(0);
		
	if (selectedModels != null) {
		for (java.util.Iterator i = selectedModels.iterator(); i.hasNext();) {
			IDocumentoAmministrativoRigaBulk rigaSelected = (IDocumentoAmministrativoRigaBulk)i.next();
			importo = importo.add(rigaSelected.getIm_imponibile().add(rigaSelected.getIm_iva()));
		}
	}

	importo = importo.setScale(2, java.math.BigDecimal.ROUND_HALF_UP);
	return importo;
}
public java.util.List getDetails() {

	java.util.Vector lista = new java.util.Vector();
	if (getParentModel() != null && getParentModel() instanceof IDocumentoAmministrativoBulk) {
		java.util.Hashtable h = ((IDocumentoAmministrativoBulk)getParentModel()).getAccertamentiHash();
		if (h != null) {
			for (java.util.Enumeration e = h.keys(); e.hasMoreElements();)
				lista.add(e.nextElement());
		}
	}
	return lista;
}
/**
 * Restituisce true se � possibile aggiungere nuovi elementi
 */
public boolean isGrowable() {
	
	return	super.isGrowable() && !((it.cnr.jada.util.action.CRUDBP)getParentController()).isSearching();
}
/**
 * Restituisce true se � possibile aggiungere nuovi elementi
 */
public boolean isShrinkable() {

	return	super.isShrinkable() && !((it.cnr.jada.util.action.CRUDBP)getParentController()).isSearching();
}
public void writeHTMLToolbar(
	javax.servlet.jsp.PageContext context,
	boolean reset,
	boolean find,
	boolean delete) throws java.io.IOException, javax.servlet.ServletException {

	super.writeHTMLToolbar(context, reset, find, delete);

	it.cnr.jada.util.action.CRUDBP bp = (it.cnr.jada.util.action.CRUDBP)getParentController();
	boolean enabled = !bp.isSearching();
	boolean modelEditable = true;
	if (bp instanceof VoidableBP) {
		enabled = enabled && !((VoidableBP)bp).isModelVoided();
	}
	if (bp instanceof IDocumentoAmministrativoBP) {
		enabled = enabled || ((IDocumentoAmministrativoBP)bp).isDeleting() || ((IDocumentoAmministrativoBP)bp).isManualModify();
		modelEditable = (getParentModel() != null &&
						((IDocumentoAmministrativoBulk)getParentModel()).isEditable() &&
						!((IDocumentoAmministrativoBulk)getParentModel()).isDeleting());
//						&& !((VoidableBP)bp).isModelVoided();
	}
	it.cnr.jada.util.jsp.JSPUtils.toolbarButton(
		context,
		"img/redo16.gif",
		(bp.isViewing() || enabled) ? "javascript:submitForm('doOpenAccertamentiWindow')" : null,
		true,
		"Aggiorna in manuale");
	it.cnr.jada.util.jsp.JSPUtils.toolbarButton(
		context,
		"img/refresh16.gif",
		(!bp.isViewing() && enabled && modelEditable) ? "javascript:submitForm('doModificaScadenzaInAutomatico("+getInputPrefix()+")')" : null,
		false,
		"Aggiorna in automatico");

}
}
