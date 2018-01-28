import edu.stanford.nlp.trees.Tree;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by Tanner on 10/28/2016.
 */
public class XMLWrapper {

    private static final String punctuation = ".\"',";
    private static String[] links = new String[]
    {
            "who","what","that","which"
    };

    public static List<CorefDocument> getCorefDocs(StanfordWrapper stanford, File inputFile) throws IOException, SAXException {
        List<CorefDocument> result = new ArrayList<CorefDocument>();

        return result;
    }

    private static CorefModel getModel(Node node, StanfordWrapper stanford) {
        String text = node.getTextContent();
        text = NormalizeText(text);
        CorefModel model = null;
        String id = "";
        String refId = "";
        if(node.getNodeType() == Node.ELEMENT_NODE){
            Element e = (Element) node;
            id = e.getAttribute("ID");
            if(e.hasAttribute("REF"))
                refId = e.getAttribute("REF");
            model = new CorefModel(stanford,id,text,refId);
        }
        return model;
    }

    public static CorefDocument getCorefs(StanfordWrapper stanford, File inputFile, boolean createNew) throws IOException, SAXException {
        List<CorefModel> list = new ArrayList<CorefModel>();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        Node txt = doc.getFirstChild();
        NodeList children = txt.getChildNodes();
        int newNPid = 0;
        for(int i = 0; i < children.getLength(); i++){
            Node child = children.item(i);
            String text = child.getTextContent();
            text = NormalizeText(text);
            CorefModel model;
            String id = "";
            String refId = "";
            if(child.getNodeType() == Node.ELEMENT_NODE){
                Element eElement = (Element) child;
                id = eElement.getAttribute("ID");
                if(eElement.hasAttribute("REF"))
                    refId = eElement.getAttribute("REF");
                model = new CorefModel(stanford, id,text,refId);
                list.add(model);
            }else if(createNew){
               // text = CleanText(text);
                List<String> nouns = stanford.getNounPhrasesStrings(text,true);
                int startIndex = 0;
                for(String noun : nouns){
                    if(isJunk(noun))
                        continue;
                    int npIndex = text.indexOf(noun);

                    if(npIndex > startIndex){
                        String before = text.substring(startIndex,npIndex);
                        startIndex = startIndex + noun.length();
                        model = new CorefModel(stanford, "X" + (newNPid++),noun,"");
                        list.add(model);
                    }
                }
            }
        }
        return new CorefDocument(list);
    }

    private static String CleanText(String text) {
        text = text.replaceAll("-"," ");
        return text;
    }

    private static boolean isJunk(String noun) {
        boolean a = punctuation.contains(noun);
        boolean b = false;
        String[] s = noun.split("\\s+");
        if(s.length > 0){
            for(String link : links){
                if(s[0].equalsIgnoreCase(link))
                    b = true;
            }
        }
        return a || b;
    }

    private static String NormalizeText(String text) {
        return text.replaceAll("\\s+"," ").trim();
    }

    public static String getText(String inputPath) throws IOException, SAXException {
        File xmlFile = new File(inputPath);
        List<CorefModel> list = new ArrayList<CorefModel>();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        NodeList nList = doc.getElementsByTagName("TXT");
        return nList.item(0).getTextContent();
    }

    public static void writeDocumentToFile(File outputFile, CorefDocument doc){

        try {
            FileWriter writer = new FileWriter(outputFile.getAbsolutePath());
            writer.write(doc.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(MLModel model, String filename) throws FileNotFoundException{
        XMLEncoder encoder =
                new XMLEncoder(
                        new BufferedOutputStream(
                                new FileOutputStream(filename)));

        encoder.writeObject(model);
        encoder.writeObject(model.getFilesTrainedOn());
        encoder.writeObject(model.getData());
        encoder.writeObject(model.getAverageDistance());
        encoder.writeObject(model.getAverageSimilarityScore());
        encoder.close();
    }

    public static MLModel read(String filename) throws Exception {
        XMLDecoder decoder =
                new XMLDecoder(new BufferedInputStream(
                        new FileInputStream(filename)));
        MLModel model = (MLModel) decoder.readObject();
        model.setFilesTrainedOn((List<String>) decoder.readObject());
        model.setData((Hashtable<String, Hashtable<String, Integer>>) decoder.readObject());
        model.setAverageDistance((Double) decoder.readObject());
        model.setAverageSimilarityScore((Double) decoder.readObject());
        decoder.close();
        return model;
    }
}
