
<%@ page import="org.motus.AccountNotification" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'accountNotification.label', default: 'AccountNotification')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-accountNotification" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-accountNotification" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list accountNotification">
			
				<g:if test="${accountNotificationInstance?.uuid}">
				<li class="fieldcontain">
					<span id="uuid-label" class="property-label"><g:message code="accountNotification.uuid.label" default="Uuid" /></span>
					
						<span class="property-value" aria-labelledby="uuid-label"><g:fieldValue bean="${accountNotificationInstance}" field="uuid"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${accountNotificationInstance?.link}">
				<li class="fieldcontain">
					<span id="link-label" class="property-label"><g:message code="accountNotification.link.label" default="Link" /></span>
					
						<span class="property-value" aria-labelledby="link-label"><g:fieldValue bean="${accountNotificationInstance}" field="link"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${accountNotificationInstance?.description}">
				<li class="fieldcontain">
					<span id="description-label" class="property-label"><g:message code="accountNotification.description.label" default="Description" /></span>
					
						<span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${accountNotificationInstance}" field="description"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${accountNotificationInstance?.account}">
				<li class="fieldcontain">
					<span id="account-label" class="property-label"><g:message code="accountNotification.account.label" default="Account" /></span>
					
						<span class="property-value" aria-labelledby="account-label"><g:link controller="account" action="show" id="${accountNotificationInstance?.account?.id}">${accountNotificationInstance?.account?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${accountNotificationInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="accountNotification.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${accountNotificationInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${accountNotificationInstance?.type}">
				<li class="fieldcontain">
					<span id="type-label" class="property-label"><g:message code="accountNotification.type.label" default="Type" /></span>
					
						<span class="property-value" aria-labelledby="type-label"><g:fieldValue bean="${accountNotificationInstance}" field="type"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${accountNotificationInstance?.typeId}">
				<li class="fieldcontain">
					<span id="typeId-label" class="property-label"><g:message code="accountNotification.typeId.label" default="Type Id" /></span>
					
						<span class="property-value" aria-labelledby="typeId-label"><g:fieldValue bean="${accountNotificationInstance}" field="typeId"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${accountNotificationInstance?.id}" />
					<g:link class="edit" action="edit" id="${accountNotificationInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
