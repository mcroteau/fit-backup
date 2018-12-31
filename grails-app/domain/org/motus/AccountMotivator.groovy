package org.motus

class AccountMotivator {

	Account account
	Account motivator
	
	String status
	
	String comment
	
	static belongsTo = [ account : Account, motivator : Account ]
		
    static constraints = {
		status(nullable:false)
		id generator: 'sequence', params:[sequence:'ID_ACCOUNT_MOTIVATOR_PK_SEQ']
    }
	
}
