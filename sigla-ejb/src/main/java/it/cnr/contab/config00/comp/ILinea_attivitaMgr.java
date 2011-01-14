package it.cnr.contab.config00.comp;
import it.cnr.contab.config00.latt.bulk.WorkpackageBulk;
import it.cnr.contab.config00.sto.bulk.CdrBulk;
import it.cnr.jada.UserContext;
import it.cnr.jada.comp.ComponentException;
public interface ILinea_attivitaMgr extends it.cnr.jada.comp.ICRUDMgr
{


/** 
  *  Esercizio fine validit� non impostato 
  *	   PreCondition:
  *		 L'esercizio di fine validit� � nullo o minore dell'esercizio di inizio validit�
  *    PostCondition:
  *		 L'esercizio di fine validit� viene impostato uguale all'esercizio di fine validit� del cdr.
  *  Tipo linea attivit� non specificato
  *    PreCondition:
  *      Non � stato specificato il tipo linea attivit�
  *    PostCondition:
  *		 Imposta cd_tipo_linea_attivita = 'PROP' 
  *  Normale
  *    PreCondition:
  *      Viene richiesto la creazione di una nuova linea di attivita
  *    PostCondition:
  *		 Invoca validaFunzione() e validaNaturaPerInserimento(), quindi effettua l'inserimento
 */

public abstract it.cnr.jada.bulk.OggettoBulk creaConBulk(it.cnr.jada.UserContext param0,it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException;

/** 
  *  Linea di attivit� CSSAC gi� esistente
  *    PreCondition:
  *      Viene cercata E TROVATA una linea di attivit� con TIPO=CSSAC, cd_cdr_collegato=CDR LA origine, e cd_lacollegato=cd_linea_attivita origine.
  *    PostCondition:
  *      Quest'istanza viene restituita all'utente.
  *  Linea di attivit� CSSAC non esiste
  *    PreCondition:
  *      Viene cercata E NON TROVATA una linea di attivit� con TIPO=CSSAC, cd_cdr_collegato=CDR LA origine, e cd_lacollegato=cd_linea_attivita origine.
  *    PostCondition:
  *      Viene creata una linea di attivit� con codice CdR = aCdrBulk.cd_centro_responsabilita, TIPO=CSSAC, cd_cdr_collegato=CDR LA origine, cd_lacollegato=cd_linea_attivita origine, Funzione = funzione LA origine, e Natura = 4, denominazione = Denominazione LA origine, Descrizione=descrizione LA origine, e Risultati=risultati LA origine. La numerazione segue le logiche della numerazione delle linee di attivit� di tipo Sistema (SXX..XX) dove XX..XX progressivo numerico di numero di cifre = a quanto specificato in lunghezza chiavi per tabella LINEA_ATTIVITA - 1.
  *      
 */

public abstract it.cnr.contab.config00.latt.bulk.WorkpackageBulk creaLineaAttivitaCSSAC(it.cnr.jada.UserContext param0,it.cnr.contab.config00.sto.bulk.CdrBulk param1,it.cnr.contab.config00.latt.bulk.WorkpackageBulk param2) throws it.cnr.jada.comp.ComponentException;

/** 
  *  Linea di attivit� SAUO gi� esistente
  *    PreCondition:
  *      Viene cercata E TROVATA una linea di attivit� con TIPO=SAUO, Funzione=Funzione LA origine, e Natura=Natura LA origine se diversa da 5, altrimenti 1 che corresponde per codice CdR.
  *    PostCondition:
  *      Quest'istanza viene restituita all'utente.
  *  Linea di attivit� SAUO non esiste
  *    PreCondition:
  *      Viene cercata MA NON TROVATA una linea di attivit� con TIPO=SAUO, Funzione=Funzione LA origine, e Natura=Natura LA origine se diversa da 5 altirmenti 1, che corresponde per codice CdR.
  *    PostCondition:
  *      Viene creata una Linea di attivit� per il CdR richiesto con TIPO=SAUO, Funzione=Funzione LA origine, Natura=Natura LA origine e denominazione = 'Spese per costi altrui'. La numerazione per il codice linea di attivit� segue le logiche della numerazione delle linee di attivit� di tipo Sistema (SXX..XX) dove XX..XX progressivo numerico di numero di cifre = a quanto specificato in lunghezza chiavi per tabella LINEA_ATTIVITA - 1.
 */

public abstract it.cnr.contab.config00.latt.bulk.WorkpackageBulk creaLineaAttivitaSAUO(it.cnr.jada.UserContext param0,it.cnr.contab.config00.sto.bulk.CdrBulk param1,it.cnr.contab.config00.latt.bulk.WorkpackageBulk param2) throws it.cnr.jada.comp.ComponentException;

/** 
  *  Linea di attivit� SAUOP gi� esistente
  *    PreCondition:
  *      Viene cercata E TROVATA una linea di attivit� con TIPO=SAUOP, Funzione=01 e Natura=1 che corresponde per codice CdR.
  *    PostCondition:
  *      Quest'istanza viene restituita all'utente, perch� esiste per ogni CDR (servente) una e una sola LA SAUOP.
  *  Linea di attivit� SAUOP non esiste
  *    PreCondition:
  *      Viene cercata MA NON TROVATA una linea di attivit� con TIPO=SAUOP, Funzione=01 e Natura=1 che corresponde per codice CdR.
  *    PostCondition:
  *      Viene creata una Linea di attivit� per il CdR richiesto con TIPO=SAUOP, Funzione=01, Natura=1, e denominazione = 'Spese per costi altrui'. La numerazione per il codice linea di attivit� segue le logiche della numerazione delle linee di attivit� di tipo Sistema (SXX..XX) dove XX..XX progressivo numerico di numero di cifre = a quanto specificato in lunghezza chiavi per tabella LINEA_ATTIVITA - 1.
 */

public abstract it.cnr.contab.config00.latt.bulk.WorkpackageBulk creaLineaAttivitaSAUOP(it.cnr.jada.UserContext param0,it.cnr.contab.config00.sto.bulk.CdrBulk param1) throws it.cnr.jada.comp.ComponentException;

/**
 * Esegue una operazione di eliminazione di una Linea di Attivit�
 *
 * Pre-post-conditions:
 *
 * Nome: Cancellazione di una Linea di attivit� non utilizzata
 * Pre:  La richiesta di cancellazione di una Linea di attivit� e' stata generata
 * Post: La Linea di attivit� e' stato cancellata
 *
 * Nome: Cancellazione di una Linea di attivit� utilizzata
 * Pre:  La richiesta di cancellazione di una Linea di attivit� utilizzata e' stata generata
 * Post: Un messaggio di errrore viene geenrato che suggerisce l'impostazione dell'Esercizio di Terminazione
 * 
 * @param userContext lo userContext che ha generato la richiesta
 * @param bulk l'istanza di Linea di attivit� che deve essere cancellata 
 */

public abstract void eliminaConBulk(it.cnr.jada.UserContext param0,it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException;

/**
 * Prepara un OggettoBulk per la presentazione all'utente per una possibile
 * operazione di modifica.
 *
 *  Normale
 *    PreCondition:
 *      Nessun'altra per-post condition � verificata
 *    PostCondition:
 *      Viene riletta la linea attivit� e caricato l'elenco dei risultati collegati.
 */

public abstract it.cnr.jada.bulk.OggettoBulk inizializzaBulkPerModifica(it.cnr.jada.UserContext param0,it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException;

/**
 *  Normale
 *    PreCondition:
 *      E' stato modificata la natura o il cdr di una linea di attivit� con gestione spesa
 *    PostCondition:
 *      Viene impostato l'insieme uguale a quello della prima linea attivit� con la stessa natura e cdr di quella specificata.
 */

public abstract it.cnr.contab.config00.latt.bulk.WorkpackageBulk inizializzaNaturaPerInsieme(it.cnr.jada.UserContext param0,it.cnr.contab.config00.latt.bulk.WorkpackageBulk param1) throws it.cnr.jada.comp.ComponentException;

/**
  *  Esercizio fine validit� non valido
  *	   PreCondition:
  *		 Viene invocato il metodo aggiornaEsercizioFine e quest'ultimo genera una eccezione di validazione
  *    PostCondition:
  *		 Viene lasciata uscire l'eccezione senza salvare la linea di attivit�
  *  Default
  *    PreCondition:
  *      Nessun'altra precondizione � verificata
  *    PostCondition:
  *		 Invoca:
  *        validaFunzione()
  *        validaModificaInsieme()
  *        validaNaturaPerInsieme()
  *        validaModificaFunzioneNatura()
  *      ed infine viene salvata la linea di attivit� specificata.
 */

public abstract it.cnr.jada.bulk.OggettoBulk modificaConBulk(it.cnr.jada.UserContext param0,it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException;
}