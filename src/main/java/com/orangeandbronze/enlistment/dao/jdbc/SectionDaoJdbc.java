package com.orangeandbronze.enlistment.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.orangeandbronze.enlistment.dao.DataAccessException;
import com.orangeandbronze.enlistment.dao.SectionDAO;
import com.orangeandbronze.enlistment.domain.Room;
import com.orangeandbronze.enlistment.domain.Schedule;
import com.orangeandbronze.enlistment.domain.Section;
import com.orangeandbronze.enlistment.domain.Subject;

public class SectionDaoJdbc extends AbstractDaoJdbc implements SectionDAO{
	
	@Override
	public Section findBy(String sectionId) {
		try {
			initializeConnection();
    		PreparedStatement stmt = createStatement(getSql("SectionDaoJdbc.findById.sql"));
            stmt.setString(1, sectionId);
            
            ResultSet rs = stmt.executeQuery();
            
            Section section = null;
            
            while (rs.next()) {
                section = createSectionFromResultSet(rs);
            }
            
            if(section == null){
            	throw new DataAccessException("Section "+sectionId+ " does not exist in our database.");
            }
            connection.close();
            return section;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
	}

	@Override
	public Collection<Section> findAll() {
		try{
			initializeConnection();
    		PreparedStatement stmt = createStatement(getSql("SectionDaoJdbc.findAll.sql"));
            
            ResultSet rs = stmt.executeQuery();
            
            Set<Section> sections = new HashSet<Section>();
            
            while (rs.next()) {
                sections.add(createSectionFromResultSet(rs));
            }
            connection.close();
            return sections;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
	}

	@Override
	public Section create(String sectionId, Subject subject, Schedule schedule,
			Room room) {
		/** ASSUMPTIONS
		 * 		parameters are validated
		 * 		subject and room are existing in the database
		**/
		try {
			initializeConnection();
    		PreparedStatement stmt = createStatement(getSql("SectionDaoJdbc.createSection.sql"));
            stmt.setString(1, sectionId);
            stmt.setString(2, schedule.toString());
            stmt.setString(3, subject.getSubjectId());
            stmt.setString(4, room.getName());
            
            if(stmt.executeUpdate() == 1){
            	connection.close();
            	return new Section(sectionId, subject, schedule, room);
            }else{
            	throw new DataAccessException("Section creation not successful!");
            }
         
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
	}
}
