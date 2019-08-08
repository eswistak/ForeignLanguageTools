/*

 * To change this license header, choose License Headers in Project Properties.

 * To change this template file, choose Tools | Templates

 * and open the template in the editor.

 */
package UI;

import DataModel.Card;
import DataModel.Doc;
import DataModel.Group;
import DataModel.Item;
import DataModel.User;
import DataModel.LanguagePair;
import DataModel.Note;
import DataModel.Utils;
import Logic.ActualAPI;
import Logic.Define;
import static Testing.SelectionExtractor.SELECT_TEXT;
import UI.CardCreationController;
import controller.TestItem;
import java.awt.Color;
import java.awt.Paint;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

/**
 *
 * FXML Controller class
 *
 *
 *
 * @author Hyung Kang
 *
 */
public class UIController implements Initializable {

    @FXML
    Menu languagesMenu;

    //-------Text Field Context Menu Fields------
    @FXML
    MenuItem newMiscFlashcard;

    @FXML
    MenuItem newWordFlashcard;

    @FXML
    MenuItem newWordDefFlashcard;

    @FXML
    MenuItem newMiscNote;

    @FXML
    MenuItem newPhraseNote;

    private static final String SELECT_TEXT
            = "(function getSelectionText() {\n"
            + "    var text = \"\";\n"
            + "    if (window.getSelection) {\n"
            + "        text = window.getSelection().toString();\n"
            + "    } else if (document.selection && document.selection.type != \"Control\") {\n"
            + "        text = document.selection.createRange().text;\n"
            + "    }\n"
            + "    if (window.getSelection) {\n"
            + "      if (window.getSelection().empty) {  // Chrome\n"
            + "        window.getSelection().empty();\n"
            + "      } else if (window.getSelection().removeAllRanges) {  // Firefox\n"
            + "        window.getSelection().removeAllRanges();\n"
            + "      }\n"
            + "    } else if (document.selection) {  // IE?\n"
            + "      document.selection.empty();\n"
            + "    }"
            + "    return text;\n"
            + "})()";

    private static final String SELECTED_TEXT_START
            = "(function getSelectionText() {\n"
            + "    var text = \"\";\n"
            + "    if (window.getSelection) {\n"
            + "        text = window.getSelection().getRangeAt(0).startOffset;\n"
            + "    } else if (document.selection && document.selection.type != \"Control\") {\n"
            + "        text = document.selection.getRangeAt(0).startOffset;\n"
            + "    }\n"
            + "    if (window.getSelection) {\n"
            + "      if (window.getSelection().empty) {  // Chrome\n"
            + "        window.getSelection().empty();\n"
            + "      } else if (window.getSelection().removeAllRanges) {  // Firefox\n"
            + "        window.getSelection().removeAllRanges();\n"
            + "      }\n"
            + "    } else if (document.selection) {  // IE?\n"
            + "      document.selection.empty();\n"
            + "    }"
            + "    return text;\n"
            + "})()";
    //***********Text Field Context Menu Fields*********
    @FXML

    private MenuItem menuFileNewDoc;

    @FXML

    private MenuItem menuFileNewFlashcard;

    @FXML

    private MenuItem menuFileSave;

    @FXML

    private MenuItem menuFileLoad;

    @FXML

    private MenuItem menuFileCreateNewLangPair;

    @FXML

    private MenuItem menuFileImportDoc;

    @FXML

    private MenuItem menuFileExportDoc;

    @FXML

    private MenuItem menuFileSaveExit;

    @FXML

    private MenuItem menuEditText;

    @FXML

    private MenuItem menuEditFlashcard;

    @FXML

    private MenuItem menuEditNote;

    @FXML

    private MenuItem menuViewCurrentDeck;

    @FXML

    private MenuItem menuViewQuizSetting;

    @FXML

    private MenuItem menuViewQuiz;

    @FXML

    private MenuItem menuHelpAbout;

    @FXML

    //private TextArea textAreaMain;
    private HTMLEditor textAreaMain;

    @FXML

    protected TreeView<Item> treeViewMain;

    @FXML

    private TableView<Card> cardsTableView;

    @FXML

    private TableView<Note> notesTableView;

    @FXML

    private TableColumn<TestItem, String> cardsWordColumn;

    @FXML

    private TableColumn<TestItem, String> cardsDefinitionColumn;

    @FXML

    private TableColumn<TestItem, String> notesIndexColumn;

    @FXML

    private TableColumn<TestItem, String> notesNoteColumn;

    public LanguagePair viewingLanguage;

    public Doc viewingDoc;

    // other variables
    private File openedFile;

    private File importedFile;

    private TreeItem<Item> root;

    /**
     *
     * Initializes the controller class.
     *
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {

        System.out.println("INIT");

        //textAreaMain.setWrapText(true);
        // don't let user edit the text-area
        //textAreaMain.setEditable(false);
        treeViewMain.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        hideHTMLEditorToolbars(textAreaMain);
        setTableDoubleClick(cardsTableView);

        setTableDoubleClick(notesTableView);
        Define.setLangCodes();

        List<LanguagePair> pairs = ActualAPI.getInstance().getLangPair();
        for (LanguagePair pair : pairs) {
            setLanguagePairMenuItem(pair);
        }
    }
    //TODO Actually implement any method with a println

    /* HIDES THE HTML TOOLBAR */
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
    
    private void setLanguagePairMenuItem(LanguagePair pair) {
        MenuItem item = new MenuItem();
        item.setText(pair.getNat() + "->" + pair.getTarget());
        item.getProperties().put(LanguagePair.class, pair);
        item.setId(String.valueOf(pair.getID()));
        item.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                viewingLanguage = (LanguagePair)item.getProperties().get(LanguagePair.class);
                buildTreeView();
            }
        });
        languagesMenu.getItems().add(item);
    }

    private void setTableDoubleClick(TableView table) {
        table.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    if (table.equals(cardsTableView)) {
                        menuEditFlashcardEvent();
                    }
                    if (table.equals(notesTableView)) {
                        menuEditNoteEvent();
                    }
                    //System.out.println(table.getSelectionModel().getSelectedItem());                   
                }
            }
        });
    }

    @FXML
    //TODO Implement Hyung Kang
    private void menuFileNewDocEvent(ActionEvent event) {

        TreeItem<Item> newItem = treeViewMain.getSelectionModel().getSelectedItem();

        // check if treeview is selected or not
        if (newItem == null || newItem.getValue() instanceof LanguagePair == false) {
            popUpDialog("Please select the language pair first from the tree view.");
        } else if (newItem.getValue() instanceof LanguagePair) {
            System.out.println("File -> New Document");
            // add new doc to the current language pair
            // New dialog
            // ....
            // pop-up window
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("New Document");
            dialog.setHeaderText("Create new Document");

            // button
            ButtonType buttonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            // text-field
            TextField newDocName = new TextField();

            grid.add(new Label("New Document Name:"), 0, 0);
            grid.add(newDocName, 1, 0);
            dialog.getDialogPane().setContent(grid);

            // request focus on the native field by default
            Platform.runLater(() -> newDocName.requestFocus());

            Optional<String> result = dialog.showAndWait();

            if (result.isPresent()) {
                TreeItem<Item> targetLanguagePair = treeViewMain.getSelectionModel().getSelectedItem();

            }
        }
    }

    @FXML
    private void menuFlashcardWordEvent(ActionEvent event) {
        String selectedStr = "";
        
        WebView webView = (WebView) textAreaMain.lookup("WebView");
        if (webView != null) {
            WebEngine engine = webView.getEngine();
            Object selection = engine.executeScript(SELECT_TEXT);
            if (selection instanceof String) {
                selectedStr = (String) selection;
            }
//            Object wordStart = engine.executeScript(SELECTED_TEXT_START);
//            System.out.println(wordStart);
//            if (selection instanceof String) {
//                selectedStr = (String) selection;
//            }
        }

        Card wordcard = new Card();
        wordcard = ActualAPI.getInstance().createCard(viewingDoc, wordcard);
        wordcard.setWordAsAppears(selectedStr);
        CardCreationController newCard = new CardCreationController(viewingDoc, wordcard);
        
    }
    @FXML
    private void menuFileNewFlashcardEvent(ActionEvent event) {

        CardCreationController newCard = new CardCreationController(viewingDoc);
    }

    @FXML
    private void menuFlashcardDefineEvent(ActionEvent event) {
        String selectedStr = "";
        
        WebView webView = (WebView) textAreaMain.lookup("WebView");
        if (webView != null) {
            WebEngine engine = webView.getEngine();
            Object selection = engine.executeScript(SELECT_TEXT);
            if (selection instanceof String) {
                selectedStr = (String) selection;
            }
//            Object wordStart = engine.executeScript(SELECTED_TEXT_START);
//            System.out.println(wordStart);
//            if (selection instanceof String) {
//                selectedStr = (String) selection;
//            }
        }

        Card definecard = new Card();
        definecard = ActualAPI.getInstance().createCard(viewingDoc, definecard);
        String natural = viewingLanguage.getNat().toUpperCase();
        String target = viewingLanguage.getTarget().toUpperCase();
        List<String> LangStr = Define.getLangCodes();
        Map<String, String> LangCodes = new HashMap<String, String>();
        System.out.println(LangStr.size());
        for (String lang : LangStr) {
            lang = lang.replaceAll("\'","");
            String[] langVals = lang.split(":");
        
            LangCodes.put(langVals[0].toUpperCase(),langVals[1].toUpperCase());
        }
        
        String naturalCode = LangCodes.get(natural);
        String targetCode =  LangCodes.get(target);

        String translate = Define.getDefinition(targetCode, naturalCode, selectedStr);
        translate = translate.substring(4, translate.indexOf("\","));
        definecard.setWordAsAppears(selectedStr);
        // definecard.setGeneric("Test");
        definecard.setTransInContext(translate);
        // definecard.setPartOfSpeech("Test");
        // definecard.setOtherTrans("Test");
        CardCreationController newCard = new CardCreationController(viewingDoc, definecard);
        
    }
    
    @FXML
    //TODO Implement Matt Rieser
    private void menuFileSaveEvent(ActionEvent event) {

        try {
            Utils.save();
        } catch (ParserConfigurationException ex) {
            System.out.println("Parser not configured correctly");
        } catch (SAXException ex) {
            System.out.println("SAX messed up");
        } catch (IOException ex) {
            System.out.println("The file was not found");
        } catch (TransformerException ex) {
            System.out.println("Transformer exception");
        }
    }

    @FXML
    private void menuFileLoadEvent(ActionEvent event) {
        openedFile = openFileExplorer();

        try {
            Utils.load(openedFile.getAbsolutePath());
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(UIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(UIController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UIController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    @FXML
    private void setNativeLanguage(){
    }
    
    @FXML
    private void deleteLanguagePair(){
        MenuItem itemToRemove = null;
        for(MenuItem item : languagesMenu.getItems()){
            int id = viewingLanguage.getID();
            if(item.getId() == null){
                
            }else if(item.getId().equals(String.valueOf(id))){
                itemToRemove = item;
            }
        }
        languagesMenu.getItems().remove(itemToRemove);
        ActualAPI.getInstance().deleteLangPair(viewingLanguage);
        buildTreeView();
        textAreaMain.setHtmlText("");
        viewingDoc = null;
    }

    @FXML
    //TODO Implement Hyung Kang
    private void menuFileCreateNewLangPairEvent(ActionEvent event) {
        LanguagePair newLangPair = new LanguagePair();

        // pop-up window
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("New Language Pair");
        dialog.setHeaderText("Create New Language Pair");

        // button
        ButtonType buttonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // text-field for native and target variables
        TextField nativeTextField = new TextField();
        nativeTextField.setPromptText("Native");
        TextField targetTextField = new TextField();
        targetTextField.setPromptText("Target");

        grid.add(new Label("Native:"), 0, 0);
        grid.add(nativeTextField, 1, 0);
        grid.add(new Label("Target:"), 0, 1);
        grid.add(targetTextField, 1, 1);
        dialog.getDialogPane().setContent(grid);

        // request focus on the native field by defualt
        Platform.runLater(() -> nativeTextField.requestFocus());

        // convert the result to a native-target when the ok button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == buttonType) {
                return new Pair<>(nativeTextField.getText(), targetTextField.getText());
            }
            return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();

        // check result
        result.ifPresent((Pair<String, String> pair) -> {
            // check if text-field is empty
            if (pair.getKey().isEmpty() || pair.getValue().isEmpty()) {
                System.out.println("Empty Field N/A!");
                popUpDialog("Please enter the native and target language.");
            } else {
                // set new language pair
                newLangPair.setNat(pair.getKey());
                newLangPair.setTarget(pair.getValue());

                TreeItem<Item> newPair = new TreeItem<>(newLangPair);
                root.getChildren().add(newPair);
                setLanguagePairMenuItem(newLangPair);
                System.out.println("Native: " + pair.getKey() + "\nTarget: " + pair.getValue());
            }
        });

        System.out.println("File -> Create New Language Pair");
    }

    @FXML
    //TODO Implement Matt Rieser
    private void menuFileImportDocEvent(ActionEvent event) {

        System.out.println("File -> Import Document");

        importedFile = openFileExplorer();

        System.out.println("IMPORT: " + importedFile.getAbsolutePath());

    }

    @FXML
    //TODO Implement Matt Rieser
    private void menuFileExportDocEvent(ActionEvent event) {

        DirectoryChooser dChooser = new DirectoryChooser();
        File chosen = dChooser.showDialog(null);

        try {
            Utils.save(chosen.getAbsolutePath());
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }

    }

    @FXML
    //TODO Implement Matt Rieser
    private void menuFileSaveExitEvent(ActionEvent event) {

        try {
            Utils.save();
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (TransformerException ex) {
            ex.printStackTrace();
        }
        
        ((Stage)this.cardsTableView.getScene().getWindow()).close();

    }

    @FXML
    //TODO Implement Hyung Kang
    private void menuEditTextEvent(ActionEvent event) {
        if (textAreaMain.getHtmlText().isEmpty()) {
            popUpDialog("Please select the text first.");
            return;
        }

        // pop-up window
        Dialog<String> dialog = new Dialog();
        dialog.setTitle("Edit Text");
        dialog.setHeaderText("Edit Text");

        // button
        ButtonType buttonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        // text-area
        TextArea newTextArea = new TextArea();
        newTextArea.setWrapText(true);
        newTextArea.setText(textAreaMain.getHtmlText());

        grid.add(newTextArea, 0, 0);
        dialog.getDialogPane().setContent(grid);

        // request focus on the native field by defualt
        Platform.runLater(() -> newTextArea.requestFocus());

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent() && !newTextArea.getText().isEmpty()) {
            // update the current text document 
            Doc newDoc = (Doc) treeViewMain.getSelectionModel().getSelectedItem().getValue();
            newDoc.setText(newTextArea.getText());
            textAreaMain.setHtmlText(newDoc.getText());

            System.out.println("New Text:\n" + newTextArea.getText());
        } else {
            System.out.println("Edit text field is empty.");
            popUpDialog("Text field is empty");
            return;
        }

        System.out.println("Edit -> Text");
    }

    @FXML
    private void menuEditFlashcardEvent() {

        if (viewingDoc == null) {

        } else {
            Card viewingCard = this.cardsTableView.getSelectionModel().getSelectedItem();
            CardCreationController subWindow = new CardCreationController(this.viewingDoc, viewingCard);
        }
    }

    @FXML
    //TODO Implement Hyung Kang, we don't yet have a note edit screen, should build one
    private void menuEditNoteEvent() {

        Note n = this.notesTableView.getSelectionModel().getSelectedItem();

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

        n.setContent(newTextArea.getText());
    }

    @FXML
    //TODO Implement Tim Waite
    private void menuViewCurrentDeckEvent(ActionEvent event) {
        System.out.println(event.getSource());
        Stage stage = (Stage) textAreaMain.getScene().getWindow();
        TableColumn<TestItem, String> popupWordColumn = new TableColumn("Word");
        TableColumn<TestItem, String> popupDefinitionColumn = new TableColumn("Definition");
        TableColumn<TestItem, String> popupGenericColumn = new TableColumn("Generic");
        System.out.println("View -> Current Deck");
        TreeItem selection = treeViewMain.getSelectionModel().selectedItemProperty().getValue();
        Popup popup = new Popup();
        popup.autoHideProperty().setValue(true);
        popup.centerOnScreen();
        TableView popupTableView = new TableView();
        ObservableList<Card> data = null;
        if (selection != null && !(selection.getValue() instanceof User)) {
            Doc doc = getDocParent(selection);
            if (null == doc) {
                LanguagePair lang = getLangParent(selection);
                data = getCardsData(lang);
            } else {
                data = getCardsData(doc);
            }
            popupTableView.getColumns().add(popupWordColumn);
            popupTableView.getColumns().add(popupDefinitionColumn);
            popupTableView.getColumns().add(popupGenericColumn);
            popupWordColumn.setCellValueFactory(new Callback() {
                @Override
                public Object call(Object obj) {
                    final Object dataObj = ((TableColumn.CellDataFeatures) obj).getValue();
                    if (dataObj instanceof Card) {
                        return new ReadOnlyStringWrapper(String.valueOf(((Card) dataObj).getWordAsAppears()));
                    }
                    return null;
                }
            });

            popupDefinitionColumn.setCellValueFactory(new Callback() {
                @Override
                public Object call(Object obj) {
                    final Object dataObj = ((TableColumn.CellDataFeatures) obj).getValue();
                    if (dataObj instanceof Card) {
                        return new ReadOnlyStringWrapper(String.valueOf(((Card) dataObj).getTransInContext()));
                    }
                    return null;
                }
            });
            popupGenericColumn.setCellValueFactory(new Callback() {
                @Override
                public Object call(Object obj) {
                    final Object dataObj = ((TableColumn.CellDataFeatures) obj).getValue();
                    if (dataObj instanceof Card) {
                        return new ReadOnlyStringWrapper(String.valueOf(((Card) dataObj).getGeneric()));
                    }
                    return null;
                }
            });
            popupTableView.setItems(data);
            popup.getContent().add(popupTableView);
            popup.show(stage);
        }
    }

    @FXML
    //TODO Remove this from fxml as well
    private void menuViewQuizSettingEvent(ActionEvent event) {

        System.out.println("View -> Quiz Setting");

    }

    //Get the parent of document being viewed or parent of group selected
    //pass to getallcards
    //pass result to quiz controller;
    @FXML
    private void menuViewQuizEvent(ActionEvent event) {
        LanguagePair LangPair = viewingLanguage;
        if (viewingLanguage != null) {

            //System.out.println(System.getProperty("user.dir"));
            QuizController cntrl = new QuizController(ActualAPI.getInstance().getAllCards(LangPair));
            try {
                openPopup("/UI/quiz.fxml", cntrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    @FXML

    private void menuHelpAboutEvent(ActionEvent event) {

        Stage popupwindow=new Stage();
      
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("About");
        
        VBox layout = new VBox();
        WebView view = new WebView();
        URL url = UIController.class.getClassLoader().getResource("UI/Help/About.html");
        view.getEngine().load(url.toString());
        
        layout.getChildren().add(view);
        Scene scene = new Scene(layout, 800.0, 800.0);
        popupwindow.setScene(scene);
        popupwindow.showAndWait();

    }

    @FXML

    private void treeViewMainSelectedEvent(MouseEvent event) {

        treeViewMain.getSelectionModel().selectedItemProperty().
                addListener(((observable, oldValue, newValue) -> handleTreeViewMain(newValue)));

    }

    // new notes tableview event
    @FXML

    private void notesTableViewSelectedEvent(MouseEvent event) {
        notesTableView.getSelectionModel().selectedItemProperty().
                addListener(((observable, oldValue, newValue) -> handleNotesTableView(newValue)));
    }
    
    @FXML
    private void notesDeleteEvent(){
        Note note = notesTableView.getSelectionModel().getSelectedItem();
        ActualAPI.getInstance().deleteNote(note);
        notesTableView.getItems().remove(note);
    }
    
    @FXML
    private void cardDeleteEvent(){
        Card card = cardsTableView.getSelectionModel().getSelectedItem();
        ActualAPI.getInstance().deleteCard(card);
        cardsTableView.getItems().remove(card);
    }

    // treeview handling
    private void handleTreeViewMain(TreeItem<Item> newValue) {

        if (newValue == null) {
            textAreaMain.setHtmlText("");
        } else if (newValue.getValue() instanceof Doc) {

            Doc doc = (Doc) newValue.getValue();
            doc.getID();
            this.viewingDoc = doc;

            StringBuilder s = new StringBuilder(doc.getText());

            List<Card> cards = ActualAPI.getInstance().getCards(doc);
            List<Note> notes = ActualAPI.getInstance().getNote(doc);

            //Highlight all cards
            for (int i = 0; i < cards.size(); i++) {
                Card c = cards.get(i);
                int start = c.getStartChar();
                int end = c.getEndChar();

                s.insert(end, "</mark>");
                s.insert(start, "<mark>");

                updateOthers(end, 7, cards, notes);
                updateOthers(start, 6, cards, notes);
            }

            //Underline all notes         
            for (int i = 0; i < notes.size(); i++) {
                Note n = notes.get(i);
                int start = n.getStartChar();
                int end = n.getEndChar();

                s.insert(end, "</u>");
                s.insert(start, "<u>");

                updateOthers(end, 4, cards, notes);
                updateOthers(start, 3, cards, notes);
            }

            textAreaMain.setHtmlText(s.toString());

            createCardTableView(doc);
            createNoteTableView(doc);
        }

    }

    //Helps format the text for handleTreeViewMain
    private void updateOthers(int index, int amount, List<Card> cards, List<Note> notes) {
        for (int i = 0; i < cards.size(); i++) {
            Card c = cards.get(i);
            int start = c.getStartChar();
            int end = c.getEndChar();

            if (start > index) {
                start += amount;
                c.setStartChar(start);
            }
            if (end > index) {
                end += amount;
                c.setEndChar(end);
            }
        }
        for (int i = 0; i < notes.size(); i++) {
            Note n = notes.get(i);
            int start = n.getStartChar();
            int end = n.getEndChar();
            if (start > index) {
                start += amount;
                n.setStartChar(start);
            }
            if (end > index) {
                end += amount;
                n.setEndChar(end);
            }
        }

    }

    // need to work on this method after Note-edit screen
    private void handleNotesTableView(Note newValue) {
            
    }

    // open file explorer
    private File openFileExplorer() {

        FileChooser fc = new FileChooser();

        File file = fc.showOpenDialog(null);

        if (file == null) {

            System.out.println("File N/A!");

            popUpDialog("Please select the file.");

            file = openFileExplorer();

        }

        return file;

    }

    private void buildTreeView() {

        /* I made a root private instance variable so that when
        "Create new Language Pair" is pressed, new language pair can be
        added into root treeview. -Hyung Kang-
         */
        root = new TreeItem(ActualAPI.getInstance().getUser());

        for (Group group : ActualAPI.getInstance().getGroups(viewingLanguage)) {
            TreeItem<Item> groupItem = new TreeItem(group);
            root.getChildren().add(groupItem);
            for (Doc doc : ActualAPI.getInstance().getDocuments(group)) {
                TreeItem<Item> docItem = new TreeItem(doc);
                groupItem.getChildren().add(docItem);
            }
        }

        treeViewMain.setRoot(root);
        treeViewMain.setShowRoot(false);

    }

    // testing tableView
    private void createCardTableView(Doc doc) {

        cardsWordColumn.setCellValueFactory(new Callback() {
            @Override
            public Object call(Object obj) {
                final Object dataObj = ((TableColumn.CellDataFeatures) obj).getValue();
                if (dataObj instanceof Card) {
                    return new ReadOnlyStringWrapper(String.valueOf(((Card) dataObj).getWordAsAppears()));
                }
                return null;
            }
        });

        cardsDefinitionColumn.setCellValueFactory(new Callback() {
            @Override
            public Object call(Object obj) {
                final Object dataObj = ((TableColumn.CellDataFeatures) obj).getValue();
                if (dataObj instanceof Card) {
                    return new ReadOnlyStringWrapper(String.valueOf(((Card) dataObj).getTransInContext()));
                }
                return null;
            }
        });

        cardsTableView.setItems(getCardsData(doc));

    }

    private void createNoteTableView(Doc doc) {

        notesIndexColumn.setCellValueFactory(new Callback() {
            @Override
            public Object call(Object obj) {
                final Object dataObj = ((TableColumn.CellDataFeatures) obj).getValue();
                if (dataObj instanceof Note) {
                    return new ReadOnlyStringWrapper(String.valueOf(((Note) dataObj).getIndex()));
                }
                return null;
            }
        });

        notesNoteColumn.setCellValueFactory(new Callback() {
            @Override
            public Object call(Object obj) {
                final Object dataObj = ((TableColumn.CellDataFeatures) obj).getValue();
                if (dataObj instanceof Note) {
                    return new ReadOnlyStringWrapper(String.valueOf(((Note) dataObj).getContent()));
                }
                return null;
            }
        });

        notesTableView.setItems(getNotesData(doc));

    }

    private ObservableList<Card> getCardsData(Doc doc) {

        ObservableList<Card> data = FXCollections.observableArrayList();

        List<Card> cards = ActualAPI.getInstance().getCards(doc);

        data.addAll(cards);

        return data;

    }

    private ObservableList<Note> getNotesData(Doc doc) {

        ObservableList<Note> data = FXCollections.observableArrayList();

        List<Note> notes = ActualAPI.getInstance().getNote(doc);

        data.addAll(notes);

        return data;

    }

    private void popUpDialog(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Error");

        alert.setHeaderText(null);

        alert.setContentText(content);

        alert.showAndWait();
    }

    public void openPopup(String fxmlPath, Object cntrl) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            fxmlLoader.setController(cntrl);
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LanguagePair getLangParent(TreeItem selection) {

        TreeItem returnVal = selection;
        while (!(returnVal.getValue() instanceof LanguagePair)) {
            if (null != returnVal.getParent()) {
                returnVal = returnVal.getParent();
            } else {
                return null;
            }
        }
        return (LanguagePair) returnVal.getValue();
    }

    public Doc getDocParent(TreeItem selection) {

        TreeItem returnVal = selection;
        while (!(returnVal.getValue() instanceof Doc)) {
            if (null != returnVal.getParent()) {
                returnVal = returnVal.getParent();
            } else {
                return null;
            }
        }
        return (Doc) returnVal.getValue();
    }

    private ObservableList<Card> getCardsData(LanguagePair lang) {

        ObservableList<Card> data = FXCollections.observableArrayList();

        List<Card> cards = ActualAPI.getInstance().getAllCards(lang);

        data.addAll(cards);

        return data;

    }
    

}
