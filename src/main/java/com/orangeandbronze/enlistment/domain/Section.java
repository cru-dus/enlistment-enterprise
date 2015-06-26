package com.orangeandbronze.enlistment.domain;

import static org.apache.commons.lang3.Validate.*;

import java.util.*;

public class Section {
	
	private final String sectionId;
	private Schedule schedule;
	private final Subject subject;
	private Room room;
	private Set<SemesterEnlistment> enlistments = new HashSet<>();
	
	public Section(String sectionId, Subject subject, Schedule schedule, Room room) {
		notBlank(sectionId);
		notNull(schedule);
		notNull(subject);
		notNull(room);
		this.sectionId = sectionId;
		this.schedule = schedule;
		this.subject = subject;
		this.room = room;
	}
	
	void validateConflict(Section otherSection) {
		if (schedule.equals(otherSection.schedule)) {
			throw new SchedueConflictException("This secton: " + this + 
					" Other section: " + otherSection);
		}
		if (subject.equals(otherSection.subject)) {
			throw new SameSubjectException("You are already enlisted in " + subject.toString());
		}
	} 
	
	void validateSectionCanAccommodateEnlistment() {
		if (enlistments.size() >= room.getCapacity()) {
			throw new RoomCapacityReachedException("enlistments: " + enlistments.size() + " capacity: " + room.getCapacity());
		}
	}
	
	void add(SemesterEnlistment enlistment) {
		if (!enlistment.getSections().contains(this)) {
			throw new IllegalArgumentException("Enlistment does not contain this section: " + enlistment);
		}
		validateSectionCanAccommodateEnlistment();
		enlistments.add(enlistment);
	}
	
	public boolean hasPrereq() {
		return subject.hasPrereq();
	}
	
	public boolean isPrereqFor(Section otherSection) {
		return otherSection.subject.isPrereq(this.subject);
	}
	
	public Schedule getSchedule() {
		return schedule;
	}
	
	public Subject getSubject() {
		return subject;
	}
	
	public String getSectionId() {
		return sectionId;
	}	
	
	public Room getRoom() {
		return room;
	}

	@Override
	public String toString() {
		return sectionId + " " + subject + " " + schedule;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sectionId == null) ? 0 : sectionId.hashCode());
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
		Section other = (Section) obj;
		if (sectionId == null) {
			if (other.sectionId != null)
				return false;
		} else if (!sectionId.equals(other.sectionId))
			return false;
		return true;
	}
}
