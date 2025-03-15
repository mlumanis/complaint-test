package com.complaint.application.port.in;

import com.complaint.domain.model.Complaint;

public interface UpdateComplaintUseCase {
    record UpdateComplaintCommand(
            String complaintId,
            String content
    ) {}

    Complaint updateComplaint(UpdateComplaintCommand command);
} 