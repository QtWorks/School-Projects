import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Tanner on 9/26/2016.
 */
public class parser {

    Grammar grammar;

    boolean debug = false;

    public parser(String grammarFile) { grammar = new Grammar(grammarFile);}

    public void parseFile(String path){
        File file = new File(path);
        try {
            Scanner sc = new Scanner(file);
			print("");
            while(sc.hasNextLine()){
                String line = sc.nextLine();
                printOutput(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String>[][] parseSentence(String sentence){
        return parseSentence(sentence.split(" "));
    }

    private ArrayList<String>[][] parseSentence(String[] sentence){
        int len = sentence.length;
        ArrayList<String>[][] table = new ArrayList[len][len];
        for(int col = 0; col < len; col++){
            ArrayList<String> wordSet = grammar.lookupSet(sentence[col]);
            table[col][col] = wordSet;
            if(debug)
                printTableDebug(table);
            for(int row = col - 1; row >= 0; row--){
                int offset = col - row;
                int tempRow = row + 1;
                ArrayList<String> result = new ArrayList<String>();
                if(debug)
                    print("Getting results for [" + row + "][" + col + "]\n\n");
                for(int k = col - offset; k < col; k++){
                    ArrayList<String> firstSet = table[row][k];
                    ArrayList<String> secondSet = table[tempRow][col];
                    ArrayList<String> combined = grammar.combined(firstSet,secondSet);
                    if(debug){
                        print("First set at: [" + row + "][" + k + "]");
                        print("First set: " + firstSet.toString());
                        print("Second set at: [" + tempRow + "][" + col + "]");
                        print("Second set: " + secondSet.toString());
                        print("Combined: " + combined.toString() + "\n\n");
                    }
                    result.addAll(combined);

                    tempRow++;
                }
                table[row][col] = result;
            }
        }
        return table;
    }

    public void printTable(ArrayList<String>[][] table){
        for(int i = 0; i < table.length; i++){
            ArrayList<String>[] row = table[i];
            for(int j = i; j < row.length; j++){
                StringBuilder sb = new StringBuilder();
                if(table[i][j] != null){
					if(table[i][j].size() == 0)
						sb.append("-");
                    ArrayList<String> sorted = table[i][j];
                    sorted.sort(new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return o1.compareTo(o2);
                        }
                    });
                    for(String t : sorted){
                        sb.append(t + " ");
                    }
                }
                print(String.format("  chart[" + (i+1) + "," + (j+1) + "]: ",i+1,j+1) + sb.toString());
            }
        }
    }

    public void printOutput(String sentence){
        ArrayList<String>[][] chart = parseSentence(sentence);
        print("PARSING SENTENCE: " + sentence);
        int numParses = 0;
        ArrayList<String> endResult = chart[0][chart.length-1];
        for(String p : endResult){
            if(p.equals("S"))
                numParses++;
        }
        print("NUMBER OF PARSES FOUND: " + numParses);
        print("CHART:");
        printTable(chart);
        print("");
    }



    public void printTableDebug(ArrayList<String>[][] table){
        StringBuilder sb = new StringBuilder();
        sb.append("\tbook\tthe\tflight\tthrough\tHouston\n");
        for(int i = 0; i < table.length; i++){
            ArrayList<String>[] row = table[i];
            sb.append(" i = " + i + "\t");
            for(int j = 0; j < row.length; j++){
                sb.append(row[j] + "\t");
            }
            sb.append("\n");
        }
        print(sb.toString());
    }
    private static void print(String s){
        System.out.println(s);
    }

    public static void main(String[]args){

        parser parser = new parser(args[0]);
        parser.parseFile(args[1]);
    }
}


class Grammar {

    private Hashtable<String, ArrayList<GrammarRule>> rules;

    public Grammar(String path) {
        rules = new Hashtable<String, ArrayList<GrammarRule>>();
        populateRules(path);
    }

    private void populateRules(String path) {
        File grammarFile = new File(path);
        Scanner sc;
        try {
            sc = new Scanner(grammarFile);
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.trim().isEmpty())
                    return;
                String[] split = line.split(" -> ");
                String ruleName = split[0];
                String subRules = split[1];
                GrammarRule rule = new GrammarRule(ruleName, subRules);
                if (rules.containsKey(subRules)) {
                    rules.get(subRules).add(rule);
                } else {
                    ArrayList<GrammarRule> list = new ArrayList<GrammarRule>();
                    list.add(rule);
                    rules.put(subRules, list);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> lookupSet(String s){
        ArrayList<GrammarRule> rules = lookup(s);
        ArrayList<String> result = new ArrayList<String>();
        for(GrammarRule rule : rules){
            result.add(rule.getName());
        }
        return result;
    }

    public ArrayList<GrammarRule> lookup(String s) {
        if (rules.containsKey(s))
            return rules.get(s);
        return null;
    }

    public ArrayList<String> combined(ArrayList<String> set1, ArrayList<String> set2){
        if(set1 == null)
            return set2;
        if(set2 == null)
            return set1;
        ArrayList<String> result = new ArrayList<String>();
        for(String s1 : set1){
            for(String s2 : set2){
                String key = s1 + " " + s2;
                if(rules.containsKey(key)){
                    ArrayList<GrammarRule> list = rules.get(key);
                    for(GrammarRule r : list){
                        result.add(r.getName());
                    }
                }
            }
        }
        return result;
    }
}

class GrammarRule {
    String name;
    String[] subRules;

    public GrammarRule(String name, String subRules){
        this.name = name;
        this.subRules = subRules.split(" ");
    }

    public String getName() {
        return name;
    }

    public String[] getSubRules() {
        return subRules;
    }

    public String getFirst(){
        return subRules[0];
    }
}
