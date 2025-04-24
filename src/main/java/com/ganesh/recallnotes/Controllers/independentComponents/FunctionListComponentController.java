package com.ganesh.recallnotes.Controllers.independentComponents;

import com.ganesh.recallnotes.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class FunctionListComponentController {

    @FXML private Text functionName;


    @FXML
    private void handleFunctionClicked(MouseEvent event){
        System.out.println("Function Clicked");
        final String nameOfFunction = functionName.getText();

        switch(nameOfFunction){
            case "FlashCards":
                switchScreens("FlashCardsView");
                break;
            case "Notes":
                switchScreens("NotesTyping");
                break;
            case "Recall Notes":
                switchScreens("RecallNotes");
                break;
            case "Timer":
                switchScreens("Timer");
                break;
            default:
                System.out.println("Not implemented Yet!!");

        }

    }

    public void setFunctionName(String name){
        if(name == null){
            return;
        }
        functionName.setText(name);
    }

    private void switchScreens(String screenName){
        try {
            String screen = screenName + ".fxml";
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(screen)));
            Stage stage = (Stage) functionName.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch(IOException e){
            System.out.println("switching screens io problem!");
            e.printStackTrace();
        }
    }
}
