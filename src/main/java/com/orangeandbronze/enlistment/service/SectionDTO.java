package com.orangeandbronze.enlistment.service;

import static org.apache.commons.lang3.Validate.notNull;

import com.orangeandbronze.enlistment.domain.Section;

public class SectionDTO {
	public final String sectionId;
	public final String schedule;
	public final String subjectId;
	public final String roomName;
	
	private SectionDTO(Section section){
		notNull(section);
		sectionId = section.getSectionId();
		schedule = section.getSchedule().toString();
		subjectId = section.getSubject().getSubjectId();
		roomName = section.getRoom().getName();
	}
	
	public String getSectionId(){
		return sectionId;
	}
	
	public String getSchedule(){
		return schedule;
	}
	
	public String getSubjectId(){
		return subjectId;
	}
	
	public String getRoomName(){
		return roomName;
	}
	
	public static SectionDTO getInfoOf(Section section){
		return new SectionDTO(section);
	}
	
	@Override
	public String toString(){
		StringBuilder builder = new StringBuilder();
		builder.append(sectionId);
		builder.append(" ");
		builder.append(subjectId);
		builder.append(" ");
		builder.append(schedule);
		builder.append(" ");
		builder.append(roomName);
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((roomName == null) ? 0 : roomName.hashCode());
		result = prime * result
				+ ((schedule == null) ? 0 : schedule.hashCode());
		result = prime * result
				+ ((sectionId == null) ? 0 : sectionId.hashCode());
		result = prime * result
				+ ((subjectId == null) ? 0 : subjectId.hashCode());
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
		SectionDTO other = (SectionDTO) obj;
		if (roomName == null) {
			if (other.roomName != null)
				return false;
		} else if (!roomName.equals(other.roomName))
			return false;
		if (schedule == null) {
			if (other.schedule != null)
				return false;
		} else if (!schedule.equals(other.schedule))
			return false;
		if (sectionId == null) {
			if (other.sectionId != null)
				return false;
		} else if (!sectionId.equals(other.sectionId))
			return false;
		if (subjectId == null) {
			if (other.subjectId != null)
				return false;
		} else if (!subjectId.equals(other.subjectId))
			return false;
		return true;
	}

	
}
