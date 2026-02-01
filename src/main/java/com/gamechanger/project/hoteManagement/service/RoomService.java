package com.gamechanger.project.hoteManagement.service;

import com.gamechanger.project.hoteManagement.dto.RoomDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {

    RoomDto createNewRoom(Long hotelId,RoomDto roomDto);

    List<RoomDto> getAllRoomsInHotel(Long hotelId);

    RoomDto getRoomById(Long roomId);

    void deleteRoomById(Long hotelId); // with that we will be deleting the inventory as well.
}
