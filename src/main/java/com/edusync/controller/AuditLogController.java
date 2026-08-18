package com.edusync.controller;

import com.edusync.dto.AppDto;
import com.edusync.model.AuditLog;
import com.edusync.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for Admin Audit Logs.
 */
@RestController
@RequestMapping("/api/admin/audit-logs")
@RequiredArgsConstructor
@CrossOrigin
public class AuditLogController {

    private final AuditService auditService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AppDto.ApiResponse<List<AuditLog>>> getRecentAuditLogs(
            @RequestParam(defaultValue = "50") int limit
    ) {
        List<AuditLog> logs = auditService.getRecentLogs(limit);
        return ResponseEntity.ok(AppDto.ApiResponse.ok("Audit logs fetched", logs));
    }
}
