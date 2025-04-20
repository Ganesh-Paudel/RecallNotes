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
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class MainController implements Initializable {
   @FXML
   private HBox feelingsBox;
   @FXML private Text greetingText;
   @FXML private Button nextSectionButton;
   @FXML private Canvas noteCanvas;
   @FXML private Button newNote;
    private ReadFile readFile;

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
    private void showFileExplorer(ActionEvent event) throws FileNotFoundException {
        Stage  stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       FileChooserComponent dialogBox = new FileChooserComponent("choose", "Choose a file", stage);
       FileChooser fileChooser = dialogBox.getChooser();
       File file = dialogBox.showDialogBox(fileChooser);

       if (file != null){
           this.readFile = new ReadFile(file);
           String[] sections = this.readFile.getSection();
           System.out.println(sections[0]);
           System.out.println(sections[1]);
       }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GraphicsContext gc = noteCanvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("JetBrains Mono", 30));
        gc.fillText("Ganesh Notes", 100, 100);
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
}

