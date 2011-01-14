<%@ page 
	import="it.cnr.jada.util.jsp.*,
		it.cnr.jada.action.*,
		java.util.*,
		it.cnr.jada.util.action.*,
		it.cnr.contab.compensi00.bp.*,		
		it.cnr.contab.compensi00.tabrif.bulk.*"
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<% JSPUtils.printBaseUrl(pageContext); %>
<script language="JavaScript" src="scripts/util.js"></script>
<script language="javascript" src="scripts/css.js"></script>
<title>Tipologia Rischio</title>
</head>
<body class="Form">

<% 	SimpleCRUDBP bp = (SimpleCRUDBP)BusinessProcess.getBusinessProcess(request);
 	bp.openFormWindow(pageContext);
 	Tipologia_rischioBulk rischio = (Tipologia_rischioBulk)bp.getModel(); %>

<table class="Panel">
  <tr>
	<td><% bp.getController().writeFormLabel(out,"cd_tipologia_rischio"); %></td>
	<td><% bp.getController().writeFormInput(out,"cd_tipologia_rischio"); %></td>
  </tr>
  <tr>
	<td><% bp.getController().writeFormLabel(out,"ds_tipologia_rischio"); %></td>
	<td colspan=3><% bp.getController().writeFormInput(out,"ds_tipologia_rischio"); %></td>
  </tr>			
  <tr>
	<td><% bp.getController().writeFormLabel(out,"dt_inizio_validita"); %></td>
	<td><% bp.getController().writeFormInput(out,"dt_inizio_validita"); %></td>
	<% if (rischio.getDataFineValidita()!=null){ %>
		<td align="right"><% bp.getController().writeFormLabel(out,"dataFineValidita"); %></td>
		<td><% bp.getController().writeFormInput(out,"dataFineValidita"); %></td>
	<% } %>
  </tr>

  <tr>
	<td><% bp.getController().writeFormLabel(out,"aliquota_percipiente"); %></td>
	<td><% bp.getController().writeFormInput(out,"aliquota_percipiente"); %></td>
  </tr>			
  <tr>
	<td><% bp.getController().writeFormLabel(out,"aliquota_ente"); %></td>
	<td><% bp.getController().writeFormInput(out,"aliquota_ente"); %></td>
  </tr>			
</table>


<% bp.closeFormWindow(pageContext); %>
</body>
</html>