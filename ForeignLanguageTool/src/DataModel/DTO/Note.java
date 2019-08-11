/*
File: Note.java
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

@XmlRootElement(name="Note")
@XmlAccessorType(XmlAccessType.NONE)
public class Note extends Item {
    
    @XmlElement(name="content")
    private String content = "";
    @XmlElement(name="index")
    private int index = 0;
    @XmlElement(name="startChar")
    private int startChar = 0;
    @XmlElement(name="endChar")
    private int endChar = 0;
    
    public Note(){
        super();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getStartChar() {
        return startChar;
    }

    public void setStartChar(int startChar) {
        this.startChar = startChar;
    }

    public int getEndChar() {
        return endChar;
    }

    public void setEndChar(int endChar) {
        this.endChar = endChar;
    }
    
    
    
        //method is called on a read operation
    public static Note constructObject(Node node) throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(Note.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object retval = unmarshaller.unmarshal(node);
        if(retval instanceof Note){
            ((Note) retval).setNode(node);
            return (Note)retval;
        }else {
            throw new JAXBException("the object" + Note.class.getSimpleName() + "did not parse correctly");
        }
    }
    
    //method is called on an update operation
    public void update(){
        super.updateNode("content", content);
        super.updateNode("index", String.valueOf(index));
        super.updateNode("startChar", String.valueOf(startChar));
        super.updateNode("endChar", String.valueOf(endChar));
    }
    
    //method is called on a create operation
    public static Note createNew() throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(Card.class);
        Note note = new Note();
        Node node = MotherTree.getInstance().getNodes().createDocumentFragment();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(note, node);
        note.setNode(node.getFirstChild());
        return note;   
   }

}
