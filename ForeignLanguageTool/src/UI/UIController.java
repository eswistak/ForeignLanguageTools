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
import DataModel.LanguagePair;
import DataModel.Note;
import Logic.MockAPI;
import java.io.File;

import java.net.URL;

import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.fxml.Initializable;

import javafx.scene.control.Alert;

import javafx.scene.control.MenuItem;

import javafx.scene.control.SelectionMode;

import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;

import javafx.scene.control.TextArea;

import javafx.scene.control.TreeItem;

import javafx.scene.control.TreeView;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;

import javafx.stage.FileChooser;



/**

 * FXML Controller class

 *

 * @author Hyung Kang

 */

public class UIController implements Initializable {



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

    private TextArea textAreaMain;

    @FXML

    private TreeView<Item> treeViewMain;

    @FXML

    private TableView<TestItem> cardsTableView;

    @FXML

    private TableView<TestItem> notesTableView;

    @FXML

    private TableColumn<TestItem, String> cardsWordColumn;

    @FXML

    private TableColumn<TestItem, String> cardsDefinitionColumn;

    @FXML

    private TableColumn<TestItem, String> notesIndexColumn;

    @FXML

    private TableColumn<TestItem, String> notesNoteColumn;

    

    

    

    // other variables

    private File openedFile;

    private File importedFile;

    

    

    

    



    /**

     * Initializes the controller class.

     */

    @Override

    public void initialize(URL url, ResourceBundle rb) {

        System.out.println("INIT");

        textAreaMain.setWrapText(true);

        

        // testing tableView

        createCardTableView();

        createNoteTableView();

        

        // testing treeView

        treeViewTest();

        treeViewMain.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        

    }    



    @FXML

    private void menuFileNewDocEvent(ActionEvent event) {

        System.out.println("File -> New Document");

    }



    @FXML

    private void menuFileNewFlashcardEvent(ActionEvent event) {

        System.out.println("File -> New Falshcard");

    }



    @FXML

    private void menuFileSaveEvent(ActionEvent event) {

        System.out.println("File -> Save");

    }



    @FXML

    private void menuFileLoadEvent(ActionEvent event) {

        System.out.println("File -> Load");

        openedFile = openFileExplorer();

        

        System.out.println("LOAD: " + openedFile.getAbsolutePath());

    }

    

    @FXML

    private void menuFileCreateNewLangPairEvent(ActionEvent event) {

        System.out.println("File -> Create New Language Pair");

    }



    @FXML

    private void menuFileImportDocEvent(ActionEvent event) {

        System.out.println("File -> Import Document");

        importedFile = openFileExplorer();

        

        System.out.println("IMPORT: " + importedFile.getAbsolutePath());

    }

    

    @FXML

    private void menuFileExportDocEvent(ActionEvent event) {

        System.out.println("File -> Export Document");

    }



    @FXML

    private void menuFileSaveExitEvent(ActionEvent event) {

        System.out.println("File -> Save & Exit");

    }

    

    @FXML

    private void menuEditTextEvent(ActionEvent event) {

        System.out.println("Edit -> Text");

    }



    @FXML

    private void menuEditFlashcardEvent(ActionEvent event) {

        System.out.println("Edit -> Flashcard");

    }



    @FXML

    private void menuEditNoteEvent(ActionEvent event) {

        System.out.println("Edit -> Note");

    }



    @FXML

    private void menuViewCurrentDeckEvent(ActionEvent event) {

        System.out.println("View -> Current Deck");

    }



    @FXML

    private void menuViewQuizSettingEvent(ActionEvent event) {

        System.out.println("View -> Quiz Setting");

    }



    @FXML

    private void menuViewQuizEvent(ActionEvent event) {

        System.out.println("View -> Quiz");

    }



    @FXML

    private void menuHelpAboutEvent(ActionEvent event) {

        System.out.println("Help -> About");

    }

    

    @FXML

    private void treeViewMainSelectedEvent(MouseEvent event) {

        treeViewMain.getSelectionModel().selectedItemProperty().

            addListener(((observable, oldValue, newValue) -> handle(newValue)));

    }

    

   // open file explorer

    private File openFileExplorer() {

        FileChooser fc = new FileChooser();

        File file = fc.showOpenDialog(null);

        

        if (file == null) {

            System.out.println("File N/A!");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("Error");

            alert.setHeaderText(null);

            alert.setContentText("Please select file.");

            alert.showAndWait();

            

            file = openFileExplorer();

        }

        

        return file;

    }

    

    // treeview testing

    private void handle(TreeItem<Item> newValue) {
        
        if(newValue.getValue() instanceof Doc){

            textAreaMain.setText(((Doc)newValue.getValue()).getText());
        }

    }



    private void treeViewTest() {
        
        TreeItem<Item> root = new TreeItem(MockAPI.getInstance().getUser());
        
        for(LanguagePair langPair : MockAPI.getInstance().getLangPair()){
            TreeItem<Item> langItem = new TreeItem(langPair);
            root.getChildren().add(langItem);
            for(Group group : MockAPI.getInstance().getGroups(langPair)){
                TreeItem<Item> groupItem = new TreeItem(group);
                langItem.getChildren().add(groupItem);
                for(Doc doc : MockAPI.getInstance().getDocuments(group)){
                    TreeItem<Item> docItem = new TreeItem(doc);
                    groupItem.getChildren().add(docItem);
                }
            }
        }
        
        treeViewMain.setRoot(root);
        treeViewMain.setShowRoot(false);

    }





    // testing tableView

    private void createCardTableView() {

        cardsWordColumn.setCellValueFactory

                        (new PropertyValueFactory<>("name"));

        cardsDefinitionColumn.setCellValueFactory

                        (new PropertyValueFactory<>("value"));

        cardsTableView.setItems(getCardsData());

    }

    

    private void createNoteTableView() {

        notesIndexColumn.setCellValueFactory

                        (new PropertyValueFactory<>("name"));

        notesNoteColumn.setCellValueFactory

                        (new PropertyValueFactory<>("value"));

        notesTableView.setItems(getNotesData());

    }

    

    private ObservableList<Card> getCardsData() {

        ObservableList<TestItem> data = FXCollections.observableArrayList();

        data.add(new TestItem("Card 1", "Def 1"));

        data.add(new TestItem("Card 2", "Def 2"));

        data.add(new TestItem("Card 3", "Def 3"));

        data.add(new TestItem("Card 4", "Def 4"));

        

        return data;

    }



    private ObservableList<Note> getNotesData() {

        ObservableList<TestItem> data = FXCollections.observableArrayList();

        data.add(new TestItem("Index 1", "Note 1"));

        data.add(new TestItem("Index 2", "Note 2"));

        data.add(new TestItem("Index 3", "Note 3"));

        data.add(new TestItem("Index 4", "Note 4"));

        

        return data;

    }



    

}
