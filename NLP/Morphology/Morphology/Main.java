package com.company;

public class Main {

    public static void main(String[] args) {
        String dictionary = args[0];
        String rules = args[1];
        String test = args[2];
        Analyzer a = new Analyzer(dictionary,rules);
        a.PrintDefinitionsFromFile(test);
    }
}
