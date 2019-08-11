/*
File: LanguagesMenu.java
Author: Ethan Swistak
Date: Aug 4, 2019
Purpose:
*/

package UI.Components;

import DataModel.DTO.Doc;
import UI.Model.Model;
import DataModel.DTO.LanguagePair;
import Logic.ActualAPI;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class LanguagesMenu implements Initializable {
    
    private Model model = Model.getInstance();
    private ActualAPI api = ActualAPI.getInstance();
    
    @FXML
    private Menu langMenu;
    
    @FXML
    private MenuItem setNativeLanguageItem;
    
    @FXML
    private MenuItem newLanguagePairItem;
    
    @FXML
    private MenuItem deleteLanguagePairItem;
    
    @FXML
    private SeparatorMenuItem separator;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.availableLanguagesProperty().addListener(new ListChangeListener(){
            @Override
            public void onChanged(ListChangeListener.Change c) {
                Menu menu = langMenu;
                menu.getItems().clear();
                menu.getItems().add(setNativeLanguageItem);
                menu.getItems().add(newLanguagePairItem);
                menu.getItems().add(deleteLanguagePairItem);
                menu.getItems().add(separator);
                for(LanguagePair pair: model.availableLanguagesProperty()){
                    MenuItem item = new MenuItem();
                    item.setText(pair.getNat() + "->" + pair.getTarget());
                    item.setOnAction((value)->{
                    model.setCurrentLanguage(pair);
                    });
                    menu.getItems().add(item);
                }
                langMenu = menu;
            }
        
        });
        
        for(LanguagePair pair :model.availableLanguagesProperty()){
            MenuItem item = new MenuItem(pair.getNat() + "->" + pair.getTarget());
            langMenu.getItems().add(item);
            item.setOnAction((value)->{
                    model.setCurrentLanguage(pair);
                    System.out.println("Language Pair Changed");
             });
        }
    }
    
    /**\
     * Set the model to the currently active native language
     * 
     */
    @FXML
    public void setNativeLang(){
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("UI/Components/Popups/SetNativeLang.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            stage.setScene(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        stage.showAndWait();
    }
    
    @FXML
    public void addNewLang(){
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("UI/Components/Popups/NewLanguage.fxml"));
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        try {
            stage.setScene(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        stage.showAndWait();
    }
    
    @FXML
    public void deleteLang(){
        LanguagePair pair = model.getCurrentLanguage();
        api.deleteLangPair(pair);
        model.availableLanguagesProperty().remove(pair);
        model.currentDocumentProperty().set(new Doc());
        model.cardsListProperty().clear();
        model.notesListProperty().clear();
    }

}
