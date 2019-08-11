/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import DataModel.DTO.LanguagePair;
import UI.Model.Model;

/**
 *
 * @author Ethan Swistak
 */
public class TestingProperties {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Model model = Model.getInstance();
        
        model.currentLanguageProperty().addListener((object, oldvalue, newvalue)->{
            System.out.println("The value changed");
        });
        
        LanguagePair pair1 = new LanguagePair();
        LanguagePair pair2 = new LanguagePair();
        
        model.setCurrentLanguage(pair2);
        
        model.setCurrentLanguage(pair1);
        
        model.setCurrentLanguage(pair1);
        
        model.setCurrentLanguage(pair2);
    }
    
    
    
}
