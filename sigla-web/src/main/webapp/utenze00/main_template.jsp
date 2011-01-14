<!-- 
 ?ResourceName ""
 ?ResourceTimestamp ""
 ?ResourceEdition ""
-->

<%@ page 
	import="it.cnr.jada.util.jsp.*,
	  it.cnr.jada.action.*,
		it.cnr.jada.bulk.*,
		it.cnr.jada.util.action.*,
		it.cnr.contab.utenze00.bp.*"
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<script language="JavaScript" src="scripts/util.js"></script>
<% JSPUtils.printBaseUrl(pageContext); %>
</head>
<script language="javascript" src="scripts/css.js"></script>
<title>Gestione Template</title>
<body class="Form">

<% CRUDBP bp = (CRUDBP)BusinessProcess.getBusinessProcess(request);
	 bp.openFormWindow(pageContext); %>

	<div class="Panel">
		<%	JSPUtils.tabbed(
					pageContext,
					"tab",
					new String[][] {
							{ "tabUtenza","Template","/utenze00/tab_template.jsp" },
							{ "tabAccessi","Accessi","/utenze00/tab_accessi.jsp" },
							{ "tabRuoli","Ruoli","/utenze00/tab_ruoli.jsp" },
                            { "tabMail","Indirizzi E-Mail","/utenze00/tab_mail.jsp" }},
					bp.getTab("tab"),
					"center",
					"100%",
                    "100%" );
		%>
	</div>

<%	bp.closeFormWindow(pageContext); %>
</body>
</html>