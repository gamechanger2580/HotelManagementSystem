package com.gamechanger.project.hoteManagement.advice;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiResponse<T> {

//    @JsonFormat(pattern = "hh:mm:ss dd-mm-yyyy")
    private LocalDateTime timestamp;
    private T data; // either data field
    private ApiError error;  // or error field
    // if data then error will not be there and vice-versa

    public ApiResponse(){
        this.timestamp = LocalDateTime.now();
        // everytime it is called will use this for timestamp
    }

    public ApiResponse(T data){
        this();    // it will call the timestamp
        // as well as it is default constructor
        this.data = data;
    }


    public ApiResponse(ApiError error) {
        this();
        this.error = error;
    }
}
