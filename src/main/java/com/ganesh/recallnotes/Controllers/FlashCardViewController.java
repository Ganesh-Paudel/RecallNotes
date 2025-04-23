package com.ganesh.recallnotes.Controllers;

import com.ganesh.recallnotes.Controllers.independentComponents.SingleCardController;
import com.ganesh.recallnotes.FileHandling.ReadFile;
import com.ganesh.recallnotes.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class FlashCardViewController{

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
    @FXML private Button previousCardButton;
    @FXML private Button nextCardButton;

    private File file;
    private ArrayList<Section> sections = new ArrayList<>();
    private int currentSection;



    public void initialData(File file){
        this.file = file;
        System.out.println("In the initialData method");
        System.out.println(file.getAbsolutePath());
        String filename = file.getName().substring(0, file.getName().lastIndexOf("."));
        flashCardsTitle.setText(filename);
        addCards();
    }

    private void addCards(){
        try {
            ReadFile readFile = new ReadFile(file);
            String[] section;
            int id = 0;
            while ((section = readFile.getSection()) != null) {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("FlashCard.fxml"));
                SingleCardController singleCardController = new SingleCardController(id, this);
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



    public void showInvisible(int cardId, SingleCardController cardController){
        System.out.println("In showInvisible method");
        System.out.println(cardId);
        bigCardTitle.setText(cardController.getTitle());
        Text text = new Text(cardController.getContent());
        bigCardTextFlow.getChildren().clear();
        bigCardTextFlow.getChildren().add(text);

        bigCardPane.setVisible(true);
    }

    @FXML
    private void getPreviousCardHandler(ActionEvent event){
        int section = currentSection - 1 >= 0 ? --currentSection : 0;
        showCard(section);
    }

    @FXML
    private void getNextCardHandler(ActionEvent event){
        int section = currentSection + 1 < this.sections.size() ? ++currentSection : this.sections.size() - 1;
        showCard(section);
    }

    private void showCard(int section){
        if(section >= 0 && section < this.sections.size()){
            bigCardTitle.setText(sections.get(section).title);
            bigCardTextFlow.getChildren().clear();
            bigCardTextFlow.getChildren().add(new Text(sections.get(section).content));

        }
    }

    @FXML
    private void handleClosingBigCard(ActionEvent event){
        bigCardPane.setVisible(false);
    }



}
