/*
File: LanguagePair.java
Author: Ethan Swistak
Date: Jul 5, 2019
Purpose:
*/

package DataModel;

import Logic.MotherTree;
import javax.xml.bind.*;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import org.w3c.dom.*;

@XmlRootElement(name="LanguagePair")
@XmlAccessorType(XmlAccessType.NONE)
class LanguagePair extends Item {

    @XmlElement(name="native")
    private String nat = "";
    @XmlElement(name="target")
    private String target = "";
    
    private LanguagePair(){
        super();
    }

//method is called on a read operation
    public LanguagePair constructObject(Node node) throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(LanguagePair.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object retval = unmarshaller.unmarshal(node);
        if(retval instanceof LanguagePair){
            ((LanguagePair) retval).setNode(node);
            return (LanguagePair)retval;
        }else {
            throw new JAXBException("the object" + LanguagePair.class.getSimpleName() + "did not parse correctly");
        }
    }
    
    //method is called on an update operation
    @Override
    public void update(){
        super.updateNode(0, nat);
        super.updateNode(1, target);
    }
    
    //method is called on a create operation
    public static LanguagePair createNew() throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(LanguagePair.class);
        LanguagePair langpair = new LanguagePair();
        Node node = MotherTree.getInstance().getNodes().createElement("User");
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(langpair, node);
        langpair.setNode(node);
        return langpair;
   }
}