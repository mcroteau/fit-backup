package org.motus

class AccountNotification {

	Date dateCreated

	String uuid
	Account account
	String type
	int typeId
	String description
	String link
	
	static belongsTo = [account:Account]
	
    static constraints = {
		uuid(nullable:true)
		link(nullable:true)
		description(nullable:true)
    }
}
