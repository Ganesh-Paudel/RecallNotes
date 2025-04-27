package com.ganesh.recallnotes.Controllers.independentComponents;

import com.ganesh.recallnotes.Controllers.ToDoList;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TaskController {
    public static int currentSelectedTaskId = -1;
    @FXML private Text taskTitle;
    @FXML private Pane priorityIndicator;
    private ToDoList toDoclass;
    private String description;
    private int id;

    @FXML
    private void showTask(MouseEvent event){
        System.out.println("Showing task");
        System.out.println(taskTitle.getText());
        toDoclass.setInfoPanel(taskTitle.getText(), this.description);
        toDoclass.showTaskInfoPanel();
        currentSelectedTaskId = this.id;
    }



    public void setTaskTitle(String taskTitle){
        this.taskTitle.setText(taskTitle);
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setPriorityColor(String priority){
        String color = priority.equals("H") ? "red" : priority.equals("M") ? "blue" : "green";
        priorityIndicator.setStyle("-fx-background-color: " + color + ";" +
                                    "-fx-background-radius: 100;"
        );
    }

    public void setToDoListClass(ToDoList toDoClass){
        this.toDoclass = toDoClass;
    }


}
