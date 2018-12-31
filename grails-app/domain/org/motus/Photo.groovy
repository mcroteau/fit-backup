package org.motus

class Photo {
	
	Date dateCreated
	Date dateUpdated
	
	Date dateTaken
	
	String name
	String description
	String link
	
    static constraints = {
		id generator: 'sequence', params:[sequence:'ID_PHOTO_SEQ']
    }
	
}
