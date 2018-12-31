<html lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
	<title><g:message code="app.name"  /> : Reset Password</title>
</head>

<body>

<div class="row">

	<div class="col-md-12">

		<h1>Begin Password Reset Process</h1>
    	
		<g:if test="${flash.message}">
			<div class="message warn" id="">
				<span class="header">Something Went wrong</span>
				<span class="messageDetails">
			    	${flash.message}
				</span>
			</div>		
    	</g:if>
    	
    	
		<g:hasErrors bean="${accountInstance}">
			<div class="message error" id="">
				<span class="header">Something Went wrong</span>
				<span class="messageDetails">
			    	<g:renderErrors bean="${accountInstance}" as="list" />
				</span>
			</div>
		</g:hasErrors>
		
		<div class="resetWrapper notes">
    	
			<g:form action="sendResetEmail" method="post" >
    	
				<div class="resetForm">
				
					<p class="large">
						<em class="highight">Step One : Enter Email</em><br/>
						<span class="small">An email will be sent to this address with instructions on how to continue reset process</span>
						<input type="email" value="" class="largeInput" id="email" name="email"/>
					</p>
					
					<p class="large inactive">
						<em>Step Two : Verify Email</em><br/>
						<span class="small">An email will be sent to this address with instructions on how to continue reset process</span>
					</p>
					
					<p class="large inactive">
						<em>Step Three : Reset Password</em><br/>
					</p>
    	    	
				</div>
				
				<input type="submit" value="Start Reset Process" class="btn"/>
				
			</g:form>
    	
		</div>
    	
		<br class="clear"/>
		
	</div>
    	
</div>


	
</body>
</html>
