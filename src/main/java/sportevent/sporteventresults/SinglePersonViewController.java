
package sportevent.sporteventresults;

import DAO.DBService;
import domain.Contest;
import domain.Participant;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Olavi
 */
public class SinglePersonViewController implements Initializable {

    private final DBService dbService = new DBService();
    
    private Participant participant;
    
    @FXML
    private ChoiceBox<Contest> contestChoice;
    @FXML
    TextField bidNumberField;
    @FXML
    TextField firstNameField;
    @FXML
    TextField lastNameField;
    @FXML
    TextField clubField;
    @FXML
    TextField emailField;
    @FXML
    TextField addressField;
    @FXML
    TextField phoneField;

    
    @FXML    
    private Button saveParticipantButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        
        saveParticipantButton.setOnAction(event -> {            
            System.out.println(contestChoice.getSelectionModel().getSelectedItem());
            dbService.addParticipant(
                    Integer.parseInt(bidNumberField.getText()),
                    firstNameField.getText(),
                    lastNameField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    addressField.getText(),
                    clubField.getText(),
                    contestChoice.getSelectionModel().getSelectedItem());
        });
    }    
    
    /**
     * Fills fields with data
     */    
    public void populateFields() {
        if (participant == null) {
            participant = new Participant();
        }
        ObservableList<Contest> contests = dbService.getContests();
        
        contestChoice.getItems().addAll(contests);
        firstNameField.setText(participant.getFirstName());
        lastNameField.setText(participant.getLastName());
        emailField.setText(participant.geteMail());
        phoneField.setText(participant.getPhone());
        addressField.setText(participant.getAddress());
        clubField.setText(participant.getAddress());
        contestChoice.getSelectionModel().select(participant.getContest());        
    }
    
    public void setParticipant(Participant participant) {
        this.participant = participant;   
        populateFields();
    }
}
