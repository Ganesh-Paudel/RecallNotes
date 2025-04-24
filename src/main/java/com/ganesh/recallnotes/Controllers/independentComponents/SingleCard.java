package com.ganesh.recallnotes.Controllers.independentComponents;

import com.ganesh.recallnotes.Controllers.FlashCardView;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;


public class SingleCard {
    @FXML private Text cardTitle;
    @FXML private TextArea cardTextArea;
    private int cardId;
    private FlashCardView parentController;

    public SingleCard(int id, FlashCardView parentController){
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
