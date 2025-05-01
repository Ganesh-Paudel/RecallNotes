package com.ganesh.recallnotes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    /**
     * The start method from the Application class of Javafx which is the entry point for javafx application
     * @param stage
     * @throws IOException if the given fxml file doesnot exist
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("HomeScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Study Room");
        stage.setScene(scene);
//        stage.setMaximized(true);
//        stage.setResizable(false);
        stage.show();
    }

    /**
     * The entry point of the application
     * @param args cmd args
     */
    public static void main(String[] args) {
        launch();
    }
}