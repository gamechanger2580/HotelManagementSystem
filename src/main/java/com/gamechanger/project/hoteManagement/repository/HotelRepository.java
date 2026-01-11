package com.gamechanger.project.hoteManagement.repository;

import com.gamechanger.project.hoteManagement.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
