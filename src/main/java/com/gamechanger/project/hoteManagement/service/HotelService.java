package com.gamechanger.project.hoteManagement.service;

import com.gamechanger.project.hoteManagement.dto.HotelDto;

public interface HotelService {

    HotelDto createNewHotel(HotelDto hotelDto);

    HotelDto getHotelById(Long hotelId);
}
