package it.cnr.contab.utente00.nav.ejb;

import it.cnr.contab.config00.sto.bulk.CdrBulk;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.utenze00.bulk.PreferitiBulk;
import it.cnr.contab.utenze00.bulk.SessionTraceBulk;
import it.cnr.contab.utenze00.bulk.UtenteBulk;
import it.cnr.jada.UserContext;

import java.rmi.RemoteException;
import java.util.Hashtable;
import java.util.List;

import javax.ejb.Remote;

@Remote
public interface GestioneLoginComponentSession extends it.cnr.jada.ejb.GenericComponentSession {
it.cnr.contab.utenze00.bulk.UtenteBulk cambiaPassword(it.cnr.jada.UserContext param0,it.cnr.contab.utenze00.bulk.UtenteBulk param1,java.lang.String param2) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
boolean controllaAccesso(it.cnr.jada.UserContext param0,java.lang.String param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.contab.utenze00.bulk.Albero_mainBulk generaAlberoPerUtente(it.cnr.jada.UserContext param0,it.cnr.contab.utenze00.bulk.UtenteBulk param1,java.lang.String param2,java.lang.String param3,short param4) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
void leggiMessaggi(it.cnr.jada.UserContext param0,it.cnr.contab.messaggio00.bulk.MessaggioBulk[] param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
java.lang.Integer[] listaEserciziPerUtente(it.cnr.jada.UserContext param0,it.cnr.contab.utenze00.bulk.UtenteBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.jada.util.RemoteIterator listaMessaggi(it.cnr.jada.UserContext param0,java.lang.String param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.jada.util.RemoteIterator listaUOPerUtente(it.cnr.jada.UserContext param0,it.cnr.contab.utenze00.bulk.UtenteBulk param1,java.lang.Integer param2) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.jada.util.RemoteIterator listaCdrPerUtente(it.cnr.jada.UserContext param0,it.cnr.contab.utenze00.bulk.UtenteBulk param1,java.lang.Integer param2) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
void notificaMessaggi(it.cnr.jada.UserContext param0,java.lang.String param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
void registerUser(it.cnr.jada.UserContext param0,java.lang.String param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
void unregisterUser(it.cnr.jada.UserContext param0) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
void unregisterUsers(java.lang.String param0) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
java.lang.String validaBPPerUtente(it.cnr.jada.UserContext param0,it.cnr.contab.utenze00.bulk.UtenteBulk param1,java.lang.String param2,java.lang.String param3) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
java.lang.Boolean isBPEnableForUser(it.cnr.jada.UserContext param0,it.cnr.contab.utenze00.bulk.UtenteBulk param1,java.lang.String param2,java.lang.String param3) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.contab.utenze00.bulk.Albero_mainBulk validaNodoPerUtente(it.cnr.jada.UserContext param0,it.cnr.contab.utenze00.bulk.UtenteBulk param1,java.lang.String param2,java.lang.String param3) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.contab.utenze00.bulk.UtenteBulk validaUtente(it.cnr.jada.UserContext param0,it.cnr.contab.utenze00.bulk.UtenteBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.contab.utenze00.bulk.UtenteBulk validaUtente(it.cnr.jada.UserContext param0,it.cnr.contab.utenze00.bulk.UtenteBulk param1, int faseValidazione) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.contab.config00.bulk.ApplicationServerBulk validaServerLogin(it.cnr.jada.UserContext param0,it.cnr.contab.config00.bulk.ApplicationServerBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.contab.config00.bulk.ApplicationServerBulk validaServerAttivo(it.cnr.jada.UserContext param0,it.cnr.contab.config00.bulk.ApplicationServerBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
java.util.List utentiMultipli(it.cnr.jada.UserContext param0,it.cnr.contab.utenze00.bulk.UtenteBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
boolean isUtenteAbilitatoLdap(it.cnr.jada.UserContext param0, String param1, boolean param2)  throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
void cambiaAbilitazioneUtente(it.cnr.jada.UserContext param0, String param1, boolean param2)  throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
List<SessionTraceBulk> sessionList(it.cnr.jada.UserContext param0, String param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
CdrBulk cdrDaUo(it.cnr.jada.UserContext param0,Unita_organizzativaBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
List<PreferitiBulk> preferitiList(it.cnr.jada.UserContext param0) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
public String[] getLdapUserFromMatricola(UserContext param0, Integer param1)  throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
public List getUnitaRuolo(UserContext param0, UtenteBulk utente) throws it.cnr.jada.comp.ComponentException, RemoteException;
public List getRuoli(UserContext param0, UtenteBulk utente) throws it.cnr.jada.comp.ComponentException, RemoteException;
}
