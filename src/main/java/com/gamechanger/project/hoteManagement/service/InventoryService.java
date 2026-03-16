package com.gamechanger.project.hoteManagement.service;

import com.gamechanger.project.hoteManagement.dto.HotelDto;
import com.gamechanger.project.hoteManagement.dto.HotelSearchRequest;
import com.gamechanger.project.hoteManagement.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {
    void initializeRoomForAYear(Room room);

    void deleteFutureInventory(Room room);

    void deleteAllInventories(Room room);

    Page<HotelDto> searchHotels(HotelSearchRequest hotelSearchRequest);
}
