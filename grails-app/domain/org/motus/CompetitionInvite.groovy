package org.motus

class CompetitionInvite {

	String status
	
	Date dateInvited
	Date dateAccepted
	Date dateIgnored
	
	Date dateAcknowledged
	
	Date dateCreated
	Date dateUpdated
	
	static belongsTo = [ competition : Competition, account : Account ]
		
    static constraints = {
		status(nullable:false)
		dateInvited(nullable:false)
		dateAccepted(nullable:true)
		dateIgnored(nullable:true)
		dateAcknowledged(nullable:true)
		id generator: 'sequence', params:[sequence:'ID_COMPETITION_INVITE_SEQ']
    }
}
