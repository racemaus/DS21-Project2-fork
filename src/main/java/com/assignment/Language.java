package com.assignment;

import java.util.HashMap;

/**
 * Lektionsexempel
 */
public class Language implements Comparable {

    private String content; // Språkets textinnehåll
    private LangLabel langLabel; // enum

    // HashMaps för olika slags statistik
    private final HashMap<String, Integer> charCount = new HashMap<>();
    private final HashMap<String, Double> charDistribution = new HashMap<>();
    private final HashMap<String, Integer> threesCount = new HashMap<>();
    private final HashMap<String, Double> threesDistribution = new HashMap<>();
    private final HashMap<String, Integer> firstCount = new HashMap<>();
    private final HashMap<String, Double> firstDistribution = new HashMap<>();

    // Konstruktor som tar emot textinnehåll och språk-label (enum)
    // Fundera ännu på hur vi ska göra med att instansiera ett objekt
    // för ett okänt språk.
    public Language(String content, LangLabel langLabel) {
        this.content = content.toLowerCase();
        this.langLabel = langLabel;
        calcFirstDist();
        calculateCharDistribution();
        calcThreesDist();
    }

    // Overloading för okända språk
    public Language(String content) {
        this.content = content.toLowerCase();
        calcFirstDist();
        calculateCharDistribution();
        calcThreesDist();

    }

    private void calculateCharDistribution() {
        // Regex för att plocka bort allt som inte är bokstäver:
        // ( \\p{L} matchar alla unicode-bokstäver, ^ betyder "inte")
        this.content = content.replaceAll("[^\\p{L}]", "");
        for (int i = 0; i < content.length(); i++) {
            // Vi använder Character.toString för att typecasta char som
            // vi får från charAt() till Strings som är nyckeln i charCount
            String letter = Character.toString(content.charAt(i));

            // HashMap.getOrDefault() kan användas också på element som
            // eventuellt inte ännu har något värde
            charCount.put(letter, charCount.getOrDefault(letter, 0) + 1);
        }

        for (String key : charCount.keySet()) {
            // Vi räknar ut procentuell andel av varje tecken
            double distr = (double) charCount.getOrDefault(key, 0) / (double) content.length() * 100;
            charDistribution.put(key, distr);
        }

    }

    //DEL 2 - kombination av tre tecken (samma som chardistr, men med strängar av 3)
    private void calcThreesDist() {

        //Skapar strängar av 3 på samma sätt som i calculateCharDist
        for (int i = 0; i < content.length() - 2; i++) {
            String threes = Character.toString(content.charAt(i)) + content.charAt(i + 1) + content.charAt(i + 2);
            threesCount.put(threes, threesCount.getOrDefault(threes, 0) + 1);
        }

        for (String key : threesCount.keySet()) {
            double distr = (double) threesCount.getOrDefault(key, 0) / (double) content.length() * 100;
            threesDistribution.put(key, distr);
        }
    }

    //DEL 3 - Första bokstaven i ett ord (från lektionsexemplen)
    private void calcFirstDist() {

        String[] wordsArr = content.replaceAll("\\s+", " ").split(" ");
        for (String word : wordsArr) {
            String firstLetter = word.substring(0, 1);
            firstCount.put(firstLetter, firstCount.getOrDefault(firstLetter, 0) + 1);
        }

        for (String key : firstCount.keySet()) {
            double distr = (double) firstCount.getOrDefault(key, 0) / (double) wordsArr.length * 100;
            firstDistribution.put(key, distr);
        }
    }

    public String getContent() {
        return content;
    }

    public HashMap<String, Integer> getCharCount() {
        return charCount;
    }

    public HashMap<String, Double> getCharDistribution() {
        return charDistribution;
    }

    public HashMap<String, Integer> getThreesCount() {
        return threesCount;
    }

    public HashMap<String, Double> getThreesDist() {
        return threesDistribution;
    }

    public HashMap<String, Integer> getFirstCount() {
        return firstCount;
    }

    public HashMap<String, Double> getFirstDist() {
        return firstDistribution;
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

}