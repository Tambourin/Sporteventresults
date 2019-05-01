
package viewControllers;

import Services.ContestService;
import Services.EventService;
import domain.Contest;
import domain.Event;
import java.net.URL;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;


/**
 * FXML Controller class to initialize controls and 
 * handle user inputs on ContestView fxml. 
 * @author Olavi
 */
public class EventViewController implements Initializable {
    
    private final ContestService contestService = new ContestService();
    private final EventService eventService = new EventService();
    
    Event selectedEvent;
    
    @FXML
    TextField eventNameField;
    @FXML
    TextField eventLocationField;
    @FXML
    DatePicker eventDatePicker;
    @FXML
    TextArea eventInfoField;
    @FXML
    Button eventSaveButton;
    
    @FXML
    TableView<Contest> contestsTable;
    @FXML
    TableColumn<Contest, String> nameColumn;
    @FXML
    TableColumn<Contest, String> startingTimeColumn;
    @FXML
    TableColumn<Contest, Integer> numOfParticipantsColumn;

    @FXML
    VBox contestFieldsContainer;    
    @FXML
    TextField nameField;
    @FXML
    TextField startingTimeField;

    @FXML
    Button addNewButton;
    @FXML
    Button saveButton;
    @FXML
    Button deleteButton;
    
    @FXML
    Hyperlink changeEventLink;
    /**
     * Initializes the controller class. Populates tables and fields.
     * Sets event handlers
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        //selectedEvent = eventService.findById(1); 
        //populateEventFields(selectedEvent); 
        populateContestTable();          
        contestsTable.getSelectionModel().selectFirst();        
        populateContestFields(contestsTable.getSelectionModel().getSelectedItem());
        
        contestsTable.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldValue, newValue) -> populateContestFields(newValue));     
        deleteButton.setOnAction(e -> deleteContest());
        saveButton.setOnAction(e -> updateContest());
        addNewButton.setOnAction(e -> addNewContest());        
        eventSaveButton.setOnAction(e -> saveEvent());
    }    
    
     /**
     * Sets Selected event. This method is called when this view is loaded
     * from another view.
     * @param event Event that is assigned to selectedEvent
     */
    public void setSelectedEvent(Event event) {
        this.selectedEvent = event;
        System.out.println("EventView, Selected event is:" + this.selectedEvent);
        populateEventFields(event);
        populateContestTable();
    }
    
    private void populateEventFields(Event event) {
        if (event != null) {
            eventNameField.setText(event.getName());
            eventLocationField.setText(event.getLocation());
            eventDatePicker.setValue(event.getDate());
            eventInfoField.setText(event.getInfo());
        }
    }
    
    private void saveEvent() {
        this.selectedEvent.setName(eventNameField.getText());
        this.selectedEvent.setLocation(eventLocationField.getText());
        this.selectedEvent.setDate(eventDatePicker.getValue());
        this.selectedEvent.setInfo(eventInfoField.getText());
        eventService.update(this.selectedEvent);
    }
    
    private void populateContestTable() {
        if( selectedEvent == null) {
            System.out.println("SelectedEvent not available");
            return;
        }
        ObservableList<Contest> contests = FXCollections.<Contest>observableArrayList();
        contests.addAll(contestService.findAllByEvent(selectedEvent));        
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        startingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startingTime")); 
        numOfParticipantsColumn.setCellValueFactory(new PropertyValueFactory<>("participantsNumber"));        
        contestsTable.setItems(contests);        
    }
    
    private void populateContestFields(Contest contest) {
        if (contest != null) {
            nameField.setText(contest.getName());
            startingTimeField.setText(contest.getStartingTime().toString());
            contestFieldsContainer.setDisable(false);
        } else {
            nameField.setText("");            
            startingTimeField.setText("");
            System.out.println("Contest is null");
            contestFieldsContainer.setDisable(true);
        }
    }
    
    private void addNewContest(){                          
        contestService.addNew("Uusi sarja", "00:00", this.selectedEvent);
        populateContestTable();   
        contestsTable.getSelectionModel().selectLast();          
    }
    
    private void updateContest() {    
        if (contestsTable.getSelectionModel().getSelectedItem() == null) {
           DialogUtil.showErrorDialog("Lisää ensin uusi sarja!");
           return;
        } 
        Contest contest = contestsTable.getSelectionModel().getSelectedItem();
        if (validateFields()) {
            contest.setName(nameField.getText());
            contest.setStartingTime(LocalTime.parse(startingTimeField.getText()));
            contestService.update(contest);
        }
        contestsTable.refresh();
    }
    
    private void deleteContest() {
        Contest contest = contestsTable.getSelectionModel().getSelectedItem();
        if (contest == null) {
            DialogUtil.showErrorDialog("Ei valittua tapahtumaa!");
            return;
        }
        String deletePromtText = "Haluatko varmasti poistaa sarjan "
                + contest.getName() + "?";
        if (DialogUtil.promptDelete(deletePromtText)){
            if (contestService.delete(contest)) {
                populateContestTable();
                contestsTable.getSelectionModel().selectLast(); 
            } else {
                DialogUtil.showErrorDialog("Et voi poistaa sarjaa, jossa on osanottajia.");                
            }            
        }
    }
    
    private boolean validateFields() {
        String errorMessage = "";
        if (this.nameField.getText().length() == 0 ||
                this.nameField.getText() == null) {
            errorMessage += "Tarkista sarjan nimi! ";
        }
        try {
            LocalTime.parse(startingTimeField.getText());
        } catch (Exception e) {
            errorMessage += "Kellonaika väärässä muodossa! Käytä muotoa hh:mm";
        }
        
        if (errorMessage.length() == 0) {
            return true;
        } else {
            DialogUtil.showErrorDialog(errorMessage);
            return false;
        }        
    }
}
