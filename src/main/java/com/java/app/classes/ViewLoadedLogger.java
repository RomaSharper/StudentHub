package com.java.app.classes;

import com.java.app.enums.Views;
import com.java.app.interfaces.Loggable;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ViewLoadedLogger implements Loggable {
    @Override
    public void log(Views view) {
        if (view == null)
            return;

        ZonedDateTime currentTime = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String formattedDateTime = currentTime.format(formatter);
        run(String.format("%s  \u001B[32mINFO\u001B[0m \u001B[35m1337\u001B[0m --- [StudentHub] [JavaFX] \u001B[36mcom.java.studenthub.stages.StageInitializer\u001B[0m       : Открытие вьюхи: %s (%s)", formattedDateTime, view, view.getFullFileName()));
    }

    @Override
    public void run(String... args) {
        System.out.println(String.join("", args));
    }
}
