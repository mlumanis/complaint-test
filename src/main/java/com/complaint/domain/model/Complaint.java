package com.complaint.domain.model;

import com.complaint.domain.model.vo.ComplaintContent;
import com.complaint.domain.model.vo.ComplaintCounter;
import com.complaint.domain.model.vo.IpAddress;
import com.complaint.domain.model.vo.ProductId;
import com.complaint.domain.model.vo.Reporter;
import java.time.LocalDateTime;
import java.util.Objects;

public class Complaint {
    private final String id;
    private final ProductId productId;
    private ComplaintContent content;
    private final Reporter reportedBy;
    private final IpAddress ipAddress;
    private ComplaintCounter counter;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Complaint(String id, ProductId productId, ComplaintContent content, Reporter reportedBy, 
                     IpAddress ipAddress, ComplaintCounter counter, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.productId = productId;
        this.content = content;
        this.reportedBy = reportedBy;
        this.ipAddress = ipAddress;
        this.counter = counter;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Complaint create(String id, ProductId productId, ComplaintContent content, 
                                 Reporter reportedBy, IpAddress ipAddress) {
        LocalDateTime now = LocalDateTime.now();
        return new Complaint(id, productId, content, reportedBy, ipAddress, ComplaintCounter.initial(), now, now);
    }

    public void updateContent(ComplaintContent newContent) {
        this.content = newContent;
        this.updatedAt = LocalDateTime.now();
    }

    public void incrementCounter() {
        this.counter = counter.increment();
        this.updatedAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public ProductId getProductId() {
        return productId;
    }

    public ComplaintContent getContent() {
        return content;
    }

    public Reporter getReportedBy() {
        return reportedBy;
    }

    public IpAddress getIpAddress() {
        return ipAddress;
    }

    public ComplaintCounter getCounter() {
        return counter;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complaint complaint = (Complaint) o;
        return Objects.equals(id, complaint.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
} 