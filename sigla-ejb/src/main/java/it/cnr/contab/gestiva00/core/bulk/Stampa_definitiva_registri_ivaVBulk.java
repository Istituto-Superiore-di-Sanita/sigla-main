package it.cnr.contab.gestiva00.core.bulk;

import it.cnr.contab.docamm00.tabrif.bulk.*;
import it.cnr.contab.reports.bulk.Print_spooler_paramBulk;
import it.cnr.jada.action.ActionContext;
import it.cnr.jada.bulk.BulkList;
import it.cnr.jada.util.*;

/**
 * Insert the type's description here.
 * Creation date: (10/16/2001 11:10:31 AM)
 * @author: Ardire Alfonso
 */
public class Stampa_definitiva_registri_ivaVBulk 
	extends Stampa_registri_ivaVBulk
	implements IPrintable {

	private java.math.BigDecimal id_report = null;
	private java.lang.Integer pageNumber = null;
/**
 * Filtro_ricerca_obbligazioniVBulk constructor comment.
 */
public Stampa_definitiva_registri_ivaVBulk() {
	super();
}
/**
 * Insert the method's description here.
 * Creation date: (12/6/2002 12:34:46 PM)
 */
public java.math.BigDecimal getId_report() {
	return id_report;
}
/**
 * Insert the method's description here.
 * Creation date: (2/24/2003 10:27:53 AM)
 * @return java.lang.Integer
 */
public java.lang.Integer getPageNumber() {
	return pageNumber;
}
/**
 * Insert the method's description here.
 * Creation date: (12/6/2002 12:31:00 PM)
 * @return java.math.BigDecimal
 */
public java.lang.String getReportName() {

	if (getTipo_sezionale() == null) return "";
	
	return (Tipo_sezionaleBulk.ACQUISTI.equals(getTipo_sezionale().getTi_acquisti_vendite())) ?
        						"/gestiva/gestiva/registro_iva_acquisti.jasper" :
        						"/gestiva/gestiva/registro_iva_vendite.jasper";
}
/**
 * Insert the method's description here.
 * Creation date: (12/6/2002 12:31:00 PM)
 * @return java.math.BigDecimal
 */
public java.util.Vector getReportParameters() {

	java.util.Vector params = new java.util.Vector();
	if (getId_report() != null) {
		params.add(getId_report().toString());
		params.add(getPageNumber().toString());
	}
	return params;
}
/**
 * Insert the method's description here.
 * Creation date: (12/10/2002 2:30:19 PM)
 * @author: Alfonso Ardire
 * @param newTipo_report java.lang.String
 */
public java.lang.String getTipo_documento_stampato() {
	return "*";
}
/**
 * Insert the method's description here.
 * Creation date: (12/10/2002 2:30:19 PM)
 * @author: Alfonso Ardire
 * @param newTipo_report java.lang.String
 */
public java.lang.String getTipo_report_stampato() {
	return "REGISTRO_IVA";
}
/**
 * Insert the method's description here.
 * Creation date: (10/16/2001 11:18:42 AM)
 * @param newFl_fornitore java.lang.Boolean
 */
public it.cnr.jada.bulk.OggettoBulk initializeForSearch(
	it.cnr.jada.util.action.BulkBP bp,
	it.cnr.jada.action.ActionContext context) {

	Stampa_definitiva_registri_ivaVBulk bulk = (Stampa_definitiva_registri_ivaVBulk)super.initializeForSearch(bp, context);
	
	bulk.setEsercizio(it.cnr.contab.utenze00.bulk.CNRUserInfo.getEsercizio(context));
	it.cnr.contab.config00.sto.bulk.Unita_organizzativaBulk unita_organizzativa = it.cnr.contab.utenze00.bulk.CNRUserInfo.getUnita_organizzativa(context);
	bulk.setCd_unita_organizzativa(unita_organizzativa.getCd_unita_organizzativa());
	
	bulk.setCd_cds(unita_organizzativa.getUnita_padre().getCd_unita_organizzativa());
	bulk.setTipo_report(DEFINITIVO);
	bulk.setTipo_stampa(TIPO_STAMPA_REGISTRI);
	bulk.setPageNumber(new Integer(1));
	
	return bulk;
}
/**
 * Insert the method's description here.
 * Creation date: (2/24/2003 10:27:53 AM)
 * @return java.lang.Integer
 */
public boolean isPageNumberRequired() {
	return true;
}
/**
 * Insert the method's description here.
 * Creation date: (12/6/2002 12:34:46 PM)
 */
public boolean isRistampabile() {
	return true;
}
/**
 * Insert the method's description here.
 * Creation date: (12/6/2002 12:34:46 PM)
 */
public void setId_report(java.math.BigDecimal new_id_report) {

	id_report = new_id_report;	
}
/**
 * Insert the method's description here.
 * Creation date: (2/24/2003 10:27:53 AM)
 * @param newPageNumber java.lang.Integer
 */
public void setPageNumber(java.lang.Integer newPageNumber) {

	pageNumber = newPageNumber;
}
public BulkList getPrintSpoolerParam() {
	BulkList printSpoolerParam = new BulkList();
	Print_spooler_paramBulk param;
	param = new Print_spooler_paramBulk();
	param.setNomeParam("idReport");
	param.setValoreParam(getId_report().toString());
	param.setParamType("java.lang.Integer");
	printSpoolerParam.add(param);

	param = new Print_spooler_paramBulk();
	param.setNomeParam("aPagIniziale");
	param.setValoreParam(getPageNumber().toString());
	param.setParamType("java.lang.Integer");
	printSpoolerParam.add(param);
	
	return printSpoolerParam;
}
}
