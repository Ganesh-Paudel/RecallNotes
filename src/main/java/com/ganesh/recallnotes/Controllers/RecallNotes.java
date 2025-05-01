package com.ganesh.recallnotes.Controllers;

import com.ganesh.recallnotes.Components.FileChooserComponent;
import com.ganesh.recallnotes.FileHandling.ReadFile;
import com.ganesh.recallnotes.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


/**
 * The recall notes controller
 */
public class RecallNotes{
   @FXML
   private HBox feelingsBox;
   @FXML private Text greetingText;
   @FXML private Button newNote;
   @FXML private TextFlow textFlow;
   @FXML private Text titleContainer;
   @FXML private Button getFlashCardButton;

   /* the file to be read */
    private ReadFile readFile;
    /* to keep track of where the user is in typing */
    private int currentCharacterIndex = 0;
    /* used for saving the file */
    private File file;


    /**
     * This method gets triggered when the user types a key and once tis;s done it gets that character compares with
     * the current character the user is and if those two matches it shifts to another while changing the opacity of
     * the text to give the experience of being changed
     * @param event
     */
   @FXML
   private void handleKeyTyped(KeyEvent event) {
        if(currentCharacterIndex >= textFlow.getChildren().size()){ return; }

        char typedCharacter = event.getCharacter().charAt(0);
        Text targetCharacter = (Text) textFlow.getChildren().get(currentCharacterIndex);
        char expectedCharacter = targetCharacter.getText().charAt(0);

        if(typedCharacter == expectedCharacter){
            targetCharacter.setFill(Color.GREEN);
            targetCharacter.setOpacity(1.0);
            currentCharacterIndex++;
        }
   }

    /**
     * This method calls the filechooser class tos how the file choosing dialog box where the user can choose a file
     * Then once the file is choosen it sets the file variable to that file and shows in the textflow
     * @param event
     * @throws FileNotFoundException
     */
    @FXML
    private void showFileExplorer(ActionEvent event) throws FileNotFoundException {
        Stage  stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       FileChooserComponent dialogBox = new FileChooserComponent("choose", "Choose a file", stage);
       FileChooser fileChooser = dialogBox.getChooser();
       this.file = dialogBox.showDialogBox(fileChooser);

       if (this.file != null){
           this.readFile = new ReadFile(this.file);
           writeInTextFlow();
           getFlashCardButton.setDisable(false);
       }
       newNote.getScene().getRoot().requestFocus();
    }


    /**
     * When new Note button is clicked it changes the screen to Note screen
     * @param event
     * @throws IOException
     */
    @FXML
    public void newNoteHandler(ActionEvent event) throws IOException {
       Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("NotesTyping.fxml")));
       Stage stage = (Stage) newNote.getScene().getWindow();
       stage.setScene(new Scene(root));
    }

    /**
     * When the user finishes and clicks the get next section button it calls the write in textflow method which
     * changes to the next section
     * @param event
     * @throws FileNotFoundException
     */
    @FXML
    public void getNextSection(ActionEvent event) throws FileNotFoundException {
       writeInTextFlow();
        newNote.getScene().getRoot().requestFocus();
        currentCharacterIndex = 0;
    }

    /**
     * This method changes the text in UI and then gets the next section from the readfile instance which then is
     * updated in the UI
     */
    private void writeInTextFlow(){
        titleContainer.setText("");
        textFlow.getChildren().clear();
        if(this.readFile == null) return;
        String[] sections = this.readFile.getSection();
        if(sections == null) return;
        String content = sections[1];
        titleContainer.setText(sections[0]);
        for(char c: content.toCharArray()){
            Text t = new Text(String.valueOf(c));
            t.setFont(new Font("Arial", 45));
            t.setFill(Color.BLACK);
            t.setOpacity(0.5);
            this.textFlow.getChildren().add(t);
        }
    }

    /**
     * The user can travel to flashCard window from this window where it will generate the flashCards of the current
     * file
     * @param event
     * @throws IOException
     */
    @FXML
    private void flashCardWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("FlashCardsView.fxml")));
        Parent root = loader.load();

        FlashCardView cardController = loader.getController();
        cardController.initialData(this.file);


        Stage stage = (Stage) newNote.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    /**
     * Navigation back to home screen
     * @param event
     */
    @FXML private void handleGoBack(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("HomeScreen.fxml")));
            Stage stage = (Stage) newNote.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

