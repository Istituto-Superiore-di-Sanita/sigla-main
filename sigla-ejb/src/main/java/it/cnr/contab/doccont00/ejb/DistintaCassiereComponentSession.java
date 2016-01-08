package it.cnr.contab.doccont00.ejb;


import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.Remote;

import it.cnr.contab.anagraf00.core.bulk.BancaBulk;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.doccont00.intcass.bulk.Distinta_cassiereBulk;
import it.cnr.contab.doccont00.intcass.bulk.ExtCassiereCdsBulk;
import it.cnr.contab.doccont00.intcass.bulk.V_mandato_reversaleBulk;
import it.cnr.contab.doccont00.intcass.xmlbnl.*;
import it.cnr.jada.UserContext;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.persistency.PersistencyException;
import it.cnr.jada.util.RemoteIterator;
@Remote
public interface DistintaCassiereComponentSession extends it.cnr.jada.ejb.CRUDDetailComponentSession {
void annullaModificaDettagliDistinta(it.cnr.jada.UserContext param0,it.cnr.contab.doccont00.intcass.bulk.Distinta_cassiereBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
void associaTuttiDocContabili(it.cnr.jada.UserContext param0,it.cnr.contab.doccont00.intcass.bulk.Distinta_cassiereBulk param1,it.cnr.contab.doccont00.intcass.bulk.V_mandato_reversaleBulk param2) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.contab.doccont00.intcass.bulk.Distinta_cassiereBulk calcolaTotali(it.cnr.jada.UserContext param0,it.cnr.contab.doccont00.intcass.bulk.Distinta_cassiereBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.contab.doccont00.intcass.bulk.V_ext_cassiere00Bulk caricaLogs(it.cnr.jada.UserContext param0,it.cnr.contab.doccont00.intcass.bulk.V_ext_cassiere00Bulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.jada.util.RemoteIterator cercaFile_Cassiere(it.cnr.jada.UserContext param0,it.cnr.jada.persistency.sql.CompoundFindClause param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.jada.util.RemoteIterator cercaMandatiEReversali(it.cnr.jada.UserContext param0,it.cnr.jada.persistency.sql.CompoundFindClause param1,it.cnr.contab.doccont00.intcass.bulk.V_mandato_reversaleBulk param2,it.cnr.contab.doccont00.intcass.bulk.Distinta_cassiereBulk param3) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
void inizializzaDettagliDistintaPerModifica(it.cnr.jada.UserContext param0,it.cnr.contab.doccont00.intcass.bulk.Distinta_cassiereBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
void inviaDistinte(it.cnr.jada.UserContext param0,java.util.Collection param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
void modificaDettagliDistinta(it.cnr.jada.UserContext param0,it.cnr.contab.doccont00.intcass.bulk.Distinta_cassiereBulk param1,it.cnr.jada.bulk.OggettoBulk[] param2,java.util.BitSet param3,java.util.BitSet param4) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.contab.doccont00.intcass.bulk.V_ext_cassiere00Bulk processaFile(it.cnr.jada.UserContext param0,it.cnr.contab.doccont00.intcass.bulk.V_ext_cassiere00Bulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
public it.cnr.contab.config00.bulk.Parametri_cnrBulk parametriCnr(it.cnr.jada.UserContext aUC) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
public RemoteIterator selectFileScarti(UserContext userContext, it.cnr.contab.doccont00.intcass.bulk.Ext_cassiere00_logsBulk log) throws ComponentException,RemoteException;
void caricaFile(it.cnr.jada.UserContext context,java.io.File file) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
ExtCassiereCdsBulk recuperaCodiciCdsCassiere(UserContext userContext,Distinta_cassiereBulk distinta) throws ComponentException, PersistencyException,RemoteException;
BancaBulk recuperaIbanUo(UserContext userContext,Unita_organizzativaBulk uo) throws ComponentException, PersistencyException,java.rmi.RemoteException;
Mandato recuperaDatiMandatoFlusso(UserContext userContext,V_mandato_reversaleBulk bulk) throws ComponentException, PersistencyException,java.rmi.RemoteException;
Reversale recuperaDatiReversaleFlusso(UserContext userContext,V_mandato_reversaleBulk bulk) throws ComponentException, PersistencyException,java.rmi.RemoteException;
List dettagliDistinta(UserContext usercontext, Distinta_cassiereBulk distinta,String tipo) throws PersistencyException, ComponentException,RemoteException;
Distinta_cassiereBulk inviaDistinta(UserContext userContext,Distinta_cassiereBulk distinta) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
}
