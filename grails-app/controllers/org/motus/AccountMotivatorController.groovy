package org.motus

import org.springframework.dao.DataIntegrityViolationException
import org.apache.shiro.SecurityUtils
import org.motus.common.MotivatorRequestStatus
import org.motus.AccountMotivator
import org.motus.AccountNotification
import org.motus.Account
import java.util.UUID


class AccountMotivatorController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

	def review(){
		[username : params.username, comment: params.comment]
	}

	
	
	def request_motivate_form(){
		def subject = SecurityUtils.getSubject();
		
		if(subject.isAuthenticated()){
			if(params.username){
				[username : params.username]
			}else{
				flash.message = ""
				redirect(action:'profile')
			}
		}else{
			flash.message = "Please log in to complete request to be ${params.username}'s motivator"
			redirect(controller:'auth', action:'login')
		}
	}

	
	def request_motivate(){
	
		def subject = SecurityUtils.getSubject();
		
		if(subject.isAuthenticated()){

			def motivator = Account.findByUsername(subject.principal)
			def account = Account.findByUsername(params.username)
			
			def account_motivator = new AccountMotivator()
			
			account_motivator.status = MotivatorRequestStatus.PENDING.description()
			account_motivator.motivator = motivator
			account_motivator.account = account
			account_motivator.comment = params.comment
			account_motivator.save(flush:true)
			
			//TODO: create notification
			def notification = new AccountNotification();
			notification.account = account
			notification.description = ""
			notification.link = ""
			notification.uuid = UUID.randomUUID()
			notification.typeId = account_motivator.id
			notification.type = "AccountMotivator"
			notification.save(flush:true)
			
			account.addToNotifications(notification)
			account.save(flush:true)
			
			//TODO: email request to account and requestor
			render view:'request_submitted'
			
		}else{
			flash.message = "You must be logged in to follow someone"
			redirect(controller : 'auth', action : 'signIn')
		}
	}
	
	
	def approve(){
		def subject = SecurityUtils.getSubject();
		
		if(subject.isAuthenticated()){
			
			def account = Account.findByUsername(subject.principal)
			def motivator = Account.findByUsername(params.username)
			
			def account_motivator = AccountMotivator.findByMotivator(motivator)
			account_motivator.status = MotivatorRequestStatus.APPROVED.description()
			
			
			//TODO: email approval to requestor
			account_motivator.save(flush:true)
			flash.message = "Request approved."
			redirect controller:'account', action:'profile'
		}else{
			flash.message = "you must be logged in to continue"
			redirect (controller : 'auth', action:'signIn')
		}	
	
	}


	def deny(){
		def subject = SecurityUtils.getSubject();
	
		if(subject.isAuthenticated()){
			
			def account = Account.findByUsername(subject.principal)
			def motivator = Account.findByUsername(params.username)
			
			def account_motivator = AccountMotivator.findByMotivator(motivator)
			account_motivator.status = MotivatorRequestStatus.DENIED.description()
			
			account_motivator.save(flush:true)
			
			flash.message = "Request ignored"
			redirect controller:'account', action:'profile'
			
		}else{
			flash.message = "you must be logged in to continue"
			redirect (controller : 'auth', action:'signIn')
		}
	}
	
	

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [accountMotivatorInstanceList: AccountMotivator.list(params), accountMotivatorInstanceTotal: AccountMotivator.count()]
    }

    def create() {
        [accountMotivatorInstance: new AccountMotivator(params)]
    }

    def save() {
        def accountMotivatorInstance = new AccountMotivator(params)
        if (!accountMotivatorInstance.save(flush: true)) {
            render(view: "create", model: [accountMotivatorInstance: accountMotivatorInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'accountMotivator.label', default: 'AccountMotivator'), accountMotivatorInstance.id])
        redirect(action: "show", id: accountMotivatorInstance.id)
    }

    def show(Long id) {
        def accountMotivatorInstance = AccountMotivator.get(id)
        if (!accountMotivatorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountMotivator.label', default: 'AccountMotivator'), id])
            redirect(action: "list")
            return
        }

        [accountMotivatorInstance: accountMotivatorInstance]
    }

    def edit(Long id) {
        def accountMotivatorInstance = AccountMotivator.get(id)
        if (!accountMotivatorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountMotivator.label', default: 'AccountMotivator'), id])
            redirect(action: "list")
            return
        }

        [accountMotivatorInstance: accountMotivatorInstance]
    }

    def update(Long id, Long version) {
        def accountMotivatorInstance = AccountMotivator.get(id)
        if (!accountMotivatorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountMotivator.label', default: 'AccountMotivator'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (accountMotivatorInstance.version > version) {
                accountMotivatorInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'accountMotivator.label', default: 'AccountMotivator')] as Object[],
                          "Another user has updated this AccountMotivator while you were editing")
                render(view: "edit", model: [accountMotivatorInstance: accountMotivatorInstance])
                return
            }
        }

        accountMotivatorInstance.properties = params

        if (!accountMotivatorInstance.save(flush: true)) {
            render(view: "edit", model: [accountMotivatorInstance: accountMotivatorInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'accountMotivator.label', default: 'AccountMotivator'), accountMotivatorInstance.id])
        redirect(action: "show", id: accountMotivatorInstance.id)
    }

    def delete(Long id) {
        def accountMotivatorInstance = AccountMotivator.get(id)
        if (!accountMotivatorInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'accountMotivator.label', default: 'AccountMotivator'), id])
            redirect(action: "list")
            return
        }

        try {
            accountMotivatorInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'accountMotivator.label', default: 'AccountMotivator'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'accountMotivator.label', default: 'AccountMotivator'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def clearData(){
		def motivators = AccountMotivator.findAll()
		motivators.each{
			it.delete(flush:true)
		}
	}
	
}
