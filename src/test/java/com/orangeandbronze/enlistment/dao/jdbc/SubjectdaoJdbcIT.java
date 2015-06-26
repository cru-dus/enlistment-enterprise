package com.orangeandbronze.enlistment.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collection;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import com.orangeandbronze.enlistment.dao.SubjectDAO;
import com.orangeandbronze.enlistment.domain.Subject;

public class SubjectdaoJdbcIT {
	private void setupDatabase(String datasetFilename) throws Exception{
		Connection jdbcConnection = DriverManager.getConnection("jdbc:hsqldb:enlistmentdb", "sa", "");
        jdbcConnection.createStatement().execute("SET DATABASE REFERENTIAL INTEGRITY FALSE;");
        
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setDtdMetadata(false);
        
        IDataSet dataSet = builder.build(getClass().getResourceAsStream(datasetFilename));
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        connection.close(); 
	}
	
	@Test
	public void findBySubjectId() throws Exception{
		setupDatabase("SubjectDAO.findBySectionId.xml");
		
	    SubjectDAO dao = new SubjectDaoJdbc();
	    final String subjectId = "MATH11";
	    Subject actualSubject = dao.findBy(subjectId);
	    Subject expectedSubject = new Subject(subjectId);
	    
	    assertEquals(expectedSubject, actualSubject);
	}
	
	@Test
	public void findAllSubjects() throws Exception{
		setupDatabase("SubjectDAO.findBySectionId.xml");
		
	    SubjectDAO dao = new SubjectDaoJdbc();
	    
	    Collection<Subject> actualSubjects = dao.findAll();
	    Collection<Subject> expectedSubjects = new ArrayList<Subject>();
	    
	    expectedSubjects.add(new Subject("MATH11"));
	    expectedSubjects.add(new Subject("KAS1"));
	    expectedSubjects.add(new Subject("PHILO1"));
	    expectedSubjects.add(new Subject("COM1"));
	    expectedSubjects.add(new Subject("PHYSICS71"));
	    
	    assertTrue(actualSubjects.containsAll(expectedSubjects));
	    assertTrue(expectedSubjects.containsAll(actualSubjects));
	}
}
