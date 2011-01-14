<!-- 
 ?ResourceName "TemplateForm.jsp"
 ?ResourceTimestamp "08/11/00 16.43.22"
 ?ResourceEdition "1.0"
-->

<%@ page 
	import="it.cnr.jada.util.jsp.*,
	        it.cnr.jada.action.*,
	        java.util.*, 
	        it.cnr.jada.util.action.*,
	        it.cnr.contab.doccont00.bp.*,
	        it.cnr.contab.doccont00.core.bulk.*"
%>


<%  
		CRUDAbstractMandatoBP bp = (CRUDAbstractMandatoBP)BusinessProcess.getBusinessProcess(request);
		it.cnr.contab.doccont00.core.bulk.MandatoBulk mandato = (it.cnr.contab.doccont00.core.bulk.MandatoBulk)bp.getModel();
%>
  <% if ( bp.isInserting() && mandato.getTi_mandato().equals( mandato.TIPO_REGOLARIZZAZIONE ) )
  { %>
  <div class="Group">		
  <table border="0" cellspacing="0" cellpadding="2">
  <tr><td>
	  <table border="0" cellspacing="0" cellpadding="2">	
 	 	<tr>
		  <% if ( mandato.getCd_unita_organizzativa().equals( mandato.getCd_uo_ente() ) )
  	     { %>		  
				<td><% bp.getController().writeFormLabel( out, "pg_accertamento"); %></td>
		  <% } else { %>		  
				<td> <b><font size=2> Accertamento/Ann.di Entrata su P.Giro </font></b></td>
		  <% } %>		  				
			<td><% bp.getController().writeFormInput( out, "esercizio_ori_accertamento"); %></td>
			<td><% bp.getController().writeFormInput( out, "pg_accertamento"); %></td>
			<td><% bp.getController().writeFormInput( out, "ds_accertamento"); %></td>						
    		<td><% bp.getController().writeFormInput( out, "find_accertamento"); %></td>

		</tr>
 	  </table>
  </td></tr>
  <tr><td>
  	  <table border="0" cellspacing="0" cellpadding="2">	
 	 	<tr>
		    <% if ( mandato.getCd_unita_organizzativa().equals( mandato.getCd_uo_ente() ) ) { %>
			<td><% ((CRUDMandatoBP)bp).getDocumentiAttiviPerRegolarizzazione().writeHTMLTable(pageContext,"regolarizzazione",false,false,false,"100%","150px", true); %></td>
		    <% } else { %>  			
			<td><% ((CRUDMandatoBP)bp).getScadenzeAccertamentoPerRegolarizzazione().writeHTMLTable(pageContext,"regolarizzazione",false,false,false,"100%","150px", true); %></td>
		    <% } %>   
	 	</tr>	
	  </table>
  </td></tr>
  </table>
  </div>
  <%} else {%>

	<table border="0" cellspacing="0" cellpadding="2">		
		<tr>
			<td colspan=2>
			      <b ALIGN="CENTER"><font size=2>Doc.contabili associati</font></b>
			      <% bp.getReversaliMan().setEnabled( false );
			         bp.getReversaliMan().writeHTMLTable(pageContext,"doc_cont_coll",false,false,false,"100%","100px", true); %>
			</td>
		</tr>
	</table>

    <% if (mandato != null && mandato instanceof MandatoIBulk && 
           ((MandatoIBulk)mandato).getVar_bilancio() != null){ %>
    <BR>
    <div class="GroupLabel">Variazione al bilancio dell'Ente</div>          
    <div class="Group">
  	   <table>      
	      <tr>
		     <td><% bp.getController().writeFormField(out,"pg_variazione_bilancio");%></td>
 		     <td><% bp.getController().writeFormInput(out,"ds_variazione_bilancio");%></td>         
  	      </tr>
 	   </table>
    </div>
    <%}%>
  <%}%>