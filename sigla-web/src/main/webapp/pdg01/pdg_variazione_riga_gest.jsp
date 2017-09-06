<%@page import="it.cnr.contab.pdg00.bp.PdGVariazioneBP"%>
<head>
<%
	CRUDPdgVariazioneRigaGestBP bp = (CRUDPdgVariazioneRigaGestBP)BusinessProcess.getBusinessProcess(request);
	Pdg_variazione_riga_gestBulk rigaVariazione = (Pdg_variazione_riga_gestBulk)bp.getRigheVariazioneGestionale().getModel();
	Ass_pdg_variazione_cdrBulk bulk = (Ass_pdg_variazione_cdrBulk)bp.getModel();
%>
<title><%=bp.getTitle()%></title>

<script language="JavaScript" src="scripts/util.js"></script>
<script language="javascript" src="scripts/css.js"></script>
</head>
<%@ page 
	import="it.cnr.jada.util.jsp.*,
		it.cnr.jada.action.*,
		it.cnr.jada.bulk.*,
		it.cnr.jada.util.action.*,
		it.cnr.contab.pdg00.cdip.bulk.*,
		it.cnr.contab.pdg01.bp.*,
		it.cnr.contab.pdg01.bulk.*"
%>
<body class="Form">
<%
	bp.openFormWindow(pageContext);
	boolean isDettaglioGestionaleEnable = !bp.isDettaglioGestionaleEnable(rigaVariazione);
%>
<table>
    <tr>
        <td><% bp.getController().writeFormLabel(out,"esercizio");%></TD>
        <td><% bp.getController().writeFormInput(out,null,"esercizio",true,null,null);%></td>
        <td><% bp.getController().writeFormLabel(out,"pg_variazione_pdg");%></TD>
        <td><% bp.getController().writeFormInput(out,null,"pg_variazione_pdg",true,null,null);%></td>
    </tr>
	<tr>
		<td><% bp.getController().writeFormLabel(out,"cd_centro_responsabilita"); %></td>
	 	<td colspan=3>
	 		<% bp.getController().writeFormInput(out,"cd_centro_responsabilita"); %>
			<% bp.getController().writeFormInput(out,"ds_centro_responsabilita"); %>
		</td>	
		<td><% bp.getController().writeFormLabel(out,"totale_quota_spesa"); %></td>
		<td><% bp.getController().writeFormInput(out,"totale_quota_spesa"); %></td>
	</tr>
</table>
<table class="Panel" cellspacing=2 align=right>
	<tr>
		<td><% bp.getController().writeFormLabel(out,"totale_quota_spesa_ripartita"); %></td>
		<td><% bp.getController().writeFormInput(out,"totale_quota_spesa_ripartita"); %></td>
	</tr>
</table>
<table>
	  <tr>
	  	<td>
		  <% 
		    if (bp.getParent() instanceof CRUDPdgVariazioneGestionaleBP && ((CRUDPdgVariazioneGestionaleBP)bp.getParent()).isUoEnte() &&
	        	bulk.getPdg_variazione().isApprovata())
			  	bp.getRigheVariazioneGestionale().writeHTMLTable(
					pageContext,
					"dipInsertVariazioneGestionale"+(bp.getParametriCnr().getFl_nuovo_pdg()?"WithoutArea":""),	
					true,
					true,
					!isDettaglioGestionaleEnable,
					"900px",
					"150px",
					true);
			else
			  	bp.getRigheVariazioneGestionale().writeHTMLTable(
					pageContext,
					"insertVariazioneGestionale"+(bp.getParametriCnr().getFl_nuovo_pdg()?"WithoutArea":""),	
					true,
					true,
					!isDettaglioGestionaleEnable,
					"900px",
					"150px",
					true); 
			%>
		</td>
	  </tr>
</table>
<table> 
	<tr>
		<%	if (bp.getParametriCnr().getFl_nuovo_pdg()) 
				bp.getRigheVariazioneGestionale().writeFormField(out,null,"searchtool_progetto_liv2",1,4);
			else
				bp.getRigheVariazioneGestionale().writeFormField(out,null,"searchtool_progetto",1,4);
		%>
	</tr>
	<tr>
		<%	if (bp.getParametriCnr().getFl_nuovo_pdg()) { %>   
			<td><% bp.getRigheVariazioneGestionale().writeFormLabel(out,"find_linea_attivita_liv2");%></td>
			<td colspan="4">
				<table>
					<tr>
						<td><% bp.getRigheVariazioneGestionale().writeFormInput(out,null,"find_linea_attivita_liv2",isDettaglioGestionaleEnable,null,null);%></td>
						<td><% bp.getRigheVariazioneGestionale().writeFormInput( out,"default","crea_linea_attivita",isDettaglioGestionaleEnable,null,null);%></td>
					</tr>
				</table>
			</td>
		<% } else { %> 
			<td><% bp.getRigheVariazioneGestionale().writeFormLabel(out,"find_linea_attivita");%></td>
			<td colspan="4">
				<table>
					<tr>
						<td><% bp.getRigheVariazioneGestionale().writeFormInput(out,null,"find_linea_attivita",isDettaglioGestionaleEnable,null,null);%></td>
						<td><% bp.getRigheVariazioneGestionale().writeFormInput( out,"default","crea_linea_attivita",isDettaglioGestionaleEnable,null,null);%></td>
					</tr>
				</table>
			</td>
		<% } %>
	</tr>
	<%	if (bp.getParametriCnr().getFl_nuovo_pdg().booleanValue()) { %> 
			<tr>   
			<td><% bp.getRigheVariazioneGestionale().writeFormLabel(out,"missione");%></td>
			<td><% bp.getRigheVariazioneGestionale().writeFormInput(out,"missione");%></td>
			<td><% bp.getRigheVariazioneGestionale().writeFormField(out,"programma");%></td>
			</tr>
		<% } %>
	<tr> 
		<td><% bp.getRigheVariazioneGestionale().writeFormLabel(out,"find_elemento_voce");%></td>
		<td colspan=4><% bp.getRigheVariazioneGestionale().writeFormInput(out,null,"find_elemento_voce",(isDettaglioGestionaleEnable || bulk.getPdg_variazione().isApprovata()),null,null);%></td>
	</tr>
<% 	if (!bp.isUoArea() && !bp.getParametriCnr().getFl_nuovo_pdg()) { %> 
	<tr>
		<td><% bp.getRigheVariazioneGestionale().writeFormLabel(out,"searchtool_area");%></td>
		<td colspan=4><% bp.getRigheVariazioneGestionale().writeFormInput(out,"searchtool_area");%></td>
	</tr>
<% 	} %>
<% 	if (bp.isGestioneSpesa()) { %>
	<tr>
		<td><% bp.getRigheVariazioneGestionale().writeFormLabel(out,"im_spese_gest_decentrata_int");%></td>
		<td><% bp.getRigheVariazioneGestionale().writeFormInput(out,null,"im_spese_gest_decentrata_int",isDettaglioGestionaleEnable,null,null);%></td>
		<td><% bp.getRigheVariazioneGestionale().writeFormLabel(out,"im_spese_gest_accentrata_int");%></td>
		<td><% bp.getRigheVariazioneGestionale().writeFormInput(out,null,"im_spese_gest_accentrata_int",isDettaglioGestionaleEnable,null,null);%></td>
	</tr>
	<tr>
		<td><% bp.getRigheVariazioneGestionale().writeFormLabel(out,"im_spese_gest_decentrata_est");%></td>
		<td><% bp.getRigheVariazioneGestionale().writeFormInput(out,null,"im_spese_gest_decentrata_est",isDettaglioGestionaleEnable,null,null);%></td>
		<td><% bp.getRigheVariazioneGestionale().writeFormLabel(out,"im_spese_gest_accentrata_est");%></td>
		<td><% bp.getRigheVariazioneGestionale().writeFormInput(out,null,"im_spese_gest_accentrata_est",isDettaglioGestionaleEnable,null,null);%></td>
	</tr>
<% 	} else { %>
	<tr>
		<td><% bp.getRigheVariazioneGestionale().writeFormLabel(out,"im_entrata");%></td>
		<td><% bp.getRigheVariazioneGestionale().writeFormInput(out,null,"im_entrata",isDettaglioGestionaleEnable,null,null);%></td>
	</tr>
<% 	} %>
	<tr>
		<td><% bp.getRigheVariazioneGestionale().writeFormLabel(out,"descrizione");%></td>
		<td colspan=3><% bp.getRigheVariazioneGestionale().writeFormInput(out,null,"descrizione",isDettaglioGestionaleEnable,null,null);%></td>
	</tr>
</table>
<%	bp.closeFormWindow(pageContext); %>
</body>