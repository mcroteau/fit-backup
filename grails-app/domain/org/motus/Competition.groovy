package org.motus

import org.motus.CompetitionInvite
import org.motus.common.CompetitionStatus

//http://ryanalberts.com/92/grails-findby-for-hasmany-relationship/

class Competition {

	Date startDate
	Date endDate
	
	String program
	String description
	
	String status
	
	static belongsTo = Account
	
	static hasMany = [ members : Account, workoutPlans : WorkoutPlan, invites : CompetitionInvite ]
	
	
    static Competition findAllByMembers(Account account){
    	def c = Competition.createCriteria()
    	def result = c.get {
    	   members {
    	      idEq(account.id)
    	   }
    	}
    	return result;
	}
	
	static Competition findAllByMembersAndStatus(Account account, String status){
		def c = Competition.createCriteria()
		def result = c.get {
		   eq("status", status)
		   members {
		      idEq(account.id)
		   }
		}
		return result;
	}

	
    static constraints = {
		startDate(nullable:false)
		endDate(nullable:false)
		program(nullable:false)
		description(nullable:false)
		status(nullable:false)
		id generator: 'sequence', params:[sequence:'ID_COMPETITION_SEQ']
    }
	
}
