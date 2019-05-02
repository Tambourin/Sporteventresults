package viewControllers;


import services1.ParticipantService;
import dao.ContestDao;
import dao.ContestDaoJdbc;
import domain.Contest;
import domain.Event;
import domain.Participant;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private final ContestDao contestDao = new ContestDaoJdbc();
    private final ParticipantService participantService = new ParticipantService();
    private Event selectedEvent;
    
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
    private Button printButton;
    @FXML
    private TextField searchField;
    /**
     * Initializes the controller class. 
     * Populates tabels and sets event handlers to controls.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        populateContestList();        
//        refreshParticipantTable();
        
        //Repopulate participant table if another contest is selected from the contest list  
        contestList.getSelectionModel().selectedIndexProperty()
                .addListener((obs, oldValue, newValue) -> 
                        refreshParticipantTable()
        );          
        
        //Double click on the table to open participants details on a new window
        participantTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                showSinglePersonViewWindow(participantTable.getSelectionModel()
                        .getSelectedItem());
            }       
            contestList.getSelectionModel().select(participantTable.getSelectionModel()
                        .getSelectedItem().getContest());
        });      

        
        //Open participants details on a new window
        editButton.setOnAction(event -> {            
            showSinglePersonViewWindow(participantTable.getSelectionModel()
                        .getSelectedItem());
        });
        
        //Open foundParticipants details dialog with no data to add a new participant
        addNewButton.setOnAction(event -> showSinglePersonViewWindow(null)); 
        
        deleteButton.setOnAction(event -> deleteParticipant());
        // Reload participant table using search criteria
        searchButton.setOnAction(event ->
            populateParticipantTable(
                    participantService.findByNameOrNumber(searchField.getText(), selectedEvent)));
        // Reload participant table using search criteria                
        searchField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {                
                contestList.getSelectionModel().clearSelection();
                populateParticipantTable(
                        participantService.findByNameOrNumber(searchField.getText(), selectedEvent));
            }
        });
        
        printButton.setOnAction(event -> {
            PrinterJob job = PrinterJob.createPrinterJob();
            job.showPrintDialog((Stage)((Node)event.getSource()).getScene().getWindow());
            if (job != null) {
                boolean success = job.printPage(participantTable);
                if (success) {
                    job.endJob();
                }
            }
        });
    }     
    
    /**
     * Sets Selected event. This method is called when this view is loaded
     * from another view.
     * @param event Event that is assigned to selectedEvent
     */
    public void setSelectedEvent(Event event) {
        this.selectedEvent = event;
        populateContestList();        
        refreshParticipantTable();
    }
    
    private void refreshParticipantTable() {        
        populateParticipantTable(
                participantService.findByContest(
                        contestList.getSelectionModel().getSelectedItem()));
    }

    private void populateContestList() {
        ObservableList<Contest> contests = FXCollections.observableArrayList();
        contests.addAll(contestDao.findAllByEvent(selectedEvent));
        contestList.getItems().addAll(contests);        
        contestList.getSelectionModel().selectFirst();
    }
    
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
    
    //Delete participant if user approves
    private void deleteParticipant() {
        Participant participant = 
                participantTable.getSelectionModel().getSelectedItem();
        if (participant == null) {
            DialogUtil.showErrorDialog("Ei valittua osanottajaa");
            return;
        }
        if (DialogUtil.promptDelete("Haluatko varmasti poistaa osanottajan " 
                + participant.getFirstName() + " " 
                + participant.getLastName() + "?")) {
            participantService.delete(participant);
            refreshParticipantTable();
        }
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
                    .populateFields(participant, contestDao.findAllByEvent(selectedEvent));
             
            stage.initModality(Modality.APPLICATION_MODAL); //Lock parent window
            stage.setScene(scene);
            stage.showAndWait();
            System.out.println(participant);
            contestList.getSelectionModel().select(participant.getContest());
            refreshParticipantTable();
            participantTable.getSelectionModel().select(participant);
            
        } catch (IOException e) {            
            System.out.println(e);
        }           
    }
    
}
