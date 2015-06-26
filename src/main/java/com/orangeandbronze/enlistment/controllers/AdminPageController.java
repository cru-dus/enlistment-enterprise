package com.orangeandbronze.enlistment.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.orangeandbronze.enlistment.domain.Schedule;
import com.orangeandbronze.enlistment.service.CreateSectionService;
import com.orangeandbronze.enlistment.service.InfoService;
import com.orangeandbronze.enlistment.service.RoomDTO;
import com.orangeandbronze.enlistment.service.SectionDTO;
import com.orangeandbronze.enlistment.service.SubjectDTO;

@WebServlet("/admin")
public class AdminPageController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		final InfoService infoService = new InfoService();
		final List<SectionDTO> allSections = infoService.getAllSections();
		final List<SubjectDTO> allSubjects = infoService.getAllSubjects();
		final List<String> allDays = Schedule.getAllDays();
		final List<String> allPeriods = Schedule.getAllPeriods();
		final List<RoomDTO> allRooms = infoService.getAllRooms();
		
		req.setAttribute("all_rooms", allRooms);
		req.setAttribute("all_days", allDays);
		req.setAttribute("all_subjects", allSubjects);
		req.setAttribute("all_periods", allPeriods);
		req.setAttribute("all_sections", allSections);
		
		req.getRequestDispatcher("WEB-INF/admin_page.jsp").forward(req,res);
		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		final CreateSectionService service = new CreateSectionService();
    	final String sectionId = req.getParameter("section_id");
    	final String subjectId = req.getParameter("subject_id");
    	final String schedule = req.getParameter("day") + " " + req.getParameter("period");
    	final String roomName = req.getParameter("room_name");
    	
    	final SubjectDTO subjectDto = new SubjectDTO(subjectId);
    	final RoomDTO roomDto = new RoomDTO(roomName);
    	try{
    		service.create(sectionId, subjectDto, schedule, roomDto);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
		res.sendRedirect("admin");
	}
}
