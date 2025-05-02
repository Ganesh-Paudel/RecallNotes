package com.ganesh.recallnotes.Controllers;

import com.ganesh.recallnotes.Components.AlertBox;
import com.ganesh.recallnotes.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

/**
 * Controller for the window timer
 */
public class Timer {

    @FXML private Text hoursTime;
    @FXML private Text minutesTime;
    @FXML private Text secondsTime;
    @FXML private Button startPauseButton;
    @FXML private Button breakTimeButton;
    @FXML private Button stopTimeButton;
    @FXML private Pane breakOverPane;

    /* thread for the time */
    private Thread clockThread;
    /* to keep track if the timer is running or not and the thread */
    private boolean running = true;

    /* store the current time and also isBreak is updated if the user asks for the break */
    private int second = 0;
    private int minute = 0;
    private int hours = 0;
    private boolean isBreak = false;


    /**
     * navigation from the timer to homescreen
     */
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

    /**
     * This sets the timer to start and also makes necessary changes to the UI
     */
    @FXML
    private void startPauseHandler(){
        if(breakTimeButton.isDisabled()){
            breakTimeButton.setDisable(false);
        }
        if(breakTimeButton.getText().equals("End Break")){
            breakTimeButton.setText("Break");
        }
        if(breakOverPane.isVisible()){
            breakOverPane.setVisible(false);
        }
        if(stopTimeButton.isDisabled() && startPauseButton.isDisabled()){
            stopTimeButton.setDisable(false);
            startPauseButton.setDisable(false);
        }
//        System.out.println("start Button clicked");
        boolean pause = startPauseButton.getText().equals("Pause");
        if(!isBreak){
            startPauseButton.setText(pause ? "Start" : "Pause");
            running = !pause;
            startPauseTime(pause);
        }

    }

    /**
     * Whenever the user wants the break and asks for it then this methods asks for the time of break and starts the
     * timer now decreasing
     */
    @FXML
    private void breakTimeHandler(){
        if(breakTimeButton.getText().equals("Break")){
            running = false;
            String title = "Break Time";
            String headerText = "How long do you want to take a break";
            String contentText = "5, 10, 15";
            AlertBox alertBox = new AlertBox("confirmation", title, headerText, contentText);
            alertBox.addButtons("05", "10", "15");

            Optional<ButtonType> result = alertBox.getResult();
            if(result.isPresent()){
                if(result.get().getText().equals("05")){
                    startBreakTime(1);
                } else if(result.get().getText().equals("10")){
                    startBreakTime(10);
                } else if(result.get().getText().equals("15")){
                    startBreakTime(15);
                }
            }
            breakTimeButton.setText("End Break");
            startPauseButton.setText("Start");
            startPauseButton.setDisable(true);
            stopTimeButton.setDisable(true);

        } else if(breakTimeButton.getText().equals("End Break")){
            running = false;
            stopTime();
            breakTimeButton.setText("Break");
            isBreak = false;
            startPauseButton.setDisable(false);
            stopTimeButton.setDisable(false);
        }




    }

    /**
     * when the user stops the timer then the runnign is set to false which stops the ui and variable change and
     * calls stoptime method
     */
    @FXML
    private void stopTimeHandler(){
        running = false;
        stopTime();
    }

    /**
     * This method resets the UI and changes it to the original value
     */
    private void stopTime(){

//        breakTimeButton.setText("Break");
        startPauseButton.setText("Start");
        secondsTime.setText("00");
        minutesTime.setText("00");
        hoursTime.setText("00");
        second = 0;
        minute = 0;
        hours = 0;

//        clockThread.interrupt();
    }


    /**
     * This method is where the thread is created and it keeps running and changing the Ui unless there is a break or
     * pause
     * @param pause
     */
    private void startPauseTime(boolean pause){

        clockThread = new Thread(() -> {
            while(running){
                if(!pause){
                    if(!isBreak) {
//                        System.out.println("inside thread getting seconds");
                        increaseTime();
//                        System.out.println(second);

                    } else{
//                        System.out.println("inside thread getting seconds");
                        decreaseTime();
                    }
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

        /* this ensures when the user closes the window the thread will also end instead of going on */
        clockThread.setDaemon(true);
        clockThread.start();
    }

    /**
     * increases the time and updates the variable
     */
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

    /**
     * decreases the time and updates teh variables
     */
    private void decreaseTime(){
        second--;
        if(second <= 0 && minute <= 0 && hours <= 0){
            isBreak = false;
            running = false;
            stopTime();
            setBreakTimePaneVisible();
            return;
        }
        if(second <= 0){
            second = 59;
            minute--;
        }
        if(minute < 0){
            second = 59;
            minute = 0;
            hours--;
            if(hours <= 0){
                hours = 0;
            }
        }

    }

    /**
     * There is a panel which is shown when the break is over this is called once it's over and it shows the panel
     */
    private void setBreakTimePaneVisible(){
        breakOverPane.setVisible(true);
    }

    /**
     * When displaying the 0 alone looks not that good and to have 00 be shown this method returns the time as string
     * int 00 01 format if necessary
     * @param time the time as integers any minute hours or second
     * @return
     */
    private String getTimeText(int time){
        return (""+time).length() < 2 ? "0"+time : ""+time;
    }

    /**
     * this method starts the break time with changing the variables with the correct break time provided by the user
     * @param time
     */
    private void startBreakTime(int time){
        this.isBreak = true;
        this.second = 0;
        this.minute = time;
        this.running = true;
        startPauseTime(false);
    }


}
