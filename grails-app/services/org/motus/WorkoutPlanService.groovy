package org.motus

import org.codehaus.groovy.grails.commons.GrailsDomainClass
import java.util.GregorianCalendar
import org.motus.common.WorkoutPlanStatus
import org.motus.log.AdjustmentLog
import org.motus.workouts.PlannedWorkout
import org.motus.WorkoutPlan


class WorkoutPlanService {

	static scope = "singleton"
	static transactional = true
    def grailsApplication

	def createWorkoutPlan(account, plan, startDate){
		
		def startDateCal = startDate.toCalendar()
		
		def firstDayOfWeek = startDateCal.get(Calendar.DAY_OF_WEEK)				
		def weekOfYear = startDateCal.get(Calendar.WEEK_OF_YEAR)		

	
		def workoutPlan = new WorkoutPlan()
		workoutPlan.account = account	
		workoutPlan.name = plan.name
		workoutPlan.startDate = startDate
		workoutPlan.originalStartDate = startDate
		workoutPlan.description = plan.description
		workoutPlan.status = WorkoutPlanStatus.INCOMPLETE.description()
		
		if(!workoutPlan.save(flush:true)){
			throw Exception("Unable to save workout plan")
			return false
		}
	
		def i = 0
		def completeDate
		def year = startDateCal.get(Calendar.YEAR)
		def dayOfYear = startDateCal.get(Calendar.DAY_OF_YEAR)
		def originalYear = startDateCal.get(Calendar.YEAR)
		def originalWeekOfYear = startDateCal.get(Calendar.WEEK_OF_YEAR)
		
		if(dayOfYear > 362){
			originalWeekOfYear = 52
			weekOfYear = 52
		}
		
		plan.workoutPlan.each(){
		
			if(weekOfYear < originalWeekOfYear){
				year++
			}
			
			startDateCal.set(Calendar.WEEK_OF_YEAR, weekOfYear)
			startDateCal.set(Calendar.DAY_OF_WEEK, firstDayOfWeek)
			startDateCal.set(Calendar.YEAR, year)
		
			def m = 1
			it.each(){ day, workoutObj ->
			
				if(workoutObj.workout != "Rest"){

					def date = startDateCal.time
					def plannedWorkoutDate =  date + Integer.parseInt(day)
	
					GrailsDomainClass dc = grailsApplication.getDomainClass( "org.motus.workouts.${workoutObj.workout}" )
					
					def plannedWorkout = dc.clazz.newInstance()
					
					plannedWorkout.completed = false
					plannedWorkout.includeAbRipper = workoutObj.includeAbs
			    	
					plannedWorkout.plannedWorkoutDate = plannedWorkoutDate
					plannedWorkout.originalPlannedWorkoutDate = plannedWorkoutDate
					
					plannedWorkout.workoutPlan = workoutPlan
			    	
					plannedWorkout.save(flush:true)
					
					workoutPlan.addToPlannedWorkouts(plannedWorkout)
					
					if(completeDate < plannedWorkoutDate)
						completeDate = plannedWorkoutDate
						
				}
	
			}
		
			weekOfYear++
		}
		
		workoutPlan.plannedCompleteDate = completeDate
		workoutPlan.originalPlannedCompleteDate = completeDate
		
		workoutPlan.save(flush:true)
		
		account.addToPermissions("workoutPlan:edit:${workoutPlan.id}")
		account.save(flush:true)
		
		return workoutPlan
		
	}
	
	
	def adjustWorkoutPlan(org.motus.WorkoutPlan workoutPlan, java.lang.Integer adjustment){
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
		
		if(!adjustmentLog.save(flush:true)){
			return false
		}
		
		workoutPlan.save(flush:true)
		
		return true
	}
	
	
	/**
	def adjustWorkouts(adjustment, workoutPlan){
		workoutPlan.plannedWorkouts.each { workout ->
			def date = workout.plannedWorkoutDate + adjustment
			println "${workout.plannedWorkoutDate} : ${adjustment} : ${date}"
			workout.plannedWorkoutDate = date
			workout.save(flush:true)
		}		
	}
	
	
	def getDifference(org.motus.workouts.ChestBack plannedWorkout){
		return plannedWorkout.actualWorkoutDate - plannedWorkout.plannedWorkoutDate
	}
	**/
	
	def getWorkoutPlan(account){
		return WorkoutPlan.findByAccountAndStatus(account, WorkoutPlanStatus.INCOMPLETE.description())		
	}
	
}
