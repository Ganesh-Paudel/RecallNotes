package com.ganesh.recallnotes.Controllers.independentComponents;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TaskController {
    @FXML private Text taskTitle;
    @FXML private Pane priorityIndicator;

    @FXML
    private void showTask(MouseEvent event){
        System.out.println("Showing task");
        System.out.println(taskTitle.getText());
    }

    public void setTaskTitle(String taskTitle){
        this.taskTitle.setText(taskTitle);
    }

    public void setPriorityColor(String priority){
        String color = priority.equals("H") ? "red" : priority.equals("M") ? "blue" : "green";
        priorityIndicator.setStyle("-fx-background-color: " + color + ";" +
                                    "-fx-background-radius: 100;"
        );
    }


}
