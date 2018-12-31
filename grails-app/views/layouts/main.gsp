<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]> <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]> <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]> <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
<head>

	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	
	<link rel="stylesheet" href="${resource(dir:'bootstrap/3.0.3/css', file:'bootstrap.css')}" />	
    <link rel="stylesheet" href="${resource(dir:'bootstrap/3.0.3/css', file:'bootstrap-theme.min.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css', file:'app.css')}" />
	

	<script type="text/javascript" src="${resource(dir:'js/lib/jquery/jquery.min.js')}"></script>
	<script type="text/javascript" src="${resource(dir:'js/lib/d3/d3.v3.min.js')}"></script>
	<script type="text/javascript" src="${resource(dir:'bootstrap/3.0.3/js/bootstrap.js')}"></script>
	
	<g:layoutHead/>
	
</head>
<body>

	<div class="container">
	
		<div class="row">
		
			<div class="col-md-12">
			
				<div class="navbar navbar-default" role="navigation">
					<div class="navbar-header">
					
						<g:link controller="content" action="home" class="${home_active}" params="[activeLink: 'home']">Home</g:link>
						<g:link controller="content" action="about" class="${about_active}" params="[activeLink: 'about']">About</g:link>
						
						<g:if test="${shiro.principal()}">
							Welcome back 
							<g:link controller="account" action="profile"><shiro:principal/></g:link>&nbsp;&nbsp;|&nbsp;&nbsp;<g:link controller="auth" action="signOut" >Logout</g:link>
							<g:link controller="account" action="dashboard">Dashboard</g:link>
							<g:link controller="workoutPlan" action="list">Workout Plans</g:link>
							<g:link controller="workoutPlan" action="select">Select Plan</g:link>
							<g:link controller="workoutPlan" action="logEntry">Log Workout</g:link>
							<g:link controller="account" action="competition">Competition</g:link>
							
							&nbsp;&nbsp;
							
							<g:form controller="account" action="search">
								<input type="text" name="search"/> 
							</g:form>
						</g:if>
						
						<g:else>
							<g:link controller="auth" action="login">Login</g:link>
						</g:else>

						<g:link controller="workoutGenerator" action="generate" params="[name:legsBack]">Generate Class</g:link>
						<g:link controller="workoutPlan" action="refresh">Refresh Plans</g:link>
					</div>
				</div>
				
			</div>
			
		</div>
	
	
		<g:layoutBody/>
		
		
		<footer class="footer" style="background:#efefef; padding:20px">
			<g:link controller="content" action="privacy" class="${privacy_active}" params="[activeLink: 'privacy']">Privacy</g:link>
			<g:link controller="accountMotivator" action="clearData">Clear Motivators</g:link>
		</footer>
		
	</div>
	
	
</body>
</html>
