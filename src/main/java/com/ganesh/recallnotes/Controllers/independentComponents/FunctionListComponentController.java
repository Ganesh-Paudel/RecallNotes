package com.ganesh.recallnotes.Controllers.independentComponents;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class FunctionListComponentController {

    @FXML private Text functionName;


    @FXML
    private void handleFunctionClicked(MouseEvent event){
        System.out.println("Function Clicked");
    }

    public void setFunctionName(String name){
        if(name == null){
            return;
        }
        functionName.setText(name);
    }
}
