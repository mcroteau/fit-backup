package org.motus

import org.springframework.dao.DataIntegrityViolationException
import org.apache.shiro.crypto.hash.Sha256Hash
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.SecurityUtils
import grails.converters.*
import java.util.UUID
import org.motus.common.RoleName
import org.motus.log.ResetPasswordLog
import org.motus.common.WorkoutPlanStatus
import org.motus.common.MotivatorRequestStatus
import org.motus.workouts.PlannedWorkout
import org.motus.WorkoutPlan
import org.motus.AccountMotivator
import org.motus.Account
import groovy.text.SimpleTemplateEngine
import org.motus.BaseController

@Mixin(BaseController)
class AccountController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST", register: "POST"]

	def mailService
	def accountService

    def index() {
        redirect(action: "list", params: params)
    }

	def registration(){
		[accountInstance: new Account(params)]
	}
    
	def register(){
	
		params.ipAddress = request.getRemoteHost()
		def account = accountService.createAccountInstance(params)
		
		if(accountService.register(account)){
			flash.message = "You have successfully registered... "
			
			def host = "localhost:8080"
			
			File templateFile = grailsAttributes.getApplicationContext().getResource( File.separator + "templates" + File.separator + "email" + File.separator + "registration.gtpl").getFile();

			def binding = [ "imageLocation" : "http://${host}/${grailsApplication.config.app.name}/images/logo.png" ]
			def engine = new SimpleTemplateEngine()
			def template = engine.createTemplate(templateFile).make(binding)
			def bodyString = template.toString()
			
			
			/**
			mailService.sendMail {
			   to account.email
			   from "croteau.mike@gmail.com"
			   subject "[App] Successfully Registered"
			   html bodyString
			}
			**/
			
			redirect(controller : 'auth', action: 'signIn', params : [accountInstance: account, username : params.username, password : params.passwordHash, new_account : true])
			
		}else{
		
			flash.message = "There was a problem with your registration, please try again or contact the administrator"
			render(view: "registration", model: [accountInstance: account])
			return
		}
		
	}
	
	
	
	def dashboard(){

		authenticatedAccount { account ->
		
			def dashboardData = accountService.getDashboardData(account.username)
			if(dashboardData?.currentPlan){
				def endDateCal = dashboardData.currentPlan.plannedCompleteDate.toCalendar()
				[ dashboardData : dashboardData, endDateCal : endDateCal]
			}
			
		}
	
	}
	
	
	def competition(){
		println "COMPETITION"
		withCompetitionAndAccount { competition, account ->
			println " MADE IT "
			[competition : competition]
		}
	}
	
	
	def workouts(){

		authenticatedAccount { account ->
			def data = accountService.getDashboardData(account.username)
			render data.workouts
		}
	
	}
	
	
	
	def info(String username){
		println "INFO********************"
		if(username){
			def account = Account.findByUsername(username)
			
			def subject = SecurityUtils.getSubject();
			
			def reviewer
			if(subject.isAuthenticated())reviewer = Account.findByUsername(subject.principal)
			
			def motivator 
			if(reviewer)motivator = AccountMotivator.findByAccountAndMotivator(account, reviewer)
			
			[accountInstance : account, motivator : motivator]
			
		}else{
			flash.message = ""
			redirect action:'profile'
		}
	}

	
	
	
	
	def profile(Account account){
		authenticatedAccount { accountInstance ->
			def registeredCal = accountInstance.dateCreated.toCalendar()
			[accountInstance : accountInstance, registeredCal : registeredCal]
		}
	}
	
	
	def forgot(){}
	
	
	def sendResetEmail(){
		
		if(params.email){

			def account = Account.findByEmail(params.email)
			
			if(account){
			
				def resetUUID = UUID.randomUUID()
				account.resetUUID = resetUUID
				account.save(flush:true)
			
				def resetLog = new ResetPasswordLog()
				resetLog.date = new Date()
				resetLog.account = account
				resetLog.originalPassword = account.passwordHash
				resetLog.completed = false
				resetLog.save(flush:true)
			
				//add email logic
				//render(view:'confirmation')
				
				println "account ${account.resetUUID}  resetLog : ${ResetPasswordLog.count()}"
				
			}else{
				flash.message = "Account not found with following email address : ${params.email}"
				redirect(action:'forgot')
			}
		
		}else{
			flash.message = "Please enter an email to continue the reset password process"
			redirect(action:'forgot')
		}
	}
	
	
	
	def confirmReset(){
		def account = Account.findByUsernameAndResetUUID(params.username, params.uuid)
		if(account){
			request.username = account.username
		}else{
			flash.message = "Something went wrong, please try again."
			render view: 'forgot'
		}		
	}
	
	
	
	def resetPassword(){
		def username = params.username
		def newPassword = params.password
		
		def account = Account.findByUsername(username)
		
		account.passwordHash = new Sha256Hash(newPassword).toHex()
		if(account.save(flush:true)){
		
			def authToken = new UsernamePasswordToken(username, newPassword as String)
			
			redirect(controller : "auth", action : "signIn", params : [username : username, password : newPassword, reset : true])
			
		}else{
			flash.message = "We were unable to reset your password, please try again."
			redirect(action:'confirmReset', params : [username : username, uuid : account.resetUUID ])
		}	
	}
	

	
	def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [accountInstanceList: Account.list(params), accountInstanceTotal: Account.count()]
    }

    def create() {
        [accountInstance: new Account(params)]
    }

    def save() {
        def accountInstance = new Account(params)
        if (!accountInstance.save(flush: true)) {
            render(view: "create", model: [accountInstance: accountInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'account.label', default: 'Account'), accountInstance.id])
        redirect(action: "show", id: accountInstance.id)
    }

    def show(Long id) {
        def accountInstance = Account.get(id)
        if (!accountInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'account.label', default: 'Account'), id])
            redirect(action: "list")
            return
        }

        [accountInstance: accountInstance]
    }

    def edit(Long id) {
        def accountInstance = Account.get(id)
        if (!accountInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'account.label', default: 'Account'), id])
            redirect(action: "list")
            return
        }

        [accountInstance: accountInstance]
    }

    def update(Long id, Long version) {
        def accountInstance = Account.get(id)
        if (!accountInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'account.label', default: 'Account'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (accountInstance.version > version) {
                accountInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'account.label', default: 'Account')] as Object[],
                          "Another user has updated this Account while you were editing")
                render(view: "profile", model: [accountInstance: accountInstance])
                return
            }
        }

        accountInstance.properties = params

        if (!accountInstance.save(flush: true)) {
            render(view: "profile", model: [accountInstance: accountInstance])
            return
        }

        flash.message = "You have successfully updated your profile"
        redirect(action: "profile")
    }




    def delete(Long id) {
        def accountInstance = Account.get(id)
        if (!accountInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'account.label', default: 'Account'), id])
            redirect(action: "list")
            return
        }

        try {
            accountInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'account.label', default: 'Account'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'account.label', default: 'Account'), id])
            redirect(action: "show", id: id)
        }
    }
}
