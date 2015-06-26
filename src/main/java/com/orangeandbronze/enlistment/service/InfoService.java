package com.orangeandbronze.enlistment.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.orangeandbronze.enlistment.dao.RoomDAO;
import com.orangeandbronze.enlistment.dao.SectionDAO;
import com.orangeandbronze.enlistment.dao.SubjectDAO;
import com.orangeandbronze.enlistment.dao.jdbc.RoomDaoJdbc;
import com.orangeandbronze.enlistment.dao.jdbc.SectionDaoJdbc;
import com.orangeandbronze.enlistment.dao.jdbc.SubjectDaoJdbc;
import com.orangeandbronze.enlistment.domain.Room;
import com.orangeandbronze.enlistment.domain.Section;
import com.orangeandbronze.enlistment.domain.Subject;

public class InfoService {
	private final SubjectDAO subjectDao;
	private final RoomDAO roomDao;
	private final SectionDAO sectionDao;
	
	public InfoService(){
		subjectDao = new SubjectDaoJdbc();
		roomDao = new RoomDaoJdbc();
		sectionDao = new SectionDaoJdbc();
	}
	
	public List<SubjectDTO> getAllSubjects(){
		Collection<Subject> allSubjects = subjectDao.findAll();
		List<SubjectDTO> subjects = new ArrayList<>();
		for(Subject subject : allSubjects){
			subjects.add(SubjectDTO.getInfoOf(subject));
		}
		return subjects;
	}
	
	public List<RoomDTO> getAllRooms(){
		Collection<Room> allRooms = roomDao.findAll();
		List<RoomDTO> rooms = new ArrayList<>();
		for(Room room : allRooms){
			rooms.add(RoomDTO.getInfoOf(room));
		}
		
		return rooms;
	}
	
	public List<SectionDTO> getAllSections(){
		Collection<Section> allSections = sectionDao.findAll();
		List<SectionDTO> sections = new ArrayList<>();
		for(Section section : allSections){
			sections.add(SectionDTO.getInfoOf(section));
		}
		
		return sections;
	}
	
}
