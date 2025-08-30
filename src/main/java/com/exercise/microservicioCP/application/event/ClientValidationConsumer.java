package com.exercise.microservicioCP.application.event;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.exercise.microservicioCP.application.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ClientValidationConsumer {
    
    private final ClientService clientService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    
    @Autowired
    public ClientValidationConsumer(ClientService clientService, KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper objectMapper) {
        this.clientService = clientService;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;

    }
    
    @KafkaListener(topics = "client-validation-requests", groupId = "microservicioCP-group")
    public void handleClientValidationRequest(Map<String, Object> requestMap) {
        try {

            
            if (requestMap.containsKey("test-connection")) {
                Map<String, Object> response = new HashMap<>();
                response.put("test-connection", true);
                response.put("status", "ok");
                response.put("timestamp", System.currentTimeMillis());
                kafkaTemplate.send("client-validation-responses", "test-connection", response);
                return;
            }
            
            String requestId = (String) requestMap.get("requestId");
            String clientId = (String) requestMap.get("clientId");
            String accountNumber = (String) requestMap.get("accountNumber");
            

            
            boolean clientExists = clientService.existsByClientId(clientId);

            
            String clientName = "";
            String responseMessage = "";
            
            if (clientExists) {
                try {
                    var client = clientService.findByClientId(clientId);
                    clientName = client.getName();
                    responseMessage = "Client validated successfully";
                } catch (Exception e) {
                    log.error("Error getting client details: {}", e.getMessage(), e);
                    clientName = "Client";
                    responseMessage = "Client exists but error getting details: " + e.getMessage();
                }
            } else {
                responseMessage = "Client not found";
            }
            
            ClientValidationResponse response = new ClientValidationResponse(
                requestId,
                clientId,
                clientExists,
                clientName,
                responseMessage
            );
            
            kafkaTemplate.send("client-validation-responses", requestId, response);
            
        } catch (Exception e) {
            log.error("Error processing validation request: {}", e.getMessage(), e);
        }
    }
}
