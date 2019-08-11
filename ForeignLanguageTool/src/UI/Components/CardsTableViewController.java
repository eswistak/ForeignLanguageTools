/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Components;

import UI.Model.Model;
import DataModel.DTO.Card;
import DataModel.DTO.Doc;
import Logic.ActualAPI;
import UI.Components.Popups.CardCreationController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Ethan Swistak
 */
public class CardsTableViewController implements Initializable {

    @FXML
    private TableView<Card> cardsTableView;
    @FXML
    private TableColumn<?, ?> cardsWordColumn;
    @FXML
    private TableColumn<?, ?> cardsDefinitionColumn;
    
    Model model = Model.getInstance();
    ActualAPI api = ActualAPI.getInstance();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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

        cardsTableView.setItems(model.cardsListProperty());
        setTableDoubleClick();
    }    

    @FXML
    private void menuEditFlashcardEvent() {
        Doc doc = (Doc)model.currentTreeItemProperty().get();
        Card viewingCard = this.cardsTableView.getSelectionModel().getSelectedItem();
        CardCreationController subWindow = new CardCreationController(doc, viewingCard);
        cardsTableView.refresh();
    }

    @FXML
    private void cardDeleteEvent(ActionEvent event) {
        Card cardToDelete = cardsTableView.getSelectionModel().getSelectedItem();
        api.deleteCard(cardToDelete);
        cardsTableView.getItems().remove(cardToDelete);
    }
    private void setTableDoubleClick() {
        cardsTableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    menuEditFlashcardEvent();              
                }
            }
        });
    }
}
