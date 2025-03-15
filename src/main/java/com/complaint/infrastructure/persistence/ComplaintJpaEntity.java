package com.complaint.infrastructure.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "complaints",
    uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "reported_by"}))
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintJpaEntity {
    
    @Id
    private String id;
    
    @Column(name = "product_id", nullable = false)
    private String productId;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
    
    @Column(name = "reported_by", nullable = false)
    private String reportedBy;
    
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;
    
    @Column(nullable = false)
    private int counter;
} 