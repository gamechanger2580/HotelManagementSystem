package com.gamechanger.project.hoteManagement.dto;

import com.gamechanger.project.hoteManagement.entity.HotelContactInfo;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HotelDto {
    private Long id;
    private String name;
    private String city;
    private String[] photos;
    private String[] amenities;
    private HotelContactInfo contactInfo;
    private Boolean active;
}
