package it.cnr.contab.bilaterali00.ejb;

import it.cnr.contab.bilaterali00.bulk.Blt_visiteBulk;
import it.cnr.contab.bilaterali00.comp.BltVisiteComponent;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

/**
 * Bean implementation class for Enterprise Bean: CNRBILATERALI00_EJB_BltVisiteComponentSession
 */
@Stateless(name="CNRBILATERALI00_EJB_BltVisiteComponentSession")
public class BltVisiteComponentSessionBean extends it.cnr.jada.ejb.CRUDComponentSessionBean implements it.cnr.contab.bilaterali00.ejb.BltVisiteComponentSession {
	@PostConstruct
	public void ejbCreate() throws javax.ejb.CreateException {
		componentObj = new it.cnr.contab.bilaterali00.comp.BltVisiteComponent();
	}
	public static it.cnr.jada.ejb.CRUDComponentSessionBean newInstance() throws javax.ejb.EJBException {
		return new BltVisiteComponentSessionBean();
	}
	public it.cnr.contab.anagraf00.core.bulk.TerzoBulk findCessionarioAnticipo(it.cnr.jada.UserContext param0,Blt_visiteBulk param1) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
		pre_component_invocation(param0,componentObj);
		try {
			it.cnr.contab.anagraf00.core.bulk.TerzoBulk result = ((BltVisiteComponent)componentObj).findCessionarioAnticipo(param0,param1);
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
	public it.cnr.contab.anagraf00.core.bulk.TerzoBulk findCessionario(it.cnr.jada.UserContext param0,Blt_visiteBulk param1) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
		pre_component_invocation(param0,componentObj);
		try {
			it.cnr.contab.anagraf00.core.bulk.TerzoBulk result = ((BltVisiteComponent)componentObj).findCessionario(param0,param1);
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
	public java.util.Collection findListaBancheAnticipo(it.cnr.jada.UserContext param0,Blt_visiteBulk param1) throws it.cnr.jada.comp.ComponentException,it.cnr.jada.persistency.PersistencyException,it.cnr.jada.persistency.IntrospectionException,javax.ejb.EJBException {
		pre_component_invocation(param0,componentObj);
		try {
			java.util.Collection result = ((it.cnr.contab.bilaterali00.comp.BltVisiteComponent)componentObj).findListaBancheAnticipo(param0,param1);
			component_invocation_succes(param0,componentObj);
			return result;
		} catch(it.cnr.jada.comp.NoRollbackException e) {
			component_invocation_succes(param0,componentObj);
			throw e;
		} catch(it.cnr.jada.comp.ComponentException e) {
			component_invocation_failure(param0,componentObj);
			throw e;
		} catch(it.cnr.jada.persistency.PersistencyException e) {
			component_invocation_failure(param0,componentObj);
			throw e;
		} catch(it.cnr.jada.persistency.IntrospectionException e) {
			component_invocation_failure(param0,componentObj);
			throw e;
		} catch(RuntimeException e) {
			throw uncaughtRuntimeException(param0,componentObj,e);
		} catch(Error e) {
			throw uncaughtError(param0,componentObj,e);
		}
	}
	public java.util.Collection findListaBanche(it.cnr.jada.UserContext param0,Blt_visiteBulk param1) throws it.cnr.jada.comp.ComponentException,it.cnr.jada.persistency.PersistencyException,it.cnr.jada.persistency.IntrospectionException,javax.ejb.EJBException {
		pre_component_invocation(param0,componentObj);
		try {
			java.util.Collection result = ((it.cnr.contab.bilaterali00.comp.BltVisiteComponent)componentObj).findListaBanche(param0,param1);
			component_invocation_succes(param0,componentObj);
			return result;
		} catch(it.cnr.jada.comp.NoRollbackException e) {
			component_invocation_succes(param0,componentObj);
			throw e;
		} catch(it.cnr.jada.comp.ComponentException e) {
			component_invocation_failure(param0,componentObj);
			throw e;
		} catch(it.cnr.jada.persistency.PersistencyException e) {
			component_invocation_failure(param0,componentObj);
			throw e;
		} catch(it.cnr.jada.persistency.IntrospectionException e) {
			component_invocation_failure(param0,componentObj);
			throw e;
		} catch(RuntimeException e) {
			throw uncaughtRuntimeException(param0,componentObj,e);
		} catch(Error e) {
			throw uncaughtError(param0,componentObj,e);
		}
	}
	public java.math.BigDecimal findRimborsoNettoPrevisto(it.cnr.jada.UserContext param0, Blt_visiteBulk param1) throws it.cnr.jada.comp.ComponentException,it.cnr.jada.persistency.PersistencyException,it.cnr.jada.persistency.IntrospectionException,javax.ejb.EJBException {
		pre_component_invocation(param0,componentObj);
		try {
			java.math.BigDecimal result = ((it.cnr.contab.bilaterali00.comp.BltVisiteComponent)componentObj).findRimborsoNettoPrevisto(param0,param1);
			component_invocation_succes(param0,componentObj);
			return result;
		} catch(it.cnr.jada.comp.NoRollbackException e) {
			component_invocation_succes(param0,componentObj);
			throw e;
		} catch(it.cnr.jada.comp.ComponentException e) {
			component_invocation_failure(param0,componentObj);
			throw e;
		} catch(it.cnr.jada.persistency.PersistencyException e) {
			component_invocation_failure(param0,componentObj);
			throw e;
		} catch(it.cnr.jada.persistency.IntrospectionException e) {
			component_invocation_failure(param0,componentObj);
			throw e;
		} catch(RuntimeException e) {
			throw uncaughtRuntimeException(param0,componentObj,e);
		} catch(Error e) {
			throw uncaughtError(param0,componentObj,e);
		}
	}
}
