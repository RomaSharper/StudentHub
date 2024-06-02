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
@Table(name = "students")
public class Student {
    @Id
    private String cardId;

    @Column(nullable = false, length = 60)
    private String fullName;

    @Column(nullable = false, length = 1)
    private String gender;

    private LocalDate dateOfBirth;

    @Column(nullable = false, length = 100)
    private String address;

    @Column(nullable = false, length = 11)
    private String phone;

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public int hashCode() {
        return cardId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Student student && student.hashCode() == hashCode();
    }
}