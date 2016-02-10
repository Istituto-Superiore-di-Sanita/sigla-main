package it.cnr.contab.reports.action;

import java.util.Locale;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;

import it.cnr.contab.reports.bp.*;
import it.cnr.contab.reports.bulk.*;
import it.cnr.jada.action.*;
import it.cnr.jada.util.ejb.EJBCommonServices;

/**
 * Insert the type's description here.
 * Creation date: (11/04/2002 17:28:27)
 * @author: CNRADM
 */
public class  PrintSpoolerAction extends it.cnr.jada.util.action.SelezionatoreListaAction {
/**
 * SpoolerStatusAction constructor comment.
 */
public PrintSpoolerAction() {
	super();
}
public Forward doCambiaVisibilita(ActionContext context) {
	PrintSpoolerBP bp = (PrintSpoolerBP)context.getBusinessProcess();
	Print_spoolerBulk print_spooler = (Print_spoolerBulk)bp.getModel();
	String ti_visibilita = print_spooler.getTiVisibilita();
	try {
		fillModel(context);
		bp.refresh(context);
		return context.findDefaultForward();
	} catch(Throwable e) {
		print_spooler.setTiVisibilita(ti_visibilita);
		return handleException(context,e);
	}
}
public Forward doDelete(ActionContext context) {
	try {
		PrintSpoolerBP bp = (PrintSpoolerBP)context.getBusinessProcess();
		bp.setSelection(context);
		Print_spoolerBulk[] array = null;
		if (!bp.getSelection().isEmpty()) {
			array = new Print_spoolerBulk[bp.getSelection().size()];
			int j = 0;
			for (it.cnr.jada.util.action.SelectionIterator i = bp.getSelection().iterator();i.hasNext();)
				array[j++] = (Print_spoolerBulk)bp.getElementAt(context,i.nextIndex());
		} else if (bp.getFocusedElement() != null) {
			array = new Print_spoolerBulk[1];
			array[0] = (Print_spoolerBulk)bp.getFocusedElement();
		}
		if (array != null){
			EJBCommonServices.closeRemoteIterator(bp.getIterator());			
			bp.createComponentSession().deleteJobs(context.getUserContext(),array);
			for (int i = 0;i < array.length;i++) {
				try {
					Print_spoolerBulk print = array[i];
					if (print.getServer() == null || print.getNomeFile() == null)
						continue;
					StringBuffer reportServerURL = new StringBuffer(print.getServer());
					HttpClient httpclient = new HttpClient();
					reportServerURL.append("/").append(print.getUtcr());
					reportServerURL.append("/").append(print.getNomeFile());
					HttpMethod method = new DeleteMethod(reportServerURL.toString());
					method.setRequestHeader("Accept-Language", Locale.getDefault().toString());
			        httpclient.executeMethod(method);
				} catch(java.io.IOException e) {
					// Non posso fare molto... le stampe sono gi� state cancellate dalla
					// tabella della coda!!
				}
			}
		} else{
			throw new it.cnr.jada.comp.ApplicationException("Attenzione: selezionare almeno una riga.");
		}
		bp.refresh(context);
		return context.findDefaultForward();
	} catch(Throwable e) {
		return handleException(context,e);
	}
}
public Forward doRefresh(ActionContext context) {
	try {
		PrintSpoolerBP bp = (PrintSpoolerBP)context.getBusinessProcess();
		bp.refresh(context);
		return context.findDefaultForward();
	} catch(Exception e) {
		return handleException(context,e);
	}
}
public Forward doSelection(ActionContext context,String name) {
	try {
		PrintSpoolerBP bp = (PrintSpoolerBP)context.getBusinessProcess();
		bp.setFocus(context);
		return context.findDefaultForward();
	} catch(Exception e) {
		return handleException(context,e);
	}
}
public Forward doSign(ActionContext context) {
	try {
		PrintSpoolerBP bp = (PrintSpoolerBP)context.getBusinessProcess();
		bp.sign(context);
		return context.findDefaultForward();
	} catch(Exception e) {
		return handleException(context,e);
	}
}
}