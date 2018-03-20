<%@ page pageEncoding="UTF-8"
	import="it.cnr.jada.util.jsp.*,
		it.cnr.jada.action.*,
		it.cnr.jada.bulk.*,
		it.cnr.jada.util.action.*,
		it.cnr.contab.pdg00.bulk.*,
		it.cnr.contab.prevent01.bulk.*,
		it.cnr.contab.prevent01.bp.*"
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<% JSPUtils.printBaseUrl(pageContext);%>
<%	BulkBP bp = (BulkBP)BusinessProcess.getBusinessProcess(request);%>
<script language="JavaScript" src="scripts/util.js"></script>
<script language="javascript" src="scripts/css.js"></script>
<title>Stampa Situazione Contabile Progetto</title>
</head>
<body class="Form">

<%	bp.openFormWindow(pageContext); %>

<table width=100%>
  <tr>
	<td><% bp.getController().writeFormLabel(out,"esercizio"); %></td>
	<td><% bp.getController().writeFormInput(out,"esercizio"); %></td>
  </tr>
  <tr>
  <td><% bp.getController().writeFormLabel(out,"findCdsForPrint"); %></td>
	<td>
		<% bp.getController().writeFormInput(out,"cdCdsForPrint"); %>
		<% bp.getController().writeFormInput(out,"dsCdsForPrint"); %>
		<% bp.getController().writeFormInput(out,"findCdsForPrint"); %>
	</td>
  </tr>
  <tr>
  <td><% bp.getController().writeFormLabel(out,"findUoForPrint"); %></td>
	<td>
		<% bp.getController().writeFormInput(out,"cdUoForPrint"); %>
		<% bp.getController().writeFormInput(out,"dsUoForPrint"); %>
		<% bp.getController().writeFormInput(out,"findUoForPrint"); %>
	</td>
  </tr>
  <tr>
    <td>
		<% bp.getController().writeFormLabel(out,"findProgettoForPrint");%>
	</td>
	<td colspan="3">	
		<% bp.getController().writeFormInput(out,null,"cdProgettoForPrint",false,null,null); %>
		<% bp.getController().writeFormInput(out,null,"dsProgettoForPrint",false,null,null); %>
		<% bp.getController().writeFormInput(out,null,"findProgettoForPrint",false,null,null); %>
	</td>
  </tr>	  
  <tr>
    <td>
		<% bp.getController().writeFormLabel(out,"findGaeForPrint");%>
	</td>
	<td colspan="3">	
		<% bp.getController().writeFormInput(out,null,"cdGaeForPrint",false,null,null); %>
		<% bp.getController().writeFormInput(out,null,"dsGaeForPrint",false,null,null); %>
		<% bp.getController().writeFormInput(out,null,"findGaeForPrint",false,null,null); %>
	</td>
  </tr>	  
  <tr>
    <td>
		<% bp.getController().writeFormLabel(out,"findResponsabileGaeForPrint");%>
	</td>
	<td colspan="3">	
		<% bp.getController().writeFormInput(out,null,"cdResponsabileGaeForPrint",false,null,null); %>
		<% bp.getController().writeFormInput(out,null,"dsResponsabileGaeForPrint",false,null,null); %>
		<% bp.getController().writeFormInput(out,null,"findResponsabileGaeForPrint",false,null,null); %>
	</td>
  </tr>	  
</table>

<table>  
  <tr>
   <td>	
	<table>  
	  <tr>
	   <td>	
		<div class="Group">
		<table>    	  
		  <tr>
			<td class="GroupLabel">Stampa Dettagli</td>
			<td></td>
		  </tr>	
		  <tr>
			<td><% bp.getController().writeFormLabel(out,"printGae"); %></td>
			<td><% bp.getController().writeFormInput(out,"printGae"); %></td>
			<% if (Boolean.TRUE.equals(((Stampa_situazione_sintetica_x_progettoBulk)bp.getModel()).getPrintGae())) { %>
				<td><% bp.getController().writeFormLabel(out,"printSoloGaeAttive"); %></td>
				<td><% bp.getController().writeFormInput(out,"printSoloGaeAttive"); %></td>
			<% } %>
		  </tr>
		  <tr>
			<td><% bp.getController().writeFormLabel(out,"printVoce"); %></td>
			<td><% bp.getController().writeFormInput(out,"printVoce"); %></td>
		  </tr>
		  <tr>
			<td><% bp.getController().writeFormLabel(out,"printAnno"); %></td>
			<td><% bp.getController().writeFormInput(out,"printAnno"); %></td>
		  </tr>
		  <tr>
			<td><% bp.getController().writeFormLabel(out,"printMovimentazione"); %></td>
			<td><% bp.getController().writeFormInput(out,"printMovimentazione"); %></td>
		  </tr>
		  <tr>
			<td><% bp.getController().writeFormLabel(out,"printPianoEconomico"); %></td>
			<td><% bp.getController().writeFormInput(out,"printPianoEconomico"); %></td>
		  </tr>
		 </table>
		 </div>
		</td>
	   </tr>	    
	</table>
   </td>
  </tr>	    
<% if (Boolean.TRUE.equals(((Stampa_situazione_sintetica_x_progettoBulk)bp.getModel()).getPrintAnno()) && 
	   Boolean.TRUE.equals(((Stampa_situazione_sintetica_x_progettoBulk)bp.getModel()).getPrintVoce())) { %>
  <tr>
   <td>	
	<table>  
	  <tr>
	   <td>	
		<div class="Group">
		<table>    	  
		  <tr>
			<td class="GroupLabel">Ordine di Stampa</td>
			<td></td>
		  </tr>	
		  <tr>
			<td><% bp.getController().writeFormInput(out,"ti_ordine_stampa"); %></td>
		  </tr>
		 </table>
		 </div>
		</td>
	   </tr>	    
	</table>
   </td>
  </tr>	    
<% } %>

<% bp.closeFormWindow(pageContext); %>

</body>
</html>