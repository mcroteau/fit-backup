package org.motus

import org.motus.workouts.PlannedWorkout
import org.motus.WorkoutPlanStats
import org.motus.ProgressPhotos


class WorkoutPlan {

	String name
	String status
	String description
	
	Date startDate
	Date originalStartDate
	
	Date plannedCompleteDate
	Date originalPlannedCompleteDate
	
	Date actualCompleteDate

	Date dateCreated
	Date lastUpdated
		
	Account account
	Competition competition
	static belongsTo = [Account, Competition]
	
	
	
	static hasMany = [ plannedWorkouts : PlannedWorkout, beforePhotos : ProgressPhotos, thirtyDayPhotos : ProgressPhotos, sixtyDayPhotos : ProgressPhotos, afterPhotos : ProgressPhotos ]
	
    static constraints = {
		status(nullable:true)
		name(nullable:true)
		description(nullable:true)
		startDate(nullable:true)
		originalStartDate(nullable:true)
		plannedCompleteDate(nullable:true)
		originalPlannedCompleteDate(nullable:true)
		actualCompleteDate(nullable:true)
		beforePhotos(nullable:true)
		thirtyDayPhotos(nullable:true)
		sixtyDayPhotos(nullable:true)
		afterPhotos(nullable:true)
		competition(nullable:true)
		id generator: 'sequence', params:[sequence:'ID_WORKOUT_PLAN_PK_SEQ']
    }

}
