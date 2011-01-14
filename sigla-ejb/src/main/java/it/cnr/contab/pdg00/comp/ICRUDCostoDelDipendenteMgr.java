package it.cnr.contab.pdg00.comp;

public interface ICRUDCostoDelDipendenteMgr extends it.cnr.jada.comp.ICRUDMgr {
/*
 * Pre-post-conditions:
 *
 * Nome: Dipendente non modificabile
 * Pre: Viene richiesta l'inizializzazione per modifica dei costi del dipendente.
 *		Per la matricola specificata esiste gi� una ripartizione dei costi.
 * Post: Viene impostato a false il flag "modificabile" del V_dipendenteBulk restituito
 * Nome: Tutti i controlli superati
 * Pre:	Viene richiesta l'inizializzazione per modifica dei costi del dipendente.
 *		Nessuna delle altre pre-condizioni � verificata.
 * Post: Viene caricato un oggetto V_dipendenteBulk per la matricola specificata e l'elenco
 *		dei costi per voce del pdc (Costo_del_dipendenteBulk)
 */

public abstract it.cnr.jada.bulk.OggettoBulk inizializzaBulkPerModifica(it.cnr.jada.UserContext param0,it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException;
/*
 * Pre-post-conditions:
 *
 * Nome: Dipendente non modificabile
 * Pre: Viene richiesta la modifica dei costi del dipendente ma per la matricola specificata
 *		esiste gi� una ripartizione dei costi.
 * Post: Viene generata una ApplicationException con il messaggio: "Dipendente non modificabile perch� � gi� stata fatta una ripartizione dei costi."
 * Nome: Unit� organizzativa del dipendente modificata
 * Pre: Viene richiesta la modifica dei costi del dipendente ed � stata modificata
 *		l'unit� organizzativa di appartenenza.
 * Post: Viene eliminata la matricola specificata dalla tabella COSTO_DEL_DIPENDENTE e
 *		vengono inseriti nuovi record nella stessa tabella con la nuova u.o
 * Nome: Tutti i controlli superati
 * Pre:	Viene richiesta la modifica dei costi del dipendente.
 *		Nessuna delle altre pre-condizioni � verificata.
 * Post: Vengono salvati i costi del dipendente.
 */

public abstract it.cnr.jada.bulk.OggettoBulk modificaConBulk(it.cnr.jada.UserContext param0,it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException;
}
