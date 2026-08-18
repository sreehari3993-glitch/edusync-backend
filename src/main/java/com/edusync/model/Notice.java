package com.edusync.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Notice / Announcement entity.
 * Created by Admin/HOD/Principal; visible to all or specific roles.
 */
@Entity
@Table(name = "notices")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column
    @Builder.Default
    private Category category = Category.ACADEMIC;

    @Enumerated(EnumType.STRING)
    @Column
    @Builder.Default
    private Visibility visibility = Visibility.ENTIRE_COLLEGE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "is_active")
    @Builder.Default
    private boolean active = true;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum Category {
        ACADEMIC,
        ADMINISTRATIVE,
        PLACEMENT,
        EVENTS,
        URGENT
    }

    public enum Visibility {
        ENTIRE_COLLEGE,
        STUDENTS_ONLY,
        FACULTY_ONLY,
        DEPARTMENT_ONLY
    }
}
