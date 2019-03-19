package sportevent.sporteventresults;


import DAO.DBService;
import domain.Contest;
import domain.Participant;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    private final DBService daoService = new DBService();
    
    private ObservableList participants;
    private ObservableList Contests;
    
    
    @FXML
    private ListView<Contest> contestList;
    @FXML
    private TableColumn<Contest, String> contestNameColumn;
    
    
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateContestList();        
        populateParticipantTable();
        
        contestList.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldValue, newValue) -> populateParticipantTable());
        
        participantTable.getSelectionModel().selectedItemProperty()
                .addListener((obs, o, n) -> showSinglePersonViewWindow(n));

    }    
    
    /**
     * Populates ListView with Contest elements from database
     */
    private void populateContestList() {
        contestList.getItems().addAll(daoService.getContests());        
        contestList.getSelectionModel().selectFirst();
    }
    
    /**
     * Populates TableView with Participant objects from database
     * Only participants from selected contest are accuired
     */
    private void populateParticipantTable() {        
        Contest selectedContest = contestList.getSelectionModel().getSelectedItem();       
        participants = daoService.getParticipants(selectedContest);
        bidNumberColumn.setCellValueFactory(new PropertyValueFactory<>("bidNumber"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));                
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        clubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));
        participantTable.setItems(participants);        
        
    }
    
    /**
     * Loads resources for a SinglePersonView window and passes a Participant to
     * its controller class.
     * Finally shows the window while locking parent window.
     * @param participant participant whose data is preloaded to SinglePersonView
     */    
    private void showSinglePersonViewWindow(Participant participant) {        
        if (participant == null) {
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/fxml/SinglePersonView.fxml"));            
            Scene scene = new Scene(fxmlLoader.load());
            fxmlLoader.<SinglePersonViewController>getController().setParticipant(participant);
            Stage stage = new Stage();     
            stage.setTitle("Kilpailija");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
}
