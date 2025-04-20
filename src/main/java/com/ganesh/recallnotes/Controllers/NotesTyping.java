package com.ganesh.recallnotes.Controllers;

import com.ganesh.recallnotes.Components.FileChooserComponent;
import com.ganesh.recallnotes.FileHandling.WriteFile;
import com.ganesh.recallnotes.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;


public class NotesTyping {

    @FXML private Button addNotesButton;
    @FXML private TextField noteTitle;
    @FXML private TextArea noteContent;
    private FileChooser fileChooser;
    private FileChooserComponent dialogBox;
    private String title;
    private String content;

    @FXML public void addNewNoteHandler(ActionEvent event) throws IOException {
        Stage stage = (Stage) addNotesButton.getScene().getWindow();
        System.out.println("New Note button clicked");
        this.title = noteTitle.getText();
        this.content = noteContent.getText();
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
                showDialogBox("save", stage);
                changeScene(stage);
            } else if (result.get() == existingNoteFile) {
                showDialogBox("choose", stage);
                changeScene(stage);
            } else{
                System.out.println("Cancel button clicked");
            }

        }

    }

    private void showDialogBox(String type, Stage stage) {
        dialogBox = new FileChooserComponent();
        boolean append = false;
        if(type.equals("save")){
            dialogBox.setAction("save");
            dialogBox.setTitle("New File");
        } else if (type.equals("choose")){
            append = true;
            dialogBox.setAction("choose");
            dialogBox.setTitle("Choose File");
        }

        dialogBox.setStage(stage);
        FileChooser fileChooser = dialogBox.getChooser();
        File file = dialogBox.showDialogBox(fileChooser);
        if (file != null) {
            if(this.title != null && this.content != null){
                WriteFile writer = new WriteFile(append);
                writer.write(file, this.title, this.content);
            }
        }

    }
    
    private void changeScene(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("MainView.fxml")));
        stage.setScene(new Scene(root));
    }
}
