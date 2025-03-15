package com.complaint.infrastructure.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateComplaintRequest(
        @NotBlank(message = "Content cannot be blank")
        @Size(min = 10, max = 1000, message = "Content must be between 10 and 1000 characters")
        String content
) {} 