import edu.stanford.nlp.trees.Tree;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import org.xml.sax.SAXException;

/**
 * Created by Tanner on 10/24/2016.
 */
public class Coreferencer {
    StanfordWrapper stanford;
    CorefDocument corefModels;
    String text;
    MLModel mlModel;
    double SIMILARITY_THRESHOLD = 0.0;

    public Coreferencer(){
        stanford = new StanfordWrapper();
    }

    public void TrainModel() {
        //System.out.println("Using Existing Model");
        try {
            mlModel = XMLWrapper.read("machineLearning.xml");
            mlModel.setStanford(stanford);
        } catch (Exception e) {
            TrainModel(new String[]{"test\\dev","test\\tst1"}, ".fkey");
            //TrainModel(new String[]{"test\\dev"}, ".fkey");

        }
    }

    public void TrainModel(String[] inputDirectories, String ext){
        //System.out.println("Training New Model");
        mlModel = new MLModel(stanford);
        mlModel.trainOnFiles(inputDirectories,ext);
        try {
            XMLWrapper.write(mlModel,"machineLearning.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void Coreference(File inputFile, File outputFile) throws IOException, SAXException {
        corefModels = XMLWrapper.getCorefs(stanford, inputFile, true);
        mlAlgorithm();
        XMLWrapper.writeDocumentToFile(outputFile,corefModels);
    }

    private void mlAlgorithm() {
        for(int i = 0; i < corefModels.getCorefs().size(); i++){
            CorefModel current = corefModels.getCorefs().get(i);
            if(current.getId().isEmpty())
                continue;
            CorefModel original = getOriginal(current, i);
           // String refId = getAncestorReference(original);
            if(original != null){
                current.setRefId(original.getId());
            }
        }
    }

    private String getAncestorReference(CorefModel model) {
        if(model != null){
            if(model.getRefId().isEmpty())
                return model.getId();
            else
                return getAncestorReference(corefModels.getById(model.getRefId()));
        }
        return null;
    }

    private CorefModel getOriginal(CorefModel current, int index) {
        index--;
        List<CorefCompare> comparisons = new ArrayList<CorefCompare>();
        boolean itFlag = current.getText().equalsIgnoreCase("it");
        for(int i = index; i >= 0; i--){
            CorefModel candidate = corefModels.getCorefs().get(i);
            if(itFlag)
                if(candidate.getText().startsWith("the"))
                    return candidate;
            if(candidate.getText().equals(current.getText()))
                return candidate;
            boolean genderMatch = stanford.genderMatch(candidate,current);
            double candidateScore = mlModel.getSimilarityScore(current, candidate, index - i);
            boolean hasNaiveHeadNoun = false;
            if(candidate.getNaiveHeadNoun() != null && current.getNaiveHeadNoun() != null &&
                    candidate.getNaiveHeadNoun().equals(current.getNaiveHeadNoun())){
                hasNaiveHeadNoun = true;
            }
            boolean matchesPlurality = current.getIsPlural() == candidate.getIsPlural();
            boolean matchesProper = current.getIsProper() == candidate.getIsProper();
            comparisons.add(new CorefCompare(candidateScore,genderMatch,hasNaiveHeadNoun,(index-i)+1,matchesPlurality,matchesProper,candidate));
        }
        SortComparisons(comparisons);
        if(!comparisons.isEmpty()){
            CorefCompare first = comparisons.get(0);
            if(first.getSimilarityScore() > SIMILARITY_THRESHOLD || first.isHasNaiveHeadNoun())
                return first.getCandidate();
            else if(first.isMatchesProper() && first.isMatchesPlurality()){//&& (first.getDistance() <= mlModel.getAverageDistance())){
                return first.getCandidate();
            }
        }
        return null;
    }


    private void SortComparisons(List<CorefCompare> comparisons){
        //Least important
        comparisons.sort(new Comparator<CorefCompare>() {
            public int compare(CorefCompare o1, CorefCompare o2) {
                return o2.isGenderMatch().compareTo(o2.isGenderMatch());
            }
        });
        comparisons.sort(new Comparator<CorefCompare>() {
            public int compare(CorefCompare o1, CorefCompare o2) {
                return o2.isHasNaiveHeadNoun().compareTo(o1.isHasNaiveHeadNoun());
            }
        });
        comparisons.sort(new Comparator<CorefCompare>() {
            public int compare(CorefCompare o1, CorefCompare o2) {
                return o1.getDistance().compareTo(o2.getDistance());
            }
        });
        comparisons.sort(new Comparator<CorefCompare>() {
            public int compare(CorefCompare o1, CorefCompare o2) {
                return o2.isMatchesPlurality().compareTo(o1.isMatchesPlurality());
            }
        });
        comparisons.sort(new Comparator<CorefCompare>() {
            public int compare(CorefCompare o1, CorefCompare o2) {
                return o2.isMatchesProper().compareTo(o1.isMatchesProper());
            }
        });
        //Most important
        comparisons.sort(new Comparator<CorefCompare>() {
            public int compare(CorefCompare o1, CorefCompare o2) {
                return o2.getSimilarityScore().compareTo(o1.getSimilarityScore());
            }
        });
    }
}
