package com.assignment;

import java.util.HashMap;
import java.util.Scanner;

// UI logik hit
public class UI {
    Scanner sc = new Scanner(System.in, "Windows-1252");
    Analyze analyze = new Analyze();
    Language userLang;
    HashMap<String, HashMap> guesses;

    public void writeSomething() {
        System.out.println("Write a text and let me guess the language: ");

        // Läser in input.txt som user input för att få med specialtecken
        String text = FileUtils.readFile("assets/lang-samples/input.txt");
        userLang = new Language(text);
        guesses = analyze.compareLanguages(userLang);

        HashMap combinedList = guesses.get("sortedCombined");

        System.out.format("+-------+----------+----------+----------+------------+---------+\n");
        System.out.format("| Språk | Analys 1 | Analys 2 | Analys 3 | Kombinerat | Ordning |\n");
        int count = 1;
        for (Object key : combinedList.keySet()) {

            double anal1 = (double) guesses.get("sortedList").get(key);
            double anal2 = (double) guesses.get("sortedThreesList").get(key);
            double anal3 = (double) guesses.get("sortedFirstsList").get(key);
            double combined = (double) guesses.get("sortedCombined").get(key);
            System.out.format("+-------+----------+----------+----------+------------+---------+\n");
            System.out.format("| %s    | %.2f    | %.2f  | %.2f    | %.2f      | %d\n", key.toString(), anal1, anal2,
                    anal3, combined, count);
            count++;
        }
    }
}
