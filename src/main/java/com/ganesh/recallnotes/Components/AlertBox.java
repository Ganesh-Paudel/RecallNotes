package com.ganesh.recallnotes.Components;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertBox {

    private Alert alert;
    private String title;
    private String header;
    private String message;

    @FXML
    private ButtonType[] buttons;

    public AlertBox(String type) {
        switch (type){
            case "confirmation":
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                break;
            case "warning":
                alert = new Alert(Alert.AlertType.WARNING);
                break;
            case "error":
                alert = new Alert(Alert.AlertType.ERROR);
                break;
            case "none":
                alert = new Alert(Alert.AlertType.NONE);
                break;
            default:
                alert = new Alert(Alert.AlertType.CONFIRMATION);
        }
    }

    public AlertBox(String type, String title, String headerText, String contentText){
        this(type);
        this.alert.setTitle(title);
        this.alert.setHeaderText(headerText);
        this.alert.setContentText(contentText);
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public void setHeader(String header) {
        this.header = header;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public void addButtons(String... buttonNames){
        buttons = new ButtonType[buttonNames.length];

        for (int i = 0; i < buttonNames.length; i++) {
            buttons[i] = new ButtonType(buttonNames[i]);
        }
        this.alert.getButtonTypes().setAll(buttons);
    }

    public Optional<ButtonType> getResult(){
        return this.alert.showAndWait();
    }
}
