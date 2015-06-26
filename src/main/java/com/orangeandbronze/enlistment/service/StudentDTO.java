package com.orangeandbronze.enlistment.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.orangeandbronze.enlistment.dao.StudentDAO;
import com.orangeandbronze.enlistment.dao.jdbc.SectionDaoJdbc;
import com.orangeandbronze.enlistment.dao.jdbc.StudentDaoJdbc;
import com.orangeandbronze.enlistment.domain.Section;
import com.orangeandbronze.enlistment.domain.SemesterEnlistment;
import com.orangeandbronze.enlistment.domain.Student;

public class StudentDTO {
	private Student student;
	
	public StudentDTO(int studentNumber){
		StudentDAO dao = new StudentDaoJdbc();
		student = dao.findBy(studentNumber);
	}
	
	public static StudentDTO getInfoOf(int studentNumber){
		return new StudentDTO(studentNumber);
	}
	
	public List<SectionDTO> getCurrentSemesterEnlistedSections(){
		List<SectionDTO> sections = new ArrayList<>();
		
		SemesterEnlistment currentSem = student.getCurrentSemesterEnlistment();
		
		for(Section section : currentSem.getSections()){
			sections.add(SectionDTO.getInfoOf(section));
		}
		
		return sections;
	}

	public List<SectionDTO> getNotEnlistedSections() {
		
		Collection<Section> allSections = new SectionDaoJdbc().findAll();
		List<SectionDTO> sections = new ArrayList<>();
		for(Section section : allSections){
			if(!student.getCurrentSemesterEnlistment().getSections().contains(section)){
				sections.add(SectionDTO.getInfoOf(section));
			}
		}
		
		return sections;
	}
	
	@Override
	public String toString(){
		return String.valueOf(student.getStudentNumber());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		StudentDTO other = (StudentDTO) obj;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		return true;
	}
	
	
}
