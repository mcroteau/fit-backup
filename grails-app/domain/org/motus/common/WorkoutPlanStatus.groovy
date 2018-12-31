package org.motus.common

public enum WorkoutPlanStatus {

	COMPLETE('Workout Plan Completed'),
	INCOMPLETE('Workout Plan Incomplete'),
	STOPPED('Stopped Workout Plan')
	
	private final String description
	
	WorkoutPlanStatus(String description){
		this.description = description
	}
	
	public String description(){
		return this.description
	}
	
}
