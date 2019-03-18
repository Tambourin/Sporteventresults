/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sportevent.sporteventresults;

import DAO.DBService;
import domain.Contest;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Olavi
 */
public class SinglePersonViewController implements Initializable {

    private final DBService daoService = new DBService();
    
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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contestChoice.getItems().addAll(daoService.getContests());
        
        saveParticipantButton.setOnAction(event -> {            
            System.out.println(contestChoice.getSelectionModel().getSelectedItem());
            daoService.addParticipant(
                    Integer.parseInt(bidNumberField.getText()),
                    firstNameField.getText(),
                    lastNameField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    addressField.getText(),
                    clubField.getText(),
                    contestChoice.getSelectionModel().getSelectedItem());
        });
    }    
    
        
}
