package org.motus

import org.motus.WorkoutPlan
import org.motus.Photo

class ProgressPhotos {
	
	WorkoutPlan workoutPlan
	
	static hasMany = [ photos : Photo ]
	static belongsTo = { workoutPlan: WorkoutPlan }
	
    static constraints = {
		id generator: 'sequence', params:[sequence:'ID_PROGRESS_PHOTOS_SEQ']
    }
	
}
