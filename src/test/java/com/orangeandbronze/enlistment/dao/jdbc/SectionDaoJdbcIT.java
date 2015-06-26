package com.orangeandbronze.enlistment.dao.jdbc;
import static com.orangeandbronze.enlistment.domain.Schedule.Day.MTH;
import static com.orangeandbronze.enlistment.domain.Schedule.Day.TF;
import static com.orangeandbronze.enlistment.domain.Schedule.Day.WS;
import static com.orangeandbronze.enlistment.domain.Schedule.Period.H0830;
import static com.orangeandbronze.enlistment.domain.Schedule.Period.H1130;
import static com.orangeandbronze.enlistment.domain.Schedule.Period.H1300;
import static com.orangeandbronze.enlistment.domain.Schedule.Period.H1430;
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
import org.junit.After;
import org.junit.Test;

import com.orangeandbronze.enlistment.dao.DataAccessException;
import com.orangeandbronze.enlistment.dao.SectionDAO;
import com.orangeandbronze.enlistment.domain.Room;
import com.orangeandbronze.enlistment.domain.Schedule;
import com.orangeandbronze.enlistment.domain.Section;
import com.orangeandbronze.enlistment.domain.Subject;

public class SectionDaoJdbcIT {
	
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
	
	@After
	public void emptyDatabase() throws Exception{
		setupDatabase("dataSetForTestRun.xml");
	}
	@Test (expected = DataAccessException.class)
	public void createNewSectionWhichIsAlreadyCreated() throws Exception {
		setupDatabase("SectionDAO.createNewSectionHappyCase.xml");
		SectionDAO dao = new SectionDaoJdbc();
		
		Collection<Section> expectedSections = new ArrayList<Section>();
	    
	    expectedSections.add(new Section("MHW432", 
	    				new Subject("COM1"), 
	    				new Schedule(MTH, H1300), 
	    				new Room("AVR1", 20)));
	    
	    expectedSections.add(new Section("TFZ321", 
				new Subject("PHYSICS71"), 
				new Schedule(TF, H1430), 
				new Room("IP103", 20)));
	    
	    String newSectionId = "MHY987";
	    Subject subject = new Subject("KAS1");
	    Schedule schedule = new Schedule(MTH, H1130);
	    Room room = new Room("AVR1", 20);
	    Section newSection = new Section(newSectionId, subject, schedule, room);
	    
	    expectedSections.add(newSection);
	    
	    
	    dao.create(newSectionId, subject, schedule, room);
	}
	
	@Test
	public void createNewSectionHappyCase() throws Exception {
		setupDatabase("SectionDAO.createNewSectionHappyCase.xml");
		
		SectionDAO dao = new SectionDaoJdbc();
		
		
	    Collection<Section> expectedSections = new ArrayList<Section>();
	    
	    expectedSections.add(new Section("MHW432", 
	    				new Subject("COM1"), 
	    				new Schedule(MTH, H1300), 
	    				new Room("AVR1", 20)));
	    
	    expectedSections.add(new Section("TFZ321", 
				new Subject("PHYSICS71"), 
				new Schedule(TF, H1430), 
				new Room("IP103", 20)));
	    
	    expectedSections.add(new Section("MHY987", 
				new Subject("KAS1"), 
				new Schedule(MTH, H1130), 
				new Room("AVR1", 20)));
	    
	    String newSectionId = "AR10";
	    Subject subject = new Subject("CMSC198");
	    Schedule schedule = new Schedule(WS, H1130);
	    Room room = new Room("AVR1", 20);
	    Section newSection = new Section(newSectionId, subject, schedule, room);
	    expectedSections.add(newSection);
	    
	    
	    dao.create(newSectionId, subject, schedule, room);
	    
	    Collection<Section> actualSections = dao.findAll();
	    Section actualSection = dao.findBy(newSectionId);
	    
	    assertTrue(actualSections.containsAll(expectedSections));
	    assertEquals(newSection, actualSection);
	}
	
	@Test
	public void findAllSections() throws Exception{
		setupDatabase("SectionDAO.findAllSections.xml");
		
	    SectionDAO dao = new SectionDaoJdbc();
	    
	    Collection<Section> actualSections = dao.findAll();
	    Collection<Section> expectedSections = new ArrayList<Section>();
	    
	    expectedSections.add(new Section("MHW432", 
	    				new Subject("COM1"), 
	    				new Schedule(MTH, H1300), 
	    				new Room("AVR1", 20)));
	    
	    expectedSections.add(new Section("TFZ321", 
				new Subject("PHYSICS71"), 
				new Schedule(TF, H1430), 
				new Room("IP103", 20)));
	    
	    expectedSections.add(new Section("MHY987", 
				new Subject("KAS1"), 
				new Schedule(MTH, H1130), 
				new Room("AVR1", 20)));
	    
	    assertTrue(actualSections.containsAll(expectedSections));
	}
	
	@Test
	public void findBySectionSubjectRoomCapacityValidation() throws Exception{
		setupDatabase("SectionDAO.findBySectionSubjectHasPrereq.xml");
		
	    SectionDAO dao = new SectionDaoJdbc();
	    final String sectionId = "MHX123";
	    Section actualSection = dao.findBy(sectionId);
	    Section expectedSection = new Section(sectionId, new Subject("MATH14", new Subject("MATH11")), new Schedule(MTH, H0830), new Room("MATH105", 20));
	    assertEquals(expectedSection.getRoom().getCapacity(), actualSection.getRoom().getCapacity());
	}
	
	@Test
	public void findBySectionSubjectHasPrereq() throws Exception{
		setupDatabase("SectionDAO.findBySectionSubjectHasPrereq.xml");
		
	    SectionDAO dao = new SectionDaoJdbc();
	    final String sectionId = "MHX123";
	    Section actualSection = dao.findBy(sectionId);
	    Section expectedSection = new Section(sectionId, new Subject("MATH14", new Subject("MATH11")), new Schedule(MTH, H0830), new Room("MATH105", 20));
	    assertEquals(expectedSection.getSubject().getPrerequisite(), actualSection.getSubject().getPrerequisite());
	}
	
	@Test
	public void findBySection() throws Exception{
		setupDatabase("SectionDAO.findBySection.xml");
		
	    SectionDAO dao = new SectionDaoJdbc();
	    final String sectionId = "MHX123";
	    Section actualSection = dao.findBy(sectionId);
	    Section expectedSection = new Section(sectionId, new Subject("MATH11"), new Schedule(MTH, H0830), new Room("MATH105", 20));
	    assertEquals(expectedSection, actualSection);
	}
	
	
}
