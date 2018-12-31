<%@ page import="org.motus.AccountNotification" %>



<div class="fieldcontain ${hasErrors(bean: accountNotificationInstance, field: 'uuid', 'error')} ">
	<label for="uuid">
		<g:message code="accountNotification.uuid.label" default="Uuid" />
		
	</label>
	<g:textField name="uuid" value="${accountNotificationInstance?.uuid}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: accountNotificationInstance, field: 'link', 'error')} ">
	<label for="link">
		<g:message code="accountNotification.link.label" default="Link" />
		
	</label>
	<g:textField name="link" value="${accountNotificationInstance?.link}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: accountNotificationInstance, field: 'description', 'error')} ">
	<label for="description">
		<g:message code="accountNotification.description.label" default="Description" />
		
	</label>
	<g:textField name="description" value="${accountNotificationInstance?.description}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: accountNotificationInstance, field: 'account', 'error')} required">
	<label for="account">
		<g:message code="accountNotification.account.label" default="Account" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="account" name="account.id" from="${org.motus.Account.list()}" optionKey="id" required="" value="${accountNotificationInstance?.account?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: accountNotificationInstance, field: 'type', 'error')} ">
	<label for="type">
		<g:message code="accountNotification.type.label" default="Type" />
		
	</label>
	<g:textField name="type" value="${accountNotificationInstance?.type}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: accountNotificationInstance, field: 'typeId', 'error')} required">
	<label for="typeId">
		<g:message code="accountNotification.typeId.label" default="Type Id" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="typeId" type="number" value="${accountNotificationInstance.typeId}" required=""/>
</div>

