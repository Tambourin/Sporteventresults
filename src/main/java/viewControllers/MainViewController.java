/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewControllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Olavi
 */
public class MainViewController implements Initializable {


    @FXML
    AnchorPane menuPane;
    @FXML
    AnchorPane anchorPane1;
    @FXML
    AnchorPane anchorPane2;
    @FXML
    AnchorPane anchorPane3;
    @FXML
    AnchorPane anchorPane4;
    
//    @FXML
//    Button startingListButton;
//    @FXML
//    Button contestsButton;
//    @FXML
//    Button raceButton;
//    @FXML
//    Button resultsButton;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        //loadSinglePersonView();
        loadContestView();
        loadListParticipantsView();
        loadRaceView();
        loadResultsView();
//        contestsButton.setOnAction(event -> {
//            loadContestView();
//        });
//        startingListButton.setOnAction(event -> {
//            loadListParticipantsView();
//        });
//        raceButton.setOnAction(event -> {
//            loadRaceView();
//        });
//        resultsButton.setOnAction(event -> {
//            loadResultsView();
//        });
    }    
    
    private void loadContestView(){
        try {
            Parent main = FXMLLoader.load(getClass().getResource("/fxml/EventView.fxml"));          
            anchorPane1.getChildren().setAll(main);
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadListParticipantsView(){
        try {
            Parent main = FXMLLoader.load(getClass().getResource("/fxml/ListParticipantsView.fxml"));          
            anchorPane2.getChildren().setAll(main);
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadRaceView(){
        try {
            Parent main = FXMLLoader.load(getClass().getResource("/fxml/RaceView.fxml"));          
            anchorPane3.getChildren().setAll(main);
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadResultsView(){
        try {
            Parent main = FXMLLoader.load(getClass().getResource("/fxml/ResultsView.fxml"));          
            anchorPane4.getChildren().setAll(main);
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
