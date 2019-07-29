/*
File: Item.java
Author: Ethan Swistak
Date: Jul 5, 2019
Purpose:
*/

package DataModel;

import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;
import org.w3c.dom.Node;


@XmlSeeAlso({User.class, LanguagePair.class, Group.class, Doc.class, Card.class, Note.class})
public class Item {
    
    @XmlTransient
    private static int count=0;
    private int ID;
    @XmlTransient
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

    @XmlAttribute(name="ID")
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
    
    protected void updateNode(int index, String text){
        node.getChildNodes().item(index).setTextContent(text);
    }
    
    //method is called on delete operation
    public void delete(Node node) {
        this.getNode().getParentNode().removeChild(this.getNode());
    }
    
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
