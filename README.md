# EduSync â€” Spring Boot Backend
### TKM Institute of Technology, Kollam, Kerala

---

## ðŸ—‚ï¸ Project Structure

```
edusync-backend/
â”‚
â”œâ”€â”€ pom.xml                          â† Maven dependencies
â”œâ”€â”€ frontend-connected.html          â† Your HTML + API client JS
â”‚
â””â”€â”€ src/main/
    â”œâ”€â”€ java/com/edusync/
    â”‚   â”œâ”€â”€ EduSyncApplication.java  â† Main (run this!)
    â”‚   â”‚
    â”‚   â”œâ”€â”€ model/                   â† Database entities
    â”‚   â”‚   â”œâ”€â”€ User.java
    â”‚   â”‚   â”œâ”€â”€ LeaveRequest.java
    â”‚   â”‚   â”œâ”€â”€ Attendance.java
    â”‚   â”‚   â”œâ”€â”€ Subject.java
    â”‚   â”‚   â”œâ”€â”€ Notice.java
    â”‚   â”‚   â”œâ”€â”€ PlacementDrive.java
    â”‚   â”‚   â”œâ”€â”€ PlacementApplication.java
    â”‚   â”‚   â”œâ”€â”€ Mark.java
    â”‚   â”‚   â””â”€â”€ Timetable.java
    â”‚   â”‚
    â”‚   â”œâ”€â”€ repository/              â† JPA database queries
    â”‚   â”œâ”€â”€ service/                 â† Business logic
    â”‚   â”œâ”€â”€ controller/              â† REST API endpoints
    â”‚   â”œâ”€â”€ dto/                     â† Request/Response objects
    â”‚   â”œâ”€â”€ security/                â† JWT + Spring Security
    â”‚   â””â”€â”€ config/                  â† Security config, CORS, error handling
    â”‚
    â””â”€â”€ resources/
        â”œâ”€â”€ application.properties   â† MySQL + JWT config
        â””â”€â”€ schema-seed.sql          â† Database schema + sample data
```

---

## âš™ï¸ STEP 1 â€” Install Prerequisites

Download and install these (all free):

| Tool | Download | Purpose |
|------|----------|---------|
| **IntelliJ IDEA Community** | https://www.jetbrains.com/idea/download | IDE to write & run Java |
| **Java 17 JDK** | https://adoptium.net | Java runtime |
| **MySQL Community Server 8.0** | https://dev.mysql.com/downloads/mysql | Database server |
| **MySQL Workbench** | https://dev.mysql.com/downloads/workbench | Database GUI |

---

## ðŸ—„ï¸ STEP 2 â€” Create the Database

1. Open **MySQL Workbench**
2. Connect with:
   - Host: `localhost`
   - Port: `3306`
   - Username: `root`
   - Password: `sreehari_99`
3. Click **File â†’ Open SQL Script**
4. Select `src/main/resources/schema-seed.sql`
5. Click the âš¡ **Execute All** button
6. You should see tables created + sample data loaded

âœ… Verify: Run `SELECT * FROM users;` â€” you should see 12+ rows.

---

## ðŸš€ STEP 3 â€” Run the Backend in IntelliJ

1. Open IntelliJ IDEA
2. **File â†’ Open** â†’ select the `edusync-backend` folder
3. Wait for Maven to download dependencies (~2 min first time)
4. Open `src/main/java/com/edusync/EduSyncApplication.java`
5. Click the â–¶ï¸ **Run** button (green play icon) next to `main()`
6. You should see:

```
â•”â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•—
â•‘    EduSync Backend is RUNNING âœ…      â•‘
â•‘    http://localhost:8081              â•‘
â•‘    TKMIT College Digital Platform     â•‘
â•šâ•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•â•
```

---

## ðŸŒ STEP 4 â€” Open the Frontend

Open `http://localhost:8081/` in your browser after starting Spring Boot.
Spring Boot serves the HTML configured by `edusync.frontend.html-path`.

You can also open `C:\Users\sreeh\Downloads\college-platform-connected.html` directly.
The page automatically tries to connect to `http://localhost:8081`.

---

## ðŸ” Login Credentials (Seed Data)

| Role | Email | Password |
|------|-------|----------|
| Admin | admin@tkmit.ac.in | admin123 |
| Principal | principal@tkmit.ac.in | principal123 |
| HOD (CSE) | hod.cse@tkmit.ac.in | faculty123 |
| Faculty | meera.nair@tkmit.ac.in | faculty123 |
| Placement Officer | placement@tkmit.ac.in | faculty123 |
| Student (Anzal) | anzal.r@student.tkmit.ac.in | student123 |
| Student (Hajira) | hajira.f@student.tkmit.ac.in | student123 |
| Student (Sreehari) | sreehari.d@student.tkmit.ac.in | student123 |

---

## ðŸ“¡ API Endpoints Reference

### Auth (no token needed)
```
POST /api/auth/register    â†’ create account
POST /api/auth/login       â†’ get JWT token
GET  /api/auth/ping        â†’ health check
```

### Student endpoints (STUDENT role)
```
POST /api/leaves/submit           â†’ submit leave/OD
GET  /api/leaves/my               â†’ my leave history
GET  /api/attendance/my/summary   â†’ attendance % per subject
GET  /api/attendance/my/records   â†’ raw attendance records
GET  /api/placement/drives/public â†’ view all placement drives
POST /api/placement/apply         â†’ apply to a drive
GET  /api/placement/my-applications â†’ my applications
GET  /api/users/profile           â†’ my profile
```

### Faculty endpoints
```
GET  /api/leaves/faculty/pending?department=CSE   â†’ pending leaves
PUT  /api/leaves/{id}/faculty-action              â†’ approve/reject
POST /api/attendance/mark                          â†’ mark attendance
GET  /api/attendance/subject/{id}?date=2025-03-15 â†’ view class attendance
```

### HOD endpoints
```
GET  /api/leaves/hod/pending?department=CSE       â†’ HOD queue
PUT  /api/leaves/{id}/hod-action                  â†’ approve/reject
POST /api/notices/create                           â†’ post notice
GET  /api/users/by-dept?dept=CSE                  â†’ view students
```

### Principal endpoints
```
GET  /api/leaves/principal/pending                 â†’ pending for principal
PUT  /api/leaves/{id}/principal-action             â†’ final approval
GET  /api/users/admin/stats                        â†’ dashboard numbers
```

### Admin endpoints
```
GET  /api/users/admin/all                         â†’ all users
PUT  /api/users/admin/{id}/toggle                 â†’ activate/deactivate
GET  /api/leaves/all                              â†’ all leave requests
DELETE /api/notices/{id}                          â†’ remove notice
```

### Placement Officer endpoints
```
POST /api/placement/manage/drives                  â†’ create drive
PUT  /api/placement/manage/drives/{id}/status      â†’ update drive status
GET  /api/placement/manage/drives/{id}/applicants  â†’ view applicants
PUT  /api/placement/manage/applications/{id}/statusâ†’ shortlist/select/reject
```

---

## ðŸ§ª Quick Test with cURL

```bash
# 1. Login
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@tkmit.ac.in","password":"admin123"}'

# 2. Copy token from response, then get stats:
curl http://localhost:8081/api/users/admin/stats \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"

# 3. Get active notices (no auth needed):
curl http://localhost:8081/api/notices/public
```

---

## ðŸ—ï¸ Database Tables

| Table | Purpose |
|-------|---------|
| `users` | All roles: students, faculty, HOD, principal, admin |
| `subjects` | Courses linked to faculty and department |
| `leave_requests` | Full leave approval chain with timestamps |
| `attendance` | Per-student per-subject per-day records |
| `marks` | Internal + university exam scores |
| `notices` | College notice board with visibility control |
| `placement_drives` | Campus recruitment drives |
| `placement_applications` | Student applications with AI scoring |
| `timetable` | Class schedule by dept/semester/section |

---

## ðŸ”§ Troubleshooting

**Problem: "Access denied for user 'root'"**
â†’ Open MySQL Workbench, run: `ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'sreehari_99';`

**Problem: Port 8081 already in use**
â†’ In `application.properties`, change `server.port=8081` to another free port and update the HTML `API_BASE` fallback to match.

**Problem: CORS error in browser**
â†’ The backend allows all origins in dev. Make sure backend is running on port 8081.

**Problem: 401 Unauthorized**
â†’ Token expired (24h). Log in again to get a fresh token.

**Problem: Maven download fails in IntelliJ**
â†’ Right-click `pom.xml` â†’ Maven â†’ Reload Project

---

## ðŸŽ¯ Leave Approval Flow

```
Student submits
      â†“
Faculty reviews â†’ APPROVED/REJECTED
      â†“ (if approved)
HOD reviews â†’ APPROVED/REJECTED
      â†“ (if > 3 days or Medical)
Principal reviews â†’ FINAL DECISION
      â†“
OD/Leave letter auto-generated (PDF)
```

Leaves â‰¤ 3 days: Faculty â†’ HOD (final)
Leaves > 3 days or Medical: Faculty â†’ HOD â†’ Principal

---

*EduSync Backend v1.0 Â· Spring Boot 3.2 Â· MySQL 8.0 Â· Java 17*

