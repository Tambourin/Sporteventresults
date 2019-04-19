
package viewControllers;

import Services.ParticipantService;
import dao.ContestDao;
import dao.ContestDaoJdbc;
import domain.Contest;
import domain.Participant;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class for SinglePersonView.
 * This is a controller fo a dialog view to edit and save details of specified participant.
 * If no participant is preloaded, dialog shows empty fields and
 * new participant can be created.
 * 
 * @author Olavi
 */
public class SinglePersonViewController implements Initializable {
    
    private final ContestDao contestDao = new ContestDaoJdbc();
    private final ParticipantService participantService = new ParticipantService();
    
    private Participant participant; //Participant that is being edited
    
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
    @FXML
    private Button deleteButton;
    @FXML
    private Button cancelButton;
    /**
     * Initializes the controller class.
     * Sets event handlers to the buttons
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {     
        saveParticipantButton.setOnAction(event -> saveParticipant(event));
        deleteButton.setOnAction(event -> deleteParticipant(event));
        cancelButton.setOnAction(event -> DialogUtil.closeWindow(event));
    }    
    
    /**
     * Fills fields with data.
     * @param participant Participant whose data is used to fill the fields. 
     * If parameter is null, a new participant is created intead.
     */    
    public void populateFields(Participant participant) {
        if (participant == null) {
            participant = new Participant();
        }
        this.participant = participant;        
        contestChoice.getItems().addAll(contestDao.findAll());        
        contestChoice.getSelectionModel().select(participant.getContest()); // Select participant's contest from the choicebox
        bidNumberField.setText(participant.getBidNumber().toString());
        firstNameField.setText(participant.getFirstName());
        lastNameField.setText(participant.getLastName());
        emailField.setText(participant.geteMail());
        phoneField.setText(participant.getPhone());
        addressField.setText(participant.getAddress());
        clubField.setText(participant.getClub());                
    }  
    
    /**
     * Takes content of each field and sends the values update or create new
     * @param event action event. Used to close the right window after task done.
     */
    private void saveParticipant(Event event) {
        if (participantFieldsAreValid()) {
            participantService.save(
                    this.participant.getId(),
                    bidNumberField.getText(),
                    firstNameField.getText(),
                    lastNameField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    addressField.getText(),
                    clubField.getText(),
                    contestChoice.getSelectionModel().getSelectedItem());
            DialogUtil.closeWindow(event);
        }
    }
    
    private void deleteParticipant(Event event) {
        if (DialogUtil.promptDelete(
                    "Haluatko varmasti poistaa osanottajan " 
                            + participant.getFirstName() + " " 
                            + participant.getLastName() + "?")){
                participantService.delete(this.participant);
                DialogUtil.closeWindow(event);
            }
    }
    
    // Check if required fields are filled and have valid content
    private boolean participantFieldsAreValid() {
        String errorMessage = "";        
        if(bidNumberField.getText() == null || 
                bidNumberField.getText().length() == 0){
            errorMessage += "Lähtönumerokenttä on tyhjä ";
        } else if(!bidNumberField.getText().matches("[0-9]+")) {
            errorMessage += "Tarkista lähtönumero ";
        } else if(!Objects.equals(participantService.bidNumberAlreadyInUse(bidNumberField.getText()).getId(), this.participant.getId())
                && participantService.bidNumberAlreadyInUse(bidNumberField.getText()) != null) {
            errorMessage += "Lähtönumero on jo käytössä";
        }
        
        if(firstNameField.getText() == null || 
                firstNameField.getText().length() == 0){
            errorMessage += "Etunimikenttä on tyhjä ";
        }
        if(lastNameField.getText() == null || 
                lastNameField.getText().length() == 0){
            errorMessage += "Sukunimikenttä on tyhjä ";
        }
        Contest selectedContest = 
                contestChoice.getSelectionModel().getSelectedItem();
        if (selectedContest == null) {
            errorMessage += "Valitse sarja! ";
        }        
        
        if (errorMessage.length() == 0) {
            return true;
        }
        
        DialogUtil.showErrorDialog(errorMessage);
        return false;
        
    }    
    
}
