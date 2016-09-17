package Morphology;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 *
 * Created by Tanner on 8/31/2016.
 */
class Analyzer {

    /**
     * String for Dictionary as a source
     */
    private final String DICT = "dictionary";
    /**
     * String for Morphology as a source
     */
    private final String MORPH = "morphology";
    /**
     * Dictionary object containing word models for each string
     */
    private HashMap<String,WordModel> dictionary;
    /**
     * List of all rules. Don't need a HashMap because we just iterate through ALL of them exhaustively every time
     */
    private ArrayList<RuleModel> rules;

    /**
     * Creates a new Analyzer with the given rules and dictionary path
     * @param dictPath - Path to dictionary file
     * @param rulesPath - Path to rules file
     */
    Analyzer(String dictPath, String rulesPath){
        //Populate the dictionary with file
        dictionary = ReadDictionary(dictPath);
        //Populate the rules with file
        rules = ReadRules(rulesPath);
    }

    /**
     * Driver method for getting the definitions for a given word
     * @param word - Word to look up
     * @return - Set of all definitions for word
     */
    ArrayList<DefinitionModel> GetDefinitions(String word){
        //Normalize word
        word = word.toLowerCase();
        //Get definitions for word
        ArrayList<DefinitionModel> result = GetDefinitionsRecursive(word);
        //If there are no definitions, return set with just the default definition
        if(result.isEmpty())
            return new ArrayList<DefinitionModel>(Arrays.asList(new DefinitionModel(word)));
        else
            return result;
    }

    /**
     * Gets the definitions of word. Applies rules recursively if no definitions are found in dictionary
     * @param word - Word for which to seek definitions
     * @return Set of all definitions (one word may have multiple)
     */
    ArrayList<DefinitionModel> GetDefinitionsRecursive(String word){
        //If there are definitions in the dictionary, stop the process
        ArrayList<DefinitionModel> definitions = Lookup(word);
        if(definitions.size() > 0){
            return definitions;
        }
        //Otherwise, get definition from Morphology
        for(RuleModel rule : rules){
            //Get substring with rule. Empty string if rule doesn't apply
            String sub = GetSubstring(word,rule);
            if(sub.length() > 0){
                //Recursive call
                ArrayList<DefinitionModel> subDefs = GetDefinitionsRecursive(sub);
                //Iterate through sub-definitions to check if any match for the given rule
                for(DefinitionModel subDef : subDefs){
                    if(subDef.getPartOfSpeech().equals(rule.getPartOfSpeech())){
                        //If there is a match, add it to the dictionary
                        DefinitionModel d = new DefinitionModel(word,rule.getNewPartOfSpeech(),subDef.getRoot(),MORPH);
                        //If the definition is not already in the list
                        if(!DefContains(definitions, d))
                            definitions.add(d);
                    }
                }
            }
        }
        return definitions;
    }

    /**
     * Returns true if set contains definition, false otherwise
     */
    private boolean DefContains(ArrayList<DefinitionModel> definitions, DefinitionModel d) {
        for(DefinitionModel def : definitions){
            if(def.equals(d))
                return true;
        }
        return false;
    }

    /**
     * Gets the substring from the word according to the rule given
     * @param word
     * @param rule
     * @return - Substring of word
     */
    private String GetSubstring(String word, RuleModel rule) {
        //Get the replacement characters if they exist (not "-")
        String replace = (rule.getReplace().equals("-")) ? "" : rule.getReplace();
        //If the rule is a prefix and the word has the prefix, get the remaining letters (minus the prefix) with the replacement
        if(rule.isPrefix() && word.startsWith(rule.getChars())){
            return replace + word.substring(rule.getChars().length());
        }
        //If the rule is a suffix and the word has the suffix, get the remaining letters (minus the suffix) with the replacement
        else if(!rule.isPrefix() && word.endsWith(rule.getChars())){
            return word.substring(0, word.length() - rule.getChars().length()) + replace;
        }
        //Rule does not apply. Return empty string
        return "";
    }

    /**
     * Returns all definitions found in the dictionary for a word. Empty set if there are none
     * @param word
     * @return
     */
    private ArrayList<DefinitionModel> Lookup(String word){
        if(dictionary.containsKey(word))
            return dictionary.get(word).GetDefinitions();
        else
            return new ArrayList<DefinitionModel>();
    }

    /**
     * Prints definitions for all words (separated by line) from a given file
     * @param path
     */
    String StringDefinitionsFromFile(String path){
        File file = new File(path);
        StringBuilder builder = new StringBuilder();
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                builder.append(StringDefinitions(line.trim()));
                builder.append("\n");
            }
        } catch (FileNotFoundException e){ }
        return builder.toString();
    }


    /**
     * Prints all definitions of a given word
     * @param word
     */
    String StringDefinitions(String word){
        StringBuilder builder = new StringBuilder();
        for(DefinitionModel def : GetDefinitions(word)){
            builder.append(def.toString());
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * Reads rule file and populates list of rules
     * @param rulesPath - Path to rules file
     * @return - List of RuleModels to be used in Morphology
     */
    private ArrayList<RuleModel> ReadRules(String rulesPath) {
        ArrayList<RuleModel> rules = new ArrayList<RuleModel>();
        ArrayList<String[]> lines = GetWordsFromFile(rulesPath);
        for(String[] line : lines){
            RuleModel rule = new RuleModel(line[0].equalsIgnoreCase("PREFIX"),line[1],line[2],line[3],line[5]);
            rules.add(rule);
        }
        return rules;
    }

    /**
     * Reads dictionary file and populates HashMap with WordModels according to each word
     * @param path - Path to dictionary file
     * @return - HashMap of Strings mapping to WordModels
     */
    private HashMap<String,WordModel> ReadDictionary(String path){
        HashMap<String,WordModel> words = new HashMap<String,WordModel>();
        ArrayList<String[]> lines = GetWordsFromFile(path);
        for(String[] line : lines){
            String word = line[0];
            String pos = line[1];
            String root = null;
            if(line.length > 3)
                root = line[3];
            DefinitionModel definition = new DefinitionModel(word,pos,root,DICT);
            if(words.containsKey(word)){
                WordModel w = words.get(word);
                w.addDefinition(definition);
                words.replace(word,w);
            }else{
                WordModel w = new WordModel();
                w.addDefinition(definition);
                words.put(word,w);
            }
        }
        return words;
    }

    /**
     * Gets an ArrayList of lines (in the form of String[]) from a given file
     * @param path - Path to file from which to read
     * @return - List of lines
     */
    private ArrayList<String[]> GetWordsFromFile(String path){
        ArrayList<String[]> words = new ArrayList<String[]>();
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                String line = scanner.nextLine();
                String[] split = line.split("\\s+");
                words.add(split);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return words;
    }
}
class DefinitionModel{

    private final String DEFAULT = "default";
    private final String NOUN = "noun";

    private String word;
    private String partOfSpeech;
    private String root;
    private String source;

    /**
     * Creates a default definition for the given word (Used when no definition can be found)
     * @param _word
     */
    DefinitionModel(String _word){
        this.word = _word;
        this.partOfSpeech = NOUN;
        this.root = _word;
        this.source = DEFAULT;
    }

    /**
     * Creates a definition with the given attributes
     * @param _word - Word being defined
     * @param _partOfSpeech - Part of speech for definition
     * @param _root - Root of word
     * @param _source - Source of definition
     */
    DefinitionModel(String _word, String _partOfSpeech, String _root, String _source){
        this.word = _word;
        this.partOfSpeech = _partOfSpeech;
        if(_root != null)
            this.root = _root;
        else
            this.root = _word;
        this.source = _source;
    }

    //BASIC GETTERS

    String getWord() { return word; }
    String getPartOfSpeech() { return partOfSpeech; }
    String getRoot() { return root; }
    String getSource() { return source; }

    @Override
    public boolean equals(Object o){
        DefinitionModel other = (DefinitionModel) o;
        return this.defEquals(other);
    }

    /**
     * Equality defined as the same word, part of speech and root
     */
    private boolean defEquals(DefinitionModel other) {
        return this.word.equals(other.getWord()) &&
                this.partOfSpeech.equals(other.getPartOfSpeech());
    }

    @Override
    public String toString(){
        return String.format("%s %s ROOT=%s SOURCE=%s",this.getWord(),this.getPartOfSpeech(),this.getRoot(),this.getSource());
    }
}

/**
 * Basic data class for words from Dictionary
 */
class WordModel{

    /**
     * Set of definitions for word
     */
    private ArrayList<DefinitionModel> definitions;

    /**
     * Initializes set of definitions
     */
    WordModel(){
        definitions = new ArrayList<DefinitionModel>();
    }

    /**
     * Returns set of definitions for word
     */
    ArrayList<DefinitionModel> GetDefinitions(){
        return definitions;
    }

    /**
     * Adds a definition for word
     */
    void addDefinition(DefinitionModel d){
        if(!definitions.contains(d)){
            definitions.add(d);
        }
    }
}

/**
 * Basic data class for Rules from rules file
 */
class RuleModel{
    /**
     * Rule defines a prefix
     */
    private boolean prefix;
    /**
     * Characters for rule (prefix or suffix)
     */
    private String chars;
    /**
     * Characters to replace prefix or suffix
     */
    private String replace;
    /**
     * Part of speech before application of rule
     */
    private String partOfSpeech;
    /**
     * Part of speech after application of rule
     */
    private String newPartOfSpeech;

    /**
     * Initialize a Rule with the given attributes
     */
    RuleModel(boolean _prefix, String _chars, String _replace, String _partOfSpeech, String _newPartOfSpeech){
        this.prefix = _prefix;
        this.chars = _chars;
        this.replace = _replace;
        this.partOfSpeech = _partOfSpeech;
        this.newPartOfSpeech = _newPartOfSpeech;
    }

    //BASIC GETTERS

    boolean isPrefix() { return prefix; }
    String getChars() { return chars; }
    String getReplace() { return replace; }
    String getPartOfSpeech() { return partOfSpeech; }
    String getNewPartOfSpeech() { return newPartOfSpeech; }
}




