<%@ page
        import="it.cnr.jada.action.*,
                it.cnr.jada.bulk.*,
                it.cnr.jada.util.action.*,
                it.cnr.jada.util.jsp.*,
                it.cnr.contab.progettiric00.bp.*,
                it.cnr.contab.progettiric00.core.bulk.*"
%>

<%!     static String[][] tabs = null;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>

<head>
<% JSPUtils.printBaseUrl(pageContext); %>
<script language="javascript" src="scripts/css.js"></script>
<script language="JavaScript" src="scripts/util.js"></script>
<title>Moduli di Attivit�</title>
</head>

<body class="Form">
<% ModuloAttivitaBP bp = (ModuloAttivitaBP)BusinessProcess.getBusinessProcess(request);
   bp.openFormWindow(pageContext);   
   tabs = new String[][] {
                   { "tabTestata","Testata","/progettiric00/progetto_ricerca_testata_moduli.jsp" }};//,
                   //{ "tabDettagli","UO partecipanti","/progettiric00/progetto_ricerca_dettagli.jsp" },
                   //{ "tabDettagliPartner_esterni","Partner esterni","/progettiric00/progetto_ricerca_dettagliPartner_esterni.jsp" },
                   //{ "tabDettagliPostIt","Post-It","/config00/dettagliPostIt.jsp" }};
   JSPUtils.tabbed(
                   pageContext,
                   "tab",
                   tabs,
                   bp.getTab("tab"),
                   "center",
                   "100%",
                   "100%" );

   bp.closeFormWindow(pageContext); 
%>
</body>