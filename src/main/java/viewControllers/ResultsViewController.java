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
import java.util.Collections;

import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
            //Printer printer = Printer.getDefaultPrinter();
            //PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
            PrinterJob job = PrinterJob.createPrinterJob();
            job.showPrintDialog((Stage)((Node)event.getSource()).getScene().getWindow());
            
            if (job != null) {
                boolean success = job.printPage(webView);
                if (success) {
                    job.endJob();
                }
            }
        });
        
        String message = "";        
        Event event = eventDao.findById(1);
        message += "<h1>" + event.getName() + " " + event.getDate() + "</h1> \n";
        message += "<b>" + event.getLocation() + "</b>";
        
        List<Contest> contests = contestDao.findAll();
        for (Contest contest : contests) {
            message += "<h2>" + contest.getName() + "</h2>\n";            
            List<Participant> participants = participantDao.listByContest(contest);
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
