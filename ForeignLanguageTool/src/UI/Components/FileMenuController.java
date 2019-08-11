/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Components;

import DataModel.Utils;
import UI.Model.Model;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 * FXML Controller class
 *
 * @author Ethan Swistak
 */
public class FileMenuController implements Initializable {

    Model model = Model.getInstance();
    
    @FXML
    Menu fileMenu;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void save(){
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
    }
    
    @FXML
    private void load(){
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(new Stage());
        if(file==null){
            return;
        }
        String path = file.getAbsolutePath();
        try {
            Utils.load(path);
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void export(){
        FileChooser chooser = new FileChooser();
        File file = chooser.showSaveDialog(new Stage());
        if(file==null){
            return;
        }
        String path = file.getAbsolutePath();
        try {
            Utils.save(path);
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
    private void saveAndExit(){
        save();
        model.setClosed();
    }
    
}
