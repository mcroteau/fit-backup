<%@ page import="org.motus.Competition" %>



<div class="fieldcontain ${hasErrors(bean: competitionInstance, field: 'startDate', 'error')} required">
	<label for="startDate">
		<g:message code="competition.startDate.label" default="Start Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="startDate" precision="day"  value="${competitionInstance?.startDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: competitionInstance, field: 'endDate', 'error')} required">
	<label for="endDate">
		<g:message code="competition.endDate.label" default="End Date" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="endDate" precision="day"  value="${competitionInstance?.endDate}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: competitionInstance, field: 'program', 'error')} ">
	<label for="program">
		<g:message code="competition.program.label" default="Program" />
		
	</label>
	<g:textField name="program" value="${competitionInstance?.program}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: competitionInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="competition.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${competitionInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: competitionInstance, field: 'members', 'error')} ">
	<label for="members">
		<g:message code="competition.members.label" default="Members" />
		
	</label>
	<g:select name="members" from="${org.motus.CompetitionMember.list()}" multiple="multiple" optionKey="id" size="5" value="${competitionInstance?.members*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: competitionInstance, field: 'organizer', 'error')} required">
	<label for="organizer">
		<g:message code="competition.organizer.label" default="Organizer" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="organizer" name="organizer.id" from="${org.motus.Account.list()}" optionKey="id" required="" value="${competitionInstance?.organizer?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: competitionInstance, field: 'workoutPlans', 'error')} ">
	<label for="workoutPlans">
		<g:message code="competition.workoutPlans.label" default="Workout Plans" />
		
	</label>
	<g:select name="workoutPlans" from="${org.motus.WorkoutPlan.list()}" multiple="multiple" optionKey="id" size="5" value="${competitionInstance?.workoutPlans*.id}" class="many-to-many"/>
</div>

