<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<title><g:message code="app.name"  /> : Dashboard</title>
	
    <link rel="stylesheet" href="${resource(dir:'js/lib/fullcalendar/1.6.4/',file:'fullcalendar.css')}" />
		
	<script type="text/javascript" src="${resource(dir:'js/lib/moment/2.4.0/moment.min.js')}"></script>
	
</head>
<body>

	<div class="row">
	
		<div class="col-md-12">
			
			<g:if test="${flash.message}">
				<div class="alert alert-info" role="status">${flash.message}</div>
			</g:if>
			
			<h1>${competition.program} Competition</h1>
			<p><strong>start date : </strong>${competition.startDate}</p>
			<p><strong>end date : </strong>${competition.endDate}</p>
			<p>${competition.description}</p>
			

			<g:each in="${competition.workoutPlans}" status="i" var="workoutPlan">
				<div class="well">
					<h3>${workoutPlan.account.username}</h3>
					<p><strong>start date : </strong> ${workoutPlan.startDate}</p>
				</div>
			</g:each>
			
		</div>
		
	</div>




<script type="text/javascript">

$(document).ready(function(){
	console.info('loaded...')
});

</script>
		
</body>
</html>
