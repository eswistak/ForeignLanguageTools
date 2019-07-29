/*
File: CardCreationController.java
Author: Ethan Swistak
Date: Jul 25, 2019
Purpose:
*/

package UI;

import DataModel.Card;
import DataModel.Doc;
import Logic.ActualAPI;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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


public class CardCreationController extends Window {
    
    @FXML
    private Button saveAndCloseButton;
    
    @FXML
    private Button saveAndNewButton;
    
    @FXML
    private Button cancelButton;
    
    @FXML
    private TextField wordAsAppearsField;
    
    @FXML
    private TextField genericField;
    
    @FXML
    private TextField defInContextField;
    
    @FXML
    private TextField partOfSpeechField;
    
    @FXML
    private TextArea otherDefsField;
    
    public static Card card = null;
    public static Doc doc;
    
    public CardCreationController(){
        
    }
    
    public CardCreationController(Doc doc, Card card){
        CardCreationController.card = card;
        CardCreationController.doc = doc;
        FXMLLoader loader = new FXMLLoader();
        try {
            FileInputStream inputStream = new FileInputStream("UI\\CardCreation.fxml");
            Scene inputCard = loader.load(inputStream);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(inputCard);
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.show();
        } catch (FileNotFoundException ex) {
            System.out.println("Failed to initialize FXML, File Not Found");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public CardCreationController(Doc doc){
        this.doc = doc;
        FXMLLoader loader = new FXMLLoader();
        try {
            FileInputStream inputStream = new FileInputStream("UI\\CardCreation.fxml");
            Scene inputCard = loader.load(inputStream);
            Stage secondaryStage = new Stage();
            secondaryStage.setScene(inputCard);
            secondaryStage.initModality(Modality.APPLICATION_MODAL);
            secondaryStage.show();
        } catch (FileNotFoundException ex) {
            System.out.println("Failed to initialize FXML, File Not Found");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    
    @FXML
    private void saveAndCloseClickedEvent(){
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



}
