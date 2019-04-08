
package viewControllers;

import Services.ParticipantService;
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
 * FXML Controller class for RaceView
 *
 * @author Olavi
 */
public class RaceViewController implements Initializable {
    
    ParticipantService participantService = new ParticipantService();

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
        populateTables();        
        
        timeField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                addToFinished();
                bidNumberField.requestFocus();
            }
        });
        addButton.setOnAction(event -> addToFinished());
        removeFromFinished.setOnAction(event -> removeFromFinished());
    }    
    
    private void populateTables() {
        ObservableList<Participant> notFinished = FXCollections.observableArrayList();
        ObservableList<Participant> finished = FXCollections.observableArrayList();
        
        participantService.findAll().forEach(participant -> {
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
                participantService.findByNameOrNumber(bidNumberField.getText()).get(0);
        if (participant == null) {
            DialogUtil.showErrorDialog("Kilpailunumero ei ole käytössä!");
        } else if(participant.getRaceResult() != Duration.ZERO) {
            DialogUtil.showErrorDialog("Kilpailija on jo maalissa!");                
        } else {
            Duration duration = participantService.parseDuration(timeField.getText());
            if (duration != null) {
                participant.setRaceResult(duration);
                participantService.update(participant);
                populateTables();
                bidNumberField.setText("");
                timeField.setText("");
            }
        }
    }
    
    private void removeFromFinished() {
        Participant participant = finishedTable.getSelectionModel().getSelectedItem();
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
