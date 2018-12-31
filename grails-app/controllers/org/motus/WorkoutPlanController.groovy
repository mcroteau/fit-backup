package org.motus

import org.springframework.dao.DataIntegrityViolationException
import org.codehaus.groovy.grails.commons.GrailsDomainClass
import java.util.GregorianCalendar
import org.apache.shiro.SecurityUtils
import org.motus.common.WorkoutPlanStatus
import org.motus.Account
import org.motus.workouts.PlannedWorkout
import org.motus.BaseController
import grails.converters.*
import org.motus.log.AdjustmentLog


@Mixin(BaseController)
class WorkoutPlanController {

	def accountService
	def workoutPlanService
	def workoutPlanOptionService
	
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]


	def adjustmentSelect(){
		withWorkoutPlanAndPlannedWorkout { workoutPlan, plannedWorkout ->
			[workoutPlan : workoutPlan, plannedWorkout : plannedWorkout]
		}
	}


	def confirmAdjustment(){
		withWorkoutPlanAndPlannedWorkout { workoutPlan, plannedWorkout ->
		
			def adjustment = getDifference(plannedWorkout)
			def completeDate = workoutPlan.plannedCompleteDate + adjustment
			def completeDateCal = completeDate.toCalendar()
			
			[ workoutPlan : workoutPlan, plannedWorkout : plannedWorkout, completeDateCal : completeDateCal ]
		}	
	}



	def adjust(){
		withWorkoutPlanAndPlannedWorkout { workoutPlan, plannedWorkout ->
			def adjustment = getDifference(plannedWorkout)
			workoutPlan.plannedCompleteDate = workoutPlan.plannedCompleteDate + adjustment
			
			adjustWorkouts(adjustment, workoutPlan)
			
			def adjustmentLog = new AdjustmentLog()
			adjustmentLog.account = workoutPlan.account
			adjustmentLog.workoutPlan = workoutPlan
			
			adjustmentLog.startDate = workoutPlan.startDate
			workoutPlan.startDate = workoutPlan.startDate + adjustment
			adjustmentLog.adjustedStartDate = workoutPlan.startDate
			
			adjustmentLog.plannedCompleteDate = workoutPlan.plannedCompleteDate
			workoutPlan.plannedCompleteDate = workoutPlan.plannedCompleteDate + adjustment
			adjustmentLog.adjustedPlannedCompleteDate = workoutPlan.plannedCompleteDate
			
			adjustmentLog.adjustment = adjustment
			
			adjustmentLog.save(flush:true)
			workoutPlan.save(flush:true)
			
			flash.message = "Successfully adjusted schedule"
			redirect(controller:'account', action:'dashboard')
		}
	}
	
	


	def adjustWorkouts(adjustment, workoutPlan){
		workoutPlan.plannedWorkouts.each { workout ->
			def date = workout.plannedWorkoutDate + adjustment
			println "${workout.plannedWorkoutDate} : ${adjustment} : ${date}"
			workout.plannedWorkoutDate = date
			workout.save(flush:true)
		}		
	}
	
	
	
	def revert(workoutPlan){
		workoutPlan.plannedWorkouts.each { workout ->
			workout.originalPlannedWorkoutDate = workout.plannedWorkoutDate
			workout.save(flush:true)
			flash.message = "Something went wrong"
			redirect(controller : 'account', action: 'dashboard')
		}
	}
	
	
	
	def getDifference(plannedWorkout){
		return plannedWorkout.actualWorkoutDate - plannedWorkout.plannedWorkoutDate
	}

	

	def refresh(){
		workoutPlanOptionService.refresh()
		flash.message = "Successfully refreshed plans"
	}
	
	
	
	def select(){
		authenticated { subject ->
			workoutPlanOptionService.refresh()
			def workoutPlans = workoutPlanOptionService.getWorkoutPlans()
			[ workoutPlans : workoutPlans ]
		}
	}
	


	def setup(){
		authenticated { subject ->
		
			if(params.workoutPlan){
				def workoutPlan = workoutPlanOptionService.getWorkoutPlan(params.workoutPlan)
				
				[ workoutPlan : workoutPlan ]		
			}else{
				flash.message = ""
				redirect(controller:'account', action:'dashboard')
			}
		}
	}
	
	

	def start(){

		authenticatedAccount { account ->
			
			def currentPlan = WorkoutPlan.findByAccountAndStatus(account, WorkoutPlanStatus.INCOMPLETE.description())
			
			if(currentPlan){
				redirect controler:'account', action: 'dashboard'
			}
			
			def plan = workoutPlanOptionService.getWorkoutPlan(params.workoutPlan)
			
			if(params.startDate){
				def workoutPlan = workoutPlanService.createWorkoutPlan( account, plan, params.startDate )
				if(workoutPlan){
					flash.message = "Congratulations on new beginnings"
					redirect controller:'account', action:'dashboard'
				}
			}else{
				flash.message = "Something went wrong..."
				redirect(controller:'account', action:'dashboard')
			}
			
		}
		
	}
	
	
	def current(){
		def subject = SecurityUtils.getSubject();
		
		if(subject.isAuthenticated()){
			
			def account = Account.findByUsername(subject?.getPrincipal())
			
			def currentPlan = WorkoutPlan.findByAccountAndStatus(account, WorkoutPlanStatus.INCOMPLETE.description())
			
			if(currentPlan){		
				[ currentPlan : currentPlan ]
			}else{
				flash.message = "You currently have no plan started"
				redirect action:'select'
			}
		}
	}
	
	
	
	def confirmStop(){
		authenticatedAccount { account ->
			
			def currentPlan = WorkoutPlan.findByAccountAndStatus(account, WorkoutPlanStatus.INCOMPLETE.description())
			
			if(currentPlan){		
			
				def workoutsCompleted = PlannedWorkout.countByWorkoutPlanAndCompleted(currentPlan, true)
			
				def totalWorkouts = PlannedWorkout.countByWorkoutPlan(currentPlan)
				def percentComplete = workoutsCompleted/totalWorkouts
				percentComplete = Math.round(percentComplete * 100)
			
				[currentPlan: currentPlan, workoutsCompleted: workoutsCompleted, totalWorkouts: totalWorkouts, percentComplete: percentComplete]
				
			}else{
				flash.message = "You currently have no plan started"
				redirect action:'select'
			}
		}	
	}
	
	
	def stop(){

		authenticatedAccount { account ->
			
			def currentPlan = WorkoutPlan.findByAccountAndStatus(account, WorkoutPlanStatus.INCOMPLETE.description())
			
			if(currentPlan){		
				currentPlan.status = WorkoutPlanStatus.STOPPED.description()
				currentPlan.save(flush:true)
				flash.message = "successfully stopped workout plan. start a new one?"
				redirect action: 'select'
			}else{
				flash.message = "You currently have no plan started"
				redirect action:'select'
			}
		}
	}
	
	
    def index() {
        redirect(action: "list", params: params)
    }


    def list(Integer max) {
		authenticatedAccount { account ->
        	params.max = Math.min(max ?: 10, 100)
        	[workoutPlanInstanceList: WorkoutPlan.findAllByAccount(account), workoutPlanInstanceTotal: WorkoutPlan.countByAccount(account)]
		}
    }



    def show(Long id) {
		withWorkoutPlan {  workoutPlan ->
			[workoutPlanInstance: workoutPlan]
		}
    }



    def edit(Long id) {
		withWorkoutPlan {  workoutPlan ->
        	[workoutPlanInstance: workoutPlanInstance]
		}
    }



    def update(Long id, Long version) {
		withWorkoutPlan {  workoutPlan ->
        	workoutPlan.properties = params        	
        	if (!workoutPlan.save(flush: true)) {
        	    render(view: "edit", model: [workoutPlanInstance: workoutPlan])
        	    return
        	}
        	
        	flash.message = "Workout Plan Successfully Updated"
        	redirect(action: "show", id: workoutPlan.id)
		}
    }
	
	

    def delete(Long id) {
		withWorkoutPlan {  workoutPlanInstance ->
        	try {
        	    workoutPlanInstance.delete(flush: true)
        	    flash.message = "Successfully Deleted workout plan"
        	    redirect(action: "list")
        	
			}catch (DataIntegrityViolationException e) {
        	    flash.message = "Something went wrong, please try again"
        	    redirect(action: "show", id: id)
        	}
		}
    }
}
