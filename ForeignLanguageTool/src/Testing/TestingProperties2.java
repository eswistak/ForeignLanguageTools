/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import DataModel.DTO.LanguagePair;
import UI.Model.Model;
import javafx.collections.ListChangeListener;

/**
 *
 * @author Ethan Swistak
 */
public class TestingProperties2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Model model = Model.getInstance();
        model.availableLanguagesProperty().addListener(new ListChangeListener<LanguagePair>(){
            @Override
            public void onChanged(Change<? extends LanguagePair> change) {
                System.out.println("List has changed");
            }
        });
        
        model.availableLanguagesProperty().add(new LanguagePair());
    }
    
}
