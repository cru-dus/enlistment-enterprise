package com.orangeandbronze.enlistment.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.orangeandbronze.enlistment.dao.DataAccessException;
import com.orangeandbronze.enlistment.dao.SubjectDAO;
import com.orangeandbronze.enlistment.domain.Subject;

public class SubjectDaoJdbc extends AbstractDaoJdbc implements SubjectDAO{

	@Override
	public Subject findBy(String subjectId) {
		try {
			initializeConnection();
    		PreparedStatement stmt = createStatement(getSql("SubjectDaoJdbc.findBySubjectId.sql"));
            stmt.setString(1, subjectId);
            
            ResultSet rs = stmt.executeQuery();
            
            Subject subject = null;
            
            while (rs.next()) {
            	String prereqSubjectId = rs.getString("prerequisite_subject_id");
            	subject = (prereqSubjectId == null || prereqSubjectId.equalsIgnoreCase("NONE"))? new Subject(subjectId) : new Subject(subjectId, new Subject(prereqSubjectId));
            }
            
            if(subject == null){
            	throw new SubjectNotFoundException("Subject "+subjectId+ " does not exist in our database.");
            }
            
            connection.close();
            return subject;
        } catch (SQLException | SubjectNotFoundException e) {
            throw new DataAccessException(e);
        }
	}

	@Override
	public Collection<Subject> findAll() {
		try{
			initializeConnection();
    		PreparedStatement stmt = createStatement(getSql("SubjectDaoJdbc.findAll.sql"));
            
            ResultSet rs = stmt.executeQuery();
            
            Set<Subject> subjects = new HashSet<Subject>();
            
            while (rs.next()) {
            	String subjectId = rs.getString("subject_id");
            	String prereqSubjectId = rs.getString("prerequisite_subject_id");
            	Subject subject = (prereqSubjectId == null || prereqSubjectId.equalsIgnoreCase("NONE"))? new Subject(subjectId) : new Subject(subjectId, new Subject(prereqSubjectId));
                subjects.add(subject);
            }
            connection.close();
            return subjects;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
	}
}
