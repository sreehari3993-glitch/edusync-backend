package com.edusync.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Marks entity for internal assessments and university results.
 */
@Entity
@Table(name = "marks")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Enumerated(EnumType.STRING)
    @Column(name = "exam_type", nullable = false)
    private ExamType examType;

    @Column(name = "marks_obtained")
    private Double marksObtained;

    @Column(name = "max_marks")
    private Double maxMarks;

    @Column
    private String grade;             // A+, A, B+, etc.

    @Column
    private String semester;

    @Column(name = "academic_year")
    private String academicYear;      // e.g. "2025-2026"

    @Column(name = "entered_by")
    private String enteredBy;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public enum ExamType {
        INTERNAL_1,
        INTERNAL_2,
        INTERNAL_3,
        MODEL_EXAM,
        UNIVERSITY_EXAM,
        ASSIGNMENT,
        LAB
    }
}
