package com.ganesh.recallnotes.Controllers;

import com.ganesh.recallnotes.Components.FileChooserComponent;
import com.ganesh.recallnotes.Controllers.independentComponents.SingleCard;
import com.ganesh.recallnotes.FileHandling.ReadFile;
import com.ganesh.recallnotes.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class FlashCardView {

    /**
     * private class that represents a section with title and content. Easier to store and access later when need to
     * display and access
     */
    private class Section {
        String title;
        String content;

        public Section (String title, String content){
            this.title = title;
            this.content = content;
        }
    }

    @FXML private FlowPane flowFlashCard;
    @FXML private Text flashCardsTitle;
    @FXML private Pane bigCardPane;
    @FXML private Text bigCardTitle;
    @FXML private TextFlow bigCardTextFlow;

    /* File provided by the user*/
    private File file;
    /* List of sections in the choosen file by the user*/
    private ArrayList<Section> sections = new ArrayList<>();
    /* to keep track of the section so it makes easier when switching between sections */
    private int currentSection;


    /**
     * Sets the file to the given file by the user and calls addCards which adds them to the flowpane
     * @param file file which content will be read (must be the one created using this app)
     */
    public void initialData(File file){
        this.file = file;
        System.out.println("In the initialData method");
        System.out.println(file.getAbsolutePath());
        String filename = file.getName().substring(0, file.getName().lastIndexOf("."));
        flashCardsTitle.setText(filename);
        addCards();
    }


    /**
     * Goes through the file and for each of the section that is returned from the readfile class which reads each
     * section then creates fxml element for each file and adds them to the flowpane and sections which later is used
     * for switching the sections
     */
    private void addCards(){
        try {
            ReadFile readFile = new ReadFile(file);
            String[] section;
            int id = 0;
            while ((section = readFile.getSection()) != null) {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("independentComponents/FlashCard.fxml"));
                SingleCard singleCardController = new SingleCard(id, this);
                loader.setController(singleCardController);
                Pane cardPane = loader.load();
                singleCardController.setTitle(section[0]);
                singleCardController.setContent(section[1]);
                flowFlashCard.getChildren().add(cardPane);

                sections.add(new Section(section[0], section[1]));
                id++;
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }  catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * This methods reveals the ui elements that are not visible; calling this method triggers the flashCards to be a
     * big view mode where user can see the flashCards even closer
     * @param cardId the id of the card(not used right now)
     * @param cardController the controller so it's easier to get the content of the card
     */
    public void showInvisible(int cardId, SingleCard cardController){
        System.out.println("In showInvisible method");
        System.out.println(cardId);
        bigCardTitle.setText(cardController.getTitle());
        Text text = new Text(cardController.getContent());
        bigCardTextFlow.getChildren().clear();
        bigCardTextFlow.getChildren().add(text);

        bigCardPane.setVisible(true);
    }

    /**
     * button which changes the section to previous section gets triggered if the invisible ui is shown
     * @param event
     */
    @FXML
    private void getPreviousCardHandler(ActionEvent event){
        int section = currentSection - 1 >= 0 ? --currentSection : 0;
        showCard(section);
    }

    /**
     * changes the section to the next section only if possible can only be triggered if the invisible ui is visible
     * @param event
     */
    @FXML
    private void getNextCardHandler(ActionEvent event){
        int section = currentSection + 1 < this.sections.size() ? ++currentSection : this.sections.size() - 1;
        showCard(section);
    }

    /**
     * Checks if the given section is within the range of total sections if it is then it changes the ui elements value
     * @param section
     */
    private void showCard(int section){
        if(section >= 0 && section < this.sections.size()){
            bigCardTitle.setText(sections.get(section).title);
            bigCardTextFlow.getChildren().clear();
            bigCardTextFlow.getChildren().add(new Text(sections.get(section).content));

        }
    }

    /**
     * Gets triggered when the button close is clicked and sets the ui to not visible
     * @param event
     */
    @FXML
    private void handleClosingBigCard(ActionEvent event){
        bigCardPane.setVisible(false);
    }

    /**
     * Gets triggered when the goBack button is clicked and changes the scene to the homeScreen one
     * @param event
     */
    @FXML private void handleGoBack(ActionEvent event){
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("HomeScreen.fxml")));
            Stage stage = (Stage) bigCardTitle.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * if the user haven't provided the file then on clicking the button this method get triggered and asks for the file
     * @param event
     */
    @FXML private void askForFile(ActionEvent event){
        Stage  stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FileChooserComponent dialogBox = new FileChooserComponent("choose", "Choose a file", stage);
        FileChooser fileChooser = dialogBox.getChooser();
        this.file = dialogBox.showDialogBox(fileChooser);

        if (this.file != null){
            addCards();
        }
        bigCardPane.getScene().getRoot().requestFocus();
    }



}
