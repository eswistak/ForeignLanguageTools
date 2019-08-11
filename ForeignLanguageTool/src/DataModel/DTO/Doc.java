/*
File: Doc.java
Author: Ethan Swistak
Date: Jul 5, 2019
Purpose:
*/

package DataModel.DTO;

import Logic.MotherTree;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.w3c.dom.Node;
import javax.xml.bind.annotation.*;

@XmlRootElement(name="Document")
@XmlAccessorType(XmlAccessType.NONE)
public class Doc extends Item{

    @XmlElement(name="title")
    private String title = "";
    @XmlElement(name="text")
    private String text = "";
    @XmlElement(name="translation")
    private String translation = "";
    
    public Doc(){
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
    
    
    
    //method is called on a read operation
    public static Doc constructObject(Node node) throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(Doc.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object retval = unmarshaller.unmarshal(node);
        if(retval instanceof Doc){
            ((Doc) retval).setNode(node);
            return (Doc)retval;
        }else {
            throw new JAXBException("the object " + Doc.class.getSimpleName() + " did not parse correctly");
        }
    }
    
    //method is called on an update operation
    public void update(){
        super.updateNode("title", title);
        super.updateNode("text", text);
        super.updateNode("translation", translation);
    }
    
    //method is called on a create operation
    public static Doc createNew() throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(Card.class);
        Doc doc = new Doc();
        Node node = MotherTree.getInstance().getNodes().createDocumentFragment();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(doc, node);
        doc.setNode(node.getFirstChild());
        return doc;   
    }
    
    @Override
    public String toString(){
        return this.title;
    }
}