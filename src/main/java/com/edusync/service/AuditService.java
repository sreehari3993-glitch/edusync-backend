package com.edusync.service;

import com.edusync.model.AuditLog;
import com.edusync.model.User;
import com.edusync.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public void log(String action, String actorEmail, String actorName, User.Role actorRole, String targetEntity, String targetId, String details) {
        try {
            AuditLog log = AuditLog.builder()
                    .action(action)
                    .actorEmail(actorEmail != null ? actorEmail : "system")
                    .actorName(actorName != null ? actorName : "System")
                    .actorRole(actorRole)
                    .targetEntity(targetEntity)
                    .targetId(targetId)
                    .details(details)
                    .build();
            auditLogRepository.save(log);
        } catch (Exception e) {
            // Non-blocking: audit failure should not break the business action
            System.err.println("Failed to write audit log: " + e.getMessage());
        }
    }

    public List<AuditLog> getRecentLogs(int limit) {
        return auditLogRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, Math.min(limit, 100)));
    }
}
