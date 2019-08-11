/*
File: Item.java
Author: Ethan Swistak
Date: Jul 5, 2019
Purpose: Provides a superclass for all of the...well let's call them DTO's (Data Transfer Objects) that exist, including Docs, Cards, and LanguagePairs
*/

package DataModel.DTO;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

//The XmlSeeAlso allows us to Marshal and UnMarshal an object that has a superclass
@XmlSeeAlso({User.class, LanguagePair.class, Group.class, Doc.class, Card.class, Note.class})
public class Item {
    
    @XmlTransient//The counter is used to assign a unique ID to each DTO
    private static int count=0;
    private int ID;
    @XmlTransient//This is the actual XML node that maps back to the XML persistence store, I thought it would be easier to manipulate them this way but in hindsight, you probably could refactor the code to eliminate this
    private Node node;
    
    public Item(){
        this.ID = count;
        count++;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Item.count = count;
    }

    @XmlAttribute(name="ID")//For a superclass, you need to have JAXB mapped to the getter instead of the field iteslf
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @XmlTransient
    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
    
    //Called on an update operation, to sync up field values with XML persistence store
    protected void updateNode(String tagname, String text){
        Element element = (Element)((Element)node).getElementsByTagName(tagname).item(0);
        element.setTextContent(text);
    }
    
    //method is called on delete operation
    public void delete(Node node) {
        this.getNode().getParentNode().removeChild(this.getNode());
    }
    
    /**
     * This is purely for testing purposes
     * 
     * @return XML representation of item's node
     */
    @Override
    public String toString() {
        try {
            JAXBContext context = JAXBContext.newInstance(this.getClass());
            Marshaller marshaller = context.createMarshaller();
            StringWriter writer = new StringWriter();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(this, writer);
            return writer.toString();
        } catch (JAXBException ex) {
            ex.printStackTrace();
        }
        return "Unable to generate an XML representation of this object";
    }


}
