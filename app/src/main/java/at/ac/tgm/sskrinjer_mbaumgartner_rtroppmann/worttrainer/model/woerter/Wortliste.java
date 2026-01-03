package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.Wort;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.WortImporter; // Muss angepasst werden, s.u.
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Wortliste {

    // 1. Die einzige Instanz (Singleton)
    private static Wortliste instance;

    // 2. Mutable Speicherstrukturen (Set verhindert Duplikate beim Nachladen)
    private final Set<Wort> woerter;
    private final Map<Wort, Wort> elternLookup;
    private final List<String> importWarnungen;

    // 3. Privater Konstruktor
    private Wortliste() {
        this.woerter = new HashSet<>();
        this.elternLookup = new HashMap<>();
        this.importWarnungen = new ArrayList<>();
    }

    // 4. Globaler Zugriffspunkt
    public static synchronized Wortliste getInstance() {
        if (instance == null) {
            instance = new Wortliste();
        }
        return instance;
    }

    // --- LADE-LOGIK ---

    /**
     * Lädt Wörter aus einer oder mehreren JSON-Dateien und fügt sie der aktuellen Liste hinzu.
     * @param pfade Ein Array von Dateipfaden oder eine einzelne Datei.
     */
    public void ladeAusDateien(String... pfade) throws IOException {
        if (pfade == null) return;

        for (String pfad : pfade) {
            // Den (unsichtbaren) Importer nutzen
            ImportResult result = WortImporter.importJson(Files.newBufferedReader(Path.of(pfad)));

            // Daten mergen (Hinzufügen zur bestehenden Liste)
            this.woerter.addAll(result.woerter());
            this.elternLookup.putAll(result.elternLookup());
            this.importWarnungen.addAll(result.warnungen());
        }
    }

    /**
     * Setzt die Liste komplett zurück (löscht alles).
     */
    public void leeren() {
        this.woerter.clear();
        this.elternLookup.clear();
        this.importWarnungen.clear();
    }

    // --- ZUGRIFFSMETHODEN ---

    public Wort[] getAlleWoerter() {
        // Konvertiert das Set zurück in ein Array für die Queries
        return woerter.toArray(new Wort[0]);
    }

    public WortQuery query() {
        return new WortQuery(this);
    }

    public Wort getBasisWort(Wort w) {
        // ... Logik wie vorher ...
        return elternLookup.getOrDefault(w, w); // Vereinfacht
    }

    public List<String> getWarnungen() {
        return Collections.unmodifiableList(importWarnungen);
    }
}