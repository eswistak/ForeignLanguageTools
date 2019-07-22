/*
File: Utils.java
Author: Ethan Swistak
Date: Jul 5, 2019
Purpose:
*/

package DataModel;

import Logic.MotherTree;
import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class Utils {
    
    
    public static void load() throws ParserConfigurationException, SAXException, IOException {
        File xmlFile = new File("DataModel\\TestData.xml");
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        MotherTree tree = MotherTree.getInstance();
        tree.setNodes(document);
    }
    
    public static boolean validateSchema(Document document){
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        try {
            Schema schema = factory.newSchema(new File("DataModel\\Schema.xsd"));
            Validator validator = schema.newValidator();
            
            DOMSource source = new DOMSource(document);
            validator.validate(source);
            
            
        } catch (SAXException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return true;
    }

}
