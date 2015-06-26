package com.orangeandbronze.enlistment.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import com.orangeandbronze.enlistment.service.RoomDTO;
import com.orangeandbronze.enlistment.service.SubjectDTO;

public class ObjectValueOfTest {
	@Test
	public void roomDtoToRoomObject(){
		Room room = new Room("AVR1", 20);
		RoomDTO roomDto = RoomDTO.getInfoOf(room);
		
		Room roomFromDTO = Room.valueOf(roomDto);
		assertEquals(room, roomFromDTO);
	}
	
	@Test
	public void subjectDtoToSubjectObject(){
		Subject subject = new Subject("MATH11");
		SubjectDTO subjectDto = SubjectDTO.getInfoOf(subject);
		
		Subject subjectFromDTO = Subject.valueOf(subjectDto);
		assertEquals(subject, subjectFromDTO);
	}
}
