package com.complaint.infrastructure.rest;

import com.complaint.application.port.in.CreateComplaintUseCase;
import com.complaint.application.port.in.GetComplaintUseCase;
import com.complaint.application.port.in.UpdateComplaintUseCase;
import com.complaint.domain.model.Complaint;
import com.complaint.infrastructure.rest.dto.ComplaintResponse;
import com.complaint.infrastructure.rest.dto.CreateComplaintRequest;
import com.complaint.infrastructure.rest.dto.UpdateComplaintRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@Validated
class ComplaintController {

    private final CreateComplaintUseCase createComplaintUseCase;
    private final UpdateComplaintUseCase updateComplaintUseCase;
    private final GetComplaintUseCase getComplaintUseCase;

    ComplaintController(
            CreateComplaintUseCase createComplaintUseCase,
            UpdateComplaintUseCase updateComplaintUseCase,
            GetComplaintUseCase getComplaintUseCase
    ) {
        this.createComplaintUseCase = createComplaintUseCase;
        this.updateComplaintUseCase = updateComplaintUseCase;
        this.getComplaintUseCase = getComplaintUseCase;
    }

    @PostMapping
    ResponseEntity<ComplaintResponse> createComplaint(
            @Valid @RequestBody CreateComplaintRequest request,
            HttpServletRequest httpRequest
    ) {
        String ipAddress = httpRequest.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty()) {
            ipAddress = httpRequest.getRemoteAddr();
        }

        Complaint complaint = createComplaintUseCase.createComplaint(
                new CreateComplaintUseCase.CreateComplaintCommand(
                        request.productId(),
                        request.content(),
                        request.reportedBy(),
                        ipAddress
                )
        );

        return ResponseEntity.ok(toResponse(complaint));
    }

    @PutMapping("/{id}")
    ResponseEntity<ComplaintResponse> updateComplaint(
            @PathVariable @NotBlank(message = "Complaint ID cannot be blank") String id,
            @Valid @RequestBody UpdateComplaintRequest request
    ) {
        Complaint complaint = updateComplaintUseCase.updateComplaint(
                new UpdateComplaintUseCase.UpdateComplaintCommand(
                        id,
                        request.content()
                )
        );

        return ResponseEntity.ok(toResponse(complaint));
    }

    @GetMapping("/{id}")
    ResponseEntity<ComplaintResponse> getComplaint(
            @PathVariable @NotBlank(message = "Complaint ID cannot be blank") String id
    ) {
        Complaint complaint = getComplaintUseCase.getComplaintById(id);
        return ResponseEntity.ok(toResponse(complaint));
    }

    @GetMapping
    ResponseEntity<List<ComplaintResponse>> getAllComplaints() {
        List<Complaint> complaints = getComplaintUseCase.getAllComplaints();
        return ResponseEntity.ok(
                complaints.stream()
                        .map(this::toResponse)
                        .toList()
        );
    }

    private ComplaintResponse toResponse(Complaint complaint) {
        return new ComplaintResponse(
                complaint.getId(),
                complaint.getProductId().value(),
                complaint.getContent().value(),
                complaint.getCreatedAt(),
                complaint.getReportedBy().value(),
                complaint.getIpAddress().value(),
                complaint.getCounter().value()
        );
    }
} 