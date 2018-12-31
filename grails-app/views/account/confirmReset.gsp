<html lang="en">
<head>
    <meta name="layout" content="main" />
	<title><g:message code="app.name"  /> : Reset Password</title>
</head>
<body>

	<div class="row">
	
		<div class="col-md-12">
		
			<h1>Reset Password</h1>
			<g:if test="${flash.message}">
				<div class="message info" id="">
					<span class="header"></span>
					<span class="messageDetails">
				    	${flash.message}
					</span>
				</div>		
		    </g:if>
	
		
			<div class="resetWrapper notes">
			
				<div class="resetForm">
					<p class="large inactive">
						<em class="highight">Step One : Enter Email</em><br/>
					</p>
					<p class="large inactive">
						<em>Step Two : Verify Email</em><br/>
					</p>
					<p class="large ">
						<em>Step Three : Reset Password</em><br/>
					</p>
					
					<p>Reseting password for <strong>${username}</strong></p>
		            <g:form action="resetPassword" method="post" >
						<p>
		            		<label for="password">New Password</label>
		            		<input type="password" name="password" id="password" value="" >
		            	</p>
						<p>
			        		<label for="username">Confirm Password</label>
			        		<input type="password" name="confirmPassword" id="confirmPassword" value="" >
			        	</p>
						<input type="hidden" value="${username}" name="username"/>
						
						<input type="submit" value="Reset Password" class="btn"/>
					</g:form>
					
				</div>
	
		
			</div>
			
			
		</div>
	
	</div>
	

</body>
</html>
