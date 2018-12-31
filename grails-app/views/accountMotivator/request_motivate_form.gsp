<%@ page import="org.motus.Account" %>
<html lang="en">
<head>
 	<meta name="layout" content="main" />
	<title><g:message code="app.name"  /> : Request to Motivate</title>
</head>
<body>
  
	<div class="row">
	
		<div class="col-md-5">
		
  			<g:if test="${flash.message}">
    			<div class="alert alert-warning">${flash.message}</div>
  			</g:if>

			<g:form action="request_motivate" method="post">
				<p>Request to motivate : ${username}</p>
				<input type="hidden" name="username" value="${username}"/>
				<textarea name="comment"></textarea>
				<input type="submit" class="btn btn-info" value="Request Motivate"/>
			</g:form>
			
		</div>
		
  	</div>
  	
</body>
</html>
