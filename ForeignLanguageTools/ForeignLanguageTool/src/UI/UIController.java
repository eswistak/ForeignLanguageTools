/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
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
    private TreeView<TestItem> treeViewMain;
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
    private void handle(TreeItem<TestItem> newValue) {
        textAreaMain.setText(newValue.getValue().toString());
    }

    private void treeViewTest() {
        TreeItem<TestItem> root = 
                new TreeItem<>(new TestItem("Root","The root"));
        TreeItem<TestItem> parent1 = 
                new TreeItem<>(new TestItem("PARENT 1", "This is parent 1"));
        TreeItem<TestItem> parent2 = 
                new TreeItem<>(new TestItem("PARENT 2", "This is parent 2"));
        
        TreeItem<TestItem> item1 = 
                new TreeItem<>(new TestItem("ITEM 1", "This is Item 1"));
        TreeItem<TestItem> item2 = 
                new TreeItem<>(new TestItem("ITEM 2", "This is Item 2"));
        TreeItem<TestItem> item3 = 
                new TreeItem<>(new TestItem("ITEM 3", "This is Item 3"));
        TreeItem<TestItem> item4 = 
                new TreeItem<>(new TestItem("ITEM 4", "This is Item 4"));
        TreeItem<TestItem> item5 = 
                new TreeItem<>(new TestItem("ITEM 5", "This is Item 5"));
        TreeItem<TestItem> item6 = 
                new TreeItem<>(new TestItem("ITEM 6", "This is Item 6"));
        TreeItem<TestItem> item7 = 
                new TreeItem<>(new TestItem("ITEM 7", "This is Item 7"));
        TreeItem<TestItem> item8 = 
                new TreeItem<>(new TestItem("ITEM 8", "This is Item 8"));      
        TreeItem<TestItem> item9 = 
                new TreeItem<>(new TestItem("ITEM 9", "This is Item 9"));
        TreeItem<TestItem> item10 = 
                new TreeItem<>(new TestItem("ITEM 10", "This is Item 10"));
        
        TreeItem<TestItem> item11 = 
                new TreeItem<>(new TestItem("ITEM 11", "This is Item 11"));
        TreeItem<TestItem> item12 = 
                new TreeItem<>(new TestItem("ITEM 12", "This is Item 12"));
        TreeItem<TestItem> item13 = 
                new TreeItem<>(new TestItem("ITEM 13", "This is Item 13"));
        TreeItem<TestItem> item14 = 
                new TreeItem<>(new TestItem("ITEM 14", "This is Item 14"));
        TreeItem<TestItem> item15 = 
                new TreeItem<>(new TestItem("ITEM 15", "This is Item 15"));
        TreeItem<TestItem> item16 = 
                new TreeItem<>(new TestItem("ITEM 16", "This is Item 16"));
        TreeItem<TestItem> item17 = 
                new TreeItem<>(new TestItem("ITEM 17", "This is Item 17"));
        TreeItem<TestItem> item18 = 
                new TreeItem<>(new TestItem("ITEM 18", "This is Item 18"));
        TreeItem<TestItem> item19 = 
                new TreeItem<>(new TestItem("ITEM 19", "This is ITem 19"));
        TreeItem<TestItem> item20 = 
                new TreeItem<>(new TestItem("ITEM 20", "This is Item 20"));
        
        parent1.getChildren().setAll(item1, item2, item3, item4, item5,
                                     item6, item7, item8, item9, item10);
        parent2.getChildren().setAll(item11, item12, item13, item14, item15,
                                     item16, item17, item18, item19, item20);
        root.getChildren().setAll(parent1, parent2);
                
        treeViewMain.setRoot(root);
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
    
    private ObservableList<TestItem> getCardsData() {
        ObservableList<TestItem> data = FXCollections.observableArrayList();
        data.add(new TestItem("Card 1", "Def 1"));
        data.add(new TestItem("Card 2", "Def 2"));
        data.add(new TestItem("Card 3", "Def 3"));
        data.add(new TestItem("Card 4", "Def 4"));
        
        return data;
    }

    private ObservableList<TestItem> getNotesData() {
        ObservableList<TestItem> data = FXCollections.observableArrayList();
        data.add(new TestItem("Index 1", "Note 1"));
        data.add(new TestItem("Index 2", "Note 2"));
        data.add(new TestItem("Index 3", "Note 3"));
        data.add(new TestItem("Index 4", "Note 4"));
        
        return data;
    }

    
}
