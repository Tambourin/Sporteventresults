
package viewControllers;

import dao.ParticipantDao;
import dao.ParticipantDaoJdbc;
import domain.Contest;
import domain.Participant;
import java.net.URL;
import java.time.Duration;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;

/**
 * FXML Controller class
 *
 * @author Olavi
 */
public class RaceViewController implements Initializable {
    
    ParticipantDao participantDao;

    @FXML
    private TextField bidNumberField;
    @FXML
    private TextField timeField;
    @FXML
    private Button addButton;
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
    private Button removeFromFinished;
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
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        participantDao = new ParticipantDaoJdbc();
        
        populateTables();
        
        addButton.setOnAction(event -> addFinished());
        timeField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                addFinished();
                bidNumberField.requestFocus();
            }
        });
        removeFromFinished.setOnAction(event -> {
            Participant participant = finishedTable.getSelectionModel().getSelectedItem();
            if (promptRemoveParticipant(participant)) {
                participant.setRaceResult(Duration.ZERO);
                participantDao.update(participant);
                populateTables();
            }
        });
        
    }    
    
    private void addFinished() {
        if (!fieldsAreValid()) {
            return;
        }        
        Integer bidNumber = Integer.parseInt(bidNumberField.getText());
        Participant participant = participantDao.findByBidNumber(bidNumber);
        if (participant == null) {
            showErrorDialog("Kilpailunumero ei ole käytössä!");
        } else if(participant.getRaceResult() != Duration.ZERO) {
            showErrorDialog("Kilpailunumero on jo maalissa!");                
        } else {
            participant.setRaceResult(parseDuration(timeField.getText()));
            participantDao.update(participant);
            populateTables();
            bidNumberField.setText("");
            timeField.setText("");
        }
    }
    
    private void populateTables() {
        ObservableList<Participant> notFinished = FXCollections.observableArrayList();
        ObservableList<Participant> finished = FXCollections.observableArrayList();
        participantDao.listAll().forEach(participant -> {
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
        //System.out.println(notFinished.size());
    }
    
    //Parses a string to Duration
    private Duration parseDuration(String original) {
        String parsed = "PT";
        String[] parts = original.split(":");
        if (parts.length == 3) {
            parsed += parts[0] + "H" + parts[1] + "M" + parts[2] + "S";
        } else if (parts.length == 2) {
            parsed += parts[0] + "M" + parts[1] + "S";
        } else {
            return null;
        }      
        System.out.println(parsed);
        try {
            return Duration.parse(parsed);
        } catch (Exception e){
            showErrorDialog("parseDuration: Could not parse duration!");
            return null;
        }        
    }
    
    //Ask user if he/she really wants to remove a participant from finished participants list 
    public boolean promptRemoveParticipant(Participant participant) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Huom!");
        alert.setHeaderText("Haluatko varmasti poistaa osanottajan " 
                + participant.getFirstName() + " "
                + participant.getLastName() + " " 
                + participant.getBidNumber() + " " 
                + "maaliintulleista?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {            
            return true;
        }  
        return false;
    }
    
    private boolean fieldsAreValid(){
        String bidNumber = bidNumberField.getText();
        String time = timeField.getText();
        if (bidNumber == null || bidNumber.isEmpty()) {
            return false;
        } else if (!bidNumber.matches("[0-9]+")) {
            showErrorDialog("Tarkista kilpailunumero!");
            return false;
        } else if (time == null || time.isEmpty()) {
            return false;
        } else if (!time.matches("[0-9]{1,2}:[0-9]{1,2}") 
                && !time.matches("[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}")) {
            showErrorDialog("Tarkista aika!");
            return false;                  
        }       
        
        return true;
    }
    
    private void showErrorDialog(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Huom!");
        alert.setHeaderText(errorMessage);
        alert.showAndWait();
    }
}
