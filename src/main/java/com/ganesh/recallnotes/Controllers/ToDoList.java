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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the toDoList window
 */
public class ToDoList implements Initializable {

    /**
     * This is an representation of task when using listview and observable list
     */
    private class TaskData{
        static int idCount = 0;
        private int id;
        private String taskTitle;
        private String taskPriority;
        private String taskDescription;

        /**
         * constructor which sets the initital value
         * @param title title of task
         * @param priority priority of task
         * @param description description of task
         */
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
        public boolean equals(TaskData anotherTask){
            return anotherTask.taskTitle.equals(this.taskTitle) && anotherTask.taskPriority.equals(this.taskPriority);
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
    /* observalbe list that will be used with the listview so instant updates */
    private ObservableList<TaskData> tasks = FXCollections.observableArrayList();


    /**
     * event method that is used for navigation and goes back to the home screen
     * @param event
     */
    @FXML private void goBacktoHome(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("HomeScreen.fxml")));
            Stage stage = (Stage) goBack.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * when the add task button is clicked a pane is shown up which is setVisible here
     * @param event
     */
    @FXML private void addNewTask(ActionEvent event){
        addTaskPane.setVisible(true);
    }

    /**
     * This method gets called when the add task button in the panel of addin task is clicked then it retrieves all
     * the task and uses the write file class to write the task into the file
     * @param event
     * @throws IOException
     */
    @FXML private void handleNewTask(ActionEvent event) throws IOException {
        String title = titleInput.getText();
        String description = descriptionInput.getText();
        RadioButton priorityToggle = (RadioButton) priority.getSelectedToggle();
        String priority = priorityToggle.getText();
        String priorityText = priority.equals("High") ? "H" : priority.equals("Medium") ? "M" : "L";

//        System.out.println("Priority: " + priorityText);
//        System.out.println("Title: " + title);
//        System.out.println("Description: " + description);

        WriteFile writeFile = new WriteFile("tasks.txt");
        writeFile.writeTask(title, description, priorityText);

        goBacktoToDo(event);

    }

    /**
     * This is another navigation where the pane of adding task is setvisible to false so it feels like navigating back
     * also it sets the inputs of the adding task panel to default and updates the task list so the newly added task
     * is displayed concurrently in the UI
     * @param event
     */
    @FXML private void goBacktoToDo(ActionEvent event){
        titleInput.setText("");
        descriptionInput.setText("");
        updateTaskList();
        addTaskPane.setVisible(false);
    }

    /**
     * This method is called even before the ui is called and i am using this so it retrieves the task and displays
     * it in the list view so it doesn't seem labby
     * @param url
     * @param resourceBundle
     */
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


    /**
     * This method reads all the tasks and adds them all to the tasks file
     */
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

    /**
     * sets the visibility of the information panel of task to true si it shows up
     */
    public void showTaskInfoPanel(){
        taskInfoPanel.setVisible(true);
    }

    /**
     * the information regarding the panel is setted here. it changes the text to the information given about the
     * task the user clicked
     * @param title
     * @param description
     */
    public void setInfoPanel(String title, String description){
        taskTitle.setText(title);
        taskDescriptionArea.setText(description);
    }

    /**
     * changes the visibility of the panel to false so it's not visible
     */
    public void closeTaskInfoPanel(){
        taskInfoPanel.setVisible(false);
    }

    /**
     * closes the info panel that shows the task title and description with option to delete
     * @param event
     */
    @FXML
    private void closeDescriptiveView(ActionEvent event){
        closeTaskInfoPanel();
    }

    /**
     * When user deletes task it removes from the observable lsit which updates the list view and
     * then write the task in the file so it's removed completely using the write file class
     * @param event
     */
    @FXML
    private void handleDeleteTask(ActionEvent event){
//        System.out.println("Deleting task");
        File file = new File("tasks.txt");
        TaskData selectedTask = (TaskData) taskListView.getSelectionModel().getSelectedItem();
        tasks.remove(selectedTask);
        try {
                ArrayList<String[]> newTask = new ArrayList<>();
                for (int i = 0; i < tasks.size(); i++) {
                        newTask.add(new String[]{tasks.get(i).taskPriority, tasks.get(i).taskTitle,
                                tasks.get(i).taskDescription});
                }

                WriteFile writeFile = new WriteFile();
                writeFile.writeTask(newTask);
                updateTaskList();
                closeTaskInfoPanel();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
