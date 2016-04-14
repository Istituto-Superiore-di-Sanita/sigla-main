package it.cnr.contab.docamm00.ejb;
import javax.annotation.PostConstruct;
import javax.ejb.*;
@Stateless(name="CNRDOCAMM00_EJB_RiportoDocAmmComponentSession")
public class RiportoDocAmmComponentSessionBean extends it.cnr.jada.ejb.GenericComponentSessionBean implements RiportoDocAmmComponentSession {
	private it.cnr.contab.docamm00.comp.RiportoDocAmmComponent componentObj;
	@Remove
	public void ejbRemove() throws javax.ejb.EJBException {
		componentObj.release();
	}
	@PostConstruct
	public void ejbCreate() {
		componentObj = new it.cnr.contab.docamm00.comp.RiportoDocAmmComponent();
	}
	public static RiportoDocAmmComponentSessionBean newInstance() throws EJBException {
		return new RiportoDocAmmComponentSessionBean();
	}
	public java.lang.String getStatoRiporto(it.cnr.jada.UserContext param0,it.cnr.contab.docamm00.docs.bulk.IDocumentoAmministrativoBulk param1) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
		pre_component_invocation(param0,componentObj);
		try {
			java.lang.String result = componentObj.getStatoRiporto(param0,param1);
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
	public java.lang.String getStatoRiportoInScrivania(it.cnr.jada.UserContext param0,it.cnr.contab.docamm00.docs.bulk.IDocumentoAmministrativoBulk param1) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
		pre_component_invocation(param0,componentObj);
		try {
			java.lang.String result = componentObj.getStatoRiportoInScrivania(param0,param1);
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
	public it.cnr.contab.docamm00.docs.bulk.IDocumentoAmministrativoBulk riportaAvanti(it.cnr.jada.UserContext param0,it.cnr.contab.docamm00.docs.bulk.IDocumentoAmministrativoBulk param1,it.cnr.contab.doccont00.core.bulk.OptionRequestParameter param2) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
		pre_component_invocation(param0,componentObj);
		try {
			it.cnr.contab.docamm00.docs.bulk.IDocumentoAmministrativoBulk result = componentObj.riportaAvanti(param0,param1,param2);
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
	public it.cnr.contab.docamm00.docs.bulk.IDocumentoAmministrativoBulk riportaIndietro(it.cnr.jada.UserContext param0,it.cnr.contab.docamm00.docs.bulk.IDocumentoAmministrativoBulk param1) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
		pre_component_invocation(param0,componentObj);
		try {
			it.cnr.contab.docamm00.docs.bulk.IDocumentoAmministrativoBulk result = componentObj.riportaIndietro(param0,param1);
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
