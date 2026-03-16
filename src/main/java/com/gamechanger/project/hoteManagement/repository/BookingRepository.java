package com.gamechanger.project.hoteManagement.repository;

import com.gamechanger.project.hoteManagement.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
