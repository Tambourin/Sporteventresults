package viewControllers;


import dao.ContestDao;
import dao.ContestDaoJdbc;
import dao.ParticipantDao;
import dao.ParticipantDaoJdbc;
import domain.Contest;
import domain.Participant;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class to initialize controls and 
 handle user inputs on foundParticipants view.
 * On foundParticipants view foundParticipants from a selected contest are viewed.
 Participants can be edited and deleted. New foundParticipants can be added.
 * @author Olavi
 */
public class ListParticipantsViewController implements Initializable {    
    private final ParticipantDao participantDao = new ParticipantDaoJdbc();
    private final ContestDao contestDao = new ContestDaoJdbc();
    
    @FXML
    private ListView<Contest> contestList;       
    @FXML
    private TableView<Participant> participantTable;
    @FXML
    private TableColumn<Participant, String> bidNumberColumn;
    @FXML
    private TableColumn<Participant, String> firstNameColumn;
    @FXML
    private TableColumn<Participant, String> lastNameColumn;
    @FXML
    private TableColumn<Participant, String> clubColumn;        
    @FXML
    private Button addNewButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button searchButton;
    @FXML
    private TextField searchField;
    /**
     * Initializes the controller class. 
     * Populates tabels and sets event handlers to controls.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateContestList();        
        refreshParticipantTable();
        
        //Repopulate participant table if another contest is selected from the contest list  
        contestList.getSelectionModel().selectedIndexProperty()
                .addListener((obs, oldValue, newValue) -> 
                        populateParticipantTable(
                        participantDao.listByContest(contestList.getSelectionModel().getSelectedItem())));          
        //Double click on the table to open foundParticipants details
        participantTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                showSinglePersonViewWindow(participantTable.getSelectionModel()
                        .getSelectedItem());
            }
        });
        //Open foundParticipants details dialog
        editButton.setOnAction(event
                -> showSinglePersonViewWindow(participantTable.getSelectionModel()
                        .getSelectedItem()));
        //Open foundParticipants details dialog with no data to add a new participant
        addNewButton.setOnAction(event -> showSinglePersonViewWindow(null)); 
        
        deleteButton.setOnAction(event -> {
                Participant participant = 
                        participantTable.getSelectionModel().getSelectedItem();
                if (promptDeleteParticipant(participant)) {
                    participantDao.delete(participant.getId());
                    refreshParticipantTable();
                }
        });
        
        searchButton.setOnAction(event -> 
                populateParticipantTable(search(searchField.getText())));
        searchField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {                
                contestList.getSelectionModel().clearSelection();
                populateParticipantTable(search(searchField.getText()));
            }
        });
    }    
    
    private List<Participant> search(String searchWord){       
        List<Participant> foundParticipants = new ArrayList<>();
        foundParticipants.addAll(participantDao.findByNameLike(searchWord));
        try {
            Participant p = participantDao.findByBidNumber(Integer.parseInt(searchWord));
            foundParticipants.add(p);
        } catch (Exception e) {
            System.out.println("Search: not a number");
        }       
        return foundParticipants;
    }
    
    
    private void refreshParticipantTable() {        
        populateParticipantTable(
                participantDao.listByContest(
                        contestList.getSelectionModel().getSelectedItem()));
    }
    /**
     * Populates ListView with Contest elements from database
     */
    private void populateContestList() {
        ObservableList<Contest> contests = FXCollections.observableArrayList();
        contests.addAll(contestDao.findAll());
        contestList.getItems().addAll(contests);        
        contestList.getSelectionModel().selectFirst();
    }
    
    /**
     * Populates TableView with foundParticipants
     * @param participants Participants to show in table
     */
    private void populateParticipantTable(List<Participant> participants) {
        if (participants == null) {
            return;
        }
        ObservableList<Participant> ObsParticipants = FXCollections.observableArrayList();
        ObsParticipants.addAll(participants);
        bidNumberColumn.setCellValueFactory(new PropertyValueFactory<>("bidNumber"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));                
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        clubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));
        participantTable.setItems(ObsParticipants);  
        participantTable.refresh();
    }
    
    public boolean promptDeleteParticipant(Participant participant) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Huom!");
        alert.setHeaderText("Haluatko varmasti poistaa osanottajan " +
                participant.getFirstName() + " " + participant.getLastName());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {            
            return true;
        }  
        return false;
    }
    
    /**
     * Loads resources, prepares and launches a SinglePersonView window.
     * @param participant A participant whose data is preloaded to the SinglePersonView.
     * If parameter is null, a new participant is going to be edited.
     */    
    private void showSinglePersonViewWindow(Participant participant) {      
        try {
            Stage stage = new Stage();
            if(participant == null) {
                participant = new Participant();
                stage.setTitle("Uusi kilpailija");
            } else {
                stage.setTitle("Muokkaa kilpailijaa");
            }           
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass()
                    .getResource("/fxml/SinglePersonView.fxml"));            
            Scene scene = new Scene(fxmlLoader.load());
            fxmlLoader.<SinglePersonViewController>getController()
                    .populateFields(participant);
             
            stage.initModality(Modality.APPLICATION_MODAL); //Lock parent window
            stage.setScene(scene);
            stage.showAndWait();
            contestList.getSelectionModel().select(participant.getContest());
            refreshParticipantTable();
            participantTable.getSelectionModel().select(participant);
            
        } catch (IOException e) {            
            System.out.println(e);
        }           
    }
    
}
