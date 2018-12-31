<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<title>Chest & Back Entry : <shiro:principal/></title>

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
		</div>		
	</div>
	
	
	
	<g:form method="post" >
	
		<g:hiddenField name="id" value="${plannedWorkout?.id}" />
		<g:hiddenField name="version" value="${plannedWorkout?.version}" />
		
		<g:hiddenField name="completed" value="true" />
		
		<h4><g:datePicker value="${plannedWorkout?.plannedWorkoutDate}" precision="day" name="actualWorkoutDate"/></h4>
	
		<g:if test="${!plannedWorkout.skipped}">
			
			<g:render template="exercises"/>
			
			<div class="row">
				<div class="col-md-12">
    		
					<g:if test="${!plannedWorkout?.completed}">
						<g:actionSubmit class="btn btn-warning" action="confirmSkip" value="Skip"/>
						<g:actionSubmit class="btn btn-success" action="update" value="Save" />
					</g:if>
					<g:else>
						<g:actionSubmit class="btn btn-warning" action="confirmSkip" value="Skipped" />
						<g:actionSubmit class="btn btn-primary" action="update" value="Update" />
					</g:else>
					
				</div>
			</div>
    		
		</g:if>
		<g:else>
			<h5>Skipped workout on  <g:formatDate date="${plannedWorkout?.skippedDate}" format="dd MMM yyyy"/> </h5>
			<g:actionSubmit class="btn btn-warning" action="confirmRecover" value="Recover" />			
		</g:else>
		
	
	</g:form>
	
</body>
</html>
