/*
* Created by Generator 1.0
* Date 23/02/2006
*/
package it.cnr.contab.utenze00.bulk;
import it.cnr.contab.config00.sto.bulk.CdrBulk;
import it.cnr.contab.config00.sto.bulk.CdrHome;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk;
import it.cnr.contab.pdg00.bulk.Pdg_variazioneBulk;
import it.cnr.contab.pdg00.cdip.bulk.Ass_pdg_variazione_cdrBulk;
import it.cnr.contab.varstanz00.bulk.Ass_var_stanz_res_cdrBulk;
import it.cnr.contab.varstanz00.bulk.Var_stanz_resBulk;
import it.cnr.jada.bulk.BulkHome;
import it.cnr.jada.persistency.IntrospectionException;
import it.cnr.jada.persistency.PersistencyException;
import it.cnr.jada.persistency.PersistentCache;
import it.cnr.jada.persistency.sql.SQLBuilder;
public class Utente_indirizzi_mailHome extends BulkHome {
	public Utente_indirizzi_mailHome(java.sql.Connection conn) {
		super(Utente_indirizzi_mailBulk.class, conn);
	}
	public Utente_indirizzi_mailHome(java.sql.Connection conn, PersistentCache persistentCache) {
		super(Utente_indirizzi_mailBulk.class, conn, persistentCache);
	}
	public java.util.Collection findUtenteMancataApprovazioneVariazioniBilancioEnte() throws IntrospectionException, PersistencyException 
	{
		SQLBuilder sql = createSQLBuilder();
		sql.addClause("AND","fl_err_appr_var_bil_cnr_res",SQLBuilder.EQUALS,Boolean.TRUE);
		return fetchAll(sql);
	}
	public java.util.Collection findUtenteMancataApprovazioneVariazioniBilancioEnteComp() throws IntrospectionException, PersistencyException 
	{
		SQLBuilder sql = createSQLBuilder();
		sql.addClause("AND","fl_err_appr_var_bil_cnr_comp",SQLBuilder.EQUALS,Boolean.TRUE);
		return fetchAll(sql);
	}
	public java.util.Collection findUtenteApprovaVariazioniBilancio(Var_stanz_resBulk var_stanz_res) throws IntrospectionException, PersistencyException 
	{
		SQLBuilder sql = createSQLBuilder();
		sql.addClause("AND","fl_com_app_var_stanz_res",SQLBuilder.EQUALS,Boolean.TRUE);
			SQLBuilder sqlUtente = getHomeCache().getHome(UtenteBulk.class).createSQLBuilder();
				SQLBuilder sqlAssVar = getHomeCache().getHome(Ass_var_stanz_res_cdrBulk.class).createSQLBuilder();
				sqlAssVar.addClause("AND","esercizio",SQLBuilder.EQUALS,var_stanz_res.getEsercizio());
				sqlAssVar.addClause("AND","pg_variazione",SQLBuilder.EQUALS,var_stanz_res.getPg_variazione());
				sqlAssVar.addSQLJoin("ASS_VAR_STANZ_RES_CDR.CD_CENTRO_RESPONSABILITA","UTENTE.CD_CDR");
			sqlUtente.addSQLExistsClause("AND",sqlAssVar);
			sqlUtente.addSQLJoin("UTENTE.CD_UTENTE","UTENTE_INDIRIZZI_MAIL.CD_UTENTE");				
		sql.addSQLExistsClause("AND",sqlUtente);
		return fetchAll(sql);
	}
	public java.util.Collection findUtenteApprovaVariazioniBilancio(Pdg_variazioneBulk varPdg) throws IntrospectionException, PersistencyException 
	{
		SQLBuilder sql = createSQLBuilder();
		sql.addClause("AND","fl_com_app_var_stanz_comp",SQLBuilder.EQUALS,Boolean.TRUE);
			SQLBuilder sqlUtente = getHomeCache().getHome(UtenteBulk.class).createSQLBuilder();
				SQLBuilder sqlAssVar = getHomeCache().getHome(Ass_pdg_variazione_cdrBulk.class).createSQLBuilder();
				sqlAssVar.addClause("AND","esercizio",SQLBuilder.EQUALS,varPdg.getEsercizio());
				sqlAssVar.addClause("AND","pg_variazione_pdg",SQLBuilder.EQUALS,varPdg.getPg_variazione_pdg());
				sqlAssVar.addSQLJoin("ASS_PDG_VARIAZIONE_CDR.CD_CENTRO_RESPONSABILITA","UTENTE.CD_CDR");
			sqlUtente.addSQLExistsClause("AND",sqlAssVar);
			sqlUtente.addSQLJoin("UTENTE.CD_UTENTE","UTENTE_INDIRIZZI_MAIL.CD_UTENTE");				
		sql.addSQLExistsClause("AND",sqlUtente);
		return fetchAll(sql);
	}
	
	public java.util.Collection findUtenteNotificaRicezioneFatturaElettronica(Unita_organizzativaBulk bulk) throws IntrospectionException, PersistencyException 
	{
		SQLBuilder sql = createSQLBuilder();
		sql.addClause("AND","flFepNotificaRicezione",SQLBuilder.EQUALS,Boolean.TRUE);
			SQLBuilder sqlUtente = getHomeCache().getHome(UtenteBulk.class).createSQLBuilder();
				SQLBuilder sqlCdR = ((CdrHome)getHomeCache().getHome(CdrBulk.class)).createSQLBuilderEsteso();
				sqlCdR.addClause("AND","cd_unita_organizzativa",SQLBuilder.EQUALS, bulk.getCd_unita_organizzativa());
				sqlCdR.addSQLJoin("CDR.CD_CENTRO_RESPONSABILITA","UTENTE.CD_CDR");
			sqlUtente.addSQLExistsClause("AND",sqlCdR);
			sqlUtente.addSQLJoin("UTENTE.CD_UTENTE","UTENTE_INDIRIZZI_MAIL.CD_UTENTE");				
		sql.addSQLExistsClause("AND",sqlUtente);
		return fetchAll(sql);
	}	
}