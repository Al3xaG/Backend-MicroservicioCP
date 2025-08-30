package com.exercise.microservicioCP.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Client extends Person {
    private String clientId;
    private String password;
    private String status;
    private LocalDateTime registrationDate;

    public boolean isActive() {
        return "ACTIVE".equalsIgnoreCase(status);
    }

    public boolean isInactive() {
        return "INACTIVE".equalsIgnoreCase(status);
    }
}
