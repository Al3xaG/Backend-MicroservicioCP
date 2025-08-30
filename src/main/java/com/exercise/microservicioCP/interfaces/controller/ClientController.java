package com.exercise.microservicioCP.interfaces.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exercise.microservicioCP.application.dto.ClientDTO;
import com.exercise.microservicioCP.application.service.ClientService;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientController {
    
    private final ClientService clientService;
    
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }
    

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        List<ClientDTO> clients = clientService.findAll();
        return ResponseEntity.ok(clients);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        ClientDTO client = clientService.findById(id);
        return ResponseEntity.ok(client);
    }
    
    @GetMapping("/identification/{identification}")
    public ResponseEntity<ClientDTO> getClientByIdentification(@PathVariable String identification) {
        ClientDTO client = clientService.findByIdentification(identification);
        return ResponseEntity.ok(client);
    }
    
    @GetMapping("/clientId/{clientId}")
    public ResponseEntity<ClientDTO> getClientByClientId(@PathVariable String clientId) {
        ClientDTO client = clientService.findByClientId(clientId);
        return ResponseEntity.ok(client);
    }
    

    
    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody ClientDTO clientDTO) {
        ClientDTO createdClient = clientService.create(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdClient);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        ClientDTO updatedClient = clientService.update(id, clientDTO);
        return ResponseEntity.ok(updatedClient);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ClientDTO> deleteClient(@PathVariable Long id) {
        ClientDTO deactivatedClient = clientService.deactivateById(id);
        return ResponseEntity.ok(deactivatedClient);
    }
    

    @GetMapping("/exists/{id}")
    public ResponseEntity<Boolean> existsById(@PathVariable Long id) {
        boolean exists = clientService.existsById(id);
        return ResponseEntity.ok(exists);
    }
    
    @GetMapping("/exists/clientId/{clientId}")
    public ResponseEntity<Boolean> existsByClientId(@PathVariable String clientId) {
        boolean exists = clientService.existsByClientId(clientId);
        return ResponseEntity.ok(exists);
    }
    
    @GetMapping("/exists/identification/{identification}")
    public ResponseEntity<Boolean> existsByIdentification(@PathVariable String identification) {
        boolean exists = clientService.existsByIdentification(identification);
        return ResponseEntity.ok(exists);
    }
}
