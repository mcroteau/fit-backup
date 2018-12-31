<%@ page import="org.motus.AccountMotivator" %>



<div class="fieldcontain ${hasErrors(bean: accountMotivatorInstance, field: 'status', 'error')} ">
	<label for="status">
		<g:message code="accountMotivator.status.label" default="Status" />
		
	</label>
	<g:textField name="status" value="${accountMotivatorInstance?.status}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: accountMotivatorInstance, field: 'account', 'error')} required">
	<label for="account">
		<g:message code="accountMotivator.account.label" default="Account" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="account" name="account.id" from="${org.motus.Account.list()}" optionKey="id" required="" value="${accountMotivatorInstance?.account?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: accountMotivatorInstance, field: 'comment', 'error')} ">
	<label for="comment">
		<g:message code="accountMotivator.comment.label" default="Comment" />
		
	</label>
	<g:textField name="comment" value="${accountMotivatorInstance?.comment}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: accountMotivatorInstance, field: 'motivator', 'error')} required">
	<label for="motivator">
		<g:message code="accountMotivator.motivator.label" default="Motivator" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="motivator" name="motivator.id" from="${org.motus.Account.list()}" optionKey="id" required="" value="${accountMotivatorInstance?.motivator?.id}" class="many-to-one"/>
</div>

