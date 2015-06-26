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
		select{
			width: 175px;
		}
	</style>

</head>
<h1>Aloha, Admin! </h1>
<div style="float:left;">
	<table style="width:500px">
		<tr >
		 <td colspan='5' style="text-align:center;">Existing Sections</td>
		</tr>
		<tr>
			<th>SECTION</th>
			<th>SUBJECT</th>
			<th>DAY</th>
			<th>PERIOD</th>
			<th>ROOM</th>
		</tr>
		<c:forEach var='section' items="${all_sections}">
			<tr>
				<c:set var="info" value="${fn:split(section, ' ')}" />
				<c:forEach var='str' items="${info}">
					<td>${str }</td>
				</c:forEach>
			</tr>
		</c:forEach>
	</table>
</div>
<div style="float:left; width:200px;"><p></p> </div>
<div style="width:350px; float:left;">
	<fieldset>
		<legend> Create NEW  Section here...</legend>
		<form action="admin" method="POST">
		<table style="width: 300px;">
			<tr>
				<td colspan="2"> Fill out the form </td>
			</tr>
			<tr>
				<td>Section ID</td>
				<td><input type="text" name="section_id" placeholder="Enter section ID here"></td>
			</tr>
			<tr>
				<td>Subject ID</td>
				<td>
					<select name="subject_id">
						<c:forEach var='subject' items="${all_subjects}">
							<option value="${subject}">${subject }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Days</td>
				<td>
					<select name="day">
						<c:forEach var='day' items="${all_days}">
							<option value="${day}">${day}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Period</td>
				<td>
					<select name="period">
						<c:forEach var='period' items="${all_periods}">
							<option value="${period}">${period }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>Room</td>
				<td>
					<select name="room_name">
						<c:forEach var='room' items="${all_rooms}">
							<option value="${room}">${room}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" name="submit" value="CREATE SECTION">
				</td>
			</tr>
		</table>
		</form>
	</fieldset>
</div>

