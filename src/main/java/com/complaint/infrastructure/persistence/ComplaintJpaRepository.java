package com.complaint.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface ComplaintJpaRepository extends JpaRepository<ComplaintJpaEntity, String> {
    Optional<ComplaintJpaEntity> findByProductIdAndReportedBy(String productId, String reportedBy);
} 