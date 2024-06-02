package com.java.app.data;

import com.java.app.entities.User;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.Setter;

public class App {
    @Getter
    @Setter
    private static User user;

    @Getter
    private static final Image favicon = new Image("/views/img/favicon.png");

    @Getter
    private static final String stringsPath = "src/main/resources/values/strings.xml";
}
