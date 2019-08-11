/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import DataModel.DTO.Card;
import DataModel.DTO.Doc;
import DataModel.DTO.Group;
import DataModel.DTO.LanguagePair;
import DataModel.DTO.Note;
import DataModel.Utils;
import Logic.MotherTree;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Ethan Swistak
 */
public class TestingCreate {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Document document = MotherTree.getInstance().getNodes();
        
        try {
            Utils.load();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(TestingCreate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(TestingCreate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestingCreate.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        LanguagePair langPair = null;
        Group group = null;
        Doc doc = null;
        Card card = null;
        Note note = null;
        
        try {
            langPair = LanguagePair.createNew();
            group = Group.createNew();
            doc = Doc.createNew();
            card = Card.createNew();
            note = Note.createNew();
        } catch (JAXBException ex) {
            Logger.getLogger(TestingCreate.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        langPair.setNat("English");
        langPair.setTarget("Spanish");
        
        langPair.update();
        
        Utils.validateSchema(document);
        
        group.setName("Test");
        
        group.update();
        
        Utils.validateSchema(document);
        
        doc.setTitle("Test");
        
        doc.update();
        
        Utils.validateSchema(document);
        
        card.setOtherTrans("Test");
        
        card.update();
        
        Utils.validateSchema(document);
        
        note.setContent("Test");
        
        note.update();
        
        Utils.validateSchema(document);
        
        
        
    }
    
}
