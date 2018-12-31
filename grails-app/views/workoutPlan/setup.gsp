<html lang="en">
<head>
  	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 	<meta name="layout" content="main" />
	<title><g:message code="app.name"  /> : Select Workout Plan</title>
		
	<script type="text/javascript" src="${resource(dir:'js/lib/moment/2.4.0/moment.min.js')}"></script>
	
	
</head>
<body>
  
  	<div class="row">
		<div class="col-md-12">
			<g:if test="${flash.message}">
				<div class="alert alert-info">${flash.message}</div>
			</g:if>	
		</div>
	</div>
	
	
	<div class="row">
	
		<div class="col-md-5">
			<h2>Setup : ${workoutPlan.name}
				<g:if test="${workoutPlan.popular}">
					<span class="glyphicon glyphicon-star">Popular</span>
				</g:if>
			</h2>
			<p><strong># weeks : </strong> ${workoutPlan.weeksCount}</p>
			<p><strong># workouts : </strong> ${workoutPlan.workoutsCount}</p>
			<p><strong># active users : </strong> ${workoutPlan.plansCount}</p>
			<p>${workoutPlan.description}</p>
			
			<g:form action="start" method="post">
				<input type="hidden" name="workoutPlan" value="${workoutPlan.name}"/>

				<div class="form-group">
				  	<label for="startDate">Start Date</label>
					<g:datePicker name="startDate" value="${new Date()}"
						precision="day"/>
				</div>
				<div class="form-group">
					<label for="endDate">End Date</label>
					<span id="endDate"></span>
				</div>
				
				<input type="submit" class="btn btn-info" value="Start"/>
			</g:form>
			
		</div>
	
		<div class="col-md-6">
		</div>
	
		
  	</div>
  	
<script type="text/javascript">

$(document).ready(function(){
	
	var $day = $('#startDate_day'),
		$month = $('#startDate_month'),
		$year = $('#startDate_year'),
		$endDate = $('#endDate');
		
	$day.change(updateEndDate)	
	$month.change(updateEndDate)
	$year.change(updateEndDate)
		
	var weeks = ${workoutPlan.weeksCount};
	var days = weeks * 7
	
	function updateEndDate(){
		var futureDate = new Date($year.val(), $month.val() -1, $day.val() )		
		futureDate.setDate(futureDate.getDate() + days)		
		$endDate.html(moment(futureDate).format("DD MMMM YYYY"))
	}
	
	updateEndDate()
	
});

</script>
	
</body>
</html>
