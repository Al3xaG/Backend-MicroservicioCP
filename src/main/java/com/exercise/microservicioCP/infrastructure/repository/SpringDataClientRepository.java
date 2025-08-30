package com.exercise.microservicioCP.infrastructure.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exercise.microservicioCP.infrastructure.entity.ClientEntity;

@Repository
public interface SpringDataClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByClientId(String clientId);
    Optional<ClientEntity> findByIdentification(String identification);
    List<ClientEntity> findByStatus(String status);
}
