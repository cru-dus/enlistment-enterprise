package com.orangeandbronze.enlistment.service;

import org.junit.*;

import com.orangeandbronze.enlistment.dao.*;
import com.orangeandbronze.enlistment.domain.*;

import static com.orangeandbronze.enlistment.domain.Schedule.Day.*;
import static com.orangeandbronze.enlistment.domain.Schedule.Period.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EnlistServiceTest {
	
	@Test
	public void enlistFirst() {
		SectionDAO sectionDao = mock(SectionDAO.class);
		StudentDAO studentDao = mock(StudentDAO.class);
		EnlistService service = new EnlistService();
		service.setSectionDao(sectionDao);
		service.setStudentDao(studentDao);
		
		int studentNo = 777;
		Student student = new Student(studentNo);
		when(studentDao.findBy(studentNo)).thenReturn(student);
		String sectionId = "TFX123";
		Section section = new Section(sectionId, new Subject("Math53"), new Schedule(TF, H0830), new Room("Lec1", 50));
		when(sectionDao.findBy(sectionId)).thenReturn(section);
		
		service.enlist(studentNo, sectionId);
		
		assertTrue(student.getCurrentSemesterEnlistment().getSections().contains(section));
		verify(studentDao).update(student);
	}
	
}
