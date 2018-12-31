<%@ page import="org.motus.Account" %>
<html lang="en">
<head>
 	<meta name="layout" content="main" />
	<title><g:message code="app.name"  /> : Review Motivator Request</title>
</head>
<body>
  
	<div class="row">
	
		<div class="col-md-5">
		
  			<g:if test="${flash.message}">
    			<div class="alert alert-warning">${flash.message}</div>
  			</g:if>


			<h3>Motivator Request : ${username}</h3>
			<p>${comment}</p>
			<g:form action="approve" method="post">
				<input type="hidden" name="username" value="${username}"/>
				<input type="submit" class="btn btn-info" value="Approve"/>
			</g:form>

			<g:form action="deny" method="post">
				<input type="hidden" name="username" value="${username}"/>
				<input type="submit" class="btn btn-warning" value="Ignore"/>
			</g:form>

			
		</div>
		
  	</div>
  	
</body>
</html>
