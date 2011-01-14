<!-- 
 ?ResourceName "TemplateForm.jsp"
 ?ResourceTimestamp "08/11/00 16.43.22"
 ?ResourceEdition "1.0"
-->

<%@ page 
	import="it.cnr.jada.util.jsp.*,it.cnr.jada.action.*,java.util.*,it.cnr.jada.util.action.*,it.cnr.contab.doccont00.core.bulk.ImpegnoResiduoBulk"
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<% JSPUtils.printBaseUrl(pageContext);%>
<script language="JavaScript" src="scripts/util.js"></script>
<script language="JavaScript" src="scripts/disableRightClick.js"></script>
<script language="javascript" src="scripts/css.js"></script>
</head>
<title>Gestione Impegno Residuo</title>
<body class="Form">
<% CRUDBP bp = (CRUDBP)BusinessProcess.getBusinessProcess(request);
	 bp.openFormWindow(pageContext);
	 ImpegnoResiduoBulk imp = (ImpegnoResiduoBulk) bp.getModel();%>	 

	<table class="Panel">
	<tr colspan=3>
	<td><% bp.getController().writeFormLabel( out, "esercizio_competenza"); %></td>
	<td colspan=2>
		<% bp.getController().writeFormInput( out, "esercizio_competenza"); %> </td>
	</tr>
	<tr>
	<td><% bp.getController().writeFormLabel( out, "unita_organizzativa"); %></td>
	<td>
		<% bp.getController().writeFormInput( out,"unita_organizzativa" ); %>
	</td>		
	</tr>
	<tr>
	<td><% bp.getController().writeFormLabel( out, "pg_obbligazione"); %></td>	
	<td colspan=2>
		<% bp.getController().writeFormInput( out, "pg_obbligazione"); %>
		<% bp.getController().writeFormLabel( out, "dt_registrazione"); %>
		<% bp.getController().writeFormInput( out, "dt_registrazione"); %>
		<% bp.getController().writeFormLabel( out, "pg_obbligazione_ori_riporto"); %>				
		<% bp.getController().writeFormInput( out, "pg_obbligazione_ori_riporto"); %></td>
	</tr>
	<tr>
	<td><% bp.getController().writeFormLabel( out, "ds_obbligazione"); %></td>
	<td colspan=2>
		<% bp.getController().writeFormInput( out, "ds_obbligazione"); %></td>
	</tr>
	</table>
	
	<div class="Group">
	<table>	
	<tr>
	<td><% bp.getController().writeFormLabel( out, "cd_terzo"); %></td>
	<td colspan=2>
		<% bp.getController().writeFormInput( out, "cd_terzo"); %>
		<% bp.getController().writeFormInput( out, "ds_creditore"); %>
		<% bp.getController().writeFormInput( out, "find_creditore"); %>		
	</td>				 
	</tr>
	<tr>
	<td>	<% bp.getController().writeFormLabel( out, "cd_terzo_precedente"); %></td>
	<td>	<% bp.getController().writeFormInput( out, "cd_terzo_precedente"); %></td>
	</tr>

	<tr>
	<td>	<% bp.getController().writeFormLabel( out, "codice_fiscale"); %></td>
	<td>	<% bp.getController().writeFormInput( out, "codice_fiscale"); %>
	        <% bp.getController().writeFormLabel( out, "partita_iva"); %>
	        <% bp.getController().writeFormInput( out, "partita_iva"); %>	        
	</td>
	</tr>
	</table>	
    </div>
	
	<table>
    <tr>
	<td><% bp.getController().writeFormLabel( out, "im_obbligazione"); %></td>
	<td colspan=2 >
		<% bp.getController().writeFormInput( out, "im_obbligazione"); %></td>
	</tr>
	<tr>
	<td><% bp.getController().writeFormLabel( out, "cd_voce"); %></td>
	<td colspan=2>
		<% bp.getController().writeFormInput( out, "cd_voce"); %>
		<% bp.getController().writeFormInput( out, "ds_voce"); %>
		<% bp.getController().writeFormInput( out, "find_voce"); %></td>				 
	</tr>
<!--		
	<tr>
	<td><% bp.getController().writeFormLabel( out, "dt_scadenza"); %></td>
	<td colspan=2>
		<% bp.getController().writeFormInput( out, "dt_scadenza"); %></td>
	</tr>
	<tr>
	<td><% bp.getController().writeFormLabel( out, "cd_riferimento_contratto"); %></td>
	<td colspan=2>
		<% bp.getController().writeFormInput( out, "cd_riferimento_contratto"); %></td>
	</tr>
		<tr>
	<td><% bp.getController().writeFormLabel( out, "dt_scadenza_contratto"); %></td>
	<td colspan=2>
		<% bp.getController().writeFormInput( out, "dt_scadenza_contratto"); %></td>
	</tr>
	<tr>
	<td><% bp.getController().writeFormLabel( out, "cd_tipo_obbligazione"); %></td>
	<td colspan=2>
		<% bp.getController().writeFormInput( out, "cd_tipo_obbligazione"); %>
		<% bp.getController().writeFormInput( out, "ds_tipo_obbligazione"); %>
		<% bp.getController().writeFormInput( out, "find_tipo_obbligazione"); %></td>				 
	</tr>
-->	
	</table>
<!--
  <div class="Group">		
  <table border="0" cellspacing="0" cellpadding="2">
	<tr>
	<td><% bp.getController().writeFormLabel( out, "esercizio_doc_passivo"); %></td>
	<td colspan=2>
		<% bp.getController().writeFormInput( out, "esercizio_doc_passivo"); %>
		<% bp.getController().writeFormLabel( out, "pg_doc_passivo"); %>
		<% bp.getController().writeFormInput( out, "pg_doc_passivo"); %>
		<% bp.getController().writeFormLabel( out, "cd_tipo_documento_amm"); %>
		<% bp.getController().writeFormInput( out, "cd_tipo_documento_amm"); %></td>				 
	</tr>
	<tr>
	<td><% bp.getController().writeFormLabel( out, "esercizio_mandato"); %></td>
	<td colspan=2>
		<% bp.getController().writeFormInput( out, "esercizio_mandato"); %>
		<% bp.getController().writeFormLabel( out, "pg_mandato"); %>
		<% bp.getController().writeFormInput( out, "pg_mandato"); %></td>				 
	</tr>
  </table>
  </div>
-->
<%	bp.closeFormWindow(pageContext); %>
</body>
</html>