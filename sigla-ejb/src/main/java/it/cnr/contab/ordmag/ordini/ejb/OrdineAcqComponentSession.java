package it.cnr.contab.ordmag.ordini.ejb;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.Remote;

import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.ordmag.ordini.bulk.OrdineAcqBulk;
import it.cnr.contab.ordmag.ordini.bulk.OrdineAcqConsegnaBulk;
import it.cnr.contab.ordmag.ordini.dto.ImportoOrdine;
import it.cnr.contab.ordmag.ordini.dto.ParametriCalcoloImportoOrdine;
import it.cnr.contab.ordmag.richieste.bulk.RichiestaUopBulk;
import it.cnr.jada.UserContext;
import it.cnr.jada.comp.ApplicationException;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.persistency.PersistencyException;
@Remote
public interface OrdineAcqComponentSession extends it.cnr.jada.ejb.CRUDComponentSession{
	public Boolean isUtenteAbilitatoOrdine(UserContext usercontext, OrdineAcqBulk ordine) throws RemoteException,ComponentException, PersistencyException, EJBException;
	public Boolean isUtenteAbilitatoValidazioneOrdine(UserContext usercontext, OrdineAcqBulk ordine) throws RemoteException,ComponentException, PersistencyException, EJBException;
	public void completaOrdine(UserContext userContext, OrdineAcqBulk ordine) throws RemoteException,ComponentException, PersistencyException;
	it.cnr.jada.util.RemoteIterator cercaObbligazioni(it.cnr.jada.UserContext param0,it.cnr.contab.docamm00.docs.bulk.Filtro_ricerca_obbligazioniVBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
	OrdineAcqBulk contabilizzaDettagliSelezionati(it.cnr.jada.UserContext param0,OrdineAcqBulk param1,java.util.Collection param2,it.cnr.contab.doccont00.core.bulk.Obbligazione_scadenzarioBulk param3) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
	public OrdineAcqBulk calcolaImportoOrdine(it.cnr.jada.UserContext userContext, OrdineAcqBulk ordine) throws RemoteException,ComponentException, PersistencyException;
	public void controllaQuadraturaObbligazioni(UserContext aUC,OrdineAcqBulk ordine) throws RemoteException,ComponentException, PersistencyException;
	public OrdineAcqBulk creaOrdineDaRichieste(it.cnr.jada.UserContext userContext, OrdineAcqBulk ordine, List<RichiestaUopBulk> lista) throws RemoteException,ComponentException, PersistencyException;
	public OrdineAcqBulk cancellaOrdine(it.cnr.jada.UserContext userContext, OrdineAcqBulk ordine) throws RemoteException,ComponentException, PersistencyException;
	public Unita_organizzativaBulk recuperoUoPerImpegno(it.cnr.jada.UserContext userContext, OrdineAcqConsegnaBulk consegna) throws RemoteException,ComponentException, PersistencyException;
	OrdineAcqBulk contabilizzaConsegneSelezionate(it.cnr.jada.UserContext param0,OrdineAcqBulk param1,java.util.Collection param2,it.cnr.contab.doccont00.core.bulk.Obbligazione_scadenzarioBulk param3) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
}
