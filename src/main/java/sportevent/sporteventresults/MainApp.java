package sportevent.sporteventresults;


import domain.Participant;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Olavi
 */
public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/MainView.fxml"));        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        //ParticipantDao pDao = new ParticipantDaoJdbc();
        //ContestDao cDao = new ContestDaoJdbc();
        
        //Contest contest = new Contest();
        //contest.setName("Vappuhölkkä");
        //contest.setStartingTime(LocalTime.of(10, 30));
        //cDao.create(contest);
        //cDao.listAll().forEach(c -> System.out.println(c));
        //System.out.println(cDao.findById(1));
        
        //Participant participant = new Participant();
        //participant.setFirstName("Väinö");
        //participant.setLastName("Tepponen");
        //participant.seteMail("väinö@hasta.fi");
        //participant.setContest(cDao.findById(1));
        //
        //pDao.create(participant);
        //Participant participant = pDao.findById(1);
        //participant.setContest(cDao.findById(1));
        //pDao.update(participant);
        //pDao.listAll().forEach(p -> System.out.println(p));
        //pDao.listByContest(cDao.findById(1)).forEach(c -> System.out.println(c));
        //System.out.println(pDao.findByName("oika"));
        //System.out.println(pDao.findById(1));
        //pDao.delete(2);
        //pDao.listAll().forEach(p -> System.out.println(p));
        
    }
    


}
