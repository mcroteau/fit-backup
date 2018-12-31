
<%@ page import="org.motus.AccountMotivator" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'accountMotivator.label', default: 'AccountMotivator')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-accountMotivator" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-accountMotivator" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="status" title="${message(code: 'accountMotivator.status.label', default: 'Status')}" />
					
						<th><g:message code="accountMotivator.account.label" default="Account" /></th>
					
						<g:sortableColumn property="comment" title="${message(code: 'accountMotivator.comment.label', default: 'Comment')}" />
					
						<th><g:message code="accountMotivator.motivator.label" default="Motivator" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${accountMotivatorInstanceList}" status="i" var="accountMotivatorInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${accountMotivatorInstance.id}">${fieldValue(bean: accountMotivatorInstance, field: "status")}</g:link></td>
					
						<td>${fieldValue(bean: accountMotivatorInstance, field: "account")}</td>
					
						<td>${fieldValue(bean: accountMotivatorInstance, field: "comment")}</td>
					
						<td>${fieldValue(bean: accountMotivatorInstance, field: "motivator")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${accountMotivatorInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
