package com.orangeandbronze.enlistment.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.orangeandbronze.enlistment.domain.PrereqNotTakenException;
import com.orangeandbronze.enlistment.domain.RoomCapacityReachedException;
import com.orangeandbronze.enlistment.domain.SameSubjectException;
import com.orangeandbronze.enlistment.domain.SchedueConflictException;
import com.orangeandbronze.enlistment.domain.SemesterClosedException;
import com.orangeandbronze.enlistment.service.EnlistService;
import com.orangeandbronze.enlistment.service.SectionDTO;
import com.orangeandbronze.enlistment.service.StudentDTO;

@WebServlet("/student")
public class StudentPageController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		StudentDTO student = StudentDTO.getInfoOf(1);
		final List<SectionDTO> enlistedSections = student.getCurrentSemesterEnlistedSections();
		final List<SectionDTO> notEnlistedSections = student.getNotEnlistedSections();
		req.setAttribute("status_message", res.getHeader("message"));
		req.setAttribute("enlisted_sections", enlistedSections);
		req.setAttribute("notEnlisted_sections", notEnlistedSections);
		req.getRequestDispatcher("WEB-INF/show_sections.jsp").forward(req,res);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		final int studentNumber = 1;
		final String sectionId = req.getParameter("section_id");
		String statusMessage = "";
    	final EnlistService service = new EnlistService();
    	
    	try{
    		service.enlist(studentNumber, sectionId);
    		statusMessage = "Successfully enlisted in "+sectionId;
    	}catch(SemesterClosedException e){
    		statusMessage = e.getMessage();
    	}catch(SchedueConflictException e){
    		statusMessage = e.getMessage();
    	}catch(SameSubjectException e){
    		statusMessage = e.getMessage();
    	}catch(PrereqNotTakenException e){
    		statusMessage = e.getMessage();
    	}catch(RoomCapacityReachedException e){
    		statusMessage = e.getMessage();
    	}
    	catch(Exception e){
    		statusMessage = e.getMessage();
    	}
    	
    	res.setHeader("message", statusMessage);
    	doGet(req, res);
	}
}
