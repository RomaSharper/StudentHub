package com.java.app.objects;

import com.java.app.components.MyTooltip;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.java.app.objects.Images.prepareImageView;

public class Avatars {
    public static void setupAvatar(String from, String to) throws IOException {
        String currentDirectory = System.getProperty("user.dir");
        Path source = Paths.get(from);
        Path target = Paths.get(currentDirectory, "src", "main", "resources", "drawables", to);
        java.nio.file.Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    /**
     * Привязывает к <b>node</b> Tooltip с изображением
     **/
    public static void load(File selectedFile, Node node) {
        ImageView view = prepareImageView(selectedFile);
        MyTooltip tooltip = new MyTooltip(selectedFile.getAbsolutePath(), view);
        tooltip.setGraphic(view);
        Tooltip.install(node, tooltip);
    }
}
