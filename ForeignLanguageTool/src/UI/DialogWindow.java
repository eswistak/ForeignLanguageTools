/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DataModel.Doc;
import DataModel.Group;
import DataModel.Item;
import DataModel.LanguagePair;
import Logic.ActualAPI;
import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 *
 * @author Hyung Kang
 */
class DialogWindow {
        
    static void createNewDocument(TreeItem<Item> item) {
        if (item == null || item.getValue() instanceof LanguagePair == false) {
            popUpDialog("Please select the language pair first from the tree view.");
        } else if (item.getValue() instanceof LanguagePair) {
            // pop-up dialog
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("New Document");
            dialog.setHeaderText("Create New Document");
            
            // button
            ButtonType buttonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            // text-field for native and target variables
            TextField documentFolder = new TextField();
            TextField documentName = new TextField();
            TextArea documentText = new TextArea();
            documentText.setWrapText(true);
            
            grid.add(new Label("Folder Name:"), 0, 0);
            grid.add(documentFolder, 1, 0);
            grid.add(new Label("Document Name:"), 0, 1);
            grid.add(documentName, 1, 1);
            grid.add(new Label("Text"), 0, 2);
            grid.add(documentText, 1, 2);
            dialog.getDialogPane().setContent(grid);
            
            // request focus on the document name by default
            Platform.runLater(() -> documentFolder.requestFocus());
            
            // convert the result to a name-text when the ok button is clicked
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == buttonType) {
                    return new Pair<>(documentName.getText(), documentText.getText());
                }
                
                return null;
            });
            
            Optional<Pair<String, String>> result = dialog.showAndWait();
            
            // check result
            result.ifPresent((Pair<String, String> pair) -> {
                // check if text-field and text-area is empty
                if (pair.getKey().isEmpty() || pair.getValue().isEmpty()) {
                    popUpDialog("Please enter the document name and text.");
                } else {
                    
                    TreeItem<Item> currentPair = item;
                    
                    Group newGroup = new Group();
                    newGroup.setName(documentFolder.getText());
                    
                    Doc newDoc = new Doc();
                    newDoc.setTitle(pair.getKey());
                    newDoc.setText(pair.getValue());
                     
                    TreeItem<Item> newGroupItem = new TreeItem<>(newGroup);
                    TreeItem<Item> newItem = new TreeItem<>(newDoc);
                    
                    newGroupItem.getChildren().add(newItem);
                    item.getChildren().add(newGroupItem);
                    
                    // I get runtime exception error from ActualAPI line 227
                    /*
                    LanguagePair selectedPair = (LanguagePair) item.getValue();
                    Group addedGroup = ActualAPI.getInstance().createGroup(selectedPair, newGroup);
                    Doc addedDoc = ActualAPI.getInstance().createDoc(newGroup, newDoc);
                    addedGroup.update();
                    addedDoc.update();
                    */
                    
                    System.out.println("Doc Added");
                }
            });
        }
    }
    
    static void createNewLangPair(TreeItem<Item> root) {
        LanguagePair newLangPair = ActualAPI.getInstance().createLangPair(new LanguagePair());
      
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
                
                // using API instance and update language pair
                LanguagePair addedPair = ActualAPI.getInstance().createLangPair(newLangPair);
                addedPair.update();
                
                // display into tree
                TreeItem<Item> newPair = new TreeItem<>(newLangPair);
                root.getChildren().add(newPair);
            }
        });
    }
    
    static void editText(TextArea mainTextArea, TreeItem<Item> item) {
        if (mainTextArea.getText().isEmpty()) {
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
        newTextArea.setText(mainTextArea.getText());
        
        grid.add(newTextArea, 0, 0);
        dialog.getDialogPane().setContent(grid);

        // request focus on the native field by defualt
        Platform.runLater(() -> newTextArea.requestFocus());
        
        Optional<String> result = dialog.showAndWait();
        
        
        if (result.isPresent() && !newTextArea.getText().isEmpty()) {
            // update the current text document 
            Doc newDoc = (Doc) item.getValue();
            
            newDoc.setText(newTextArea.getText());
            
            // using API instance
            ActualAPI.getInstance().updateDoc(newDoc);
            
            // display the modified document
            mainTextArea.setText(newDoc.getText());
        } else {
            System.out.println("Edit text field is empty.");
            popUpDialog("Text field is empty");
        }
    }

    private static void popUpDialog(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
}
