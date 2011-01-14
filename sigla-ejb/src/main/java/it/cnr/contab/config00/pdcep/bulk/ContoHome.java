package it.cnr.contab.config00.pdcep.bulk;

import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.sql.*;

/**
 * Home che gestisce i conti.
 */
public class ContoHome extends Voce_epHome {
	
public ContoHome(java.sql.Connection conn) {
	super(ContoBulk.class,conn);
}
public ContoHome(java.sql.Connection conn, it.cnr.jada.persistency.PersistentCache persistentCache) {
	super(ContoBulk.class,conn,persistentCache);
}
/**
 * Restituisce il SQLBuilder per selezionare fra tutte le Voci Economico Patrimoniali quelle relative
 * ai Conti.
 * @return SQLBuilder 
 */
public SQLBuilder createSQLBuilder( )	
{
	SQLBuilder sql = super.createSQLBuilder();
	sql.addClause("AND", "ti_voce_ep", SQLBuilder.EQUALS, TIPO_CONTO);
	return sql; 
}
/**
 * Restituisce il SQLBuilder per selezionare gli elementi con Sezione di tipo Avere per l'esercizio di scrivania.
 * @param bulk bulk ricevente
 * @param home home del bulk su cui si cerca
 * @param bulkClause � l'istanza di bulk che ha indotto le clauses 
 * @param clause clause che arrivano dalle properties (form collegata al search tool) 
 * @return it.cnr.jada.persistency.sql.SQLBuilder
 */
public SQLBuilder selectContiCostoByClause( Voce_epBulk bulk,it.cnr.jada.bulk.BulkHome home,it.cnr.jada.bulk.OggettoBulk bulkClause,CompoundFindClause clause) throws java.lang.reflect.InvocationTargetException,IllegalAccessException, it.cnr.jada.persistency.PersistencyException {
	SQLBuilder sql = home.selectByClause(clause);
	sql.addClause("AND", "ti_sezione", SQLBuilder.NOT_EQUALS, SEZIONE_AVERE );
	sql.addClause("AND", "esercizio", SQLBuilder.EQUALS, bulk.getEsercizio() );		
	return sql;
}
/**
 * Restituisce il SQLBuilder per selezionare gli elementi con Sezione di tipo Dare per l'esercizio di scrivania.
 * @param bulk bulk ricevente
 * @param home home del bulk su cui si cerca
 * @param bulkClause � l'istanza di bulk che ha indotto le clauses 
 * @param clause clause che arrivano dalle properties (form collegata al search tool) 
 * @return it.cnr.jada.persistency.sql.SQLBuilder
 */
public SQLBuilder selectContiRicavoByClause( Voce_epBulk bulk,it.cnr.jada.bulk.BulkHome home,it.cnr.jada.bulk.OggettoBulk bulkClause,CompoundFindClause clause) throws java.lang.reflect.InvocationTargetException,IllegalAccessException, it.cnr.jada.persistency.PersistencyException {
	SQLBuilder sql = home.selectByClause(clause);
	sql.addClause("AND", "ti_sezione", SQLBuilder.NOT_EQUALS, SEZIONE_DARE );
	sql.addClause("AND", "esercizio", SQLBuilder.EQUALS, bulk.getEsercizio() );		
	return sql;
}
}
