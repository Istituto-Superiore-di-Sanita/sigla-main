package it.cnr.contab.utenze00.action;

import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.utenze00.bulk.*;
import it.cnr.contab.utenze00.bp.*;
import it.cnr.contab.utente00.nav.ejb.*;
import it.cnr.jada.UserContext;
import it.cnr.jada.action.*;
import it.cnr.jada.bulk.*;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.util.ejb.EJBCommonServices;

import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Action di gestione della selezione di unit� organizzative di scrivania
 */

public class SelezionatoreUnitaOrganizzativaAction extends it.cnr.jada.util.action.SelezionatoreListaAction {
public SelezionatoreUnitaOrganizzativaAction() {
	super();
}
public Forward basicDoBringBack(ActionContext context) throws BusinessProcessException {
	try {
		SelezionatoreUnitaOrganizzativaBP bp = (SelezionatoreUnitaOrganizzativaBP)context.getBusinessProcess();
		context.closeBusinessProcess();
		if (((CNRUserInfo)context.getUserInfo()).getUtente()!=null &&
			((CNRUserInfo)context.getUserInfo()).getUtente().isSupervisore()) {
			Unita_organizzativaBulk	uo = (it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk)bp.getFocusedElement();
			SelezionaCdsBulk scds = new SelezionaCdsBulk();
			scds.setUo(uo);
			scds = bp.findUo(context, scds);
	        bp.selezionaUO(context, null, uo, scds.getCdr());
		}
		else
			bp.selezionaUO(context);
		return context.findForward("desktop");
	} catch(Throwable e) {
		return handleException(context,e);
	}
}
/**
 * Gestisce l'azione scatenzata dalla richiesta dell'utente di un nuovo esercizio di scrivania
 *
 * @param context	L'ActionContext della richiesta
 * @return Il Forward alla pagina di risposta
 */
public Forward doSelezionaEsercizio(ActionContext context) {
	try {
		SelezionatoreUnitaOrganizzativaBP bp = (SelezionatoreUnitaOrganizzativaBP)context.getBusinessProcess();
		bp.getUserInfo().fillFromActionContext(context,null,it.cnr.jada.util.action.FormController.EDIT,bp.getFieldValidationMap());
		if (!bp.getUserInfo().getUtente().isUtenteComune()) {
			context.setUserContext(new CNRUserContext(
				bp.getUserInfo().getUtente().getCd_utente(),
				context.getSessionId(),
				bp.getUserInfo().getEsercizio(),
				null,
				null,
				null));
			return context.findForward("desktop");
		}
		it.cnr.jada.util.RemoteIterator ri = getComponentSession().listaUOPerUtente(context.getUserContext(),bp.getUserInfo().getUtente(),bp.getUserInfo().getEsercizio());
		bp.setIterator(context,ri);
		return context.findDefaultForward();
	} catch(Throwable e) {
		return handleException(context,e);
	}
}
/**
 * <!-- @TODO: da completare -->
 * Restituisce il valore della propriet� 'componentSession'
 *
 * @return Il valore della propriet� 'componentSession'
 * @throws EJBException	Se si verifica qualche eccezione applicativa per cui non � possibile effettuare l'operazione
 * @throws RemoteException	Se si verifica qualche eccezione di sistema per cui non � possibile effettuare l'operazione
 */
public static GestioneLoginComponentSession getComponentSession() throws javax.ejb.EJBException, java.rmi.RemoteException {
	return (GestioneLoginComponentSession)it.cnr.jada.util.ejb.EJBCommonServices.createEJB("CNRUTENZE00_NAV_EJB_GestioneLoginComponentSession",GestioneLoginComponentSession.class);
}
}
