package com.orangeandbronze.enlistment.dao;

import java.util.Collection;

import com.orangeandbronze.enlistment.domain.Room;

public interface RoomDAO {
	Room findBy(String roomName);
	
	Collection<Room> findAll();
}
