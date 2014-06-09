package it.cnr.contab.utente00.ejb;

import javax.ejb.Remote;

import it.cnr.jada.UserContext;
@Remote
public interface RuoloComponentSession extends it.cnr.jada.ejb.CRUDComponentSession {
boolean isCapoCommessa(it.cnr.jada.UserContext param0) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
boolean isAbilitatoApprovazioneBilancio(it.cnr.jada.UserContext param0) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
boolean isAmministratoreInventario(it.cnr.jada.UserContext param0) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
boolean isInventarioUfficiale(it.cnr.jada.UserContext param0) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
boolean isGestoreIstatSiope(it.cnr.jada.UserContext param0) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
boolean isAbilitatoECF(it.cnr.jada.UserContext param0) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
boolean isAbilitatoModificaModPag(it.cnr.jada.UserContext param0) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;

boolean isAbilitatoF24EP(it.cnr.jada.UserContext param0) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
boolean isAbilitatoPubblicazioneSito(it.cnr.jada.UserContext param0) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
boolean isAbilitatoFunzioniIncarichi(it.cnr.jada.UserContext param0) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
boolean isSuperUtenteFunzioniIncarichi(it.cnr.jada.UserContext param0) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
boolean isAbilitatoSospensioneCori(it.cnr.jada.UserContext param0) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
boolean isAbilitatoModificaDescVariazioni(it.cnr.jada.UserContext param0) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
boolean isAbilitatoAllTrattamenti(it.cnr.jada.UserContext param0) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException,it.cnr.jada.persistency.IntrospectionException;
boolean isAbilitatoFirmaFatturazioneElettronica(it.cnr.jada.UserContext param0) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;

}
