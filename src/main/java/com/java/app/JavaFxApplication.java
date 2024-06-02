package com.java.app;

import com.java.app.enums.SoundTracks;
import com.java.app.objects.Alerts;
import com.java.app.stages.StageReadyEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import static com.java.app.objects.Sounds.playSound;

public class JavaFxApplication extends Application {
    @Getter
    @Setter
    @Qualifier("applicationContext")
    private static ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(StudentHubApplication.class).run();
    }

    @Override
    public void start(Stage stage) {
        applicationContext.publishEvent(new StageReadyEvent(stage));
        playSound(SoundTracks.STARTUP);
        stage.setOnCloseRequest(event -> {
            if (Alerts.showCloseDialog() != ButtonType.YES)
                event.consume();
            playSound(SoundTracks.CLICK, .4);
        });
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }
}
