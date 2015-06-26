package com.orangeandbronze.test_jdbc;

import java.sql.*;
import com.orangeandbronze.enlistment.domain.*;
public class InsertRoomInfo {
    static void insertNewRooms(Room[] rooms) {
    	try (Connection conn = DriverManager.getConnection("jdbc:hsqldb:enlistmentdb", "sa", "")) {
    	    PreparedStatement stmt = conn.prepareStatement(
    	        "INSERT INTO rooms (room_name, capacity) VALUES (?, ?)");
    	    for (Room room : rooms) {
    	       stmt.setString(1, room.getName());
    	       stmt.setInt(2, room.getCapacity());
    	       stmt.addBatch();
    	    }
    	    stmt.executeBatch(); // passed to DB just once
    	} catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    public static void main(String[] args) {
        Room[] rooms = {new Room("MUSIC27", 30), new Room("CAL204", 35)};
        insertNewRooms(rooms);      
        RetrieveRoomInfo.main(new String[0]);       
    }
}