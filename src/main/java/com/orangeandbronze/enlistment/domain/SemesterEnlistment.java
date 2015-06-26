package com.orangeandbronze.enlistment.domain;

import java.util.*;

public class SemesterEnlistment implements Comparable<SemesterEnlistment> {
	
	public static final SemesterEnlistment NONE = new SemesterEnlistment(Student.NONE, Semester.NONE);
	
	private final Student student;
	private final Semester semester;
	private final Set<Section> sections = new HashSet<>();
	
	public SemesterEnlistment(Student student, Semester semester, Collection<Section> sections) {
		this.student = student;
		this.semester = semester;
		this.sections.addAll(sections);
		student.add(this);
	}
	
	public SemesterEnlistment(Student student, Semester semester) {
		this(student, semester, new LinkedList<>());
	}
	
	/** Instantiates with the 'current' semester.**/
	public SemesterEnlistment(Student student) {
		this(student, Semester.current());
	}
	
	public boolean isCurrent() {
		return semester == Semester.current();
	}

	public void enlist(Section newSection) {
		validateCurrentSemesterEnlistment();
		validateSectionConflict(newSection);
		validatePrereq(newSection);		
		newSection.validateSectionCanAccommodateEnlistment();		
		// two-way add:
		sections.add(newSection);
		newSection.add(this);
	}

	private void validateCurrentSemesterEnlistment() {
		if (!isCurrent()) {
			throw new SemesterClosedException("Cannot enlist for non-current semester. Semester: " + semester);
		}
	}

	private void validateSectionConflict(Section newSection) {
		for (Section currentSection : sections) {
			currentSection.validateConflict(newSection);
		}
	}

	private void validatePrereq(Section newSection) {
		if (!student.hasTakenPrereqFor(newSection)) {
			throw new PrereqNotTakenException(newSection.toString());
		}
	}
	
	public boolean hasPrereqFor(Section otherSection) {
		boolean hasPrereq = false;
		for (Section enlistedSection : sections) {
			hasPrereq = enlistedSection.isPrereqFor(otherSection);
			if (hasPrereq) {
				break;
			}
		}
		return hasPrereq;
	}
	
	/** 
	 * If the parameter is for a later semester than this, then a negative integer is returned.
	 * If the parameter is for an earlier semester than this, then a positive integer is returned.
	 * If the parameter has the same semester as this, then zero is returned. **/
	@Override
	public int compareTo(SemesterEnlistment other) {
		return this.semester.compareTo(other.semester);
	}
	
	public Student getStudent() {
		return student;
	}

	public Semester getSemester() {
		return semester;
	}

	public Collection<Section> getSections() {
		return new ArrayList<>(sections);
	}

	@Override
	public String toString() {
		return student + "-" + semester + ": sections " + sections; 
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((semester == null) ? 0 : semester.hashCode());
		result = prime * result + ((student == null) ? 0 : student.hashCode());
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
		SemesterEnlistment other = (SemesterEnlistment) obj;
		if (semester != other.semester)
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		return true;
	}




	
	
}
