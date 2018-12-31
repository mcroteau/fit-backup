package org.motus.common

public enum MotivatorRequestStatus {

	APPROVED('APPROVED'),
	PENDING('PENDING'),
	DENIED('DENIED')
	
	private final String description
	
	MotivatorRequestStatus(String description){
		this.description = description
	}
	
	public String description(){
		return this.description
	}
	
}
