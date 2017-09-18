package it.cnr.contab.ordmag.ordini.ejb;
import java.rmi.RemoteException;
import java.util.List;

import it.cnr.contab.ordmag.ordini.bulk.OrdineAcqBulk;
import it.cnr.contab.ordmag.richieste.bulk.RichiestaUopBulk;
import it.cnr.jada.UserContext;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.persistency.PersistencyException;

public class TransactionalOrdineAcqComponentSession extends it.cnr.jada.ejb.TransactionalCRUDComponentSession implements OrdineAcqComponentSession {
public void gestioneStampaOrdine(UserContext userContext, OrdineAcqBulk ordine) throws RemoteException,it.cnr.jada.comp.ComponentException{
	try {
		invoke("gestioneStampaOrdine",new Object[] {
			userContext,
			ordine });
	} catch(java.rmi.RemoteException e) {
		throw e;
	} catch(java.lang.reflect.InvocationTargetException e) {
		try {
			throw e.getTargetException();
		} catch(it.cnr.jada.comp.ComponentException ex) {
			throw ex;
		} catch(Throwable ex) {
			throw new java.rmi.RemoteException("Uncaugth exception",ex);
		}
	}
}

	public void controllaQuadraturaObbligazioni(UserContext userContext,OrdineAcqBulk ordine) throws RemoteException,ComponentException, PersistencyException{
		try {
			invoke("controllaQuadraturaObbligazioni",new Object[] {
					userContext,
					ordine });
		} catch(java.rmi.RemoteException e) {
			throw e;
		} catch(java.lang.reflect.InvocationTargetException e) {
			try {
				throw e.getTargetException();
			} catch(it.cnr.jada.comp.ComponentException ex) {
				throw ex;
			} catch(Throwable ex) {
				throw new java.rmi.RemoteException("Uncaugth exception",ex);
			}
		}
	}

public void completaOrdine(UserContext userContext, OrdineAcqBulk ordine) throws RemoteException,ComponentException, PersistencyException{
	try {
		invoke("completaOrdine",new Object[] {
			userContext,
			ordine });
	} catch(java.rmi.RemoteException e) {
		throw e;
	} catch(java.lang.reflect.InvocationTargetException e) {
		try {
			throw e.getTargetException();
		} catch(it.cnr.jada.comp.ComponentException ex) {
			throw ex;
		} catch(Throwable ex) {
			throw new java.rmi.RemoteException("Uncaugth exception",ex);
		}
	}
}
public Boolean isUtenteAbilitatoOrdine(UserContext usercontext, OrdineAcqBulk ordine) throws ComponentException, PersistencyException,javax.ejb.EJBException, RemoteException{
	try {
		return (Boolean)invoke("isUtenteAbilitatoOrdine",new Object[] {
				usercontext,
				ordine });
	} catch(java.rmi.RemoteException e) {
		throw e;
	} catch(java.lang.reflect.InvocationTargetException e) {
		try {
			throw e.getTargetException();
		} catch(it.cnr.jada.comp.ComponentException ex) {
			throw ex;
		} catch(Throwable ex) {
			throw new java.rmi.RemoteException("Uncaugth exception",ex);
		}
	}
}
public Boolean isUtenteAbilitatoValidazioneOrdine(UserContext usercontext, OrdineAcqBulk ordine) throws ComponentException, PersistencyException,javax.ejb.EJBException, RemoteException{
	try {
		return (Boolean)invoke("isUtenteAbilitatoValidazioneOrdine",new Object[] {
				usercontext,
				ordine });
	} catch(java.rmi.RemoteException e) {
		throw e;
	} catch(java.lang.reflect.InvocationTargetException e) {
		try {
			throw e.getTargetException();
		} catch(it.cnr.jada.comp.ComponentException ex) {
			throw ex;
		} catch(Throwable ex) {
			throw new java.rmi.RemoteException("Uncaugth exception",ex);
		}
	}
}
public it.cnr.jada.util.RemoteIterator cercaObbligazioni(it.cnr.jada.UserContext param0,it.cnr.contab.docamm00.docs.bulk.Filtro_ricerca_obbligazioniVBulk param1) throws RemoteException,it.cnr.jada.comp.ComponentException {
	try {
		return (it.cnr.jada.util.RemoteIterator)invoke("cercaObbligazioni",new Object[] {
			param0,
			param1 });
	} catch(java.rmi.RemoteException e) {
		throw e;
	} catch(java.lang.reflect.InvocationTargetException e) {
		try {
			throw e.getTargetException();
		} catch(it.cnr.jada.comp.ComponentException ex) {
			throw ex;
		} catch(Throwable ex) {
			throw new java.rmi.RemoteException("Uncaugth exception",ex);
		}
	}
}
public OrdineAcqBulk contabilizzaDettagliSelezionati(it.cnr.jada.UserContext param0,OrdineAcqBulk param1,java.util.Collection param2,it.cnr.contab.doccont00.core.bulk.Obbligazione_scadenzarioBulk param3) throws RemoteException,it.cnr.jada.comp.ComponentException {
	try {
		return (OrdineAcqBulk)invoke("contabilizzaDettagliSelezionati",new Object[] {
			param0,
			param1,
			param2,
			param3 });
	} catch(java.rmi.RemoteException e) {
		throw e;
	} catch(java.lang.reflect.InvocationTargetException e) {
		try {
			throw e.getTargetException();
		} catch(it.cnr.jada.comp.ComponentException ex) {
			throw ex;
		} catch(Throwable ex) {
			throw new java.rmi.RemoteException("Uncaugth exception",ex);
		}
	}
}
public OrdineAcqBulk calcolaImportoOrdine(it.cnr.jada.UserContext userContext, OrdineAcqBulk ordine) throws RemoteException,ComponentException, PersistencyException{
	try {
		return (OrdineAcqBulk)invoke("calcolaImportoOrdine",new Object[] {
				userContext,
				ordine });
	} catch(java.rmi.RemoteException e) {
		throw e;
	} catch(java.lang.reflect.InvocationTargetException e) {
		try {
			throw e.getTargetException();
		} catch(it.cnr.jada.comp.ComponentException ex) {
			throw ex;
		} catch(Throwable ex) {
			throw new java.rmi.RemoteException("Uncaugth exception",ex);
		}
	}
}
public OrdineAcqBulk creaOrdineDaRichieste(it.cnr.jada.UserContext userContext, OrdineAcqBulk ordine, List<RichiestaUopBulk> lista) throws RemoteException,ComponentException, PersistencyException{
	try {
		return (OrdineAcqBulk)invoke("creaOrdineDaRichieste",new Object[] {
				userContext,
				ordine,
				lista});
	} catch(java.rmi.RemoteException e) {
		throw e;
	} catch(java.lang.reflect.InvocationTargetException e) {
		try {
			throw e.getTargetException();
		} catch(it.cnr.jada.comp.ComponentException ex) {
			throw ex;
		} catch(Throwable ex) {
			throw new java.rmi.RemoteException("Uncaugth exception",ex);
		}
	}
}
}
