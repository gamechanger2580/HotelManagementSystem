package com.gamechanger.project.hoteManagement.service;

import com.gamechanger.project.hoteManagement.dto.HotelDto;
import com.gamechanger.project.hoteManagement.dto.HotelInfoDto;
import org.jspecify.annotations.Nullable;

public interface HotelService {

    HotelInfoDto getHotelInfoById(Long hotelId);

    HotelDto createNewHotel(HotelDto hotelDto);

    HotelDto getHotelById(Long hotelId);

    HotelDto updateHotelById(Long hotelId, HotelDto hotelDto);

    void deleteHotelById(Long hotelId);

    void activateHotel(Long hotelId);
}
