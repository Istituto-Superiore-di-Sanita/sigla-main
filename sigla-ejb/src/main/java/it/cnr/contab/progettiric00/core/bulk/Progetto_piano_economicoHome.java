package it.cnr.contab.progettiric00.core.bulk;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import it.cnr.contab.config00.bulk.Parametri_cnrBulk;
import it.cnr.contab.config00.bulk.Parametri_cnrHome;
import it.cnr.contab.config00.pdcfin.bulk.Elemento_voceBulk;
import it.cnr.contab.config00.pdcfin.bulk.Elemento_voceHome;
import it.cnr.contab.config00.pdcfin.cla.bulk.Classificazione_vociBulk;
import it.cnr.contab.config00.pdcfin.cla.bulk.V_classificazione_vociBulk;
import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.BulkHome;
import it.cnr.jada.bulk.BulkList;
import it.cnr.jada.persistency.PersistencyException;
import it.cnr.jada.persistency.Persistent;
import it.cnr.jada.persistency.PersistentCache;
import it.cnr.jada.persistency.sql.FindClause;
import it.cnr.jada.persistency.sql.SQLBuilder;

public class Progetto_piano_economicoHome extends BulkHome {

	public Progetto_piano_economicoHome(java.sql.Connection conn) {
		super(Progetto_piano_economicoBulk.class,conn);
	}
	
	public Progetto_piano_economicoHome(java.sql.Connection conn,PersistentCache persistentCache) {
		super(Progetto_piano_economicoBulk.class,conn,persistentCache);
	}

	public java.util.Collection findProgettoPianoEconomicoList( Integer pgProgetto ) throws PersistencyException 
	{
		SQLBuilder sql = this.createSQLBuilder();
		sql.addClause(FindClause.AND, "pg_progetto",SQLBuilder.EQUALS,pgProgetto);
		return this.fetchAll(sql);
	}

	public SQLBuilder selectProgettoPianoEconomicoList( java.lang.Integer esercizio, java.lang.Integer pgProgetto, Integer idClassificazione ) throws PersistencyException {
		SQLBuilder sql = this.createSQLBuilder();
        sql.addSQLClause(FindClause.AND, "PROGETTO_PIANO_ECONOMICO.PG_PROGETTO", SQLBuilder.EQUALS, pgProgetto);
		
        Ass_progetto_piaeco_voceHome assHome = (Ass_progetto_piaeco_voceHome)getHomeCache().getHome(Ass_progetto_piaeco_voceBulk.class);
    	SQLBuilder sqlExists = assHome.selectAssProgettoPiaecoVoceList(esercizio, pgProgetto, idClassificazione);
        sqlExists.addSQLJoin("ASS_PROGETTO_PIAECO_VOCE.PG_PROGETTO", "PROGETTO_PIANO_ECONOMICO.PG_PROGETTO");
        sqlExists.addSQLJoin("ASS_PROGETTO_PIAECO_VOCE.CD_UNITA_ORGANIZZATIVA", "PROGETTO_PIANO_ECONOMICO.CD_UNITA_ORGANIZZATIVA");
        sqlExists.addSQLJoin("ASS_PROGETTO_PIAECO_VOCE.CD_VOCE_PIANO", "PROGETTO_PIANO_ECONOMICO.CD_VOCE_PIANO");
        sqlExists.addSQLJoin("ASS_PROGETTO_PIAECO_VOCE.ESERCIZIO_PIANO", "PROGETTO_PIANO_ECONOMICO.ESERCIZIO_PIANO");

        sql.addSQLExistsClause(FindClause.AND, sqlExists);
		return sql;
	}

	public SQLBuilder selectProgettoPianoEconomicoList( java.lang.Integer esercizio, java.lang.Integer pgProgetto, Elemento_voceBulk elementoVoce ) throws PersistencyException {
		SQLBuilder sql = this.createSQLBuilder();
        sql.addSQLClause(FindClause.AND, "PROGETTO_PIANO_ECONOMICO.PG_PROGETTO", SQLBuilder.EQUALS, pgProgetto);
		
        Ass_progetto_piaeco_voceHome assHome = (Ass_progetto_piaeco_voceHome)getHomeCache().getHome(Ass_progetto_piaeco_voceBulk.class);
    	SQLBuilder sqlExists = assHome.createSQLBuilder();
        sqlExists.addSQLJoin("ASS_PROGETTO_PIAECO_VOCE.PG_PROGETTO", "PROGETTO_PIANO_ECONOMICO.PG_PROGETTO");
        sqlExists.addSQLJoin("ASS_PROGETTO_PIAECO_VOCE.CD_UNITA_ORGANIZZATIVA", "PROGETTO_PIANO_ECONOMICO.CD_UNITA_ORGANIZZATIVA");
        sqlExists.addSQLJoin("ASS_PROGETTO_PIAECO_VOCE.CD_VOCE_PIANO", "PROGETTO_PIANO_ECONOMICO.CD_VOCE_PIANO");
        sqlExists.addSQLJoin("ASS_PROGETTO_PIAECO_VOCE.ESERCIZIO_PIANO", "PROGETTO_PIANO_ECONOMICO.ESERCIZIO_PIANO");
    	sqlExists.addSQLClause(FindClause.AND, "ASS_PROGETTO_PIAECO_VOCE.ESERCIZIO_VOCE", SQLBuilder.EQUALS, esercizio);
    	sqlExists.addSQLClause(FindClause.AND, "ASS_PROGETTO_PIAECO_VOCE.PG_PROGETTO", SQLBuilder.EQUALS, pgProgetto);
    	sqlExists.addSQLClause(FindClause.AND, "ASS_PROGETTO_PIAECO_VOCE.ESERCIZIO_VOCE", SQLBuilder.EQUALS, elementoVoce.getEsercizio());
    	sqlExists.addSQLClause(FindClause.AND, "ASS_PROGETTO_PIAECO_VOCE.TI_APPARTENENZA", SQLBuilder.EQUALS, elementoVoce.getTi_appartenenza());
    	sqlExists.addSQLClause(FindClause.AND, "ASS_PROGETTO_PIAECO_VOCE.TI_GESTIONE", SQLBuilder.EQUALS, elementoVoce.getTi_gestione());
    	sqlExists.addSQLClause(FindClause.AND, "ASS_PROGETTO_PIAECO_VOCE.CD_ELEMENTO_VOCE", SQLBuilder.EQUALS, elementoVoce.getCd_elemento_voce());

        sql.addSQLExistsClause(FindClause.AND, sqlExists);
		return sql;
	}
	
	public java.util.Collection<Progetto_piano_economicoBulk> findProgettoPianoEconomicoList( java.lang.Integer esercizio, java.lang.Integer pgProgetto, Integer idClassificazione ) throws PersistencyException {
		SQLBuilder sql = this.selectProgettoPianoEconomicoList(esercizio, pgProgetto, idClassificazione);
		return this.fetchAll(sql);
	}
	
	public java.util.Collection<Progetto_piano_economicoBulk> findProgettoPianoEconomicoList( java.lang.Integer esercizio, java.lang.Integer pgProgetto, Elemento_voceBulk elementoVoce ) throws PersistencyException {
		SQLBuilder sql = this.selectProgettoPianoEconomicoList(esercizio, pgProgetto, elementoVoce);
		return this.fetchAll(sql);
	}
}
