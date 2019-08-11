/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Components;

import UI.Model.Model;
import DataModel.DTO.Doc;
import DataModel.DTO.Group;
import DataModel.DTO.Item;
import Logic.ActualAPI;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ethan Swistak
 */
public class TreeViewController implements Initializable {

    Model model = Model.getInstance();
    ActualAPI api = ActualAPI.getInstance();
    
    @FXML
    TreeView treeView;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        model.currentLanguageProperty().addListener((listener)->{
            model.getTreeViewRoot().getChildren().clear();
            for (Group group : api.getGroups(model.getCurrentLanguage())) {
                TreeItem<Item> groupItem = new TreeItem(group);
                model.getTreeViewRoot().getChildren().add(groupItem);
                for (Doc doc : api.getDocuments(group)) {
                    TreeItem<Item> docItem = new TreeItem(doc);
                    groupItem.getChildren().add(docItem);
                }
            }
        });
        
        treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        treeView.getSelectionModel().selectedItemProperty().
                addListener(((observable, oldValue, newValue) -> {
                    if(newValue!=null){
                    Item selectedItem = ((TreeItem<Item>)newValue).getValue();
                    model.currentTreeItemProperty().set(selectedItem);
                    if(selectedItem instanceof Doc){
                        Doc doc = (Doc)selectedItem;
                        model.setCurrentDocument(doc);
                        model.cardsListProperty().clear();
                        model.cardsListProperty().addAll(api.getCards(doc));
                        model.notesListProperty().clear();
                        model.notesListProperty().addAll(api.getNote(doc));
                        }
                    }
                })
            );
        TreeItem root = new TreeItem("The Root");
        model.setTreeViewRoot(root);

        treeView.setRoot(root);
        treeView.setShowRoot(false);
    }


//--------------Context Menu---------------------------------------------------


    @FXML
    private void addGroup(){
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("UI/Components/Popups/NewGroup.fxml"));
        Stage stage = null; 
        try {   
            Scene scene = loader.load();
            stage = new Stage();
            stage.setScene(scene);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        stage.showAndWait();
        
    }
    
    @FXML
    private void deleteGroup(){
        if(model.getCurrentTreeItem() instanceof Group){
            Group group = (Group)model.getCurrentTreeItem();
            api.deleteGroup(group);
            refreshTreeView();
            
        }
    }
    
    @FXML
    private void addDocument(){
        class Result{
            String NAME;
            String CONTENT;
            
            protected Result(String name, String content){
                this.NAME = name;
                this.CONTENT = content;
            }
        }
        
        Dialog<Result> dialog = new Dialog<>();
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
        TextArea newDocContent = new TextArea();

        grid.add(new Label("New Document Name:"), 0, 0);
        grid.add(newDocName, 1, 0);
        GridPane.setColumnSpan(newDocContent, GridPane.REMAINING);
        grid.add(newDocContent,0,1);
        dialog.getDialogPane().setContent(grid);
        
        dialog.setResultConverter(value->{
            return new Result(newDocName.getText(), newDocContent.getText());
        });

        // request focus on the native field by default
        Platform.runLater(() -> newDocName.requestFocus());
        Optional<Result> result = dialog.showAndWait();
        if(model.getCurrentTreeItem() instanceof Group){
            Doc doc = api.createDoc((Group)model.getCurrentTreeItem(), new Doc());
            doc.setTitle(result.get().NAME);
            doc.setText(result.get().CONTENT);
            api.updateDoc(doc);
            refreshTreeView();
        }else{
            TreeItem item = (TreeItem)treeView.getSelectionModel().getSelectedItems().get(0);
            Group group = (Group)item.getParent().getValue();
            Doc doc = api.createDoc(group, new Doc());
            doc.setTitle(result.get().NAME);
            doc.setText(result.get().CONTENT);
            api.updateDoc(doc);
            refreshTreeView();
        }
        
        
    }
    
    @FXML
    private void deleteDoc(){
        if(model.getCurrentTreeItem() instanceof Doc){
            Doc doc = (Doc)model.getCurrentTreeItem();
            api.deleteDoc(doc);
            refreshTreeView();
            model.currentDocumentProperty().set(new Doc());
            model.cardsListProperty().clear();
            model.notesListProperty().clear();
        }
    }
    
    
    private void refreshTreeView(){
        model.getTreeViewRoot().getChildren().clear();
            for (Group group : api.getGroups(model.getCurrentLanguage())) {
                TreeItem<Item> groupItem = new TreeItem(group);
                model.getTreeViewRoot().getChildren().add(groupItem);
                for (Doc doc : api.getDocuments(group)) {
                    TreeItem<Item> docItem = new TreeItem(doc);
                    groupItem.getChildren().add(docItem);
                }
            }
    }
    
}
