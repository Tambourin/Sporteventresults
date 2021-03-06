
package viewControllers;

import services.ParticipantService;
import domain.Event;
import domain.Participant;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;

/**
 * FXML Controller class for RaceView. In this view user inputs details of
 * participants who arrive to goal.
 *
 * @author Olavi
 */
public class RaceViewController implements Initializable {
    
    ParticipantService participantService = new ParticipantService();
    private Event selectedEvent;

    @FXML
    private TextField bidNumberField;
    @FXML
    private TextField timeField;
    @FXML
    private Button addButton;
    @FXML
    private Button removeFromFinished;
    @FXML
    private TableView<Participant> finishedTable;
    @FXML
    private TableColumn<Participant, Integer> finishedBibNumberColumn;
    @FXML
    private TableColumn<Participant, Duration> finishedTimeColumn;
    @FXML
    private TableColumn<Participant, String> finishedFirstNameColumn;
    @FXML
    private TableColumn<Participant, String> finishedLastNameColumn;
    @FXML
    private TableView<Participant> notFinishedTable;
    @FXML
    private TableColumn<Participant, Integer> nfBidNumerColumn;
    @FXML
    private TableColumn<Participant, String> nfFirstNameColumn;
    @FXML
    private TableColumn<Participant, String> nfLastNameColumn;
    @FXML
    private TableColumn<Participant, String> nfContestColumn;
    @FXML
    private Label nfCountLabel;

    /**
     * Initializes the controller class. Sets events and populates tables.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
          
        timeField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                addToFinished();
                bidNumberField.requestFocus();
            }
        });
        addButton.setOnAction(event -> addToFinished());
        removeFromFinished.setOnAction(event -> removeFromFinished());        
        finishedTable.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                removeFromFinished();
            } 
        });
        
    }    
    
    /**
     * Sets Selected event. This method should be called when this view is loaded
     * from another view.
     * @param event Event that is assigned to selectedEvent
     */
    public void setSelectedEvent(Event event) {
        this.selectedEvent = event;
        populateTables();
    }
    
    private void populateTables() {
        ObservableList<Participant> notFinished = FXCollections.observableArrayList();
        ObservableList<Participant> finished = FXCollections.observableArrayList();
         
        participantService.findByEvent(selectedEvent).forEach(participant -> {
            if (participant.getRaceResult().isZero()) {
                notFinished.add(participant);
            } else {
                finished.add(participant);
            }
        });
        
        nfBidNumerColumn.setCellValueFactory(new PropertyValueFactory<>("bidNumber"));
        nfFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        nfLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        nfContestColumn.setCellValueFactory(new PropertyValueFactory<>("contest"));        
        notFinishedTable.setItems(notFinished);
        
        finishedBibNumberColumn.setCellValueFactory(new PropertyValueFactory<>("bidNumber"));
        finishedTimeColumn.setCellValueFactory(new PropertyValueFactory<>("raceResult"));
        finishedFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        finishedLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        finishedTable.setItems(finished);
        finishedTable.getSortOrder().add(finishedTimeColumn);
        
        nfCountLabel.setText("Yhteensä saapumatta: " + notFinished.size());
    }
    
    private void addToFinished() {
        if (!fieldsAreValid()) {
            return;
        }     
        Participant participant = 
                participantService.findByNameOrNumber(bidNumberField.getText(), selectedEvent).get(0);
        if (participant == null) {
            DialogUtil.showErrorDialog("Kilpailunumero ei ole käytössä!");
        } else if(participant.getRaceResult() != Duration.ZERO) {
            DialogUtil.showErrorDialog("Kilpailija on jo maalissa!");                
        } else {
            participantService.addToFinished(participant, timeField.getText());
            populateTables();
            bidNumberField.setText("");
            timeField.setText("");
        }
    }
    
    private void removeFromFinished() {
        Participant participant = finishedTable.getSelectionModel().getSelectedItem();
        if (participant == null) {
            DialogUtil.showErrorDialog("Ei valittua osanottajaa");
            return;
        }
        String message = "Haluatko varmasti poistaa osanottajan " 
            + participant.getFirstName() + " "
            + participant.getLastName() + " " 
            + participant.getBidNumber() + " " 
            + "maaliintulleista?";
        if (DialogUtil.promptDelete(message)) {
            participant.setRaceResult(Duration.ZERO);
            participantService.update(participant);
            populateTables();
        }
    }
    
    private boolean fieldsAreValid(){
        String bidNumber = bidNumberField.getText();
        String time = timeField.getText();
        if (bidNumber == null || bidNumber.isEmpty()) {
            return false;
        } else if (!bidNumber.matches("[0-9]+")) {
            DialogUtil.showErrorDialog("Tarkista kilpailunumero!");
            return false;
        } else if (time == null || time.isEmpty()) {
            return false;
        } else if (!time.matches("[0-9]{1,2}:[0-9]{1,2}") 
                && !time.matches("[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}")) {
            DialogUtil.showErrorDialog("Tarkista aika!");
            return false;                  
        }       
        return true;
    }    
 
    
}
