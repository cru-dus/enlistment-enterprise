package com.orangeandbronze.enlistment.service;

import com.orangeandbronze.enlistment.dao.*;
import com.orangeandbronze.enlistment.dao.jdbc.SectionDaoJdbc;
import com.orangeandbronze.enlistment.dao.jdbc.StudentDaoJdbc;
import com.orangeandbronze.enlistment.domain.*;

public class EnlistService {
	
	private SectionDAO sectionDao;
	private StudentDAO studentDao;
	
	public EnlistService(){
		sectionDao = new SectionDaoJdbc();
		studentDao = new StudentDaoJdbc();
	}
	
	public void enlist(int studentNo, String sectionId) {
		Student student = studentDao.findBy(studentNo);
		Section section = sectionDao.findBy(sectionId);
		student.enlist(section);
		studentDao.update(student);
	}
	
	public void setSectionDao(SectionDAO sectionDao) {
		this.sectionDao = sectionDao;
	}

	public void setStudentDao(StudentDAO studentDao) {
		this.studentDao = studentDao;
	}
	
	
}
