<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<title><g:message code="app.name"  /> : Confirm Skip</title>

</head>
<body>

	<div class="row">
	
		<div class="col-md-12">
			<h1>Confirm Skip ${plannedWorkout.displayName} Workout</h1>
			<p>Are you sure you want to skip this workout today?</p>
			<g:form action="skip" method="post">
				<input type="hidden" name="id" value="${plannedWorkout.id}"/>
				<input type="submit" class="btn btn-danger" value="Skip"/>
			</g:form>
		</div>
    		
	</div>

</body>
</html>
