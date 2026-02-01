package com.gamechanger.project.hoteManagement.service;

import com.gamechanger.project.hoteManagement.entity.Room;

public interface InventoryService {
    void initializeRoomForAYear(Room room);

    void deleteFutureInventory(Room room);
}
