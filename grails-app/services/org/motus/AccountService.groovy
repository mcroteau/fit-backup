package org.motus

import org.apache.shiro.crypto.hash.Sha256Hash
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.SecurityUtils
import org.motus.common.RoleName
import org.motus.log.ResetPasswordLog
import org.motus.common.WorkoutPlanStatus
import org.motus.common.MotivatorRequestStatus
import org.motus.workouts.PlannedWorkout
import org.motus.WorkoutPlan
import org.motus.AccountMotivator
import org.motus.Account
import grails.converters.*
import java.util.UUID

class AccountService {
	
	static scope = "singleton"
	static transactional = true

	def createAccountInstance(params){
		def account = new Account(params)
		def	password = new Sha256Hash(params.passwordHash).toHex()
		account.passwordHash = password
		
		def role = Role.findByName(RoleName.ROLE_SIMPLE.description())
		account.addToRoles(role)
		
		return account
	}
	
	
	def register(account){		
		try{
			if(account){
				account.save(flush: true)
				println "Accounts : ${Account.count()} -> password : ${account.passwordHash}"
				return true			
			}
			return false
		}catch (Exception e){
			println e.printStackTrace();
			return false
		}
	}
	
	
	def getDashboardData(username){
	
		try{
	
			def account = Account.findByUsername(username)
			
			def workoutPlans = WorkoutPlan.findAllByAccount(account)
			def currentPlan = WorkoutPlan.findByAccountAndStatus(account, WorkoutPlanStatus.INCOMPLETE.description())
			
			println "account : ${account.username}, currentPlan : ${currentPlan}, workoutPlans ${workoutPlans}"
			
			def workouts = null
			
			if(currentPlan)workouts = new JSON(getFormattedCalendarJSON(currentPlan.plannedWorkouts))
			
			def completedWorkouts = PlannedWorkout.findAllByWorkoutPlanAndCompleted(currentPlan, true)
			def incompleteWorkouts = PlannedWorkout.findAllByWorkoutPlanAndCompletedAndSkipped(currentPlan, false, false)
			
			def skippedWorkouts = PlannedWorkout.findAllByWorkoutPlanAndCompletedAndSkipped(currentPlan, false, true)
			
			def skippedAbRipperWorkouts = PlannedWorkout.findAllByWorkoutPlanAndCompletedAndIncludeAbRipperAndAbRipperCompleted(currentPlan, true, true, false)
			
			def todayPre = new Date()
			Calendar cal = Calendar.getInstance(); // locale-specific
			cal.setTime(todayPre);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			long time = cal.getTimeInMillis();
			
			def today = new Date(time)
			
			def todaysWorkout = PlannedWorkout.findByWorkoutPlanAndPlannedWorkoutDate(currentPlan, today)
			
			
			println "# Completed : ${completedWorkouts.size()}"
			println "# Incomplete : ${incompleteWorkouts.size()}"
			println "# Skipped Abs : ${skippedAbRipperWorkouts.size()}"
			
			def dashboardData = new DashboardData();
			
			if(todaysWorkout)dashboardData.todaysWorkout = todaysWorkout
			
			dashboardData.workouts = workouts.toString()
			
			dashboardData.currentPlan = currentPlan
			dashboardData.workouts = workouts
			dashboardData.completedWorkouts = completedWorkouts
			dashboardData.incompleteWorkouts = incompleteWorkouts
			dashboardData.skippedWorkouts = skippedWorkouts
			dashboardData.skippedAbRipperWorkouts = skippedAbRipperWorkouts
			
			return dashboardData
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	def getFormattedCalendarJSON(plannedWorkouts){
		def entries = []
		
		def z = 0
		plannedWorkouts.each{ 
		
			def day   
			def month 
			def year
			def css = ''
			def action = 'show'
			
			
			if(it.completed){

				css = 'completed'
				action = 'show'
			
				day   = it.actualWorkoutDate.getAt(Calendar.DAY_OF_MONTH)
				month = it.actualWorkoutDate.getAt(Calendar.MONTH) + 1
				year  = it.actualWorkoutDate.getAt(Calendar.YEAR)	
			}else{
				day   = it.plannedWorkoutDate.getAt(Calendar.DAY_OF_MONTH)
				month = it.plannedWorkoutDate.getAt(Calendar.MONTH) + 1
				year  = it.plannedWorkoutDate.getAt(Calendar.YEAR)		
			}

			
			if(it.skipped)
				css = 'skipped'
				
				
			def entry = [
				id        : it.id,
				title     : it.displayName,
				url       : '../' + it.link + "/${action}/" + it.id,
				completed : it.completed,
				class     : css,
				start     : year + '-' + String.format("%02d", month) + '-' + String.format("%02d", day)
			]
    		
			entries.add(entry)
			z++
			
		}
		
		return entries
	}
	
	
}
