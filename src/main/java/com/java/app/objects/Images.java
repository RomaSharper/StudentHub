package com.java.app.objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Images {
    public static ImageView prepareImageView(File imageFile) {
        ImageView view = new ImageView(new Image(imageFile.toURI().toString()));
        view.setFitHeight(100);
        view.setFitWidth(100);
        // view.setPreserveRatio(true); // сохранение пропорций изображения
        view.setSmooth(true); // включение сглаживания
        view.setCache(true); // включение кэширования для увеличения производительности
        return view;
    }
}
