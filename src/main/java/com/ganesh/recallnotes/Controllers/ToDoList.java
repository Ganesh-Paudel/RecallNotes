package com.ganesh.recallnotes.Controllers;

import com.ganesh.recallnotes.Controllers.independentComponents.TaskController;
import com.ganesh.recallnotes.FileHandling.ReadFile;
import com.ganesh.recallnotes.FileHandling.WriteFile;
import com.ganesh.recallnotes.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ToDoList implements Initializable {

    private class TaskData{
        static int idCount = 0;
        private int id;
        private String taskTitle;
        private String taskPriority;
        private String taskDescription;

        public TaskData(String title, String priority, String description){
            this.id = idCount++;
            this.taskTitle = title;
            this.taskPriority = priority;
            this.taskDescription = description;
        }

        public String getTitle(){
            return this.taskTitle;
        }

        public String getPriority(){
            return this.taskPriority;
        }
    }


    @FXML private Button goBack;
    @FXML private ListView taskListView;
    @FXML private Pane addTaskPane;
    @FXML private TextField titleInput;
    @FXML private TextArea descriptionInput;
    @FXML private ToggleGroup priority;
    @FXML private Pane taskInfoPanel;
    @FXML private Text taskTitle;
    @FXML private TextArea taskDescriptionArea;
    private ObservableList<TaskData> tasks = FXCollections.observableArrayList();


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
        updateTaskList();
        addTaskPane.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTaskList();
        taskListView.setStyle("-fx-selection-bar: #FF5733; -fx-selection-bar-non-focused: #FF5733;");
        taskListView.setItems(tasks);

            taskListView.setCellFactory(listView -> new ListCell<TaskData>(){
                @Override
                protected  void updateItem(TaskData task, boolean empty){
                    super.updateItem(task, empty);
                    if(empty || task == null){
                        setGraphic(null);
                    } else {
                        try{
                            FXMLLoader loader = new FXMLLoader(Main.class.getResource("independentComponents/TaskList" +
                                    ".fxml"));
                            Pane root = loader.load();
                            TaskController taskController = loader.getController();
                            taskController.setId(task.id);
                            taskController.setDescription(task.taskDescription);
                            taskController.setToDoListClass(ToDoList.this);
                            taskController.setTaskTitle(task.getTitle());
                            taskController.setPriorityColor(task.getPriority());
                            setGraphic(root);

                        } catch (IOException e){
                            e.printStackTrace();
                        }

                    }

                }
            });
    }


    private void updateTaskList() {
        try {
            ArrayList<String[]> taskList = new ArrayList<>();
            File file = new File("tasks.txt");

            if (file.exists()) {
                ReadFile readFile = new ReadFile(file);
                taskList = readFile.getTasks();
            }
            int i = 0;
            tasks.clear();
            while (i < taskList.size()) {
                tasks.add(new TaskData(taskList.get(i)[1], taskList.get(i)[0], taskList.get(i)[2]));
                i++;
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void showTaskInfoPanel(){
        taskInfoPanel.setVisible(true);
        System.out.println(TaskController.currentSelectedTaskId);
    }

    public void setInfoPanel(String title, String description){
        taskTitle.setText(title);
        taskDescriptionArea.setText(description);
    }

    public void closeTaskInfoPanel(){
        taskInfoPanel.setVisible(false);
    }

    @FXML
    private void closeDescriptiveView(ActionEvent event){
        closeTaskInfoPanel();
    }

    @FXML
    private void handleDeleteTask(ActionEvent event){
        System.out.println("Deleting task");
        File file = new File("tasks.txt");

        try {
            if (file.exists()) {
                ReadFile readFile = new ReadFile(file);
                ArrayList<String[]> taskList = readFile.getTasks();
                ArrayList<String[]> newTaskList = new ArrayList<>();
                for (int i = 0; i < taskList.size(); i++) {
                    if(i != TaskController.currentSelectedTaskId){
                        newTaskList.add(taskList.get(i).clone());
                    }
                }

                WriteFile writeFile = new WriteFile();
                writeFile.writeTask(newTaskList, true);
                updateTaskList();
                closeTaskInfoPanel();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
