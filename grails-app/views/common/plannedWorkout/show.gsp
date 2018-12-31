<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<title>${plannedWorkout.displayName}</title>
	
    <link rel="stylesheet" href="${resource(dir:'css', file:'exercises.css')}" />
</head>
<body>
		
	<div class="row">
		<div class="col-md-12">		
			<g:render template="messages"/>
		</div>
	</div>
		
		
		
	<div class="row">		
		<div class="col-md-12">
		
			<h2>${plannedWorkout.displayName}
				<g:if test="${!plannedWorkout?.completed}">
					<g:if test="${!plannedWorkout.skipped}">
						<code>Incomplete</code>
					</g:if>
					<g:else>
						<code>Skipped</code>
					</g:else>
				</g:if>
				<g:else>
					<code class="completed">Completed</code>
				</g:else>
			</h2>
			
			<g:if test="${plannedWorkout.completed}">
				<h4>Workout Date :
				<g:formatDate date="${plannedWorkout?.actualWorkoutDate}" format="dd MMM yyyy"/>
				</h4>
				<g:render template="exercises_show"/>
			</g:if>
			<g:else>
				<h4>Planned Workout Date : 
				<g:formatDate date="${plannedWorkout?.plannedWorkoutDate}" format="dd MMM yyyy"/></h4>

			</g:else>
		</div>	
	</div>		
		
		
	
	<g:if test="${plannedWorkout.completed}">	
		<div class="row">
			<div class="col-md-12">
		
				<g:if test="${plannedWorkout.includeAbRipper}">
					<g:if test="${plannedWorkout?.abRipperCompleted}">
						<p>Ab Ripper X Completed</p>
					</g:if>
				</g:if>
				
				<p>Notes : ${plannedWorkout.notes}</p>
				
			</div>
		</div>	
	</g:if>
		
		
		
	<div class="row">
		<div class="col-md-12">
			<g:form>
				<g:hiddenField name="id" value="${plannedWorkout?.id}" />
				
	
				<g:if test="${plannedWorkout.completed}">
					<g:link class="btn btn-primary" action="entry" id="${plannedWorkout?.id}">Edit</g:link>
				</g:if>
				<g:else>					
					<g:link class="btn btn-primary" action="entry" id="${plannedWorkout?.id}">Log Workout</g:link>
				</g:else>
				
			</g:form>
		</div>
	</div>
	

</body>
</html>
