package com.ganesh.recallnotes.Controllers.independentComponents;

import com.ganesh.recallnotes.Controllers.FlashCardView;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;


/**
 * This is controller for a single card in flashcard
 */
public class SingleCard {
    @FXML private Text cardTitle;
    @FXML private TextArea cardTextArea;
    /* id to keep track of which card is where and also when it's clicked */
    private int cardId;
    /* it's so that we can access the methods in the flashcard view controller once they are clicked */
    private FlashCardView parentController;

    /* constructor that initializes the variables with the given ones*/
    public SingleCard(int id, FlashCardView parentController){
        this.cardId = id;
        this.parentController = parentController;
    }


    /**
     * setter for the title of the flash card
     * @param title title of the card
     */
    public void setTitle(String title){
        if(title != null){
            cardTitle.setText(title);
        }
    }

    /**
     * setter for the content of the flash card
     * @param content content of the card
     */
    public void setContent(String content){
        if(content != null){
            cardTextArea.setText(content);
        }
    }

    /**
     * returns the title of the card
     * @return title of the card
     */
    public String getTitle(){
        return this.cardTitle.getText();
    }

    /**
     * returns the content of the card
     * @return content of the card
     */
    public String getContent(){
        return this.cardTextArea.getText();
    }

    /**
     * whenever the card is clicked it shows a panel for the card in big view which is done using the showInvisible
     * method of the parentController
     * @param event
     */
    @FXML
    private void handleCardClicked(MouseEvent event){
        parentController.showInvisible(this.cardId, this);
    }
}
