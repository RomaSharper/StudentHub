package com.java.app.repositories;

import com.java.app.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("StudentsRepository")
public interface StudentsRepository extends JpaRepository<Student, String> {
    Optional<Student> getStudentByPhone(String phone);
    Optional<Student> getStudentByCardId(String cardId);
    List<Student> getStudentsByFullNameLike(String fullName);
}
