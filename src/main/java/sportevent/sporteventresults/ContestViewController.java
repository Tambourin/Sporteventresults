/*
 * Controller class to initialize controls and 
 * handle user inputs on ContestView fxml. 
 */
package sportevent.sporteventresults;

import DAO.DBService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;


/**
 * FXML Controller class
 *
 * @author Olavi
 */
public class ContestViewController implements Initializable {
    
    private final DBService dbService = new DBService();
    
    @FXML
    ListView contestsListView;
    /**
     * Initializes the controller class. A ListView is populated with
     * contests from the database.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contestsListView.getItems().addAll(dbService.getContests());
    }    
    
}
