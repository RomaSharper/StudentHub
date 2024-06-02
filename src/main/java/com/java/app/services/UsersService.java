package com.java.app.services;

import com.java.app.classes.PasswordSecure;
import com.java.app.data.App;
import com.java.app.entities.User;
import com.java.app.repositories.UsersRepository;
import com.java.app.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UsersService {
    @Autowired
    @Qualifier("UsersRepository")
    private UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public User getUserById(Long id) {
        return usersRepository.getUserById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("Пользователь с id '%d' не найден", id))
        );
    }

    public User getUserByDisplayName(String displayName) {
        return usersRepository.getUserByDisplayName(displayName).orElseThrow(
                () -> new UserNotFoundException(String.format("Пользователь с отображаемым именем '%s' не найден", displayName))
        );
    }

    public void authenticate(String displayName, String password) {
        User user = usersRepository.getUserByDisplayName(displayName).orElseThrow(
                () -> new UserNotFoundException("Неверный логин")
        );

        if (PasswordSecure.verifyPassword(password, user.getPassword()))
            App.setUser(user);
        else
            throw new UserNotFoundException("Неверный пароль");
    }

    public User createUser(User user) {
        return usersRepository.save(user);
    }

    public void deleteUser(User user) {
        usersRepository.delete(user);
    }

    public void updateUserDisplayName(User user, String displayName) {
        Long id = user.getId();
        User existingUser = getUserById(id);
        existingUser.setDisplayName(displayName);
        usersRepository.save(existingUser);
    }

    public void updateUserPassword(User user, String password) {
        Long id = user.getId();
        User existingUser = getUserById(id);
        existingUser.setPassword(PasswordSecure.hashPassword(password));
        usersRepository.save(existingUser);
    }

    public void updateUserAvatarUrl(User user, String avatarUrl) {
        Long id = user.getId();
        User existingUser = getUserById(id);
        existingUser.setAvatarUrl(avatarUrl);
        usersRepository.save(existingUser);
    }

    public void updateUserDateOfBirth(User user, LocalDate dateOfBirth) {
        Long id = user.getId();
        User existingUser = getUserById(id);
        existingUser.setDateOfBirth(dateOfBirth);
        usersRepository.save(existingUser);
    }
}
