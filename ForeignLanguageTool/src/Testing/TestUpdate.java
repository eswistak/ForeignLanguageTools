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
import Logic.ActualAPI;
import Logic.MotherTree;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Ethan Swistak
 */
public class TestUpdate {

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
        
        LanguagePair langPair = new LanguagePair();
        Group group = new Group();
        Doc doc = new Doc();
        Card card = new Card();
        Note note = new Note();
        

        langPair = ActualAPI.getInstance().createLangPair(langPair);
        group = ActualAPI.getInstance().createGroup(langPair, group);
        doc = ActualAPI.getInstance().createDoc(group, doc);
        card = ActualAPI.getInstance().createCard(doc, card);
        note = ActualAPI.getInstance().createNote(doc, note);
        
        
        group.setName("Test");
        
        ActualAPI.getInstance().updateGroup(group);
        
        doc.setTitle("Test");
        
        ActualAPI.getInstance().updateDoc(doc);
        
        card.setWordAsAppears("Test");
        
        ActualAPI.getInstance().updateCard(card);
        
        note.setContent("Note");
        
        ActualAPI.getInstance().updateNote(note);
        
        LanguagePair langPair2 = ActualAPI.getInstance().getLangPair().get(0);
        
        Card card2 = ActualAPI.getInstance().getAllCards(langPair2).get(0);
        
        card2.setTimesCorrect(5);
        card2.setTimesIncorrect(3);
        
        ActualAPI.getInstance().updateCard(card2);
        ActualAPI.getInstance().updateCard(card2);
        ActualAPI.getInstance().updateCard(card2);
        
        try {
            Utils.save();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(TestUpdate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(TestUpdate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestUpdate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(TestUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
