package com.java.app.stages;

import com.java.app.interfaces.Loggable;
import com.java.app.classes.ViewLoadedLogger;
import com.java.app.enums.Views;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.java.app.data.App.*;

@Component
public class StageInitializer implements ApplicationListener<StageReadyEvent> {
    @Getter
    @Setter
    private static Views currentView;

    @Getter
    @Setter
    private static MediaPlayer mediaPlayer;

    @Getter
    @Setter
    private static ViewLoadedLogger defaultLogger = new ViewLoadedLogger();

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        setStage(event, Views.SIGNON, defaultLogger);
    }

    /**
     * Выполняет вывод в консоль
     * @author Roman Stalinist
     * @param logger Логгер для вывода в консоль
     **/
    public static void log(Loggable logger) {
        if (logger != null)
            logger.log(getCurrentView());
        else
            getDefaultLogger().log(getCurrentView());
    }

    /**
     * Устанавливает текущую стадию
     * @author Roman Stalinist
     * @param event Событие специально для кнопки <b>JFXButton</b>
     * @param view Вьюха, которую нужно поставить
     */
    public static void setStage(ActionEvent event, Views view) {
        setStage(event, view, null);
    }

    /**
     * Устанавливает текущую стадию
     * @param event Событие специально для кнопки <b>JFXButton</b>
     * @param view Вьюха, которую нужно поставить
     * @param logger Логгер для вывода в консоль
     * @author Roman Stalinist
     */
    public static void setStage(@NotNull ActionEvent event, Views view, Loggable logger) {
        try {
            if (view == currentView)
                return;

            setCurrentView(view);
            FXMLLoader loader = new FXMLLoader(StageInitializer.class.getResource(view.getFullFileName()));
            Parent parent = loader.load();

            Stage stage = (Stage) ((JFXButton) event.getSource()).getScene().getWindow();
            setupStage(view, logger, parent, stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Устанавливает текущую стадию
     * @author Roman Stalinist
     * @param event Событие при подгрузке
     * @param view Вьюха, которую нужно поставить
     * @param logger Логгер для вывода в консоль
     */
    public static void setStage(StageReadyEvent event, Views view, Loggable logger) {
        try {
            if (view == currentView)
                return;

            setCurrentView(view);
            FXMLLoader loader = new FXMLLoader(StageInitializer.class.getResource(view.getFullFileName()));
            Parent parent = loader.load();

            Stage stage = event.getStage();
            setupStage(view, logger, parent, stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Подготавливает и выводит на экран стадию
     * @author Roman Stalinist
     * @param view Вьюха стадии
     * @param logger Логгер
     * @param parent Родительский элемент
     * @param stage Стадия
     **/
    private static void setupStage(Views view, Loggable logger, Parent parent, Stage stage) {
        Scene scene = new Scene(parent);
        stage.setTitle(view.toString());
        stage.getIcons().add(getFavicon());
        stage.setMinWidth(850);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
        log(logger);
    }
}