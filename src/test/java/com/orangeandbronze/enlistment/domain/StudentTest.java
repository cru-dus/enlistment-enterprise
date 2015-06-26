package com.orangeandbronze.enlistment.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import static com.orangeandbronze.enlistment.domain.Schedule.Day.*;
import static com.orangeandbronze.enlistment.domain.Schedule.Period.*;

public class StudentTest {
	
	
	@Test
	public void enlistFirstSection() {
		Student student = new Student(7);
		Section section = new Section("MHX123", new Subject("Math53"), new Schedule(MTH, H0830),  new Room("Lec1", 50));
		student.enlist(section);
		assertTrue(student.getCurrentSemesterEnlistment().getSections().contains(section));
	}
	
	@Test(expected = SchedueConflictException.class)
	public void enlistSectionSameScheduleAsCurrentSection() {
		Student student = new Student(7);
		Section section1 = new Section("MHX123", new Subject("Math53"), new Schedule(MTH, H0830), new Room("AVR2", 100));
		student.enlist(section1);
		Section section2 = new Section("MHX456", new Subject("Philo1"), new Schedule(MTH, H0830), new Room("Lec1", 50));
		student.enlist(section2);
	}
	
	@Test(expected = PrereqNotTakenException.class)
	public void enlistSectionMissingPrereq() {
		Student student = new Student(7);
		Subject prereq = new Subject("Math17");
		Subject subject =  new Subject("Math53", prereq);
		Section section = new Section("MHX123", subject, new Schedule(MTH, H0830), new Room("Lec1", 50));
		student.enlist(section);
	}
	
	@Test(expected = SameSubjectException.class)
	public void enlistSectionSameSubject() {
		Student student = new Student(7);
		Section section1 = new Section("MHX123", new Subject("Math53"), new Schedule(MTH, H0830), new Room("Lec1", 50));
		student.enlist(section1);
		Section section2 = new Section("MHX456", new Subject("Math53"), new Schedule(TF, H0830), new Room("AVR2", 100));
		student.enlist(section2);
	}
	
	@Test(expected = RoomCapacityReachedException.class) 
	public void enlistSectionAtCapacity() {
		Student student = new Student(7);
		Section section = new Section("MHX123", new Subject("Math53"), new Schedule(MTH, H0830), new Room("Lec1", 0));
		student.enlist(section);
	}


}
