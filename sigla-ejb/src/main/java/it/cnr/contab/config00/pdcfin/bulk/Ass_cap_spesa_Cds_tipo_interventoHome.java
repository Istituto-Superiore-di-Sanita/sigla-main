package it.cnr.contab.config00.pdcfin.bulk;

import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.sql.*;

/**
 * Home che gestisce i capitoli di spesa del Cds associati alla tipologia
 * di intervento.
 */
public class Ass_cap_spesa_Cds_tipo_interventoHome extends Ass_ev_evHome {
public Ass_cap_spesa_Cds_tipo_interventoHome(java.sql.Connection conn) {
	super(Ass_cap_spesa_Cds_tipo_interventoBulk.class,conn);
}
public Ass_cap_spesa_Cds_tipo_interventoHome(java.sql.Connection conn, it.cnr.jada.persistency.PersistentCache persistentCache) {
	super(Ass_cap_spesa_Cds_tipo_interventoBulk.class,conn,persistentCache);
}
/**
 * Restituisce il SQLBuilder per selezionare fra tutti gli elementi voce quelli relativi ai capitoli di spesa
 * del CDS e ai capitoli di spesa del CNR.
 * @return SQLBuilder 
 */
public SQLBuilder createSQLBuilder( )	
{
	SQLBuilder sql = super.createSQLBuilder();
	sql.addClause("AND", "ti_appartenenza", SQLBuilder.EQUALS, Elemento_voceHome.APPARTENENZA_CDS);
	sql.addClause("AND", "ti_gestione", SQLBuilder.EQUALS, Elemento_voceHome.GESTIONE_SPESE );
	sql.addClause("AND", "ti_elemento_voce", SQLBuilder.EQUALS, Elemento_voceHome.TIPO_CAPITOLO );	
	sql.addClause("AND", "ti_appartenenza_coll", SQLBuilder.EQUALS, Elemento_voceHome.APPARTENENZA_CNR );
	sql.addClause("AND", "ti_gestione_coll", SQLBuilder.EQUALS, Elemento_voceHome.GESTIONE_SPESE );
	sql.addClause("AND", "ti_elemento_voce_coll", SQLBuilder.EQUALS, Elemento_voceHome.TIPO_CAPITOLO );	
	sql.addClause("AND", "cd_natura", SQLBuilder.EQUALS, "*" );
	return sql; 
}
/**
 * Restituisce il SQLBuilder per selezionare le tipologie di intervento di Spesa del Cnr per l'esercizio di scrivania.
 * @param bulk bulk ricevente
 * @param home home del bulk su cui si cerca
 * @param bulkClause � l'istanza di bulk che ha indotto le clauses 
 * @param clause clause che arrivano dalle properties (form collegata al search tool) 
 * @return it.cnr.jada.persistency.sql.SQLBuilder
 */
public SQLBuilder selectElemento_voce_collByClause( Ass_cap_spesa_Cds_tipo_interventoBulk bulk, Elemento_voceHome home,it.cnr.jada.bulk.OggettoBulk bulkClause,CompoundFindClause clause) throws java.lang.reflect.InvocationTargetException,IllegalAccessException, it.cnr.jada.persistency.PersistencyException {
	SQLBuilder sql = home.createSQLBuilder();
	sql.addClause("AND", "ti_appartenenza", SQLBuilder.EQUALS, home.APPARTENENZA_CNR );
	sql.addClause("AND", "ti_gestione", SQLBuilder.EQUALS, home.GESTIONE_SPESE );
	sql.addClause("AND", "ti_elemento_voce", SQLBuilder.EQUALS, home.TIPO_CAPITOLO );
	sql.addClause("AND", "cd_parte", SQLBuilder.EQUALS, Elemento_voceHome.PARTE_1 );
	sql.addClause("AND", "esercizio", SQLBuilder.EQUALS, bulk.getEsercizio() );		
	sql.addClause( clause );
	return sql;
}
/**
 * Restituisce il SQLBuilder per selezionare le Categorie di Spesa del Cds per l'esercizio di scrivania.
 * @param bulk bulk ricevente
 * @param home home del bulk su cui si cerca
 * @param bulkClause � l'istanza di bulk che ha indotto le clauses 
 * @param clause clause che arrivano dalle properties (form collegata al search tool) 
 * @return it.cnr.jada.persistency.sql.SQLBuilder
 */
public SQLBuilder selectElemento_voceByClause( Ass_cap_spesa_Cds_tipo_interventoBulk bulk, Elemento_voceHome home,it.cnr.jada.bulk.OggettoBulk bulkClause,CompoundFindClause clause) throws java.lang.reflect.InvocationTargetException,IllegalAccessException, it.cnr.jada.persistency.PersistencyException {
	SQLBuilder sql = home.createSQLBuilder();
	sql.addClause("AND", "ti_appartenenza", SQLBuilder.EQUALS, home.APPARTENENZA_CDS );
	sql.addClause("AND", "ti_gestione", SQLBuilder.EQUALS, home.GESTIONE_SPESE );
	sql.addClause("AND", "ti_elemento_voce", SQLBuilder.EQUALS, home.TIPO_CAPITOLO );
	sql.addClause("AND", "esercizio", SQLBuilder.EQUALS, bulk.getEsercizio() );		
	sql.addClause( clause );
	return sql;
}
}
