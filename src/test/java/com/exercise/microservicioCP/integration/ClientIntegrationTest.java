package com.exercise.microservicioCP.integration;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.exercise.microservicioCP.application.dto.ClientDTO;
import com.exercise.microservicioCP.application.service.ClientService;
import com.exercise.microservicioCP.domain.model.Client;
import com.exercise.microservicioCP.domain.repository.ClientRepository;
import com.exercise.microservicioCP.infrastructure.entity.ClientEntity;
import com.exercise.microservicioCP.infrastructure.repository.JpaClientRepository;
import com.exercise.microservicioCP.infrastructure.repository.SpringDataClientRepository;

/**
 * Prueba de integración para el flujo completo de clientes
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("Prueba de integración del flujo completo de clientes")
class ClientIntegrationTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JpaClientRepository jpaClientRepository;

    @Autowired
    private SpringDataClientRepository springDataClientRepository;

    private ClientDTO testClientDTO;
    private String testClientId;
    private String testIdentification;

    @BeforeEach
    void setUp() {
        // Preparar datos de prueba
        testClientId = "TEST" + System.currentTimeMillis();
        testIdentification = "ID" + System.currentTimeMillis();
        
        testClientDTO = new ClientDTO();
        testClientDTO.setName("Cliente de Prueba");
        testClientDTO.setGender("FEMALE");
        testClientDTO.setAge(25);
        testClientDTO.setIdentification(testIdentification);
        testClientDTO.setAddress("Dirección de Prueba 123");
        testClientDTO.setPhone("3001234567");
        testClientDTO.setPassword("password123");
        testClientDTO.setStatus("ACTIVE");
    }

    @Test
    @DisplayName("Debería integrar correctamente todo el flujo CRUD de clientes")
    void shouldIntegrateCompleteClientCRUDFlow() {
        ClientDTO createdClient = clientService.create(testClientDTO);
        
        assertNotNull(createdClient, "El cliente creado no debe ser null");
        assertNotNull(createdClient.getId(), "El ID debe ser generado automáticamente");
        assertEquals(testClientDTO.getName(), createdClient.getName(), "El nombre debe coincidir");
        assertEquals(testClientDTO.getGender(), createdClient.getGender(), "El género debe coincidir");
        assertEquals(testClientDTO.getAge(), createdClient.getAge(), "La edad debe coincidir");
        assertEquals(testClientDTO.getIdentification(), createdClient.getIdentification(), "La identificación debe coincidir");
        assertEquals(testClientDTO.getAddress(), createdClient.getAddress(), "La dirección debe coincidir");
        assertEquals(testClientDTO.getPhone(), createdClient.getPhone(), "El teléfono debe coincidir");
        assertEquals("ACTIVE", createdClient.getStatus(), "El status debe ser ACTIVE por defecto");
        assertNotNull(createdClient.getCreatedAt(), "CreatedAt debe ser establecido");
        assertNotNull(createdClient.getUpdatedAt(), "UpdatedAt debe ser establecido");
        assertNotNull(createdClient.getRegistrationDate(), "RegistrationDate debe ser establecido");

        boolean existsById = clientService.existsById(createdClient.getId());
        boolean existsByClientId = clientService.existsByClientId(createdClient.getClientId());
        boolean existsByIdentification = clientService.existsByIdentification(createdClient.getIdentification());
        
        assertTrue(existsById, "El cliente debe existir por ID");
        assertTrue(existsByClientId, "El cliente debe existir por clientId");
        assertTrue(existsByIdentification, "El cliente debe existir por identificación");

        ClientDTO foundById = clientService.findById(createdClient.getId());
        ClientDTO foundByClientId = clientService.findByClientId(createdClient.getClientId());
        ClientDTO foundByIdentification = clientService.findByIdentification(createdClient.getIdentification());
        
        assertNotNull(foundById, "El cliente debe ser encontrado por ID");
        assertNotNull(foundByClientId, "El cliente debe ser encontrado por clientId");
        assertNotNull(foundByIdentification, "El cliente debe ser encontrado por identificación");
        assertEquals(createdClient.getId(), foundById.getId(), "Los IDs deben coincidir");
        assertEquals(createdClient.getClientId(), foundByClientId.getClientId(), "Los clientIds deben coincidir");
        assertEquals(createdClient.getIdentification(), foundByIdentification.getIdentification(), "Las identificaciones deben coincidir");

        List<ClientDTO> allClients = clientService.findAll();
        
        assertNotNull(allClients, "La lista de clientes no debe ser null");
        assertTrue(allClients.size() > 0, "Debe haber al menos un cliente en la lista");
        boolean clientInList = allClients.stream()
                .anyMatch(client -> client.getId().equals(createdClient.getId()));
        assertTrue(clientInList, "El cliente creado debe aparecer en la lista");

        ClientDTO updateDTO = new ClientDTO();
        updateDTO.setName("Cliente de Prueba Actualizado");
        updateDTO.setGender("FEMALE");
        updateDTO.setAge(26);
        updateDTO.setAddress("Nueva Dirección 456");
        updateDTO.setPhone("3009876543");
        updateDTO.setPassword("newpassword123");
        
        ClientDTO updatedClient = clientService.update(createdClient.getId(), updateDTO);
        
        assertNotNull(updatedClient, "El cliente actualizado no debe ser null");
        assertEquals(updateDTO.getName(), updatedClient.getName(), "El nombre debe ser actualizado");
        assertEquals(updateDTO.getAge(), updatedClient.getAge(), "La edad debe ser actualizada");
        assertEquals(updateDTO.getAddress(), updatedClient.getAddress(), "La dirección debe ser actualizada");
        assertEquals(updateDTO.getPhone(), updatedClient.getPhone(), "El teléfono debe ser actualizado");
        assertEquals(updateDTO.getPassword(), updatedClient.getPassword(), "La contraseña debe ser actualizada");
        assertEquals(createdClient.getClientId(), updatedClient.getClientId(), "El clientId no debe cambiar");
        assertEquals(createdClient.getIdentification(), updatedClient.getIdentification(), "La identificación no debe cambiar");
        assertNotNull(updatedClient.getUpdatedAt(), "UpdatedAt debe ser actualizado");

        ClientDTO deactivatedClient = clientService.deactivateById(createdClient.getId());
        
        assertNotNull(deactivatedClient, "El cliente desactivado no debe ser null");
        assertEquals("INACTIVE", deactivatedClient.getStatus(), "El status debe ser INACTIVE");
        assertNotNull(deactivatedClient.getUpdatedAt(), "UpdatedAt debe ser actualizado");

        List<ClientDTO> activeClientsAfterDeactivation = clientService.findAll();
        
        boolean clientStillInActiveList = activeClientsAfterDeactivation.stream()
                .anyMatch(client -> client.getId().equals(createdClient.getId()));
        assertFalse(clientStillInActiveList, "El cliente desactivado no debe aparecer en la lista activa");

        ClientDTO stillExistsClient = clientService.findById(createdClient.getId());
        
        assertNotNull(stillExistsClient, "El cliente desactivado debe seguir existiendo en la base");
        assertEquals("INACTIVE", stillExistsClient.getStatus(), "El status debe seguir siendo INACTIVE");
        assertEquals(createdClient.getId(), stillExistsClient.getId(), "El ID debe ser el mismo");

        Optional<Client> clientFromJpa = jpaClientRepository.findById(createdClient.getId());
        assertTrue(clientFromJpa.isPresent(), "El cliente debe ser encontrado por JPA Repository");
        assertEquals(createdClient.getId(), clientFromJpa.get().getId(), "Los IDs deben coincidir");
        
        Optional<ClientEntity> clientEntityFromSpringData = springDataClientRepository.findById(createdClient.getId());
        assertTrue(clientEntityFromSpringData.isPresent(), "El cliente debe ser encontrado por Spring Data Repository");
        assertEquals(createdClient.getId(), clientEntityFromSpringData.get().getId(), "Los IDs deben coincidir");
    }

    @Test
    @DisplayName("Debería manejar correctamente los errores de validación")
    void shouldHandleValidationErrorsCorrectly() {
        ClientDTO duplicateClient = new ClientDTO();
        duplicateClient.setName("Cliente Duplicado");
        duplicateClient.setGender("MALE");
        duplicateClient.setAge(30);
        duplicateClient.setIdentification(testIdentification);
        duplicateClient.setAddress("Dirección Duplicada");
        duplicateClient.setPhone("3001111111");
        duplicateClient.setPassword("password456");
        
        ClientDTO firstClient = clientService.create(testClientDTO);
        assertNotNull(firstClient, "El primer cliente debe crearse correctamente");
        
        try {
            clientService.create(duplicateClient);
            fail("Debería haber lanzado una excepción por identificación duplicada");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("identification"), "El error debe mencionar la identificación");
        }
    }
}
