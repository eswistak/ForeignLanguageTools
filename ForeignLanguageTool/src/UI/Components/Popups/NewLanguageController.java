/*
File: NewLanguageController.java
Author: Ethan Swistak
Date: Aug 4, 2019
Purpose:
*/

package UI.Components.Popups;

import DataModel.DTO.LanguagePair;
import Logic.ActualAPI;
import Logic.Define;
import UI.Model.Model;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class NewLanguageController implements Initializable {
    
    @FXML
    ChoiceBox<String> nat;
    
    @FXML
    ChoiceBox<String> target;
    
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
        Define.setLangCodes();
        Map<String, String> LangCodes = Define.getLangCodes();

        nat.setItems(FXCollections.observableArrayList(LangCodes.keySet()));
        target.setItems(FXCollections.observableArrayList(LangCodes.keySet()));
        nat.setValue(nativelang);
        warning.setVisible(false);
    }
    
    @FXML
    private void okButton(){
        if(nat.getValue()==""||target.getValue()==null){
            warning.setVisible(true);
        }else{
            LanguagePair pair = api.createLangPair(new LanguagePair());
            pair.setNat(nat.getValue());
            pair.setTarget(target.getValue());
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
