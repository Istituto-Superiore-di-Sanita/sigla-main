package it.cnr.contab.inventario00.bp;

import it.cnr.contab.inventario00.tabrif.bulk.*;
import it.cnr.contab.inventario00.docs.bulk.OptionRequestParameter;

/**
 * Un BusinessProcess controller che permette di effettuare operazioni di CRUD su istanze di 
 *	Inventario_ap_chBulk per la gestione dell'Apertura e della Chiusura di un Inventario
**/

public class CRUDInventarioApChBP extends it.cnr.jada.util.action.SimpleCRUDBP {

	private OptionRequestParameter userConfirm = null;
	
public CRUDInventarioApChBP() {
	super();
}
public CRUDInventarioApChBP(String function) {
	super(function);
}

public void create(it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException {
		
	try {
		getModel().setToBeCreated();
		setModel(
				context,
				((it.cnr.contab.inventario00.ejb.InventarioApChComponentSession)createComponentSession()).creaConBulk(
																			context.getUserContext(),
																			getModel(),
																			getUserConfirm()));
		
	} catch(Exception e) {
		throw handleException(e);
	} finally {
		setUserConfirm(null);
	}
}
/**
 * Insert the method's description here.
 * Creation date: (28/10/2002 11.19.41)
 * @return it.cnr.contab.inventario00.docs.bulk.OptionRequestParameter
 */
public it.cnr.contab.inventario00.docs.bulk.OptionRequestParameter getUserConfirm() {
	return userConfirm;
}
/**
 * Nasconde il pulsante di "Elimina".
 *	Il pulsante � nascosto poich� non � possibile cancellare una riga dalla tabella INVENTARIO_AP_CH,
 *	
 * @return <code>boolean</code> TRUE
 *
**/
public boolean isDeleteButtonHidden() {

	return true;
}
/**
 * Restituisce il valore della propriet� 'inventarioConsegnatarioRO'.
 *	Tale propriet� � utilizzata nel form di gestione dell'apertura e chiusura dell'Inventario,
 *	per stabilire se il campo relativo alla <code>Data Inizio Validit�</code> per il Consegnatario 
 *	� READONLY.
 *
 * @return <code>boolean</code>
 */
public boolean isInventarioConsegnatarioRO() {
	return ((Inventario_ap_chBulk)getModel()).isInventarioConsegnatarioRO();
}
/**
 * Restituisce il valore della propriet� 'inventarioRO'.
 *	Tale propriet� � utilizzata nel form di gestione dell'apertura e chiusura dell'Inventario,
 *	per stabilire se il campo relativo al <code>Numero Bene Iniziale</code> � READONLY.
 *
 * @return <code>boolean</code>
 */
public boolean isInventarioRO() {
	
	Inventario_ap_chBulk inventario = (Inventario_ap_chBulk)getModel();
	
	return inventario.isInventarioRO();
}
/**
 * Nasconde il pulsante di "Nuovo".
 *
 * @return <code>boolean</code> TRUE
 *
**/
public boolean isNewButtonHidden() {

	return false;
}
/**
 * Disabilita il pulsante "Salva" se si � in EDITING.
 *
 * @return <code>boolean</code> 
 *
**/
public boolean isSaveButtonEnabled() {
	return isEditable() && isInserting();
}
/**
 * Inzializza il ricevente in modo che carichi l'ultimo Stato dell'Inventario
 *
 * @param context il <code>ActionContext</code> che ha generato la richiesta.
 */
public void reset(it.cnr.jada.action.ActionContext context) throws it.cnr.jada.action.BusinessProcessException {
	//try {
		//Inventario_ap_chBulk invApCh = ((it.cnr.contab.inventario00.ejb.InventarioApChComponentSession)createComponentSession()).loadInventarioApChAttuale(
			//context.getUserContext());
		
		//if (invApCh == null){
			
			//super.reset(context);
		//}
		//else edit(context, invApCh);
	//} catch(Throwable e) {
		//throw new it.cnr.jada.action.BusinessProcessException(e);
	//}

	super.reset(context);
}
/**
 * Insert the method's description here.
 * Creation date: (28/10/2002 11.19.41)
 * @param newUserConfirm it.cnr.contab.inventario00.docs.bulk.OptionRequestParameter
 */
public void setUserConfirm(it.cnr.contab.inventario00.docs.bulk.OptionRequestParameter newUserConfirm) {
	userConfirm = newUserConfirm;
}
}
