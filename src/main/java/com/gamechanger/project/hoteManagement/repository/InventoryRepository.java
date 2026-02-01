package com.gamechanger.project.hoteManagement.repository;

import com.gamechanger.project.hoteManagement.entity.Inventory;
import com.gamechanger.project.hoteManagement.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    void deleteByDateAfterAndRoom(LocalDate date, Room room);
}
