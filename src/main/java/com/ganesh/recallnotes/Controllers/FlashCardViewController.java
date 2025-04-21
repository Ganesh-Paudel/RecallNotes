package com.ganesh.recallnotes.Controllers;

import com.ganesh.recallnotes.FileHandling.ReadFile;
import com.ganesh.recallnotes.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FlashCardViewController{
    @FXML private FlowPane flowFlashCard;
    private File file;

    public void initialData(File file){
        this.file = file;
        System.out.println("In the initialData method");
        System.out.println(file.getAbsolutePath());

        addCards();
    }

    private void addCards(){
        try {
            ReadFile readFile = new ReadFile(file);
            String[] section;
            while ((section = readFile.getSection()) != null) {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("FlashCard.fxml"));
                SingleCardController singleCardController = new SingleCardController();
                loader.setController(singleCardController);
                Pane cardPane = loader.load();
                singleCardController.setTitle(section[0]);
                singleCardController.setContent(section[1]);
                flowFlashCard.getChildren().add(cardPane);
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }  catch (IOException e){
            e.printStackTrace();
        }
    }



}
