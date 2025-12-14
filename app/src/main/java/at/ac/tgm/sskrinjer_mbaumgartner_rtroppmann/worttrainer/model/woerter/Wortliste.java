package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Wortliste {
    private Wort[] alleWoerter;
    public static final Path woerterPath = Path.of(".import/input.json");

    private Wortliste(Wort[] woerter) {
        this.alleWoerter = woerter;
    }

    public Wort[] alleWoerter() {
        return alleWoerter;
    }

    /**
     * Loads the JSON file.
     */
    public static Wortliste loadWortliste() {
        // Fallback for testing if file doesn't exist
        if (!Files.exists(woerterPath)) {
            System.err.println("Wortliste Datei nicht gefunden: " + woerterPath.toAbsolutePath());
            return new Wortliste(new Wort[0]);
        }
        return loadWortliste(woerterPath);
    }

    public static Wortliste loadWortliste(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Wort>>(){}.getType();
            List<Wort> list = gson.fromJson(reader, listType);
            
            // Filter nulls if JSON parsing failed partially
            list.removeIf(Objects::isNull);
            
            return new Wortliste(list.toArray(new Wort[0]));
        } catch (IOException e) {
            e.printStackTrace();
            return new Wortliste(new Wort[0]);
        }
    }
    
    // Helper for the game to get random words
    public Wort getRandomWort() {
        if (alleWoerter == null || alleWoerter.length == 0) return null;
        return alleWoerter[new Random().nextInt(alleWoerter.length)];
    }
}