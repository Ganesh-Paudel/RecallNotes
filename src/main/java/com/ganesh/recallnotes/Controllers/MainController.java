package com.ganesh.recallnotes.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
import java.net.URL;
import java.util.ResourceBundle;


public class MainController implements Initializable {
   @FXML
   private HBox feelingsBox;
   @FXML private Text greetingText;
   @FXML private Button chooseFileButton;
   @FXML private Canvas noteCanvas;



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
    private void showFileExplorer(ActionEvent event){
       FileChooser fileChooser = new FileChooser();
       fileChooser.setTitle("Open Notes File");
       fileChooser.getExtensionFilters().add(
               new FileChooser.ExtensionFilter("Text Files", "*.txt")
       );

       fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Documents"));

       Stage  stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
       File file = fileChooser.showOpenDialog(stage);

       if (file != null){
           chooseFileButton.setText(file.getName());
           System.out.println(file.getAbsolutePath());
       }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GraphicsContext gc = noteCanvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.setFont(new Font("JetBrains Mono", 30));
        gc.fillText("Ganesh Notes", 100, 100);
    }
}

