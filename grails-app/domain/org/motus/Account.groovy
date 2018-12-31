package org.motus


class Account {

    String username
	String email
    String passwordHash
    
	String name
	Integer age
	String ipAddress
	
	Date dateCreated
	Date lastUpdated
	
	String resetUUID
	
    static hasMany = [ roles: Role, competitions : Competition, motivators: AccountMotivator, notifications: AccountNotification, permissions: String ]

    static constraints = {
		name(blank:true, nullable:true)
		age(blank:true, nullable:true)
		ipAddress(blank:true, nullable:true)
		resetUUID(nullable:true)
        username(nullable: false, blank: false, unique: true)
        email(email:true, nullable: false, blank: false, unique: true)
		passwordHash(nullable:false, blank:false)
		id generator: 'sequence', params:[sequence:'ID_ACCOUNT_PK_SEQ']
    }
	
}
