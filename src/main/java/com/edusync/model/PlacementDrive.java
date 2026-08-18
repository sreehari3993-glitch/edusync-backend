package com.edusync.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Placement Drive entity.
 * Managed by Placement Officer; students apply via PlacementApplication.
 */
@Entity
@Table(name = "placement_drives")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlacementDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column
    private String logo;               // emoji or file path

    @Column(nullable = false)
    private String role;               // e.g. "Software Engineer"

    @Column
    private String description;

    @Column(name = "package_lpa")
    private Double packageLpa;         // in LPA

    @Column(name = "drive_date")
    private LocalDate driveDate;

    @Column(name = "last_date_to_apply")
    private LocalDate lastDateToApply;

    @Column(name = "min_cgpa")
    private Double minCgpa;

    @Column(name = "eligible_branches")
    private String eligibleBranches;   // comma-separated: "CSE,ECE"

    @Column(name = "eligible_batch")
    private String eligibleBatch;      // e.g. "2025"

    @Column(name = "job_type")
    private String jobType;            // Full-time, Internship

    @Column
    private String location;

    @Enumerated(EnumType.STRING)
    @Column
    @Builder.Default
    private DriveStatus status = DriveStatus.UPCOMING;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum DriveStatus {
        UPCOMING,
        ONGOING,
        COMPLETED,
        CANCELLED
    }
}
