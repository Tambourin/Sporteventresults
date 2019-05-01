/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewControllers;

import dao.ContestDao;
import dao.ContestDaoJdbc;
import dao.EventDaoJdbc;
import dao.ParticipantDao;
import dao.ParticipantDaoJdbc;
import domain.Contest;
import domain.Event;
import domain.Participant;
import java.net.URL;
import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;

import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Olavi
 */
public class ResultsViewController implements Initializable {
    ParticipantDao participantDao = new ParticipantDaoJdbc();
    ContestDao contestDao = new ContestDaoJdbc();
    EventDaoJdbc eventDao = new EventDaoJdbc();
    
    private Event selectedEvent;

    @FXML
    private WebView webView;
    @FXML
    private Button printButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        printButton.setOnAction(event -> {        
            PrinterJob job = PrinterJob.createPrinterJob();
            job.showPrintDialog((Stage)((Node)event.getSource()).getScene().getWindow());
            
            if (job != null) {
                boolean success = job.printPage(webView);
                if (success) {
                    job.endJob();
                }
            }
        });
        
    }    
    
    /**
     * Sets Selected event. This method is called when this view is loaded
     * from another view. 
     * @param event Event that is assigned to selectedEvent
     */
    public void setSelectedEvent(Event event) {
        this.selectedEvent = event;
        constructHTML();
    }
    
    private void constructHTML() {
        String message = "";        
        Event event = eventDao.findById(selectedEvent.getId());
        message += "<h1>" + event.getName() + " " + event.getDate() + "</h1> \n";
        message += "<b>" + event.getLocation() + "</b>";
        message += "<p>" + event.getInfo() + "</p>";
        
        List<Contest> contests = contestDao.findAllByEvent(event);
        for (Contest contest : contests) {
            message += "<h2>" + contest.getName() + "</h2>\n";            
            
            List<Participant> participants = participantDao.listByContest(contest);
            participants.removeIf(participant -> participant.getRaceResult().equals(Duration.ZERO));
            
            Collections.sort(participants);            
            for (int i = 0; i < participants.size(); i++) {                
                message += "<p>" + (i + 1)  + ". ";
                message += " " + participants.get(i).getFirstName() + " ";
                message += " " + participants.get(i).getLastName() + " ";
                message += " [" + participants.get(i).getBidNumber() + "] ";
                message += " " + participants.get(i).getRaceResult() + "</p>";                                
            }
        }
        webView.getEngine().loadContent(message);
    }
}
