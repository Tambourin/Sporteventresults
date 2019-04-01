
package viewControllers;

import dao.ContestDao;
import dao.ContestDaoJdbc;
import dao.EventDaoJdbc;
import domain.Contest;
import domain.Event;
import java.net.URL;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


/**
 * FXML Controller class to initialize controls and 
 * handle user inputs on ContestView fxml. 
 * @author Olavi
 */
public class EventViewController implements Initializable {
    
    private final EventDaoJdbc eventDaoJdbc = new EventDaoJdbc();
    private final ContestDao contestDao = new ContestDaoJdbc();
    
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
    TextField nameField;
    @FXML
    TextField startingTimeField;
    
    @FXML
    Button addNewButton;
    @FXML
    Button saveButton;
    @FXML
    Button deleteButton;
    /**
     * Initializes the controller class. 

     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        //LocalDate LocalDate = null;        
        //eventDaoJdbc.create(new Event("nimi", "sijainti", LocalDate.of(2020, Month.MARCH, 5), "fff"));//
        selectedEvent = eventDaoJdbc.findById(1); //Valitaan tässävaiheessa aina ykkönen
        populateEventFields(selectedEvent); 
        populateTable();        
        contestsTable.getSelectionModel().selectFirst();
        populateFields(contestsTable.getSelectionModel().getSelectedItem());
        
        contestsTable.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldValue, newValue) -> populateFields(newValue));
     
        deleteButton.setOnAction(e -> {
            if (deleteContest(contestsTable.getSelectionModel().getSelectedItem())){
                populateTable();
                contestsTable.getSelectionModel().selectLast();
            }
            
        });
        saveButton.setOnAction(e -> {
            updateContest(contestsTable.getSelectionModel().getSelectedItem());
            contestsTable.refresh();
        });
        addNewButton.setOnAction(e -> {
            populateFields(null);
            addNewContest();
            populateTable();   
            contestsTable.getSelectionModel().selectLast();
        });
        
        eventSaveButton.setOnAction(e -> {
            this.selectedEvent.setName(eventNameField.getText());
            this.selectedEvent.setLocation(eventLocationField.getText());
            this.selectedEvent.setDate(eventDatePicker.getValue());
            this.selectedEvent.setInfo(eventInfoField.getText());
            eventDaoJdbc.update(this.selectedEvent);
        });
    }    
    
    private void populateEventFields(Event event) {
        if (event != null) {
            eventNameField.setText(event.getName());
            eventLocationField.setText(event.getLocation());
            eventDatePicker.setValue(event.getDate());
            eventInfoField.setText(event.getInfo());
        }
    }
    
    private void populateTable() {
        ObservableList<Contest> contests = FXCollections.<Contest>observableArrayList();
        contests.addAll(contestDao.findAll());        
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        startingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startingTime")); 
        numOfParticipantsColumn.setCellValueFactory(new PropertyValueFactory<>("participantsNumber"));        
        contestsTable.setItems(contests);        
    }
    
    private void populateFields(Contest contest) {
        if (contest != null) {
            nameField.setText(contest.getName());
            startingTimeField.setText(contest.getStartingTime().toString());
        } else {
            nameField.setText("Uusi sarja");            
            startingTimeField.setText("00:00");
        }
    }
    
    private void addNewContest(){                
        if (validateFields()) {
            Contest newContest = 
                    new Contest(nameField.getText(), 
                            LocalTime.parse(startingTimeField.getText()));
            contestDao.create(newContest);
        }                     
    }
    
    private void updateContest(Contest contest) {
        if (validateFields()) {
            contest.setName(nameField.getText());
            contest.setStartingTime(LocalTime.parse(startingTimeField.getText()));
            contestDao.update(contest);
        }
    }
    
    private boolean deleteContest(Contest contest) {
        if (contest.getParticipantsNumber() > 0) {
            showErrorDialog("Et voi poistaa sarjaa, jossa on osanottajia.");
            return false;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Huom!");
        alert.setHeaderText("Haluatko varmasti poistaa sarjan "
                + contest.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            contestDao.delete(contest.getId());
            return true;
        }  
        return false;        
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
            showErrorDialog(errorMessage);
            return false;
        }        
    }
    
    private void showErrorDialog(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Huom!");
        alert.setHeaderText(errorMessage);
        alert.showAndWait();
    }
}
