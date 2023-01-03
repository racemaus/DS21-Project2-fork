package com.assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Collections;

/**
 * Från lektionsexemplen
 */
public class Analyze {
    ArrayList<Language> languages = new ArrayList<>();
    HashMap<LangLabel, Language> languagesMap = new HashMap<>();

    /**
     * Från lektionsexemplen
     */
    // Loopar enums och skapar language instanser
    public void loopEnums() {
        for (LangLabel lang : LangLabel.values()) {
            // Exempel: sätt in instanser av Language i en HashMap med enum som nyckel
            languagesMap.put(lang, new Language(FileUtils.readFile("assets/lang-samples/" + lang + ".txt"), lang));
        }

    }

    // DEL 1 & DEL 2 i samma metod
    public HashMap compareLanguages(Language userLang) {
        loopEnums();
        HashMap<LangLabel, Double> absList = new HashMap<>();
        HashMap<LangLabel, Double> threesAbsList = new HashMap<>();
        HashMap<LangLabel, Double> firstsAbsList = new HashMap<>();
        HashMap<LangLabel, Double> combinedList = new HashMap<>();
        String guess, threesGuess, firstsGuess, combined;

        for (LangLabel key : languagesMap.keySet()) {
            Language current = languagesMap.get(key);

            double totalAbs = 0;
            double totalAbsThrees = 0;
            double totalAbsFirsts = 0;
            double allCombined = 0;
            /* Jämför användarens sträng med värden från exemplen
             *   Från lektionsexemplen
             */
            for (String letter : current.getCharDistribution().keySet()) {
                /*
                 * Fortsätter ifall det inte finns null i hashmapen getordefault i language.java
                 * gör det inte av nån anledning
                 */
                if (userLang.getCharDistribution().get(letter) != null) {
                    totalAbs += Math.abs(userLang.getCharDistribution().get(letter) - current.getCharDistribution().get(letter));
                    // avrundar till 2 decimaler
                    totalAbs = Math.round(totalAbs * 100.0) / 100.0;
                    // Sätter in summan av absolutbelopp för ett språk
                    absList.put(key, totalAbs);

                    // Sätter input språket som 0 ifall den är NULL i listan
                } else {
                    totalAbs += Math.abs(0 - current.getCharDistribution().get(letter));
                    // avrundar till 2 decimaler
                    totalAbs = Math.round(totalAbs * 100.0) / 100.0;
                    // Sätter in summan av absolutbelopp för ett språk
                    absList.put(key, totalAbs);
                }

            }

            // Fördelning av tre tecken på samma sätt som i del 1
            for (String letter : current.getThreesDist().keySet()) {
                if (userLang.getThreesDist().get(letter) != null) {
                    totalAbsThrees += Math.abs(userLang.getThreesDist().get(letter) - current.getThreesDist().get(letter));
                    totalAbsThrees = Math.round(totalAbsThrees * 100.0) / 100.0;
                    threesAbsList.put(key, totalAbsThrees);
                } else {
                    totalAbsThrees += Math.abs(0 - current.getThreesDist().get(letter));
                    totalAbsThrees = Math.round(totalAbsThrees * 100.0) / 100.0;
                    threesAbsList.put(key, totalAbsThrees);
                }
            }

            // Del 3 - Fördelning av första tecknet i varje ord
            for (String letter : current.getFirstDist().keySet()) {
                if (userLang.getFirstDist().get(letter) != null) {
                    totalAbsFirsts += Math.abs(userLang.getFirstDist().get(letter) - current.getFirstDist().get(letter));
                    totalAbsFirsts = Math.round(totalAbsFirsts * 100.0) / 100.0;
                    firstsAbsList.put(key, totalAbsFirsts);
                } else {
                    totalAbsFirsts += Math.abs(0 - current.getFirstDist().get(letter));
                    totalAbsFirsts = Math.round(totalAbsFirsts * 100.0) / 100.0;
                    firstsAbsList.put(key, totalAbsFirsts);
                }
            }
            //medeltalet på alla kombinerat
            allCombined = (totalAbs + totalAbsThrees + totalAbsFirsts) / 3;
            allCombined = Math.round(allCombined * 100.0) / 100.0;
            combinedList.put(key, allCombined);
        }

        LinkedHashMap sortedList = sortHashMap(absList);
        LinkedHashMap sortedThreesList = sortHashMap(threesAbsList);
        LinkedHashMap sortedFirstsList = sortHashMap(firstsAbsList);
        LinkedHashMap sortedCombined = sortHashMap(combinedList);

        // Returnerar gissningarna i en array
        HashMap<String, LinkedHashMap> guesses = new HashMap<>();
        guesses.put("sortedList", sortedList);
        guesses.put("sortedThreesList", sortedThreesList);
        guesses.put("sortedFirstsList", sortedFirstsList);
        guesses.put("sortedCombined", sortedCombined);

        return guesses;
    }

    // Credits: https://stackoverflow.com/a/8119406
    public LinkedHashMap<LangLabel, Double> sortHashMap(HashMap<LangLabel, Double> hashmap) {
        /*
         * Sorterar hashmapen
         */
        List<LangLabel> mapLang = new ArrayList<>(hashmap.keySet());
        List<Double> mapValues = new ArrayList<>(hashmap.values());
        Collections.sort(mapValues);
        Collections.sort(mapLang);

        LinkedHashMap<LangLabel, Double> sortedMap = new LinkedHashMap<>();

        Iterator<Double> keyVal = mapValues.iterator();

        while (keyVal.hasNext()) {
            Double val = keyVal.next();
            Iterator<LangLabel> valueIt = mapLang.iterator();

            while (valueIt.hasNext()) {
                LangLabel key = valueIt.next();
                Double comp1 = hashmap.get(key);
                Double comp2 = val;

                if (comp1.equals(comp2)) {
                    valueIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }
}
