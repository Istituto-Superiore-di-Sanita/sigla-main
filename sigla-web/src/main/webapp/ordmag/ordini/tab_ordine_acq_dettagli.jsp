<%@ page 
	import = "it.cnr.jada.util.jsp.*,
		it.cnr.jada.action.*,
		java.util.*,
		it.cnr.jada.util.action.*,
		it.cnr.contab.ordmag.ordini.bp.CRUDOrdineAcqBP,
		it.cnr.contab.ordmag.ordini.bulk.OrdineAcqRigaBulk,
		it.cnr.contab.ordmag.anag00.*"
%>

<% CRUDOrdineAcqBP bp = (CRUDOrdineAcqBP)BusinessProcess.getBusinessProcess(request);
    OrdineAcqRigaBulk riga = (OrdineAcqRigaBulk)bp.getRighe().getModel();
	bp.getRighe().writeHTMLTable(pageContext,"righeSet",true,false,true,"100%","200px"); %>
<tr><td colspan=10>
	      <%
	      	String[][] pages = null;
	      	if(riga != null && riga.getNumero() != null){
	      		pages = new String[][] {
					{ "tabOrdineDettaglio","Dettaglio Riga","/ordmag/ordini/tab_ordine_acq_dettaglio.jsp" },
					{ "tabOrdineDettaglioAllegati","Allegati","/ordmag/ordini/tab_ordine_acq_dettaglio_allegati.jsp" } };
	      	} else {
	      		pages = new String[][] {
	      			{ "tabOrdineDettaglio","Dettaglio Riga","/ordmag/ordini/tab_ordine_acq_dettaglio.jsp" } };
	      	}
	      	JSPUtils.tabbed(pageContext, "tabOrdineAcqDettaglio",
	      			pages,
	      			bp.getTab("tabOrdineAcqDettaglio"), "left", "90%", null, true);
	      %>
</td></tr>
