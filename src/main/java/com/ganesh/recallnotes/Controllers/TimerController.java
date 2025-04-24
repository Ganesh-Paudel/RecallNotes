package com.ganesh.recallnotes.Controllers;

import com.ganesh.recallnotes.Components.AlertBox;
import com.ganesh.recallnotes.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;

public class TimerController {

    @FXML private Text hoursTime;
    @FXML private Text minutesTime;
    @FXML private Text secondsTime;
    @FXML private Button startPauseButton;

    private Thread clockThread;
    private boolean running = true;

    private int second = 0;
    private int minute = 0;
    private int hours = 0;

    @FXML
    private void handleGoBack(){
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("HomeScreen.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) startPauseButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    @FXML
    private void startPauseHandler(){

        System.out.println("start Button clicked");
        boolean pause = startPauseButton.getText().equals("Pause");
        startPauseButton.setText(pause ? "Start" : "Pause");
        running = !pause;
        startPauseTime(pause);

    }

    @FXML
    private void breakTimeHandler(){
        String title = "Break Time";
        String headerText = "How long do you want to take a break";
        String contentText = "5, 10, 15";
        AlertBox alertBox = new AlertBox("confirmation", title, headerText, contentText);
        alertBox.addButtons("05", "10", "15");

        Optional<ButtonType> result = alertBox.getResult();
        if(result.isPresent()){
            if(result.get().getText().equals("05")){
                System.out.println("break time of 5 minutes");
            } else if(result.get().getText().equals("10")){
                System.out.println("break time of 10 minutes");
            } else if(result.get().getText().equals("15")){
                System.out.println("break time of 15 minutes");
            }
        }


        running = false;
        startPauseButton.setText("Start");


    }

    @FXML
    private void stopTimeHandler(){
        running = false;
        secondsTime.setText("00");
        minutesTime.setText("00");
        hoursTime.setText("00");
        second = 0;
        minute = 0;
        hours = 0;

        clockThread.interrupt();
    }


    private void startPauseTime(boolean pause){

        clockThread = new Thread(() -> {
            while(running){
                if(!pause){
                    System.out.println("inside thread getting seconds");
                    increaseTime();
                    System.out.println(second);
                    Platform.runLater(() -> {
                        secondsTime.setText(getTimeText(second));
                        minutesTime.setText(getTimeText(minute));
                        hoursTime.setText(getTimeText(hours));
                    });
                }

                try{
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });

        clockThread.setDaemon(true);
        clockThread.start();
    }

    private void increaseTime(){
        second++;
        if(second == 60){
            minute++;
            second = 0;
        }
        if(minute == 60){
            hours++;
            minute = 0;
        }
    }

    private String getTimeText(int time){
        return (""+time).length() < 2 ? "0"+time : ""+time;
    }


}
