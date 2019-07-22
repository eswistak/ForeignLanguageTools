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
import Logic.ActualAPI;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;



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

//        createCardTableView();
//
//        createNoteTableView();

        

        // testing buildTreeView

        buildTreeView();

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

    
    // treeview handling

    private void handle(TreeItem<Item> newValue) {
        
        if(newValue.getValue() instanceof Doc){
            
            Doc doc = (Doc)newValue.getValue();

            textAreaMain.setText(doc.getText());
            createCardTableView(doc);
            createNoteTableView(doc);
        }

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

    




    private void buildTreeView() {
        
        TreeItem<Item> root = new TreeItem(ActualAPI.getInstance().getUser());
        
        for(LanguagePair langPair : ActualAPI.getInstance().getLangPair()){
            TreeItem<Item> langItem = new TreeItem(langPair);
            root.getChildren().add(langItem);
            for(Group group : ActualAPI.getInstance().getGroups(langPair)){
                TreeItem<Item> groupItem = new TreeItem(group);
                langItem.getChildren().add(groupItem);
                for(Doc doc : ActualAPI.getInstance().getDocuments(group)){
                    TreeItem<Item> docItem = new TreeItem(doc);
                    groupItem.getChildren().add(docItem);
                }
            }
        }
        
        treeViewMain.setRoot(root);
        treeViewMain.setShowRoot(false);

    }





    // testing tableView

    private void createCardTableView(Doc doc) {

        cardsWordColumn.setCellValueFactory(new Callback(){
            @Override
            public Object call(Object obj){
                final Object dataObj = ((TableColumn.CellDataFeatures) obj).getValue();
                if(dataObj instanceof Card){
                    return new ReadOnlyStringWrapper(String.valueOf(((Card)dataObj).getWordAsAppears()));
                }
                return null;
            }
        });

        cardsDefinitionColumn.setCellValueFactory(new Callback(){
            @Override
            public Object call(Object obj){
                final Object dataObj = ((TableColumn.CellDataFeatures) obj).getValue();
                if(dataObj instanceof Card){
                    return new ReadOnlyStringWrapper(String.valueOf(((Card)dataObj).getTransInContext()));
                }
                return null;
                }
        });

        cardsTableView.setItems(getCardsData(doc));

    }

    

    private void createNoteTableView(Doc doc) {

        notesIndexColumn.setCellValueFactory(new Callback(){
            @Override
            public Object call(Object obj){
                final Object dataObj = ((TableColumn.CellDataFeatures) obj).getValue();
                if(dataObj instanceof Note){
                    return new ReadOnlyStringWrapper(String.valueOf(((Note)dataObj).getIndex()));
                }
                return null;
                }
        });

                        

        notesNoteColumn.setCellValueFactory(new Callback(){
            @Override
            public Object call(Object obj){
                final Object dataObj = ((TableColumn.CellDataFeatures) obj).getValue();
                if(dataObj instanceof Note){
                    return new ReadOnlyStringWrapper(String.valueOf(((Note)dataObj).getContent()));
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



    

}
