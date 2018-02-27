<%@ page pageEncoding="UTF-8"
	import="it.cnr.jada.util.jsp.*,it.cnr.jada.action.*,java.util.*,it.cnr.jada.util.action.*,it.cnr.contab.reports.bp.*"
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<% JSPUtils.printBaseUrl(pageContext); %>
<title>Risultato ricerca</title>
<script language="JavaScript" src="scripts/util.js"></script>
<script language="javascript" src="scripts/css.js"></script>
</head>

<body class="Form">

<% PrintSpoolerBP bp = (PrintSpoolerBP)BusinessProcess.getBusinessProcess(request);
	 bp.openFormWindow(pageContext); %>
	 <% bp.writeFormLabel(out,"ti_visibilita"); %>&nbsp;
	 <div class="d-inline-block">
	    <% bp.writeFormInput(out,null,"ti_visibilita",false,null,"onclick=\"javascript:submitForm('doCambiaVisibilita')\""); %>
	 </div>
	 <% bp.writeHTMLTable(pageContext,"100%","70%"); %>
	 <% bp.writeHTMLNavigator(out); %>
	 <% if (bp.isEMailEnabled()){ %>
		<div class="GroupLabel text-primary h2">E-Mail</div>
		<table class="Group card p-2" >
			<tr>
				<td><% bp.getController().writeFormLabel(out,"email_a");%> </td>
				<td><% bp.getController().writeFormInput(out,null,"email_a",true,null,null);%></td>
			</tr>
			<tr>
				<td><% bp.getController().writeFormLabel(out,"email_cc");%></td>
				<td><% bp.getController().writeFormInput(out,null,"email_cc",true,null,null);%></td>
			</tr>	
			<tr>
				<td><% bp.getController().writeFormLabel(out,"email_ccn");%></td>
				<td><% bp.getController().writeFormInput(out,null,"email_ccn",true,null,null);%></td>
			</tr>
			<tr>
				<td><% bp.getController().writeFormLabel(out,"email_subject");%></td>
				<td><% bp.getController().writeFormInput(out,null,"email_subject",true,null,null);%></td>
			</tr>
			<tr>
				<td><% bp.getController().writeFormLabel(out,"email_body");%></td>
				<td><% bp.getController().writeFormInput(out,null,"email_body",true,null,null);%></td>
			</tr>
		</table>
	 <%}%>
<%	bp.closeFormWindow(pageContext); %>
</body>

</html>