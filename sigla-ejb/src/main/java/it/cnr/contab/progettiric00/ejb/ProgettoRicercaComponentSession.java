package it.cnr.contab.progettiric00.ejb;

import javax.ejb.Remote;

@Remote
public interface ProgettoRicercaComponentSession extends it.cnr.jada.ejb.CRUDComponentSession,it.cnr.jada.ejb.PrintComponentSession {
it.cnr.jada.util.RemoteIterator getChildren(it.cnr.jada.UserContext param0,it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.jada.util.RemoteIterator getChildrenWorkpackage(it.cnr.jada.UserContext param0,it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.jada.util.RemoteIterator getChildrenForSip(it.cnr.jada.UserContext param0,it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.jada.bulk.OggettoBulk getParent(it.cnr.jada.UserContext param0,it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
boolean isLeaf(it.cnr.jada.UserContext param0,it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.jada.bulk.OggettoBulk getParentForSip(it.cnr.jada.UserContext param0,it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
boolean isLeafForSip(it.cnr.jada.UserContext param0,it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
it.cnr.contab.progettiric00.core.bulk.ProgettoBulk cercaWorkpackages(it.cnr.jada.UserContext param0,it.cnr.contab.progettiric00.core.bulk.ProgettoBulk param1) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
void validaCancellazioneUoAssociata(it.cnr.jada.UserContext param0, it.cnr.contab.progettiric00.core.bulk.ProgettoBulk param1, it.cnr.jada.bulk.OggettoBulk param2) throws it.cnr.jada.comp.ComponentException,java.rmi.RemoteException;
}
