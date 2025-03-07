package com.java.app.controllers;

import com.java.app.enums.SoundTracks;
import com.java.app.enums.Views;
import com.java.app.services.UsersService;
import com.java.app.stages.StageInitializer;
import com.java.app.exceptions.UserNotFoundException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ResourceBundle;

import static com.java.app.objects.Alerts.showError;
import static com.java.app.objects.Sounds.playSound;

public class SignInController extends ControllerBase implements Initializable {
    @Getter
    @Setter
    private UsersService usersService;

    @FXML public JFXButton signUpBtn;
    @FXML public JFXButton signOnBtn;
    @FXML public JFXTextField usernameField;
    @FXML public JFXPasswordField passwordField;

    public SignInController() {
        this.usersService = getApplicationContext().getBean(UsersService.class);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    protected void onSignOnButtonAction(ActionEvent event) {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            usersService.authenticate(username, password);
            playSound(SoundTracks.ACCESS_GRANTED, .4);
            StageInitializer.setStage(event, Views.MAIN);
        } catch (UserNotFoundException unfe) {
            playSound(SoundTracks.ACCESS_DENIED, .4);
            showError(unfe.getLocalizedMessage());
        }
    }

    public void onGoToSignUpAction(ActionEvent event) {
        playSound(SoundTracks.CLICK, .4);
        StageInitializer.setStage(event, Views.SIGNUP);
    }
}