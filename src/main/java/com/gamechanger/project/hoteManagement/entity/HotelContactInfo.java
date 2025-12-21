package com.gamechanger.project.hoteManagement.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable  // because of this a new table will not be created for this
// instead it will get save in a different model (eg: Hotel) wherever we specify @Embedded
// which means it will get embedded there. // it can be used many times and not in jus one entity
public class HotelContactInfo {
    private String address;
    private String phoneNumber;
    private String email;
    private String location;
}