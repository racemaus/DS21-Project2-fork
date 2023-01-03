package com.assignment;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Från lektionsexemplen
 */
public class FileUtils {

    public static String readFile(String fileName) {

        // Vi kan använda StringBuilder-klassen för att få lite mera
        // egenskaper till vår teckensträng
        StringBuilder retStr = new StringBuilder();

        try {
            // Öppnar en ström från en specifik sökväg (t.ex. "assets/lang-samples/English.txt")
            FileInputStream inputStream = new FileInputStream(fileName);
            // Skapar ett reader-objekt av strömmen
            InputStreamReader reader = new InputStreamReader(inputStream);
            // Skapar en buffrad reader
            BufferedReader bufferedReader = new BufferedReader(reader);

            // Då vi använder bufferedReader kan vi loopa en hel rad åt gången
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Vi använder StringBuilder-metoden append() för att lägga till raden
                retStr.append(line);
            }

            bufferedReader.close();
            reader.close();

            // Fånga eventuella Input-Output-errors
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Gör StringBuilder-objektet till en String och returnera
        return retStr.toString();
    }

}