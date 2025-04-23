package com.ganesh.recallnotes.Controllers;

import com.ganesh.recallnotes.Controllers.independentComponents.FunctionListComponentController;
import com.ganesh.recallnotes.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {

    ArrayList<String> functions = new ArrayList<>();
    @FXML
    FlowPane functionsListPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        functions.add("FlashCards");
        functions.add("Timer");
        functions.add("Notes");
        functions.add("Recall Notes");
        functions.add("Mind Maps");

        try {
            addFunctions();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addFunctions() throws IOException {
        int i = 0;
        while(i < this.functions.size()){
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("independentComponents/FunctionListComponent.fxml"));
            FunctionListComponentController functionController = new FunctionListComponentController();
            loader.setController(functionController);
            Pane pane = loader.load();
            functionController.setFunctionName(this.functions.get(i));

            functionsListPane.getChildren().add(pane);
            i++;
        }
    }
}
