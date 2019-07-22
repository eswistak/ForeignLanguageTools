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
import DataModel.User;
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
 *
 * @author Ethan Swistak
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
    public List<Card> getAllCards(LanguagePair langPair) {
        String xpathQuery = "User/LanguagePair/Group/Document/Card";
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
    
    
}
