<%@ page 
	import="it.cnr.jada.action.*,
		it.cnr.jada.util.action.*,
		it.cnr.contab.incarichi00.bulk.*,
		it.cnr.contab.incarichi00.bp.*"
%>
<%
	CRUDIncarichiProceduraBP bp = (CRUDIncarichiProceduraBP)BusinessProcess.getBusinessProcess(request);
	SimpleDetailCRUDController controller = bp.getCrudIncarichiArchivioAllegati();
	Incarichi_repertorioBulk incarico = (Incarichi_repertorioBulk)bp.getIncarichiColl().getModel();

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
	    
	controller.writeHTMLTable(pageContext,"archivioAllegati",incarico!=null&&!incarico.isROIncaricoDefinitivo()&&!bp.isSearching(),false,incarico!=null&&!incarico.isROIncaricoDefinitivo()&&!bp.isSearching(),"100%","100px"); 

	boolean isRODettaglio = false;
	if (allegato!=null && ((allegato.isContratto() && (bp.isSuperUtente() || bp.isUtenteAbilitatoModificaAllegatoContratto())) ||
						   (allegato.isAllegatoGenerico() && bp.isSuperUtente()))) {
		isRODettaglio = incarico==null||allegato==null||!allegato.isToBeCreated()||
						!allegato.isAllegatoValido()||
						incarico.getIncarichi_procedura().getFaseProcesso().compareTo(Incarichi_proceduraBulk.FASE_PUBBLICAZIONE)==0||
						(incarico.getIncarichi_procedura().getFaseProcesso().compareTo(Incarichi_proceduraBulk.FASE_INSERIMENTO_CONTRATTO)==0 &&
						 !allegato.isToBeCreated());
	} else {
		isRODettaglio = incarico==null||allegato==null||!allegato.isToBeCreated()||
					    incarico.isROIncaricoDefinitivo()||
			    		!allegato.isAllegatoValido()||
			    		incarico.getIncarichi_procedura().getFaseProcesso().compareTo(Incarichi_proceduraBulk.FASE_PUBBLICAZIONE)==0||
						(incarico.getIncarichi_procedura().getFaseProcesso().compareTo(Incarichi_proceduraBulk.FASE_INSERIMENTO_CONTRATTO)==0 &&
						 !allegato.isToBeCreated()) ||
						(incarico.isIncaricoInviatoCorteConti() && 
						 !allegato.isAttoEsitoControllo() && !allegato.isAllegatoGenerico());
	}
%>

<script language="JavaScript">
function doScaricaFile() {	
   larghFinestra=5;
   altezFinestra=5;
   sinistra=(screen.width)/2;
   alto=(screen.height)/2;
   window.open("<%= (allegato==null?null:allegato.getDownloadUrl()) %>","DOWNLOAD","left="+sinistra+",top="+alto+",width="+larghFinestra+", height="+altezFinestra+",menubar=no,toolbar=no,location=no")
}
</script>

<table class="Panel" cellspacing=2>
	<tr>
        <td><% controller.writeFormLabel(out,"default","tipo_archivio"); %></td>
        <td colspan=4><% controller.writeFormInput(out,"default","tipo_archivio", true,"FormInput",null); %></td>
	</tr>
    <% if (allegato==null || allegato.getTipo_archivio()!=null) {%>
	    <% if (allegato!=null && allegato.getTipo_archivio()!=null &&
	    	  (allegato.isContratto() || allegato.isAllegatoDaPubblicare())) {%>
		<tr>
			<td colspan=5>
			<div class="Group"><table>
				<% if (allegato.isContratto()) { %>
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
 				<% if (allegato.isContratto() || allegato.isAllegatoDaPubblicare()) { %>
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

		<% if (!bp.isSearching() && !isRODettaglio) { %>
		<tr>
	        <td><% controller.writeFormLabel(out,"default","blob"); %></td>
	        <td colspan=4><% controller.writeFormInput(out,"default","blob"); %></td>
	    </tr>
		<% } %>
		<tr>
	        <td><% controller.writeFormLabel(out,"default","ds_file"); %></td>
	        <td colspan=4><% controller.writeFormInput(out,"default","ds_file", isRODettaglio,"FormInput",null); %></td>
		</tr>
		<tr>
	        <td><% controller.writeFormLabel(out,"default","nome_file"); %></td>
	        <td><% controller.writeFormInput(out,"default","nome_file"); %>
		<% if (allegato!=null && !allegato.isToBeCreated()) {
				controller.writeFormField(out,"default","attivaFile_blob");
		 } %>
			</td>
		</tr>
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