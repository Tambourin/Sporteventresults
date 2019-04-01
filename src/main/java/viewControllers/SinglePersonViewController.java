
package ViewControllers;

import DAO.DBService;
import domain.Contest;
import domain.Participant;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import sun.security.krb5.internal.crypto.Des3;
/**
 * FXML Controller class for SinglePersonView.
 * This view is a dialog to edit and save details of specified participant.
 * If no participant is preloaded, dialog shows empty fields and
 * new participant can be created.
 * 
 * @author Olavi
 */
public class SinglePersonViewController implements Initializable {

    private DBService dbService;
    
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
     * Sets event handler to the buttons
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {    
        dbService = new DBService();
        saveParticipantButton.setOnAction(event -> {
            //If the partisipant does not have an id attribute(it is not in the database),
            //add new participant to database otherwise update existing
            if (this.participant.getId() == null) {
                addNewParticipant();
            } else {
                updateParticipant();
            }});          
        
        deleteButton.setOnAction(event -> deleteParticipant());
        cancelButton.setOnAction(event -> closeWindow());
        
    }    
    
    /**
     * Fills fields with data
     * @param participant Participant whose data is used to fill the fields. 
     * If parameter is null, a new participant is created intead.
     */    
    public void populateFields(Participant participant) {
        if (participant == null) {
            participant = new Participant();
        }
        this.participant = participant;
        
        contestChoice.getItems().addAll(dbService.getContests());        
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
     * 
     */
    public void updateParticipant() {
        if(participantDataIsValid()) {
           dbService.updateParticipant(
            this.participant.getId(),
            Integer.parseInt(bidNumberField.getText()),
            firstNameField.getText(),
            lastNameField.getText(),
            emailField.getText(),
            phoneField.getText(),
            addressField.getText(),
            clubField.getText(),
            contestChoice.getSelectionModel().getSelectedItem());        
        closeWindow(); 
        }        
    }
    
    public void addNewParticipant() {
        if(participantDataIsValid()) {
            dbService.addParticipant(
                Integer.parseInt(bidNumberField.getText()),
                firstNameField.getText(),
                lastNameField.getText(),
                emailField.getText(),
                phoneField.getText(),
                addressField.getText(),
                clubField.getText(),
                contestChoice.getSelectionModel().getSelectedItem());
            closeWindow();
        }
        
    }
    
    /**
     * Opens an alert dialog if participant should be deleted or not. Deletes
     * participant from database if user approves.
     */
    public void deleteParticipant() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Huom!");
        alert.setHeaderText("Haluatko varmasti poistaa osanottajan");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            dbService.deleteParticipant(this.participant);
            closeWindow();
        }  
    }
    
    // Check if required fields are filled and have valid content
    private boolean participantDataIsValid() {
        String errorMessage = "";
        if(bidNumberField.getText() == null || 
                bidNumberField.getText().length() == 0){
            errorMessage += "Lähtönumerokenttä on tyhjä ";
        } else if(!bidNumberField.getText().matches("[0-9]+")) {
            errorMessage += "Tarkista lähtönumero ";
        } else if(bidNumberAlreadyInUse()) {
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
        
        showErrorDialog(errorMessage);
        return false;
        
    }
    
   private boolean bidNumberAlreadyInUse(){
       List<Participant> participants = dbService.getParticipants();
       int bidNumber = Integer.valueOf(bidNumberField.getText());
       for (Participant p : participants) {
           if (p.getBidNumber() == bidNumber) {
               if (!p.equals(this.participant)){
                   return true;
               }
           }
       }
       return false;
   }
    
    private void showErrorDialog(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Huom!");
        alert.setHeaderText(errorMessage);
        alert.showAndWait();
    }
    
    private void closeWindow() {
        Stage stage = (Stage)saveParticipantButton.getScene().getWindow();
        stage.close();
    }
    
}
