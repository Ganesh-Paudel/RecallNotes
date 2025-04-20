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
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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


public class MainController{
   @FXML
   private HBox feelingsBox;
   @FXML private Text greetingText;
   @FXML private Button newNote;
   @FXML private TextFlow textFlow;
   @FXML private Text titleContainer;

    private ReadFile readFile;
    private int currentCharacterIndex = 0;


    @FXML
    private void feelingsGiven(ActionEvent event) {
       Button clickedButton = (Button) event.getSource();
       String id = clickedButton.getId();

       switch (id){
           case "happy":

                greetingText.setText("Happy");
                break;
           case "veryHappy":
               greetingText.setText("Very Happy");
               break;
           case "veryVeryHappy":
               greetingText.setText("Very Very Happy");
               break;
           case "extremelyHappy":
               greetingText.setText("Extremely Happy");
               break;
           default:
               greetingText.setText("No feelings");
       }
//       greetingText.setFont(new Font("JetBrains Mono", 50));
       feelingsBox.getChildren().clear();
       feelingsBox.setVisible(false);


   }

   @FXML
   private void handleKeyTyped(KeyEvent event) {
        if(currentCharacterIndex >= textFlow.getChildren().size()){ return; }

        char typedCharacter = event.getCharacter().charAt(0);
        Text targetCharacter = (Text) textFlow.getChildren().get(currentCharacterIndex);
        char expectedCharacter = targetCharacter.getText().charAt(0);

        if(typedCharacter == expectedCharacter){
            targetCharacter.setOpacity(1.0);
            currentCharacterIndex++;
        }
   }

    @FXML
    private void showFileExplorer(ActionEvent event) throws FileNotFoundException {
        Stage  stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       FileChooserComponent dialogBox = new FileChooserComponent("choose", "Choose a file", stage);
       FileChooser fileChooser = dialogBox.getChooser();
       File file = dialogBox.showDialogBox(fileChooser);

       if (file != null){
           this.readFile = new ReadFile(file);
           writeInTextFlow();
       }
       newNote.getScene().getRoot().requestFocus();
    }


    @FXML
    public void newNoteHandler(ActionEvent event) throws IOException {
       Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("NotesTyping.fxml")));
       Stage stage = (Stage) newNote.getScene().getWindow();
       stage.setScene(new Scene(root));
    }

    @FXML
    public void getNextSection(ActionEvent event) throws FileNotFoundException {
       String[] sections = this.readFile.getSection();
       if(sections != null){
           System.out.println(sections[0]);
           System.out.println(sections[1]);
       }
    }

    private void writeInTextFlow(){
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


}

