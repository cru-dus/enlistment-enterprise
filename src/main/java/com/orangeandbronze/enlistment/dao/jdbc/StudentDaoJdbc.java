package com.orangeandbronze.enlistment.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.orangeandbronze.enlistment.dao.DataAccessException;
import com.orangeandbronze.enlistment.dao.StudentDAO;
import com.orangeandbronze.enlistment.domain.Section;
import com.orangeandbronze.enlistment.domain.Semester;
import com.orangeandbronze.enlistment.domain.SemesterEnlistment;
import com.orangeandbronze.enlistment.domain.Student;

public class StudentDaoJdbc extends AbstractDaoJdbc implements StudentDAO {
    @Override
    public void update(Student student) {
       try {
    	    initializeConnection();
			updateSemesterEnlistment(student);
			
			updateSemesterEnlistmentSections(student);
			connection.close();
        }catch(SQLException e){
        	throw new DataAccessException(e);
        }
    }
    
    @Override
    public Student findBy(int studentNumber) {
    	try {
    		initializeConnection();
    		PreparedStatement stmt = createStatement(getSql("StudentDaoJdbc.findById.sql"));
            stmt.setInt(1, studentNumber);
            
            ResultSet rs = stmt.executeQuery();
            
            Student student = Student.NONE;
            String prevSemString = "";
            Map<String, Collection<Section>> semSections = new HashMap<>();
            while (rs.next()) {
                if (student == Student.NONE) {
                    student = new Student(rs.getInt("student_number"));
                }
                
                if (student != Student.NONE) {
                    String semString = rs.getString("semester").toUpperCase();
                    if (!prevSemString.equalsIgnoreCase(semString)) {
                        prevSemString = semString;
                        semSections.put(semString, new ArrayList<Section>());
                    }
                    String sectionId = rs.getString("section_id");
                    if (sectionId == null || sectionId.equalsIgnoreCase("NONE")) {
                        continue;
                    }
                    
                    Section section = createSectionFromResultSet(rs);
                    semSections.get(semString).add(section);
                }
            }
            for (Map.Entry<String, Collection<Section>> entry : semSections.entrySet()) {
                new SemesterEnlistment(
                      student, Semester.valueOf(entry.getKey()), entry.getValue());
            }
            return student;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
    }
    
    private void updateSemesterEnlistmentSections(Student student)
			throws SQLException {
		/** CREATE TEMPORARY TABLE FOR UPDATED DATA (sections) **/
		PreparedStatement stmtToCreateTemporarySectionsTable = createStatement(
				"CREATE TABLE IF NOT EXISTS temp_sections (student_number INT, semester VARCHAR(25), section_id VARCHAR(25),);");
		stmtToCreateTemporarySectionsTable.executeUpdate();
		
		/** POPULATE TEMPORARY TABLE**/
		PreparedStatement stmtToInsertSections = createStatement(getSql("StudentDaoJdbc.insertToTemporarySectionsTable.sql"));
		
		for(SemesterEnlistment semester : student.getSemesterEnlistments()){
			for(Section section : semester.getSections()){
				stmtToInsertSections.setInt(1, student.getStudentNumber());
				stmtToInsertSections.setString(2, semester.getSemester().toString());
				stmtToInsertSections.setString(3, section.getSectionId());
				stmtToInsertSections.addBatch();
			}
		}
		stmtToInsertSections.executeBatch();
		/** MERGE USING THIS TABLE**/
		PreparedStatement stmtToUpdateSections = createStatement(getSql("StudentDaoJdbc.updateSemesterEnlistmentSections.sql"));
		stmtToUpdateSections.executeUpdate();
		
		Statement stmt = connection.createStatement();
		stmt.executeUpdate("TRUNCATE TABLE temp_sections");
		stmt.executeUpdate("DROP TABLE temp_sections");
	}

	private void updateSemesterEnlistment(Student student) throws SQLException {
		PreparedStatement stmtToUpdateSemesterEnlistments = createStatement(getSql("StudentDaoJdbc.updateSemesterEnlistments.sql"));
		
		final int studentNumber = student.getStudentNumber();
		final String currentSemester = Semester.current().toString();
		
		stmtToUpdateSemesterEnlistments.setString(1, currentSemester);
		stmtToUpdateSemesterEnlistments.setInt(2, studentNumber);
		stmtToUpdateSemesterEnlistments.setString(3, currentSemester);
		stmtToUpdateSemesterEnlistments.execute();
	}
}
