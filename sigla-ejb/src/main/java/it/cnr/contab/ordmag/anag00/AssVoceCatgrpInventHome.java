/*
 * Created by Aurelio's BulkGenerator 1.0
 * Date 07/05/2007
 */
package it.cnr.contab.ordmag.anag00;
import java.sql.Connection;

import it.cnr.contab.config00.bulk.Codici_siopeHome;
import it.cnr.contab.config00.pdcfin.bulk.Elemento_voceHome;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.BulkHome;
import it.cnr.jada.persistency.PersistencyException;
import it.cnr.jada.persistency.PersistentCache;
import it.cnr.jada.persistency.sql.CompoundFindClause;
import it.cnr.jada.persistency.sql.FindClause;
import it.cnr.jada.persistency.sql.SQLBuilder;

public class AssVoceCatgrpInventHome extends BulkHome {
	public AssVoceCatgrpInventHome(Connection conn) {
		super(AssVoceCatgrpInventBulk.class, conn);
	}
	public AssVoceCatgrpInventHome(Connection conn, PersistentCache persistentCache) {
		super(AssVoceCatgrpInventBulk.class, conn, persistentCache);
	}
	
	public SQLBuilder selectElemento_voceByClause( AssVoceCatgrpInventBulk bulk, Elemento_voceHome home,it.cnr.jada.bulk.OggettoBulk bulkClause,CompoundFindClause clause) throws java.lang.reflect.InvocationTargetException,IllegalAccessException, it.cnr.jada.persistency.PersistencyException {
		SQLBuilder sql = home.createSQLBuilder();
		sql.addClause("AND", "esercizio", SQLBuilder.EQUALS, bulk.getEsercizio() );		
		sql.addClause("AND", "ti_appartenenza", SQLBuilder.EQUALS,bulk.getTi_appartenenza());
		sql.addClause("AND", "ti_gestione", SQLBuilder.EQUALS, bulk.getTi_gestione());

		sql.addTableToHeader("PARAMETRI_CNR");
		sql.addSQLJoin("PARAMETRI_CNR.ESERCIZIO","ELEMENTO_VOCE.ESERCIZIO");

		sql.openParenthesis(FindClause.AND);
		sql.addSQLClause(FindClause.OR,"PARAMETRI_CNR.FL_NUOVO_PDG='Y'");
		sql.addClause(FindClause.OR, "ti_elemento_voce", SQLBuilder.EQUALS, Elemento_voceHome.TIPO_CAPITOLO);
		sql.closeParenthesis();
		
		sql.addClause( clause );
		return sql;
	}
	
	public SQLBuilder selectByClause(UserContext usercontext, CompoundFindClause compoundfindclause) throws PersistencyException {
		SQLBuilder sql = super.selectByClause(usercontext, compoundfindclause);
	    sql.addClause("AND","esercizio",SQLBuilder.EQUALS, CNRUserContext.getEsercizio(usercontext));
		return sql;
	}
}