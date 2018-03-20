<%@ page pageEncoding="UTF-8"
	import="it.cnr.jada.action.*,
		it.cnr.jada.bulk.*,
		it.cnr.jada.util.action.*,
		it.cnr.jada.util.jsp.*,
		it.cnr.contab.progettiric00.tabrif.bulk.*"
%>

<%
	SimpleCRUDBP bp = (SimpleCRUDBP)BusinessProcess.getBusinessProcess(request);
	Voce_piano_economico_prgBulk bulk = (Voce_piano_economico_prgBulk)bp.getModel();
%>

<html>

<head>
<% JSPUtils.printBaseUrl(pageContext); %>
<script language="javascript" src="scripts/css.js"></script>
<script language="JavaScript" src="scripts/util.js"></script>
<title>Voci del Piano Economico per Progetti di Ricerca</title>
</head>

<body class="Form">
<%
	bp.openFormWindow(pageContext);
%>

	<table class="Panel">
		<TR><TD>
			<% bp.getController().writeFormLabel(out,"cd_voce_piano");%>
			</TD><TD>
			<% bp.getController().writeFormInput(out,"cd_voce_piano");%>
		</TD></TR>
		<TR><TD>
			<% bp.getController().writeFormLabel(out,"ds_voce_piano");%>
			</TD><TD colspan="3">
			<% bp.getController().writeFormInput(out,"ds_voce_piano");%>
		</TD></TR>
		<TR><TD>
			<% bp.getController().writeFormLabel(out,"fl_valido");%>
			</TD><TD colspan="3">
			<% bp.getController().writeFormInput(out,"fl_valido");%>
		</TD></TR>
	</table>

<%	bp.closeFormWindow(pageContext); %>
</body>