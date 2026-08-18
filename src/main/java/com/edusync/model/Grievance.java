package com.edusync.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Grievance & Feedback entity.
 * Supports confidential submission to HOD or Principal.
 */
@Entity
@Table(name = "grievances")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Grievance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private String subject;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_role", nullable = false)
    private TargetRole targetRole; // HOD, PRINCIPAL

    @Column
    private String department;     // e.g. "CSE"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private Status status = Status.OPEN;

    @Column(name = "is_anonymous")
    @Builder.Default
    private boolean anonymous = false;

    @Column(columnDefinition = "TEXT")
    private String response;

    @Column(name = "responded_by")
    private String respondedBy;

    @Column(name = "responded_at")
    private LocalDateTime respondedAt;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public enum Category {
        ACADEMIC,
        HOSTEL,
        INFRASTRUCTURE,
        EXAMINATION,
        HARASSMENT,
        OTHER
    }

    public enum TargetRole {
        HOD,
        PRINCIPAL
    }

    public enum Status {
        OPEN,
        UNDER_REVIEW,
        RESOLVED,
        DISMISSED
    }
}
