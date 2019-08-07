/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Components;


import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ethan Swistak
 */
public class HelpController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void menuHelpAboutEvent(ActionEvent event) {

        Stage popupwindow=new Stage();
      
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("About");
        
        VBox layout = new VBox();
        WebView view = new WebView();
        URL url = getClass().getClassLoader().getResource("UI/Help/About.html");
        view.getEngine().load(url.toString());
        
        layout.getChildren().add(view);
        Scene scene = new Scene(layout, 800.0, 800.0);
        popupwindow.setScene(scene);
        popupwindow.showAndWait();

    }
    
}
