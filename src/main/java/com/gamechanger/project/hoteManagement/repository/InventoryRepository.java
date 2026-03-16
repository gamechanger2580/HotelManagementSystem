package com.gamechanger.project.hoteManagement.repository;

import com.gamechanger.project.hoteManagement.entity.Hotel;
import com.gamechanger.project.hoteManagement.entity.Inventory;
import com.gamechanger.project.hoteManagement.entity.Room;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    void deleteByDateAfterAndRoom(LocalDate date, Room room);

    void deleteByRoom(Room room);

    @Query(
            """
            select distinct i.hotel
            from Inventory i
            where i.city = :city
                and i.date between :startDate and :endDate
                and i.closed = false
                and (i.totalCount - i.bookedCount - i.reservedCount) >= :roomsCount
            group by i.hotel, i.room
            having count(i.date) = :dateCount
            """
    )
    Page<Hotel> findHotelsWithAvailableInventory(
            @Param("city") String city,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomsCount") Integer roomsCount,
            @Param("dateCount") Long dateCount,
            Pageable pageable 
    );

    // Criteria:  (for above query)
    // We want to give at least on hotel for search while the user is searching
    // Look for inventory
    // in inventory it should check for the date which user has put for eg ; find hotels
    // betn 01/01/2026 - 05/01/2026
    // based on date check the availability
    // also that room should be there for 5 days as we gave date
    // that means we are looking for hotel which have rooms for 5 days only
    // also check totalCount & bookedCount , totalCount - bookedCount >= roomsCount
    // Group thn response by room
    // and get the response by unique hotels
    // and the close = false should be there


    @Query(
            """
            select i from Inventory i
            where i.room.id = :roomId
                and i.date between :startDate and :endDate
                and i.closed = false
                and (i.totalCount - i.bookedCount - i.reservedCount) >= :roomsCount
            """
    )
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    // this we will get the inventories for that room for that day
    // this avoids race condition so no other
    // user is allowed to query for the same rows or same day
    List<Inventory> findAndLockAvailableInventory(
            @Param("roomId") Long roomId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("roomsCount") Integer roomsCount
    );
}
