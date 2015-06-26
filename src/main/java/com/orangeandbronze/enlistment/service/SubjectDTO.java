package com.orangeandbronze.enlistment.service;

import com.orangeandbronze.enlistment.domain.Subject;

public class SubjectDTO {
	public final String subjectId;
	public final String prerequisiteId;
	
	private SubjectDTO(Subject subject){
		subjectId = subject.getSubjectId();
		prerequisiteId = subject.getPrerequisite().getSubjectId();
	}

	public SubjectDTO(String subjectId, String prereqId){
		this.subjectId = subjectId;
		prerequisiteId = prereqId;
	}
	
	public SubjectDTO(String subjectId){
		this(subjectId, "NONE");
	}
	
	public static SubjectDTO getInfoOf(Subject subject){
		return new SubjectDTO(subject);
	}
	
	@Override
	public String toString(){
		return subjectId;
	}
}
