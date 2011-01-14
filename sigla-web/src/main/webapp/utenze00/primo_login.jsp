<%@ page 
	import="it.cnr.jada.util.jsp.*,it.cnr.jada.action.*,java.util.*,it.cnr.jada.util.action.*,it.cnr.contab.utenze00.bp.*,it.cnr.contab.utenze00.bulk.*"
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<% JSPUtils.printBaseUrl(pageContext); %>
<script language="JavaScript" src="scripts/util.js"></script>
<script language="javascript" src="scripts/css.js"></script>
</head>

<title>Primo accesso all'applicazione</title>

<body class="Workspace">

<% 	LoginBP bp = (LoginBP)BusinessProcess.getBusinessProcess(request);
	bp.openForm(pageContext,"Login",null); %>

<table align="center" class="Window">
	<tr>
		<td>
			<table class="Panel">
			  <tr>
				<td rowspan="2"><img src="img/question.gif"></td>
			  	<td>Questo � il primo login.</td>
			  </tr>
			  <tr>
			  	<td>E' necessario assegnare una password</td>
			  </tr>
			</table>
			<table class="Panel" width="100%">
		  	<% bp.getController().writeForm(out,"assegna_password"); %>
			  <tr>
				<td colspan=2 align=center>
				  <input type=submit value="Assegna password" name="comando.doAssegnaPassword">
				</td>
			  </tr>
			</table>
		</td>
	</tr>
</table>

<%	bp.closeFormWindow(pageContext); %>
</body>
</html>