<%@page import="it.cnr.contab.ordmag.richieste.bp.CRUDRichiestaUopBP"%>
<%@ page 
	import="it.cnr.jada.util.jsp.*,
		it.cnr.jada.action.*,
		java.util.*,
		it.cnr.jada.util.action.*,
		it.cnr.contab.ordmag.anag00.*,
		it.cnr.contab.ordmag.richieste.*"
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<% JSPUtils.printBaseUrl(pageContext); %>
<script language="JavaScript" src="scripts/util.js"></script>
<script language="javascript" src="scripts/css.js"></script>
<title>Richieste da Unit� Operativa</title>
</head>
<body class="Form">
<% CRUDBP bp = (CRUDRichiestaUopBP)BusinessProcess.getBusinessProcess(request);
	 bp.openFormWindow(pageContext); %>
	<table>
		<%

		JSPUtils.tabbed(
					pageContext,
					"tab",
					new String[][] {
					{ "tabRichiestaUop","Richiesta","/ordmag/richieste/tab_richiesta_uop.jsp" },
					{ "tabRichiestaUopDettaglio","Dettaglio","/ordmag/richieste/tab_richiesta_uop_dettagli.jsp" },
					{ "tabAllegati","Allegati","/ordmag/richieste/tab_richiesta_allegati.jsp" }},
					bp.getTab("tab"),
					"center",
					"100%",
					null );
		%>
	
<%	bp.closeFormWindow(pageContext); %>
</body>
</html>