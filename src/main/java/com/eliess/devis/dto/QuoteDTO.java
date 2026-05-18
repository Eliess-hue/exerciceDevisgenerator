package com.eliess.devis.dto;

public record QuoteDTO(
    Long id,
    String clientName,
    String status,
    java.time.LocalDateTime createdAt
) {
    
}