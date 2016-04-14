package it.cnr.contab.docamm00.ejb;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import it.cnr.contab.docamm00.comp.ProgressiviAmmComponent;
@Stateless(name="CNRDOCAMM00_EJB_ProgressiviAmmComponentSession")
public class ProgressiviAmmComponentSessionBean extends it.cnr.jada.ejb.CRUDComponentSessionBean implements ProgressiviAmmComponentSession {
@PostConstruct
	public void ejbCreate() {
	componentObj = new it.cnr.contab.docamm00.comp.ProgressiviAmmComponent();
}
public static it.cnr.jada.ejb.CRUDComponentSessionBean newInstance() throws javax.ejb.EJBException {
	return new ProgressiviAmmComponentSessionBean();
}
public java.lang.Long getNextPG(it.cnr.jada.UserContext param0,it.cnr.contab.docamm00.docs.bulk.Numerazione_doc_ammBulk param1) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
	pre_component_invocation(param0,componentObj);
	try {
		java.lang.Long result = ((ProgressiviAmmComponent)componentObj).getNextPG(param0,param1);
		component_invocation_succes(param0,componentObj);
		return result;
	} catch(it.cnr.jada.comp.NoRollbackException e) {
		component_invocation_succes(param0,componentObj);
		throw e;
	} catch(it.cnr.jada.comp.ComponentException e) {
		component_invocation_failure(param0,componentObj);
		throw e;
	} catch(RuntimeException e) {
		throw uncaughtRuntimeException(param0,componentObj,e);
	} catch(Error e) {
		throw uncaughtError(param0,componentObj,e);
	}
}
}
