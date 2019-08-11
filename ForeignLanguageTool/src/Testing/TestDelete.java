/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import DataModel.DTO.Doc;
import DataModel.DTO.User;
import DataModel.DTO.Item;
import DataModel.DTO.Group;
import DataModel.DTO.LanguagePair;
import DataModel.DTO.Note;
import DataModel.DTO.Card;
import DataModel.*;

import DataModel.Utils;
import Logic.ActualAPI;
import Logic.MotherTree;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Ethan Swistak
 */
public class TestDelete {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        
            ActualAPI api = ActualAPI.getInstance();
        
            Utils.load();
            
            User user = ActualAPI.getInstance().getUser();
            
            
            
            for(LanguagePair pair :api.getLangPair()){
                api.deleteLangPair(pair);
                if(performXMLSearch("//LanguagePair[@ID='" + String.valueOf(pair.getID()) + "']").getLength()!=0)
                    throwError(pair);
            }
            
            Utils.save();
            
            Utils.load();
            
            for(LanguagePair pair : api.getLangPair()){
                for(Group group : api.getGroups(pair)){
                    api.deleteGroup(group);
                    if(performXMLSearch("//Group[@ID='" + String.valueOf(group.getID()) + "']").getLength()!=0)
                        throwError(group);
                }
            }
            
            Utils.save();
            
            Utils.load();
            
            for(LanguagePair pair : api.getLangPair()){
                for(Group group : api.getGroups(pair)){
                    for(Doc doc : api.getDocuments(group)){
                        api.deleteDoc(doc);
                        if(performXMLSearch("//Document[@ID='" + String.valueOf(doc.getID()) + "']").getLength()!=0)
                            throwError(doc);
                    }
                }
            }
            
            Utils.save();
            
            Utils.load();
            
            for(LanguagePair pair : api.getLangPair()){
                for(Group group : api.getGroups(pair)){
                    for(Doc doc : api.getDocuments(group)){
                        for(Note note : api.getNote(doc)){
                            api.deleteNote(note);
                            if(performXMLSearch("//Note[@ID='" + String.valueOf(note.getID()) + "']").getLength()!=0)
                                throwError(note);
                        }
                    }
                }
            }
            
            Utils.save();
            
            Utils.load();
            
            for(LanguagePair pair : api.getLangPair()){
                for(Card card : api.getAllCards(pair)){
                    api.deleteCard(card);
                    if(performXMLSearch("//Note[@ID='" + String.valueOf(card.getID()) + "']").getLength()!=0)
                       throwError(card);
                }
            }
            
            Utils.save();
            
            Utils.load();
            
            
        
        
        
    }
    
    private static NodeList performXMLSearch(String xpathQuery){
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

    private static void throwError(Item pair) {
        System.out.println("Found a " + pair.getClass().getSimpleName() + " that should have been deleted");
    }
    
}
