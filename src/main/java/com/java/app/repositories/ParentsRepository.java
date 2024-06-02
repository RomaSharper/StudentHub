package com.java.app.repositories;

import com.java.app.entities.Parent;
import com.java.app.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ParentsRepository")
public interface ParentsRepository extends JpaRepository<Parent, Long> {
    Optional<Parent> getParentById(Long id);
    List<Parent> getParentsByStudent(Student student);
    List<Parent> getParentsByWorkPhone(String workPhone);
    Optional<Parent> getParentByFullName(String fullName);
    Optional<Parent> getParentByWorkPhone(String workPhone);
    List<Parent> getParentsByFullNameLike(String fullName);
}
