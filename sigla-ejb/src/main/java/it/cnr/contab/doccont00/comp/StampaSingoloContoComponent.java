package it.cnr.contab.doccont00.comp;

import java.math.BigDecimal;
import java.util.Iterator;

import it.cnr.contab.config00.pdcfin.bulk.*;

import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.util.Utility;
import it.cnr.contab.config00.sto.bulk.EnteBulk;
import it.cnr.contab.config00.sto.bulk.Tipo_unita_organizzativaHome;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaHome;
import it.cnr.contab.doccont00.core.bulk.SospesoBulk;
import it.cnr.contab.doccont00.singconto.bulk.*;

import it.cnr.contab.config00.esercizio.bulk.EsercizioBulk;
import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.OggettoBulk;
import it.cnr.jada.comp.*;
import it.cnr.jada.persistency.PersistencyException;
import it.cnr.jada.persistency.sql.*;

public class StampaSingoloContoComponent 
	extends it.cnr.jada.comp.CRUDComponent
	implements IStampaSingoloContoMgr {

	
	/**
	 * FondoEconomaleComponent costruttore standard.
	 */
	public StampaSingoloContoComponent() {
		super();
	}

/**
 * annullaModificaSelezione method comment.
 */
public void annullaModificaSelezione(
	it.cnr.jada.UserContext userContext, 
	it.cnr.contab.doccont00.singconto.bulk.V_voce_f_sing_contoBulk filtro)
	throws ComponentException {

	try {
		rollbackToSavepoint(userContext,"STAMPA_SINGOLO_CONTO");
	} catch(java.sql.SQLException e) {
		throw handleException(e);
	}	
}
/**
 * associaTutti method comment.
 */
public void associaTutti(
	it.cnr.jada.UserContext userContext, 
	it.cnr.contab.doccont00.singconto.bulk.V_voce_f_sing_contoBulk filtro,
	java.math.BigDecimal pg_stampa) 
	throws ComponentException {
	try {
			annullaModificaSelezione(userContext, filtro);
			BigDecimal currentSequence = Utility.ZERO;
			V_voce_f_sing_contoHome home = (V_voce_f_sing_contoHome)getHome(userContext,V_voce_f_sing_contoBulk.class);
			SQLBuilder query = home.createSQLBuilder();
			query.addClause(filtro.getSqlClauses());
			for (Iterator righe = home.fetchAll(query).iterator(); righe.hasNext();){
				V_voce_f_sing_contoBulk voce_f_sing_conto = (V_voce_f_sing_contoBulk)righe.next();
				V_stm_paramin_sing_contoBulk singoloConto = new V_stm_paramin_sing_contoBulk(voce_f_sing_conto);
				singoloConto.setUser(it.cnr.contab.utenze00.bp.CNRUserContext.getUser(userContext));
				singoloConto.setId_report(pg_stampa);
				singoloConto.setChiave(pg_stampa.toString());
				currentSequence = currentSequence.add(new java.math.BigDecimal(1));
				singoloConto.setSequenza(currentSequence);	
				if(voce_f_sing_conto.getFl_partita_giro()!=null && voce_f_sing_conto.getFl_partita_giro().booleanValue()){
					singoloConto.setCd_cds(it.cnr.contab.utenze00.bp.CNRUserContext.getCd_cds(userContext));
				}
				insertBulk(userContext,singoloConto);			 
			}
	} catch (Throwable t) {
		throw handleException(filtro, t);
	}
}
/**
 * getPgStampa method comment.
 */
public java.math.BigDecimal getPgStampa(it.cnr.jada.UserContext userContext) throws it.cnr.jada.comp.ComponentException {
	
    //ricavo il progressivo unico pg_stampa
    return getSequence(userContext);
}
//^^@@
/** 
  *  Identificativo univoco progressivo per la gestione dell' IVA
  *    PreCondition:
  *      Viene richiesta un progressivo
  *    PostCondition:
  *      ritorna un valore 
  *    PreCondition:
  *      Si è verificato un errore.
  *    PostCondition:
  *      Viene inviato un messaggio con il relativo errore ritornato dal DB
 */
//^^@@
private java.math.BigDecimal getSequence(it.cnr.jada.UserContext userContext) throws it.cnr.jada.comp.ComponentException {

	//ricavo il progressivo unico pg_stampa
    java.math.BigDecimal pg_Stampa= new java.math.BigDecimal(0);
    try {
    	LoggableStatement ps= new LoggableStatement(getConnection(userContext),
    			"select IBMSEQ00_STAMPA.nextval from dual",true,this.getClass());
        try {
            java.sql.ResultSet rs= ps.executeQuery();
            try {
                if (rs.next())
                    pg_Stampa= rs.getBigDecimal(1);
            } finally {
                try{rs.close();}catch( java.sql.SQLException e ){};
            }
        } catch (java.sql.SQLException e) {
            throw handleException(e);
        } finally {
            try{ps.close();}catch( java.sql.SQLException e ){};
        }
    } catch (java.sql.SQLException e) {
        throw handleException(e);
    }
    return pg_Stampa;

}
public OggettoBulk inizializzaBulkPerRicerca(UserContext userContext,OggettoBulk bulk) throws ComponentException {

	try {
		Integer esercizioScrivania = ((CNRUserContext)userContext).getEsercizio();
		String cdsScrivania = ((CNRUserContext)userContext).getCd_cds();
		verificaStatoEsercizio(userContext, esercizioScrivania, cdsScrivania);

		V_voce_f_sing_contoBulk voce = (V_voce_f_sing_contoBulk)super.inizializzaBulkPerRicerca(userContext, bulk);

		EnteBulk ente = (EnteBulk) getHome( userContext, EnteBulk.class).findAll().get(0);
		voce.setCd_cds_ente( ente.getCd_unita_organizzativa());
		voce.setCd_cds_scrivania(cdsScrivania);
		voce.setEsercizio(esercizioScrivania);
		voce.setFl_ente(Boolean.FALSE);
		voce.setTi_gestione(it.cnr.contab.doccont00.core.bulk.SospesoBulk.TIPO_SPESA);
		
		return bulk;
	} catch(Throwable e) {
		throw handleException(e);
	}
}
/**
 * inizializzaSelezionePerModifica method comment.
 */
public void inizializzaSelezionePerModifica(
	it.cnr.jada.UserContext userContext, 
	it.cnr.contab.doccont00.singconto.bulk.V_voce_f_sing_contoBulk filtro) 
	throws it.cnr.jada.comp.ComponentException {

	try {
		setSavepoint(userContext,"STAMPA_SINGOLO_CONTO");
	} catch(java.sql.SQLException e) {
		throw handleException(e);
	}
}
/**
 * modificaSelezione method comment.
 */
public java.math.BigDecimal modificaSelezione(
		it.cnr.jada.UserContext userContext, 
		it.cnr.contab.doccont00.singconto.bulk.V_voce_f_sing_contoBulk filtro, 
		it.cnr.jada.bulk.OggettoBulk[] bulks, 
		java.util.BitSet oldSelection, 
		java.util.BitSet newSelection,
		java.math.BigDecimal pg_stampa,
		java.math.BigDecimal currentSequence) 
		throws it.cnr.jada.comp.ComponentException {

	try {
		if (pg_stampa == null || currentSequence == null)
			throw new ApplicationException("Impossibile ottenere un id report!");
			
		//lockBulk(userContext,tipo_la);
		for (int i = 0;i < bulks.length;i++) {
			V_voce_f_sing_contoBulk voceF = (V_voce_f_sing_contoBulk)bulks[i];
			V_stm_paramin_sing_contoBulk singoloConto = new V_stm_paramin_sing_contoBulk(voceF);
			singoloConto.setUser(it.cnr.contab.utenze00.bp.CNRUserContext.getUser(userContext));
			singoloConto.setId_report(pg_stampa);
			singoloConto.setChiave(pg_stampa.toString());
			if(voceF.getFl_partita_giro()!=null && voceF.getFl_partita_giro().booleanValue()){
				singoloConto.setCd_cds(it.cnr.contab.utenze00.bp.CNRUserContext.getCd_cds(userContext));
			}
			if (oldSelection.get(i) != newSelection.get(i)) {
				if (newSelection.get(i)) {
					singoloConto.setSequenza(currentSequence);
					currentSequence = currentSequence.add(new java.math.BigDecimal(1));
					insertBulk(userContext,singoloConto);
				} else {
					deleteBulk(userContext,singoloConto);
				}
			}
		}
		return currentSequence;
	} catch(it.cnr.jada.persistency.PersistencyException e) {
		throw handleException(e);
	}	
}
public it.cnr.jada.persistency.sql.SQLBuilder selectElemento_voceByClause(
	UserContext userContext,
	V_voce_f_sing_contoBulk filtro, 
	Elemento_voceBulk elementoVoce, 
	CompoundFindClause clauses) 
	throws ComponentException {

	Elemento_voceHome elementoVoceHome = (Elemento_voceHome)getHome(userContext, elementoVoce);
	it.cnr.jada.persistency.sql.SQLBuilder sql = elementoVoceHome.createSQLBuilder();
	sql.addClause("AND", "esercizio", sql.EQUALS, ((CNRUserContext)userContext).getEsercizio());
	if(filtro.getTi_gestione().compareTo(SospesoBulk.TIPO_ENTRATA)==0){
		sql.addClause("AND", "ti_gestione", sql.EQUALS,SospesoBulk.TIPO_ENTRATA);
				sql.addClause("AND", "ti_elemento_voce", sql.EQUALS,"C");
				if(!filtro.isEnteInScrivania()&& filtro.getFl_partita_giro())
					sql.addClause("AND", "ti_appartenenza", sql.EQUALS,"D");
				else
					sql.addClause("AND", "ti_appartenenza", sql.EQUALS,"C");	
	}
	else{
		sql.addClause("AND", "ti_gestione", sql.EQUALS,SospesoBulk.TIPO_SPESA);
		sql.addClause("AND", "ti_appartenenza", sql.EQUALS,"D");
		sql.addClause("AND", "ti_elemento_voce", sql.EQUALS,"C");
	}
	sql.addClause("AND","fl_partita_giro",sql.EQUALS,filtro.getFl_partita_giro());
	sql.addClause(clauses);
	return sql;
}
public it.cnr.jada.persistency.sql.SQLBuilder selectVoce_fByClause(
	UserContext userContext,
	V_voce_f_sing_contoBulk filtro, 
	Voce_fBulk voce_f, 
	CompoundFindClause clauses) 
	throws ComponentException {

	Voce_fHome voceFHome = (Voce_fHome)getHome(userContext, voce_f);
	it.cnr.jada.persistency.sql.SQLBuilder sql = voceFHome.createSQLBuilder();
	sql.addClause("AND", "esercizio", sql.EQUALS, ((CNRUserContext)userContext).getEsercizio());
	if((!filtro.getFl_partita_giro()||filtro.getFl_ente()) && !filtro.isEnteInScrivania()){
		sql.addClause("AND", "cd_cds",sql.EQUALS,((CNRUserContext)userContext).getCd_cds());
		String uo=(((CNRUserContext)userContext).getCd_unita_organizzativa());
		try {
			Unita_organizzativaBulk bulk = (Unita_organizzativaBulk) getHome(userContext, Unita_organizzativaBulk.class).findByPrimaryKey(new Unita_organizzativaBulk(uo));
		    if(!bulk.getFl_uo_cds().booleanValue())
		    	sql.addClause("AND", "cd_unita_organizzativa",sql.EQUALS,((CNRUserContext)userContext).getCd_unita_organizzativa());
		} catch (PersistencyException e) {
			handleException(e);
		}
	}
	if(!filtro.getFl_partita_giro()&& filtro.isEnteInScrivania())
		sql.addClause("AND", "cd_cds",sql.EQUALS,filtro.getCd_cds_proprio());
	sql.addClause("AND", "cd_elemento_voce",sql.EQUALS,filtro.getCd_elemento_voce());
	if(filtro.getTi_gestione().compareTo(SospesoBulk.TIPO_ENTRATA)==0){
		sql.addClause("AND", "ti_gestione", sql.EQUALS,SospesoBulk.TIPO_ENTRATA);
		sql.addClause("AND", "ti_appartenenza", sql.EQUALS,"C");
		if(filtro.getUnita_organizzativa()!=null)
			sql.addClause("AND", "cd_unita_organizzativa", sql.EQUALS,filtro.getUnita_organizzativa().getCd_unita_organizzativa());
	}
	else{	
		sql.addClause("AND", "ti_gestione", sql.EQUALS,SospesoBulk.TIPO_SPESA);
		if(filtro.getFl_ente().booleanValue() ||(filtro.getCd_proprio_voce()!=null)||filtro.isEnteInScrivania()){
			sql.addClause("AND", "cd_proprio_voce", sql.EQUALS,filtro.getCd_proprio_voce());
			sql.addClause("AND", "ti_appartenenza", sql.EQUALS,"C");
		}
		else{
				sql.addClause("AND", "ti_appartenenza", sql.EQUALS,"D");
		}
		
	}
	sql.addClause("AND", "fl_mastrino", sql.EQUALS,true);
	sql.addClause(clauses);
	return sql;
}
/**
 * Verifica dello stato dell'esercizio 
 *
 * @param userContext <code>UserContext</code>
 * @param es <code>Integer</code>  l'esercizio da verificare
 * @param cd_cds <code>String</code>  il cds da verificare 
 *
 * @return FALSE se per il cds interessato non è stato inserito nessun esercizio o se l'esercizio non è in stato di "aperto"
 *		     TRUE in tutti gli altri casi
 *
 */

void verificaStatoEsercizio( UserContext userContext, Integer es, String cd_cds ) throws ComponentException, it.cnr.jada.persistency.PersistencyException
{
	EsercizioBulk esercizio = (EsercizioBulk) getHome(userContext, EsercizioBulk.class).findByPrimaryKey( 
																									new EsercizioBulk( cd_cds, es ));
	if (esercizio == null )
			throw handleException( new ApplicationException( "Funzione non abilitata: esercizio inesistente!") );
	/*if ( !esercizio.STATO_APERTO.equals(esercizio.getSt_apertura_chiusura()))
			throw handleException( new ApplicationException( "Funzione non abilitata: esercizio non aperto!") );*/
}			
			
public it.cnr.jada.persistency.sql.SQLBuilder selectArea_ricercaByClause(
		UserContext userContext,
		V_voce_f_sing_contoBulk filtro, 
		Unita_organizzativaBulk uo, 
		CompoundFindClause clauses) 
		throws ComponentException {

		Unita_organizzativaHome uoHome = (Unita_organizzativaHome)getHome(userContext, uo);
		it.cnr.jada.persistency.sql.SQLBuilder sql = uoHome.createSQLBuilderArea();
		sql.addClause(clauses);
		return sql;
	}

}
