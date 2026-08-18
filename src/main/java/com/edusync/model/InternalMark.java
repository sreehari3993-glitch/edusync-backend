package com.edusync.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Internal marks entity for series exams, assignments, practicals, and grade totals.
 */
@Entity
@Table(name = "internal_marks")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternalMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @Column(name = "series_name", nullable = false)
    private String seriesName;       // e.g. "Series I", "Series II", "Model Exam"

    @Column(name = "test_marks")
    @Builder.Default
    private Double testMarks = 0.0;  // out of 30

    @Column(name = "assignment_marks")
    @Builder.Default
    private Double assignmentMarks = 0.0; // out of 10

    @Column(name = "practical_marks")
    @Builder.Default
    private Double practicalMarks = 0.0;  // out of 10

    @Column(name = "total_marks")
    @Builder.Default
    private Double totalMarks = 0.0; // out of 50

    @Column(name = "max_marks")
    @Builder.Default
    private Double maxMarks = 50.0;

    @Column
    private String grade;            // "A+", "A", "B+", "B", "C", "F"

    @Column
    private String semester;

    @Column
    private String department;

    @Column(name = "entered_by")
    private String enteredBy;

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
}
