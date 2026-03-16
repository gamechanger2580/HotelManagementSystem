package com.gamechanger.project.hoteManagement.service;

import com.gamechanger.project.hoteManagement.dto.HotelDto;
import com.gamechanger.project.hoteManagement.dto.HotelInfoDto;
import com.gamechanger.project.hoteManagement.dto.RoomDto;
import com.gamechanger.project.hoteManagement.entity.Hotel;
import com.gamechanger.project.hoteManagement.entity.Room;
import com.gamechanger.project.hoteManagement.exception.ResourceNotFoundException;
import com.gamechanger.project.hoteManagement.repository.HotelRepository;
import com.gamechanger.project.hoteManagement.repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final ModelMapper modelMapper;
    private final InventoryService inventoryService;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating a new hotel with name {} for adding", hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setActive(false);
        // hotel will be inactive and after all other inventory filling and other things
        // the admin can make it active, for that we can make a different controller and service
        // so that it is explicitly updated by admin.
        hotelRepository.save(hotel);
        log.info("Created a new hotel with ID {} and name {}", hotelDto.getId(), hotelDto.getName());
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long hotelId) {
        log.info("Getting the hotel with ID : {} for getting", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with Id " + hotelId));

        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long hotelId, HotelDto hotelDto) {
        log.info("Getting the hotel with Id {} for updation", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with Id: " + hotelId));
        modelMapper.map(hotelDto, hotel);
        hotel.setId(hotelId);
        hotel = hotelRepository.save(hotel);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    @Transactional
    public void deleteHotelById(Long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));


        for (Room room: hotel.getRooms()){
            inventoryService.deleteAllInventories(room);
            roomRepository.deleteById(room.getId());
        }
        hotelRepository.deleteById(hotelId);
    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId) {
        log.info("Getting the hotel with Id {} for activating hotel", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + hotelId));

        hotel.setActive(true);

        hotelRepository.save(hotel);
        
        // assuming only do it once
        for (Room room : hotel.getRooms()){
            inventoryService.initializeRoomForAYear(room);
        }
    }


    @Override
    public HotelInfoDto getHotelInfoById(Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id : " + hotelId));

        List<RoomDto> rooms = hotel
                .getRooms()
                .stream()
                .map((element) -> modelMapper.map(element, RoomDto.class))
                .toList();

        return new HotelInfoDto(modelMapper.map(hotel, HotelDto.class), rooms);
    }
}
