package com.ganesh.recallnotes.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class FlashCardViewController{
    @FXML private FlowPane flowFlashCard;
    private File file;

    public void initialData(File file){
        this.file = file;
        System.out.println("In the initialData method");
        System.out.println(file.getAbsolutePath());
    }

    

}
