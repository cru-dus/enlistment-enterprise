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

import com.orangeandbronze.enlistment.dao.RoomDAO;
import com.orangeandbronze.enlistment.domain.Room;

public class RoomDaoJdbcIT {
	
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
	public void findByRoom() throws Exception{
		setupDatabase("RoomDAO.findByRoom.xml");
		
	    RoomDAO dao = new RoomDaoJdbc();
	    final String roomName = "AVR1";
	    final int capacity = 20;
	    Room actualRoom = dao.findBy(roomName);
	    Room expectedRoom = new Room(roomName,capacity);
	    assertEquals(expectedRoom, actualRoom);
	}
	
	@Test
	public void findAllRooms() throws Exception{
		setupDatabase("RoomDAO.findByRoom.xml");
		
	    RoomDAO dao = new RoomDaoJdbc();
	    
	    Collection<Room> actualRooms = dao.findAll();
	    Collection<Room> expectedRooms = new ArrayList<Room>();
	    
	    expectedRooms.add(new Room("AVR1", 20));
	    expectedRooms.add(new Room("MATH105", 20));
	    expectedRooms.add(new Room("IP103", 35));
	    expectedRooms.add(new Room("AS113", 35));
	    
	    assertTrue(actualRooms.containsAll(expectedRooms));
	    assertTrue(expectedRooms.containsAll(actualRooms));
	}
	
	
}
