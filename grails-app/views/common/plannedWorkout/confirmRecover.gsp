<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<title><g:message code="app.name"  /> : Confirm Recover</title>

</head>
<body>

	<div class="row">
	
		<div class="col-md-12">
			<h1>Confirm Recover ${plannedWorkout.displayName} Workout</h1>
			<p>Are you sure you want to recover this workout?</p>
			<g:form action="recover" method="post">
				<input type="hidden" name="id" value="${plannedWorkout.id}"/>
				<input type="submit" class="btn btn-danger" value="Recover"/>
			</g:form>
		</div>
    		
	</div>

</body>
</html>
