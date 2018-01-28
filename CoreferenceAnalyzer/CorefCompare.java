/**
 * Created by Tanner on 11/22/2016.
 */
public class CorefCompare {
    private double similarityScore;
    private boolean genderMatch;
    private boolean hasNaiveHeadNoun;
    private int distance;
    private boolean matchesPlurality;
    private boolean matchesProper;
    private CorefModel candidate;

    public CorefCompare(double similarityScore, boolean genderMatch, boolean hasNaiveHeadNoun, int distance, boolean matchesPlurality, boolean matchesProper, CorefModel candidate) {
        this.similarityScore = similarityScore;
        this.genderMatch = genderMatch;
        this.hasNaiveHeadNoun = hasNaiveHeadNoun;
        this.distance = distance;
        this.matchesPlurality = matchesPlurality;
        this.matchesProper = matchesProper;
        this.candidate = candidate;
    }

    public Double getSimilarityScore() {
        return similarityScore;
    }

    public Boolean isGenderMatch() {
        return genderMatch;
    }

    public Boolean isHasNaiveHeadNoun() {
        return hasNaiveHeadNoun;
    }

    public Integer getDistance() {
        return distance;
    }

    public Boolean isMatchesPlurality() {
        return matchesPlurality;
    }

    public Boolean isMatchesProper() {
        return matchesProper;
    }

    public CorefModel getCandidate() {
        return candidate;
    }
}
