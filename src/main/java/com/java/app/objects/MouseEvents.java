package com.java.app.objects;

import javafx.scene.Parent;
import javafx.stage.Stage;

public class MouseEvents {
    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void dragMove(Parent rootPanel) {
        rootPanel.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        rootPanel.setOnMouseDragged(event -> {
            Stage stage = (Stage) rootPanel.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }
}
