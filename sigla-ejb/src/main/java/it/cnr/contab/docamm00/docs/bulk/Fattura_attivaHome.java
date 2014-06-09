package it.cnr.contab.docamm00.docs.bulk;

/**
 * Insert the type's description here.
 * Creation date: (9/5/2001 5:02:18 PM)
 * @author: Ardire Alfonso
 */
import it.cnr.jada.UserContext;
import it.cnr.jada.bulk.BulkHome;
import it.cnr.jada.persistency.PersistencyException;
import it.cnr.jada.persistency.Persistent;
import it.cnr.jada.persistency.PersistentCache;

public class Fattura_attivaHome extends BulkHome {
protected Fattura_attivaHome(Class clazz,java.sql.Connection connection) {
	super(clazz,connection);
}
protected Fattura_attivaHome(Class clazz,java.sql.Connection connection,PersistentCache persistentCache) {
	super(clazz,connection,persistentCache);
}
public Fattura_attivaHome(java.sql.Connection conn) {
	super(Fattura_attivaBulk.class,conn);
}
public Fattura_attivaHome(java.sql.Connection conn,PersistentCache persistentCache) {
	super(Fattura_attivaBulk.class,conn,persistentCache);
}
/**
 * Inizializza la chiave primaria di un OggettoBulk per un
 * inserimento. Da usare principalmente per riempire i progressivi
 * automatici.
 * @param bulk l'OggettoBulk da inizializzare  
 */
public java.sql.Timestamp findForMaxDataRegistrazione(it.cnr.jada.UserContext userContext,Fattura_attivaBulk fattura) throws PersistencyException,it.cnr.jada.comp.ComponentException {

	if (fattura == null) return null;
	try {
		java.sql.Connection contact = getConnection();
		String query="SELECT MAX(DT_REGISTRAZIONE) FROM "+
		it.cnr.jada.util.ejb.EJBCommonServices.getDefaultSchema() + "FATTURA_ATTIVA " + 
		"WHERE CD_UO_ORIGINE= '"+fattura.getCd_uo_origine()+"'";

		if (fattura.getSezionale()!=null && fattura.getSezionale().getCd_tipo_sezionale()!=null)
			query=query+"AND CD_TIPO_SEZIONALE= '"+fattura.getSezionale().getCd_tipo_sezionale()+"' ";
		
		java.sql.ResultSet rs = contact.createStatement().executeQuery(query);

		if(rs.next())
			return rs.getTimestamp(1);
		else
			return null;
	} catch(java.sql.SQLException sqle) {
		throw new PersistencyException(sqle);
	}
}
@Override
public Persistent completeBulkRowByRow(UserContext userContext,
		Persistent persistent) throws PersistencyException {
	Fattura_attivaBulk fattura = (Fattura_attivaBulk)persistent;
	fattura.setCollegamentoDocumentale("<button class='Button' style='width:60px;' onclick='cancelBubble(event); if (disableDblClick()) "+
			"doVisualizzaSingoloDocumentiCollegati("+fattura.getEsercizio()+",\""+fattura.getCd_cds()+"\",\""+fattura.getCd_uo()+"\","+fattura.getPg_fattura_attiva()+",\""+Filtro_ricerca_doc_ammVBulk.DOC_ATT_GRUOP+"\"); return false' "+
			"onMouseOver='mouseOver(this)' onMouseOut='mouseOut(this)' onMouseDown='mouseDown(this)' onMouseUp='mouseUp(this)' "+
			"title='Visualizza Documenti Collegati'><img align='middle' class='Button' src='img/application-pdf.png'></button>");
	return super.completeBulkRowByRow(userContext, persistent);
	}
}
