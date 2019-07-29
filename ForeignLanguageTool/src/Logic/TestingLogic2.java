/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import DataModel.Card;
import DataModel.Doc;
import DataModel.Group;
import DataModel.LanguagePair;
import DataModel.Note;
import DataModel.Utils;
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
public class TestingLogic2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Document document = MotherTree.getInstance().getNodes();
        
        try {
            Utils.load();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(TestingLogic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(TestingLogic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestingLogic.class.getName()).log(Level.SEVERE, null, ex);
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
        
        try {
            Utils.save();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(TestingLogic2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(TestingLogic2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TestingLogic2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(TestingLogic2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
