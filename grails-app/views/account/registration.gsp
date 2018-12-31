<%@ page import="org.motus.Account" %>
<html lang="en">
<head>
 	<meta name="layout" content="main" />
	<title><g:message code="app.name"  /> : Register</title>
</head>
<body>
  
	<div class="row">
	
		<div class="col-md-5">
		
  			<g:if test="${flash.message}">
    			<div class="alert alert-warning">${flash.message}</div>
  			</g:if>

			<g:hasErrors bean="${accountInstance}">
				<div class="alert alert-danger">
				<ul class="errors" role="alert">
					<g:eachError bean="${accountInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
					</g:eachError>
				</ul>
				</div>
			</g:hasErrors>
			
			<g:form action="register" class="form">
				<div class="form-group">
				  	<label for="username">Username</label>
				  	<input type="text" name="username" class="form-control" id="username" placeholder="">
				</div>
				<div class="form-group">
				  	<label for="email">Email Address</label>
				  	<input type="text" name="email" class="form-control" id="email" placeholder="">
				</div>
				<div class="form-group">
				  	<label for="passwordHash">Password</label>
				  	<input type="password" name="passwordHash" class="form-control" id="password" placeholder="***">
				</div>
				
				<button type="submit" class="btn btn-default">Register</button>
				
			</g:form>
		</div>
		
  	</div>
  	
</body>
</html>
