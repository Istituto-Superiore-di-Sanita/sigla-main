package it.cnr.contab.anagraf00.tabter.bulk;

import java.sql.PreparedStatement;

import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.sql.*;


public class ComuneHome extends BulkHome {
	protected ComuneHome(Class bulkClass,java.sql.Connection conn) {
		super(bulkClass,conn);
	}
	protected ComuneHome(Class bulkClass,java.sql.Connection conn,PersistentCache persistentCache) {
		super(bulkClass,conn,persistentCache);
	}
	public ComuneHome(java.sql.Connection conn) {
		super(ComuneBulk.class,conn);
	}
	public ComuneHome(java.sql.Connection conn,PersistentCache persistentCache) {
		super(ComuneBulk.class,conn,persistentCache);
	}
	/**
	 * Restituisce tutti i cap di un comune.
	 *
	 * @param comune Il comune di cui bisogna recuperare i cap.
	 *
	 * @return java.util.Collection
	 *
	 * @exeption IntrospectionException
	 * @exeption PersistencyException
	 */

	public java.util.Collection findCaps(ComuneBulk comune) throws IntrospectionException, PersistencyException {
		if(comune != null  && !comune.getPg_comune().equals("") )
			try {
				comune = (ComuneBulk)findByPrimaryKey(comune);
				SQLBuilder sql = getHomeCache().getHome(CapBulk.class).createSQLBuilder();
				sql.addClause("AND","pg_comune",sql.EQUALS,comune.getPg_comune());
				LoggableStatement stm = sql.prepareStatement(getConnection());
				try {
					java.sql.ResultSet rs = stm.executeQuery();
					java.util.Collection coll = new java.util.Vector();
					boolean flag = true;
					while (rs.next()) {
						String cap = rs.getString("CD_CAP");
						coll.add(cap);
						flag = flag && !cap.equals(comune.getCd_cap());
					}
					if (flag && comune.getCd_cap() != null)
						coll.add(comune.getCd_cap());
					return coll;
				} finally {
					try{stm.close();}catch( java.sql.SQLException e ){};
				}
			} catch(java.sql.SQLException e) {
				throw new PersistencyException(e);
			}
		return null;
	}

public Long findNuovoProgressivo(ComuneBulk comune) throws BusyResourceException, PersistencyException, ValidationException{

	Long maxValue = (Long)findAndLockMax(comune, "pg_comune", new Long(0));
	Long x = new Long(maxValue.longValue()+1);

	if (x.longValue() < comune.PG_PRIMO_COMUNE_ESTERO)
			x = new Long(comune.PG_PRIMO_COMUNE_ESTERO);

	return x;
}
	public String getCatastale(Long pgComune) throws IntrospectionException, PersistencyException {
		ComuneBulk comune = new ComuneBulk(pgComune);
		comune = (ComuneBulk)findByPrimaryKey(comune);
		return comune.getCd_catastale();
	}
	public ComuneBulk findComune(UserContext userContext,String cd_catastale) throws PersistencyException{
		ComuneBulk comune = new ComuneBulk();
		SQLBuilder sql = createSQLBuilder();
		sql.openParenthesis("AND");
		sql.addSQLClause("AND", "DT_CANC", sql.ISNULL, null);
		sql.addSQLClause("OR","DT_CANC",sql.GREATER,it.cnr.jada.util.ejb.EJBCommonServices.getServerDate());
		sql.closeParenthesis();
		sql.addSQLClause("AND","CD_CATASTALE",sql.EQUALS, cd_catastale);
		Broker broker = createBroker(sql);
		if (broker.next()){
			comune = (ComuneBulk)fetch(broker);
			getHomeCache().fetchAll(userContext);
		}
		broker.close();
		return comune;
	}
@Override
public SQLBuilder selectByClause(CompoundFindClause compoundfindclause) throws PersistencyException {
	SQLBuilder sql;
	if((this instanceof ComuneItalianoHome)||(this instanceof ComuneEsteroHome))
		sql = super.selectByClause(compoundfindclause);
	else{
		sql = super.selectByClause(compoundfindclause);
		sql.openParenthesis("AND");
		sql.addSQLClause("AND", "DT_CANC", sql.ISNULL, null);
		sql.addSQLClause("OR","DT_CANC",sql.GREATER,it.cnr.jada.util.ejb.EJBCommonServices.getServerDate());
		sql.closeParenthesis();
}
	return sql;
}
}
