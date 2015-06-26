package com.orangeandbronze.enlistment.domain;

import java.util.*;

import static org.apache.commons.lang3.Validate.*;

public class Student {

	public static final Student NONE = new Student(0);
	
	private final int studentNumber;
	private final Set<SemesterEnlistment> semEnlistments = new HashSet<>();

	public Student(int studentNumber) {
		this(studentNumber, new ArrayList<SemesterEnlistment>());
	}

	public Student(int studentNumber, Collection<SemesterEnlistment> semEnlistements) {
		if (studentNumber < 0) {
			throw new IllegalArgumentException("Student number cannot be negative. Was: " + studentNumber);
		}
		notNull(semEnlistements);
		this.studentNumber = studentNumber;
		addAll(semEnlistements);
	}
	
	public void add(SemesterEnlistment e) {
		if (!this.equals(e.getStudent())) {
			throw new IllegalArgumentException("This student : " + this + ", student in SemesterEnlistment: " + e.getStudent());
		}
		semEnlistments.add(e);
	}
	
	public void addAll(Collection<SemesterEnlistment> semEnlistements) {
		for (SemesterEnlistment e : semEnlistements) {
			add(e);
		}
	}
	
	public void enlist(Section section) {
		getCurrentSemesterEnlistment().enlist(section);
	}

	public boolean hasTakenPrereqFor(Section section) {
		if (!section.hasPrereq()) {
			return true;
		}
		boolean hasTaken = false;
		for (SemesterEnlistment enlistment : semEnlistments) {
			if (!enlistment.isCurrent()) {
				hasTaken = enlistment.hasPrereqFor(section);
				if (hasTaken) {
					break;
				}
			}
		}
		return hasTaken;
	}
	
	public SemesterEnlistment getCurrentSemesterEnlistment() {
		for (SemesterEnlistment enlistment : semEnlistments) {
			if (enlistment.isCurrent()) {
				return enlistment;
			}
		}
		// no current enlistement found, create new one
		// TODO: This no longer works. Semester Enlistement must come from DAO.
		SemesterEnlistment currentEnlistment = new SemesterEnlistment(this);
		semEnlistments.add(currentEnlistment);
		return currentEnlistment;
	}
	
	void addSemesterEnlistment(SemesterEnlistment enlistment) {
		if (!enlistment.getStudent().equals(this)) {
			throw new IllegalArgumentException("Enlistment is not for this student. Was: " + enlistment);
		}
		this.semEnlistments.add(enlistment);
	}
	
	void addSemesterEnlistments(Collection<SemesterEnlistment> enlistments) {
		for (SemesterEnlistment enlistment : enlistments) {
			addSemesterEnlistment(enlistment);
		}
	}
	
	public Collection<SemesterEnlistment> getSemesterEnlistments() {
		return semEnlistments;
	}

	public int getStudentNumber() {
		return studentNumber;
	}

	@Override
	public String toString() {
		return "Student#" + studentNumber + " " + semEnlistments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + studentNumber;
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
		Student other = (Student) obj;
		if (studentNumber != other.studentNumber)
			return false;
		return true;
	}


	
	

}
