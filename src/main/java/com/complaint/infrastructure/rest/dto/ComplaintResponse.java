package com.complaint.infrastructure.rest.dto;

import java.time.LocalDateTime;

public record ComplaintResponse(
        String id,
        String productId,
        String content,
        LocalDateTime createdAt,
        String reportedBy,
        String ipAddress,
        int counter
) {} 