package com.java.app.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuItemController extends ControllerBase implements Initializable {
    @FXML public Label name;
    @FXML public Button changeBtn;
    @FXML public Label currentValue;
    @FXML public FontAwesomeIconView icon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setCurrentValue(Object value) {
        this.currentValue.setText(value.toString());
    }

    public void setInfo(String name, Object value, String glyphName) {
        this.name.setText(name);
        this.currentValue.setText(value.toString());
        this.icon.setGlyphName(glyphName);
    }

    public void setListener(EventHandler<ActionEvent> event) {
        changeBtn.setOnAction(event);
    }
}
