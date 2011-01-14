<%@ page 
	import="it.cnr.jada.util.jsp.*,
		it.cnr.jada.action.*,
		it.cnr.jada.bulk.*,
		it.cnr.jada.util.action.*" %>

<html>
<head>
<% JSPUtils.printBaseUrl(pageContext); %>
<script language="JavaScript" src="scripts/util.js"></script>
<script language="javascript" src="scripts/css.js"></script>
<title>Causale Variazione Bilancio</title>
</head>
<body class="Form">

<%	CRUDBP bp = (CRUDBP)BusinessProcess.getBusinessProcess(request);
	bp.openFormWindow(pageContext); %>

<table class="Panel">
	<% bp.getController().writeForm(out); %>
</table>

<%	bp.closeFormWindow(pageContext); %>

</body>
</html>