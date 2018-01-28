import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Basilisk {

    public static void main(String[] args) throws IOException{
        Basilisk basilisk = new Basilisk(args[0],args[1]);
    }
    /**
     * Starts with containing seed words
     */
    Lexicon lexicon;

    /**
     * Key - Pattern (right side of the *)
     * Value - Set of all unique head nouns (words farthest to the right)
     */
    Hashtable<String, HashSet<String>> contextMapping;

    public Basilisk(String seedsPath, String contextsPath) throws IOException {
        List<String> seedWords = Files.readAllLines(Paths.get(seedsPath));
        PrintSeedWords(seedWords);
        NormalizeWordsToLearn(seedWords);
        lexicon = new Lexicon(seedWords);
        List<String> contexts = Files.readAllLines(Paths.get(contextsPath));
        contextMapping = GetContextMapping(contexts);
        System.out.println("Unique patterns: " + contextMapping.size());
        for (int i = 0; i < 5; i++) {
            System.out.println("\nITERATION " + (i + 1) + "\n");
            Hashtable<String, Double> rLogFScores = GetRLogFScores();
            List<Map.Entry<String, Double>> sortedRLogF = SortByValue(rLogFScores);
            List<Map.Entry<String, Double>> PatternPool = GetTop(sortedRLogF, 10, false);
            System.out.println("PATTERN POOL");
            PrintPatternPool(PatternPool);
            HashSet<String> candidateList = GetCandidateList(PatternPool);
            Hashtable<String, Double> scoredCandidateList = ScoreCandidateList(candidateList);
            List<Map.Entry<String, Double>> sortedCandidates = SortByValue(scoredCandidateList);
            List<Map.Entry<String, Double>> topCandidates = GetTop(sortedCandidates, 5, true);
            System.out.println("NEW WORDS");
            PrintTopCandidates(topCandidates);
        }
    }

    private void PrintSeedWords(List<String> seedWords) {
        StringBuilder sb = new StringBuilder();
        sb.append("\nSeed Words: ");
        for (String s : seedWords) {
            sb.append(s + " ");
        }
        System.out.println(sb.toString());
    }

    private void PrintTopCandidates(List<Map.Entry<String, Double>> topCandidates) {
        for (int i = 0; i < topCandidates.size(); i++) {
            Map.Entry<String, Double> entry = topCandidates.get(i);
            System.out.println(String.format("%s (%.3f)", entry.getKey().toLowerCase(), entry.getValue()));
        }
    }

    private void PrintPatternPool(List<Map.Entry<String, Double>> patternPool) {
        for (int i = 0; i < patternPool.size(); i++) {
            Map.Entry<String, Double> entry = patternPool.get(i);
            System.out.println(String.format("%d. %s (%.3f)", i + 1, entry.getKey(), entry.getValue()));
        }
        System.out.println("");
    }

    private Hashtable<String, Double> ScoreCandidateList(HashSet<String> candidateList) {
        Hashtable<String, Double> scored = new Hashtable<String, Double>();
        for (String s : candidateList) {
            scored.put(s, AvgLogScore(s));
        }
        return scored;
    }

    private double AvgLogScore(String s) {
        double count = 0;
        double total = 0;
        for (HashSet<String> set : contextMapping.values()) {
            if (set.contains(s)) {
                double semFreq = GetSemFreqK(set, s);
                double value = log(semFreq, 2);
                total += log(semFreq + 1, 2);
                count++;
            }
        }
        return total / count;
    }

    private HashSet<String> GetCandidateList(List<Map.Entry<String, Double>> patternPool) {
        HashSet<String> candidateList = new HashSet<String>();
        for (Map.Entry<String, Double> entry : patternPool)
            AddAllUnique(candidateList, contextMapping.get(entry.getKey()));
        return candidateList;
    }

    private void AddAllUnique(HashSet<String> dest, Collection<String> src) {
        for (String s : src) {
            if (!dest.contains(s))
                dest.add(s);
        }
    }

    private List<Map.Entry<String, Double>> GetTop(List<Map.Entry<String, Double>> sorted, int limit, boolean checkLexicon) {
        List<Map.Entry<String, Double>> top = new ArrayList<Map.Entry<String, Double>>();
        for (int i = 0; top.size() < limit && i < sorted.size(); i++) {
            Map.Entry<String, Double> entry = sorted.get(i);
            if (checkLexicon) {
                if (lexicon.addWord(entry.getKey()))
                    top.add(entry);
            } else {
                top.add(entry);
            }
        }
        Map.Entry<String, Double> last = top.get(top.size() - 1);
        if (top.size() == limit) {
            int i = limit;
            while (i < sorted.size() && sorted.get(i).getValue().equals(last.getValue())) {
                top.add(sorted.get(i));
                i++;
            }
        }
        SortAlphabetically(top);
        SortByValue(top);
        return top;
    }

    private void SortByValue(List<Map.Entry<String, Double>> list) {
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });
    }

    private void SortAlphabetically(List<Map.Entry<String, Double>> top) {
        SortSubListAlphabetically(top, 0, top.size());
    }

    private void SortSubListAlphabetically(List<Map.Entry<String, Double>> top, int index, int subIndex) {
        Collections.sort(top.subList(index, subIndex), new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
    }

    private List<Map.Entry<String, Double>> SortByValue(Hashtable<String, Double> rLogFScores) {
        ArrayList<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(rLogFScores.entrySet());
        SortByValue(list);
        return list;
    }

    public Hashtable<String, Double> GetRLogFScores() {
        Hashtable<String, Double> rLogFScores = new Hashtable<String, Double>();
        for (int i = 0; i < 5; i++) {
            Set<Map.Entry<String,HashSet<String>>> set = contextMapping.entrySet();
            Iterator<Map.Entry<String,HashSet<String>>> it = set.iterator();
            while (it.hasNext()) {
                Map.Entry<String, HashSet<String>> entry = (Map.Entry<String, HashSet<String>>) it.next();
                String key = entry.getKey();
                HashSet<String> value = entry.getValue();
                double rLogF = RlogF(key, value);
                rLogFScores.put(key, rLogF);
            }
        }
        return rLogFScores;
    }

    private double RlogF(String pattern, HashSet<String> values) {
        double semFreqK = GetSemFreqK(values, null);
        double totalFreqK = values.size();
        return (semFreqK / totalFreqK) * log(totalFreqK, 2);
    }

    private double GetSemFreqK(HashSet<String> values, String except) {
        HashSet<String> uniqueLexiconMembers = lexicon.getWordSet();
        double count = 0;
        for (String member : uniqueLexiconMembers) {
            if (except != null) {
                if (!member.equals(except))
                    if (values.contains(member))
                        count++;
            } else {
                if (values.contains(member))
                    count++;
            }
        }
        return count;
    }

    private double log(double x, double base) {
        return (Math.log(x) / Math.log(base));
    }

    private Hashtable<String, HashSet<String>> GetContextMapping(List<String> contexts) {
        Hashtable<String, HashSet<String>> contextMapping = new Hashtable<String, HashSet<String>>();
        for (String c : contexts) {
            String[] split = c.split(" \\* ");
            String nounPhrase = split[0];
            String[] nounSplit = nounPhrase.split(" ");
            String headNoun = nounSplit[nounSplit.length - 1];
            String pattern = split[1];
            if (contextMapping.containsKey(pattern)) {
                HashSet<String> set = contextMapping.get(pattern);
                if (!set.contains(headNoun)) {
                    set.add(headNoun);
                    contextMapping.put(pattern, set);
                }
            } else {
                HashSet<String> set = new HashSet<String>();
                set.add(headNoun);
                contextMapping.put(pattern, set);
            }
        }
        return contextMapping;
    }

    private static void NormalizeWordsToLearn(List<String> wordsToLearn) {
        for (int i = 0; i < wordsToLearn.size(); i++) {
            String word = wordsToLearn.get(i);
            String newWord = word.toUpperCase();
            wordsToLearn.set(i, newWord);
        }
    }
}

class Lexicon {
    private List<String> wordList;
    private HashSet<String> wordSet;

    public Lexicon(List<String> seeds){
        wordList = seeds;
        wordSet = new HashSet<String>();
        for(String word : wordList){
            if(!wordSet.contains(word))
                wordSet.add(word);
        }
    }

    public List<String> getWordList() {
        return wordList;
    }

    public boolean containsWord(String s){
        return wordSet.contains(s);
    }

    public boolean addWord(String s){
        if(wordSet.contains(s))
            return false;
        else
            wordSet.add(s);
        return true;
    }

    public HashSet<String> getWordSet() {
        return wordSet;
    }
}
