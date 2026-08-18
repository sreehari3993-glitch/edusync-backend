package com.edusync.repository;

import com.edusync.model.Timetable;
import com.edusync.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimetableRepository extends JpaRepository<Timetable, Long> {

    List<Timetable> findByDepartmentAndSemesterAndSectionOrderByDayOfWeekAscPeriodNumberAsc(
        String department, String semester, String section
    );

    List<Timetable> findByFacultyOrderByDayOfWeekAscPeriodNumberAsc(User faculty);
}
