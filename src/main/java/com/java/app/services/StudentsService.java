package com.java.app.services;

import com.java.app.entities.Student;
import com.java.app.exceptions.StudentNotFoundException;
import com.java.app.repositories.StudentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentsService {
    @Autowired
    @Qualifier("StudentsRepository")
    private StudentsRepository studentsRepository;

    @Autowired
    public StudentsService(StudentsRepository studentsRepository) {
        this.studentsRepository = studentsRepository;
    }

    public List<Student> getStudents() {
        return studentsRepository.findAll();
    }

    public Student getStudentByCardId(String cardId) {
        return studentsRepository.getStudentByCardId(cardId).orElseThrow(
                () -> new StudentNotFoundException(String.format("Студент с cardId '%s' не найден", cardId))
        );
    }

    public Student getStudentByPhone(String phone) {
        return studentsRepository.getStudentByPhone(phone).orElseThrow(
                () -> new StudentNotFoundException(String.format(
                        "Студент с телефоном '%s' не найден", phone
                ))
        );
    }

    public List<Student> getStudentsByFullNameLike(String fullName) {
        return studentsRepository.getStudentsByFullNameLike("%" + fullName + "%");
    }

    public void createStudent(Student student) {
        studentsRepository.save(student);
    }

    public List<Student> createStudents(List<Student> students) {
        return studentsRepository.saveAll(students);
    }

    public void deleteStudent(Student student) {
        studentsRepository.delete(student);
    }

    public void updateStudent(Student student) {
        String cardId = student.getCardId();
        Student existingStudent = getStudentByCardId(cardId);
        existingStudent.setFullName(student.getFullName());
        existingStudent.setPhone(student.getPhone());
        existingStudent.setAddress(student.getAddress());
        existingStudent.setDateOfBirth(student.getDateOfBirth());
        existingStudent.setGender(student.getGender());
        studentsRepository.save(existingStudent);
    }
}
