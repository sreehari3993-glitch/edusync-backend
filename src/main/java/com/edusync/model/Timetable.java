package com.edusync.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

/**
 * Timetable slot for a department/semester/section.
 */
@Entity
@Table(name = "timetable")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String semester;

    @Column
    private String section;

    @Column(name = "day_of_week", nullable = false)
    private String dayOfWeek;     // MON, TUE, WED, THU, FRI

    @Column(name = "period_number", nullable = false)
    private Integer periodNumber; // 1–7

    @Column(name = "start_time")
    private String startTime;     // e.g. "09:00"

    @Column(name = "end_time")
    private String endTime;       // e.g. "09:50"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "faculty_id")
    private User faculty;

    @Column
    private String room;          // e.g. "CS Lab 1", "Room 203"

    @Column(name = "is_break")
    @Builder.Default
    private boolean isBreak = false; // lunch / short break slot
}
