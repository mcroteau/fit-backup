<%@ page import="org.motus.CompetitionMember" %>



<div class="fieldcontain ${hasErrors(bean: competitionMemberInstance, field: 'status', 'error')} ">
	<label for="status">
		<g:message code="competitionMember.status.label" default="Status" />
		
	</label>
	<g:textField name="status" value="${competitionMemberInstance?.status}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: competitionMemberInstance, field: 'competitionStatus', 'error')} ">
	<label for="competitionStatus">
		<g:message code="competitionMember.competitionStatus.label" default="Competition Status" />
		
	</label>
	<g:textField name="competitionStatus" value="${competitionMemberInstance?.competitionStatus}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: competitionMemberInstance, field: 'dateInvited', 'error')} required">
	<label for="dateInvited">
		<g:message code="competitionMember.dateInvited.label" default="Date Invited" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dateInvited" precision="day"  value="${competitionMemberInstance?.dateInvited}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: competitionMemberInstance, field: 'dateAccepted', 'error')} required">
	<label for="dateAccepted">
		<g:message code="competitionMember.dateAccepted.label" default="Date Accepted" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dateAccepted" precision="day"  value="${competitionMemberInstance?.dateAccepted}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: competitionMemberInstance, field: 'account', 'error')} required">
	<label for="account">
		<g:message code="competitionMember.account.label" default="Account" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="account" name="account.id" from="${org.motus.Account.list()}" optionKey="id" required="" value="${competitionMemberInstance?.account?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: competitionMemberInstance, field: 'dateUpdated', 'error')} required">
	<label for="dateUpdated">
		<g:message code="competitionMember.dateUpdated.label" default="Date Updated" />
		<span class="required-indicator">*</span>
	</label>
	<g:datePicker name="dateUpdated" precision="day"  value="${competitionMemberInstance?.dateUpdated}"  />
</div>

<div class="fieldcontain ${hasErrors(bean: competitionMemberInstance, field: 'isOrganizer', 'error')} ">
	<label for="isOrganizer">
		<g:message code="competitionMember.isOrganizer.label" default="Is Organizer" />
		
	</label>
	<g:checkBox name="isOrganizer" value="${competitionMemberInstance?.isOrganizer}" />
</div>

<div class="fieldcontain ${hasErrors(bean: competitionMemberInstance, field: 'workoutPlan', 'error')} required">
	<label for="workoutPlan">
		<g:message code="competitionMember.workoutPlan.label" default="Workout Plan" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="workoutPlan" name="workoutPlan.id" from="${org.motus.WorkoutPlan.list()}" optionKey="id" required="" value="${competitionMemberInstance?.workoutPlan?.id}" class="many-to-one"/>
</div>

