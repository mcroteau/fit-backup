<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main">
	<title><g:message code="app.name"  /> : Dashboard</title>
	
    <link rel="stylesheet" href="${resource(dir:'js/lib/fullcalendar/1.6.4/',file:'fullcalendar.css')}" />
		
	<script type="text/javascript" src="${resource(dir:'js/lib/fullcalendar/1.6.4/fullcalendar.min.js')}"></script>
		
	<script type="text/javascript" src="${resource(dir:'js/lib/moment/2.4.0/moment.min.js')}"></script>
	
	
	<style type="text/css">
		
		.fc-event{
			opacity : 0.8
		}
		
		.fc-event.completed{
			background: #0000ff
		}
		
		.fc-event.skipped{
			background: #333
		}
		
		.fc-event:hover{
			color:#fff;
			text-decoration:none;
			opacity:1.0
		}
	</style>
	
</head>
<body>

	<div class="row">
	
		<div class="col-md-12">
			
			<g:if test="${flash.message}">
				<div class="alert alert-info" role="status">${flash.message}</div>
			</g:if>
			
			<g:if test="${dashboardData?.currentPlan?.plannedWorkouts}">
				<h3>${dashboardData?.currentPlan?.plannedWorkouts.size()}</h3>
			</g:if>
			
			<g:if test="${dashboardData?.currentPlan}">
		
				<h1>Dashboard</h1>
			
				<h3>${dashboardData?.currentPlan.name}</h3>
				<p>${dashboardData?.currentPlan.description}</p>
				<p><strong><span id="fromNow"></span> to go</strong></p>
				<p><strong>Workouts Completed : </strong> <span id="workoutsCompleted">${dashboardData?.completedWorkouts.size()}</span></p>
				<p><strong>Workouts Remaining : </strong> <span id="workoutsCompleted">${dashboardData?.incompleteWorkouts.size()}</span></p>
				<p><strong>Workouts Skipped : </strong> <span id="workoutsCompleted">${dashboardData?.skippedWorkouts.size()}</span></p>
				<p><strong>End Date : </strong> <span id="completeDate"></span></p>
				
				<g:if test="${dashboardData?.todaysWorkout}">
					<p>
						<Strong>Todays Workout</strong>
						<span>${dashboardData?.todaysWorkout.displayName}</span>
					
						<g:if test="${dashboardData?.todaysWorkout.completed}">
							<span class="">Completed</span>
						</g:if>
						<g:elseif test="${dashboardData?.todaysWorkout.skipped}">
							<span class="">Skipped</span>
						</g:elseif>
						<g:else>
							<g:link controller="${dashboardData?.todaysWorkout.link}" action="show" params="[id:dashboardData.todaysWorkout.id]">Incomplete</g:link>
						</g:else>
					</p>
				</g:if>
				
				
				<g:link controller="workoutPlan" action="confirmStop" class="btn btn-warning">Stop Plan</g:link>
				<div id="calendar"></div>
				
			</g:if>
			<g:else>
			
				<h2>Start something...</h2>

				<p>You are currently have Workout Program started</p>
				<g:link controller="workoutPlan" action="select" class="btn btn-info">Start a new workout plan</g:link>
				
				<!--
					<p>You are currently not involved in a competition or Workout Program</p>
					<g:link controller="workoutPlan" action="select" class="btn btn-info">Start a new workout plan</g:link>
					<g:link controller="competition" action="configure" class="btn btn-info">Start a Competition</g:link>
				-->
				
			</g:else>
			
		</div>
		
	</div>




<script type="text/javascript">

$(document).ready(function(){
completeDate
	var $completeDate = $('#completeDate'),
		$fromNow = $('#fromNow');
	
	<g:if test="${completeDateCal}">
		var day = ${completeDateCal.get(Calendar.DAY_OF_MONTH)}
		var month = ${completeDateCal.get(Calendar.MONTH)}
		var year = ${completeDateCal.get(Calendar.YEAR)}
		
		var date = new Date(year, month, day)

		var fromNow = moment([year, month, day]).fromNow(true)
		
		console.info(day, month, year, date)
		var completeDateFormatted = moment(date).format("DD MMMM YYYY")
		
		$completeDate.html(completeDateFormatted)
		$fromNow.html(fromNow)
		
	</g:if>

	var workouts = ${dashboardData?.workouts};
	console.log(workouts.length);

	var Calendar = function($calendarDiv){
		var self = this;
		
		console.info('calendar div', $calendarDiv);
		
		self.$calendarDiv = $calendarDiv;


		var $day = $('.fc-widget-content');
		var $loading = $('#loading');

		var $calendar;


		self.init = function(){
			self.render();
			//self.bindQtipOnEvents();
		};

		var count = 0;

		self.render = function(){
	
			console.info('RENDER', workouts.length);
			
			$calendar = self.$calendarDiv.fullCalendar({

				//events : "entries",
				events : "workouts",
				disableDragging : true,
				selectable : true,
				select : function(start, end, allDay, jsEvent, view, onevent){

					var $cellOverlay = 	$('.fc-cell-overlay');

					var dateString = start.getFullYear() + '-' + (start.getMonth() + 1) + '-' + start.getDate();
					var today = new Date();
					var result = util.dates.compare(start, today);

					if(result === 1){

						var message = 'Cannot log entry for the future... what are you trying to pull here...';

						
						/**
						$cellOverlay.qtip({
							content: {
								text: message
							},
						   	position: {
						    	my: 'bottom center',  // Position my top left...
						      	at: 'top center', // at the bottom right of...
						    },
							style: {
								classes: 'ui-tooltip-youtube'
							}
						});
						$cellOverlay.qtip().show();
						**/

					}else{
					
						//window.location = '/franklins13/virtueEntry/logEntry?date=' + dateString;
					}

				},
				loading : function(bool) {
					if (bool){
						$loading.show();
						console.info('loading....')	
					} else {
						$loading.hide();
					}
				},
				eventRender: function(event, element) {
					count++
					console.log(count, event)
					$(element).addClass(event.class)
				}

			});				
		};

		self.bindQtipOnEvents = function(){
		
			/** replace qtip qith modern hover lib
			$('.fc-event').qtip({
				content: {
					text: "View Details"
				},
				position: {
					my: 'bottom center',  // Position my top left...
				  	at: 'top center', // at the bottom right of...
				},
				style: {
					classes: 'ui-tooltip-youtube'
				}

			});
			**/
			
		};

		self.zeroPad = function(num, count){
			var numZeropad = num + '';
			while(numZeropad.length < count) {
				numZeropad = "0" + numZeropad;
			}
			return numZeropad;
		}
	}


	var calendar = new Calendar($('#calendar'));
	calendar.init();


});

function zeroPad(num, count){
	var numZeropad = num + '';
	while(numZeropad.length < count) {
		numZeropad = "0" + numZeropad;
	}
	return numZeropad;
}





</script>
		
</body>
</html>
