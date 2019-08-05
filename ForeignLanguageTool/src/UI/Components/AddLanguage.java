/*
File: AddLanguage.java
Author: Ethan Swistak
Date: Aug 4, 2019
Purpose:
*/

package UI.Components;

import DataModel.LanguagePair;
import Logic.ActualAPI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class AddLanguage implements Initializable {
    
    @FXML
    TextField nat;
    
    @FXML
    TextField target;
    
    @FXML
    Button okButton;
    
    @FXML
    Button cancelButton;
    
    @FXML
    Label warning;
    
    ActualAPI api = ActualAPI.getInstance();
    Model model = Model.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String nativelang = api.getUser().getNativeLanguage();
        nat.setText(nativelang);
        warning.setVisible(false);
    }
    
    @FXML
    private void okButton(){
        if(nat.getText()==""||target.getText()==null){
            warning.setVisible(true);
        }else{
            LanguagePair pair = api.createLangPair(new LanguagePair());
            pair.setNat(nat.getText());
            pair.setTarget(target.getText());
            pair.update();
            model.availableLanguagesProperty().add(pair);
            ((Stage)okButton.getScene().getWindow()).close();
        }
    }
    
    @FXML
    private void cancelButton(){
        ((Stage)cancelButton.getScene().getWindow()).close();
    }
    
    

}
