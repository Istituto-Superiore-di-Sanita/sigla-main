package it.cnr.contab.pdg00.comp;
import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.comp.ApplicationException;
import it.cnr.jada.comp.CRUDComponent;
import it.cnr.jada.comp.ComponentException;
import it.cnr.jada.comp.IMultipleCRUDMgr;

import java.io.Serializable;
import java.util.Vector;

/**
  * Componente di gestione nel servito dei costi scricati verso altra uo (Spese)
  */

public class PdGCostiScaricatiSpeComponent extends PdGComponent implements Cloneable,Serializable,IPdGCostiScaricatiMgr {
/**
 * PdGCostiScaricatiSpeComponent constructor comment.
 */
public PdGCostiScaricatiSpeComponent() {
	super();
}

    //^^@@
/** 
  *  default
  *    PreCondition:
  *      Viene richiesto di eliminare un dettaglio scaricato
  *    PostCondition:
  *      Restituisce un'eccezione
 */
//^^@@
	public void eliminaConBulk (it.cnr.jada.UserContext userContext,it.cnr.jada.bulk.OggettoBulk bulk) throws it.cnr.jada.comp.ComponentException {
		throw new it.cnr.jada.comp.ApplicationException("Non è possibile eliminare il dettaglio caricato!");
	}

    //^^@@
/** 
  *  default
  *    PreCondition:
  *      Viene richiesto di modificare un dettaglio scaricato
  *    PostCondition:
  *      Restituisce un'eccezione
 */
//^^@@
	public it.cnr.jada.bulk.OggettoBulk modificaConBulk(it.cnr.jada.UserContext userContext,it.cnr.jada.bulk.OggettoBulk bulk) throws it.cnr.jada.comp.ComponentException {
//		return bulk;
			throw new it.cnr.jada.comp.ApplicationException("Non è possibile modificare il dettaglio caricato!");
	}

/**	
  * Costruisce un SQL builder per l'estrazione dei dettagli scaricati verso altra uo nel servito
  */

	protected it.cnr.jada.persistency.sql.Query select(it.cnr.jada.UserContext userContext,it.cnr.jada.persistency.sql.CompoundFindClause clauses,it.cnr.jada.bulk.OggettoBulk bulk) throws it.cnr.jada.comp.ComponentException, it.cnr.jada.persistency.PersistencyException {
		if (clauses == null) clauses = bulk.buildFindClauses(null);
		if (clauses == null) clauses = new it.cnr.jada.persistency.sql.CompoundFindClause();

		clauses.addClause("AND",
						  "esercizio",
						  it.cnr.jada.persistency.sql.SQLBuilder.EQUALS,
						  ((it.cnr.contab.pdg00.bulk.Pdg_preventivo_spe_detBulk)bulk).getEsercizio()
						 );
		clauses.addClause("AND",
						  "cd_centro_responsabilita",
						  it.cnr.jada.persistency.sql.SQLBuilder.EQUALS,
						  ((it.cnr.contab.pdg00.bulk.Pdg_preventivo_spe_detBulk)bulk).getCd_centro_responsabilita()
						 );
		clauses.addClause("AND",
						  "categoria_dettaglio",
						  it.cnr.jada.persistency.sql.SQLBuilder.EQUALS,
						  it.cnr.contab.pdg00.bulk.Pdg_preventivo_detBulk.CAT_SCARICO
						 );

		clauses.addClause("AND",
						  "cd_centro_responsabilita_clgs",
						  it.cnr.jada.persistency.sql.SQLBuilder.ISNOTNULL,
						  null
						 );

		return super.select(userContext,clauses,bulk);
	}

}
