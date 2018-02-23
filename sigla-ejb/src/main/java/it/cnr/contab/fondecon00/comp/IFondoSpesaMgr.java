package it.cnr.contab.fondecon00.comp;

/**
 * Insert the type's description here.
 * Creation date: (30/01/2002 15.42.05)
 * @author: Luca Bessi
 */
public interface IFondoSpesaMgr extends it.cnr.jada.comp.ICRUDMgr {
/**
 * Set dei parametri di default in creazione di fondi e spese.
 *
 * Creazione Fondo_economaleBulk:
 * importo totale spese = 0,
 * importo residuo fondo = importo ammontare fondo,
 * se importo ammontare iniziale è nullo importo ammontare iniziale = importo ammontare fondo.
 *
 * Creazione Fondo_spesaBulk:
 * inizializzazione della spesa; vedi initSpesa.
 */

public abstract it.cnr.jada.bulk.OggettoBulk creaConBulk(it.cnr.jada.UserContext param0,it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException;
/** 
  *  Oggetto non esistente
  *    PreCondition:
  *      L'OggettoBulk specificato non esiste.
  *    PostCondition:
  *      Viene generata una CRUDException con la descrizione dell'errore.
 */

public abstract it.cnr.jada.bulk.OggettoBulk inizializzaBulkPerModifica(it.cnr.jada.UserContext param0,it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException;
/**
 * Imposta il comune fiscale relativo alla spesa.
 *
 * Nome: Gestione comune fiscale;
 * Pre:  Ricerca del comune e acricamenti dei cap relativi;
 * Post: Viene assegnato il comune e lanciato l'aggornamento dell'elenco dei cap associati.
 *
 * @param spsea Fondo_spesaBulk su cui va impostato il comune fiscale.
 * @param comune il ComuneBulk del comune da impostare.
 *
 * @return Fondo_spesaBulk con comune impostato.
 */

public abstract it.cnr.contab.fondecon00.core.bulk.Fondo_spesaBulk setCitta(it.cnr.jada.UserContext param0,it.cnr.contab.fondecon00.core.bulk.Fondo_spesaBulk param1,it.cnr.contab.anagraf00.tabter.bulk.ComuneBulk param2) throws it.cnr.jada.comp.ComponentException;
}
