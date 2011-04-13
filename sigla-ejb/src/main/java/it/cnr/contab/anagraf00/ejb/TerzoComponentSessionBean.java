package it.cnr.contab.anagraf00.ejb;
import java.sql.Timestamp;
import java.util.Date;

import it.cnr.contab.anagraf00.comp.TerzoComponent;
import it.cnr.contab.anagraf00.core.bulk.TerzoBulk;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Stateless(name="CNRANAGRAF00_EJB_TerzoComponentSession")
public class TerzoComponentSessionBean extends it.cnr.jada.ejb.CRUDComponentSessionBean implements TerzoComponentSession {
@PostConstruct
	public void ejbCreate() throws javax.ejb.CreateException {
	componentObj = new it.cnr.contab.anagraf00.comp.TerzoComponent();
}
public static it.cnr.jada.ejb.CRUDComponentSessionBean newInstance() throws javax.ejb.EJBException {
	return new TerzoComponentSessionBean();
}
public it.cnr.jada.util.RemoteIterator cercaBanchePerTerzoCessionario(it.cnr.jada.UserContext param0,it.cnr.contab.anagraf00.core.bulk.Modalita_pagamentoBulk param1) throws it.cnr.jada.comp.ComponentException,it.cnr.jada.persistency.PersistencyException,javax.ejb.EJBException {
	pre_component_invocation(param0,componentObj);
	try {
		it.cnr.jada.util.RemoteIterator result = ((TerzoComponent)componentObj).cercaBanchePerTerzoCessionario(param0,param1);
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
	} catch(RuntimeException e) {
		throw uncaughtRuntimeException(param0,componentObj,e);
	} catch(Error e) {
		throw uncaughtError(param0,componentObj,e);
	}
}
public it.cnr.jada.util.RemoteIterator cercaModalita_pagamento_disponibiliByClause(it.cnr.jada.UserContext param0,it.cnr.contab.anagraf00.core.bulk.TerzoBulk param1) throws it.cnr.jada.comp.ComponentException,it.cnr.jada.persistency.PersistencyException,javax.ejb.EJBException {
	pre_component_invocation(param0,componentObj);
	try {
		it.cnr.jada.util.RemoteIterator result = ((TerzoComponent)componentObj).cercaModalita_pagamento_disponibiliByClause(param0,param1);
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
	} catch(RuntimeException e) {
		throw uncaughtRuntimeException(param0,componentObj,e);
	} catch(Error e) {
		throw uncaughtError(param0,componentObj,e);
	}
}
public it.cnr.contab.anagraf00.core.bulk.TerzoBulk cercaTerzoPerUnitaOrganizzativa(it.cnr.jada.UserContext param0,it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk param1) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
	pre_component_invocation(param0,componentObj);
	try {
		it.cnr.contab.anagraf00.core.bulk.TerzoBulk result = ((TerzoComponent)componentObj).cercaTerzoPerUnitaOrganizzativa(param0,param1);
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
public it.cnr.contab.anagraf00.core.bulk.TerzoBulk inizializzaTerzoPerUnitaOrganizzativa(it.cnr.jada.UserContext param0,it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk param1) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
	pre_component_invocation(param0,componentObj);
	try {
		it.cnr.contab.anagraf00.core.bulk.TerzoBulk result = ((TerzoComponent)componentObj).inizializzaTerzoPerUnitaOrganizzativa(param0,param1);
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
public it.cnr.contab.anagraf00.core.bulk.TerzoBulk setComune_sede(it.cnr.jada.UserContext param0,it.cnr.contab.anagraf00.core.bulk.TerzoBulk param1,it.cnr.contab.anagraf00.tabter.bulk.ComuneBulk param2) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
	pre_component_invocation(param0,componentObj);
	try {
		it.cnr.contab.anagraf00.core.bulk.TerzoBulk result = ((TerzoComponent)componentObj).setComune_sede(param0,param1,param2);
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
public java.util.List findListaTerziSIP(it.cnr.jada.UserContext param0,String param1,String param2,String param3,String param4) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
	pre_component_invocation(param0,componentObj);
	try {
		java.util.List result = ((TerzoComponent)componentObj).findListaTerziSIP(param0,param1,param2,param3,param4);
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
public java.util.List findNazioniIban(it.cnr.jada.UserContext param0,it.cnr.contab.anagraf00.core.bulk.BancaBulk param1) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
	pre_component_invocation(param0,componentObj);
	try {
		java.util.List result = ((TerzoComponent)componentObj).findNazioniIban(param0,param1);
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

public java.util.List findListaTerziSIP_rendicontazione(it.cnr.jada.UserContext param0,String param1,String param2,String param3,String param4,Timestamp param5,Timestamp param6,String param7) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
	pre_component_invocation(param0,componentObj);
	try {
		java.util.List result = ((TerzoComponent)componentObj).findListaTerziSIP_rendicontazione(param0,param1,param2,param3,param4,param5,param6,param7);
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
public Integer findMatricolaDipendente(it.cnr.jada.UserContext param0,TerzoBulk param1, Date param2) throws it.cnr.jada.comp.ComponentException,javax.ejb.EJBException {
	pre_component_invocation(param0,componentObj);
	try {
		Integer result = ((TerzoComponent)componentObj).findMatricolaDipendente(param0,param1, param2);
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

