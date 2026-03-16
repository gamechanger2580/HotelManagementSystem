package com.gamechanger.project.hoteManagement.controller;

import com.gamechanger.project.hoteManagement.dto.BookingDto;
import com.gamechanger.project.hoteManagement.dto.BookingRequest;
import com.gamechanger.project.hoteManagement.dto.GuestDto;
import com.gamechanger.project.hoteManagement.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bookings")
public class HotelBookingController {

    private final BookingService bookingService;

    @PostMapping("/init")
    public ResponseEntity<BookingDto> initalizeBooking(@RequestBody BookingRequest bookingRequest){
        return ResponseEntity.ok(bookingService.initalizeBooking(bookingRequest));
    }

    @PostMapping("/{bookingId}/addGuests")
    public ResponseEntity<BookingDto> addGuests(@PathVariable Long bookingId, @RequestBody List<GuestDto> guestDtoList){
        return ResponseEntity.ok(bookingService.addGuests(bookingId, guestDtoList));
    }
    
}
