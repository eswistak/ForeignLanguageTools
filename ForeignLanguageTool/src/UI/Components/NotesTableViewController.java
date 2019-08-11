/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Components;

import UI.Model.Model;
import DataModel.DTO.Card;
import DataModel.DTO.Note;
import Logic.ActualAPI;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Ethan Swistak
 */
public class NotesTableViewController implements Initializable {

    @FXML
    private TableView<Note> notesTableView;
    @FXML
    private TableColumn<?, ?> notesIndexColumn;
    @FXML
    private TableColumn<?, ?> notesNoteColumn;
    
    Model model = Model.getInstance();
    ActualAPI api = ActualAPI.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

        notesTableView.setItems(model.notesListProperty());
        setTableDoubleClick();
    }    

    @FXML
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
        api.updateNote(n);
        notesTableView.refresh();
    }

    @FXML
    private void notesDeleteEvent(ActionEvent event) {
        Note noteToDelete = notesTableView.getSelectionModel().getSelectedItem();
        api.deleteNote(noteToDelete);
        notesTableView.getItems().remove(noteToDelete);
    }
    
    @FXML
    private void notesTableViewSelectedEvent(MouseEvent event) {
        Note selectedNote = notesTableView.getSelectionModel().getSelectedItem();
        model.currentNoteProperty().set(selectedNote);
    }
    
        private void setTableDoubleClick() {
        notesTableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    menuEditNoteEvent();                 
                }
            }
        });
    }
}
