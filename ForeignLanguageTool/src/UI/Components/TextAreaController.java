/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Components;

import UI.Model.Model;
import DataModel.DTO.Card;
import DataModel.DTO.Doc;
import DataModel.DTO.LanguagePair;
import DataModel.DTO.Note;
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
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;



/**
 * FXML Controller class
 *
 * @author Ethan Swistak
 */
public class TextAreaController implements Initializable {
    
    Model model = Model.getInstance();
    ActualAPI api = ActualAPI.getInstance();

    //Scripts to extract Selection from HTMLEditor
    public static final String SELECT_TEXT = "window.getSelection().toString();";
    public static final String GET_START = "window.getSelection().getRangeAt(0).startOffset;";
    public static final String GET_END = "window.getSelection().getRangeAt(0).endOffset;";
    
    
    //TextArea area;
    @FXML
    HTMLEditor area;
    
    public static void hideHTMLEditorToolbars(final HTMLEditor editor) {
        editor.setVisible(false);
        Platform.runLater(() -> {
            Node[] nodes = editor.lookupAll(".tool-bar").toArray(new Node[0]);
            for (Node node : nodes) {
                node.setVisible(false);
                node.setManaged(false);
            }
            editor.setVisible(true);
        });
    }
    
    //Helps format the text for handleTreeViewMain
    private void updateOthers(int index,int amount,List<Card> cards,List<Note> notes){
        for(int i=0;i<cards.size();i++) {
            Card c = cards.get(i);
            int start = c.getStartChar();
            int end = c.getEndChar();
            
            if(start>index) {
                start+=amount;
                c.setStartChar(start);
            }
            if(end>index) {
                end+=amount;
                c.setEndChar(end);
            }
        }
        for(int i=0;i<notes.size();i++) {
            Note n = notes.get(i);
            int start = n.getStartChar();
            int end = n.getEndChar();
            if(start>index) {
                start+=amount;
                n.setStartChar(start);
            }
            if(end>index) {
                end+=amount;
                n.setEndChar(end);
            }
        }
        
    }

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Hide toolbars
        hideHTMLEditorToolbars(area);
        
        model.currentDocumentProperty().addListener((e)->{
            Doc doc = model.getCurrentDocument();
            
            StringBuilder s = new StringBuilder(doc.getText());

            List<Card> cards = ActualAPI.getInstance().getCards(doc);
            List<Note> notes = ActualAPI.getInstance().getNote(doc);
            
            //Highlight all cards
            for(int i=0;i<cards.size();i++) {
                Card c = cards.get(i);
                int start = c.getStartChar();
                int end = c.getEndChar();

                s.insert(end,"</mark>");
                s.insert(start,"<mark>");
                
                updateOthers(end,7,cards,notes);
                updateOthers(start,6,cards,notes);
            }
            
            //Underline all notes         
            for(int i=0;i<notes.size();i++) {
                Note n = notes.get(i);
                int start = n.getStartChar();
                int end = n.getEndChar();

                s.insert(end,"</u>");
                s.insert(start,"<u>");
                
                updateOthers(end,4,cards,notes);
                updateOthers(start,3,cards,notes);
            }
            
            area.setHtmlText(s.toString());
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

        WebView webView = (WebView)area.lookup("WebView");
        WebEngine engine = webView.getEngine();
        Object selection = engine.executeScript(SELECT_TEXT);
        if (selection instanceof String) {
            selectedStr = (String) selection;
        }
        
        Object r1 = engine.executeScript(GET_START);
        int start = (int) r1;
        Object r2 = engine.executeScript(GET_END);
        int end = (int) r2;
        
        //System.out.println("Start: " + start + " End: " + end);
        
        Card wordcard = new Card();
        wordcard = ActualAPI.getInstance().createCard(model.getCurrentDocument(), wordcard);
        wordcard.setWordAsAppears(selectedStr);
        wordcard.setStartChar(start);
        wordcard.setEndChar(end);
        CardCreationController newCard = new CardCreationController(model.getCurrentDocument(), wordcard);
        
    }

    @FXML
    private void newCardDefineEvent() {
        String selectedStr = "";

        WebView webView = (WebView)area.lookup("WebView");
        WebEngine engine = webView.getEngine();
        Object selection = engine.executeScript(SELECT_TEXT);
        if (selection instanceof String) {
            selectedStr = (String) selection;
        }
        
        Object r1 = engine.executeScript(GET_START);
        int start = (int) r1;
        Object r2 = engine.executeScript(GET_END);
        int end = (int) r2;
        
        //System.out.println("Start: " + start + " End: " + end);
        
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
        //definecard.setGeneric("Test");
        definecard.setTransInContext(translate);
        definecard.setStartChar(start);
        definecard.setEndChar(end);
        // definecard.setPartOfSpeech("Test");
        // definecard.setOtherTrans("Test");
        CardCreationController newCard = new CardCreationController(model.getCurrentDocument(), definecard);
        
    }
}
