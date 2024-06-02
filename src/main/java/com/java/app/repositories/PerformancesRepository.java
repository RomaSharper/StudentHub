package com.java.app.repositories;

import com.java.app.entities.Performance;
import com.java.app.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("PerformancesRepository")
public interface PerformancesRepository extends JpaRepository<Performance, Long> {
    Optional<Performance> getPerformanceById(Long id);
    List<Performance> getPerformancesByStudent(Student student);
    List<Performance> getPerformancesBySubjectLike(String subjectName);
}
