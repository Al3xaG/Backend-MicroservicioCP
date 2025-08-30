package com.exercise.microservicioCP.application.event;

import java.time.LocalDateTime;

public class ClientValidationRequest {
    
    private String requestId;
    private Long clientId;
    private String accountNumber;
    private LocalDateTime timestamp;
    
    public ClientValidationRequest() {}
    
    public ClientValidationRequest(String requestId, Long clientId, String accountNumber) {
        this.requestId = requestId;
        this.clientId = clientId;
        this.accountNumber = accountNumber;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getRequestId() { return requestId; }
    public void setRequestId(String requestId) { this.requestId = requestId; }
    
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
