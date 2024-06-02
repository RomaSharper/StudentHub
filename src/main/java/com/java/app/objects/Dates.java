package com.java.app.objects;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Dates {
    public static <T> Callback<TableColumn<T, LocalDate>, TableCell<T, LocalDate>> setRussianDateFormat() {
        return column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    setText(item.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                }
            }
        };
    }
}
