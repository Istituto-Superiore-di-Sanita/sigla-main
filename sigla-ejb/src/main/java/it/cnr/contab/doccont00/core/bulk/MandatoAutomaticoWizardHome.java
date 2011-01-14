package it.cnr.contab.doccont00.core.bulk;

import it.cnr.contab.anagraf00.core.bulk.V_anagrafico_terzoBulk;
import it.cnr.contab.docamm00.docs.bulk.Documento_genericoBulk;
import it.cnr.contab.docamm00.docs.bulk.Numerazione_doc_ammBulk;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.sql.*;

import java.util.*;

/**
 * <!-- @TODO: da completare -->
 */

public class MandatoAutomaticoWizardHome extends MandatoIHome {
	public MandatoAutomaticoWizardHome(Class clazz, java.sql.Connection conn) {
		super(clazz,conn);	
	
	}

	public MandatoAutomaticoWizardHome(Class clazz, java.sql.Connection conn, it.cnr.jada.persistency.PersistentCache persistentCache) {
		super(clazz,conn, persistentCache);	
	
	}

	public MandatoAutomaticoWizardHome(java.sql.Connection conn) {
		super(MandatoAutomaticoWizardBulk.class,conn);
	}

	public MandatoAutomaticoWizardHome(java.sql.Connection conn, it.cnr.jada.persistency.PersistentCache persistentCache) {
		super(MandatoAutomaticoWizardBulk.class,conn, persistentCache);	
	}
	
	/**
	 * <!-- @TODO: da completare -->
	 * 
	 *
	 * @param mandato	
	 * @return 
	 * @throws IntrospectionException	
	 * @throws PersistencyException	
	 */
	public Collection findImpegni( MandatoAutomaticoWizardBulk mandato ) throws IntrospectionException, PersistencyException 
	{
		return getHomeCache().getHome(V_obbligazioneBulk.class ).fetchAll( selectImpegno( mandato ));
	}

	/**
	 * <!-- @TODO: da completare -->
	 * 
	 *
	 * @param mandato	
	 * @return 
	 * @throws IntrospectionException	
	 * @throws PersistencyException	
	 */
	public Collection findTerzi( MandatoAutomaticoWizardBulk mandato ) throws IntrospectionException, PersistencyException 
	{
		return getHomeCache().getHome(V_anagrafico_terzoBulk.class ).fetchAll( selectTerzo( mandato ));
	}

	public Collection findDocPassivi( MandatoIBulk mandato, it.cnr.contab.utenze00.bp.CNRUserContext context ) throws IntrospectionException, PersistencyException 
	{
		return getHomeCache().getHome(V_doc_passivo_obbligazione_wizardBulk.class ).fetchAll( selectDocPassivi( mandato ));
	}

	/**
	 * <!-- @TODO: da completare -->
	 * 
	 *
	 * @param mandato	
	 * @return 
	 * @throws IntrospectionException	
	 * @throws PersistencyException	
	 */
	public SQLBuilder selectImpegno( MandatoAutomaticoWizardBulk mandato ) throws IntrospectionException, PersistencyException 
	{
		PersistentHome home = getHomeCache().getHome( V_obbligazioneBulk.class );
		SQLBuilder sql = home.createSQLBuilder();
		sql.addClause( "AND", "esercizio", sql.EQUALS, mandato.getEsercizio());
		sql.addClause( "AND", "cd_cds", sql.EQUALS, mandato.getCd_cds());
		sql.addClause( "AND", "cd_terzo", sql.EQUALS, mandato.getMandato_terzo().getCd_terzo());
 		if (mandato.getTi_impegni().equals(MandatoAutomaticoWizardBulk.IMPEGNI_TIPO_COMPETENZA)) {
			sql.openParenthesis("AND");
			sql.addClause( "OR", "cd_tipo_documento_cont", sql.EQUALS, Numerazione_doc_contBulk.TIPO_IMP);
			sql.addClause( "OR", "cd_tipo_documento_cont", sql.EQUALS, Numerazione_doc_contBulk.TIPO_OBB);
			sql.closeParenthesis();
		} else if (mandato.getTi_impegni().equals(MandatoAutomaticoWizardBulk.IMPEGNI_TIPO_RESIDUO)) {
			sql.openParenthesis("AND");
			sql.addClause( "OR", "cd_tipo_documento_cont", sql.EQUALS, Numerazione_doc_contBulk.TIPO_IMP_RES);
			sql.addClause( "OR", "cd_tipo_documento_cont", sql.EQUALS, Numerazione_doc_contBulk.TIPO_OBB_RES);
			sql.addClause( "OR", "cd_tipo_documento_cont", sql.EQUALS, Numerazione_doc_contBulk.TIPO_OBB_RES_IMPROPRIA);
			sql.closeParenthesis();
		}
		sql.addSQLClause( "AND", "IM_SCADENZA-IM_ASSOCIATO_DOC_AMM", sql.GREATER, new java.math.BigDecimal(0));

		return sql;
	}

	public SQLBuilder selectTerzo( MandatoIBulk mandato ) throws IntrospectionException, PersistencyException 
	{
		PersistentHome home = getHomeCache().getHome( V_anagrafico_terzoBulk.class );
		SQLBuilder sql = home.createSQLBuilder();
		sql.addClause( "AND", "cd_terzo", sql.EQUALS, mandato.getFind_doc_passivi().getCd_terzo());
		sql.addClause( "AND", "cd_precedente", sql.EQUALS, mandato.getFind_doc_passivi().getCd_precedente());
		sql.addClause( "AND", "cognome", sql.EQUALS, mandato.getFind_doc_passivi().getCognome());
		sql.addClause( "AND", "ragione_sociale", sql.EQUALS, mandato.getFind_doc_passivi().getRagione_sociale());
		sql.addClause( "AND", "nome", sql.EQUALS, mandato.getFind_doc_passivi().getNome());
		sql.addClause( "AND", "partita_iva", sql.EQUALS, mandato.getFind_doc_passivi().getPartita_iva());
		sql.addClause( "AND", "codice_fiscale", sql.EQUALS, mandato.getFind_doc_passivi().getCodice_fiscale());

		return sql;
	}
	/**
	 * Metodo per cercare i documenti passivi associati al mandato.
	 *
	 * @param mandato <code>MandatoIBulk</code> il mandato
	 * @param context <code>CNRUserContext</code>
	 *
	 * @return <code>Collection</code> i documenti passivi associati al mandato
	 *
	 */
	public SQLBuilder selectDocPassivi( MandatoIBulk mandato ) throws IntrospectionException, PersistencyException 
	{
		PersistentHome home = getHomeCache().getHome( V_doc_passivo_obbligazione_wizardBulk.class );
		SQLBuilder sql = home.createSQLBuilder();
		sql.addSQLClause( "AND", "esercizio_obbligazione", sql.EQUALS, mandato.getEsercizio());			
		sql.addClause( "AND", "cd_terzo", sql.EQUALS, mandato.getFind_doc_passivi().getCd_terzo());	
		sql.addClause( "AND", "nr_fattura_fornitore", sql.EQUALS, mandato.getFind_doc_passivi().getNr_fattura_fornitore());
		sql.addClause( "AND", "pg_documento_amm", sql.EQUALS, mandato.getFind_doc_passivi().getPg_documento_amm());
		sql.addClause( "AND", "cd_tipo_documento_amm", sql.EQUALS, mandato.getFind_doc_passivi().getCd_tipo_documento_amm());
//		sql.addClause( "AND", "cd_tipo_documento_amm", sql.NOT_EQUALS, Numerazione_doc_ammBulk.TIPO_COMPENSO);
		sql.addClause( "AND", "esercizio_ori_obbligazione", sql.EQUALS, mandato.getFind_doc_passivi().getEsercizio_ori_obbligazione());			
		sql.addClause( "AND", "pg_obbligazione", sql.EQUALS, mandato.getFind_doc_passivi().getPg_obbligazione());
		sql.addClause( "AND", "dt_scadenza", sql.EQUALS, mandato.getFind_doc_passivi().getDt_scadenza());
		sql.addClause( "AND", "im_scadenza", sql.EQUALS, mandato.getFind_doc_passivi().getIm_scadenza());
		sql.addClause( "AND", "ti_pagamento", sql.EQUALS, mandato.getFind_doc_passivi().getTi_pagamento());			
		sql.addClause( "AND", "cd_cds_obbligazione", sql.EQUALS, mandato.getCd_cds());

 		if (((MandatoAutomaticoWizardBulk)mandato).getTi_impegni().equals(MandatoAutomaticoWizardBulk.IMPEGNI_TIPO_COMPETENZA)) {
			sql.openParenthesis("AND");
			sql.addClause( "OR", "cd_tipo_documento_cont", sql.EQUALS, Numerazione_doc_contBulk.TIPO_IMP);
			sql.addClause( "OR", "cd_tipo_documento_cont", sql.EQUALS, Numerazione_doc_contBulk.TIPO_OBB);
			sql.closeParenthesis();
		} else if (((MandatoAutomaticoWizardBulk)mandato).getTi_impegni().equals(MandatoAutomaticoWizardBulk.IMPEGNI_TIPO_RESIDUO)) {
			sql.openParenthesis("AND");
			sql.addClause( "OR", "cd_tipo_documento_cont", sql.EQUALS, Numerazione_doc_contBulk.TIPO_IMP_RES);
			sql.addClause( "OR", "cd_tipo_documento_cont", sql.EQUALS, Numerazione_doc_contBulk.TIPO_OBB_RES);
			sql.addClause( "OR", "cd_tipo_documento_cont", sql.EQUALS, Numerazione_doc_contBulk.TIPO_OBB_RES_IMPROPRIA);
			sql.closeParenthesis();
		}

		if ( !mandato.TIPO_REGOLARIZZAZIONE.equals( mandato.getTi_mandato()) )
		{
			sql.addClause( "AND", "cd_cds_origine", sql.EQUALS, mandato.getCd_cds());
			sql.addClause( "AND", "cd_uo_origine", sql.EQUALS, mandato.getCd_unita_organizzativa());
		}	
		sql.addSQLClause( "AND", "fl_selezione", sql.EQUALS, "Y");
		sql.addClause( "AND", "stato_cofi", sql.EQUALS, Documento_genericoBulk.STATO_CONTABILIZZATO);	

		sql.addSQLClause( "AND", "IM_SCADENZA", sql.GREATER, new java.math.BigDecimal(0));

		SQLBuilder sqlTerzo = selectTerzo(mandato);
		sqlTerzo.addSQLJoin("V_ANAGRAFICO_TERZO.CD_TERZO", "V_DOC_PASSIVO_OBBLIGAZIONE.CD_TERZO");
		sql.addSQLExistsClause("AND", sqlTerzo);
		return sql;
	}
}
