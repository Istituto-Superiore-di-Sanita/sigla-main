package it.cnr.contab.doccont00.core.bulk;

import it.cnr.contab.docamm00.docs.bulk.Tipo_documento_ammBulk;
import it.cnr.contab.utenze00.bp.CNRUserContext;
import it.cnr.contab.util.Utility;
import it.cnr.contab.anagraf00.core.bulk.TerzoBulk;
import it.cnr.contab.config00.pdcfin.bulk.Voce_fHome;
import it.cnr.contab.config00.pdcfin.bulk.Elemento_voceHome;
import it.cnr.contab.config00.sto.bulk.Unita_organizzativa_enteBulk;
import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.*;
import it.cnr.jada.comp.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

import java.util.*;
import java.rmi.RemoteException;
import java.sql.*;

import javax.ejb.EJBException;

public abstract class MandatoHome extends BulkHome {
public MandatoHome(Class clazz, java.sql.Connection conn) {
	super(clazz,conn);
}
public MandatoHome(Class clazz, java.sql.Connection conn,PersistentCache persistentCache) {
	super(clazz,conn,persistentCache);
}
/**
 * <!-- @TODO: da completare -->
 * Costruisce un MandatoHome
 *
 * @param conn	La java.sql.Connection su cui vengono effettuate le operazione di persistenza
 */
public MandatoHome(java.sql.Connection conn) {
	super(MandatoBulk.class,conn);
}
/**
 * <!-- @TODO: da completare -->
 * Costruisce un MandatoHome
 *
 * @param conn	La java.sql.Connection su cui vengono effettuate le operazione di persistenza
 * @param persistentCache	La PersistentCache in cui vengono cachati gli oggetti persistenti caricati da questo Home
 */
public MandatoHome(java.sql.Connection conn,PersistentCache persistentCache) {
	super(MandatoBulk.class,conn,persistentCache);
}
/**
 * <!-- @TODO: da completare -->
 * 
 *
 * @param mandato	
 * @return 
 * @throws PersistencyException	
 */
public Timestamp findDataUltimoMandatoPerCds( MandatoBulk mandato ) throws PersistencyException
{
	try
	{
		LoggableStatement ps = new LoggableStatement(getConnection(),
			"SELECT TRUNC(MAX(DT_EMISSIONE)) " +			
			"FROM " +
			it.cnr.jada.util.ejb.EJBCommonServices.getDefaultSchema() + 			
			"MANDATO WHERE " +
			"ESERCIZIO = ? AND CD_CDS = ?",true,this.getClass());
		try
		{
			ps.setObject( 1, mandato.getEsercizio() );
			ps.setString( 2, mandato.getCds().getCd_unita_organizzativa() );
		
			ResultSet rs = ps.executeQuery();
			try
			{
				if(rs.next())
					return rs.getTimestamp(1);
				else
					return null;
			}
			catch( SQLException e )
			{
				throw new PersistencyException( e );
			}
			finally
			{
				try{rs.close();}catch( java.sql.SQLException e ){};
			}
		}
		catch( SQLException e )
		{
			throw new PersistencyException( e );
		}
		finally
		{
			try{ps.close();}catch( java.sql.SQLException e ){};	
		}
	}
	catch ( SQLException e )
	{
			throw new PersistencyException( e );
	}
}
/**
 * <!-- @TODO: da completare -->
 * 
 *
 * @param mandato	
 * @return 
 * @throws PersistencyException	
 * @throws IntrospectionException	
 */
public abstract Collection findMandato_riga( it.cnr.jada.UserContext userContext,MandatoBulk mandato ) throws PersistencyException, IntrospectionException;
/**
 * <!-- @TODO: da completare -->
 * 
 *
 * @param mandato	
 * @return 
 * @throws PersistencyException	
 * @throws IntrospectionException	
 */
public abstract Mandato_terzoBulk findMandato_terzo( it.cnr.jada.UserContext userContext,MandatoBulk mandato ) throws PersistencyException, IntrospectionException;
/**
 * Metodo per cercare i sospesi associati al mandato.
 *
 * @param mandato <code>MandatoBulk</code> il mandato
 *
 * @return result i sospesi associati al mandato
 *
 */
public Collection findSospeso_det_usc( it.cnr.jada.UserContext userContext,MandatoBulk mandato ) throws PersistencyException, IntrospectionException
{
	PersistentHome home = getHomeCache().getHome( Sospeso_det_uscBulk.class );
	SQLBuilder sql = home.createSQLBuilder();
	sql.addClause( "AND", "esercizio", sql.EQUALS, mandato.getEsercizio());	
	sql.addClause( "AND", "cd_cds", sql.EQUALS, mandato.getCd_cds());
	sql.addClause( "AND", "pg_mandato", sql.EQUALS, mandato.getPg_mandato());
	sql.addClause( "AND", "ti_sospeso_riscontro", sql.EQUALS, SospesoBulk.TI_SOSPESO);
//	sql.addClause( "AND", "stato", sql.EQUALS, Sospeso_det_uscBulk.STATO_DEFAULT);	
	Collection result = home.fetchAll( sql);
	getHomeCache().fetchAll(userContext);
	return result;
}	

	/**
	 * Imposta il pg_mandato di un oggetto <code>MandatoBulk</code>.
	 *
	 * @param mandato <code>OggettoBulkBulk</code>
	 *
	 * @exception PersistencyException
	 */

public void initializePrimaryKeyForInsert(it.cnr.jada.UserContext userContext,OggettoBulk bulk) throws PersistencyException, ComponentException 
{
	try
	{
		MandatoBulk mandato = (MandatoBulk) bulk;
		Long pg;
		Numerazione_doc_contHome numHome = (Numerazione_doc_contHome) getHomeCache().getHome( Numerazione_doc_contBulk.class );
		if (Utility.createParametriCnrComponentSession().getParametriCnr(userContext,mandato.getEsercizio()).getFl_tesoreria_unica().booleanValue()){
			Unita_organizzativa_enteBulk uoEnte = (Unita_organizzativa_enteBulk)(getHomeCache().getHome(Unita_organizzativa_enteBulk.class).findAll().get(0));
			pg = numHome.getNextPg(userContext, mandato.getEsercizio(), uoEnte.getCd_cds(), Numerazione_doc_contBulk.TIPO_MAN, mandato.getUser());
		}
		else{
			pg = numHome.getNextPg(userContext, mandato.getEsercizio(), mandato.getCd_cds(), Numerazione_doc_contBulk.TIPO_MAN, mandato.getUser());
		}
		mandato.setPg_mandato( pg );
	}catch ( IntrospectionException e ){
		throw new PersistencyException( e );
	}
	catch ( ApplicationException e ){
		throw new ComponentException( e );
	} catch (RemoteException e) {
		throw new ComponentException( e );
	} catch (EJBException e) {
		throw new ComponentException( e );
	}
}
/**
 * <!-- @TODO: da completare -->
 * 
 *
 * @param bulk	
 * @return 
 * @throws PersistencyException	
 */
public java.util.Hashtable loadTipoDocumentoKeys( MandatoBulk bulk ) throws PersistencyException
{
	SQLBuilder sql = getHomeCache().getHome( Tipo_documento_ammBulk.class ).createSQLBuilder();
	sql.addClause( "AND", "ti_entrata_spesa", sql.EQUALS, "S" );
	List result = getHomeCache().getHome( Tipo_documento_ammBulk.class ).fetchAll( sql );
	Hashtable ht = new Hashtable();
	Tipo_documento_ammBulk tipo;
	for (Iterator i = result.iterator(); i.hasNext(); )
	{
		tipo = (Tipo_documento_ammBulk) i.next();
		ht.put( tipo.getCd_tipo_documento_amm(), tipo.getDs_tipo_documento_amm());
	}	
	return ht;
}
/**
 * <!-- @TODO: da completare -->
 * 
 *
 * @param bulk	
 * @return 
 * @throws PersistencyException	
 */
public java.util.Hashtable loadTipoDocumentoPerRicercaKeys( MandatoBulk bulk ) throws PersistencyException
{
	SQLBuilder sql = getHomeCache().getHome( Tipo_documento_ammBulk.class ).createSQLBuilder();
//	sql.addClause( "AND", "ti_entrata_spesa", sql.EQUALS, "S" );
	sql.openParenthesis( "AND");
	sql.addSQLClause( "AND", "fl_manrev_utente", sql.EQUALS, "M" );
	sql.addSQLClause( "OR", "fl_manrev_utente", sql.EQUALS, "E" );
	sql.closeParenthesis();	
	List result = getHomeCache().getHome( Tipo_documento_ammBulk.class ).fetchAll( sql );
	Hashtable ht = new Hashtable();
	Tipo_documento_ammBulk tipo;
	for (Iterator i = result.iterator(); i.hasNext(); )
	{
		tipo = (Tipo_documento_ammBulk) i.next();
		ht.put( tipo.getCd_tipo_documento_amm(), tipo.getDs_tipo_documento_amm());
	}	
	return ht;
}
}
