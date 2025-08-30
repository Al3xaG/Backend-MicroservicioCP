package com.exercise.microservicioCP.infrastructure.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clients")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity extends PersonEntity {
    
    @Column(name = "client_id", nullable = false, unique = true, length = 36)
    private String clientId;
    
    @Column(name = "password", nullable = false, length = 100)
    private String password;
    
    @Column(name = "status", nullable = false, length = 20)
    private String status;
    
    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @PrePersist
    @Override
    protected void onCreate() {
        super.onCreate();
        if (registrationDate == null) {
            registrationDate = LocalDateTime.now();
        }
        if (status == null || status.isEmpty()) {
            status = "ACTIVE";
        }
    }
}
