package com.ganesh.recallnotes.Controllers;

import com.ganesh.recallnotes.Components.FileChooserComponent;
import com.ganesh.recallnotes.FileHandling.ReadFile;
import com.ganesh.recallnotes.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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


public class MainController implements Initializable {
   @FXML
   private HBox feelingsBox;
   @FXML private Text greetingText;
   @FXML private Button newNote;
   @FXML private TextFlow textFlow;
   @FXML private Text titleContainer;
   @FXML private Button getFlashCardButton;
   @FXML private TreeView treeView;
   @FXML private Button collapseTreeViewButton;
   @FXML private Button chooseFileButton;

    private ReadFile readFile;
    private int currentCharacterIndex = 0;
    private File file;





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
       this.file = dialogBox.showDialogBox(fileChooser);

       if (this.file != null){
           this.readFile = new ReadFile(this.file);
           writeInTextFlow();
           getFlashCardButton.setDisable(false);
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
       writeInTextFlow();
    }

    private void writeInTextFlow(){
        titleContainer.setText("");
        textFlow.getChildren().clear();
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

    @FXML
    private void flashCardWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Main.class.getResource("FlashCardsView.fxml")));
        Parent root = loader.load();

        FlashCardViewController cardController = loader.getController();
        cardController.initialData(this.file);


        Stage stage = (Stage) newNote.getScene().getWindow();
        stage.setScene(new Scene(root));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(() -> {
            File rootDir;
            if(this.file != null){
                rootDir = this.file;
            } else {
                System.out.println("here");
                rootDir = new File("C:/Users/LENOVO/Desktop/test");
            }

            System.out.println("outside here");
            TreeItem<String> rootItem = createNode(rootDir);
            rootItem.setExpanded(true);
            treeView.setRoot(rootItem);
        });


    }

    private TreeItem<String> createNode(File rootDir){
        TreeItem<String> rootItem = new TreeItem<>(rootDir.getName());
        if(rootDir.isDirectory()){
            File[] files = rootDir.listFiles();
            if(files != null) {
                for(File f: files){
                    rootItem.getChildren().add(createNode(f));
                }
            }
        }

        return rootItem;
    }


    @FXML
    private void handleTreeViewCollapse(ActionEvent event){
        if(collapseTreeViewButton.getText().equals(">>>")){
            treeView.setVisible(true);
            collapseTreeViewButton.setTranslateX(0);
            collapseTreeViewButton.setText("<<<");
            chooseFileButton.setTranslateX(0);
            getFlashCardButton.setTranslateX(0);
            textFlow.setTranslateX(0);
            textFlow.setPrefWidth(662);

        } else if(collapseTreeViewButton.getText().equals("<<<")){
            treeView.setVisible(false);
            collapseTreeViewButton.setTranslateX(-165);
            collapseTreeViewButton.setText(">>>");
            chooseFileButton.setTranslateX(-130);
            getFlashCardButton.setTranslateX(-130);
            textFlow.setTranslateX(-130);
            textFlow.setPrefWidth(790);
        }
    }
}

