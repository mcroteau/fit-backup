package org.motus.common

public enum CompetitionInviteStatus {

	ACTIVE('ACTIVE'),
	PENDING('PENDING'),
	IGNORED('IGNORED')
	
	private final String description
	
	CompetitionInviteStatus(String description){
		this.description = description
	}
	
	public String description(){
		return this.description
	}
	
}
