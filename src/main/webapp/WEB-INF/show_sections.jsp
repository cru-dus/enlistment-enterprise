<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<head>
	<style>
		table, th, td {
    		border: 1px solid black;
	    	border-collapse: collapse;
	    	text-align : center;
		}
	</style>

</head>
<h2>Hello Student !</h2>

<div>
	<p> ${status_message} </p>
</div>
<div>
	<h3>You are enlisted in the ff sections: </h3>
	<table style="width:500px;">
		<tr>
			<th>Section ID </th>
			<th>Subject ID </th>
			<th>Schedule </th>
			<th>Room </th>
		</tr>
		<c:forEach var='section' items="${enlisted_sections}">
			<tr>
				<td>${ section.sectionId }</td>
				<td>${ section.subjectId }</td>
				<td>${ section.schedule }</td>
				<td>${ section.roomName }</td> 
			
			</tr>
		</c:forEach>
	</table>
	
</div>
<div>
	<h3>You are not enlisted in the ff sections: </h3>
	<form action="student" method="POST">
		<table style="width:500px;">
			<tr>
				<th>Section ID </th>
				<th>Subject ID </th>
				<th>Schedule </th>
				<th>Room </th>
				<th>Enlist</th>
			</tr>
			<c:forEach var='section' items="${notEnlisted_sections}">
				<tr>
					<td>${ section.sectionId }</td>
					<td>${ section.subjectId }</td>
					<td>${ section.schedule }</td>
					<td>${ section.roomName }</td> 
					<td>
						<input type="submit" name="submit" value="ENLIST"/>
						<input type="hidden" name="section_id" value="${ section.sectionId }">
					</td>
				
				</tr>
			</c:forEach>
		</table>
	</form>
	
</div>



