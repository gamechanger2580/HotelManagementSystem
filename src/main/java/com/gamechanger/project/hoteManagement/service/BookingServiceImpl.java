package com.gamechanger.project.hoteManagement.service;

import com.gamechanger.project.hoteManagement.dto.BookingDto;
import com.gamechanger.project.hoteManagement.dto.BookingRequest;
import com.gamechanger.project.hoteManagement.dto.GuestDto;
import com.gamechanger.project.hoteManagement.entity.*;
import com.gamechanger.project.hoteManagement.entity.enums.BookingStatus;
import com.gamechanger.project.hoteManagement.exception.ResourceNotFoundException;
import com.gamechanger.project.hoteManagement.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final GuestRepository guestRepository;
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    private final BookingRepository bookingRepository;
    private final InventoryRepository inventoryRepository;
    private final ModelMapper  modelMapper;

    @Override
    @Transactional
    public BookingDto initalizeBooking(BookingRequest bookingRequest) {
        log.info("Initializing Booking for hotel : {}, room : {}, date : {}-{}",
                bookingRequest.getHotelId(),
                bookingRequest.getRoomId(),
                bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate());
        Hotel hotel = hotelRepository.findById(bookingRequest.getHotelId())
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + bookingRequest.getHotelId()));

        Room room = roomRepository.findById(bookingRequest.getRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + bookingRequest.getRoomId()));

        // Getting the inventory & while getting inventory
        // we will lock the rooms for that particular date
        // so that if another person comes to book for same date they will not find it
        // & this lock if the user books then for that amount of time or will show for 10days
        // it is called pasimistic lock so for that time period
        // so that no one else can lock that rows or book

        List<Inventory> inventoryList = inventoryRepository.findAndLockAvailableInventory(
                room.getId(),
                bookingRequest.getCheckInDate(),
                bookingRequest.getCheckOutDate(),
                bookingRequest.getRoomsCount()
        );

        long daysCount = ChronoUnit.DAYS.between(bookingRequest.getCheckInDate(), bookingRequest.getCheckOutDate())  + 1;

        if(inventoryList.size() != daysCount){
            // means we do not have rooms in between these day
            throw new IllegalStateException("Room is not available anymore");
        }

        // Reserve the room/update the booked count of inventories
        // Means inventory is available
        for (Inventory inventory : inventoryList){
            inventory.setReservedCount(inventory.getReservedCount() + bookingRequest.getRoomsCount());
        }

        // bookedCount updated
        inventoryRepository.saveAll(inventoryList);

        // TODO: CALC DYNAMIC PRICE

        // Create the booking
        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.PENDING)
                .hotel(hotel)
                .room(room)
                .checkInDate(bookingRequest.getCheckInDate())
                .checkOutDate(bookingRequest.getCheckOutDate())
                .roomsCount(bookingRequest.getRoomsCount())
                .user(getCurrUser())
                .amount(BigDecimal.TEN)
                .build();

        booking = bookingRepository.save(booking);
        return modelMapper.map(booking, BookingDto.class);
    }

    @Override
    @Transactional
    public BookingDto addGuests(Long bookingId, List<GuestDto> guestDtos) {
        log.info("Adding guest for booking with id : {}",
                bookingId);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking with id " + bookingId + " not found"));

        if(hasBookingExpired(booking)){
            throw new IllegalStateException("Booking with id " + bookingId + " is already expired." );
        }

        if(booking.getBookingStatus() != BookingStatus.PENDING){
            throw new IllegalStateException("Booking is not under reserved state, cannot add guests");
        }

        for(GuestDto guestDto : guestDtos){
            Guest guest = modelMapper.map(guestDto, Guest.class);
            guest.setUser(getCurrUser());
            guest = guestRepository.save(guest);
            booking.getGuests().add(guest);
        }

        booking.setBookingStatus(BookingStatus.GUEST_ADDED);
        booking = bookingRepository.save(booking);

        return modelMapper.map(booking, BookingDto.class);
    }

    public boolean hasBookingExpired(Booking booking){
        return booking.getCreatedAt().plusMinutes(10).isBefore(LocalDateTime.now());
    }

    public User getCurrUser() {
        User user = new User();
        user.setId(1L);   // TODO : REMOVE DUMMY USER
        return user;
    }
}
