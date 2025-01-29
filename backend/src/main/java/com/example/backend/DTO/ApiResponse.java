package com.example.backend.DTO;



import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ApiResponse<T> {

    // Getters and Setters
    private int status;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    // Constructor
    public ApiResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

}
