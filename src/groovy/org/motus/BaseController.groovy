package org.motus

import org.motus.common.RoleName
import org.apache.shiro.SecurityUtils
import org.motus.workouts.PlannedWorkout
import org.motus.Account
import org.motus.log.SkippedWorkoutLog
import org.motus.common.CompetitionStatus

import org.motus.AppConstants

//http://mrpaulwoods.wordpress.com/2011/01/23/a-pattern-to-simplify-grails-controllers/

class BaseController {
	
	private def authenticated(Closure c){
		def subject = SecurityUtils.getSubject();
	
		if(!subject.isAuthenticated()){
			flash.message = "Please sign in to continue"
			forward(controller : 'auth', action: 'login')
		}
		
		c.call subject
	}
	
	private def authenticatedAccount(Closure c){
		def subject = SecurityUtils.getSubject();
	
		if(!subject.isAuthenticated()){
			flash.message = "Please sign in to continue"
			forward(controller : 'auth', action: 'login')
		}
		
		def account = Account.findByUsername(subject.principal)
		
		if(!account){
			flash.message = "Please login to continue"
			forward(controller: 'auth', action: 'login')
			return
		}else{
			c.call account
		}
		
	}
	






	private def withPlannedWorkout(Closure c) {
	
		def subject = SecurityUtils.getSubject();
	
		if(!subject.isAuthenticated()){
			flash.message = "Please sign in to continue"
			forward(controller : 'auth', action: 'login')
		}
			
		if(!params.id){
			flash.message = "Workout not found"
			forward(controller : 'account', action: 'dashboard')
		}
	
		if(subject.isPermitted("workoutPlan:edit:${params.id}") ||
			subject.hasRole(RoleName.ROLE_ADMIN.description())){
			
			def plannedWorkout = PlannedWorkout.get(params.id)		
			if (!plannedWorkout) {
			    flash.message = "Workout not found"
			    forward(controller: "account", action:'dashboard')
			    return
			}
			
			c.call plannedWorkout
		}else{
			flash.message = "Unauthorized to access workout"
			forward(controller : 'account', action: 'dashboard')
		}
		
    }
	
	
	
	private def withPlannedWorkoutAndAccount(Closure c) {
		def subject = SecurityUtils.getSubject();
    	
		if(!subject.isAuthenticated()){
			flash.message = "Please sign in to continue"
			forward(controller : 'auth', action: 'login')
		}
		
		if(!params.id){
			flash.message = "Workout not found"
			forward(controller : 'account', action: 'dashboard')
		}
	
		if(subject.isPermitted("workoutPlan:edit:${params.id}") ||
			subject.hasRole(RoleName.ROLE_ADMIN.description())){
			
			def plannedWorkout = PlannedWorkout.get(params.id)		
			if (!plannedWorkout) {
			    flash.message = "Workout not found"
			    forward(controller: "account", action:'dashboard')
			    return
			}
		
			def account = Account.findByUsername(subject.principal)
			
			c.call plannedWorkout account
			
		}else{
			flash.message = "Unauthorized to access workout"
			forward(controller : 'account', action: 'dashboard')
		}
		
	}
	
	
	
	
	private def withPlannedWorkoutUpdated(Closure c){
	
		def subject = SecurityUtils.getSubject();
		
		if(!subject.isAuthenticated()){
			flash.message = "Please sign in to continue"
			forward(controller : 'auth', action: 'login')
		}
		
		if(!params.id){
			flash.message = "Workout not found"
			forward(controller : 'account', action: 'dashboard')
		}
	
		if(subject.isPermitted("workoutPlan:edit:${params.id}") ||
			subject.hasRole(RoleName.ROLE_ADMIN.description())){
			
			def plannedWorkout = PlannedWorkout.get(params.id)		
			if (!plannedWorkout) {
			    flash.message = "Workout not found"
			    forward(controller: "account", action:'dashboard')
			    return
			}
		
			//first update, used later if entry date is different from planned date
			def firstUpdate = false
			if(!plannedWorkout.updated){
				firstUpdate = true
				println "LOG FIRST LOGGED ENTRY STAT"
			}
			
			
			plannedWorkout.properties = params
		
			if(params.notes){
				plannedWorkout.notes = params.notes
			}
		
			if(params.abRipperCompleted)
				plannedWorkout.abRipperCompleted = params.abRipperCompleted
		
		
			plannedWorkout.updated = true
			plannedWorkout.completed = true
			plannedWorkout.skipped = false
				
			
			println "SHOULD UPDATE updated ${plannedWorkout.updated}"
		
			if(!plannedWorkout.actualWorkoutDate)plannedWorkout.actualWorkoutDate = new Date()
		
			if(params.actualWorkoutDate){
				println "ACTUAL WORKOUT DATE ${params.actualWorkoutDate}"
				plannedWorkout.actualWorkoutDate = params.actualWorkoutDate
			}
		
		
			if (!plannedWorkout.save(flush: true)) {
			    render(view: AppConstants.ENTRY_VIEW, model: [plannedWorkout: plannedWorkout])
			    return
			}else{

				println "\n\nDIFFERENCE : ${ differenceInEntryDate(plannedWorkout)}"
				if(firstUpdate && differenceInEntryDate(plannedWorkout)){
					println "FIRST UPDATE, DIFFERNCE IN ENTRY DATE!"
					redirect(controller: 'workoutPlan', action: 'adjustmentSelect', params:[ workoutPlanId : plannedWorkout.workoutPlan.id, plannedWorkoutId : plannedWorkout.id ])
					return
				}
			
				c.call plannedWorkout
			}
			
				
		}else{
			flash.message = "Unauthorized to access workout"
			forward(controller : 'account', action: 'dashboard')
		}
	}
	
	
	

	
	def withPlannedWorkoutRecovered(Closure c) {
	
		def subject = SecurityUtils.getSubject();
		
		if(!subject.isAuthenticated()){
			flash.message = "Please sign in to continue"
			forward(controller : 'auth', action: 'login')
		}
			
			
		if(!params.id){
			flash.message = "Workout not found"
			forward(controller : 'account', action: 'dashboard')
		}	
			
			
		if(subject.isPermitted("workoutPlan:edit:${params.id}") ||
			subject.hasRole(RoleName.ROLE_ADMIN.description())){
			
			def plannedWorkout = PlannedWorkout.get(params.id)		
			def account = Account.findByUsername(subject.principal)
		
			if (!plannedWorkout) {
			    flash.message = "Workout not found"
			    forward(controller: "account", action:'dashboard')
			    return
			}
		
			
			plannedWorkout.skipped = false
			if(!plannedWorkout.save(flush:true)){
				flash.message = "Something went wrong, please try again."
				forward(controller:'account', action:'dashboard')
			}
		
		
			def skipLog = SkippedWorkoutLog.findByPlannedWorkoutAndAccount(plannedWorkout, account)
			skipLog.recovered = true
		
			if(!skipLog.save(flush:true)){
				flash.message = "Something went wrong, please try again."
				forward(controller:'account', action:'dashboard')
			}
		
			c.call plannedWorkout
			
		}else{
			flash.message = "Unauthorized to access workout"
			forward(controller : 'account', action: 'dashboard')
		}	
				
	}
	
	
	
	def withPlannedWorkoutSkipped(Closure c) {
	
		def subject = SecurityUtils.getSubject();
		
		if(!subject.isAuthenticated()){
			flash.message = "Please sign in to continue"
			forward(controller : 'auth', action: 'login')
		}
			
		if(!params.id){
			flash.message = "Workout not found"
			forward(controller : 'account', action: 'dashboard')
		}	
			
			
		if(subject.isPermitted("workoutPlan:edit:${params.id}") ||
			subject.hasRole(RoleName.ROLE_ADMIN.description())){
			
			def plannedWorkout = PlannedWorkout.get(params.id)		
			def account = Account.findByUsername(subject.principal)
		
			if (!plannedWorkout) {
			    flash.message = "Workout not found"
			    forward(controller: "account", action:'dashboard')
			    return
			}
			
			plannedWorkout.skipped = true
			plannedWorkout.skippedDate = new Date()
			if(!plannedWorkout.save(flush:true)){
				flash.message = "Something went wrong, please try again."
				forward(controller:'account', action:'dashboard')
			}
		
		
			def skipLog = new SkippedWorkoutLog()
			skipLog.plannedWorkout = plannedWorkout
			skipLog.date = new Date()
			skipLog.account = account
			
			if(!skipLog.save(flush:true)){
				flash.message = "Something went wrong, please try again."
				forward(controller:'account', action:'dashboard')
			}
		
			c.call plannedWorkout
			
		}else{
			flash.message = "Unauthorized to access workout"
			forward(controller : 'account', action: 'dashboard')
		}

	}
	
	
	private def withWorkoutPlan(Closure c) {
	
		def subject = SecurityUtils.getSubject();
	
		if(!subject.isAuthenticated()){
			flash.message = "Please sign in to continue"
			forward(controller : 'auth', action: 'login')
		}
			
		if(!params.id){
			flash.message = "Workout plan not found"
			forward(controller : 'account', action: 'dashboard')
		}	
			
		if(subject.isPermitted("workoutPlan:edit:${params.id}") ||
			subject.hasRole(RoleName.ROLE_ADMIN.description())){
			def workoutPlan = WorkoutPlan.findById(params.id)
			c.call workoutPlan
		}else{
			flash.message = "Unauthorized to access workout"
			forward(controller : 'account', action: 'dashboard')
		}
    }
	
	
	
	private def withWorkoutPlanAndPlannedWorkout(Closure c){
	
		def subject = SecurityUtils.getSubject();
    	
		if(!subject.isAuthenticated()){
			flash.message = "Please sign in to continue"
			forward(controller : 'auth', action: 'login')
		}
			
		if(!params.workoutPlanId){
			println "\n\n REDIRECT FROM ADJUST SELECT : ${params}"
			flash.message = "Workout Plan not found"
			//forward(controller : 'account', action: 'dashboard')
			redirect(controller : 'account', action: 'dashboard')
			return
		}	
			
		if(!params.plannedWorkoutId){
			flash.message = "Workout not found"
			forward(controller : 'account', action: 'dashboard')
		}	
			
		if(subject.isPermitted("workoutPlan:edit:${params.workoutPlanId}") ||
			subject.hasRole(RoleName.ROLE_ADMIN.description())){
			
			def workoutPlan = WorkoutPlan.findById(params.workoutPlanId)
			def plannedWorkout = PlannedWorkout.get(params.plannedWorkoutId)	
			
			if(!workoutPlan){
				flash.message = "Workout Plan not found"
				redirect(controller : 'account', action: 'dashboard')
				return
			}
			
			if(!plannedWorkout){
				flash.message = "Workout not found"
				forward(controller : 'account', action: 'dashboard')
			}
			
			c.call workoutPlan, plannedWorkout
		}else{
			flash.message = "Unauthorized to access workout"
			forward(controller : 'account', action: 'dashboard')
		}
	}
	
	
	
	def withCompetitionAndAccount(Closure c){
		def subject = SecurityUtils.getSubject();
		
		if(!subject.isAuthenticated()){
			flash.message = "Please sign in to continue"
			forward(controller : 'auth', action: 'login')
			
		}else{
		
			def account = Account.findByUsername(subject.principal)
			
			def competition = Competition.findAllByMembersAndStatus(account, CompetitionStatus.ACTIVE.description())
			
			if(!competition){
				flash.message = "You currently have no competitions started ... "
				forward(controller : 'competition', action: 'setup')
			}else{		
				c.call competition, account
			}	
		}
	}
	
	
	
	
	def differenceInEntryDate(plannedWorkout){
		return (plannedWorkout.plannedWorkoutDate > plannedWorkout.actualWorkoutDate || plannedWorkout.plannedWorkoutDate < plannedWorkout.actualWorkoutDate)
	}
	
	
	def getDifference(plannedWorkout, entryDate){
		def diff = plannedWorkout.plannedWorkoutDate - entryDate
		return diff
	}
	
}