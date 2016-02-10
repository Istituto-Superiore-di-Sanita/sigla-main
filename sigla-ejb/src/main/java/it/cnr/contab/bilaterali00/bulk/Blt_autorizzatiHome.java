/*
 * Created by BulkGenerator 2.0 [07/12/2009]
 * Date 02/02/2012
 */
package it.cnr.contab.bilaterali00.bulk;
import it.cnr.jada.bulk.BulkHome;
import it.cnr.jada.persistency.IntrospectionException;
import it.cnr.jada.persistency.PersistencyException;
import it.cnr.jada.persistency.PersistentCache;
import it.cnr.jada.persistency.sql.FindClause;
import it.cnr.jada.persistency.sql.PersistentHome;
import it.cnr.jada.persistency.sql.SQLBuilder;

import java.sql.Connection;
import java.util.List;
public class Blt_autorizzatiHome extends BulkHome {
	public Blt_autorizzatiHome(Connection conn) {
		super(Blt_autorizzatiBulk.class, conn);
	}
	public Blt_autorizzatiHome(Connection conn, PersistentCache persistentCache) {
		super(Blt_autorizzatiBulk.class, conn, persistentCache);
	}
	public java.util.List findBltAutorizzatiDettList( it.cnr.jada.UserContext userContext,Blt_autorizzatiBulk autorizzati ) throws IntrospectionException,PersistencyException 
	{
		PersistentHome autorizzatiDettHome = getHomeCache().getHome(Blt_autorizzati_dettBulk.class );
		SQLBuilder sql = autorizzatiDettHome.createSQLBuilder();
		if (autorizzati.getCdAccordo()==null)
			sql.addClause(FindClause.AND,"cdAccordo",SQLBuilder.ISNULL, null);
		else
			sql.addClause(FindClause.AND,"cdAccordo",SQLBuilder.EQUALS, autorizzati.getCdAccordo());

		if (autorizzati.getCdProgetto()==null)
			sql.addClause(FindClause.AND,"cdProgetto",SQLBuilder.ISNULL, null);
		else
			sql.addClause(FindClause.AND,"cdProgetto",SQLBuilder.EQUALS, autorizzati.getCdProgetto());
		
		if (autorizzati.getCdTerzo()==null)
			sql.addClause(FindClause.AND,"cdTerzo",SQLBuilder.ISNULL, null);
		else
			sql.addClause(FindClause.AND,"cdTerzo",SQLBuilder.EQUALS, autorizzati.getCdTerzo());

		sql.addOrderBy("ANNO_VISITA, PG_AUTORIZZAZIONE");
		List l =  autorizzatiDettHome.fetchAll(sql);
		getHomeCache().fetchAll(userContext);
		return l;
	}
	public java.util.Collection findCaps_comune(Blt_autorizzatiBulk autorizzato, it.cnr.contab.anagraf00.tabter.bulk.CapHome capHome, it.cnr.contab.anagraf00.tabter.bulk.CapBulk clause) throws IntrospectionException, PersistencyException {
		return ((it.cnr.contab.anagraf00.tabter.bulk.ComuneHome)getHomeCache().getHome(it.cnr.contab.anagraf00.tabter.bulk.ComuneBulk.class)).findCaps(autorizzato.getComuneEnteDiAppartenenza());
	}
}