package com.java.app.services;

import com.java.app.entities.Performance;
import com.java.app.entities.Student;
import com.java.app.exceptions.PerformanceNotFoundException;
import com.java.app.repositories.PerformancesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformancesService {
    @Autowired
    @Qualifier("PerformancesRepository")
    private PerformancesRepository performancesRepository;

    @Autowired
    public PerformancesService(PerformancesRepository performancesRepository) {
        this.performancesRepository = performancesRepository;
    }

    public List<Performance> getPerformancesBySubjectLike(String subjectName) {
        return performancesRepository.getPerformancesBySubjectLike(subjectName);
    }

    public Performance getPerformanceById(Long id) {
        return performancesRepository.getPerformanceById(id).orElseThrow(
                () -> new PerformanceNotFoundException(String.format("Студент с cardId '%s' не найден", id))
        );
    }

    public List<Performance> getPerformances() {
        return performancesRepository.findAll();
    }

    public List<Performance> getPerformancesByStudent(Student student) {
        return performancesRepository.getPerformancesByStudent(student);
    }

    public void createPerformance(Performance performance) {
        performancesRepository.save(performance);
    }

    public void deletePerformance(Performance performance) {
        performancesRepository.delete(performance);
    }

    public void deletePerformances(List<Performance> performances) {
        performancesRepository.deleteAll(performances);
    }

    public void updatePerformance(Performance performance) {
        Long id = performance.getId();
        Performance existingPerformance = getPerformanceById(id);
        existingPerformance.setDate(performance.getDate());
        existingPerformance.setSubject(performance.getSubject());
        existingPerformance.setStudent(performance.getStudent());
        existingPerformance.setGrade(performance.getGrade());
        performancesRepository.save(existingPerformance);
    }
}
