
<%@ page import="org.motus.CompetitionMember" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'competitionMember.label', default: 'CompetitionMember')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-competitionMember" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-competitionMember" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="status" title="${message(code: 'competitionMember.status.label', default: 'Status')}" />
					
						<g:sortableColumn property="competitionStatus" title="${message(code: 'competitionMember.competitionStatus.label', default: 'Competition Status')}" />
					
						<g:sortableColumn property="dateInvited" title="${message(code: 'competitionMember.dateInvited.label', default: 'Date Invited')}" />
					
						<g:sortableColumn property="dateAccepted" title="${message(code: 'competitionMember.dateAccepted.label', default: 'Date Accepted')}" />
					
						<th><g:message code="competitionMember.account.label" default="Account" /></th>
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'competitionMember.dateCreated.label', default: 'Date Created')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${competitionMemberInstanceList}" status="i" var="competitionMemberInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${competitionMemberInstance.id}">${fieldValue(bean: competitionMemberInstance, field: "status")}</g:link></td>
					
						<td>${fieldValue(bean: competitionMemberInstance, field: "competitionStatus")}</td>
					
						<td><g:formatDate date="${competitionMemberInstance.dateInvited}" /></td>
					
						<td><g:formatDate date="${competitionMemberInstance.dateAccepted}" /></td>
					
						<td>${fieldValue(bean: competitionMemberInstance, field: "account")}</td>
					
						<td><g:formatDate date="${competitionMemberInstance.dateCreated}" /></td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${competitionMemberInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
