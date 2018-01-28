import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by Tanner on 11/3/2016.
 */
public class MLModel {
    private Hashtable<String,Hashtable<String,Integer>> data;
    private List<String> filesTrainedOn;
    private StanfordWrapper stanford;
    private double count, totalDistance, averageDistance, totalSimilarityScore, averageSimilarityScore;

    public MLModel(StanfordWrapper stanford){
        count = 0;
        totalDistance = 0;
        totalSimilarityScore = 0;
        averageDistance = 0;
        averageSimilarityScore = 0;
        this.stanford = stanford;
        data = new Hashtable<String, Hashtable<String, Integer>>();
        filesTrainedOn = new ArrayList<String>();
    }

    public MLModel(){
        data = new Hashtable<String, Hashtable<String, Integer>>();
        filesTrainedOn = new ArrayList<String>();
    }

    public List<String> getFilesTrainedOn() {
        return filesTrainedOn;
    }

    public Hashtable<String, Hashtable<String, Integer>> getData() {
        return data;
    }

    public void setStanford(StanfordWrapper stanford){
        this.stanford = stanford;
    }

    public void setData(Hashtable<String, Hashtable<String, Integer>> data) {
        this.data = data;
    }

    public void setFilesTrainedOn(List<String> filesTrainedOn) {
        this.filesTrainedOn = filesTrainedOn;
    }

    public Double getAverageDistance(){return averageDistance;}

    public Double getAverageSimilarityScore(){return averageSimilarityScore;}

    public void setAverageDistance(Double distance){averageDistance = distance;}

    public void setAverageSimilarityScore(Double score){averageSimilarityScore = score;}

    public void trainBig(File file){
        if(filesTrainedOn.contains(file.getName()))
            return;
        try {
            List<CorefDocument> docs = XMLWrapper.getCorefDocs(stanford,file);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    public void train(File file){
        if(filesTrainedOn.contains(file.getName()))
            return;
        CorefDocument doc = null;
        try {
            doc = XMLWrapper.getCorefs(stanford,file,true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        if(doc == null) return;
        for(int i = 0; i < doc.getCorefs().size(); i++){
            CorefModel model = doc.getCorefs().get(i);
            if(!model.getRefId().isEmpty()){
                CorefModel original = doc.getById(model.getRefId());
                totalDistance += doc.getDistance(original, model);
                totalSimilarityScore += getSimilarityScore(original, model, i - doc.getCorefs().indexOf(original));
                count++;
                if(original != null){
                    if(!model.getText().equals(original.getText()))
                        addRelation(model.getText(),original.getText());
                    if(model.getHeadNouns() != null && original.getHeadNouns() != null){
                        for(String modelHead : model.getHeadNouns()){
                            for(String originalHead: original.getHeadNouns()){
                                addRelation(modelHead,originalHead);
                            }
                        }
                    }
                }
            }
        }
        filesTrainedOn.add(file.getName());
    }

    public double getSimilarityScore(CorefModel current, CorefModel candidate, int distance) {
        double s = getProbability(current.getText(),candidate.getText());
        s += headNounScore(current,candidate);
        s += (headNounIntersection(current,candidate) * 2);
        if(Double.isNaN(s))
            return 0.0;
        if(averageSimilarityScore > 0){
          /*  s = (s / averageSimilarityScore);
            if(distance > getAverageDistance()){
                s -= ((distance - getAverageDistance()) * 0.01);
            }
            */
        }
        return s;
    }

    private double headNounIntersection(CorefModel current, CorefModel candidate) {
        double count = 0;
        if(current.getHeadNouns() != null && candidate.getHeadNouns() != null){
            for(String hn : current.getHeadNouns()){
                if(candidate.getHeadNouns().contains(hn))
                    count++;
            }
            double result = count / (double)(current.getHeadNouns().size() + candidate.getHeadNouns().size());
            if(!Double.isNaN(result))
                return result;
        }
        return count;
    }

    private double headNounScore(CorefModel current, CorefModel candidate) {
        double headNounProbability = 0;
        List<String> currentHeadNouns = current.getHeadNouns();
        List<String> candidateHeadNouns = candidate.getHeadNouns();
        if(currentHeadNouns != null && candidateHeadNouns != null
                && currentHeadNouns.size() > 0 && candidateHeadNouns.size() > 0){
            for(String currentHeadNoun : currentHeadNouns){
                for(String candidateHeadNoun : candidateHeadNouns){
                    headNounProbability += getProbability(currentHeadNoun,candidateHeadNoun);
                }
            }
            return headNounProbability / ((double) currentHeadNouns.size() + (double) candidateHeadNouns.size());
        }
        return headNounProbability;
    }



    public void addRelation(String reference, String original){
        Hashtable<String,Integer> counts;
        if(!data.containsKey(reference)){
            counts = new Hashtable<String, Integer>();
            counts.put(original,1);
        }else{
            counts = data.get(reference);
            if(counts.containsKey(original)){
                counts.put(original,counts.get(original) + 1);
            }else{
                counts.put(original,1);
            }
        }
        data.put(reference,counts);
    }

    public double getProbability(String reference, String original){
        if(data.containsKey(reference)){
            Hashtable<String,Integer> counts = data.get(reference);
            if(counts.containsKey(original)){
                double total = 0;
                for(int c : counts.values()){
                    total += c;
                }
                double count = counts.get(original);
                return (count / total);
            }
        }
        return 0;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,Hashtable<String,Integer>> entry : data.entrySet()){
            sb.append(entry.getKey() + "\t");
            for(Map.Entry<String,Integer> count : entry.getValue().entrySet()){
                sb.append(count.getKey() + "_" + count.getValue() + "\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    public void trainOnFiles(String[] inputDirectories, String ext) {
        for(String dir : inputDirectories){
            File directory = new File(dir);
            for(File file : directory.listFiles()){
                String extension = getExtension(file);
                if(extension.equals(ext)){
                    train(file);
                }
            }
        }
        averageDistance = (totalDistance / count);
        averageSimilarityScore = (totalSimilarityScore / count);
        System.out.println("Average distance: " + averageDistance);
        System.out.println("Average similarity score: " + averageSimilarityScore);
    }

    private String getExtension(File file) {
        String path = file.getAbsolutePath();
        int index = path.lastIndexOf('.');
        if(index > 0){
            return path.substring(index);
        }
        return "";
    }
}
