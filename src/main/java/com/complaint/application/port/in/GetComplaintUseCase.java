package com.complaint.application.port.in;

import com.complaint.domain.model.Complaint;

import java.util.List;

public interface GetComplaintUseCase {
    Complaint getComplaintById(String complaintId);
    List<Complaint> getAllComplaints();
} 