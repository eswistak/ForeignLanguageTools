/*
File: CardCreationController.java
Author: Ethan Swistak
Date: Jul 25, 2019
Purpose:
*/

package UI.Components.Popups;

import DataModel.DTO.Card;
import DataModel.DTO.Doc;
import Logic.ActualAPI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;


public class CardCreationController implements Initializable {
    
    @FXML
    private Button saveAndCloseButton;
    
    @FXML
    private Button saveAndNewButton;
    
    @FXML
    private Button cancelButton;
    
    @FXML
    protected TextField wordAsAppearsField;
    
    @FXML
    protected TextField genericField;
    
    @FXML
    protected TextField defInContextField;
    
    @FXML
    protected TextField partOfSpeechField;
    
    @FXML
    protected TextArea otherDefsField;
    
    @FXML TextArea hint = new TextArea();
    
    public static Card card;
    public static Doc doc;
    
    public CardCreationController(){
        
    }
    
    public CardCreationController(Doc doc, Card card){
        CardCreationController.doc = doc;
        this.card = card;
        FXMLLoader loader = new FXMLLoader();
        ClassLoader cl = CardCreationController.class.getClassLoader();
        InputStream inputStream = cl.getResourceAsStream("UI/Components/Popups/CardCreation.fxml");
        Scene inputCard = null;
        try {
            inputCard = loader.load(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(CardCreationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage secondaryStage = new Stage();
        secondaryStage.setScene(inputCard);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.show();
    }
    
    private void defineFields(){
        
    }
    
    public CardCreationController(Doc doc){
        CardCreationController.doc = doc;
        this.card = null;
        FXMLLoader loader = new FXMLLoader();
        ClassLoader cl = CardCreationController.class.getClassLoader();
        InputStream inputStream = cl.getResourceAsStream("UI/Components/Popups/CardCreation.fxml");
        Scene inputCard = null;
        try {
            inputCard = loader.load(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(CardCreationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Stage secondaryStage = new Stage();
        secondaryStage.setScene(inputCard);
        secondaryStage.initModality(Modality.APPLICATION_MODAL);
        secondaryStage.show();
    }

    
    @FXML
    private void saveAndCloseClickedEvent(){
        if(this.card == null){
            this.card = new Card();
            card = ActualAPI.getInstance().createCard(CardCreationController.doc, card);
            card.setWordAsAppears(wordAsAppearsField.getText());
            card.setGeneric(genericField.getText());
            card.setTransInContext(defInContextField.getText());
            card.setPartOfSpeech(partOfSpeechField.getText());
            card.setOtherTrans(otherDefsField.getText());
            ActualAPI.getInstance().updateCard(card);
        }else{
            card.setWordAsAppears(wordAsAppearsField.getText());
            card.setGeneric(genericField.getText());
            card.setTransInContext(defInContextField.getText());
            card.setPartOfSpeech(partOfSpeechField.getText());
            card.setOtherTrans(otherDefsField.getText());
            ActualAPI.getInstance().updateCard(card);
        }
        Scene scene = saveAndCloseButton.getScene();
        Stage stage = ((Stage)scene.getWindow());
        stage.close();
    }
    
    @FXML
    private void saveAndNewClickedEvent(){
        if(this.card == null){
            this.card = new Card();
            card = ActualAPI.getInstance().createCard(doc, card);
            card.setWordAsAppears(wordAsAppearsField.getText());
            card.setGeneric(genericField.getText());
            card.setTransInContext(defInContextField.getText());
            card.setPartOfSpeech(partOfSpeechField.getText());
            card.setOtherTrans(otherDefsField.getText());
            ActualAPI.getInstance().updateCard(card);
        }else{
            card.setWordAsAppears(wordAsAppearsField.getText());
            card.setGeneric(genericField.getText());
            card.setTransInContext(defInContextField.getText());
            card.setPartOfSpeech(partOfSpeechField.getText());
            card.setOtherTrans(otherDefsField.getText());
            ActualAPI.getInstance().updateCard(card);
        }
        this.wordAsAppearsField.clear();
        this.genericField.clear();
        this.defInContextField.clear();
        this.partOfSpeechField.clear();
        this.otherDefsField.clear();
        this.card = null;
    }
    
    @FXML
    private void cancelClickedEvent(){
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(card == null){
            
        }else{
            wordAsAppearsField.setText(card.getWordAsAppears());
            genericField.setText(card.getGeneric());
            defInContextField.setText(card.getTransInContext());
            partOfSpeechField.setText(card.getPartOfSpeech());
            otherDefsField.setText(card.getOtherTrans());
            hint.setText(card.getHint());
        }
    }



}
