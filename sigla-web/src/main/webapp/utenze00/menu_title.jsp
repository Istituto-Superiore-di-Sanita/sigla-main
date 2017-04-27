<%@ page 
	import="it.cnr.jada.util.jsp.*,it.cnr.jada.action.*,java.util.*,it.cnr.jada.util.action.*,it.cnr.contab.utenze00.bp.*,it.cnr.contab.utenze00.bulk.*"
%>
<%	GestioneUtenteBP bp = (GestioneUtenteBP)BusinessProcess.getBusinessProcess(request,"/GestioneUtenteBP"); %>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<% JSPUtils.printBaseUrl(pageContext); %>
<script language="javascript" src="scripts/css.js"></script>
<script language="JavaScript" src="scripts/util.js"></script>
<script>
function selezionaUnitaOrganizzativa() {
	var frame = window.parent.frames['workspace'];
	if (frame && frame.document.mainForm && frame.document.mainForm.comando && !frame.document.mainForm.submitted) {
		_submitForm(frame.document.mainForm,'doCambiaUnitaOrganizzativa',true,"GestioneMenu.do");
	} else {
		document.mainForm.target = "workspace";
		_submitForm(document.mainForm,'doCambiaUnitaOrganizzativa',false);
	}
}

function selezionaListaUnitaOrganizzativa() {
	var frame = window.parent.frames['workspace'];
	if (frame && frame.document.mainForm && frame.document.mainForm.comando && !frame.document.mainForm.submitted) {
		_submitForm(frame.document.mainForm,'doListaUnitaOrganizzativa',true,"GestioneMenu.do");
	} else {
		document.mainForm.target = "workspace";
		_submitForm(document.mainForm,'doListaUnitaOrganizzativa',false);
	}
}

function selezionaCdr() {
	var frame = window.parent.frames['workspace'];
	if (frame && frame.document.mainForm && frame.document.mainForm.comando && !frame.document.mainForm.submitted) {
		_submitForm(frame.document.mainForm,'doCambiaCdr',true,"GestioneMenu.do");
	} else {
		document.mainForm.target = "workspace";
		_submitForm(document.mainForm,'doCambiaCdr',false);
	}
}

function apriListaMessaggi() {
	window.MessageWindow = showPopup('', 'messaggi', 'dependent,menubar=no,toolbar=no,location=no,scrollbar=yes,resizable=yes,width=640,height=480');
	document.mainForm.target = "messaggi";
	_submitForm(document.mainForm,'doApriListaMessaggi',false);
	document.mainForm.target = "_self";
	setMessage("img/spacer.gif",null);
}

function startCheckmessage() {
	startCheckmessageOnlyIf(true)
}

function startCheckmessageOnlyIf(check) {
	if (check)
		window.top.MessageCheckFrame.location="<%= it.cnr.jada.util.Config.getHandler().getProperty("Message_check.url")%>?user=<%= bp.getUserInfo().getUserid()%>&server_url=<%= it.cnr.jada.util.jsp.JSPUtils.getAppServerUrl(pageContext)%>"
}

if (window.top.MessageCheckFrame) {
	startCheckmessage()
	window.top.MenuTitleFrame = window
	window.top.MenuTitleFrame.apriListaMessaggi = apriListaMessaggi
}

</script>
</head>
<body bgcolor="#89aed4" class="ColoredFrame">
<%
 String fLogo = null;
  if (bp.getUserInfo().getLogo()!=null) 
    fLogo = "img/"+bp.getUserInfo().getLogo();
  else
    fLogo = "img/logo_mini_dummy.gif";
%>
<form name="mainForm" action="GestioneMenu.do" method=post onSubmit="return disableDblClick()">
<input type=hidden name="comando">
<% JSPUtils.scrollSupport(pageContext); %>
<% BusinessProcess.encode(bp,pageContext); %>
<% bp.writeMessage(out); %>

<table>

 <tr>
     <td width="100%" colspan=3><img src="<%=fLogo%>">
     <% if(bp.getUserInfo().getTipo_db() != null && !(bp.getUserInfo().getTipo_db().equals(it.cnr.contab.config00.bulk.Parametri_enteBulk.DB_PRODUZIONE)) &&
                                                    !(bp.getUserInfo().getTipo_db().equals(it.cnr.contab.config00.bulk.Parametri_enteBulk.DB_ALTRO))) {%>
       <span class="FormLabel"><%=" al "+new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(it.cnr.jada.util.ejb.EJBCommonServices.getDateRefreshDB())%></span>
     <%}%>  
     </td>
 </tr>

<tr>
	<td><%	JSPUtils.buttonWithTitle(out,"img/home16.gif","javascript:doLogout()","Logout", bp.getParentRoot().isBootstrap()); %></td>
	<td><% bp.getUserInfo().writeFormLabel(out,"scelta_esercizio_uo","userid"); %></td>
	<td align=left><% bp.getUserInfo().writeFormInput(out,"scelta_esercizio_uo","userid",false,null,"onchange=\"javascript:submitForm('doSelezionaEsercizio')\" style=\"width:100%;background-color:transparent;\"",null,FormController.EDIT,bp.getFieldValidationMap(), false); %></td>
</tr>
<% if (bp.getUserInfo().getLdap_userid()!=null && !bp.getUserInfo().getLdap_userid().equals(bp.getUserInfo().getUserid())) {%>
<tr>
	<td><% bp.getUserInfo().writeFormLabel(out,"scelta_esercizio_uo","ldap_userid"); %></td>
	<td width="100%" colspan=2><% bp.getUserInfo().writeFormInput(out,"scelta_esercizio_uo","ldap_userid",false,null,"onchange=\"javascript:submitForm('doSelezionaEsercizio')\" style=\"width:100%;background-color:transparent;\"",null,FormController.EDIT,bp.getFieldValidationMap(), false); %></td>
</tr>
<%}%>  
<tr>
	<% if (bp.getUserInfo().getUtente().isSupervisore()) { %>
		<td><%	JSPUtils.buttonWithTitle(out,"img/find16.gif","javascript:selezionaListaUnitaOrganizzativa()","Lista Unit� Organizzative", bp.getParentRoot().isBootstrap()); %></td>
	<% } else { %>
		<td></td>
	<% } %>
	<td><% bp.getUserInfo().writeFormLabel(out,"scelta_esercizio_uo","esercizio"); %></td>
	<td align=left><% bp.getUserInfo().writeFormInput(out,"scelta_esercizio_uo","esercizio",bp.getUserInfo().getUtente().isUtenteComune(),null,"onchange=\"javascript:submitForm('doSelezionaEsercizio')\"",null,FormController.EDIT,bp.getFieldValidationMap(), false); %></td>
</tr>
<% if (bp.getUserInfo().getUtente().isUtenteComune()) { %>
<tr>
	<% if (bp.getUserInfo().getUtente().isSupervisore()) { %>
		<td><% bp.getUserInfo().writeFormLabel(out,"scelta_esercizio_uo","ds_cdr"); %></td>
	<% } else { %>
		<td><%	JSPUtils.buttonWithTitle(out,"img/find16.gif","javascript:selezionaCdr()","Lista Cdr", bp.getParentRoot().isBootstrap()); %></td>
	<% } %>
	<td><% bp.getUserInfo().writeFormInput(out,"scelta_esercizio_uo","cd_centro_responsabilita",false,null,"onchange=\"javascript:submitForm('doSelezionaEsercizio')\"",null,FormController.EDIT,bp.getFieldValidationMap(), false); %></td>
	<td align=left><% bp.getUserInfo().writeFormInput(out,"scelta_esercizio_uo","ds_cdr",false,null,"onchange=\"javascript:submitForm('doSelezionaEsercizio')\"",null,FormController.EDIT,bp.getFieldValidationMap(), false); %></td>
</tr>
<% if (bp.getUserInfo().getUtente().getDipartimento() != null && bp.getUserInfo().getUtente().getDipartimento().getCd_dipartimento()!= null) { %>
<tr>
	<td><% bp.getUserInfo().writeFormLabel(out,"scelta_esercizio_uo","ds_dipartimento"); %></td>
	<td><% bp.getUserInfo().writeFormInput(out,"scelta_esercizio_uo","cd_dipartimento",false,null,"onchange=\"javascript:submitForm('doSelezionaEsercizio')\"",null,FormController.EDIT,bp.getFieldValidationMap(), false); %></td>
	<td align=left><% bp.getUserInfo().writeFormInput(out,"scelta_esercizio_uo","ds_dipartimento",false,null,"onchange=\"javascript:submitForm('doSelezionaEsercizio')\"",null,FormController.EDIT,bp.getFieldValidationMap(), false); %></td>
</tr>
<% } %>
<tr>
	<td><%	JSPUtils.buttonWithTitle(out,"img/find16.gif","javascript:selezionaUnitaOrganizzativa()","Cambia Unit� Organizzativa", bp.getParentRoot().isBootstrap()); %></td>
	<td><% bp.getUserInfo().writeFormInput(out,"scelta_esercizio_uo","cd_unita_organizzativa",false,null,"onchange=\"javascript:submitForm('doSelezionaEsercizio')\"",null,FormController.EDIT,bp.getFieldValidationMap(), false); %></td>
	<td align=left><% bp.getUserInfo().writeFormInput(out,"scelta_esercizio_uo","ds_unita_organizzativa",false,null,"onchange=\"javascript:submitForm('doSelezionaEsercizio')\"",null,FormController.EDIT,bp.getFieldValidationMap(), false); %></td>
</tr>
<% } %>
<tr>
	<td colspan="3"><span id="rotext" class="FormInput" style="width:7em; heigth: 2em;"><%= request.getSession().getId()%></span>
</tr>
</table>
</form>
</body>

</html>