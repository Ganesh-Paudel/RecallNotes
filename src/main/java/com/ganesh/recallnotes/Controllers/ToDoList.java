package com.ganesh.recallnotes.Controllers;

import com.ganesh.recallnotes.Controllers.independentComponents.TaskController;
import com.ganesh.recallnotes.FileHandling.ReadFile;
import com.ganesh.recallnotes.FileHandling.WriteFile;
import com.ganesh.recallnotes.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ToDoList implements Initializable {
    @FXML private Button goBack;
    @FXML private VBox taskList;
    @FXML private Pane addTaskPane;
    @FXML private TextField titleInput;
    @FXML private TextArea descriptionInput;
    @FXML private ToggleGroup priority;


    @FXML private void goBacktoHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("HomeScreen.fxml")));
            Stage stage = (Stage) goBack.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML private void addNewTask(ActionEvent event){
        addTaskPane.setVisible(true);
    }

    @FXML private void handleNewTask(ActionEvent event) throws IOException {
        String title = titleInput.getText();
        String description = descriptionInput.getText();
        RadioButton priorityToggle = (RadioButton) priority.getSelectedToggle();
        String priority = priorityToggle.getText();
        String priorityText = priority.equals("High") ? "H" : priority.equals("Medium") ? "M" : "L";

        System.out.println("Priority: " + priorityText);
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);

        WriteFile writeFile = new WriteFile("tasks.txt");
        writeFile.writeTask(title, description, priorityText);

        goBacktoToDo(event);

    }

    @FXML private void goBacktoToDo(ActionEvent event){
        titleInput.setText("");
        descriptionInput.setText("");
        addTaskPane.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("tasks.txt");
        ArrayList<String[]> tasks = new ArrayList<>();
        try {
            if (file.exists()) {
                ReadFile readFile = new ReadFile(file);
                tasks = readFile.getTasks();
            }

            int i = 0;
            while (i < tasks.size()) {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("independentComponents/TaskList.fxml"));
                TaskController taskController = new TaskController();
                loader.setController(taskController);
                Pane pane = loader.load();
                setTaskTitle(taskController, tasks.get(i)[1]);
                taskController.setPriorityColor(tasks.get(i)[0]);
                taskList.getChildren().add(pane);
                i++;
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTaskTitle(TaskController taskController, String task){
        taskController.setTaskTitle(task);
    }
}
