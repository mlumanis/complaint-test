package com.complaint.application.service;

import com.complaint.application.port.in.CreateComplaintUseCase;
import com.complaint.application.port.in.GetComplaintUseCase;
import com.complaint.application.port.in.UpdateComplaintUseCase;
import com.complaint.domain.model.Complaint;
import com.complaint.domain.model.vo.ComplaintContent;
import com.complaint.domain.model.vo.IpAddress;
import com.complaint.domain.model.vo.ProductId;
import com.complaint.domain.model.vo.Reporter;
import com.complaint.domain.repository.ComplaintRepository;
import com.complaint.infrastructure.rest.ComplaintNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ComplaintService implements CreateComplaintUseCase, UpdateComplaintUseCase, GetComplaintUseCase {
    
    private final ComplaintRepository complaintRepository;

    public ComplaintService(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }

    @Override
    public Complaint createComplaint(CreateComplaintCommand command) {
        ProductId productId = ProductId.of(command.productId());
        ComplaintContent content = ComplaintContent.of(command.content());
        Reporter reporter = Reporter.of(command.reportedBy());
        IpAddress ipAddress = IpAddress.of(command.ipAddress());

        return complaintRepository.findByProductIdAndReporter(productId, reporter)
                .map(complaint -> {
                    complaint.incrementCounter();
                    return complaintRepository.save(complaint);
                })
                .orElseGet(() -> {
                    Complaint newComplaint = Complaint.create(
                            UUID.randomUUID().toString(),
                            productId,
                            content,
                            reporter,
                            ipAddress
                    );
                    return complaintRepository.save(newComplaint);
                });
    }

    @Override
    public Complaint updateComplaint(UpdateComplaintCommand command) {
        return complaintRepository.findById(command.complaintId())
                .map(complaint -> {
                    complaint.updateContent(ComplaintContent.of(command.content()));
                    return complaintRepository.save(complaint);
                })
                .orElseThrow(() -> new ComplaintNotFoundException("Complaint not found: " + command.complaintId()));
    }

    @Override
    public Complaint getComplaintById(String complaintId) {
        return complaintRepository.findById(complaintId)
                .orElseThrow(() -> new ComplaintNotFoundException("Complaint not found: " + complaintId));
    }

    @Override
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }
} 