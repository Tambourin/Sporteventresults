
package viewControllers;

import dao.ContestDao;
import dao.ContestDaoJdbc;
import dao.ParticipantDao;
import dao.ParticipantDaoJdbc;
import domain.Contest;
import domain.Participant;
import java.net.URL;
import java.util.List;
import java.util.Objects;
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
import javafx.event.Event;
import javafx.scene.Node;

/**
 * FXML Controller class for SinglePersonView.
 * This is a controller fo a dialog view to edit and save details of specified participant.
 * If no participant is preloaded, dialog shows empty fields and
 * new participant can be created.
 * 
 * @author Olavi
 */
public class SinglePersonViewController implements Initializable {
    
    private final ParticipantDao participantDao = new ParticipantDaoJdbc();
    private final ContestDao contestDao = new ContestDaoJdbc();
    
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
        //If the partisipant does not have an id attribute(it is not in the database),
        //add a new participant to database otherwise update existing one.
        saveParticipantButton.setOnAction(event -> {            
            if (this.participant.getId() == null) {
                if(addNewParticipant()) {
                    closeWindow(event);
                }
            } else {
                if (updateParticipant()){
                    closeWindow(event);
                }
            }            
        });      
        deleteButton.setOnAction(event -> {
            if (deleteParticipant()){
                closeWindow(event);
            } 
        });
        cancelButton.setOnAction(event -> closeWindow(event));
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
     * 
     * @return Return true if operation was success.
     */
    public boolean updateParticipant() {        
        if(participantFieldsAreValid()) {  
            readFieldsToParticipant();
            participantDao.update(this.participant);
            return true;
        }        
        return false;
    }  
    
    public boolean addNewParticipant() {
        if(participantFieldsAreValid()) {
            readFieldsToParticipant();
            participantDao.create(this.participant);
            return true;
        }        
        return false;
    }
    
    private void readFieldsToParticipant(){
        this.participant.setBidNumber(Integer.parseInt(bidNumberField.getText()));
        this.participant.setFirstName(firstNameField.getText());
        this.participant.setLastName(lastNameField.getText());
        this.participant.seteMail(emailField.getText());
        this.participant.setPhone(phoneField.getText());
        this.participant.setAddress(addressField.getText());
        this.participant.setClub(clubField.getText());
        this.participant.setContest(
                contestChoice.getSelectionModel().getSelectedItem());
    }
    
    /**
     * Opens an alert dialog asking if participant should be deleted or not.
     * @return Returns true if user pressed OK and participant was deleted
     */
    public boolean deleteParticipant() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Huom!");
        alert.setHeaderText("Haluatko varmasti poistaa osanottajan");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            participantDao.delete(this.participant.getId());
            return true;
        }  
        return false;
    }
    
    // Check if required fields are filled and have valid content
    private boolean participantFieldsAreValid() {
        String errorMessage = "";        
        if(bidNumberField.getText() == null || 
                bidNumberField.getText().length() == 0){
            errorMessage += "Lähtönumerokenttä on tyhjä ";
        } else if(!bidNumberField.getText().matches("[0-9]+")) {
            errorMessage += "Tarkista lähtönumero ";
        } else if(bidNumberAlreadyInUse(Integer.parseInt(bidNumberField.getText()))) {
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
    
    /**
     * @return Return true if participants bid number in is already assigned to 
     * anothor participant (in this event.) / TO DO TO DO TODO TODO!!!!!!!!!!!!!!!
     */
    private boolean bidNumberAlreadyInUse(Integer bidNumber){
//       List<Participant> participants = participantDao.listAll(); 
//       for (Participant p : participants) {
//           System.out.println(p.getBidNumber() + " : " + bidNumber);
//           if (Objects.equals(p.getBidNumber(), bidNumber)) {               
//               if (!p.equals(this.participant)){
//                   return true;
//               }
//           }
//       }
//       return false;
       if (participantDao.findByBidNumber(bidNumber) == null) {
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
    
    //Close the window where a closing event was fired.
    private void closeWindow(Event event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    
}
