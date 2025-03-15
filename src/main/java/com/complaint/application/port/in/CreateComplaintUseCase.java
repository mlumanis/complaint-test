package com.complaint.application.port.in;

import com.complaint.domain.model.Complaint;

public interface CreateComplaintUseCase {
    record CreateComplaintCommand(
            String productId,
            String content,
            String reportedBy,
            String ipAddress
    ) {}

    Complaint createComplaint(CreateComplaintCommand command);
} 