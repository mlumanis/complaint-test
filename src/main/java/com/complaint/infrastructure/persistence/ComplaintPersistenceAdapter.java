package com.complaint.infrastructure.persistence;

import com.complaint.domain.model.Complaint;
import com.complaint.domain.model.vo.*;
import com.complaint.domain.repository.ComplaintRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
class ComplaintPersistenceAdapter implements ComplaintRepository {
    
    private final ComplaintJpaRepository jpaRepository;

    ComplaintPersistenceAdapter(ComplaintJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Complaint save(Complaint complaint) {
        ComplaintJpaEntity entity = toJpaEntity(complaint);
        ComplaintJpaEntity savedEntity = jpaRepository.save(entity);
        return toDomainEntity(savedEntity);
    }

    @Override
    public Optional<Complaint> findById(String id) {
        return jpaRepository.findById(id)
                .map(this::toDomainEntity);
    }

    @Override
    public Optional<Complaint> findByProductIdAndReporter(ProductId productId, Reporter reporter) {
        return jpaRepository.findByProductIdAndReportedBy(productId.value(), reporter.value())
                .map(this::toDomainEntity);
    }

    @Override
    public List<Complaint> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomainEntity)
                .collect(Collectors.toList());
    }

    private ComplaintJpaEntity toJpaEntity(Complaint complaint) {
        return ComplaintJpaEntity.builder()
                .id(complaint.getId())
                .productId(complaint.getProductId().value())
                .content(complaint.getContent().value())
                .createdAt(complaint.getCreatedAt())
                .updatedAt(complaint.getUpdatedAt())
                .reportedBy(complaint.getReportedBy().value())
                .ipAddress(complaint.getIpAddress().value())
                .counter(complaint.getCounter().value())
                .build();
    }

    private Complaint toDomainEntity(ComplaintJpaEntity entity) {
        return new Complaint(
                entity.getId(),
                ProductId.of(entity.getProductId()),
                ComplaintContent.of(entity.getContent()),
                Reporter.of(entity.getReportedBy()),
                IpAddress.of(entity.getIpAddress()),
                ComplaintCounter.of(entity.getCounter()),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
} 