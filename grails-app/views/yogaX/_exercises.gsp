<div class="row">

	<div class="col-md-12">
	
		<g:if test="${plannedWorkout.includeAbRipper}">
			<p>	
				Ab Ripper X Completed : <g:checkBox name="abRipperCompleted" value="${plannedWorkout?.abRipperCompleted}"  />
			</p>
		</g:if>
		
		<p>Notes : </p>
		
		<p><textarea name="notes" cols="50" rows="4">${plannedWorkout?.notes}</textarea></p>
		
	</div>
	
</div>