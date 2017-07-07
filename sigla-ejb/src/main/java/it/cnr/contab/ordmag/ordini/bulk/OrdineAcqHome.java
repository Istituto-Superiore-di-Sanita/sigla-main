/*
 * Created by BulkGenerator 2.0 [07/12/2009]
 * Date 28/06/2017
 */
package it.cnr.contab.ordmag.ordini.bulk;
import java.sql.Connection;

import it.cnr.contab.ordmag.anag00.NumerazioneOrdBulk;
import it.cnr.contab.ordmag.anag00.NumerazioneOrdHome;
import it.cnr.contab.ordmag.anag00.TipoOperazioneOrdBulk;
import it.cnr.contab.ordmag.anag00.UnitaOperativaOrdBulk;
import it.cnr.contab.ordmag.anag00.UnitaOperativaOrdHome;
import it.cnr.contab.ordmag.ordini.bulk.OrdineAcqBulk;
import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.BulkHome;
import it.cnr.jada.persistency.PersistencyException;
import it.cnr.jada.persistency.Persistent;
import it.cnr.jada.persistency.PersistentCache;
import it.cnr.jada.persistency.sql.CompoundFindClause;
import it.cnr.jada.persistency.sql.SQLBuilder;
public class OrdineAcqHome extends BulkHome {
	public OrdineAcqHome(Connection conn) {
		super(OrdineAcqBulk.class, conn);
	}
	public OrdineAcqHome(Connection conn, PersistentCache persistentCache) {
		super(OrdineAcqBulk.class, conn, persistentCache);
	}
	public SQLBuilder selectUnitaOperativaOrdByClause(UserContext userContext, OrdineAcqBulk ordine, 
			UnitaOperativaOrdHome unitaOperativaHome, UnitaOperativaOrdBulk unitaOperativaBulk, 
			CompoundFindClause compoundfindclause) throws PersistencyException{
		SQLBuilder sql = unitaOperativaHome.selectByClause(userContext, compoundfindclause);
		sql.addTableToHeader("ABIL_UTENTE_UOP_OPER");
		sql.addSQLJoin("UNITA_OPERATIVA_ORD.CD_UNITA_OPERATIVA", "ABIL_UTENTE_UOP_OPER.CD_UNITA_OPERATIVA");
		if (ordine.getIsForApprovazione()){
			sql.addSQLClause("OR", "ABIL_UTENTE_UOP_OPER.CD_TIPO_OPERAZIONE", SQLBuilder.EQUALS, TipoOperazioneOrdBulk.OPERAZIONE_VALIDAZIONE_ORDINE_1);
		} else {
			sql.addSQLClause("AND", "ABIL_UTENTE_UOP_OPER.CD_TIPO_OPERAZIONE", SQLBuilder.EQUALS, TipoOperazioneOrdBulk.OPERAZIONE_ORDINE);
		}
		sql.addSQLClause("AND", "ABIL_UTENTE_UOP_OPER.CD_UTENTE", SQLBuilder.EQUALS, userContext.getUser());

		return sql;
	}
	
	public void addConditionAbilUtenteUop(UserContext userContext, SQLBuilder sql) {
		sql.addTableToHeader("ABIL_UTENTE_UOP_OPER");
		sql.openParenthesis("AND");
		sql.openParenthesis("AND");
		sql.addSQLClause("AND", "ABIL_UTENTE_UOP_OPER.CD_TIPO_OPERAZIONE", SQLBuilder.EQUALS, TipoOperazioneOrdBulk.OPERAZIONE_ORDINE);
		sql.closeParenthesis();
		sql.openParenthesis("OR");
		sql.addSQLClause("AND", "ABIL_UTENTE_UOP_OPER.CD_TIPO_OPERAZIONE", SQLBuilder.EQUALS, TipoOperazioneOrdBulk.OPERAZIONE_VALIDAZIONE_ORDINE_1);
		sql.openParenthesis("AND");
		sql.addSQLClause("AND", "ORDINE_ACQ.STATO", SQLBuilder.EQUALS, OrdineAcqBulk.STATO_DEFINITIVO);
		sql.addSQLClause("OR", "ORDINE_ACQ.STATO", SQLBuilder.EQUALS, OrdineAcqBulk.STATO_INVIATO_ORDINE);
		sql.closeParenthesis();
		sql.closeParenthesis();
		sql.closeParenthesis();
		sql.addSQLClause("AND", "ABIL_UTENTE_UOP_OPER.CD_UTENTE", SQLBuilder.EQUALS, userContext.getUser());
	}

	public SQLBuilder selectNumerazioneOrdByClause(UserContext userContext, OrdineAcqBulk ordine, 
			NumerazioneOrdHome numerazioneHome, NumerazioneOrdBulk numerazioneBulk, 
			CompoundFindClause compoundfindclause) throws PersistencyException{
		if (ordine == null || ordine.getCdUnitaOperativa() == null){
			throw new PersistencyException("Selezionare prima l'unit� operativa");
		}
		SQLBuilder sql = numerazioneHome.selectByClause(userContext, compoundfindclause);
		sql.addSQLClause("AND", "NUMERAZIONE_ORD.CD_UNITA_OPERATIVA", SQLBuilder.EQUALS, ordine.getCdUnitaOperativa());
		sql.addSQLClause("AND", "NUMERAZIONE_ORD.CD_TIPO_OPERAZIONE", SQLBuilder.EQUALS, TipoOperazioneOrdBulk.OPERAZIONE_ORDINE);
		return sql;
	}

	@Override
	public SQLBuilder selectByClause(UserContext usercontext, CompoundFindClause compoundfindclause)
			throws PersistencyException {
		// TODO Auto-generated method stub
		return super.selectByClause(usercontext, compoundfindclause);
	}
	@Override
	public void delete(Persistent persistent, UserContext userContext) throws PersistencyException {
		((OrdineAcqBulk)persistent).setStato(OrdineAcqBulk.STATO_ANNULLATO);
		 super.update(persistent, userContext);
	}
}