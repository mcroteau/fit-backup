<html lang="en">
<head>
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 	<meta name="layout" content="main" />
	<title><g:message code="app.name"  /> : Login</title>
</head>
<body>
  
	<div class="row">
	
		<div class="col-md-5">
  			<g:if test="${flash.message}">
    			<div class="alert alert-info">${flash.message}</div>
  			</g:if>
    		
			<g:form action="signIn" class="form">
				<div class="form-group">
				  	<label for="username">Username</label>
				  	<input type="text" name="username" class="form-control" id="username" placeholder="">
				</div>
				<div class="form-group">
				  	<label for="password">Password</label>
				  	<input type="password" name="password" class="form-control" id="password" placeholder="***">
				</div>
				<div class="checkbox">
				  	<label>
				    	<g:checkBox name="rememberMe" value="${rememberMe}" />&nbsp;Remember Me
				  	</label>
				</div>

				<div class="form-group">
				  	<label><g:link controller="account" action="forgot">Forgot password?</g:link></label>
				</div>
				
				<input type="hidden" name="targetUri" value="${targetUri}" />
				<button type="submit" class="btn btn-default">Login</button>
				
			</g:form>
		</div>
		
		<div class="col-md-4">
			<div class="well">
				<h5>Dont have an account?</h5>
				<p>Signup for free and begin tracking your workouts</p>
				<g:link controller="account" action="registration">Register</g:link>
			</div>
		</div>
		
  	</div>
  	
</body>
</html>
