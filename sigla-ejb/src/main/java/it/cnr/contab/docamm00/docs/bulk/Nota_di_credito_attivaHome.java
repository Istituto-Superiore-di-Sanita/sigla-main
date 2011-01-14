package it.cnr.contab.docamm00.docs.bulk;

import it.cnr.jada.persistency.sql.SQLBuilder;

/**
 * Insert the type's description here.
 * Creation date: (10/24/2001 2:32:18 PM)
 * @author: Roberto Peli
 */
public class Nota_di_credito_attivaHome extends Fattura_attivaHome {
/**
 * Nota_di_creditoHome constructor comment.
 * @param conn java.sql.Connection
 */
public Nota_di_credito_attivaHome(java.sql.Connection conn) {
	
	super(Nota_di_credito_attivaBulk.class,conn);
}
/**
 * Nota_di_creditoHome constructor comment.
 * @param conn java.sql.Connection
 * @param persistentCache it.cnr.jada.persistency.PersistentCache
 */
public Nota_di_credito_attivaHome(java.sql.Connection conn, it.cnr.jada.persistency.PersistentCache persistentCache) {
	super(Nota_di_credito_attivaBulk.class, conn, persistentCache);
}
/**
 * Ritorna un SQLBuilder con la columnMap del ricevente
 */
public SQLBuilder createSQLBuilder() {

	SQLBuilder sql = super.createSQLBuilder();
	sql.addSQLClause("AND", "FATTURA_ATTIVA.TI_FATTURA", sql.EQUALS, Nota_di_credito_attivaBulk.TIPO_NOTA_DI_CREDITO);
	return sql;
}
public SQLBuilder selectFor(Fattura_attiva_IBulk fatturaAttiva) {

	SQLBuilder sql = createSQLBuilder();
	sql.setDistinctClause(true);

	sql.addTableToHeader("FATTURA_ATTIVA_RIGA");

	sql.addSQLJoin("FATTURA_ATTIVA.ESERCIZIO","FATTURA_ATTIVA_RIGA.ESERCIZIO");
	sql.addSQLJoin("FATTURA_ATTIVA.CD_CDS","FATTURA_ATTIVA_RIGA.CD_CDS");
	sql.addSQLJoin("FATTURA_ATTIVA.CD_UNITA_ORGANIZZATIVA","FATTURA_ATTIVA_RIGA.CD_UNITA_ORGANIZZATIVA");
	sql.addSQLJoin("FATTURA_ATTIVA.PG_FATTURA_ATTIVA","FATTURA_ATTIVA_RIGA.PG_FATTURA_ATTIVA");
	
	sql.addSQLClause("AND", "FATTURA_ATTIVA_RIGA.CD_CDS_ASSNCNA_FIN", sql.EQUALS, fatturaAttiva.getCd_cds());
	sql.addSQLClause("AND", "FATTURA_ATTIVA_RIGA.CD_UO_ASSNCNA_FIN", sql.EQUALS, fatturaAttiva.getCd_unita_organizzativa());
	sql.addSQLClause("AND", "FATTURA_ATTIVA_RIGA.ESERCIZIO_ASSNCNA_FIN", sql.EQUALS, fatturaAttiva.getEsercizio());
	sql.addSQLClause("AND", "FATTURA_ATTIVA_RIGA.PG_FATTURA_ASSNCNA_FIN", sql.EQUALS, fatturaAttiva.getPg_fattura_attiva());

	return sql;
}
}
