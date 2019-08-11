/*
File: Model.java
Author: Ethan Swistak
Date: Aug 4, 2019
Purpose: The GUI components all share a single data source that they can subscribe to in order to listen for changes. This allows us to break up the GUI into components
and smoothly update as the state of the GUI changes. For ObservableProperties, I found that have a objectProperty() method was sufficient, but since I've been
indoctrinated into Java, I felt like we needed getters and setters. You could probably cut this class down a lot without those.
*/

package UI.Model;

import DataModel.DTO.Doc;
import DataModel.DTO.Item;
import DataModel.DTO.LanguagePair;
import DataModel.DTO.Note;
import DataModel.DTO.Card;
import DataModel.*;
import java.util.*;
import javafx.collections.*;
import javafx.beans.property.*;
import javafx.scene.control.TreeItem;


public class Model {
    
//Backing objects for selection
    //language that is currently selected
    private final ObjectProperty<LanguagePair> currentLanguage = new SimpleObjectProperty();
    //group that is currently selected
    private final ObjectProperty<Item> currentTreeItem = new SimpleObjectProperty();
    //note that is currently selected
    private final ObjectProperty<Note> currentNote = new SimpleObjectProperty();
    //card that is currently selected
    private final ObjectProperty<Card> currentCard = new SimpleObjectProperty();
    //root of tree view that is being displayed
    private final ObjectProperty<TreeItem> treeViewRoot = new SimpleObjectProperty();
    //document that is currently being displayed
    private final ObjectProperty<Doc> currentDocument = new SimpleObjectProperty();
    //Native language
    private final ObjectProperty<String> nativeLang = new SimpleObjectProperty();
    //Closed?
    private final BooleanProperty isClosed = new SimpleBooleanProperty(false);
    
//Displaying types for list properties
    //backing for list of available languages
    private final ObservableList<LanguagePair> availableLanguages = FXCollections.observableList(new LinkedList<LanguagePair>());
    //backing for list of cards in current document
    private final ObservableList<Card> cardsList = FXCollections.observableList(new LinkedList<Card>());
    //backing for list of notes in current document
    private final ObservableList<Note> notesList = FXCollections.observableList(new LinkedList<Note>());
    
    
    
    private Model() {
    }
    
    public static Model getInstance() {
        return ModelHolder.INSTANCE;
    }

    private static class ModelHolder {

        private static final Model INSTANCE = new Model();
    }
    
    
    public final ObjectProperty<LanguagePair> currentLanguageProperty(){
        return currentLanguage;
    }
    
    public void setCurrentLanguage(LanguagePair pair){
            currentLanguage.set(pair);
    }
    
    public LanguagePair getCurrentLanguage(){
        return currentLanguage.get();
    }
    
    
    public final ObjectProperty<Item> currentTreeItemProperty(){
        return currentTreeItem;
    }
    
    public void setCurrentTreeItem(Item item){
        currentTreeItem.set(item);
    }
    
    public Item getCurrentTreeItem(){
        return currentTreeItem.get();
    }
    
    
    public final ObjectProperty<Doc> currentDocumentProperty(){
        return currentDocument;
    }
    
    public void setCurrentDocument(Doc doc){
        currentDocument.set(doc);
    }
    
    public Doc getCurrentDocument(){
        return currentDocument.get();
    }
    
    
    public final ObjectProperty<Card> currentCardProperty(){
        return currentCard;
    }
    
    public void setCurrentCard(Card card){
        currentCard.set(card);
    }
    
    public Card getCurrentCard(){
        return currentCard.get();
    }
    
    public final ObjectProperty<String> nativeLangProperty(){
        return nativeLang;
    }
    
    public void setNativeLang(String lang){
        nativeLang.set(lang);
    }
    
    public String getNativeLang(){
        return nativeLang.get();
    }
    
    
    public final ObjectProperty<Note> currentNoteProperty(){
        return currentNote;
    }
    
    public void setCurrentNote(Note note){
        currentNote.set(note);
    }
    
    public Note getCurrentNote(){
        return currentNote.get();
    }
    
    
    public final ObjectProperty<TreeItem> treeViewRootProperty(){
        return treeViewRoot;
    }
    
    public void setTreeViewRoot(TreeItem root){
        treeViewRoot.set(root);
    }
    
    public TreeItem getTreeViewRoot(){
        return treeViewRoot.get();
    }
    
    public final BooleanProperty isClosedProperty(){
        return isClosed;
    }
    
    public void setClosed(){
        if(isClosed.get()){
            isClosed.set(false);
        }else{
            isClosed.set(true);
        }
    }
    
    public boolean isClosed(){
        return isClosed.get();
    }
    
    
    public final ObservableList<LanguagePair> availableLanguagesProperty(){
        return availableLanguages;
    }
    
    public final ObservableList<Card> cardsListProperty(){
        return cardsList;
    }
    
    public final ObservableList<Note> notesListProperty(){
        return notesList;
    }

}
