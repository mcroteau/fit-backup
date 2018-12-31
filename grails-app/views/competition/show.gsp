
<%@ page import="org.motus.Competition" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'competition.label', default: 'Competition')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-competition" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-competition" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list competition">
			
				<g:if test="${competitionInstance?.startDate}">
				<li class="fieldcontain">
					<span id="startDate-label" class="property-label"><g:message code="competition.startDate.label" default="Start Date" /></span>
					
						<span class="property-value" aria-labelledby="startDate-label"><g:formatDate date="${competitionInstance?.startDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${competitionInstance?.endDate}">
				<li class="fieldcontain">
					<span id="endDate-label" class="property-label"><g:message code="competition.endDate.label" default="End Date" /></span>
					
						<span class="property-value" aria-labelledby="endDate-label"><g:formatDate date="${competitionInstance?.endDate}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${competitionInstance?.program}">
				<li class="fieldcontain">
					<span id="program-label" class="property-label"><g:message code="competition.program.label" default="Program" /></span>
					
						<span class="property-value" aria-labelledby="program-label"><g:fieldValue bean="${competitionInstance}" field="program"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${competitionInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="competition.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${competitionInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${competitionInstance?.members}">
				<li class="fieldcontain">
					<span id="members-label" class="property-label"><g:message code="competition.members.label" default="Members" /></span>
					
						<g:each in="${competitionInstance.members}" var="m">
						<span class="property-value" aria-labelledby="members-label"><g:link controller="competitionMember" action="show" id="${m.id}">${m?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${competitionInstance?.organizer}">
				<li class="fieldcontain">
					<span id="organizer-label" class="property-label"><g:message code="competition.organizer.label" default="Organizer" /></span>
					
						<span class="property-value" aria-labelledby="organizer-label"><g:link controller="account" action="show" id="${competitionInstance?.organizer?.id}">${competitionInstance?.organizer?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${competitionInstance?.workoutPlans}">
				<li class="fieldcontain">
					<span id="workoutPlans-label" class="property-label"><g:message code="competition.workoutPlans.label" default="Workout Plans" /></span>
					
						<g:each in="${competitionInstance.workoutPlans}" var="w">
						<span class="property-value" aria-labelledby="workoutPlans-label"><g:link controller="workoutPlan" action="show" id="${w.id}">${w?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${competitionInstance?.id}" />
					<g:link class="edit" action="edit" id="${competitionInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
