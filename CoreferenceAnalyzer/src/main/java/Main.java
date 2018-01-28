import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.scoref.Clusterer;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;
import org.xml.sax.SAXException;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Tanner on 10/24/2016.
 */

public class Main {


    public static void main(String[] args) throws IOException, SAXException {
        String listFilePath = args[0];
        String responseDir = args[1];

        List<String> inputs = Files.readAllLines(Paths.get(listFilePath));
        Coreferencer coreferencer = new Coreferencer();
        //System.out.println("TRAINING");

        if (new File("machineLearning.xml").exists()) {
            coreferencer.TrainModel();
        } else {
            coreferencer.TrainModel(new String[]{"test\\dev","test\\tst1"}, ".fkey");
            //coreferencer.TrainModel(new String[]{"test\\dev"}, ".fkey");
        }
        //System.out.println("FINISHED TRAINING");

        File responseList = new File("response.listfile");
        PrintWriter writer = new PrintWriter(responseList);

        for(String inputPath : inputs){
            File inputFile = new File(inputPath);
            String name = inputFile.getName();
            int pos = name.lastIndexOf(".");
            if (pos > 0) {
                name = name.substring(0, pos);
            }
            String outputPath = responseDir + name + ".response";
            writer.write(outputPath + "\n");
            File outputFile = new File(outputPath);
            outputFile.createNewFile();
            System.out.println("NOW WRITING " + outputPath);
            coreferencer.Coreference(inputFile, outputFile);
        }
        writer.close();

        System.out.println("");

    }
}
