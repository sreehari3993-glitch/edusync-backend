package com.edusync.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Student application to a Placement Drive.
 */
@Entity
@Table(
    name = "placement_applications",
    uniqueConstraints = @UniqueConstraint(
        columnNames = {"student_id", "drive_id"}
    )
)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlacementApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drive_id", nullable = false)
    private PlacementDrive drive;

    @Column(name = "resume_path")
    private String resumePath;

    @Column
    private Double cgpa;

    @Enumerated(EnumType.STRING)
    @Column
    @Builder.Default
    private ApplicationStatus status = ApplicationStatus.APPLIED;

    @Column(name = "ai_score")   // AI resume screening score (0–100)
    private Integer aiScore;

    @Column(name = "applied_at")
    @Builder.Default
    private LocalDateTime appliedAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    public enum ApplicationStatus {
        APPLIED,
        SHORTLISTED,
        SELECTED,
        REJECTED
    }
}
