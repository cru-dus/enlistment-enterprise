package com.orangeandbronze.enlistment.dao.jdbc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.orangeandbronze.enlistment.dao.DataAccessException;
import com.orangeandbronze.enlistment.domain.Room;
import com.orangeandbronze.enlistment.domain.Schedule;
import com.orangeandbronze.enlistment.domain.Section;
import com.orangeandbronze.enlistment.domain.Subject;
public class AbstractDaoJdbc {
	protected Connection connection;
	
	protected void initializeConnection() throws SQLException{
		connection = DriverManager.getConnection("jdbc:hsqldb:enlistmentdb", "sa", "");
	}
	
	protected PreparedStatement createStatement(String sql) throws SQLException{
		return connection.prepareStatement(sql);
	}
	
	protected String getSql(String sqlFile) {
        try (Reader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream(sqlFile)))) {
            StringBuilder bldr = new StringBuilder();
            for (int i = reader.read(); i > 0; i = reader.read()) {
                bldr.append((char) i);
            }
            return bldr.toString();
        } catch (IOException e) {
            throw new DataAccessException(
                    "Problem while trying to read file from classpath.", e);
        }
    }
	
	protected Section createSectionFromResultSet(ResultSet rs) throws SQLException{
		String sectionID = rs.getString("section_id");
        String schedule = rs.getString("schedule");
        String subjectId = rs.getString("subject_id");
        String prereqSubjectId = rs.getString("prerequisite_subject_id");
        int capacity = rs.getInt("capacity");
        String roomName = rs.getString("room_name");
        
        Subject subject = (prereqSubjectId == null || prereqSubjectId.equalsIgnoreCase("NONE"))? new Subject(subjectId) : new Subject(subjectId, new Subject(prereqSubjectId));
        return new Section(sectionID, subject, Schedule.valueOf(schedule), new Room(roomName, capacity));
	}
}
