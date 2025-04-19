package com.ganesh.recallnotes.Components;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class FileChooserComponent {

    private String action;
    private String title;
    private Stage stage;
    private String[] extensions;


    public FileChooserComponent() {
        this.action = "choose";
        this.title = "Choose File";
        this.extensions = new String[] {"txt"};
        this.stage = null;
    }

    public FileChooserComponent(String action, String title, Stage stage) {
        this.action = action;
        this.title = title;
        this.stage = stage;
    }

    public FileChooserComponent(String action, String title, Stage stage, String[] extensions) {
        this(action, title, stage);
        this.extensions = extensions;
    }

    public FileChooser getChooser(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Documents"));

        return fileChooser;
    }

    public File showDialogBox(FileChooser chooser){
        if(this.action.equals("choose")){
            return chooser.showOpenDialog(this.stage);
        } else if (this.action.equals("save")){
            return chooser.showSaveDialog(this.stage);
        }
        return null;
    }

    public void setTitle(String title) {
        this.title = title != null ? title : "";
    }

    public void setAction(String action) {
        String Action = action.toLowerCase();
        boolean valid = (List.of("choose", "save").contains(Action));

        if(valid){
            this.action = action;
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
