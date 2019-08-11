/*
File: Card.java
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
import javax.xml.transform.dom.DOMResult;
import org.w3c.dom.Document;

@XmlRootElement(name="Card")
@XmlAccessorType(XmlAccessType.NONE)
public class Card extends Item{
    
    @XmlElement(name="index")
    private int index = 1;
    @XmlElement(name="wordAsAppears")
    private String wordAsAppears = "   ";
    @XmlElement(name="generic")
    private String generic = "   ";
    @XmlElement(name="partOfSpeech")
    private String partOfSpeech = "   ";
    @XmlElement(name="translationInContext")
    private String transInContext = "   ";
    @XmlElement(name="otherTranslation")
    private String otherTrans = "   ";
    @XmlElement(name="startChar")
    private int startChar = 1;
    @XmlElement(name="endChar")
    private int endChar = 1;
    @XmlElement(name="timesCorrect")
    private int timesCorrect = 1;
    @XmlElement(name="timesIncorrect")
    private int timesIncorrect = 1;
    @XmlElement(name="hint")
    private String hint = "     ";
    @XmlElement(name="cardNote")
    private String cardNote = "      ";

    public Card(){
        super();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getWordAsAppears() {
        return wordAsAppears;
    }

    public void setWordAsAppears(String wordAsAppears) {
        this.wordAsAppears = wordAsAppears;
    }

    public String getGeneric() {
        return generic;
    }

    public void setGeneric(String generic) {
        this.generic = generic;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public String getTransInContext() {
        return transInContext;
    }

    public void setTransInContext(String transInContext) {
        this.transInContext = transInContext;
    }

    public String getOtherTrans() {
        return otherTrans;
    }

    public void setOtherTrans(String otherTrans) {
        this.otherTrans = otherTrans;
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

    public int getTimesCorrect() {
        return timesCorrect;
    }

    public void setTimesCorrect(int timesCorrect) {
        this.timesCorrect = timesCorrect;
    }

    public int getTimesIncorrect() {
        return timesIncorrect;
    }

    public void setTimesIncorrect(int timesIncorrect) {
        this.timesIncorrect = timesIncorrect;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getCardNote() {
        return cardNote;
    }

    public void setCardNote(String cardNote) {
        this.cardNote = cardNote;
    }
    
    
    
        //method is called on a read operation
    public static Card constructObject(Node node) throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(Card.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object retval = unmarshaller.unmarshal(node);
        if(retval instanceof Card){
            ((Card) retval).setNode(node);
            return (Card)retval;
        }else {
            throw new JAXBException("the object" + Card.class.getSimpleName() + "did not parse correctly");
        }
    }
    
    //method is called on an update operation
    public void update(){
        super.updateNode("index", String.valueOf(index));
        super.updateNode("wordAsAppears", wordAsAppears);
        super.updateNode("generic", generic);
        super.updateNode("partOfSpeech", partOfSpeech);
        super.updateNode("translationInContext", transInContext);
        super.updateNode("otherTranslation", otherTrans);
        super.updateNode("startChar", String.valueOf(startChar));
        super.updateNode("endChar", String.valueOf(endChar));
        super.updateNode("timesCorrect", String.valueOf(timesCorrect));
        super.updateNode("timesIncorrect", String.valueOf(timesIncorrect));
        super.updateNode("hint", hint);
        super.updateNode("cardNote", cardNote);
    }
    
    //method is called on a create operation
    public static Card createNew() throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(Card.class);
        Card card = new Card();
        Node node = MotherTree.getInstance().getNodes().createDocumentFragment();
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(card, node);
        card.setNode(node.getFirstChild());
        return card;
    }   
}
