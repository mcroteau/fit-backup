package org.motus

import org.motus.workouts.PlannedWorkout
import org.motus.WorkoutPlan

import java.util.List

class DashboardData {
	
	static mapWith = "none"
	
	String workouts
	
	WorkoutPlan currentPlan
	List<WorkoutPlan> workoutPlans
	
	PlannedWorkout todaysWorkout
	
	List<PlannedWorkout> completedWorkouts
	List<PlannedWorkout> incompleteWorkouts
	List<PlannedWorkout> skippedWorkouts
	List<PlannedWorkout> skippedAbRipperWorkouts

}
