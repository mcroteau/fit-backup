<%@ page import="org.motus.Account" %>
<html lang="en">
<head>
 	<meta name="layout" content="main" />
	<title><g:message code="app.name"  /> : Request Submitted</title>
</head>
<body>
  
	<div class="row">
	
		<div class="col-md-5">
		
  			<g:if test="${flash.message}">
    			<div class="alert alert-warning">${flash.message}</div>
  			</g:if>


			<h3>Request to motivate ${username} submitted.</h3>

			
		</div>
		
  	</div>
  	
</body>
</html>
