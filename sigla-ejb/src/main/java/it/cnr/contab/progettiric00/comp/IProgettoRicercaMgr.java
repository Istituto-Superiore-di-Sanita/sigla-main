package it.cnr.contab.progettiric00.comp;

import it.cnr.jada.UserContext;
import it.cnr.jada.comp.ApplicationException;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.comp.ICRUDMgr;
import it.cnr.jada.persistency.PersistencyException;
import it.cnr.jada.persistency.sql.CompoundFindClause;
import it.cnr.jada.persistency.sql.SQLBuilder;

public interface IProgettoRicercaMgr extends ICRUDMgr {


/**
 * Pre:  Controllo Dt_inizio > Dt_fine
 * Post: Segnalazione "Data di fine deve essere maggiore della data di inizio!"
 *
 * Pre:  Controllo se Dt_fine = null e Dt_proroga != null
 * Post: Segnalazione "Non pu� esistere una data di proroga se non si indica una data di fine!"
 *
 * Pre:  Controllo Dt_fine > Dt_proroga
 * Post: Segnalazione "Data di proroga deve essere maggiore della data di fine!"
 *
 * Pre:  Controllo se la lista dei dettagli � vuota
 * Post: Se vuota viene creato un unico dettaglio che ha:
 *			UO = l'UO coordinatrice del Progetto
 *			Responsabile = Responsabile del Progetto
 *			Importo = Importo del Progetto
 *			
 * Pre:  Controllo somma importo dettagli != da importo del Progetto
 * Post: Segnalazione "La somma degli importi degli assegnatari � diversa dall'importo del Progetto"
 *
 */

public abstract it.cnr.jada.bulk.OggettoBulk creaConBulk(it.cnr.jada.UserContext param0,it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException;
/**
 * Pre:  Preparare l'oggetto alle modifiche;
 * Post: carica la lista di dettagli associati a un Progetto
 */

public abstract it.cnr.jada.bulk.OggettoBulk inizializzaBulkPerModifica(it.cnr.jada.UserContext param0,it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException;
/**
 * Pre:  Controllo Dt_inizio > Dt_fine
 * Post: Segnalazione "Data di fine deve essere maggiore della data di inizio!"
 *
 * Pre:  Controllo se Dt_fine = null e Dt_proroga != null
 * Post: Segnalazione "Non pu� esistere una data di proroga se non si indica una data di fine!"
 *
 * Pre:  Controllo Dt_fine > Dt_proroga
 * Post: Segnalazione "Data di proroga deve essere maggiore della data di fine!"
 *
 * Pre:  Controllo se la lista dei dettagli � vuota
 * Post: Se vuota viene creato un unico dettaglio che ha:
 *			UO = l'UO coordinatrice del Progetto
 *			Responsabile = Responsabile del Progetto
 *			Importo = Importo del Progetto
 *			
 * Pre:  Controllo somma importo dettagli != da importo del Progetto
 * Post: Segnalazione "La somma degli importi degli assegnatari � diversa dall'importo del Progetto"
 *
 */

public abstract it.cnr.jada.bulk.OggettoBulk modificaConBulk(it.cnr.jada.UserContext param0,it.cnr.jada.bulk.OggettoBulk param1) throws it.cnr.jada.comp.ComponentException;
}
