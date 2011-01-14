<%@ page 
	import="it.cnr.jada.util.jsp.*,
		it.cnr.jada.action.*,
		java.util.*,
		it.cnr.jada.util.action.*,
		it.cnr.contab.docamm00.tabrif.bulk.*,
		it.cnr.contab.docamm00.intrastat.bulk.*,
		it.cnr.contab.docamm00.docs.bulk.*,
		it.cnr.contab.doccont00.core.bulk.*,
		it.cnr.contab.docamm00.bp.*"
%>

<%	CRUDFatturaAttivaBP bp = (CRUDFatturaAttivaBP)BusinessProcess.getBusinessProcess(request);
	Fattura_attivaBulk fattura  = (Fattura_attivaBulk)bp.getModel();
	Fattura_attiva_intraBulk intra=null; 
	boolean noninviato=true;	
 	if(bp.getDettaglioIntrastatController().getModel()!=null){
		intra=(Fattura_attiva_intraBulk)bp.getDettaglioIntrastatController().getModel();
	 	if(intra!=null&& intra.getFl_inviato()!=null) 
			noninviato=!(intra.getFl_inviato().booleanValue());
		if(intra!=null && intra.getFl_inviato()!=null && 
		  intra.getFl_inviato().booleanValue() && intra.getNr_progressivo()==null)
			noninviato=false;
		else if(intra!=null && intra.getFl_inviato()!=null && 
		  intra.getFl_inviato().booleanValue() && intra.getNr_progressivo()!=null)
			noninviato=true;
	}
%>
	<% if (fattura!=null && fattura.getTi_bene_servizio()!=null && fattura.getTi_bene_servizio().compareTo(Fattura_passivaBulk.FATTURA_DI_BENI)==0) { %>
	<div class="Group" style="width:100%">
		<table width="100%">
			<tr>
			  	<td colspan="2">
				  	<span class="FormLabel" style="color:black">Dettagli intrastat</span>
			  	</td>
			</tr>
			<tr>
			  	<td colspan="2">
					<%	bp.getDettaglioIntrastatController().writeHTMLTable(pageContext,"BeneSet",true,false,noninviato,"100%","100px"); %>
			  	</td>
			</tr>
		</table>
	</div>
	<div class="Group" style="width:100%">
		<table width="100%">
			<tr>      			
				<td>
					<% bp.getDettaglioIntrastatController().writeFormLabel(out,"esercizio"); %>
				</td>
				<td colspan="5">
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"esercizio"); %>
				</td>
			</tr>
			<tr>      			
				<td>
					<% bp.getDettaglioIntrastatController().writeFormLabel(out,"ds_bene"); %>
				</td>
				<td colspan="5">
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"ds_bene"); %>
				</td>
			</tr>
			<tr>      			
				<% bp.getDettaglioIntrastatController().writeFormField(out,"cd_natura_transazione"); %>
				<td>
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"ds_natura_transazione"); %>
				</td>
				<td>
					<% bp.getDettaglioIntrastatController().writeFormInput(out, "natura_transazione"); %>
				</td>
				<% bp.getDettaglioIntrastatController().writeFormField(out,"modalita_trasporto"); %>
			</tr>
			<tr>
				<td>
					<% bp.getDettaglioIntrastatController().writeFormLabel(out,"pg_nazione_destinazione"); %>
				</td>
				<td>
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"cd_iso_nazione_destinazione"); %>
				<td>
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"ds_nazione_destinazione"); %>
				</td>
				<td>
					<% bp.getDettaglioIntrastatController().writeFormInput(out, "nazione_destinazione"); %>
				</td>
				<% bp.getDettaglioIntrastatController().writeFormField(out,"condizione_consegna"); %>
			</tr> 
			<tr>      			
				<% bp.getDettaglioIntrastatController().writeFormField(out,"cd_provincia_origine"); %>
				<td>
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"ds_provincia_origine"); %>
				</td>
				<td>
					<% bp.getDettaglioIntrastatController().writeFormInput(out, "provincia_origine"); %>
				</td>
			</tr>
			<tr>
				<% bp.getDettaglioIntrastatController().writeFormField(out,"cd_nomenclatura_combinata"); %>
				<td>
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"ds_nomenclatura_combinata"); %>
				</td>
				<td>
					<% bp.getDettaglioIntrastatController().writeFormInput(out, "nomenclatura_combinata"); %>
				</td>
			</tr>
			<tr>      			
				<td>
					<% bp.getDettaglioIntrastatController().writeFormLabel(out,"unita_supplementari"); %>
				</td>
				<td colspan="3">
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"unita_supplementari"); %>
				</td>
					<td>
					<% bp.getDettaglioIntrastatController().writeFormLabel(out,"valore_statistico"); %>
				</td>
				<td colspan="3">
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"valore_statistico"); %>
				</td>
			</tr>
			<tr>      			
			   <td>
					<% bp.getDettaglioIntrastatController().writeFormLabel(out,"ammontare_euro"); %>
				</td>
				<td colspan="3">
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"ammontare_euro"); %>
				</td>
			</tr>
			<tr>      			
				<td>
					<% bp.getDettaglioIntrastatController().writeFormLabel(out,"massa_netta"); %>
				</td>
				<td colspan="3">
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"massa_netta"); %>
				</td>
			</tr>
		</table>
	</div>
	<% } else { %>
	<div class="Group" style="width:100%">
		<table width="100%">
			<tr>
			  	<td colspan="2">
				  	<span class="FormLabel" style="color:black">Dettagli intrastat</span>
			  	</td>
			</tr>
			<tr>
			  	<td colspan="2">
					<%	bp.getDettaglioIntrastatController().writeHTMLTable(pageContext,"ServizioSet",true,false,noninviato,"100%","100px"); %>
			  	</td>
			</tr>
		</table>
	</div>
	<div class="Group" style="width:100%">
		<table width="100%">
			<tr>      			
				<td>
					<% bp.getDettaglioIntrastatController().writeFormLabel(out,"esercizio"); %>
				</td>
				<td>
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"esercizio"); %>
				</td>
			</tr>
			<tr>      			
				<td>
					<% bp.getDettaglioIntrastatController().writeFormLabel(out,"ds_bene"); %>
				</td>
				<td colspan="5">
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"ds_bene"); %>
				</td>
			</tr>
			<tr>
				<td>      			
					<% bp.getDettaglioIntrastatController().writeFormLabel(out,"modalita_incasso"); %>
				</td>
				<td>      			
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"modalita_incasso"); %>
				</td>
			</tr>
			<tr>
				<td>      			
					<% bp.getDettaglioIntrastatController().writeFormLabel(out,"modalita_erogazione"); %>
				</td>
				<td>      			
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"modalita_erogazione"); %>
				</td>			
			</tr>
			<tr>
				<td>
					<% bp.getDettaglioIntrastatController().writeFormLabel(out,"cd_cpa"); %>
				</td>
				<td>
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"cd_cpa"); %>
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"ds_cpa"); %>
					<% bp.getDettaglioIntrastatController().writeFormInput(out, "codici_cpa"); %>
				</td>
			</tr>
			<tr>
				<td>
					<% bp.getDettaglioIntrastatController().writeFormLabel(out,"cd_iso_nazione_destinazione"); %>
				</td>
				<td>
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"cd_iso_nazione_destinazione"); %>
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"ds_nazione_destinazione"); %>
					<% bp.getDettaglioIntrastatController().writeFormInput(out, "nazione_destinazione"); %>
				</td>
			</tr> 
			<tr>      			
				<td>
					<% bp.getDettaglioIntrastatController().writeFormLabel(out,"ammontare_euro"); %>
				</td>
				<td>
					<% bp.getDettaglioIntrastatController().writeFormInput(out,"ammontare_euro"); %>
				</td>
			</tr> 
			
		</table>
	</div>	
	<%}%>