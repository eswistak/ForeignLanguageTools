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
import javax.xml.parsers.*;
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

}
