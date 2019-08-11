/*
File: NewGroupController.java
Author: Ethan Swistak
Date: Aug 4, 2019
Purpose:
*/

package UI.Components.Popups;

import DataModel.DTO.Group;
import Logic.ActualAPI;
import UI.Model.Model;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;


public class NewGroupController implements Initializable {
    
    
    @FXML
    TextField groupName;
    
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
        warning.setVisible(false);
    }
    
    @FXML
    private void okButton(){
        if(groupName.getText()==""){
            warning.setVisible(true);
        }else{
            Group group = api.createGroup(model.getCurrentLanguage(), new Group());
            group.setName(groupName.getText());
            api.updateGroup(group);
            TreeItem item = new TreeItem(group);
            model.getTreeViewRoot().getChildren().add(item);
            ((Stage)okButton.getScene().getWindow()).close();
        }
    }
    
    @FXML
    private void cancelButton(){
        ((Stage)cancelButton.getScene().getWindow()).close();
    }
    
    

}
