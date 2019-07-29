/*

 * To change this license header, choose License Headers in Project Properties.

 * To change this template file, choose Tools | Templates

 * and open the template in the editor.

 */

package controller;



import DataModel.Card;
import DataModel.Doc;
import DataModel.Group;
import DataModel.Item;
import DataModel.LanguagePair;
import DataModel.Note;
import DataModel.Utils;
import Logic.ActualAPI;
import UI.CardCreationController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;


/**

 * FXML Controller class

 *

 * @author Hyung Kang

 */

public class UIController implements Initializable {
    private Text actionStatus;
    private Stage savedStage;

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
    
    public Doc viewingDoc;


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
    //TODO Actually implement any method with a println


    @FXML
    //TODO Implement Hyung Kang
    private void menuFileNewDocEvent(ActionEvent event) {

        System.out.println("File -> New Document");

    }



    @FXML
    private void menuFileNewFlashcardEvent(ActionEvent event){

        CardCreationController newCard = new CardCreationController(viewingDoc);
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
            System.out.println("TransformerS! More than meets the eye!");
        }

    }



    @FXML
    private void menuFileLoadEvent(ActionEvent event) {

        System.out.println("File -> Load");

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
    //TODO Implement Hyung Kang
    private void menuFileCreateNewLangPairEvent(ActionEvent event) {

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

        System.out.println("File -> Export Document");
        
        
        
        
        
       

    }



    @FXML
    //TODO Implement Matt Rieser
    private void menuFileSaveExitEvent(ActionEvent event) {

        System.out.println("File -> Save & Exit");
        
        
        
        

    }

    

    @FXML
    //TODO Implement Hyung Kang
    private void menuEditTextEvent(ActionEvent event) {

        System.out.println("Edit -> Text");

    }



    @FXML
    private void menuEditFlashcardEvent(ActionEvent event) {
        
        if(viewingDoc == null){
            
        }else{
            CardCreationController subWindow = new CardCreationController(this.viewingDoc, this.cardsTableView.getSelectionModel().getSelectedItem());
        }
    }



    @FXML
    //TODO Implement Hyung Kang, we don't yet have a note edit screen, should build one
    private void menuEditNoteEvent(ActionEvent event) {

        System.out.println("Edit -> Note");

    }



    @FXML
    //TODO Implement Tim Waite
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
//        TreeItem selection = treeViewMain.getSelectionModel().selectedItemProperty().getValue();
//        LanguagePair LangPair = null;
//        if(selection != null && !(selection.getValue() instanceof User)) {
//            LangPair = getLangParent(selection);
//            
//        }
//        ;
//        //Get the parent of document being viewed or parent of group selected
//        //pass to getallcards
//        //pass result to quiz controller;
//        QuizController cntrl = new QuizController(ActualAPI.getInstance().getAllCards(LangPair));
//        try{
//        openPopup("/UI/quiz.fxml", cntrl);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
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
            doc.getID();
            this.viewingDoc = doc;

            textAreaMain.setText(doc.getText());
            createCardTableView(doc);
            createNoteTableView(doc);
        }

    }
    
    @FXML
    public void notesTableViewSelectedEvent(){
        System.out.println("Notes Table View Selected");
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
    
    private void showSaveFileChooser() {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save file");
		File savedFile = fileChooser.showSaveDialog(savedStage);

		if (savedFile != null) {

			try {
				Utils.save(savedFile.getAbsolutePath());
			}
			catch(IOException e) {
			
				e.printStackTrace();
				actionStatus.setText("An ERROR occurred while saving the file!" +
						savedFile.toString());
				return;
			} catch (ParserConfigurationException ex) {
                        Logger.getLogger(UIController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SAXException ex) {
                        Logger.getLogger(UIController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (TransformerException ex) {
                        Logger.getLogger(UIController.class.getName()).log(Level.SEVERE, null, ex);
                    }
			
			actionStatus.setText("File saved: " + savedFile.toString());
		}
		else {
			actionStatus.setText("File save cancelled.");
		}
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
    
    public void openPopup(String fxmlPath, Object cntrl) throws Exception {               
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            fxmlLoader.setController(cntrl);
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));  
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public LanguagePair getLangParent(TreeItem selection){
    
        TreeItem returnVal = selection;
        while (!(returnVal.getValue() instanceof LanguagePair)){
            returnVal = returnVal.getParent();
        }
        return (LanguagePair) returnVal.getValue();
    }

}
