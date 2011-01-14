<%@ page 
		import="it.cnr.jada.util.jsp.*,
	        it.cnr.jada.action.*,
	        java.util.*, 
	        it.cnr.jada.util.action.*,
	        it.cnr.contab.prevent01.bp.*"
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
<% JSPUtils.printBaseUrl(pageContext);%>
<% CRUDDettagliModuloCostiBP bp = (CRUDDettagliModuloCostiBP)BusinessProcess.getBusinessProcess(request);%>
<script language="JavaScript" src="scripts/util.js"></script>
<script language="javascript" src="scripts/css.js"></script>
<title><%=bp.getBulkInfo().getShortDescription()%></title>
</head>
<body class="Form">
<%!     static String[][] tabs = null; %>
<% bp.openFormWindow(pageContext);
   tabs = new String[][] {
	               { "tabTotali","<SPAN style='font : bold 13px;'>Totali</SPAN>","/prevent01/tab_modulo_totali.jsp" },	                     
	               { "tabSpese","<SPAN style='font : bold 13px;'>Previsione di Impegno</SPAN>","/prevent01/tab_modulo_spese.jsp" },	                  
	               { "tabRisorse","<SPAN style='font : bold 13px;'>Risorse provenienti da esercizi precedenti</SPAN>","/prevent01/tab_modulo_risorse.jsp" },	               
	               { "tabCosti","<SPAN style='font : bold 13px;'>Costi Generali e Figurativi</SPAN>","/prevent01/tab_modulo_costi.jsp" }
	               };   
%>
<div class="Group" style="width:100%">
	<table class="Panel" cellspacing=2>
		<tr>
			<td><% bp.getController().writeFormLabel( out, "cd_centro_di_responsabilita"); %></td>	
			<td colspan=7>
			  <% bp.getController().writeFormInput( out, "cd_centro_di_responsabilita"); %>
			  <% bp.getController().writeFormInput( out, "ds_centro_di_responsabilita"); %>
			</td>
		</tr>
		<tr>
			<td NOWRAP><% bp.getController().writeFormLabel( out, "cd_modulo"); %></td>	
			<td><% bp.getController().writeFormInput( out, "cd_modulo"); %></td>
			<td NOWRAP><% bp.getController().writeFormLabel( out, "cd_commessa"); %></td>	
			<td><% bp.getController().writeFormInput( out, "cd_commessa"); %></td>
			<td NOWRAP><% bp.getController().writeFormLabel( out, "cd_progetto"); %></td>	
			<td><% bp.getController().writeFormInput( out, "cd_progetto"); %></td>
			<td NOWRAP><% bp.getController().writeFormLabel( out, "cd_dipartimento"); %></td>	
			<td><% bp.getController().writeFormInput( out, "cd_dipartimento"); %></td>
		</tr>
	</table>		
</div>
   <% JSPUtils.tabbed(
                   pageContext,
                   "tab",
                   tabs,
                   bp.getTab("tab"),
                   "center",
                   "100%",
                   "100%" ); %>

<%	bp.closeFormWindow(pageContext); %>
</body>