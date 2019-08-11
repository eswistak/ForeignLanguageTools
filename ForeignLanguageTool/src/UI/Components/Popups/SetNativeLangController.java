/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Components.Popups;

import Logic.ActualAPI;
import UI.Model.Model;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ethan Swistak
 */
public class SetNativeLangController implements Initializable {

    @FXML
    TextField nativeLang;
    
    @FXML
    Button okButton;
    
    @FXML
    Button cancelButton;
    
    @FXML
    Label warning;
    
    ActualAPI api = ActualAPI.getInstance();
    Model model = Model.getInstance();
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         nativeLang.setText(model.getNativeLang());
         warning.setVisible(false);
    }

    @FXML
    public void okButtonClicked(){
        if(nativeLang.getText()==""){
            warning.setVisible(true);
        }else{
        model.setNativeLang(nativeLang.getText());
        }
        cancelButtonClicked();
    }
    
    @FXML
    public void cancelButtonClicked(){
        ((Stage)cancelButton.getScene().getWindow()).close();
    }
    
    
}
