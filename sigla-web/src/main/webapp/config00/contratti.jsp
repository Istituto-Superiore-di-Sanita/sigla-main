<%@ page 
	import="it.cnr.jada.action.*,
		it.cnr.jada.bulk.*,
		it.cnr.jada.util.action.*,
		it.cnr.jada.util.jsp.*,
		it.cnr.contab.config00.contratto.bulk.*,
		it.cnr.contab.config00.bp.*"
%>

<% 
	SimpleCRUDBP bp = (SimpleCRUDBP)BusinessProcess.getBusinessProcess(request);	
%>
<%!     static String[][] tabs = null;
%>
<html>

<head>
<% JSPUtils.printBaseUrl(pageContext); %>
<script language="javascript" src="scripts/css.js"></script>
<script language="JavaScript" src="scripts/util.js"></script>
<title><%=bp.getBulkInfo().getShortDescription()%></title>
</head>

<body class="Form">
<% bp.openFormWindow(pageContext);
   ContrattoBulk contratto = (ContrattoBulk)bp.getModel();	                
   if(contratto.isPassivo() &&
		   ((contratto.getFl_pubblica_contratto()!=null && contratto.getFl_pubblica_contratto()) ||  
		   (contratto.isProvvisorio() && contratto.getTipo_contratto()!=null && contratto.getTipo_contratto().getFl_pubblica_contratto()!= null && contratto.getTipo_contratto().getFl_pubblica_contratto())))
   tabs = new String[][] {
	               { "tabTestata","Contratti","/config00/tab_contratti_testata.jsp" },
	               { "tabCessazione","Dati di cessazione dell�efficacia","/config00/tab_contratti_cessazione.jsp" },
	               { "tabAss_contratto_uo","CdR","/config00/tab_ass_contratto_uo.jsp" },
	               { "tabAllegati","Allegati","/config00/tab_contratti_allegati.jsp" },
	               { "tabAss_contratto_ditte","Ditte Invitate","/config00/tab_ass_contratto_ditte.jsp" },
	               };   
   else
	   tabs = new String[][] {
           { "tabTestata","Contratti","/config00/tab_contratti_testata.jsp" },
           { "tabCessazione","Dati di cessazione dell�efficacia","/config00/tab_contratti_cessazione.jsp" },
           { "tabAss_contratto_uo","CdR","/config00/tab_ass_contratto_uo.jsp" },
           { "tabAllegati","Allegati","/config00/tab_contratti_allegati.jsp" },
           };
%>
<table class="Panel" width="100%" height="100%">
	<tr>
		<td width="100%">
		   <table class="ToolBar" width="100%" cellspacing="0" cellpadding="2"><tr><td>
			<table class="Panel" align="left" cellspacing=4 cellpadding=4>
			  <tr>
		         <td><% bp.getController().writeFormField(out,"esercizio");%></td>
		         <td><% bp.getController().writeFormField(out,"pg_contratto");%></td>
		      </tr>  
		   	</table>
		   	</td></tr></table>	
		</td>
	</tr>
	<tr>
		<td height="100%">
		   <% JSPUtils.tabbed(
		                   pageContext,
		                   "tab",
		                   tabs,
		                   bp.getTab("tab"),
		                   "center",
		                   "100%",
		                   "100%" ); %>
		</td>
	</tr>
</table>
<% bp.closeFormWindow(pageContext); %>
</body>
</html>