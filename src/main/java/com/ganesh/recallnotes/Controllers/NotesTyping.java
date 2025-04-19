package com.ganesh.recallnotes.Controllers;

import com.ganesh.recallnotes.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;


public class NotesTyping {

    @FXML private Button addNotesButton;
    @FXML private TextField noteTitle;
    @FXML private TextArea noteContent;

    @FXML public void addNewNoteHandler(ActionEvent event) throws IOException {
        System.out.println("New Note button clicked");
        String title = noteTitle.getText();
        String content = noteContent.getText();
        System.out.println("Title: " + title);
        System.out.println("Content: " + content);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("New Note");
        alert.setHeaderText("Do you want a new File? ");
        alert.setContentText("Choose newFile or existing if you want existing file.");

        ButtonType newNoteFile = new ButtonType("New File");
        ButtonType existingNoteFile = new ButtonType("Existing File");
        ButtonType cancelButton = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(newNoteFile, existingNoteFile, cancelButton);
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()) {
            if(result.get() == newNoteFile) {
                System.out.println("New Note button clicked");
            } else if (result.get() == existingNoteFile) {
                System.out.println("Existing Note button clicked");
            } else{
                System.out.println("Cancel button clicked");
            }
        }

        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("MainView.fxml")));
        Stage stage = (Stage) addNotesButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
