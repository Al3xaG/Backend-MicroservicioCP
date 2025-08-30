package com.exercise.microservicioCP.domain.repository;

import java.util.List;
import java.util.Optional;

import com.exercise.microservicioCP.domain.model.Client;

public interface ClientRepository {
    // Métodos para Client (que incluye toda la funcionalidad de Person)
    List<Client> findActiveClients();
    Optional<Client> findById(Long id);
    Optional<Client> findByClientId(String clientId);
    Optional<Client> findByIdentification(String identification);

    Client save(Client client);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByClientId(String clientId);
    boolean existsByIdentification(String identification);
}
