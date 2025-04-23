package com.ganesh.recallnotes.Controllers.independentComponents;

import com.ganesh.recallnotes.Controllers.FlashCardViewController;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;


public class SingleCardController {
    @FXML private Text cardTitle;
    @FXML private TextArea cardTextArea;
    private int cardId;
    private FlashCardViewController parentController;

    public SingleCardController(int id, FlashCardViewController parentController){
        this.cardId = id;
        this.parentController = parentController;
    }


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

    public String getTitle(){
        return this.cardTitle.getText();
    }

    public String getContent(){
        return this.cardTextArea.getText();
    }

    @FXML
    private void handleCardClicked(MouseEvent event){
        parentController.showInvisible(this.cardId, this);
    }
}
