package it.cnr.contab.docamm00.tabrif.bulk;

import it.cnr.jada.bulk.*;
import it.cnr.jada.comp.*;
import it.cnr.jada.persistency.*;
import it.cnr.jada.persistency.beans.*;
import it.cnr.jada.persistency.sql.*;

public class Voce_ivaHome extends BulkHome {
public Voce_ivaHome(java.sql.Connection conn) {
	super(Voce_ivaBulk.class,conn);
}
public Voce_ivaHome(java.sql.Connection conn,PersistentCache persistentCache) {
	super(Voce_ivaBulk.class,conn,persistentCache);
}
/**
 * 
 */

public boolean checkDefaultIstituzionale(OggettoBulk viva)  throws ApplicationException, PersistencyException
{
	/*
	if(aAss.getCentro_responsabilita().getCd_centro_responsabilita() == null)
	 throw new ApplicationException("Riferimenti al cdr non specificati!");
	*/
	if (!((Voce_ivaBulk)viva).getPercentuale().equals(new java.math.BigDecimal("0")))
			throw new ApplicationException("Non e' possibile impostare come default istituzionale se la percentuale e' diversa da zero ");					
	try
	{
		LoggableStatement ps = new LoggableStatement(getConnection(),
			"SELECT CD_VOCE_IVA FROM " + 
			it.cnr.jada.util.ejb.EJBCommonServices.getDefaultSchema() + 
			"VOCE_IVA " +
			"WHERE FL_DEFAULT_ISTITUZIONALE = ? AND CD_VOCE_IVA <> ?",true,this.getClass());
		ps.setString( 1, "Y");
		ps.setString( 2, ((Voce_ivaBulk)viva).getCd_voce_iva());
		
		java.sql.ResultSet rs = ps.executeQuery();
		if ( rs.next() )
			 throw new ApplicationException("Esiste gi� una voce iva di default istituzionale ( codice iva = "+ rs.getString("CD_VOCE_IVA")+ ") ");
		return true;
	} catch ( java.sql.SQLException e )
	{
			throw new PersistencyException( e );
	}
	
}
/**
 * 
 */

public boolean checkPercentuale(OggettoBulk vova) throws ApplicationException, PersistencyException {

    try {
    	LoggableStatement ps=
            new LoggableStatement(getConnection(),"SELECT VOCE_IVA.PERCENTUALE FROM "
	            + it.cnr.jada.util.ejb.EJBCommonServices.getDefaultSchema() + "VOCE_IVA,GRUPPO_IVA "
	            + "WHERE VOCE_IVA.CD_GRUPPO_IVA = GRUPPO_IVA.CD_GRUPPO_IVA AND "
	            + "GRUPPO_IVA.CD_GRUPPO_IVA = ? AND "
           	 	+ "VOCE_IVA.CD_VOCE_IVA <> ? ",true,this.getClass());
        ps.setString(1, (((Voce_ivaBulk) vova).getCd_gruppo_iva()).toString());
        ps.setString(2, (((Voce_ivaBulk) vova).getCd_voce_iva()).toString());

        java.sql.ResultSet rs= ps.executeQuery();
        if (rs.next())
        	if(rs.getBigDecimal("PERCENTUALE").compareTo(((Voce_ivaBulk) vova).getPercentuale())!=0)
	            throw new ApplicationException("Il gruppo iva selezionato � associato ad una voce iva con percentuale differente");
        return true;
    } catch (java.sql.SQLException e) {
        throw new PersistencyException(e);
    }

}
/**
 * 
 */

	public Voce_ivaBulk loadDefault() throws PersistencyException{
	
		java.util.List occurences = null;
		Voce_ivaBulk voceIva = new Voce_ivaBulk();
		voceIva.setFl_default_istituzionale(Boolean.TRUE);
		occurences = find(voceIva);
		if (occurences == null || occurences.isEmpty())
			return null;
		return (Voce_ivaBulk)occurences.get(0);
	}
	@Override
	public SQLBuilder selectByClause(CompoundFindClause compoundfindclause)
			throws PersistencyException {
		SQLBuilder sql= super.selectByClause(compoundfindclause);
		sql.openParenthesis("AND");
		sql.addSQLClause("AND", "DT_CANCELLAZIONE", sql.ISNULL, null);
		sql.addSQLClause("OR","DT_CANCELLAZIONE",sql.GREATER,it.cnr.jada.util.ejb.EJBCommonServices.getServerDate());
		sql.closeParenthesis();
		return sql;
	}
	@Override
	public SQLBuilder createSQLBuilder() {
		SQLBuilder sql=super.createSQLBuilder();
		sql.openParenthesis("AND");
		sql.addSQLClause("AND", "DT_CANCELLAZIONE", sql.ISNULL, null);
		sql.addSQLClause("OR","DT_CANCELLAZIONE",sql.GREATER,it.cnr.jada.util.ejb.EJBCommonServices.getServerDate());
		sql.closeParenthesis();
		return sql;
	}
}
