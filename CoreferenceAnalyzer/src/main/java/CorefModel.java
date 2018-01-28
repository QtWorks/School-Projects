import edu.stanford.nlp.trees.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanner on 10/28/2016.
 */
public class CorefModel {
    private String id;
    private int sentenceId;
    private String refId;
    private String text;
    private String originalText;
    private Tree parseTree;
    private List<String> headNouns;
    private String[] words;
    private String naiveHeadNoun = null;
    private List<String[]> taggedWords;
    private boolean isProper = false;
    private boolean isPlural = false;
    private String[] links = new String[]
    {
        "who","what","that","which"
    };


    public CorefModel(StanfordWrapper stanford, String id, String text, String refId){
        this.id = id;
        this.originalText = text;
        if(text != null && !text.isEmpty()){
            this.words = text.split("\\s+");
            for(int i = 0; i < words.length; i++){
                String word = words[i];
                for(String link : links){
                    if(word.contains(link)){
                        if(i > 0){
                            naiveHeadNoun = words[i-1];
                            String newText = "";
                            for(int j = 0; j < i; j++)
                                newText = newText + words[j] + " ";
                            this.text = newText.trim();
                        }
                    }
                }
            }
        }

        if(this.text == null)
            this.text = text;

        String tagged = stanford.getTaggedString(this.text);
        taggedWords = new ArrayList<String[]>();
        for(String s : tagged.split("\\s+")){
            String[] a = s.split("_");
            if(a.length > 1){
                if(a[1].equals("NNP") || a[1].equals("NNPS"))
                    isProper = true;
                if(a[1].equals("NNS") || a[1].equals("NNPS"))
                    isPlural = true;
            }
        }

        this.refId = refId;
        try{
            parseTree = stanford.getFirstParseTree(this.text);
            if(!id.isEmpty()){
                headNouns = stanford.getHeadNouns(parseTree);
            }
        }catch(Exception e){}
        this.text = NormalizeText(this.text);

        if(naiveHeadNoun == null && words.length > 0)
            naiveHeadNoun = words[words.length - 1];
        else
            naiveHeadNoun = null;
    }

    private String NormalizeText(String text) {
        text = text.replaceAll("\\n"," ");
        text = text.replaceAll("[^a-zA-Z ]", "").toLowerCase();
        return text.trim();
    }
    public String getNaiveHeadNoun() { return naiveHeadNoun; }

    public String[] getWords() { return words;}

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getRefId() {
        return refId;
    }

    public String getOriginalText(){ return originalText; }

    public List<String> getHeadNouns(){return headNouns;}

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public boolean getIsProper() { return isProper; }

    public boolean getIsPlural() { return isPlural; }

    public void setSentenceId(int sentenceId) {
        this.sentenceId = sentenceId;
    }
}
