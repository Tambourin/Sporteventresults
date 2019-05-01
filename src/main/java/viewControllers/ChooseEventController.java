package viewControllers;


import Services.EventService;
import domain.Event;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class for ChooseEventView
 * In this view user selects the event that is going to be edited
 * Selected event is passed to mainView
 *
 * @author Olavi
 */
public class ChooseEventController implements Initializable {
    EventService eventService = new EventService();
    
    @FXML
    AnchorPane anchorPane;
    @FXML
    private ListView<Event> eventsList;
    
    
    @FXML
    Button createNewEventButton;
    @FXML
    Button chooseButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateEventsList();
        createNewEventButton.setOnAction(e -> {
            loadMainView(null);
        });
        chooseButton.setOnAction(e -> {
            loadMainView(eventsList.getSelectionModel().getSelectedItem());
        });
        
        eventsList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {                
                if (eventsList.getSelectionModel().getSelectedItem() != null) {
                    loadMainView(eventsList.getSelectionModel().getSelectedItem());
                }
                
            }           
        });
    }    
    
    private void loadMainView(Event event){
        try {
            if(event == null) {
                event = new Event("Uusi tapahtuma", " ", LocalDate.now(), "info");
                event.setId(eventService.create(event));
            }                        
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));  
            Parent main = fxmlLoader.load();
            fxmlLoader.<MainViewController>getController().setSelectedEvent(event);
            anchorPane.getChildren().setAll(main);
        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    private void populateEventsList() {
        ObservableList<Event> events = FXCollections.observableArrayList();
        events.addAll(eventService.findAll());
        if(events.isEmpty()) {
            return;
        }
        eventsList.getItems().addAll(events);        
        eventsList.getSelectionModel().selectFirst();
    }
    
}
