package org.motus

import grails.converters.*

import org.codehaus.groovy.grails.commons.GrailsDomainClass
import java.util.GregorianCalendar
import java.io.File
import java.io.FileInputStream
import org.motus.common.WorkoutPlanStatus

class WorkoutPlanOptionService {

	static scope = "singleton"
	static transactional = true
    def grailsApplication
	
	def json = null
	def file = null
	def filePath = "/WEB-INF/resources/plans.json"
	InputStream inputStream
	
	
	def refresh(){
		json = null
		checkSetJson()
	}
	
	
	def getWorkoutPlans(){
		checkSetJson()		
		return json
	}
	
	
	def addMetaData(){
		json.each { plan ->
			def weeksCount = plan.value.workoutPlan.size()
			def workoutsCount = 0
			plan.value.workoutPlan.each { weeks -> 
				weeks.each{
					workoutsCount++
				}
			}
			plan.value["weeksCount"] = weeksCount
			plan.value["workoutsCount"] = workoutsCount
			def plansCount = WorkoutPlan.countByNameAndStatus(plan.value.name, WorkoutPlanStatus.INCOMPLETE.description())
			plan.value["plansCount"] = plansCount
		}
		
	}
	
	
	def getWorkoutPlan(name){
		checkSetJson()
		return json[name]		
	}
	
	def checkSetJson(){
		if(!json){
			file = grailsApplication.mainContext.getResource(filePath).file
			inputStream = new FileInputStream(file);
			json = JSON.parse(inputStream, "UTF-8")
			addMetaData()
		}
	}
	
}
