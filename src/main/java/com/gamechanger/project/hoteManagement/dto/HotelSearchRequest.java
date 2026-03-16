package com.gamechanger.project.hoteManagement.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class HotelSearchRequest {
    private String city;
    private LocalDate startDate;       // check-in date
    private LocalDate endDate;         // check-out date
    private Integer roomsCount;

    private Integer page = 0;
    private Integer size = 10;
}
