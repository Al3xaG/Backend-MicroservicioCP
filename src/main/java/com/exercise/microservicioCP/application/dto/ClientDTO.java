package com.exercise.microservicioCP.application.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private Long id;
    private String name;
    private String gender;
    private Integer age;
    private String identification;
    private String address;
    private String phone;
    
    @JsonProperty(required = false)
    private String clientId;
    
    private String password;
    
    @JsonIgnore
    @JsonProperty(required = false)
    private String status;
    
    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    
    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registrationDate;

    @JsonIgnore
    public String getFullName() {
        return name;
    }

    @JsonIgnore
    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(status);
    }

    @JsonIgnore
    public boolean isInactive() {
        return "INACTIVE".equalsIgnoreCase(status);
    }
}
