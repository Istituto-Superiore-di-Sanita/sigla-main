<%@ page 
	import="it.cnr.jada.action.*,
		it.cnr.jada.util.action.*,
		it.cnr.contab.incarichi00.bulk.*,
		it.cnr.contab.incarichi00.bp.*"
%>
<%
	CRUDIncarichiProceduraBP bp = (CRUDIncarichiProceduraBP)BusinessProcess.getBusinessProcess(request);
	Incarichi_proceduraBulk procedura = (Incarichi_proceduraBulk)bp.getModel();

	boolean multi_incarico = false;

	if (procedura!=null && procedura.getNr_contratti()!=null && procedura.getNr_contratti().compareTo(new Integer(1))==1)
		multi_incarico=true;

	SimpleDetailCRUDController controller = multi_incarico?bp.getCrudArchivioAllegati():bp.getCrudArchivioAllegatiMI();

	Incarichi_archivioBulk allegato;

	// Recupero il valore (posizione) del record selezionato
	int  sel = controller.getSelection().getFocus();
	
	/*
	** Quando navigo la prima volta nella tab e non ci sono 
	** record selezionati, il valore restituito � -1. 
	** In questo caso imposto il valore a 0.
	*/
	if (sel == -1)
	   allegato = null;
	else
	   allegato = (Incarichi_archivioBulk)controller.getModel();	   
    
	controller.writeHTMLTable(pageContext,"archivioAllegati",!bp.isSearching(),false,!bp.isSearching(),"100%","100px"); 
    
	boolean isRODettaglio = false;
	if (allegato!=null && ((allegato.isContratto() && (bp.isSuperUtente() || bp.isUtenteAbilitatoModificaAllegatoContratto())) ||
						   (allegato.isAllegatoGenerico() && bp.isSuperUtente()) ||
						   (allegato.isCurriculumVincitore() || allegato.isProgetto()))) {
		isRODettaglio = procedura==null||allegato==null||!allegato.isToBeCreated()||
						!allegato.isAllegatoValido()||
						procedura.getFaseProcesso().compareTo(Incarichi_proceduraBulk.FASE_PUBBLICAZIONE)==0||
						(procedura.getFaseProcesso().compareTo(Incarichi_proceduraBulk.FASE_INSERIMENTO_CONTRATTO)==0 &&
	 					!allegato.isToBeCreated());
	} else {
		isRODettaglio = procedura==null||allegato==null||!allegato.isToBeCreated()||
				        procedura.isROProcedura()||
						!allegato.isAllegatoValido()||
						procedura.getFaseProcesso().compareTo(Incarichi_proceduraBulk.FASE_PUBBLICAZIONE)==0||
						(procedura.getFaseProcesso().compareTo(Incarichi_proceduraBulk.FASE_INSERIMENTO_CONTRATTO)==0 &&
		 				!allegato.isToBeCreated()) ||
						(procedura.isProceduraInviataCorteConti() && 
		 				!allegato.isAttoEsitoControllo() && !allegato.isAllegatoGenerico());
	}

	boolean isFileDaAllegare = allegato!=null && allegato.isFileRequired();
	boolean isUrlDaIndicare = allegato!=null && allegato.isUrlRequired();
%>

<script language="JavaScript">
function doScaricaFile() {	
	doPrint('<%=(allegato==null?null:allegato.getDownloadUrl().replace("'", "_"))%>');
}
</script>

<table class="Panel" cellspacing=2>
	<tr>
        <td><% controller.writeFormLabel(out,"tipo_archivio"); %></td>
        <td colspan=4><% controller.writeFormInput(out,"tipo_archivio"); %></td>
	</tr>
    <% if (allegato==null || allegato.getTipo_archivio()!=null) {%>
	    <% if (allegato!=null && allegato.getTipo_archivio()!=null &&
	    	  (allegato.isBando() || allegato.isContratto() || allegato.isCurriculumVincitore())) {%>
		<tr>
			<td colspan=5>
			<div class="Group"><table>
				<% if (allegato.isContratto() || allegato.isCurriculumVincitore()) { %>
				<tr><td valign=top>
			    	<span class="FormLabel" style="color:red">Attenzione:</span>
			    </td>
			    <td valign=top>
			    	<span class="FormLabel">
					al fine di rispettare le norme in materia di tutela dei dati personali, <br>
					prima di allegare il file del contratto da pubblicare sul sito internet istituzionale del CNR <br>
					e' necessario verificare che lo stesso esponga <b><i><u>esclusivamente</u></i></b> i seguenti dati personali: <br>
					<b><i>Nome, Cognome, Luogo e Data di nascita, Codice Fiscale</i></b> <br>
					Ogni altro dato personale dovr� essere <b><i>"<u>oscurato</u>"</i></b><br><br>
					</span>
				</td></tr>
				<% } %>
 				<% if (allegato.isBando() || allegato.isContratto() || allegato.isCurriculumVincitore()) { %>
				<tr><td valign=top>
			    	<span class="FormLabel" style="color:red">Attenzione:</span>
			    </td>
			    <td valign=top>
			    	<span class="FormLabel">
					ai fini della pubblicazione sul sito internet istituzionale del CNR, <i><u>si raccomanda di usare file in formato PDF</u></i><br> 
					e di <i><u>controllare sempre</u></i>, dopo il salvataggio, la leggibilit� dell'allegato utilizzando il bottone "Apri file"<br>
					</span>
				</td></tr>
				<% } %>
			</table></div>
			</td>
		</tr>
		<% } %>

		<% if (!bp.isSearching() && !isRODettaglio && isFileDaAllegare) { %>
			<tr>
		        <td><% controller.writeFormLabel(out,"default","blob"); %></td>
		        <td colspan=4><% controller.writeFormInput(out,"default","blob"); %></td>
		    </tr>
		<% } %>
		<tr>
	        <td><% controller.writeFormLabel(out,"default","ds_file"); %></td>
	        <td colspan=4><% controller.writeFormInput(out,"default","ds_file", isRODettaglio,"FormInput",null); %></td>
		</tr>
		<% if (isFileDaAllegare) { %>
			<tr>
		        <td><% controller.writeFormLabel(out,"default","nome_file"); %></td>
		        <td><% controller.writeFormInput(out,"default","nome_file"); %>
			<% if (allegato!=null && !allegato.isToBeCreated()) {
					controller.writeFormField(out,"default","attivaFile_blob");
			 } %>
				</td>
			</tr>
		<% } %>
		<% if (isUrlDaIndicare) { %>
			<tr>
		        <td><% controller.writeFormLabel(out,"default","url_file"); %></td>
		        <td colspan=4><% controller.writeFormInput(out,"default","url_file", isRODettaglio,"FormInput",null); %></td>
			</tr>
		<% } %>
		<tr>
	  	    <td><% controller.writeFormLabel(out,"default","utcr"); %></td>
			<td colspan=4><% controller.writeFormInput(out,"default","utcr",true,"FormInput",null); %></td>
		</tr>
		<tr>
	  	    <td><% controller.writeFormLabel(out,"default","data_creazione"); %></td>
			<td colspan=4><% controller.writeFormInput(out,"default","data_creazione",true,"FormInput",null); %></td>
		</tr>
	<% } %>
</table>