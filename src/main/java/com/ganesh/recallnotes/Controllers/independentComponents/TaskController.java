package com.ganesh.recallnotes.Controllers.independentComponents;

import com.ganesh.recallnotes.Controllers.ToDoList;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/**
 * controller for individual task item graphic component
 */
public class TaskController {
    @FXML private Text taskTitle;
    @FXML private Pane priorityIndicator;
    /* reference of todolist class os that i can access the methods to update */
    private ToDoList toDoclass;
    /* description of task */
    private String description;
    private int id;

    /**
     * Event method when the mouse clicked on the task it calls the info panel where the description of the task is
     * hown.
     * @param event Mouse event
     */
    @FXML
    private void showTask(MouseEvent event){
//        System.out.println(taskTitle.getText());
        toDoclass.setInfoPanel(taskTitle.getText(), this.description);
        toDoclass.showTaskInfoPanel();
    }


    /**
     * setter for the tasktitle of the text fxml element
     * @param taskTitle title of the task
     */
    public void setTaskTitle(String taskTitle){
        this.taskTitle.setText(taskTitle);
    }

    /**
     * stores the description so it's easier to access later when showing
     * @param description description of the task
     */
    public void setDescription(String description){
        this.description = description;
    }

    /**
     * sets the id for the task
     * @param id id of the task
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Based on priority the color of the label is changed and this method checks give n priority and sets the color
     * based on the priority red for high, blue for mid and green for low
     * @param priority priority ("H", "M", "L")
     */
    public void setPriorityColor(String priority){
        String color = priority.equalsIgnoreCase("H") ? "red" : priority.equalsIgnoreCase("M") ? "blue" : "green";
        priorityIndicator.setStyle("-fx-background-color: " + color + ";" +
                                    "-fx-background-radius: 100;"
        );
    }

    /**
     * setter for the todolist class reference
     * @param toDoClass the reference of the class
     */
    public void setToDoListClass(ToDoList toDoClass){
        this.toDoclass = toDoClass;
    }


}
