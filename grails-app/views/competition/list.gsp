
<%@ page import="org.motus.Competition" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'competition.label', default: 'Competition')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-competition" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-competition" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="startDate" title="${message(code: 'competition.startDate.label', default: 'Start Date')}" />
					
						<g:sortableColumn property="endDate" title="${message(code: 'competition.endDate.label', default: 'End Date')}" />
					
						<g:sortableColumn property="program" title="${message(code: 'competition.program.label', default: 'Program')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'competition.description.label', default: 'Description')}" />
					
						<th><g:message code="competition.organizer.label" default="Organizer" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${competitionInstanceList}" status="i" var="competitionInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${competitionInstance.id}">${fieldValue(bean: competitionInstance, field: "startDate")}</g:link></td>
					
						<td><g:formatDate date="${competitionInstance.endDate}" /></td>
					
						<td>${fieldValue(bean: competitionInstance, field: "program")}</td>
					
						<td>${fieldValue(bean: competitionInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: competitionInstance, field: "organizer")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${competitionInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
