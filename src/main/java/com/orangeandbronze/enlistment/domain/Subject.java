package com.orangeandbronze.enlistment.domain;

import com.orangeandbronze.enlistment.service.SubjectDTO;

public class Subject {
	
	public static final Subject NONE = new Subject("NONE");
		
	private final String subjectId;
	private final Subject prerequisite;
	
	public Subject(String subjectId, Subject prerequisite) {
		this.subjectId = subjectId;
		this.prerequisite = prerequisite;
	}
	
	public Subject(String subjectId) {
		this(subjectId, NONE);
	}

	public String getSubjectId() {
		return subjectId;
	}

	public Subject getPrerequisite() {
		return prerequisite;
	}
	
	boolean isPrereq(Subject subject) {
		return prerequisite.equals(subject);
	}
	
	boolean hasPrereq() {
		return prerequisite != NONE;
	}

	@Override
	public String toString() {
		return getSubjectId();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subjectId == null) ? 0 : subjectId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Subject other = (Subject) obj;
		if (subjectId == null) {
			if (other.subjectId != null)
				return false;
		} else if (!subjectId.equals(other.subjectId))
			return false;
		return true;
	}

	public static Subject valueOf(SubjectDTO subjectDto) {
		return (subjectDto.prerequisiteId == null || subjectDto.prerequisiteId.equalsIgnoreCase("NONE"))? 
				new Subject(subjectDto.subjectId) : 
				new Subject(subjectDto.subjectId, new Subject(subjectDto.prerequisiteId));
	}

	
	
	
}
