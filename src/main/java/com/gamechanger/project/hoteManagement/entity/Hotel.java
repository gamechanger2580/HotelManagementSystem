package com.gamechanger.project.hoteManagement.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "hotel_name")
    private String name;

    private String city;

    @Column(columnDefinition = "TEXT[]") //
    private String[] photos; // we have to give it column definition
    // as the db will not be able to convert it itself , for String it will be able to but not for this
    
    @Column(columnDefinition = "TEXT[]")
    private String[] amenities;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Embedded
    private HotelContactInfo contactInfo;
    // it will make it like this: contact_info_address, contact_info_phone_number
    // so this is getting embedded in this table and new table will not be formed

    @Column(nullable = false)
    private Boolean active;
}
