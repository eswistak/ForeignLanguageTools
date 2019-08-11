
package Logic;

import DataModel.DTO.Card;
import DataModel.DTO.Doc;
import DataModel.DTO.Group;
import DataModel.DTO.LanguagePair;
import DataModel.DTO.Note;
import DataModel.DTO.User;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * I feel most of these methods are pretty self explanatory in the context of the application, so no Javadocs
 * 
 * @author Andrew Barbarosa
 */
public class ActualAPI implements API {
    
    private ActualAPI() {
    }
    
    public static ActualAPI getInstance() {
        return ActualAPIHolder.INSTANCE;
    }

    private static class ActualAPIHolder {

        private static final ActualAPI INSTANCE = new ActualAPI();
    }
    
    @Override
    public User getUser() {
        Document document = MotherTree.getInstance().getNodes();
        try {
            User user = User.constructObject(document);
            return user;
        } catch (JAXBException ex) {
            Logger.getLogger(ActualAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<LanguagePair> getLangPair() {
        String xpathQuery = "User/LanguagePair";
        NodeList nodelist = performXMLSearch(xpathQuery);
        List<LanguagePair> result = new ArrayList<>();
        for(int i = 0; i<nodelist.getLength(); i++){
            Node node = nodelist.item(i);
            try {
                LanguagePair langPair = LanguagePair.constructObject(node);
                result.add(langPair);
            } catch (JAXBException ex) {
                Logger.getLogger(ActualAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    @Override
    public List<Group> getGroups(LanguagePair langPair) {
        String xpathQuery = "User/LanguagePair[@ID='" + String.valueOf(langPair.getID() + "']/Group");
        NodeList nodelist = performXMLSearch(xpathQuery);
        List<Group> result = new ArrayList<>();
        for(int i = 0; i<nodelist.getLength(); i++){
            Node node = nodelist.item(i);
            try {
                Group group = Group.constructObject(node);
                result.add(group);
            } catch (JAXBException ex) {
                Logger.getLogger(ActualAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    @Override
    public List<Doc> getDocuments(Group group) {
        String xpathQuery = "User/LanguagePair/Group[@ID='" + String.valueOf(group.getID() + "']/Document");
        NodeList nodelist = performXMLSearch(xpathQuery);
        List<Doc> result = new ArrayList<>();
        for(int i = 0; i<nodelist.getLength(); i++){
            Node node = nodelist.item(i);
            try {
                Doc doc = Doc.constructObject(node);
                result.add(doc);
            } catch (JAXBException ex) {
                Logger.getLogger(ActualAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    @Override
    public List<Card> getCards(Doc doc) {
        String xpathQuery = "User/LanguagePair/Group/Document[@ID='" + String.valueOf(doc.getID() + "']/Card");
        NodeList nodelist = performXMLSearch(xpathQuery);
        List<Card> result = new ArrayList<>();
        for(int i = 0; i<nodelist.getLength(); i++){
            Node node = nodelist.item(i);
            try {
                Card card = Card.constructObject(node);
                result.add(card);
            } catch (JAXBException ex) {
                Logger.getLogger(ActualAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    @Override
    public List<Note> getNote(Doc doc) {
        String xpathQuery = "User/LanguagePair/Group/Document[@ID='" + String.valueOf(doc.getID() + "']/Note");
        NodeList nodelist = performXMLSearch(xpathQuery);
        List<Note> result = new ArrayList<>();
        for(int i = 0; i<nodelist.getLength(); i++){
            Node node = nodelist.item(i);
            try {
                Note note = Note.constructObject(node);
                result.add(note);
            } catch (JAXBException ex) {
                Logger.getLogger(ActualAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    @Override
    public Doc getDoc(Card card) {
        String xpathQuery = "User/LanguagePair/Group/Document/Card[@ID='" + String.valueOf(card.getID() + "']/ancestor::Document");
        NodeList nodelist = performXMLSearch(xpathQuery);
        Doc doc = null;
        for(int i = 0; i<nodelist.getLength(); i++){
            Node node = nodelist.item(i);
            try {
                doc = Doc.constructObject(node);
            } catch (JAXBException ex) {
                Logger.getLogger(ActualAPI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return doc;
    }
     @Override
     public List<Card> getAllCards(LanguagePair langPair) {
         String xpathQuery = "User/LanguagePair[@ID='" + String.valueOf(langPair.getID()) + "']/Group/Document/Card";
         NodeList nodelist = performXMLSearch(xpathQuery);
         List<Card> result = new ArrayList<>();
         for(int i = 0; i<nodelist.getLength(); i++){
             Node node = nodelist.item(i);
             try {
                 Card card = Card.constructObject(node);
                 result.add(card);
             } catch (JAXBException ex) {
                 Logger.getLogger(ActualAPI.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
         return result;
     }
    private NodeList performXMLSearch(String xpathQuery){
        Document document = MotherTree.getInstance().getNodes();
        XPathFactory fac = XPathFactory.newInstance();
        XPath xpath = fac.newXPath();
        try {
            XPathExpression exp = xpath.compile(xpathQuery);
            NodeList result = (NodeList)exp.evaluate(document, XPathConstants.NODESET);
            return result;
        } catch (XPathExpressionException ex) {
            Logger.getLogger(ActualAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public LanguagePair createLangPair(LanguagePair langPair) {
        try {
            langPair = LanguagePair.createNew();
            Node singleNode = performXMLSearch("User").item(0);
            singleNode.appendChild(langPair.getNode());
        } catch (JAXBException ex) {
            System.out.println("JAXB failed to create new LangPair");
        }
        return langPair;
    }

    @Override
    public Group createGroup(LanguagePair langPair, Group group) {
        try {
            group = Group.createNew();
            Node singleNode = performXMLSearch("User/LanguagePair[@ID='" + String.valueOf(langPair.getID()) + "']").item(0);
            singleNode.appendChild(group.getNode());
        } catch (JAXBException ex) {
            System.out.println("JAXB failed to create new Group");
        }     
        return group;
    }

    @Override
    public Doc createDoc(Group group, Doc doc) {
        try{
            doc = Doc.createNew();
            Node singleNode = performXMLSearch("User/LanguagePair/Group[@ID='" + String.valueOf(group.getID()) + "']").item(0);
            singleNode.appendChild(doc.getNode());
        }catch(JAXBException ex){
            System.out.println("JAXB failed to create new Doc");
        }
        return doc;
    }

    @Override
    public Card createCard(Doc doc, Card card) {
        try{
            card = Card.createNew();
            Node singleNode = performXMLSearch("User/LanguagePair/Group/Document[@ID='" + String.valueOf(doc.getID()) + "']").item(0);
            singleNode.appendChild(card.getNode());
        }catch(JAXBException ex){
            System.out.println("JAXB failed to create new Card");
        }
        return card;
    }

    @Override
    public Note createNote(Doc doc, Note note) {
        try{
            note = Note.createNew();
            Node singleNode = performXMLSearch("User/LanguagePair/Group/Document[@ID='" + String.valueOf(doc.getID()) + "']").item(0);
            singleNode.appendChild(note.getNode());
        }catch(JAXBException ex){
            System.out.println("JAXB failed to create new Note");
        }
        return note;
    }

    @Override
    public void updateGroup(Group group) {
        group.update();
    }

    @Override
    public void updateDoc(Doc doc) {
        doc.update();
    }

    @Override
    public void updateCard(Card card) {
        card.update();
    }

    @Override
    public void updateNote(Note note) {
        note.update();
    }

    @Override
    public void deleteLangPair(LanguagePair langPair) {
        langPair.delete(langPair.getNode());
    }

    @Override
    public void deleteGroup(Group group) {
        group.delete(group.getNode());
    }

    @Override
    public void deleteDoc(Doc doc) {
        doc.delete(doc.getNode());
    }

    @Override
    public void deleteCard(Card card) {
        card.delete(card.getNode());
    }

    @Override
    public void deleteNote(Note note) {
        note.delete(note.getNode());
    }
    
}
