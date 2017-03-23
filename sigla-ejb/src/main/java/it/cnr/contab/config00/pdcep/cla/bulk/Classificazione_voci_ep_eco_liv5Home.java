/*
 * Created on Aug 29, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.cnr.contab.config00.pdcep.cla.bulk;

import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.BulkHome;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.comp.ApplicationException;
import it.cnr.jada.persistency.PersistencyException;
import it.cnr.jada.persistency.PersistentCache;
import it.cnr.jada.persistency.sql.CompoundFindClause;
import it.cnr.jada.persistency.sql.SQLBuilder;

public class Classificazione_voci_ep_eco_liv5Home extends Classificazione_voci_ep_eco_liv4Home {
	protected Classificazione_voci_ep_eco_liv5Home(Class clazz,java.sql.Connection connection) {
		super(clazz,connection);
	}
	protected Classificazione_voci_ep_eco_liv5Home(Class clazz,java.sql.Connection connection,PersistentCache persistentCache) {
		super(clazz,connection,persistentCache);
	}
	public Classificazione_voci_ep_eco_liv5Home(java.sql.Connection conn) {
		super(Classificazione_voci_ep_eco_liv5Bulk.class, conn);
	}
	public Classificazione_voci_ep_eco_liv5Home(java.sql.Connection conn, PersistentCache persistentCache) {
		super(Classificazione_voci_ep_eco_liv5Bulk.class, conn, persistentCache);
	}
	public SQLBuilder selectByClause(UserContext usercontext, CompoundFindClause compoundfindclause)
		throws PersistencyException
	{
		SQLBuilder sql = super.selectEco(usercontext, compoundfindclause);
		sql.addSQLClause("AND","cd_livello5",SQLBuilder.ISNOTNULL, null);
		sql.addSQLClause("AND","cd_livello6",SQLBuilder.ISNULL, null);
		return sql;
	}	
}
