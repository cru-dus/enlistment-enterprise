package com.orangeandbronze.enlistment.service;

import com.orangeandbronze.enlistment.dao.DataAccessException;
import com.orangeandbronze.enlistment.dao.SectionDAO;
import com.orangeandbronze.enlistment.dao.jdbc.SectionDaoJdbc;
import com.orangeandbronze.enlistment.domain.Room;
import com.orangeandbronze.enlistment.domain.Schedule;
import com.orangeandbronze.enlistment.domain.Section;
import com.orangeandbronze.enlistment.domain.Subject;

public class CreateSectionService {
	private SectionDAO dao;
	
	public CreateSectionService(){
		dao = new SectionDaoJdbc();
	}
	
	public void create(String sectionId, SubjectDTO subject, String schedule, RoomDTO room) throws SectionAlreadyExistsException{
		Subject subjectObject = Subject.valueOf(subject);
		Room roomObject = Room.valueOf(room);
		
		try{
			Section section = dao.findBy(sectionId);
			if(section != null){
				throw new SectionAlreadyExistsException("Section "+sectionId+" already exists!");
			}
		}catch(DataAccessException e){
			dao.create(sectionId, subjectObject, Schedule.valueOf(schedule), roomObject);
		}
	}
	
	public void setSectionDao(SectionDAO dao){
		this.dao = dao;
	}
}
