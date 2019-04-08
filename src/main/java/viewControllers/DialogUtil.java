
package viewControllers;


import java.util.Optional;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * An Utility class for common alert dialogs and closing window.
 * @author Olavi
 */
public class DialogUtil {
    
    /**
     * Opens an alert dialog showing a message that was passed to method.
     * @param message String to be shown as alert message.
     * @return Returns true if user pressed OK
     */
    public static boolean promptDelete(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Huom!");
        alert.setHeaderText(message);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {            
            return true;
        }  
        return false;
    }
        
    /**
     * Opens a error dialog with given message 
     * @param errorMessage Error string to be shown to a user
     */
    public static void showErrorDialog(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Huom!");
        alert.setHeaderText(errorMessage);
        alert.showAndWait();
    }   
    
    /**
     * Close the window where the closing event was fired.
     * @param event Event that was fired
     */     
    public static void closeWindow(Event event) {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
