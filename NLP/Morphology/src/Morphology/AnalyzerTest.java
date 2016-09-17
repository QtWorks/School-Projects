package Morphology;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by Tanner on 8/31/2016.
 */
public class AnalyzerTest {
    Analyzer analyzer;
    String testPath = "resources\\test.txt";
    String tracePath = "resources\\trace.txt";
    String dictPath = "resources\\dict.txt";
    String rulesPath = "resources\\rules.txt";

    @org.junit.Before
    public void setUp() throws Exception {
        analyzer = new Analyzer(dictPath,rulesPath);
    }

    @org.junit.After
    public void tearDown() throws Exception {
    }

    @Test
    public void TestIndividualWords(){
        Assert.assertTrue(HasDefinitions("antisleep"));
        Assert.assertTrue(HasDefinitions("stored"));
        Assert.assertTrue(HasDefinitions("goosey"));
        Assert.assertTrue(HasDefinitions("hairiest"));
        Assert.assertTrue(HasDefinitions("hairy"));
        Assert.assertTrue(HasDefinitions("sleepy"));
        Assert.assertTrue(HasDefinitions("walks"));
        Assert.assertTrue(HasDefinitions("slowest"));

        Assert.assertFalse(HasDefinitions("antisleepiest"));
        Assert.assertFalse(HasDefinitions("BYU"));
        Assert.assertFalse(HasDefinitions("sleeping"));
        Assert.assertFalse(HasDefinitions("sitting"));
        Assert.assertFalse(HasDefinitions("viewest"));
        Assert.assertFalse(HasDefinitions("sitest"));
    }

    public boolean HasDefinitions(String s){
        ArrayList<DefinitionModel> defs = analyzer.GetDefinitions(s);
        return defs.size() > 0 && !defs.get(0).getSource().equals("default");
    }

    @Test
    public void TestFile(){

        try {
            String expected = readFile(tracePath);
            String actual = analyzer.StringDefinitionsFromFile(testPath);
            for(int i = 0; i < actual.length(); i++){
                if(expected.charAt(i) != actual.charAt(i))
                    System.out.println("Char: " + i);
            }
            Assert.assertTrue(expected.equals(actual));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void PrintDefs(){
        System.out.print(analyzer.StringDefinitionsFromFile(testPath));
    }

    static String readFile(String path)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, "UTF-8");
    }

}