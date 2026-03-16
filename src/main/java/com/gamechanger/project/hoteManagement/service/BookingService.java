package com.gamechanger.project.hoteManagement.service;

import com.gamechanger.project.hoteManagement.dto.BookingDto;
import com.gamechanger.project.hoteManagement.dto.BookingRequest;
import com.gamechanger.project.hoteManagement.dto.GuestDto;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface BookingService {

    BookingDto initalizeBooking(BookingRequest bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtos);
}
