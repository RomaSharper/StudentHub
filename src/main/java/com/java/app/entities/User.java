package com.java.app.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "displayName", nullable = false, length = 20, unique = true)
    private String displayName;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "avatarUrl", length = 256)
    private String avatarUrl;

    @Column(name = "dateOfBirth", nullable = false)
    private LocalDate dateOfBirth;

    @Column(name = "created", nullable = false)
    private LocalDate created = LocalDate.now();

    @Override
    public String toString() {
        return displayName;
    }
}