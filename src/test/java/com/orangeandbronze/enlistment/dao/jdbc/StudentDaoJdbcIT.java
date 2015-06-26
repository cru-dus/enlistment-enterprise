package com.orangeandbronze.enlistment.dao.jdbc;
import static com.orangeandbronze.enlistment.domain.Schedule.Day.*;
import static com.orangeandbronze.enlistment.domain.Schedule.Period.*;
import static com.orangeandbronze.enlistment.domain.Semester.*;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;

import com.orangeandbronze.enlistment.dao.StudentDAO;
import com.orangeandbronze.enlistment.domain.Room;
import com.orangeandbronze.enlistment.domain.Schedule;
import com.orangeandbronze.enlistment.domain.Section;
import com.orangeandbronze.enlistment.domain.SemesterEnlistment;
import com.orangeandbronze.enlistment.domain.Student;
import com.orangeandbronze.enlistment.domain.Subject;

public class StudentDaoJdbcIT {
	
	@Test
	public void updateStudentNoCurrentSemesterEnlistment() throws Exception {
		setupDatabase("StudentDAO.updateStudentNoCurrentSemesterEnlistment.xml");
		
		StudentDAO dao = new StudentDaoJdbc();
		final int expectedStudentNumber = 1;
		Student actualStudent = dao.findBy(expectedStudentNumber);
		final Student expectedStudent = new Student(expectedStudentNumber);
		assertEquals(expectedStudentNumber, actualStudent.getStudentNumber());
		
		Collection<SemesterEnlistment> actualEnlistments = actualStudent.getSemesterEnlistments();
		
		/** POPULATE EXPECTED PREVIOUS ENLISTMENTS**/
		Collection<Section> sem1Sections = new ArrayList<>();
        sem1Sections.add(new Section("MHX123", new Subject("MATH11"),
                new Schedule(MTH, H0830), new Room("MATH105", 20)));
        SemesterEnlistment sem1 = new SemesterEnlistment(expectedStudent, Y2014_1ST, sem1Sections);
        
        Collection<Section> sem2Sections = new ArrayList<>();
        sem2Sections.add(
                new Section("TFX555", new Subject("PHILO1"), 
                        new Schedule(TF, H1000), new Room("AS113", 35)));
        sem2Sections.add(
                new Section("MHW432", new Subject("COM1"), 
                        new Schedule(MTH, H1300), new Room("AVR1", 20)));
        
        SemesterEnlistment sem2 = new SemesterEnlistment(expectedStudent, Y2014_2ND, sem2Sections);
        
        List<SemesterEnlistment> expectedEnlistments = new ArrayList<>();
        expectedEnlistments.add(sem1);
        expectedEnlistments.add(sem2);
        
        assertTrue(actualEnlistments.containsAll(expectedEnlistments));
        
        /** ADD CURRENT SEMESTER ENLISTMENTS**/
        Collection<Section> sem3Sections = new ArrayList<>();
        sem3Sections.add(
                new Section("TFZ321", new Subject("PHYSICS71"), 
                        new Schedule(TF, H1430), new Room("IP103", 35)));
        sem3Sections.add(
                new Section("MHY987", new Subject("KAS1"), 
                        new Schedule(MTH, H1130), new Room("AVR1", 20)));
        sem3Sections.add(
                new Section("MT14", new Subject("MATH14", new Subject("MATH11")), 
                        new Schedule(WS, H0830), new Room("MATH105", 20)));
        SemesterEnlistment newSemesterEnlistment = new SemesterEnlistment(expectedStudent, Y2015_1ST, sem3Sections);
        
        actualStudent.add(newSemesterEnlistment);
        expectedEnlistments.add(newSemesterEnlistment);
        
        dao.update(actualStudent);
        actualStudent = dao.findBy(expectedStudentNumber);
        
        actualEnlistments = actualStudent.getSemesterEnlistments();
        assertTrue(actualEnlistments.containsAll(expectedEnlistments));
       
	}
	
	@Test
	public void findByStudentPrereqAndRoomValidation() throws Exception{
		setupDatabase("StudentDAO.findByStudentPrereqAndRoomValidation.xml");
		
		StudentDAO dao = new StudentDaoJdbc(); 
        int expectedStudentNumber = 1;
        Student actualStudent = dao.findBy(expectedStudentNumber);
        Student expectedStudent = new Student(expectedStudentNumber);
        assertEquals(expectedStudentNumber, actualStudent.getStudentNumber());
        
        Collection<SemesterEnlistment> actualEnlistments = actualStudent.getSemesterEnlistments();
        
        Collection<Section> sem1Sections = new ArrayList<>();
        sem1Sections.add(new Section("MHX123", new Subject("MATH11"),
                new Schedule(MTH, H0830), new Room("MATH105", 20)));
        SemesterEnlistment sem1 = new SemesterEnlistment(expectedStudent, Y2014_1ST, sem1Sections);
        
        Collection<Section> sem2Sections = new ArrayList<>();
        sem2Sections.add(
                new Section("TFX555", new Subject("PHILO1"), 
                        new Schedule(TF, H1000), new Room("AS113", 35)));
        sem2Sections.add(
                new Section("MHW432", new Subject("COM1"), 
                        new Schedule(MTH, H1300), new Room("AVR1", 20)));
        
        SemesterEnlistment sem2 = new SemesterEnlistment(expectedStudent, Y2014_2ND, sem2Sections);
        
        Collection<Section> sem3Sections = new ArrayList<>();
        sem3Sections.add(
                new Section("TFZ321", new Subject("PHYSICS71"), 
                        new Schedule(TF, H1430), new Room("IP103", 35)));
        sem3Sections.add(
                new Section("MHY987", new Subject("KAS1"), 
                        new Schedule(MTH, H1130), new Room("AVR1", 20)));
        sem3Sections.add(
                new Section("MT14", new Subject("MATH14", new Subject("MATH11")), 
                        new Schedule(WS, H0830), new Room("MATH105", 20)));
        SemesterEnlistment sem3 = new SemesterEnlistment(expectedStudent, Y2015_1ST, sem3Sections);

        List<SemesterEnlistment> expectedEnlistments = new ArrayList<>();
        expectedEnlistments.add(sem1);
        expectedEnlistments.add(sem2);
        expectedEnlistments.add(sem3);
        
        assertTrue(actualEnlistments.containsAll(expectedEnlistments));

        for (SemesterEnlistment actualSem : actualEnlistments) {
        	ArrayList<Section> expectedSections = null;
            if (actualSem.equals(sem1)) {
                assertEquals(new HashSet<>(sem1Sections), new HashSet<>(actualSem.getSections()));
                expectedSections = new ArrayList<>(sem1Sections);
            } else if (actualSem.equals(sem2)) {
                assertEquals(new HashSet<>(sem2Sections), new HashSet<>(actualSem.getSections()));
                expectedSections = new ArrayList<>(sem2Sections);
            } else if (actualSem.equals(sem3)) {
                assertEquals(new HashSet<>(sem3Sections), new HashSet<>(actualSem.getSections()));
                expectedSections = new ArrayList<>(sem3Sections);
            }
            List<Section> actualSections = new ArrayList<>(actualSem.getSections());
            for(int i=0; i<expectedSections.size(); i++){
	        	Room expectedRoom = expectedSections.get(i).getRoom();
	        	Room actualRoom = actualSections.get(actualSections.indexOf(expectedSections.get(i))).getRoom();
	        	assertEquals(expectedRoom, actualRoom);
	        	Section section = actualSections.get(i);
	        	if(section.hasPrereq()){
        			assertTrue(actualStudent.hasTakenPrereqFor(section));
        		}
            }
        }
		
	}
	
	@Test
    public void findByStudentPrereqTakenValidation() throws Exception  {
		setupDatabase("StudentDAO.findByStudentPrereqTakenValidation.xml");
        
        StudentDAO dao = new StudentDaoJdbc(); 
        int expectedStudentNumber = 1;
        Student actualStudent = dao.findBy(expectedStudentNumber);
        assertEquals(expectedStudentNumber, actualStudent.getStudentNumber());
        
        Collection<SemesterEnlistment> actualEnlistments = actualStudent.getSemesterEnlistments();

        for (SemesterEnlistment actualSem : actualEnlistments) {
        	for(Section section : actualSem.getSections()){
        		if(section.hasPrereq()){
        			assertTrue(actualStudent.hasTakenPrereqFor(section));
        		}	
        	}
        }
    }
	
	@Test
    public void findByStudentRoomValidation() throws Exception {
		setupDatabase("StudentDAO.findByStudentEnlistmentsSectionsRoomValidation.xml");
        
        StudentDAO dao = new StudentDaoJdbc(); 
        int expectedStudentNumber = 1;
        Student actualStudent = dao.findBy(expectedStudentNumber);
        Student expectedStudent = new Student(expectedStudentNumber);
        assertEquals(expectedStudentNumber, actualStudent.getStudentNumber());
        
        Collection<SemesterEnlistment> actualEnlistments = actualStudent.getSemesterEnlistments();
        
        Collection<Section> sem1Sections = new ArrayList<>();
        sem1Sections.add(new Section("MHX123", new Subject("MATH11"),
                new Schedule(MTH, H0830), new Room("MATH105", 20)));
        SemesterEnlistment sem1 = new SemesterEnlistment(expectedStudent, Y2014_1ST, sem1Sections);
        
        Collection<Section> sem2Sections = new ArrayList<>();
        sem2Sections.add(
                new Section("TFX555", new Subject("PHILO1"), 
                        new Schedule(TF, H1000), new Room("AS113", 35)));
        sem2Sections.add(
                new Section("MHW432", new Subject("COM1"), 
                        new Schedule(MTH, H1300), new Room("AVR1", 20)));
        
        SemesterEnlistment sem2 = new SemesterEnlistment(expectedStudent, Y2014_2ND, sem2Sections);
        
        Collection<Section> sem3Sections = new ArrayList<>();
        sem3Sections.add(
                new Section("TFZ321", new Subject("PHYSICS71"), 
                        new Schedule(TF, H1430), new Room("IP103", 35)));
        sem3Sections.add(
                new Section("MHY987", new Subject("KAS1"), 
                        new Schedule(MTH, H1130), new Room("AVR1", 20)));
        SemesterEnlistment sem3 = new SemesterEnlistment(expectedStudent, Y2015_1ST, sem3Sections);

        List<SemesterEnlistment> expectedEnlistments = new ArrayList<>();
        expectedEnlistments.add(sem1);
        expectedEnlistments.add(sem2);
        expectedEnlistments.add(sem3);
        
        assertTrue(actualEnlistments.containsAll(expectedEnlistments));

        for (SemesterEnlistment actualSem : actualEnlistments) {
        	ArrayList<Section> expectedSections = null;
            if (actualSem.equals(sem1)) {
                assertEquals(new HashSet<>(sem1Sections), new HashSet<>(actualSem.getSections()));
                expectedSections = new ArrayList<>(sem1Sections);
            } else if (actualSem.equals(sem2)) {
                assertEquals(new HashSet<>(sem2Sections), new HashSet<>(actualSem.getSections()));
                expectedSections = new ArrayList<>(sem2Sections);
            } else if (actualSem.equals(sem3)) {
                assertEquals(new HashSet<>(sem3Sections), new HashSet<>(actualSem.getSections()));
                expectedSections = new ArrayList<>(sem3Sections);
            }
            List<Section> actualSections = new ArrayList<>(actualSem.getSections());
            for(int i=0; i<expectedSections.size(); i++){
	        	Room expectedRoom = expectedSections.get(i).getRoom();
	        	Room actualRoom = actualSections.get(actualSections.indexOf(expectedSections.get(i))).getRoom();
	        	assertEquals(expectedRoom, actualRoom);
            }
        }
    }
	
	@Test
    public void findByStudentEnlistmentsSections() throws Exception {
		setupDatabase("StudentDAO.findByStudentEnlistmentsSections.xml"); 
        
        StudentDAO dao = new StudentDaoJdbc(); 
        int expectedStudentNumber = 1;
        Student actualStudent = dao.findBy(expectedStudentNumber);
        Student expectedStudent = new Student(expectedStudentNumber);
        assertEquals(expectedStudentNumber, actualStudent.getStudentNumber());
        
        Collection<SemesterEnlistment> actualEnlistments = actualStudent.getSemesterEnlistments();
        
        Collection<Section> sem1Sections = new ArrayList<>();
        sem1Sections.add(new Section("MHX123", new Subject("MATH11"),
                new Schedule(MTH, H0830), new Room("MATH105", 20)));
        SemesterEnlistment sem1 = new SemesterEnlistment(expectedStudent, Y2014_1ST, sem1Sections);
        
        Collection<Section> sem2Sections = new ArrayList<>();
        sem2Sections.add(
                new Section("TFX555", new Subject("PHILO1"), 
                        new Schedule(TF, H1000), new Room("AS113", 35)));
        sem2Sections.add(
                new Section("MHW432", new Subject("COM1"), 
                        new Schedule(MTH, H1300), new Room("AVR1", 20)));
        
        SemesterEnlistment sem2 = new SemesterEnlistment(expectedStudent, Y2014_2ND, sem2Sections);
        
        Collection<Section> sem3Sections = new ArrayList<>();
        sem3Sections.add(
                new Section("TFZ321", new Subject("PHYSICS71"), 
                        new Schedule(TF, H1430), new Room("IP103", 35)));
        sem3Sections.add(
                new Section("MHY987", new Subject("KAS1"), 
                        new Schedule(MTH, H1130), new Room("AVR1", 20)));
        SemesterEnlistment sem3 = new SemesterEnlistment(expectedStudent, Y2015_1ST, sem3Sections);

        List<SemesterEnlistment> expectedEnlistments = new ArrayList<>();
        expectedEnlistments.add(sem1);
        expectedEnlistments.add(sem2);
        expectedEnlistments.add(sem3);
        
        assertTrue(actualEnlistments.containsAll(expectedEnlistments));

        for (SemesterEnlistment actualSem : actualEnlistments) {
        	
            if (actualSem.equals(sem1)) {
                assertEquals(new HashSet<>(sem1Sections), new HashSet<>(actualSem.getSections()));
            } else if (actualSem.equals(sem2)) {
                assertEquals(new HashSet<>(sem2Sections), new HashSet<>(actualSem.getSections()));
            } else if (actualSem.equals(sem3)) {
                assertEquals(new HashSet<>(sem3Sections), new HashSet<>(actualSem.getSections()));
            }
        }
    }
	
	@Test
	public void findStudentHasEnlistmentsNoSections() throws Exception {
		setupDatabase("StudentDAO.findStudentHasEnlistmentsNoSections.xml");
		
	    StudentDAO dao = new StudentDaoJdbc();
	    final int studentNumber = 1;
	    Student actualStudent = dao.findBy(studentNumber);
	    Student expectedStudent = new Student(studentNumber);
	    assertEquals(studentNumber, actualStudent.getStudentNumber());
	    final List<SemesterEnlistment> expectedEnlistments = new ArrayList<>();
	    expectedEnlistments.add(new SemesterEnlistment(expectedStudent, Y2015_1ST));
	    expectedEnlistments.add(new SemesterEnlistment(expectedStudent, Y2014_2ND));
	    expectedEnlistments.add(new SemesterEnlistment(expectedStudent, Y2014_1ST));

	    final Collection<SemesterEnlistment> actualEnlistments = 
	            actualStudent.getSemesterEnlistments();
	    assertTrue(actualEnlistments.containsAll(expectedEnlistments));
	}
	
	private void setupDatabase(String datasetFilename) throws Exception{
		Connection jdbcConnection = DriverManager.getConnection("jdbc:hsqldb:enlistmentdb", "sa", "");
        jdbcConnection.createStatement().execute("SET DATABASE REFERENTIAL INTEGRITY FALSE;");
        
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setDtdMetadata(false);
        
        IDataSet dataSet = builder.build(getClass().getResourceAsStream(datasetFilename));
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        //DatabaseOperation.DELETE_ALL.execute(connection, dataSet);
        connection.close(); 
	}
}
