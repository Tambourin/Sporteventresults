/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewControllers;

import domain.Event;
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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Olavi
 */
public class MainViewController implements Initializable {

    private Event selectedEvent;
//    @FXML
//    AnchorPane menuPane;
    @FXML
    AnchorPane anchorPane;

    

    
    @FXML
    Button eventViewButton;
    @FXML
    Button startingListButton;
    @FXML
    Button raceButton;
    @FXML
    Button resultsButton;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        
        
        eventViewButton.setOnAction(event -> {
            loadContestView();
        });
        startingListButton.setOnAction(event -> {
            loadListParticipantsView();
        });
        raceButton.setOnAction(event -> {
            loadRaceView();
        });
        resultsButton.setOnAction(event -> {
            loadResultsView();
        });        
        
    }    
    /**
     * Sets Selected event. This method is called when this view is loaded
     * from another view.
     * @param event Event that is assigned to selectedEvent
     */
    public void setSelectedEvent(Event event) {
        this.selectedEvent = event;
        System.out.println("Main, Selected event is:" + this.selectedEvent);
        loadContestView(); // ContestView is loaded when user enters MainView
    }
    
    private void loadContestView(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/EventView.fxml"));
            Parent main = fxmlLoader.load();
            fxmlLoader.<EventViewController>getController().setSelectedEvent(this.selectedEvent);
            anchorPane.getChildren().setAll(main);
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadListParticipantsView(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ListParticipantsView.fxml"));    
            Parent main = fxmlLoader.load();
            fxmlLoader.<ListParticipantsViewController>getController().setSelectedEvent(this.selectedEvent);
            anchorPane.getChildren().setAll(main);
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadRaceView(){
        try {   
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/RaceView.fxml"));    
            Parent main = fxmlLoader.load();
            fxmlLoader.<RaceViewController>getController().setSelectedEvent(this.selectedEvent);
            anchorPane.getChildren().setAll(main);
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadResultsView(){
        try {        
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ResultsView.fxml"));    
            Parent main = fxmlLoader.load();
            fxmlLoader.<ResultsViewController>getController().setSelectedEvent(this.selectedEvent);
            anchorPane.getChildren().setAll(main);
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
