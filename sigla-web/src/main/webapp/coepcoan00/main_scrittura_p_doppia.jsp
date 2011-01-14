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
		it.cnr.contab.coepcoan00.bp.*"
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<script language="JavaScript" src="scripts/util.js"></script>
<script language="JavaScript" src="scripts/disableRightClick.js"></script>
<% JSPUtils.printBaseUrl(pageContext);%>
</head>
<script language="javascript" src="scripts/css.js"></script>
<title>Scrittura in partita doppia</title>
<body class="Form">

<%  
		CRUDScritturaPDoppiaBP bp = (CRUDScritturaPDoppiaBP)BusinessProcess.getBusinessProcess(request);
		bp.openFormWindow(pageContext); 
%>

<table class="Panel">
<tr>
	<td><% bp.getController().writeFormLabel(out,"cd_cds"); %></td>
	<td><% bp.getController().writeFormInput(out,"cd_cds"); %></td>
	<td colspan=2>	    
		<% bp.getController().writeFormInput(out,"ds_cds"); %></td>	
</tr>
<tr>
	<td><% bp.getController().writeFormLabel(out,"cd_unita_organizzativa"); %></td>
	<td><% bp.getController().writeFormInput(out,"cd_unita_organizzativa"); %></td>
	<td colspan=2>	    
		<% bp.getController().writeFormInput(out,"ds_unita_organizzativa"); %></td>	
</tr>
<tr>
	<td><% bp.getController().writeFormLabel(out,"esercizio"); %></td>
	<td><% bp.getController().writeFormInput(out,"esercizio"); %></td>
	<td><% bp.getController().writeFormLabel(out,"pg_scrittura"); %></td>
	<td><% bp.getController().writeFormInput(out,"pg_scrittura"); %></td>	
</tr>
<tr>
	<td><% bp.getController().writeFormLabel(out,"imTotaleDare"); %></td>
	<td><% bp.getController().writeFormInput(out,"imTotaleDare"); %></td>
	<td><% bp.getController().writeFormLabel(out,"imTotaleAvere"); %></td>
	<td><% bp.getController().writeFormInput(out,"imTotaleAvere"); %></td>	
</tr>
</table>
<%
	if (bp.isViewing()){
%>
<br>
<table class="Group">
<tr>
	<td colspan=4>
		  <span class="FormLabel" style="color:blue">Documento origine</span>
	</td>
</tr>
<tr>
	<td><% bp.getController().writeFormLabel(out,"origine_scrittura"); %></td>
	<td><% bp.getController().writeFormInput(out,"origine_scrittura"); %></td>
	<td><% bp.getController().writeFormLabel(out,"cd_tipo_documento"); %></td>
	<td><% bp.getController().writeFormInput(out,"cd_tipo_documento"); %></td>
</tr>
<tr>
	<td><% bp.getController().writeFormLabel(out,"pg_numero_documento"); %></td>
	<td><% bp.getController().writeFormInput(out,"pg_numero_documento"); %></td>
	<td><% bp.getController().writeFormLabel(out,"cd_comp_documento"); %></td>
	<td><% bp.getController().writeFormInput(out,"cd_comp_documento"); %></td>
</tr>
<tr>
	<td><% bp.getController().writeFormLabel(out,"cd_cds_documento"); %></td>
	<td colspan=3><% bp.getController().writeFormInput(out,"cd_cds_documento"); %></td>
</tr>
<tr>
	<td><% bp.getController().writeFormLabel(out,"cd_uo_documento"); %></td>
	<td colspan=3><% bp.getController().writeFormInput(out,"cd_uo_documento"); %></td>
</tr>
<tr>
	<td><% bp.getController().writeFormLabel(out,"esercizio_documento_amm"); %></td>
	<td colspan=3><% bp.getController().writeFormInput(out,"esercizio_documento_amm"); %></td>
</tr>
</table>
<br>
<%
	}
%>
	
<table>
	<tr><td>
	<%
		JSPUtils.tabbed(
						pageContext,
						"tab",
						new String[][] {
								{ "tabScrittura","Scrittura","/coepcoan00/tab_scrittura.jsp" },
								{ "tabDare","Dare","/coepcoan00/tab_dare.jsp" },
								{ "tabAvere","Avere","/coepcoan00/tab_avere.jsp" }},						
						bp.getTab("tab"),
						"center",
						null,null);		
	%>
	</td></tr>
</table>	
<%	bp.closeFormWindow(pageContext); %>
</body>
</html>