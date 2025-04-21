package com.ganesh.recallnotes.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class SingleCardController {
    @FXML private Text cardTitle;
    @FXML private TextArea cardTextArea;

    public void setTitle(String title){
        if(title != null){
            cardTitle.setText(title);
        }
    }

    public void setContent(String content){
        if(content != null){
            cardTextArea.setText(content);
        }
    }
}
