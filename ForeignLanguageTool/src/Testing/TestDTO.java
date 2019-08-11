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
import DataModel.Utils;
import Logic.MotherTree;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *
 * @author Ethan Swistak
 */
public class TestDTO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            Utils.load();
            
            Document document = MotherTree.getInstance().getNodes();
            
            User user = User.constructObject(document);
            
            System.out.println(user.toString());
            
            XPathFactory fac = XPathFactory.newInstance();
            XPath xpath = fac.newXPath();
            XPathExpression exp = xpath.compile("User/LanguagePair");
            
            NodeList result = (NodeList)exp.evaluate(document, XPathConstants.NODESET);
            
            LanguagePair langpair = LanguagePair.constructObject(result.item(0));
            
            System.out.println(langpair.toString());
            
            exp = xpath.compile("User/LanguagePair/Group");
            
            NodeList result2 = (NodeList)exp.evaluate(document, XPathConstants.NODESET);
            
            Group group = Group.constructObject(result2.item(0));
            
            System.out.println(group.toString());
            
            exp = xpath.compile("User/LanguagePair/Group/Document");
            
            NodeList result3 = (NodeList)exp.evaluate(document, XPathConstants.NODESET);
            
            Doc doc = Doc.constructObject(result3.item(0));
            
            System.out.println(doc.toString());
            
            exp = xpath.compile("User/LanguagePair/Group/Document/Card");
            
            NodeList result4 = (NodeList)exp.evaluate(document, XPathConstants.NODESET);
            
            Card card = Card.constructObject(result4.item(0));
            
            System.out.println(card.toString());
            
            exp = xpath.compile("User/LanguagePair/Group/Document/Note");
            
            NodeList result5 = (NodeList)exp.evaluate(document, XPathConstants.NODESET);
            
            Note note = Note.constructObject(result5.item(0));
            
            System.out.println(note.toString());
            
            System.out.println(Item.getCount());
            
            Utils.validateSchema(document);
            
            
            
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (JAXBException ex) {
            Logger.getLogger(TestDTO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (XPathExpressionException ex) {
            Logger.getLogger(TestDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
