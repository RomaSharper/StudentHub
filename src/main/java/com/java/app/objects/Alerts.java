package com.java.app.objects;

import com.java.app.enums.SoundTracks;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static com.java.app.data.App.*;
import static com.java.app.Resources.*;
import static com.java.app.objects.Sounds.playSound;

public class Alerts {
    public static ButtonType showCloseDialog() {
        return showConfirmation(string("exit_confirmation"), string("exit"));
    }

    public static ButtonType showLogoutDialog() {
        return showConfirmation(string("logout_confirmation"), string("logout"));
    }

    public static ButtonType showConfirmation(String message, String title) {
        Alert alert = new Alert(AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.initStyle(StageStyle.UNIFIED);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(getFavicon());
        playSound(SoundTracks.CLICK, .4);
        return alert.showAndWait().orElseThrow(RuntimeException::new);
    }

    public static void showInformation(String message, String title) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.initStyle(StageStyle.UNIFIED);
        alert.setContentText(message);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(getFavicon());
        alert.show();
    }

    public static void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(string("error"));
        alert.setHeaderText(string("error"));
        alert.initStyle(StageStyle.UNIFIED);
        alert.setContentText(message);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(getFavicon());
        alert.show();
    }
}
