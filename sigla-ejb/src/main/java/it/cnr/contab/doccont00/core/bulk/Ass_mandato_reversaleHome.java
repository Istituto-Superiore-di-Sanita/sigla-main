package it.cnr.contab.doccont00.core.bulk;

import java.util.*;

import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class Ass_mandato_reversaleHome extends BulkHome {
/**
 * <!-- @TODO: da completare -->
 * Costruisce un Ass_mandato_reversaleHome
 *
 * @param conn	La java.sql.Connection su cui vengono effettuate le operazione di persistenza
 */
public Ass_mandato_reversaleHome(java.sql.Connection conn) {
	super(Ass_mandato_reversaleBulk.class,conn);
}
/**
 * <!-- @TODO: da completare -->
 * Costruisce un Ass_mandato_reversaleHome
 *
 * @param conn	La java.sql.Connection su cui vengono effettuate le operazione di persistenza
 * @param persistentCache	La PersistentCache in cui vengono cachati gli oggetti persistenti caricati da questo Home
 */
public Ass_mandato_reversaleHome(java.sql.Connection conn,PersistentCache persistentCache) {
	super(Ass_mandato_reversaleBulk.class,conn,persistentCache);
}
/**
 * Metodo per cercare i mandati associati alla reversale.
 *
 * @param reversale <code>ReversaleBulk</code> la reversale
 *
 * @return result i mandati associati alla reversale
 */
public Collection findMandati( it.cnr.jada.UserContext userContext,ReversaleBulk reversale ) throws PersistencyException, IntrospectionException
{
	PersistentHome home = getHomeCache().getHome( Ass_mandato_reversaleBulk.class );
	SQLBuilder sql = home.createSQLBuilder();
	sql.addClause("AND","esercizio_reversale",sql.EQUALS, reversale.getEsercizio() );
	sql.addClause("AND","cd_cds_reversale",sql.EQUALS, reversale.getCds().getCd_unita_organizzativa() );
	sql.addClause("AND","pg_reversale",sql.EQUALS, reversale.getPg_reversale() );
	Collection result = home.fetchAll( sql);
	getHomeCache().fetchAll(userContext);
	return result;
}	

/**
 * Metodo per cercare le reversali associate al mandato.
 *
 * @param mandato <code>MandatoBulk</code> il mandato
 *
 * @return result le reversali associate al mandato
 */
public Collection findReversali( it.cnr.jada.UserContext userContext,MandatoBulk mandato ) throws PersistencyException, IntrospectionException
{
	PersistentHome home = getHomeCache().getHome( Ass_mandato_reversaleBulk.class );
	SQLBuilder sql = home.createSQLBuilder();
	sql.addClause("AND","esercizio_mandato",sql.EQUALS, mandato.getEsercizio() );
	sql.addClause("AND","cd_cds_mandato",sql.EQUALS, mandato.getCds().getCd_unita_organizzativa() );
	sql.addClause("AND","pg_mandato",sql.EQUALS, mandato.getPg_mandato() );
	Collection result = home.fetchAll( sql);
	getHomeCache().fetchAll(userContext);
	return result;
}	

}
