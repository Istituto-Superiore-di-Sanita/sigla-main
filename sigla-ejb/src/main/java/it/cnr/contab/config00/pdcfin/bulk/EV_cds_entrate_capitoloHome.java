package it.cnr.contab.config00.pdcfin.bulk;

import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.sql.*;

/**
 * Home che gestisce i capitoli di entrata del CDS
 */
public class EV_cds_entrate_capitoloHome extends Elemento_voceHome {
	public EV_cds_entrate_capitoloHome(java.sql.Connection conn) {
	super(EV_cds_entrate_capitoloBulk.class,conn);
}
public EV_cds_entrate_capitoloHome(java.sql.Connection conn,PersistentCache persistentCache) {
	super(EV_cds_entrate_capitoloBulk.class,conn,persistentCache);
}
/**
 * Restituisce il SQLBuilder per selezionare fra tutti gli elementi voce quelli relativi ai capitoli di entrata
 * del CDS
 * @return SQLBuilder 
 */

public SQLBuilder createSQLBuilder() 
{
	SQLBuilder sql = super.createSQLBuilder();
	sql.addClause("AND", "ti_appartenenza", SQLBuilder.EQUALS, APPARTENENZA_CDS );
	sql.addClause("AND", "ti_gestione", SQLBuilder.EQUALS, GESTIONE_ENTRATE );
	sql.addClause("AND", "ti_elemento_voce", SQLBuilder.EQUALS, TIPO_CAPITOLO );		
	return sql;
}
/**
 * Restituisce il SQLBuilder per selezionare i Titoli di Entrata del Cds per l'esercizio di scrivania
 * @param bulk bulk ricevente
 * @param home home del bulk su cui si cerca
 * @param bulkClause è l'istanza di bulk che ha indotto le clauses 
 * @param clause clause che arrivano dalle properties (form collegata al search tool) 
 * @return it.cnr.jada.persistency.sql.SQLBuilder
 */


public SQLBuilder selectElemento_padreByClause(EV_cds_entrate_capitoloBulk bulk,Elemento_voceHome home,it.cnr.jada.bulk.OggettoBulk bulkClause,CompoundFindClause clause) throws java.lang.reflect.InvocationTargetException,IllegalAccessException, it.cnr.jada.persistency.PersistencyException {
	SQLBuilder sql = home.createSQLBuilder();
	sql.addClause("AND", "ti_appartenenza", SQLBuilder.EQUALS, APPARTENENZA_CDS );
	sql.addClause("AND", "ti_gestione", SQLBuilder.EQUALS, GESTIONE_ENTRATE );
	sql.addClause("AND", "ti_elemento_voce", SQLBuilder.EQUALS, (String) Elemento_voceHome.getTipoPadre(APPARTENENZA_CDS, GESTIONE_ENTRATE, TIPO_CAPITOLO ));
	sql.addClause("AND", "esercizio", SQLBuilder.EQUALS, bulk.getEsercizio() );		
	sql.addClause( clause );
	return sql;
}
}
