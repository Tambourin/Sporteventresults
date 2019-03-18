package sportevent.sporteventresults;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import DAO.DBService;
import domain.Contest;
import domain.Participant;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
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
        // TODO
        populateContestList();        
        populateParticipantTable();
        
    }    
    
    private void populateContestList() {
        contestList.getItems().addAll(daoService.getContests());
        contestList.getSelectionModel().selectedItemProperty()
                .addListener((obs, oldValue, newValue) -> populateParticipantTable());
        
//        Contests = daoService.getContests();
//        contestNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));      
//        contestTable.setItems(Contests);
//        contestTable.getSelectionModel().selectedItemProperty()
//                .addListener((obs, oldValue, newValue) -> {
//                    populateParticipantTable();
//                       });
          contestList.getSelectionModel().selectFirst();
    }
    

    
    private void populateParticipantTable() {        
        Contest selectedContest = contestList.getSelectionModel().getSelectedItem();       
        participants = daoService.getParticipants(selectedContest);
        bidNumberColumn.setCellValueFactory(new PropertyValueFactory<>("bidNumber"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));                
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        clubColumn.setCellValueFactory(new PropertyValueFactory<>("club"));
        participantTable.setItems(participants);
    }
    
}
