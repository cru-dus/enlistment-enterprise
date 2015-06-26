package com.orangeandbronze.enlistment.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.orangeandbronze.enlistment.dao.DataAccessException;
import com.orangeandbronze.enlistment.dao.RoomDAO;
import com.orangeandbronze.enlistment.domain.Room;

public class RoomDaoJdbc extends AbstractDaoJdbc implements RoomDAO {

	@Override
	public Room findBy(String roomName) {
		try {
			initializeConnection();
    		PreparedStatement stmt = createStatement(getSql("RoomDaoJdbc.findByRoomName.sql"));
            stmt.setString(1, roomName);
            
            ResultSet rs = stmt.executeQuery();
            
            Room room = null;
            
            while (rs.next()) {
            	room = new Room(rs.getString("room_name"), rs.getInt("capacity"));
            }
            
            if(room == null){
            	throw new DataAccessException("Room "+roomName+ " does not exist in our database.");
            }
            connection.close();
            return room;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
	}

	@Override
	public Collection<Room> findAll() {
		try{
			initializeConnection();
    		PreparedStatement stmt = createStatement(getSql("RoomDaoJdbc.findAll.sql"));
            
            ResultSet rs = stmt.executeQuery();
            
            Set<Room> rooms = new HashSet<Room>();
            
            while (rs.next()) {
                rooms.add(new Room(rs.getString("room_name"), rs.getInt("capacity")));
            }
            connection.close();
            return rooms;
        } catch (SQLException e) {
            throw new DataAccessException(e);
        }
	}
}
