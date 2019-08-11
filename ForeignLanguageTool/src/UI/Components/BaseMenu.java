/*
File: BaseMenu.java
Author: Ethan Swistak
Date: Aug 4, 2019
Purpose:
*/

package UI.Components;

import UI.Model.Model;
import DataModel.DTO.LanguagePair;
import Logic.ActualAPI;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class BaseMenu implements Initializable {
    
    @FXML
    Scene scene;

    ActualAPI api = ActualAPI.getInstance();
    Model model = Model.getInstance();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        //closes the window if closed is set to true
        model.isClosedProperty().addListener((Obj, oldVal, newVal)->{
            if(newVal.booleanValue())((Stage)scene.getWindow()).close();
            });
    }

}
