package com.java.app.controllers;

import com.java.app.JavaFxApplication;
import lombok.Getter;
import org.springframework.context.ConfigurableApplicationContext;

@Getter
public abstract class ControllerBase {
    private final ConfigurableApplicationContext applicationContext = JavaFxApplication.getApplicationContext();
}
