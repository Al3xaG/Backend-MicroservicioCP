package com.exercise.microservicioCP.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.exercise.microservicioCP.domain.model.Client;
import com.exercise.microservicioCP.domain.repository.ClientRepository;
import com.exercise.microservicioCP.infrastructure.entity.ClientEntity;

@Repository
public class JpaClientRepository implements ClientRepository {
    
    private final SpringDataClientRepository springDataClientRepository;
    
    @Autowired
    public JpaClientRepository(SpringDataClientRepository springDataClientRepository) {
        this.springDataClientRepository = springDataClientRepository;
    }
    
    @Override
    public List<Client> findActiveClients() {
        return springDataClientRepository.findByStatus("ACTIVE")
                .stream()
                .map(this::convertToDomain)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Client> findById(Long id) {
        return springDataClientRepository.findById(id)
                .map(this::convertToDomain);
    }
    
    @Override
    public Optional<Client> findByClientId(String clientId) {
        return springDataClientRepository.findByClientId(clientId)
                .map(this::convertToDomain);
    }
    
    @Override
    public Optional<Client> findByIdentification(String identification) {
        return springDataClientRepository.findByIdentification(identification)
                .map(this::convertToDomain);
    }
    

    
    @Override
    public Client save(Client client) {
        ClientEntity entity = convertToEntity(client);
        ClientEntity savedEntity = springDataClientRepository.save(entity);
        return convertToDomain(savedEntity);
    }
    
    @Override
    public void deleteById(Long id) {
        springDataClientRepository.deleteById(id);
    }
    
    @Override
    public boolean existsById(Long id) {
        return springDataClientRepository.existsById(id);
    }
    
    @Override
    public boolean existsByClientId(String clientId) {
        return springDataClientRepository.findByClientId(clientId).isPresent();
    }
    
    @Override
    public boolean existsByIdentification(String identification) {
        return springDataClientRepository.findByIdentification(identification).isPresent();
    }
    
    // Métodos de conversión
    private Client convertToDomain(ClientEntity entity) {
        Client client = new Client();
        client.setId(entity.getId());
        client.setName(entity.getName());
        client.setGender(entity.getGender());
        client.setAge(entity.getAge());
        client.setIdentification(entity.getIdentification());
        client.setAddress(entity.getAddress());
        client.setPhone(entity.getPhone());
        client.setCreatedAt(entity.getCreatedAt());
        client.setUpdatedAt(entity.getUpdatedAt());
        client.setClientId(entity.getClientId());
        client.setPassword(entity.getPassword());
        client.setStatus(entity.getStatus());
        client.setRegistrationDate(entity.getRegistrationDate());
        return client;
    }
    
    private ClientEntity convertToEntity(Client client) {
        ClientEntity entity = new ClientEntity();
        entity.setId(client.getId());
        entity.setName(client.getName());
        entity.setGender(client.getGender());
        entity.setAge(client.getAge());
        entity.setIdentification(client.getIdentification());
        entity.setAddress(client.getAddress());
        entity.setPhone(client.getPhone());
        entity.setCreatedAt(client.getCreatedAt());
        entity.setUpdatedAt(client.getUpdatedAt());
        entity.setClientId(client.getClientId());
        entity.setPassword(client.getPassword());
        entity.setStatus(client.getStatus());
        entity.setRegistrationDate(client.getRegistrationDate());
        return entity;
    }
}
