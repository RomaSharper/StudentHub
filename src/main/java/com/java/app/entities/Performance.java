package com.java.app.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "performance")
public class Performance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student", referencedColumnName = "cardId")
    private Student student;

    private byte grade;
    private String subject;
    private LocalDate date;

    @Override
    public String toString() {
        return String.format("%s: %s - %s", student, subject, grade);
    }
}