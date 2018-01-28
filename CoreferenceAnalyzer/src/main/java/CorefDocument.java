
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.trees.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tanner on 11/2/2016.
 */
public class CorefDocument {
    private List<CorefModel> corefs;
    public CorefDocument(List<CorefModel> corefs){
        this.corefs = corefs;
    }
    public List<CorefModel> getCorefs(){
        return corefs;
    }

    public List<CorefModel> getEntities(){
        List<CorefModel> entities = new ArrayList<CorefModel>();
        for(CorefModel model : corefs){
            if(!model.getId().isEmpty()){
                entities.add(model);
            }
        }
        return entities;
    }

    public void assignAllCorefId(CorefModel original, List<CorefModel> references){
        for(CorefModel model : references){
            assignCorefId(original,model);
        }
    }

    public void assignSentenceId(StanfordWrapper corenlp, List<HasWord> sentences){
        int index = 0;
        for(int j = 0; j < sentences.size(); j++){
            String sentence = sentences.get(j).toString();
            StringBuilder sb = new StringBuilder();
            List<CorefModel> tempCorefs = new ArrayList<CorefModel>();
            for(int i = index; i < corefs.size(); i++){
                tempCorefs.add(corefs.get(i));
                sb.append(corefs.get(i).getText());
                if(sb.toString().contains(sentence)){
                    for(CorefModel model : tempCorefs){
                        model.setSentenceId(i);
                    }
                    index += tempCorefs.size();
                }
            }
        }
    }

    public void assignCorefId(CorefModel original, CorefModel reference){
        reference.setRefId(original.getRefId());
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("<TXT>\n");
        for(CorefModel model : corefs){
            if(!model.getId().isEmpty()){
                sb.append("<COREF ID=\"" + model.getId() + "\"");
                if(!model.getRefId().isEmpty())
                    sb.append(" REF=\"" + model.getRefId() + "\"");
                sb.append(">");
                sb.append(model.getOriginalText());
                sb.append("</COREF>\n");
            }
        }
        sb.append("</TXT>");
        return sb.toString();
    }

    public CorefModel getById(String refId) {
        for(CorefModel model : corefs){
            if(model.getId().equals(refId))
                return model;
        }
        return null;
    }

    public int getDistance(CorefModel original, CorefModel model) {
        return (corefs.indexOf(model) - corefs.indexOf(original));
    }
}
