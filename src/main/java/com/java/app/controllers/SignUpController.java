package com.java.app.controllers;

import com.java.app.classes.PasswordSecure;
import com.java.app.entities.User;
import com.java.app.enums.SoundTracks;
import com.java.app.enums.Views;
import com.java.app.exceptions.UserNotFoundException;
import com.java.app.objects.Alerts;
import com.java.app.objects.Avatars;
import com.java.app.objects.FileChoosers;
import com.java.app.services.UsersService;
import com.java.app.stages.StageInitializer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static com.java.app.Resources.string;
import static com.java.app.objects.Alerts.showError;
import static com.java.app.objects.Sounds.*;

public class SignUpController extends ControllerBase implements Initializable {
    @Getter
    @Setter
    private UsersService usersService;

    @Getter
    @Setter
    private String avatarUrl = "";

    @Getter
    @Setter
    private LocalDate dateOfBirth = null;

    @FXML public JFXPasswordField repeatPasswordField;
    @FXML public DatePicker dateOfBirthPicker;
    @FXML public JFXTextField avatarField;
    @FXML public JFXButton openFileBtn;
    @FXML public JFXButton signUpBtn;
    @FXML public JFXButton signOnBtn;
    @FXML public JFXPasswordField passwordField;
    @FXML public JFXTextField usernameField;

    public SignUpController() {
        this.usersService = getApplicationContext().getBean(UsersService.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    public void onSignUpButtonAction(ActionEvent event) {
        try {
            StringBuilder error = new StringBuilder();
            User user = new User();

            String username = usernameField.getText();
            String password = passwordField.getText();
            String repeatedPassword = repeatPasswordField.getText();
            dateOfBirth = dateOfBirthPicker.getValue();

            if (username.isEmpty())
                error.append("""
                        Не указано имя пользователя
                        """);

            if (password.isEmpty())
                error.append("""
                        Не указан пароль
                        """);

            if (!repeatedPassword.equals(password))
                error.append("""
                        Пароли не совпадают
                        """);

            // Future topic
            if (avatarUrl.isEmpty())
                error.append("""
                        Не указан аватар
                        """);

            if (dateOfBirth == null)
                error.append("""
                        Не указана дата рождения
                        """);

            try {
                usersService.getUserByDisplayName(username);
                error.append("""
                        Имя пользователя уже занято
                        """);
            } catch (UserNotFoundException ignored) {
            }

            if (!error.isEmpty()) {
                playSound(SoundTracks.ERROR, .4);
                showError(error.toString());
                return;
            }

            Avatars.setupAvatar(avatarUrl, avatarField.getText());
            String securePassword = PasswordSecure.hashPassword(password);
            user.setDisplayName(username);
            user.setPassword(securePassword);
            user.setAvatarUrl(avatarField.getText());
            user.setDateOfBirth(dateOfBirth);

            User createdUser = usersService.createUser(user);
            usersService.authenticate(createdUser.getDisplayName(), password);
            playSound(SoundTracks.ACCESS_GRANTED, .4);
            Alerts.showInformation(String.format("Приветствуем вас, %s", username), string("success"));
            StageInitializer.setStage(event, Views.MAIN);
        } catch (IOException e) {
            playSound(SoundTracks.ERROR, .4);
            Alerts.showError("Файл аватарки не найден");
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onOpenFileBtnClick() {
        playSound(SoundTracks.CLICK, .4);
        File selectedFile = FileChoosers.chooseFile();

        if (selectedFile != null) {
            avatarField.setText(selectedFile.getName());
            avatarUrl = selectedFile.getAbsolutePath();
            Avatars.load(selectedFile, avatarField);
            playSound(SoundTracks.LOADED, .4);
        }
    }

    public void onGoToSignOnAction(ActionEvent event) {
        playSound(SoundTracks.CLICK, .4);
        StageInitializer.setStage(event, Views.SIGNON);
    }
}