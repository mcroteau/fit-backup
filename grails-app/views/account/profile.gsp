<%@ page import="org.motus.Account" %>
<html lang="en">
<head>
 	<meta name="layout" content="main" />
	<title><g:message code="app.name"  /> : <shiro:principal/></title>
		
	<script type="text/javascript" src="${resource(dir:'js/lib/moment/2.4.0/moment.min.js')}"></script>
	
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
			
			<g:form action="update" class="form">

				<g:hiddenField name="id" value="${accountInstance?.id}" />
				<g:hiddenField name="version" value="${accountInstance?.version}" />
				
				<div class="form-group">
				  	<label for="username">Username</label>
					${accountInstance?.username}
				</div>
				<div class="form-group">
				  	<label for="email">Email Address</label>
				  	<input type="text" name="email" class="form-control" id="email" placeholder="" value="${accountInstance?.email}">
				</div>
				<div class="form-group">
				  	<label for="name">Name</label>
				  	<input type="text" name="name" class="form-control" id="name" placeholder="" value="${accountInstance?.name}">
				</div>

				<div class="form-group">
				  	<label for="age">Age</label>
				  	<input type="text" name="age" class="form-control" id="age" placeholder="" value="${accountInstance?.age}">
				</div>
				
				<div class="form-group">
				  	<span >Member for </span>
					<strong id="memberFor"></strong>
				</div>
				
				<div class="form-group">
					<h3>Motivator Requests</h3>
					<g:each in="${accountInstance.motivators}" var="motivator">
						<g:if test="${motivator.status == 'PENDING'}">
							<div>
								<span class="label">${motivator.motivator.username}</span>
								<g:link controller="accountMotivator" action="review" params="[ username : motivator.motivator.username, comment : motivator.comment ]" class="btn btn-small btn-info">Review</g:link>
							</div>
						</g:if>
					</g:each>
				</div>
				
				
				<div class="form-group">
					<h3>Motivators</h3>
					<g:each in="${accountInstance.motivators}" var="motivator">
						<g:if test="${motivator.status == 'APPROVED'}">
							<span class="label">${motivator.motivator.username}</span>
						</g:if>
					</g:each>
				</div>
				
				<button type="submit" class="btn btn-default">Update Profile</button>
				
			</g:form>
		</div>
		
  	</div>
	
<script type="text/javascript">

$(document).ready(function(){

	var $memberFor = $('#memberFor');

	var day = ${registeredCal.get(Calendar.DAY_OF_MONTH)}
	var month = ${registeredCal.get(Calendar.MONTH)}
	var year = ${registeredCal.get(Calendar.YEAR)}
	
	var date = new Date(year, month, day)

	var memberFor = moment([year, month, day]).fromNow(true)
	
	
	$memberFor.html(memberFor)
	
});
</script>
  	
</body>
</html>
