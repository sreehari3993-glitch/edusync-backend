package com.edusync.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * LeaveRequest entity.
 * Covers: Duty Leave, Medical Leave, OD Letter, Emergency Leave.
 * Approval flow: Student → Faculty Advisor → HOD → Principal (if needed)
 */
@Entity
@Table(name = "leave_requests")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeaveRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Who submitted ---
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    // --- Leave details ---
    @Enumerated(EnumType.STRING)
    @Column(name = "leave_type", nullable = false)
    private LeaveType leaveType;

    @Column(nullable = false)
    private String reason;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    private LocalDate toDate;

    @Column(name = "no_of_days")
    private int noOfDays;

    @Column(name = "event_name")      // for OD / Duty Leave
    private String eventName;

    @Column(name = "attachment_path") // file upload path
    private String attachmentPath;

    // --- Approval pipeline ---
    @Enumerated(EnumType.STRING)
    @Column(name = "overall_status")
    @Builder.Default
    private Status overallStatus = Status.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "faculty_status")
    @Builder.Default
    private Status facultyStatus = Status.PENDING;

    @Column(name = "faculty_remark")
    private String facultyRemark;

    @Column(name = "faculty_approved_by")
    private String facultyApprovedBy;

    @Column(name = "faculty_approved_at")
    private LocalDateTime facultyApprovedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "hod_status")
    @Builder.Default
    private Status hodStatus = Status.PENDING;

    @Column(name = "hod_remark")
    private String hodRemark;

    @Column(name = "hod_approved_by")
    private String hodApprovedBy;

    @Column(name = "hod_approved_at")
    private LocalDateTime hodApprovedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "principal_status")
    @Builder.Default
    private Status principalStatus = Status.NOT_REQUIRED;

    @Column(name = "principal_remark")
    private String principalRemark;

    @Column(name = "principal_approved_at")
    private LocalDateTime principalApprovedAt;

    // --- Tracking ---
    @Column(name = "current_stage")
    @Builder.Default
    private String currentStage = "FACULTY_REVIEW";

    @Column(name = "pdf_path")         // generated OD/leave letter
    private String pdfPath;

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

    public enum LeaveType {
        DUTY_LEAVE,
        MEDICAL_LEAVE,
        OD_LETTER,
        EMERGENCY_LEAVE,
        CASUAL_LEAVE
    }

    public enum Status {
        PENDING,
        APPROVED,
        REJECTED,
        NOT_REQUIRED
    }
}
