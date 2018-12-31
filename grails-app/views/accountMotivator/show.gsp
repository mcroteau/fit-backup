
<%@ page import="org.motus.AccountMotivator" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'accountMotivator.label', default: 'AccountMotivator')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-accountMotivator" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-accountMotivator" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list accountMotivator">
			
				<g:if test="${accountMotivatorInstance?.status}">
				<li class="fieldcontain">
					<span id="status-label" class="property-label"><g:message code="accountMotivator.status.label" default="Status" /></span>
					
						<span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${accountMotivatorInstance}" field="status"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${accountMotivatorInstance?.account}">
				<li class="fieldcontain">
					<span id="account-label" class="property-label"><g:message code="accountMotivator.account.label" default="Account" /></span>
					
						<span class="property-value" aria-labelledby="account-label"><g:link controller="account" action="show" id="${accountMotivatorInstance?.account?.id}">${accountMotivatorInstance?.account?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${accountMotivatorInstance?.comment}">
				<li class="fieldcontain">
					<span id="comment-label" class="property-label"><g:message code="accountMotivator.comment.label" default="Comment" /></span>
					
						<span class="property-value" aria-labelledby="comment-label"><g:fieldValue bean="${accountMotivatorInstance}" field="comment"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${accountMotivatorInstance?.motivator}">
				<li class="fieldcontain">
					<span id="motivator-label" class="property-label"><g:message code="accountMotivator.motivator.label" default="Motivator" /></span>
					
						<span class="property-value" aria-labelledby="motivator-label"><g:link controller="account" action="show" id="${accountMotivatorInstance?.motivator?.id}">${accountMotivatorInstance?.motivator?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${accountMotivatorInstance?.id}" />
					<g:link class="edit" action="edit" id="${accountMotivatorInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
