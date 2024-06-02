package com.java.app.objects;

import javafx.stage.FileChooser;

import java.io.File;

public class FileChoosers {
    public static File chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "\\Downloads"));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showOpenDialog(null);
    }
}
