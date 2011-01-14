package it.cnr.contab.reports.comp;

/**
 * Insert the type's description here.
 * Creation date: (09/05/2002 15:39:16)
 * @author: CNRADM
 */
public interface IOfflineReportMgr {
/**
 *  Stampa non configurata
 *    PreCondition:
 *      La stampa specificata non � stata configurata (non esiste un record corrispondente nella tabella PRINT_PRIORITY)
 *    PostCondition:
 *      Genera una ApplicationException con il messaggio "La stampa non � stata configurata correttamente. Avvisare il supporto tecnico."
 *  Normale
 *    PreCondition:
 *      Nessun'altra precondizione � verificata
 *    PostCondition:
 *      Aggiunge la richiesta di stampa alla tabella PRINT_SPOOLER impostando la priorit� e la descrizione
 *		configurate in PRINT_PRIORITY
 */

public abstract void addJob(it.cnr.jada.UserContext param0,it.cnr.contab.reports.bulk.Print_spoolerBulk param1,it.cnr.jada.bulk.BulkList param2) throws it.cnr.jada.comp.ComponentException;
/**
 *  Una o pi� stampe gi� cancellate
 *    PreCondition:
 *      L'utente ha richiesto la cancellazione di una o pi� stampe dalla coda di stampa e almeno una di esse risulta gi� cancellata.
 *    PostCondition:
 *      Viene generata una ApplicationException con il messaggio "Una o pi� stampe sono state cancellate da altri utenti."
 *  Una o pi� stampe in esecuzione
 *    PreCondition:
 *      L'utente ha richiesto la cancellazione di una o pi� stampe dalla coda di stampa e almeno una di esse risulta in esecuzione.
 *    PostCondition:
 *      Viene generata una ApplicationException con il messaggio "Una o pi� stampe sono attualmente in esecuzione e non possono essere cancellate."
 *  Normale
 *    PreCondition:
 *      Nessun'altra precondizione � verificata
 *    PostCondition:
 *      Le stampe specificate vengono cancellate dalla coda di stampa.
 */

public abstract void deleteJobs(it.cnr.jada.UserContext param0,it.cnr.contab.reports.bulk.Print_spoolerBulk[] param1) throws it.cnr.jada.comp.ComponentException;
/**
 *  Normale
 *    PreCondition:
 *      L'utente ha richiesto la composizione della coda di stampa
 *    PostCondition:
 *		Viene restituito l'elenco delle stampe presenti nella coda di stampa compatibili con i criteri di visibilit� specificati (secondo quanto sepcificato dalla vista "V_PRINT_SPOOLER_VISIBILITA")
 */

public abstract it.cnr.jada.util.RemoteIterator queryJobs(it.cnr.jada.UserContext param0,java.lang.String param1) throws it.cnr.jada.comp.ComponentException;
}