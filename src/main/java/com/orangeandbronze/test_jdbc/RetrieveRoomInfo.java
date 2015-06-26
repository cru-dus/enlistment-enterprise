package com.orangeandbronze.test_jdbc;

import java.sql.*;

public class RetrieveRoomInfo {
	
	static int getRoomCapacity(String roomName) {
        try (Connection conn = DriverManager.getConnection("jdbc:hsqldb:enlistmentdb", "sa", "")) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT capacity FROM rooms WHERE room_name = ?");
            stmt.setString(1, roomName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt("capacity");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static void main(String[] args){
		try (Connection conn = DriverManager.getConnection("jdbc:hsqldb:enlistmentdb", "sa", ""))  {
			//Statement stmt = conn.createStatement();
			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM rooms");
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
		        System.out.println(rs.getString("room_name") 
		                + " - " + rs.getInt("capacity"));
		    }
			
		} catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}
}
