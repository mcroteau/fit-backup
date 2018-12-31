package org.motus.common

public enum CompetitionStatus {

	ACTIVE('ACTIVE'),
	INACTIVE('INACTIVE')
	
	private final String description
	
	CompetitionStatus(String description){
		this.description = description
	}
	
	public String description(){
		return this.description
	}
	
}
