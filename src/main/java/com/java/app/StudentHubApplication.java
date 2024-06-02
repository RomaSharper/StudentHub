package com.java.app;

import javafx.application.Application;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Getter
@Setter
@SpringBootApplication
public class StudentHubApplication {
	public static void main(String[] args) {
		Application.launch(JavaFxApplication.class, args);
	}
}
