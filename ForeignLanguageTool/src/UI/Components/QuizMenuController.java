/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.Components;

import UI.Model.Model;
import DataModel.DTO.LanguagePair;
import Logic.ActualAPI;
import UI.Components.Quiz.QuizController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ethan Swistak
 */
public class QuizMenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    Model model = Model.getInstance();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    @FXML
    private void startQuiz(){
        LanguagePair langPair = model.getCurrentLanguage();
        if (langPair != null) {

            //System.out.println(System.getProperty("user.dir"));
            QuizController cntrl = new QuizController(ActualAPI.getInstance().getAllCards(langPair));
            try {
                openPopup("/UI/Components/Quiz/quiz.fxml", cntrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

    private void openPopup(String fxmlPath, Object cntrl) throws Exception {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            fxmlLoader.setController(cntrl);
            Parent root = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
