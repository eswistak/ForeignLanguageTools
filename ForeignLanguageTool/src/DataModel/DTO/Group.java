/*
File: Group.java
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

@XmlRootElement(name="Group")
@XmlAccessorType(XmlAccessType.NONE)
public class Group extends Item {
    
    @XmlElement(name="name")
    private String name = "";

    public Group(){
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    //method is called on a read operation
    public static Group constructObject(Node node) throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(Group.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object retval = unmarshaller.unmarshal(node);
        if(retval instanceof Group){
            ((Group) retval).setNode(node);
            return (Group)retval;
        }else {
            throw new JAXBException("the object" + Group.class.getSimpleName() + "did not parse correctly");
        }
    }
    
    //method is called on an update operation
    public void update(){
        super.updateNode("name", name);
    }
    
    //method is called on a create operation
    public static Group createNew() throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(Card.class);
        Group group = new Group();
        Node node = MotherTree.getInstance().getNodes().createDocumentFragment();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(group, node);
        group.setNode(node.getFirstChild());
        return group;   
   }
    
    @Override
    public String toString(){
        return this.name;
    }
}
