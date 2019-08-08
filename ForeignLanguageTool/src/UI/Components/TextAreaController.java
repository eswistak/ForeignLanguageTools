/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Components;

import DataModel.Card;
import DataModel.LanguagePair;
import DataModel.Note;
import Logic.ActualAPI;
import Logic.Define;
import UI.Components.Popups.CardCreationController;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Ethan Swistak
 */
public class TextAreaController implements Initializable {
    
    Model model = Model.getInstance();
    ActualAPI api = ActualAPI.getInstance();

    @FXML
    TextArea area;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        model.currentDocumentProperty().addListener((e)->{
            area.setText(model.getCurrentDocument().getText());
        });
        Define.setLangCodes();
    }
    
    @FXML
    private void createNewCardEvent(){
        CardCreationController newCard = new CardCreationController(model.getCurrentDocument());
    }

    @FXML
    private void createNoteEvent(){
        Note n = api.createNote(model.getCurrentDocument(), new Note());

        Dialog<String> dialog = new Dialog();
        dialog.setTitle("Edit Note");
        dialog.setHeaderText("Edit Note");

        // button
        ButtonType buttonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextArea newTextArea = new TextArea();
        newTextArea.setWrapText(true);
        newTextArea.setText(n.getContent());

        grid.add(newTextArea, 0, 0);
        dialog.getDialogPane().setContent(grid);

        // request focus on the native field by defualt
        Platform.runLater(() -> newTextArea.requestFocus());

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            n.setContent(newTextArea.getText());
        }
        api.updateNote(n);
        model.notesListProperty().add(n);
    }
    @FXML
    private void newCardWordEvent() {
        String selectedStr = "";

        selectedStr = area.getSelectedText();
        IndexRange range = area.getSelection();
        
        Card wordcard = new Card();
        wordcard = ActualAPI.getInstance().createCard(model.getCurrentDocument(), wordcard);
        wordcard.setWordAsAppears(selectedStr);
        wordcard.setStartChar(range.getStart());
        wordcard.setEndChar(range.getEnd());
        CardCreationController newCard = new CardCreationController(model.getCurrentDocument(), wordcard);
        
    }
//
    @FXML
    private void newCardDefineEvent() {
        String selectedStr = "";

        selectedStr = area.getSelectedText();
        IndexRange range = area.getSelection();
        
        Card definecard = new Card();
        definecard = ActualAPI.getInstance().createCard(model.getCurrentDocument(), definecard);
        LanguagePair langPair = (LanguagePair) model.currentLanguageProperty().get();
        String natural = langPair.getNat().toUpperCase();
        String target = langPair.getTarget().toUpperCase();
        Map<String, String> LangCodes = Define.getLangCodes();
        System.out.println(natural+" "+target);
        String naturalCode = LangCodes.get(natural);
        String targetCode =  LangCodes.get(target);

        String translate = Define.getDefinition(targetCode, naturalCode, selectedStr);
        definecard.setWordAsAppears(selectedStr);
        // definecard.setGeneric("Test");
        definecard.setTransInContext(translate);
        definecard.setStartChar(range.getStart());
        definecard.setEndChar(range.getEnd());
        // definecard.setPartOfSpeech("Test");
        // definecard.setOtherTrans("Test");
        CardCreationController newCard = new CardCreationController(model.getCurrentDocument(), definecard);
        
    }
}
