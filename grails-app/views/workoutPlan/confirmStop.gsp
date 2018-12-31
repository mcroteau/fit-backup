<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<title><g:message code="app.name"  /> : Confirm Stop</title>

</head>
<body>

	<div class="row">
	
		
	
			<g:if test="${currentPlan}">
	
				<div class="col-md-7">
				
					<h1>Confirm Stop</h1>
					<h3>${currentPlan.name}</h3>
					<h5>Date Started : ${currentPlan.startDate}</h5>
					<p>Are you sure you want to stop the current plan?</p>
					<g:form action="stop" method="post">
						<input type="submit" class="btn btn-danger" value="Stop"/>
					</g:form>
				</div>
				<div class="col-md-5">
					<h2>${percentComplete}%</h2>
					<p>Complete</p>
					${workoutsCompleted}/${totalWorkouts}
				</div>
				
			</g:if>
			<g:else>
	
				<div class="col-md-12">
					<h2>No workout plans started<h2>
					<g:link controller="workoutPlan" action="select">Start a workout Plan</g:link>
				</div>
			</g:else>
			
		</div>
	</div>


</body>
</html>
