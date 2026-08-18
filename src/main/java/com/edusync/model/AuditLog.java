package com.edusync.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * System audit log for tracking administrative and user security events.
 */
@Entity
@Table(name = "audit_logs")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String action;          // e.g. "USER_LOGIN", "LEAVE_APPROVED", "LEAVE_REJECTED", "MARKS_SAVED", "NOTICE_PUBLISHED", "STUDENT_ADDED", "PASSWORD_CHANGED", "GRIEVANCE_RESOLVED"

    @Column(name = "actor_email", nullable = false)
    private String actorEmail;

    @Column(name = "actor_name")
    private String actorName;

    @Enumerated(EnumType.STRING)
    @Column(name = "actor_role")
    private User.Role actorRole;

    @Column(name = "target_entity")
    private String targetEntity;    // e.g. "LeaveRequest", "InternalMark", "Notice", "User", "Grievance"

    @Column(name = "target_id")
    private String targetId;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
