package com.edusync.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

/**
 * Subject / Course entity.
 * Linked to department, semester, and assigned faculty.
 */
@Entity
@Table(name = "subjects")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String code;          // e.g. CS401

    @Column
    private String department;    // CSE, ECE, MECH, FT

    @Column
    private String semester;      // S1, S2, … S8

    @Column
    private Integer credits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private User faculty;         // assigned faculty

    @Column(name = "is_active")
    @Builder.Default
    private boolean active = true;
}
