
<%@ page import="org.motus.CompetitionMember" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'competitionMember.label', default: 'CompetitionMember')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-competitionMember" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-competitionMember" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list competitionMember">
			
				<g:if test="${competitionMemberInstance?.status}">
				<li class="fieldcontain">
					<span id="status-label" class="property-label"><g:message code="competitionMember.status.label" default="Status" /></span>
					
						<span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${competitionMemberInstance}" field="status"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${competitionMemberInstance?.competitionStatus}">
				<li class="fieldcontain">
					<span id="competitionStatus-label" class="property-label"><g:message code="competitionMember.competitionStatus.label" default="Competition Status" /></span>
					
						<span class="property-value" aria-labelledby="competitionStatus-label"><g:fieldValue bean="${competitionMemberInstance}" field="competitionStatus"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${competitionMemberInstance?.dateInvited}">
				<li class="fieldcontain">
					<span id="dateInvited-label" class="property-label"><g:message code="competitionMember.dateInvited.label" default="Date Invited" /></span>
					
						<span class="property-value" aria-labelledby="dateInvited-label"><g:formatDate date="${competitionMemberInstance?.dateInvited}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${competitionMemberInstance?.dateAccepted}">
				<li class="fieldcontain">
					<span id="dateAccepted-label" class="property-label"><g:message code="competitionMember.dateAccepted.label" default="Date Accepted" /></span>
					
						<span class="property-value" aria-labelledby="dateAccepted-label"><g:formatDate date="${competitionMemberInstance?.dateAccepted}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${competitionMemberInstance?.account}">
				<li class="fieldcontain">
					<span id="account-label" class="property-label"><g:message code="competitionMember.account.label" default="Account" /></span>
					
						<span class="property-value" aria-labelledby="account-label"><g:link controller="account" action="show" id="${competitionMemberInstance?.account?.id}">${competitionMemberInstance?.account?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${competitionMemberInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="competitionMember.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${competitionMemberInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${competitionMemberInstance?.dateUpdated}">
				<li class="fieldcontain">
					<span id="dateUpdated-label" class="property-label"><g:message code="competitionMember.dateUpdated.label" default="Date Updated" /></span>
					
						<span class="property-value" aria-labelledby="dateUpdated-label"><g:formatDate date="${competitionMemberInstance?.dateUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${competitionMemberInstance?.isOrganizer}">
				<li class="fieldcontain">
					<span id="isOrganizer-label" class="property-label"><g:message code="competitionMember.isOrganizer.label" default="Is Organizer" /></span>
					
						<span class="property-value" aria-labelledby="isOrganizer-label"><g:formatBoolean boolean="${competitionMemberInstance?.isOrganizer}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${competitionMemberInstance?.workoutPlan}">
				<li class="fieldcontain">
					<span id="workoutPlan-label" class="property-label"><g:message code="competitionMember.workoutPlan.label" default="Workout Plan" /></span>
					
						<span class="property-value" aria-labelledby="workoutPlan-label"><g:link controller="workoutPlan" action="show" id="${competitionMemberInstance?.workoutPlan?.id}">${competitionMemberInstance?.workoutPlan?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${competitionMemberInstance?.id}" />
					<g:link class="edit" action="edit" id="${competitionMemberInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
