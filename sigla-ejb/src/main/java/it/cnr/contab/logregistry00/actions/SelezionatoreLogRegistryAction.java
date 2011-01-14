package it.cnr.contab.logregistry00.actions;

import it.cnr.contab.logregistry00.core.bulk.Log_registryBulk;
import it.cnr.contab.logregistry00.core.bulk.OggettoLogBulk;
import it.cnr.contab.logregistry00.core.bulk.ILogRegistryBulk;

import it.cnr.contab.logregistry00.bp.SelezionatoreLogRegistryBP;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.action.BusinessProcessException;
import it.cnr.jada.action.Forward;
import it.cnr.jada.util.action.RicercaLiberaBP;
import it.cnr.jada.util.action.Selection;



public class SelezionatoreLogRegistryAction extends it.cnr.jada.util.action.SelezionatoreListaAction {
/**
 * SelezionatoreLogRegistryAction constructor comment.
 */
public SelezionatoreLogRegistryAction() {
	super();
}
public Forward basicDoBringBack(ActionContext context) throws BusinessProcessException {

	SelezionatoreLogRegistryBP bp = (SelezionatoreLogRegistryBP)context.getBusinessProcess();
	Log_registryBulk logReg = (Log_registryBulk)bp.getFocusedElement(context);

	try {
		ILogRegistryBulk prototype = getPrototypeFromTableName(logReg.getNome_table_log());
		return openLogFreeSearch(context, prototype);
	} catch (ClassCastException e) {
		return handleException(context, new it.cnr.jada.comp.ApplicationException(e.getMessage()));
	} catch (Throwable t) {
		return handleException(context, t);
	}
}
private ILogRegistryBulk getPrototypeFromTableName(String tableLog) 
	throws 
		ClassNotFoundException, 
		ClassCastException,
		InstantiationException,
		IllegalAccessException {

	if (tableLog != null) {
		StringBuffer sb = new StringBuffer(tableLog.length());
		sb.append(Character.toUpperCase(tableLog.charAt(0)));
		sb.append(tableLog.substring(1).toLowerCase());
		String bulkName = sb.toString();
		sb = new StringBuffer();
		sb.append(ILogRegistryBulk.PKG_NAME);
		sb.append('.');
		sb.append(bulkName);
		sb.append(ILogRegistryBulk.SUFFIX);

		Class prototypeClass = getClass().getClassLoader().loadClass(sb.toString());
		if (!ILogRegistryBulk.class.isAssignableFrom(prototypeClass))
			throw new ClassCastException("La classe per \"" + tableLog + "\" non � di tipo LogRegistry! Operazione interrotta.");
		return (ILogRegistryBulk)prototypeClass.newInstance();
	}
	return null;
}
private Forward openLogFreeSearch(ActionContext context, ILogRegistryBulk logBulk) {

	try {
		SelezionatoreLogRegistryBP bp = (SelezionatoreLogRegistryBP)context.getBusinessProcess();
		
		RicercaLiberaBP ricercaLiberaBP = (RicercaLiberaBP)context.createBusinessProcess("RicercaLibera");
		ricercaLiberaBP.setSearchProvider(bp);
		ricercaLiberaBP.setFreeSearchSet(null);
		ricercaLiberaBP.setPrototype((OggettoLogBulk)logBulk);
		ricercaLiberaBP.setColumnSet(null);
		ricercaLiberaBP.setCanPerformSearchWithoutClauses(true);
		
		return context.addBusinessProcess(ricercaLiberaBP);
	} catch(Throwable e) {
		return handleException(context,e);
	}
}
}
