package com.orangeandbronze.enlistment.service;

import com.orangeandbronze.enlistment.domain.Room;

public class RoomDTO {
	public final String roomName;
	public final int capacity;
	
	private RoomDTO(Room room){
		roomName = room.getName();
		capacity = room.getCapacity();
	}
	
	public RoomDTO(String roomName, int capacity){
		this.roomName = roomName;
		this.capacity = capacity;
	}
	
	public RoomDTO(String roomName){
		this(roomName, 0);
	}
	
	public static RoomDTO getInfoOf(Room room){
		return new RoomDTO(room);
	}
	
	@Override
	public String toString(){
		return roomName;
	}
}
