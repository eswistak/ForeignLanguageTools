/*
File: LanguagePair.java
Author: Ethan Swistak
Date: Jul 5, 2019
Purpose:
*/

//Added a comment for example purposes

//blah blah blah blah blah is this comment long enough yet?

package DataModel.DTO;

import Logic.MotherTree;
import javax.xml.bind.*;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import org.w3c.dom.*;

@XmlRootElement(name="LanguagePair")
@XmlAccessorType(XmlAccessType.NONE)
public class LanguagePair extends Item {

    @XmlElement(name="native")
    private String nat = "";
    @XmlElement(name="target")
    private String target = "";
    
    public LanguagePair(){
        super();
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
    
    

    //method is called on a read operation
    public static LanguagePair constructObject(Node node) throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(LanguagePair.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object retval = unmarshaller.unmarshal(node);
        if(retval instanceof LanguagePair){
            ((LanguagePair) retval).setNode(node);
            return (LanguagePair)retval;
        }else {
            throw new JAXBException("the object " + LanguagePair.class.getSimpleName() + " did not parse correctly");
        }
    }
    
    //method is called on an update operation
    public void update(){
        super.updateNode("native", nat);
        super.updateNode("target", target);
    }
    
    //method is called on a create operation
    public static LanguagePair createNew() throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(Card.class);
        LanguagePair pair = new LanguagePair();
        Node node = MotherTree.getInstance().getNodes().createDocumentFragment();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(pair, node);
        pair.setNode(node.getFirstChild());
        return pair;
   }
    
    @Override
    public String toString(){
        return (this.nat + " -> " + this.target);
    }
}
