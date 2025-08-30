package com.exercise.microservicioCP.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.exercise.microservicioCP.application.dto.ClientDTO;
import com.exercise.microservicioCP.domain.exception.BusinessException;
import com.exercise.microservicioCP.domain.exception.ResourceNotFoundException;
import com.exercise.microservicioCP.domain.model.Client;
import com.exercise.microservicioCP.domain.repository.ClientRepository;

@Service
@Transactional
public class ClientService {
    
    private final ClientRepository clientRepository;
    
    @Autowired
    public ClientService(@Qualifier("jpaClientRepository") ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    

    public List<ClientDTO> findAll() {
        return clientRepository.findActiveClients()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public ClientDTO findById(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
        return convertToDTO(client);
    }
    
    public ClientDTO findByIdentification(String identification) {
        Client client = clientRepository.findByIdentification(identification)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "identification", identification));
        return convertToDTO(client);
    }
    
    public ClientDTO findByClientId(String clientId) {
        Client client = clientRepository.findByClientId(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "clientId", clientId));
        return convertToDTO(client);
    }
    

    
    public ClientDTO create(ClientDTO clientDTO) {
        if (clientDTO.getClientId() == null || clientDTO.getClientId().isEmpty()) {
            clientDTO.setClientId(UUID.randomUUID().toString());
        }
        
        if (clientRepository.existsByClientId(clientDTO.getClientId())) {
            throw new BusinessException("Client with clientId already exists: " + clientDTO.getClientId());
        }
        
        if (clientRepository.existsByIdentification(clientDTO.getIdentification())) {
            throw new BusinessException("Person with identification already exists: " + clientDTO.getIdentification());
        }
        
        Client client = convertToEntity(clientDTO);
        client.setCreatedAt(LocalDateTime.now());
        client.setUpdatedAt(LocalDateTime.now());
        client.setRegistrationDate(LocalDateTime.now());
        
        if (client.getStatus() == null || client.getStatus().isEmpty()) {
            client.setStatus("ACTIVE");
        }
        
        Client savedClient = clientRepository.save(client);
        return convertToDTO(savedClient);
    }
    
    public ClientDTO update(Long id, ClientDTO clientDTO) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
        
        // Solo permitir actualizar campos no críticos
        existingClient.setName(clientDTO.getName());
        existingClient.setGender(clientDTO.getGender());
        existingClient.setAge(clientDTO.getAge());
        existingClient.setAddress(clientDTO.getAddress());
        existingClient.setPhone(clientDTO.getPhone());
        existingClient.setPassword(clientDTO.getPassword());
        existingClient.setUpdatedAt(LocalDateTime.now());
        
        Client updatedClient = clientRepository.save(existingClient);
        return convertToDTO(updatedClient);
    }
    
    public ClientDTO deactivateById(Long id) {
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
        
        existingClient.setStatus("INACTIVE");
        existingClient.setUpdatedAt(LocalDateTime.now());
        
        Client deactivatedClient = clientRepository.save(existingClient);
        return convertToDTO(deactivatedClient);
    }
    
    public boolean existsById(Long id) {
        return clientRepository.existsById(id);
    }
    
    public boolean existsByClientId(String clientId) {
        return clientRepository.existsByClientId(clientId);
    }
    
    public boolean existsByIdentification(String identification) {
        return clientRepository.existsByIdentification(identification);
    }
    
    private ClientDTO convertToDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setGender(client.getGender());
        dto.setAge(client.getAge());
        dto.setIdentification(client.getIdentification());
        dto.setAddress(client.getAddress());
        dto.setPhone(client.getPhone());
        dto.setCreatedAt(client.getCreatedAt());
        dto.setUpdatedAt(client.getUpdatedAt());
        dto.setClientId(client.getClientId());
        dto.setPassword(client.getPassword());
        dto.setStatus(client.getStatus());
        dto.setRegistrationDate(client.getRegistrationDate());
        return dto;
    }
    
    private Client convertToEntity(ClientDTO dto) {
        Client client = new Client();
        client.setId(dto.getId());
        client.setName(dto.getName());
        client.setGender(dto.getGender());
        client.setAge(dto.getAge());
        client.setIdentification(dto.getIdentification());
        client.setAddress(dto.getAddress());
        client.setPhone(dto.getPhone());
        client.setClientId(dto.getClientId());
        client.setPassword(dto.getPassword());
        client.setStatus(dto.getStatus());
        return client;
    }
}
