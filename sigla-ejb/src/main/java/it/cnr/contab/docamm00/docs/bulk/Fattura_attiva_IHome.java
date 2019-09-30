/*
 * Copyright (C) 2019  Consiglio Nazionale delle Ricerche
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package it.cnr.contab.docamm00.docs.bulk;

import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.jada.UserContext;
import it.cnr.jada.persistency.PersistencyException;
import it.cnr.jada.persistency.sql.CompoundFindClause;
import it.cnr.jada.persistency.sql.SQLBuilder;

/**
 * Insert the type's description here.
 * Creation date: (10/24/2001 2:31:46 PM)
 * @author: Roberto Peli
 */
public class Fattura_attiva_IHome extends Fattura_attivaHome {
/**
 * Fattura_passiva_IHome constructor comment.
 * @param conn java.sql.Connection
 */
public Fattura_attiva_IHome(java.sql.Connection conn) {
	super(Fattura_attiva_IBulk.class, conn);
}
/**
 * Fattura_passiva_IHome constructor comment.
 * @param conn java.sql.Connection
 * @param persistentCache it.cnr.jada.persistency.PersistentCache
 */
public Fattura_attiva_IHome(java.sql.Connection conn, it.cnr.jada.persistency.PersistentCache persistentCache) {
	super(Fattura_attiva_IBulk.class, conn, persistentCache);
}
/**
 * Ritorna un SQLBuilder con la columnMap del ricevente
 */
public SQLBuilder selectByClauseForFatturazioneElettronica(UserContext usercontext, Fattura_attiva_IBulk fattura, CompoundFindClause compoundfindclause) throws PersistencyException {
    SQLBuilder sqlBuilder = super.createSQLBuilder();
    if(compoundfindclause == null){
        if(fattura != null)
            compoundfindclause = fattura.buildFindClauses(null);
    } else {
        compoundfindclause = CompoundFindClause.and(compoundfindclause, fattura.buildFindClauses(Boolean.FALSE));
    }
    sqlBuilder.addClause(compoundfindclause);

    CompoundFindClause clauses = new CompoundFindClause();
    clauses.addClause("AND", "esercizio", SQLBuilder.EQUALS, CNRUserContext.getEsercizio(usercontext));
    clauses.addClause("AND", "cd_cds_origine", SQLBuilder.EQUALS, CNRUserContext.getCd_cds(usercontext));
    clauses.addClause("AND", "cd_uo_origine", SQLBuilder.EQUALS, CNRUserContext.getCd_unita_organizzativa(usercontext));

    clauses.addClause("AND", "flFatturaElettronica", SQLBuilder.EQUALS, Boolean.TRUE);
    if (fattura.getStatoFattElett().equals(Fattura_attiva_IBulk.DA_PREDISPORRE_ALLA_FIRMA)) {
    	clauses.addClause("AND", "statoInvioSdi", SQLBuilder.EQUALS, Fattura_attivaBulk.FATT_ELETT_ALLA_FIRMA);
    } else if (fattura.getStatoFattElett().equals(Fattura_attiva_IBulk.DA_FIRMARE)) {
    	clauses.addClause("AND", "statoInvioSdi", SQLBuilder.EQUALS, Fattura_attivaBulk.FATT_ELETT_PREDISPOSTA_FIRMA);
    } else {
    	CompoundFindClause clausesFir = new CompoundFindClause();
    	clausesFir.addClause("OR", "statoInvioSdi", SQLBuilder.EQUALS, Fattura_attivaBulk.FATT_ELETT_ALLA_FIRMA);
    	CompoundFindClause clausesPre = new CompoundFindClause();
    	clausesPre.addClause("OR", "statoInvioSdi", SQLBuilder.EQUALS, Fattura_attivaBulk.FATT_ELETT_PREDISPOSTA_FIRMA);
    	clauses.addChild(CompoundFindClause.or(clausesFir, clausesPre));
    }
    clauses.addClause("AND", "stato_cofi", SQLBuilder.NOT_EQUALS, it.cnr.contab.docamm00.docs.bulk.Fattura_attivaBulk.STATO_ANNULLATO);

    sqlBuilder.addClause(clauses);
    sqlBuilder.addOrderBy("esercizio");
    sqlBuilder.addOrderBy("pg_Fattura_Attiva");

    return sqlBuilder;
}
public SQLBuilder createSQLBuilder() {

	SQLBuilder sql = super.createSQLBuilder();
	sql.addSQLClause("AND", "FATTURA_ATTIVA.TI_FATTURA", sql.EQUALS, Fattura_attiva_IBulk.TIPO_FATTURA_ATTIVA);
	return sql;
}
}
