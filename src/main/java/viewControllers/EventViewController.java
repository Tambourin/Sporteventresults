
package ViewControllers;

import DAO.DBService;
import domain.Contest;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;


/**
 * FXML Controller class to initialize controls and 
 * handle user inputs on ContestView fxml. 
 * @author Olavi
 */
public class ContestViewController implements Initializable {
    
    private final DBService dbService = new DBService();
    
    @FXML
    ListView<Contest> contestsListView;
    @FXML
    TextField nameField;
    @FXML
    TextField distanceField;
    @FXML
    TextField startingTimeField;
    /**
     * Initializes the controller class. A ListView is populated with
     * contests from the database.

     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contestsListView.getItems().addAll(dbService.getContests());
        contestsListView.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldValue, newValue) -> populateFields(newValue));
        
    }    
    
    public void populateFields(Contest contest) {
        nameField.setText(contest.getName());
  
    }
    
}
