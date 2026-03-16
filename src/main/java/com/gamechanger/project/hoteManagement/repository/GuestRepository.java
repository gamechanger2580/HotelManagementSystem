package com.gamechanger.project.hoteManagement.repository;

import com.gamechanger.project.hoteManagement.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
}