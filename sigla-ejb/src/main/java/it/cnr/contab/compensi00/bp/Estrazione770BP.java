package it.cnr.contab.compensi00.bp;

import it.cnr.contab.compensi00.ejb.*;
import it.cnr.contab.compensi00.docs.bulk.Estrazione770Bulk;
import it.cnr.jada.action.*;
import it.cnr.jada.comp.*;
import it.cnr.jada.util.action.*;
/**
 * Creation date: (24/09/2004)
 * @author: Aurelio D'Amico
 * @version: 1.0
 */
public class Estrazione770BP extends AbstractEstrazioneFiscaleBP {
/**
 * Estrazione770BP default contructor.
 */
public Estrazione770BP() {
	super();
}
/**
 * Estrazione770BP constructor comment.
 * @param function java.lang.String
 */
public Estrazione770BP(String function) {
	super(function);
}
protected it.cnr.jada.util.jsp.Button[] createToolbar() {

	it.cnr.jada.util.jsp.Button[] toolbar = new it.cnr.jada.util.jsp.Button[1];
	int i = 0;

	toolbar[i++] = new it.cnr.jada.util.jsp.Button(it.cnr.jada.util.Config.getHandler().getProperties(getClass()),"CRUDToolbar.extract770");

	return toolbar;
}
public void doElabora770(ActionContext context) throws BusinessProcessException {

	try{

		Estrazione770Bulk e770 = (Estrazione770Bulk)getModel();
		
		CompensoComponentSession sess = (CompensoComponentSession)createComponentSession();
		sess.doElabora770(context.getUserContext(), e770);


	}catch(javax.ejb.EJBException e){
		throw handleException(e);
	} catch(java.rmi.RemoteException re){
		throw handleException(re);
	} catch(ComponentException ce){
		throw handleException(ce);
	}

	
}
protected void init(Config config,ActionContext context) throws BusinessProcessException {

	try {
		Estrazione770Bulk e770 = new Estrazione770Bulk();		
		e770.setEsercizio(it.cnr.contab.utenze00.bp.CNRUserContext.getEsercizio(context.getUserContext()));
	
		setModel(context, e770);
	} catch(Throwable e) {
		throw handleException(e);
	}
	
	super.init(config,context);
}
}
