package com.gamechanger.project.hoteManagement.dto;

import com.gamechanger.project.hoteManagement.entity.Hotel;
import com.gamechanger.project.hoteManagement.entity.Room;
import com.gamechanger.project.hoteManagement.entity.User;
import com.gamechanger.project.hoteManagement.entity.enums.BookingStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {
    private Long id;
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BookingStatus bookingStatus;
    private Set<GuestDto> guests;
}
