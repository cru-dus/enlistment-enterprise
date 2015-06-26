package com.orangeandbronze.enlistment.domain;

import static com.orangeandbronze.enlistment.domain.Schedule.Day.*;
import static com.orangeandbronze.enlistment.domain.Schedule.Period.*;
import static com.orangeandbronze.enlistment.domain.Semester.*;

import java.util.*;

import static org.junit.Assert.*;

import org.junit.Test;

public class SemesterEnlistmentTest {

	@Test
	public void enlistFirstSection() {
		Student student = new Student(7);
		Section section = new Section("MHX123", new Subject("Math53"), new Schedule(MTH, H0830), new Room("Lec1", 50));
		SemesterEnlistment enlistement = new SemesterEnlistment(student);
		enlistement.enlist(section);
		assertTrue(enlistement.getSections().contains(section));
	}
	
	@Test(expected = SchedueConflictException.class)
	public void enlistSectionSameScheduleAsCurrentSection() {
		Student student = new Student(7);
		Section section1 = new Section("MHX123", new Subject("Math53"), new Schedule(MTH, H0830), new Room("Lec1", 50));
		SemesterEnlistment enlistement = new SemesterEnlistment(student);
		enlistement.enlist(section1);
		Section section2 = new Section("MHX456", new Subject("Philo1"), new Schedule(MTH, H0830), new Room("AVR2", 50));
		enlistement.enlist(section2);
	}
	
	@Test
	public void enlistSectionWithPrereq() {
		Student student = new Student(7);
		Subject prereq = new Subject("Math17");
		Collection<Section> prevSections = new ArrayList<>();
		prevSections.add(new Section("PrereqSection", prereq, new Schedule(MTH, H0830), new Room("Lec1", 50)));
		SemesterEnlistment prevEnlistment = new SemesterEnlistment(student, Y2013_1ST, prevSections);
		student.addSemesterEnlistment(prevEnlistment);
		Subject subject =  new Subject("Math53", prereq);
		Section section = new Section("MHX123", subject, new Schedule(MTH, H0830), new Room("AVR2", 50));
		SemesterEnlistment enlistment = new SemesterEnlistment(student);	
		student.addSemesterEnlistment(enlistment);
		enlistment.enlist(section);
		assertTrue(enlistment.getSections().contains(section));
	}
	
	@Test(expected = PrereqNotTakenException.class)
	public void enlistSectionMissingPrereq() {
		Student student = new Student(7);
		Subject prereq = new Subject("Math17");
		Subject subject =  new Subject("Math53", prereq);
		Section section = new Section("MHX123", subject, new Schedule(MTH, H0830), new Room("Lec1", 50));
		SemesterEnlistment enlistment = new SemesterEnlistment(student);
		enlistment.enlist(section);
	}

}
