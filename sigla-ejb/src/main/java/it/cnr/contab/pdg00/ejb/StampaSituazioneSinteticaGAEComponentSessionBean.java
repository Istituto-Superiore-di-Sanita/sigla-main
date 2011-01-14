package it.cnr.contab.pdg00.ejb;
import java.rmi.RemoteException;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import it.cnr.contab.config00.latt.bulk.WorkpackageBulk;
import it.cnr.contab.doccont00.comp.StampaSingoloContoComponent;
import it.cnr.contab.pdg00.comp.StampaSituazioneSinteticaGAEComponent;
import it.cnr.jada.UserContext;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.persistency.PersistencyException;
import it.cnr.jada.persistency.sql.CompoundFindClause;
import it.cnr.jada.persistency.sql.SQLBuilder;
import it.cnr.jada.util.RemoteIterator;
@Stateless(name="CNRPDG00_EJB_StampaSituazioneSinteticaGAEComponentSession")
public class StampaSituazioneSinteticaGAEComponentSessionBean extends it.cnr.jada.ejb.CRUDComponentSessionBean implements StampaSituazioneSinteticaGAEComponentSession{
@PostConstruct
	public void ejbCreate() throws javax.ejb.CreateException {
	componentObj = new StampaSituazioneSinteticaGAEComponent();
}
public static it.cnr.jada.ejb.CRUDComponentSessionBean newInstance() throws javax.ejb.EJBException {
	return new StampaSituazioneSinteticaGAEComponentSessionBean();
}
public void inserisciRecord(UserContext param0, java.math.BigDecimal param1, java.util.List param2) 
throws ComponentException,java.rmi.RemoteException{
	pre_component_invocation(param0,componentObj);
	try {
		((StampaSituazioneSinteticaGAEComponent)componentObj).inserisciRecord(param0,param1,param2);
		component_invocation_succes(param0,componentObj);
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
public java.math.BigDecimal getPgStampa(it.cnr.jada.UserContext param0) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
	pre_component_invocation(param0,componentObj);
	try {
		java.math.BigDecimal result = ((StampaSituazioneSinteticaGAEComponent)componentObj).getPgStampa(param0);
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

public RemoteIterator selezionaGae (UserContext param0,  CompoundFindClause param1) throws ComponentException, PersistencyException, RemoteException{
	pre_component_invocation(param0,componentObj);
	try {
		RemoteIterator result = ((StampaSituazioneSinteticaGAEComponent)componentObj).selezionaGae(param0,param1);
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
