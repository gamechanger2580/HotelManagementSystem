package com.gamechanger.project.hoteManagement.dto;

import com.gamechanger.project.hoteManagement.entity.User;
import com.gamechanger.project.hoteManagement.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class GuestDto {

    private Long id;
    private User user;
    private String name;
    private Gender gender;
    private Integer age;
}
