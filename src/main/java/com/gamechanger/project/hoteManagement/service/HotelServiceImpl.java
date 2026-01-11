package com.gamechanger.project.hoteManagement.service;

import com.gamechanger.project.hoteManagement.dto.HotelDto;
import com.gamechanger.project.hoteManagement.entity.Hotel;
import com.gamechanger.project.hoteManagement.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel with name {}", hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        // hotel will be inactive and after all other inventory filling and other things
        // the admin can make it active, for that we can make a different controller and service
        // so that it is explicitly updated by admin.
        hotelRepository.save(hotel);
        log.info("Created a new hotel with ID {} and name {}", hotelDto.getId(), hotelDto.getName());
        return hotelDto;
    }

    @Override
    public HotelDto getHotelById(Long hotelId) {
        log.info("Getting the hotel with ID : {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new RuntimeException("Hotel not found with Id " + hotelId));

        HotelDto hotelDto = modelMapper.map(hotel, HotelDto.class);
        return hotelDto;
    }
}
