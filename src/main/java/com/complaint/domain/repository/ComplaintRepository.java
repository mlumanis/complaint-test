package com.complaint.domain.repository;

import com.complaint.domain.model.Complaint;
import com.complaint.domain.model.vo.ProductId;
import com.complaint.domain.model.vo.Reporter;

import java.util.List;
import java.util.Optional;

public interface ComplaintRepository {
    Complaint save(Complaint complaint);
    Optional<Complaint> findById(String id);
    Optional<Complaint> findByProductIdAndReporter(ProductId productId, Reporter reporter);
    List<Complaint> findAll();
} 