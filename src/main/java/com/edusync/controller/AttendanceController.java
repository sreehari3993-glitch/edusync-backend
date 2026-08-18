package com.edusync.controller;

import com.edusync.dto.AppDto;
import com.edusync.model.Attendance;
import com.edusync.security.JwtUtil;
import com.edusync.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Attendance REST controller.
 *
 * POST /api/attendance/mark              — faculty marks attendance
 * GET  /api/attendance/my/summary        — student gets % summary per subject
 * GET  /api/attendance/my/records        — student gets raw records
 * GET  /api/attendance/subject/{id}?date — faculty views a class's attendance
 */
@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
@CrossOrigin
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final JwtUtil jwtUtil;

    @PostMapping("/mark")
    @PreAuthorize("hasAnyRole('FACULTY','HOD','ADMIN')")
    public ResponseEntity<AppDto.ApiResponse<Attendance>> markAttendance(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody AppDto.AttendanceMark dto
    ) {
        Long facultyId = extractUserId(authHeader);
        Attendance att = attendanceService.markAttendance(facultyId, dto);
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Attendance marked", att));
    }

    @GetMapping("/my/summary")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<AppDto.ApiResponse<List<AppDto.AttendanceSummary>>> getSummary(
            @RequestHeader("Authorization") String authHeader
    ) {
        Long studentId = extractUserId(authHeader);
        List<AppDto.AttendanceSummary> summary = attendanceService.getStudentSummary(studentId);
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Attendance summary", summary));
    }

    @GetMapping("/my/records")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<AppDto.ApiResponse<List<Attendance>>> getRecords(
            @RequestHeader("Authorization") String authHeader
    ) {
        Long studentId = extractUserId(authHeader);
        List<Attendance> records = attendanceService.getStudentAttendance(studentId);
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Attendance records", records));
    }

    @GetMapping("/subject/{subjectId}")
    @PreAuthorize("hasAnyRole('FACULTY','HOD','ADMIN')")
    public ResponseEntity<AppDto.ApiResponse<List<Attendance>>> getBySubjectDate(
            @PathVariable Long subjectId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<Attendance> list = attendanceService.getAttendanceBySubjectAndDate(subjectId, date);
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Attendance for subject on " + date, list));
    }

    private Long extractUserId(String authHeader) {
        return jwtUtil.extractUserId(authHeader.substring(7));
    }
}
