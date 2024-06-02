package com.java.app.interfaces;

import com.java.app.enums.Views;
import org.springframework.boot.CommandLineRunner;

public interface Loggable extends CommandLineRunner {
    void log(Views view);
}
