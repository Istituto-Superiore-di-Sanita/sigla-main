package it.cnr.contab.pdg00.consultazioni.ejb;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import it.cnr.contab.pdg00.consultazioni.comp.ConsPDGAssestatoAggregatoComponent;

@Stateless(name="CNRPDG00_EJB_ConsPDGAssestatoAggregatoComponentSession")
public class ConsPDGAssestatoAggregatoComponentSessionBean extends it.cnr.jada.ejb.CRUDComponentSessionBean implements ConsPDGAssestatoAggregatoComponentSession {
	@PostConstruct
	public void ejbCreate() throws javax.ejb.CreateException {
		componentObj = new it.cnr.contab.pdg00.consultazioni.comp.ConsPDGAssestatoAggregatoComponent();
	}
	public static it.cnr.jada.ejb.CRUDComponentSessionBean newInstance() throws javax.ejb.EJBException {
		return new ConsPDGAssestatoAggregatoComponentSessionBean();
	}
	public it.cnr.jada.util.RemoteIterator findConsultazioneEtr(it.cnr.jada.UserContext param0, java.lang.String param1, java.lang.String param2, it.cnr.jada.persistency.sql.CompoundFindClause param3,it.cnr.jada.persistency.sql.CompoundFindClause param4) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
		pre_component_invocation(param0,componentObj);
		try {
			it.cnr.jada.util.RemoteIterator result = ((ConsPDGAssestatoAggregatoComponent)componentObj).findConsultazioneEtr(param0,param1,param2,param3,param4);
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
	public it.cnr.jada.util.RemoteIterator findConsultazioneSpe(it.cnr.jada.UserContext param0, java.lang.String param1, java.lang.String param2, it.cnr.jada.persistency.sql.CompoundFindClause param3,it.cnr.jada.persistency.sql.CompoundFindClause param4) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
		pre_component_invocation(param0,componentObj);
		try {
			it.cnr.jada.util.RemoteIterator result = ((ConsPDGAssestatoAggregatoComponent)componentObj).findConsultazioneSpe(param0,param1,param2,param3,param4);
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
