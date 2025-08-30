package com.exercise.microservicioCP.application.event;

import java.time.LocalDateTime;

public class ClientValidationResponse {
    
    private String requestId;
    private String clientId;
    private Boolean clientExists;
    private String clientName;
    private String message;
    private LocalDateTime timestamp;
    
    public ClientValidationResponse() {}
    
    public ClientValidationResponse(String requestId, String clientId, Boolean clientExists, String clientName, String message) {
        this.requestId = requestId;
        this.clientId = clientId;
        this.clientExists = clientExists;
        this.clientName = clientName;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
    

    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }
    
    public String getClientId() { return clientId; }
    public void setClientId(String clientId) { this.clientId = clientId; }
    
    public Boolean getClientExists() { return clientExists; }
    public void setClientExists(Boolean clientExists) { this.clientExists = clientExists; }
    
    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
