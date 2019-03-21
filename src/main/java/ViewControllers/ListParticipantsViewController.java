package ViewControllers;


import DAO.DBService;
import domain.Contest;
import domain.Participant;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class to initialize controls and 
 * handle user inputs on participants view.
 * On participants view participants from a selected contest are viewed
 *
 * @author Olavi
 */
public class ListParticipantsViewController implements Initializable {
    private final DBService dbService = new DBService();
    
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
    /**
     * Initializes the controller class. 
     * Populates tabels and sets event handlers to controls.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateContestList();        
        populateParticipantTable();
        
        //Repopulate participant table if another contest is selected from the contest list  
        contestList.getSelectionModel().selectedIndexProperty()
                .addListener((obs, oldValue, newValue) -> populateParticipantTable());          
        //Double click on the table to open participants details
        participantTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                showSinglePersonViewWindow(participantTable.getSelectionModel()
                        .getSelectedItem());
            }
        });
        //Open participants details dialog
        editButton.setOnAction(event
                -> showSinglePersonViewWindow(participantTable.getSelectionModel()
                        .getSelectedItem()));
        //Open participants details dialog with no data to add a new participant
        addNewButton.setOnAction(event -> showSinglePersonViewWindow(null));       
    }    
    
    /**
     * Populates ListView with Contest elements from database
     */
    private void populateContestList() {
        contestList.getItems().addAll(dbService.getContests());        
        contestList.getSelectionModel().selectFirst();
    }
    
    /**
     * Populates TableView with Participants in selected Contest
     */
    private void populateParticipantTable() {        
        Contest selectedContest = contestList.getSelectionModel().getSelectedItem();       
        ObservableList participants = dbService.getParticipants(selectedContest);
        bidNumberColumn.setCellValueFactory(new PropertyValueFactory<>("bidNumber"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));                
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        clubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));
        participantTable.setItems(participants);  
        participantTable.refresh();
    }
    
    /**
     * Loads resources, prepares and launches a SinglePersonView window.
     * @param participant A participant whose data is preloaded to the SinglePersonView
     */    
    private void showSinglePersonViewWindow(Participant participant) {      
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass()
                    .getResource("/fxml/SinglePersonView.fxml"));            
            Scene scene = new Scene(fxmlLoader.load());
            fxmlLoader.<SinglePersonViewController>getController()
                    .populateFields(participant);
            Stage stage = new Stage();
            if(participant == null) {
                stage.setTitle("Uusi kilpailija");
            } else {
                stage.setTitle("Muokkaa kilpailijaa");
            }            
            stage.initModality(Modality.APPLICATION_MODAL); //Lock parent window
            stage.setScene(scene);
            stage.showAndWait();   
            populateParticipantTable();
        } catch (IOException e) {
            System.out.println(e);
        }            
    }
    
}
