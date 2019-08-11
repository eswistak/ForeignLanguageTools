/*
File: Utils.java
Author: Ethan Swistak
Date: Jul 5, 2019
Purpose:
*/

package DataModel;

import DataModel.DTO.Item;
import Logic.MotherTree;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;


public class Utils {
    
    /**
     * Loads test data file from a default source
     * 
     * 
     * @throws ParserConfigurationException if the parser is not set up correctly(this rarely happens)
     * @throws SAXException if the input file does not conform to the schema(this shouldn't happen assuming that the schema stays the same)
     * @throws IOException if the location where you're reading in the file is not correct or the file doesn't exist
     */
    public static void load() throws ParserConfigurationException, SAXException, IOException {
        InputStream xmlDoc = Utils.class.getResourceAsStream("TestData.xml");
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(xmlDoc);
        if(validateSchema(document)){
            MotherTree tree = MotherTree.getInstance();
            tree.setNodes(document); 
        }else {
            System.out.println("Schema failed to validate");
        }
        Element root = document.getDocumentElement();
        int count = Integer.parseInt(root.getAttribute("count"));
        Item.setCount(count);
    }
    
    /**
     * Loads an XML document that conforms to the schema from a given file location
     * 
     * @param URL the location where you want to load the file from
     * @throws ParserConfigurationException if the parser is not set up correctly(this rarely happens)
     * @throws SAXException if the input file does not conform to the schema(this shouldn't happen assuming that the schema stays the same)
     * @throws IOException if the location where you're reading in the file is not correct or the file doesn't exist
     */
    public static void load(String URL) throws ParserConfigurationException, SAXException, IOException {
        File xmlFile = new File(URL);
        //https://stackoverflow.com/questions/37104523/convert-org-w3c-dom-document-to-file-file
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(xmlFile);
        if(validateSchema(document)){
            MotherTree tree = MotherTree.getInstance();
            tree.setNodes(document); 
        }else {
            System.out.println("Schema failed to validate");
        }
    }
    
    /**
     * Saves the file to a default location, this is set so that it exports the file to just outside the JAR file
     * 
     * @throws ParserConfigurationException if the parser is not set up correctly(this rarely happens)
     * @throws SAXException if the input file does not conform to the schema(this shouldn't happen assuming that the schema stays the same)
     * @throws IOException if the location where you're reading in the file is not correct or the file doesn't exist
     * @throws TransformerException the system is having trouble converting the XML document to a certain type of output
     */
    public static void save() throws ParserConfigurationException, SAXException, IOException, TransformerException {
        Document file = MotherTree.getInstance().getNodes();
        if(validateSchema(file)){
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            File jarFile = null;
            try {
                jarFile = new File(Utils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            } catch (Exception ex) {
                System.out.println("URL failed to function correctly");
            }
            FileWriter outputFile = new FileWriter(jarFile.getParentFile().getPath() + "\\programOutput.xml");
            StreamResult output = new StreamResult(outputFile);
            Source input = new DOMSource(file);
            transformer.transform(input, output);             
        }else{
            System.out.println("Schema failed to validate");
        }
    }
    
    /**
     * Saves the XML file to whatever location you select
     * 
     * @param URL
     * @throws ParserConfigurationException if the parser is not set up correctly(this rarely happens)
     * @throws SAXException if the input file does not conform to the schema(this shouldn't happen assuming that the schema stays the same)
     * @throws IOException if the location where you're reading in the file is not correct or the file doesn't exist
     * @throws TransformerException the system is having trouble converting the XML document to a certain type of output
     */
    public static void save(String URL) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        Document file = MotherTree.getInstance().getNodes();
        if(validateSchema(file)){
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File(URL));
            Source input = new DOMSource(file);

            transformer.transform(input, output);             
        }else{
            System.out.println("Schema failed to validate");
        }
    }
    
    /**
     *This validates an XML document against the schema used by the program
     * 
     * @param document an XML document
     * @return whether the document is a valid ForeignLanguageTool XML document
     */
    public static boolean validateSchema(Document document){
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        URL url = Utils.class.getResource("Schema.xsd");  
        try {
            Schema schema = factory.newSchema(url);
            Validator validator = schema.newValidator();
            
            DOMSource source = new DOMSource(document);
            validator.validate(source);
        } catch (SAXException ex) {
            System.out.println(ex);
            return false;
        } catch (IOException ex) {
            return false;
        }
        
        return true;
    }
    
    
    /**
     * If you screwed up the XML file somehow you can run this method to reset it back to the way it was before
     * 
     */
    public static void reset(){
        String testFile = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
"<!--\n" +
"To change this license header, choose License Headers in Project Properties.\n" +
"To change this template file, choose Tools | Templates\n" +
"and open the template in the editor.\n" +
"-->\n" +
"\n" +
"\n" +
"<User ID='1'>\n" +
"    <name>Ethan Swistak</name>\n" +
"    <NativeLanguage>English</NativeLanguage>\n" +
"    <LanguagePair ID='2'>\n" +
"        <native>English</native>\n" +
"        <target>German</target>\n" +
"        <Group ID='4'>\n" +
"            <name>Das Sagt Man So!</name>\n" +
"            <Document ID='7'>\n" +
"                <title>Geld zum Fenster hinauswerfen</title>\n" +
"                <text>Mit Geld kann nicht jeder umgehen. Der eine spart jeden Cent, während der andere schon am Monatsanfang wieder pleite ist und gar nicht weiß, wofür er das Geld eigentlich ausgegeben hat.</text>\n" +
"                <translation>You can't always get by with just money. blah blah blah</translation>\n" +
"                <Note ID='8'>\n" +
"                    <content>kann is the present form of konnen</content>\n" +
"                    <index>1</index>\n" +
"                    <startChar>0</startChar>\n" +
"                    <endChar>13</endChar>\n" +
"                </Note>\n" +
"                <Card ID='9'>\n" +
"                    <index>1</index>\n" +
"                    <wordAsAppears>umgehen</wordAsAppears>\n" +
"                    <generic>umgehen</generic>\n" +
"                    <partOfSpeech>verb</partOfSpeech>\n" +
"                    <translationInContext>get by</translationInContext>\n" +
"                    <otherTranslation>to deal</otherTranslation>\n" +
"                    <startChar>25</startChar>\n" +
"                    <endChar>32</endChar>\n" +
"                    <timesCorrect>3</timesCorrect>\n" +
"                    <timesIncorrect>1</timesIncorrect>\n" +
"                    <hint>kann nicht jeder umgehen</hint>\n" +
"                    <cardNote>means to deal with or get by, not deal in goods for example</cardNote>\n" +
"                </Card>\n" +
"                <Card ID='10'>\n" +
"                    <index>2</index>\n" +
"                    <wordAsAppears>jeder</wordAsAppears>\n" +
"                    <generic>jeder</generic>\n" +
"                    <partOfSpeech>adj</partOfSpeech>\n" +
"                    <translationInContext>always</translationInContext>\n" +
"                    <otherTranslation></otherTranslation>\n" +
"                    <startChar>19</startChar>\n" +
"                    <endChar>24</endChar>\n" +
"                    <timesCorrect>0</timesCorrect>\n" +
"                    <timesIncorrect>0</timesIncorrect>\n" +
"                    <hint>jeder tag</hint>\n" +
"                    <cardNote></cardNote>\n" +
"                </Card>\n" +
"            </Document>\n" +
"            <Document ID='11'>\n" +
"                <title>Einen Korb Bekommen</title>\n" +
"                <text>Was ist an einem Korb so schlimm? Eigentlich gar nichts! Körbe sind schön und praktisch. Einen Korb voll mit Obst zu bekommen, ist zum Beispiel sehr schön. Aber manche Körbe bekommt niemand gerne.</text>\n" +
"                <translation>What's so bad about a basket? Actually nothing at all! Baskets are beautiful and practical. Getting a basket full of fruit, for example, is very nice. But some baskets nobody likes to get.</translation>\n" +
"                <Note ID='12'>\n" +
"                    <content>nicht so schlim means not so bad, so schlim is kind of the same usage in English</content>\n" +
"                    <index>1</index>\n" +
"                    <startChar>21</startChar>\n" +
"                    <endChar>32</endChar>\n" +
"                </Note>\n" +
"                <Note ID='13'>\n" +
"                    <content>Nothings so bad about a basket but if it's empty it kinda sucks, right?</content>\n" +
"                    <index>2</index>\n" +
"                    <startChar>0</startChar>\n" +
"                    <endChar>32</endChar>\n" +
"                </Note>\n" +
"                <Card ID='14'>\n" +
"                    <index>2</index>\n" +
"                    <wordAsAppears>Korb</wordAsAppears>\n" +
"                    <generic></generic>\n" +
"                    <partOfSpeech>N</partOfSpeech>\n" +
"                    <translationInContext>basket</translationInContext>\n" +
"                    <otherTranslation></otherTranslation>\n" +
"                    <startChar>19</startChar>\n" +
"                    <endChar>23</endChar>\n" +
"                    <timesCorrect>2</timesCorrect>\n" +
"                    <timesIncorrect>1</timesIncorrect>\n" +
"                    <hint>can also be a bad gift</hint>\n" +
"                    <cardNote></cardNote>\n" +
"                </Card>              \n" +
"            </Document>\n" +
"        </Group>\n" +
"        <Group ID='6'>\n" +
"            <name>Sayings</name>\n" +
"            <Document ID='15'>\n" +
"                <title>Kurt Tucholsky</title>\n" +
"                <text>Sprache ist eine Waffe- Haltet Sie scharf</text>\n" +
"                <translation>Language is a weapon - keep it sharp.</translation>\n" +
"                <Card ID='16'>\n" +
"                    <index>2</index>\n" +
"                    <wordAsAppears>Haltet</wordAsAppears>\n" +
"                    <generic>halten</generic>\n" +
"                    <partOfSpeech>N</partOfSpeech>\n" +
"                    <translationInContext>keep</translationInContext>\n" +
"                    <otherTranslation>to stop</otherTranslation>\n" +
"                    <startChar>23</startChar>\n" +
"                    <endChar>29</endChar>\n" +
"                    <timesCorrect>2</timesCorrect>\n" +
"                    <timesIncorrect>1</timesIncorrect>\n" +
"                    <hint>Haltet Sie scharf</hint>\n" +
"                    <cardNote></cardNote>\n" +
"                </Card>\n" +
"            </Document>         \n" +
"        </Group>\n" +
"    </LanguagePair>\n" +
"    <LanguagePair ID='3'>\n" +
"        <native>English</native>\n" +
"        <target>Chinese</target>\n" +
"        <Group ID='5'>\n" +
"            <name>Poetry</name>\n" +
"            <Document ID='19'>\n" +
"                <title>A visit to Qiantang lake in spring</title>\n" +
"                <text>\n" +
"                    孤山寺北贾亭西\n" +
"                    水面初平云脚低\n" +
"                    几处早莺争暖树\n" +
"                    谁家新燕啄春泥\n" +
"                    乱花渐欲迷人眼\n" +
"                    浅草才能没马蹄\n" +
"                    最爱湖东行不足\n" +
"                    绿杨阴里白沙堤\n" +
"                </text>\n" +
"                <translation>\n" +
"                    Gushan Temple is to the north, Jiating pavilion west,\n" +
"                    The water's surface now is calm, the bottom of the clouds low.\n" +
"                    In several places, the first orioles are fighting in warm trees,\n" +
"                    By every house new swallows peck at spring mud.\n" +
"                    Disordered flowers have grown almost enough to confuse the eye,\n" +
"                    Bright grass is able now to hide the hooves of horses.\n" +
"                    I most love the east of the lake, I cannot come often enough\n" +
"                    Within the shade of green poplars on White Sand Embankment.\n" +
"                </translation>\n" +
"                <Card ID='20'>\n" +
"                    <index>1</index>\n" +
"                    <wordAsAppears>迷</wordAsAppears>\n" +
"                    <generic></generic>\n" +
"                    <partOfSpeech></partOfSpeech>\n" +
"                    <translationInContext>confuse</translationInContext>\n" +
"                    <otherTranslation>lost</otherTranslation>\n" +
"                    <startChar>34</startChar>\n" +
"                    <endChar>34</endChar>\n" +
"                    <timesCorrect>0</timesCorrect>\n" +
"                    <timesIncorrect>0</timesIncorrect>\n" +
"                    <hint>乱花渐欲迷人眼</hint>\n" +
"                    <cardNote></cardNote>\n" +
"                </Card>\n" +
"                <Card ID='21'>\n" +
"                    <index>2</index>\n" +
"                    <wordAsAppears>寺</wordAsAppears>\n" +
"                    <generic></generic>\n" +
"                    <partOfSpeech>N</partOfSpeech>\n" +
"                    <translationInContext>temple</translationInContext>\n" +
"                    <otherTranslation></otherTranslation>\n" +
"                    <startChar>3</startChar>\n" +
"                    <endChar>3</endChar>\n" +
"                    <timesCorrect>2</timesCorrect>\n" +
"                    <timesIncorrect>0</timesIncorrect>\n" +
"                    <hint>孤山寺北贾亭西</hint>\n" +
"                    <cardNote>this can be used after names to denote some kind of temple's name, as in gushan寺</cardNote>\n" +
"                </Card>\n" +
"            </Document>\n" +
"        </Group>\n" +
"    </LanguagePair>   \n" +
"</User>\n";
        
        try {
            FileWriter filewriter = new FileWriter(new File("DataModel\\TestData.xml"));
            filewriter.write(testFile);
        } catch (IOException ex) {
            System.out.println("Failed to located file");
        }
        
        try {
            load();
        } catch (ParserConfigurationException ex) {
            System.out.println("Parser was not configured correctly");
        } catch (SAXException ex) {
            System.out.println("SAX messed up somehow");
        } catch (IOException ex) {
            System.out.println("Did not find the file");
        }
    }

}
