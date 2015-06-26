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
import com.orangeandbronze.enlistment.service.StudentDTO;
import com.orangeandbronze.enlistment.service.SubjectDTO;

@WebServlet("/login")
public class LoginController extends HttpServlet{
	
	/** you can create a new controller for student and for admin; redirect to that using this controller**/
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
	    
		String user = req.getParameter("user");
		
		if(user != null && user.equalsIgnoreCase("student")){
			res.sendRedirect("student");
		}else if(user != null && user.equalsIgnoreCase("admin")){
			res.sendRedirect("admin");
		}
	}
}
