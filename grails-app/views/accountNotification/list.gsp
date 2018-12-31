
<%@ page import="org.motus.AccountNotification" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'accountNotification.label', default: 'AccountNotification')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-accountNotification" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-accountNotification" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="uuid" title="${message(code: 'accountNotification.uuid.label', default: 'Uuid')}" />
					
						<g:sortableColumn property="link" title="${message(code: 'accountNotification.link.label', default: 'Link')}" />
					
						<g:sortableColumn property="description" title="${message(code: 'accountNotification.description.label', default: 'Description')}" />
					
						<th><g:message code="accountNotification.account.label" default="Account" /></th>
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'accountNotification.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="type" title="${message(code: 'accountNotification.type.label', default: 'Type')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${accountNotificationInstanceList}" status="i" var="accountNotificationInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${accountNotificationInstance.id}">${fieldValue(bean: accountNotificationInstance, field: "uuid")}</g:link></td>
					
						<td>${fieldValue(bean: accountNotificationInstance, field: "link")}</td>
					
						<td>${fieldValue(bean: accountNotificationInstance, field: "description")}</td>
					
						<td>${fieldValue(bean: accountNotificationInstance, field: "account")}</td>
					
						<td><g:formatDate date="${accountNotificationInstance.dateCreated}" /></td>
					
						<td>${fieldValue(bean: accountNotificationInstance, field: "type")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${accountNotificationInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
