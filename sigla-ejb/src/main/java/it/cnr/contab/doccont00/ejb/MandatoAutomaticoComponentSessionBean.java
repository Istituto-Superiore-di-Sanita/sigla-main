package it.cnr.contab.doccont00.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import it.cnr.contab.doccont00.comp.MandatoAutomaticoComponent;
@Stateless(name="CNRDOCCONT00_EJB_MandatoAutomaticoComponentSession")
public class MandatoAutomaticoComponentSessionBean extends MandatoComponentSessionBean implements MandatoAutomaticoComponentSession {
@PostConstruct
	public void ejbCreate() {
	componentObj = new it.cnr.contab.doccont00.comp.MandatoAutomaticoComponent();
}
public static it.cnr.jada.ejb.CRUDComponentSessionBean newInstance() throws javax.ejb.EJBException {
	return new MandatoAutomaticoComponentSessionBean();
}
public java.util.List findBancaOptions(it.cnr.jada.UserContext param0,it.cnr.contab.doccont00.core.bulk.MandatoAutomaticoWizardBulk param1) throws it.cnr.jada.persistency.PersistencyException,it.cnr.jada.persistency.IntrospectionException,it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
	pre_component_invocation(param0,componentObj);
	try {
		java.util.List result = ((MandatoAutomaticoComponent)componentObj).findBancaOptions(param0,param1);
		component_invocation_succes(param0,componentObj);
		return result;
	} catch(it.cnr.jada.comp.NoRollbackException e) {
		component_invocation_succes(param0,componentObj);
		throw e;
	} catch(it.cnr.jada.persistency.PersistencyException e) {
		component_invocation_failure(param0,componentObj);
		throw e;
	} catch(it.cnr.jada.persistency.IntrospectionException e) {
		component_invocation_failure(param0,componentObj);
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
public it.cnr.contab.doccont00.core.bulk.MandatoAutomaticoWizardBulk listaImpegniTerzo (it.cnr.jada.UserContext param0, it.cnr.contab.doccont00.core.bulk.MandatoAutomaticoWizardBulk param1) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
	pre_component_invocation(param0,componentObj);
	try {
		it.cnr.contab.doccont00.core.bulk.MandatoAutomaticoWizardBulk result = ((MandatoAutomaticoComponent)componentObj).listaImpegniTerzo(param0,param1);
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
public it.cnr.jada.bulk.OggettoBulk inizializzaMappaAutomatismo(it.cnr.jada.UserContext param0, it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException, javax.ejb.EJBException {
	pre_component_invocation(param0, componentObj);
	try
	{
		it.cnr.jada.bulk.OggettoBulk oggettobulk1 = ((MandatoAutomaticoComponent)componentObj).inizializzaMappaAutomatismo(param0, param1);
		component_invocation_succes(param0, componentObj);
		return oggettobulk1;
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
public it.cnr.jada.bulk.OggettoBulk creaMandatoAutomatico(it.cnr.jada.UserContext param0, it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException, javax.ejb.EJBException {
	pre_component_invocation(param0, componentObj);
	try
	{
		it.cnr.jada.bulk.OggettoBulk oggettobulk1 = ((MandatoAutomaticoComponent)componentObj).creaMandatoAutomatico(param0, param1);
		component_invocation_succes(param0, componentObj);
		return oggettobulk1;
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
