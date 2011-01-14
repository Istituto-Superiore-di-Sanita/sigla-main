package it.cnr.contab.compensi00.tabrif.bulk;

import java.sql.SQLException;

import it.cnr.contab.compensi00.docs.bulk.*;
import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class V_tipo_trattamento_tipo_coriHome extends BulkHome {
public V_tipo_trattamento_tipo_coriHome(java.sql.Connection conn) {
	super(V_tipo_trattamento_tipo_coriBulk.class,conn);
}
public V_tipo_trattamento_tipo_coriHome(java.sql.Connection conn,PersistentCache persistentCache) {
	super(V_tipo_trattamento_tipo_coriBulk.class,conn,persistentCache);
}
/**
 * Insert the method's description here.
 * Creation date: (24/06/2002 17.31.58)
 * @return java.util.List
 * @param tipoTratt it.cnr.contab.compensi00.tabrif.bulk.Tipo_trattamentoBulk
 */
public java.util.List findTipiCoriPerTipoTrattamento(Tipo_trattamentoBulk tipoTratt, java.sql.Timestamp data) throws PersistencyException {

	SQLBuilder sql = createSQLBuilder();

	if (data==null)
		data = it.cnr.contab.compensi00.docs.bulk.CompensoBulk.getDataOdierna(getServerTimestamp());

	sql.addSQLClause("AND","CD_TRATTAMENTO",sql.EQUALS, tipoTratt.getCd_trattamento());
	sql.addSQLClause("AND","DT_INI_VAL_TIPO_CORI",sql.LESS_EQUALS, data);
	sql.addSQLClause("AND","DT_FIN_VAL_TIPO_CORI",sql.GREATER_EQUALS, data);

	CompoundFindClause clause = CompoundFindClause.or(
		new SimpleFindClause("cd_cori",sql.STARTSWITH,CompensoBulk.CODICE_IRAP),
		new SimpleFindClause("cd_cori",sql.STARTSWITH,CompensoBulk.CODICE_INAIL));
	clause = CompoundFindClause.or(
		clause,
		new SimpleFindClause("cd_cori",sql.STARTSWITH,CompensoBulk.CODICE_IVA));
		
	sql.addClause(clause);

	return fetchAll(sql);
}
public boolean findTipiCoriInpsPerTipoTrattamento(Tipo_trattamentoBulk tipoTratt, java.sql.Timestamp data) throws PersistencyException, SQLException {

	SQLBuilder sql = createSQLBuilder();

	if (data==null)
		data = it.cnr.contab.compensi00.docs.bulk.CompensoBulk.getDataOdierna(getServerTimestamp());

	sql.addSQLClause("AND","CD_TRATTAMENTO",sql.EQUALS, tipoTratt.getCd_trattamento());
	sql.addSQLClause("AND","DT_INI_VAL_TIPO_CORI",sql.LESS_EQUALS, data);
	sql.addSQLClause("AND","DT_FIN_VAL_TIPO_CORI",sql.GREATER_EQUALS, data);
	sql.addSQLClause("AND","FL_GLA",sql.EQUALS, "Y");

    return sql.executeExistsQuery(getConnection());
	
}
public boolean findTipiCoriAliqRidPerTipoTrattamento(Tipo_trattamentoBulk tipoTratt, java.sql.Timestamp data) throws PersistencyException, SQLException {

	SQLBuilder sql = createSQLBuilder();

	if (data==null)
		data = it.cnr.contab.compensi00.docs.bulk.CompensoBulk.getDataOdierna(getServerTimestamp());

	sql.addSQLClause("AND","CD_TRATTAMENTO",sql.EQUALS, tipoTratt.getCd_trattamento());
	sql.addSQLClause("AND","DT_INI_VAL_TIPO_CORI",sql.LESS_EQUALS, data);
	sql.addSQLClause("AND","DT_FIN_VAL_TIPO_CORI",sql.GREATER_EQUALS, data);
	sql.addSQLClause("AND","FL_ALIQUOTA_RIDOTTA",sql.EQUALS, "Y");

	return sql.executeExistsQuery(getConnection());
}
}
