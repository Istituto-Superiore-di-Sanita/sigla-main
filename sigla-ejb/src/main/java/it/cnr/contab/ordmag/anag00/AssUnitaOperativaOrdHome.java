/*
 * Created by BulkGenerator 2.0 [07/12/2009]
 * Date 26/04/2017
 */
package it.cnr.contab.ordmag.anag00;
import java.sql.Connection;

import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativa_enteBulk;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.BulkHome;
import it.cnr.jada.persistency.PersistencyException;
import it.cnr.jada.persistency.PersistentCache;
import it.cnr.jada.persistency.sql.CompoundFindClause;
import it.cnr.jada.persistency.sql.SQLBuilder;
public class AssUnitaOperativaOrdHome extends BulkHome {
	public AssUnitaOperativaOrdHome(Connection conn) {
		super(AssUnitaOperativaOrdBulk.class, conn);
	}
	public AssUnitaOperativaOrdHome(Connection conn, PersistentCache persistentCache) {
		super(AssUnitaOperativaOrdBulk.class, conn, persistentCache);
	}

	public SQLBuilder selectUnitaOperativaOrdRifByClause(UserContext userContext, AssUnitaOperativaOrdBulk assUopBulk, 
			UnitaOperativaOrdHome unitaOperativaHome, UnitaOperativaOrdBulk unitaOperativaBulk, 
			CompoundFindClause compoundfindclause) throws PersistencyException{
		SQLBuilder sql = unitaOperativaHome.selectByClause(userContext, compoundfindclause);
		filtraUO(userContext, sql, false);
		if (assUopBulk.getCdUnitaOperativa() != null){
			sql.addSQLClause("AND","cd_unita_operativa",SQLBuilder.NOT_EQUALS,assUopBulk.getCdUnitaOperativa());
		}
		return sql;
	}

	private void filtraUO(UserContext userContext, SQLBuilder sql, boolean join) throws PersistencyException{
		Unita_organizzativa_enteBulk ente = (Unita_organizzativa_enteBulk) 
				getHomeCache().getHome(Unita_organizzativa_enteBulk.class).findAll().get(0);
		if (!CNRUserContext.getCd_unita_organizzativa(userContext).equals(ente.getCd_unita_organizzativa())){
			Unita_organizzativaBulk uoScrivania = (Unita_organizzativaBulk)getHomeCache().getHome(Unita_organizzativaBulk.class).
					findByPrimaryKey(new Unita_organizzativaBulk(CNRUserContext.getCd_unita_organizzativa(userContext)));
			if(!uoScrivania.isUoCds())
				sql.addSQLClause("AND","CD_UNITA_ORGANIZZATIVA",SQLBuilder.EQUALS,CNRUserContext.getCd_unita_organizzativa(userContext));
			else {
				if (join){
					sql.addTableToHeader("UNITA_ORGANIZZATIVA");
					sql.addSQLJoin("UNITA_OPERATIVA_ORD.CD_UNITA_ORGANIZZATIVA", "UNITA_ORGANIZZATIVA.CD_UNITA_ORGANIZZATIVA");
					sql.addSQLClause("AND", "UNITA_ORGANIZZATIVA.CD_UNITA_PADRE", SQLBuilder.EQUALS, CNRUserContext.getCd_cds(userContext));
				}else {
					sql.addSQLClause("AND","CD_UNITA_PADRE",SQLBuilder.EQUALS,CNRUserContext.getCd_cds(userContext));
				}
			}
		}
	}
}