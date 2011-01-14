package it.cnr.contab.docamm00.tabrif.bulk;

import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

import java.sql.*;

public class TariffarioHome extends BulkHome {
public TariffarioHome(java.sql.Connection conn) {
	super(TariffarioBulk.class,conn);
}
public TariffarioHome(java.sql.Connection conn,PersistentCache persistentCache) {
	super(TariffarioBulk.class,conn,persistentCache);
}
/**
 * Metodo che controlla la validit� del tariffario relativo ad un'unit� 
 * organizzativa dato il suo codice
 *
 * Creation date: (06/11/2001 17.00.04)
 *
 * @return boolean
 */

public boolean checkPeriodi(UserContext userContext, TariffarioBulk tariffario)
    throws PersistencyException, it.cnr.jada.comp.ApplicationException {

    boolean accepted = false;

    /* Ricava tutte le righe del tariffario con quella unit� organizzativa e quel codice */

    Timestamp maxData = (Timestamp) findMax(tariffario, "dt_ini_validita", null);
    Timestamp maxDataFineValidita = (Timestamp) findMax(tariffario, "dt_fine_validita", null);

    if (maxData == null) // non ci sono records in tabella: l'inserzione pu� avvenire   
        accepted = true;
    else {
        /* controlla che la data che viene immessa sia successiva alla pi� 
        	recente tra quelle in tabella: in tal caso l'inserzione avviene */
        if (tariffario.getDt_ini_validita() == null)
            throw new it.cnr.jada.comp.ApplicationException("La data di inizio validit� non pu� essere vuota!");

        if (maxDataFineValidita==null || !maxDataFineValidita.equals(it.cnr.contab.config00.esercizio.bulk.EsercizioHome.DATA_INFINITO))
            if (tariffario.getDt_ini_validita().after(maxData)) {
                /* aggiorna la data di fine_validita dell'ultimo periodo gi� inserito: corrisponde 
            alla data del giorno precedente a quella ini_validita che si vuol inserire */
                TariffarioBulk vecchioTariffario =
                    (TariffarioBulk) findByPrimaryKey(new TariffarioBulk(tariffario.getCd_tariffario(),
                        tariffario.getCd_unita_organizzativa(),
                        maxData),
                        true);
                vecchioTariffario.setDt_fine_validita(
                    it.cnr.contab.compensi00.docs.bulk.CompensoBulk.decrementaData(tariffario.getDt_ini_validita()));
                update(vecchioTariffario, userContext);
                accepted = true;
            } else
                accepted = false;
        else
        	accepted = true;
    }

    tariffario.setDt_fine_validita(it.cnr.contab.config00.esercizio.bulk.EsercizioHome.DATA_INFINITO);
    return accepted;
}
}
