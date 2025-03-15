package com.complaint.infrastructure.rest.dto;

import jakarta.validation.constraints.*;

public record CreateComplaintRequest(
        @NotBlank(message = "Product ID cannot be blank")
        @Pattern(regexp = "^PROD\\d{3,6}$", message = "Product ID must start with 'PROD' followed by 3-6 digits")
        String productId,

        @NotBlank(message = "Content cannot be blank")
        @Size(min = 10, max = 1000, message = "Content must be between 10 and 1000 characters")
        String content,

        @NotBlank(message = "Reporter email cannot be blank")
        @Email(message = "Invalid email format")
        String reportedBy
) {} 