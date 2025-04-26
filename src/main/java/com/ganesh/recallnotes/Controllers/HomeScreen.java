package com.ganesh.recallnotes.Controllers;

import com.ganesh.recallnotes.Controllers.independentComponents.FunctionListComponent;
import com.ganesh.recallnotes.FileHandling.ReadFile;
import com.ganesh.recallnotes.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeScreen implements Initializable {

    /* Stores the list of the functions that are available in the program currently it adds on runtime */
    ArrayList<String> functions = new ArrayList<>();
    /* The flowpane element which will display the functions*/
    @FXML
    private FlowPane functionsListPane;
    @FXML private Text priorityTask;


    /**
     * Runs before the ui gets rendered and currently adds the functions
     * Calls addFunctions
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        functions.add("FlashCards");
        functions.add("Timer");
        functions.add("Notes");
        functions.add("Recall Notes");
        functions.add("Mind Maps");
        functions.add("To Do");

        try {
            addFunctions();
            File file = new File("tasks.txt");
            if(file.exists()){
                ReadFile readFile = new ReadFile(file);
                priorityTask.setText(readFile.getPriorityTask().split("=>|::")[1]);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Goes through the functions defined in the list and for each of those functions creates the designated fxml
     * element with the given file and adds to the flowpane
     * @throws IOException throws if the file doesnot exist the function representing fxml file
     */
    private void addFunctions() throws IOException {
        int i = 0;
        while(i < this.functions.size()){
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("independentComponents/FunctionListComponent.fxml"));
            FunctionListComponent functionController = new FunctionListComponent();
            loader.setController(functionController);
            Pane pane = loader.load();
            functionController.setFunctionName(this.functions.get(i));

            functionsListPane.getChildren().add(pane);
            i++;
        }
    }
}
