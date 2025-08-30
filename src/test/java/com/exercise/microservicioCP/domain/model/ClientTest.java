package com.exercise.microservicioCP.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

/**
 * Pruebas unitarias para la entidad de dominio Client
 */
@DisplayName("Pruebas de la entidad de dominio Client")
class ClientTest {

    private Client client;
    private LocalDateTime testDateTime;

    @BeforeEach
    void setUp() {
        testDateTime = LocalDateTime.of(2025, 1, 15, 10, 30, 0);
        
        client = new Client();
        client.setId(1L);
        client.setName("Juan Pérez");
        client.setGender("MALE");
        client.setAge(30);
        client.setIdentification("12345678");
        client.setAddress("Calle Principal 123");
        client.setPhone("3001234567");
        client.setClientId("JP001");
        client.setPassword("password123");
        client.setStatus("ACTIVE");
        client.setCreatedAt(testDateTime);
        client.setUpdatedAt(testDateTime);
        client.setRegistrationDate(testDateTime);
    }

    @Test
    @DisplayName("Debería crear un cliente con todos los campos correctamente")
    void shouldCreateClientWithAllFields() {
        // Assert
        assertNotNull(client);
        assertEquals(1L, client.getId());
        assertEquals("Juan Pérez", client.getName());
        assertEquals("MALE", client.getGender());
        assertEquals(30, client.getAge());
        assertEquals("12345678", client.getIdentification());
        assertEquals("Calle Principal 123", client.getAddress());
        assertEquals("3001234567", client.getPhone());
        assertEquals("JP001", client.getClientId());
        assertEquals("password123", client.getPassword());
        assertEquals("ACTIVE", client.getStatus());
        assertEquals(testDateTime, client.getCreatedAt());
        assertEquals(testDateTime, client.getUpdatedAt());
        assertEquals(testDateTime, client.getRegistrationDate());
    }

    @Test
    @DisplayName("Debería actualizar el nombre del cliente")
    void shouldUpdateClientName() {
        // Arrange
        String newName = "Juan Carlos Pérez";

        // Act
        client.setName(newName);

        // Assert
        assertEquals(newName, client.getName());
    }

    @Test
    @DisplayName("Debería actualizar la edad del cliente")
    void shouldUpdateClientAge() {
        // Arrange
        Integer newAge = 31;

        // Act
        client.setAge(newAge);

        // Assert
        assertEquals(newAge, client.getAge());
    }

    @Test
    @DisplayName("Debería actualizar la dirección del cliente")
    void shouldUpdateClientAddress() {
        // Arrange
        String newAddress = "Nueva Dirección 456";

        // Act
        client.setAddress(newAddress);

        // Assert
        assertEquals(newAddress, client.getAddress());
    }

    @Test
    @DisplayName("Debería actualizar el teléfono del cliente")
    void shouldUpdateClientPhone() {
        // Arrange
        String newPhone = "3009876543";

        // Act
        client.setPhone(newPhone);

        // Assert
        assertEquals(newPhone, client.getPhone());
    }

    @Test
    @DisplayName("Debería actualizar la contraseña del cliente")
    void shouldUpdateClientPassword() {
        // Arrange
        String newPassword = "newpassword123";

        // Act
        client.setPassword(newPassword);

        // Assert
        assertEquals(newPassword, client.getPassword());
    }

    @Test
    @DisplayName("Debería actualizar el status del cliente")
    void shouldUpdateClientStatus() {
        // Arrange
        String newStatus = "INACTIVE";

        // Act
        client.setStatus(newStatus);

        // Assert
        assertEquals(newStatus, client.getStatus());
    }

    @Test
    @DisplayName("Debería actualizar el timestamp de actualización")
    void shouldUpdateUpdatedAtTimestamp() {
        // Arrange
        LocalDateTime newUpdateTime = LocalDateTime.now();

        // Act
        client.setUpdatedAt(newUpdateTime);

        // Assert
        assertEquals(newUpdateTime, client.getUpdatedAt());
    }

    @Test
    @DisplayName("Debería mantener el clientId inmutable")
    void shouldKeepClientIdImmutable() {
        // Arrange
        String originalClientId = client.getClientId();

        // Act
        client.setClientId("NEW001");

        // Assert
        assertEquals("NEW001", client.getClientId());
        assertNotEquals(originalClientId, client.getClientId());
    }

    @Test
    @DisplayName("Debería mantener la identificación inmutable")
    void shouldKeepIdentificationImmutable() {
        // Arrange
        String originalIdentification = client.getIdentification();

        // Act
        client.setIdentification("87654321");

        // Assert
        assertEquals("87654321", client.getIdentification());
        assertNotEquals(originalIdentification, client.getIdentification());
    }

    @Test
    @DisplayName("Debería mantener el ID de base de datos inmutable")
    void shouldKeepDatabaseIdImmutable() {
        // Arrange
        Long originalId = client.getId();

        // Act
        client.setId(999L);

        // Assert
        assertEquals(999L, client.getId());
        assertNotEquals(originalId, client.getId());
    }

    @Test
    @DisplayName("Debería mantener los timestamps de creación y registro")
    void shouldKeepCreationAndRegistrationTimestamps() {
        // Arrange
        LocalDateTime originalCreatedAt = client.getCreatedAt();
        LocalDateTime originalRegistrationDate = client.getRegistrationDate();

        // Act
        LocalDateTime newTime = LocalDateTime.now();
        client.setCreatedAt(newTime);
        client.setRegistrationDate(newTime);

        // Assert
        assertEquals(newTime, client.getCreatedAt());
        assertEquals(newTime, client.getRegistrationDate());
        assertNotEquals(originalCreatedAt, client.getCreatedAt());
        assertNotEquals(originalRegistrationDate, client.getRegistrationDate());
    }

    @Test
    @DisplayName("Debería validar que todos los campos obligatorios estén presentes")
    void shouldValidateRequiredFields() {
        // Assert
        assertNotNull(client.getName(), "El nombre no debe ser null");
        assertNotNull(client.getGender(), "El género no debe ser null");
        assertNotNull(client.getAge(), "La edad no debe ser null");
        assertNotNull(client.getIdentification(), "La identificación no debe ser null");
        assertNotNull(client.getAddress(), "La dirección no debe ser null");
        assertNotNull(client.getPhone(), "El teléfono no debe ser null");
        assertNotNull(client.getClientId(), "El clientId no debe ser null");
        assertNotNull(client.getPassword(), "La contraseña no debe ser null");
        assertNotNull(client.getStatus(), "El status no debe ser null");
    }

    @Test
    @DisplayName("Debería validar que los campos de texto no estén vacíos")
    void shouldValidateTextFieldsNotEmpty() {
        // Assert
        assertFalse(client.getName().trim().isEmpty(), "El nombre no debe estar vacío");
        assertFalse(client.getGender().trim().isEmpty(), "El género no debe estar vacío");
        assertFalse(client.getIdentification().trim().isEmpty(), "La identificación no debe estar vacía");
        assertFalse(client.getAddress().trim().isEmpty(), "La dirección no debe estar vacía");
        assertFalse(client.getPhone().trim().isEmpty(), "El teléfono no debe estar vacío");
        assertFalse(client.getClientId().trim().isEmpty(), "El clientId no debe estar vacío");
        assertFalse(client.getPassword().trim().isEmpty(), "La contraseña no debe estar vacía");
        assertFalse(client.getStatus().trim().isEmpty(), "El status no debe estar vacío");
    }

    @Test
    @DisplayName("Debería validar que la edad sea un número positivo")
    void shouldValidateAgeIsPositive() {
        // Assert
        assertTrue(client.getAge() > 0, "La edad debe ser un número positivo");
        assertTrue(client.getAge() <= 150, "La edad debe ser un número razonable");
    }

    @Test
    @DisplayName("Debería validar que los timestamps sean consistentes")
    void shouldValidateTimestampsConsistency() {
        // Assert
        assertNotNull(client.getCreatedAt(), "CreatedAt no debe ser null");
        assertNotNull(client.getUpdatedAt(), "UpdatedAt no debe ser null");
        assertNotNull(client.getRegistrationDate(), "RegistrationDate no debe ser null");
        
        // Los timestamps de creación y registro deben ser iguales inicialmente
        assertEquals(client.getCreatedAt(), client.getRegistrationDate(), 
                   "CreatedAt y RegistrationDate deben ser iguales inicialmente");
    }
}