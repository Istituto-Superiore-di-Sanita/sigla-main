package it.cnr.contab.inventario00.ejb;

import it.cnr.contab.inventario00.consultazioni.bulk.VInventarioRicognizioneBulk;
import it.cnr.contab.inventario00.comp.ConsRegistroInventarioComponent;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

/**
 * Bean implementation class for Enterprise Bean: CNRINVENTARIO00_EJB_ConsRegistroInventarioComponentSession
 */
@Stateless(name="CNRINVENTARIO00_EJB_ConsRegistroInventarioComponentSession")
public class ConsRegistroInventarioComponentSessionBean extends it.cnr.jada.ejb.CRUDComponentSessionBean implements ConsRegistroInventarioComponentSession {
	@PostConstruct
	public void ejbCreate() {
		componentObj = new it.cnr.contab.inventario00.comp.ConsRegistroInventarioComponent();
	}
	public static ConsRegistroInventarioComponentSessionBean newInstance() throws javax.ejb.EJBException {
		return new ConsRegistroInventarioComponentSessionBean();
	}
	public it.cnr.jada.util.RemoteIterator findConsultazione(it.cnr.jada.UserContext param0,java.lang.String param1, it.cnr.jada.persistency.sql.CompoundFindClause param2,it.cnr.jada.persistency.sql.CompoundFindClause param3) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
		pre_component_invocation(param0,componentObj);
		try {
			it.cnr.jada.util.RemoteIterator result =  ((ConsRegistroInventarioComponent)componentObj).findConsultazione(param0,param1,param2,param3);
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
	public it.cnr.jada.util.RemoteIterator findConsultazioneRicognizione(it.cnr.jada.UserContext param0,it.cnr.jada.persistency.sql.CompoundFindClause param1,it.cnr.jada.persistency.sql.CompoundFindClause param2, VInventarioRicognizioneBulk param3) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
		pre_component_invocation(param0,componentObj);
		try {
			it.cnr.jada.util.RemoteIterator result = ((ConsRegistroInventarioComponent)componentObj).findConsultazioneRicognizione(param0,param1,param2,param3);
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
