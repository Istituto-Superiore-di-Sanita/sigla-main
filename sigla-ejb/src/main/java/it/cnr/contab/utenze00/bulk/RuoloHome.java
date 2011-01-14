package it.cnr.contab.utenze00.bulk;

import it.cnr.jada.bulk.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class RuoloHome extends BulkHome {
/**
 * <!-- @TODO: da completare -->
 * Costruisce un RuoloHome
 *
 * @param conn	La java.sql.Connection su cui vengono effettuate le operazione di persistenza
 */
public RuoloHome(java.sql.Connection conn) {
	super(RuoloBulk.class,conn);
}
/**
 * <!-- @TODO: da completare -->
 * Costruisce un RuoloHome
 *
 * @param conn	La java.sql.Connection su cui vengono effettuate le operazione di persistenza
 * @param persistentCache	La PersistentCache in cui vengono cachati gli oggetti persistenti caricati da questo Home
 */
public RuoloHome(java.sql.Connection conn,PersistentCache persistentCache) {
	super(RuoloBulk.class,conn,persistentCache);
}
/**
 * Recupera la lista di accessi (AccessoBulk) di tipo pubblico ordinandoli per codice
 * @param gestore parametro non usato
 * @return List lista di AccessoBulk
 */

public java.util.List findAccessi_disponibili( UtenteBulk gestore ) throws IntrospectionException, PersistencyException 
{
	PersistentHome accessoHome = getHomeCache().getHome( AccessoBulk.class);
	SQLBuilder sql = accessoHome.createSQLBuilder();
	if ( !"*".equals( gestore.getCd_cds_configuratore() ))
		sql.addSQLClause("AND","TI_ACCESSO",SQLBuilder.EQUALS,AccessoBulk.TIPO_PUBBLICO);
	else{
		sql.openParenthesis("AND");
		sql.addSQLClause("AND","TI_ACCESSO",SQLBuilder.EQUALS,AccessoBulk.TIPO_PUBBLICO);
		sql.addSQLClause("OR","TI_ACCESSO",SQLBuilder.EQUALS,AccessoBulk.TIPO_RISERVATO_CNR);
		sql.closeParenthesis();
	}
	//sql.addSQLClause("AND","TI_ACCESSO",sql.NOT_EQUALS,AccessoBulk.TIPO_RISERVATO_CNR);	
	//sql.addSQLClause("AND","TI_ACCESSO",sql.NOT_EQUALS,AccessoBulk.TIPO_SUPERUTENTE);
	//sql.addSQLClause("AND","TI_ACCESSO",sql.NOT_EQUALS,AccessoBulk.TIPO_AMMIN_UTENZE);							
	sql.addOrderBy( "CD_ACCESSO");
	return accessoHome.fetchAll(sql);

}
/**
 * Recupera la lista di associazioni ruolo-accesso (Ruolo_accessoBulk) definite per un ruolo 
 * @param ruolo RuoloBulk per cui cercare le associazioni
 * @return List lista di Ruolo_accessoBulk
 */

public java.util.Collection findRuolo_accessi(RuoloBulk ruolo) throws IntrospectionException, PersistencyException 
{
	PersistentHome raHome = getHomeCache().getHome( Ruolo_accessoBulk.class);
	SQLBuilder sql = raHome.createSQLBuilder();
	sql.addSQLClause("AND","CD_RUOLO",sql.EQUALS,ruolo.getCd_ruolo());
	return raHome.fetchAll(sql);
}
}
