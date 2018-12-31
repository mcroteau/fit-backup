package org.motus

import org.motus.WorkoutPlan
import org.motus.Account

class CompetitionMember {	

	String status
	String competitionStatus
	
	Date dateInvited
	Date dateAccepted
	
	Date dateCreated
	Date dateUpdated
	
	boolean isOrganizer
	
	static belongsTo = [ workoutPlan : WorkoutPlan, account : Account ]
		
    static constraints = {
		status(nullable:false)
		competitionStatus(nullable:false)
		dateInvited(nullable:false)
		dateAccepted(nullable:false)
		id generator: 'sequence', params:[sequence:'ID_COMPETITION_MEMBER_SEQ']
    }
	
}
