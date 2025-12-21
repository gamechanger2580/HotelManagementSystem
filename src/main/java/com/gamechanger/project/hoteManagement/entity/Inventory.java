package com.gamechanger.project.hoteManagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(
        indexes = {},
        uniqueConstraints = @UniqueConstraint(
        name = "unqiue_hotel_room_date",
        columnNames = {"hotel_id", "room_id", "date"} // combination these 3 things should be unique
))
public class Inventory {
    // inventory is storing info about room, one every particular days.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    // this means whenever we are fetching Inventory at that time only
    // we will fetch the hotel as well.
    @JoinColumn(name = "hotel_id", nullable = false)  // column foreign key as hotel id
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false) // many inventories can belong to particular room
    // this means if in code the call of Room is there
    // only that time it will fetch room
    private Room room;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer bookedCount;

    @Column(nullable = false)
    private Integer totalCount;

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal surgeFactor;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price; // this will be calc price = basePrice(in Room entity) * surgeFactor

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private Boolean closed;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

