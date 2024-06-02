package com.java.app.services;

import com.java.app.entities.Parent;
import com.java.app.entities.Student;
import com.java.app.exceptions.ParentNotFoundException;
import com.java.app.repositories.ParentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentsService {
    @Autowired()
    @Qualifier("ParentsRepository")
    private ParentsRepository parentsRepository;

    @Autowired
    public ParentsService(ParentsRepository parentsRepository) {
        this.parentsRepository = parentsRepository;
    }

    public List<Parent> getParents() {
        return parentsRepository.findAll();
    }

    public Parent getParentById(Long id) {
        return parentsRepository.getParentById(id).orElseThrow(
                () -> new ParentNotFoundException(String.format("Родитель с id '%d' не найден", id))
        );
    }

    public List<Parent> getParentsByFullNameLike(String fullName) {
        return parentsRepository.getParentsByFullNameLike(fullName);
    }

    public Parent getParentByPhone(String workPhone) {
        return parentsRepository.getParentByWorkPhone(workPhone).orElseThrow(
                () -> new ParentNotFoundException(
                        String.format("Родитель с номером '%s' не найден", workPhone)
                )
        );
    }

    public List<Parent> getParentsByPhone(String workPhone) {
        return parentsRepository.getParentsByWorkPhone(workPhone);
    }

    public List<Parent> getParentsByStudent(Student student) {
        return parentsRepository.getParentsByStudent(student);
    }

    public Parent getParentByFullName(String fullName) {
        return parentsRepository.getParentByFullName(fullName).orElseThrow(
                () -> new ParentNotFoundException(String.format(
                        "Родитель '%s' не найден", fullName
                ))
        );
    }

    public void createParent(Parent parent) {
        parentsRepository.save(parent);
    }

    public void deleteParent(Parent parent) {
        parentsRepository.delete(parent);
    }

    public void deleteParents(List<Parent> parents) {
        parentsRepository.deleteAll(parents);
    }

    public void updateParent(Parent parent) {
        Long id = parent.getId();
        Parent existingParent = getParentById(id);
        existingParent.setFullName(parent.getFullName());
        existingParent.setWorkPhone(parent.getWorkPhone());
        existingParent.setStudent(parent.getStudent());
        existingParent.setAddress(parent.getAddress());
        parentsRepository.save(existingParent);
    }
}
