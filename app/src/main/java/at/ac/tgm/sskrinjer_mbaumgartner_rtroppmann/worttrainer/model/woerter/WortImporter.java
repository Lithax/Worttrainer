package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;

final class WortImporter {

    private WortImporter() {
        throw new IllegalStateException("Utility class");
    }

    // Hilfsklasse: Spiegelt GENAU dein JSON wider
    private static class RawWort {
        String wort;
        Wortart wortart;
        String artikel; // Basisartikel
        Map<String, String> singular;
        Map<String, String> plural;
        List<String> formen; // Für Verben & Adjektive
    }



    static Wortliste importJson(Reader reader) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<RawWort>>(){}.getType();
        List<RawWort> rawList = gson.fromJson(reader, listType);

        // Liste für das Array am Ende (enthält ALLE Wörter)
        List<Wort> alleWoerterFlach = new ArrayList<>();

        // Map für den Rückweg (Kind -> Basis), falls du das später brauchst
        Map<Wort, Wort> kindZuBasisMap = new HashMap<>();

        for (RawWort raw : rawList) {
            if (raw.wort == null) continue;

            // Schritt 1: Variationen (Kinder) vorbereiten
            List<Wort> kinderListe = new ArrayList<>();

            // Nomen Variationen sammeln
            if (raw.wortart == Wortart.NOMEN) {
                // Hier rufen wir eine Hilfsmethode auf, die Wort-Objekte zurückgibt
                kinderListe.addAll(erstelleNomenVariationen(raw.singular, raw.wortart, WortTag.SINGULAR));
                kinderListe.addAll(erstelleNomenVariationen(raw.plural, raw.wortart, WortTag.PLURAL));
            }
            // Verb/Adjektiv Variationen sammeln (Code analog zu vorher, nur Rückgabe ist Liste)
            else if ( (raw.wortart == Wortart.VERB || raw.wortart == Wortart.ADJEKTIV) && raw.formen != null) {
                kinderListe.addAll(erstelleSonstigeVariationen(raw.formen, raw.wortart));
            }



            // Schritt 2: Basis-Wort erstellen (kennt jetzt seine Kinder!)
            Set<WortTag> basisTags = new HashSet<>(Set.of(WortTag.BASIS));
            if (raw.wortart == Wortart.NOMEN) basisTags.add(WortTag.NOMINATIV);
            if (raw.wortart == Wortart.VERB) basisTags.add(WortTag.VERB_GEGENWART);
            if (raw.wortart == Wortart.ADJEKTIV) basisTags.add(WortTag.POSITIV);

            Wort basis = new Wort(
                    raw.wort,
                    raw.wortart,
                    raw.artikel,
                    basisTags,
                    kinderListe
            );

            // Schritt 3: Alles in die flache Gesamtliste packen
            alleWoerterFlach.add(basis);
            alleWoerterFlach.addAll(kinderListe);

            // Optional: Map füllen, damit wir später wissen, wer zu wem gehört
            for (Wort kind : kinderListe) {
                kindZuBasisMap.put(kind, basis);
            }
        }

        // Wir geben die Liste UND die Map an die Wortliste weiter
        return new Wortliste(alleWoerterFlach.toArray(new Wort[0]), 3, 15, kindZuBasisMap);
    }

    // Diese Methode wandelt die Map (z.B. Singular-Fälle) in eine Liste von Wort-Objekten um
    private static List<Wort> erstelleNomenVariationen(Map<String, String> map, Wortart art, WortTag numerus) {
        List<Wort> ergebnis = new ArrayList<>();

        // Sicherheitscheck: Falls das JSON feld leer war
        if (map == null || art == null || numerus == null) return ergebnis;

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String rawKey = entry.getKey().toLowerCase(); // z.B. "genitiv"
            String rawValue = entry.getValue();           // z.B. "des Hundes"

            // 1. Welcher Fall ist das? (Hilfsmethode unten)
            WortTag fall = parseFall(rawKey);

            // 2. Artikel vom Wort trennen
            String[] parts = splitArtikelWort(rawValue);
            String artikel = parts[0]; // "des"
            String wortText = parts[1]; // "Hundes"

            // 3. Tags zusammenstellen
            Set<WortTag> tags = new HashSet<>();
            tags.add(numerus); // z.B. SINGULAR oder PLURAL
            if (fall != null) {
                tags.add(fall);
            }

            // 4. Das Wort-Objekt erstellen
            // WICHTIG: Die Liste der Variationen ist hier leer
            // da eine abgeleitete Form (Kind) meist keine eigenen Unterformen mehr hat.
            Wort variation = new Wort(
                    wortText,
                    art,
                    artikel,
                    tags,
                    null
            );

            ergebnis.add(variation);
        }

        return ergebnis;
    }

    /**
     * Verarbeitet Listen von Formen (z.B. bei Verben und Adjektiven).
     * Die Bedeutung der Form wird anhand ihrer Position (Index) in der Liste bestimmt.
     */
    private static List<Wort> erstelleSonstigeVariationen(List<String> formenStrings, Wortart art) {
        List<Wort> ergebnis = new ArrayList<>();

        if (formenStrings == null || formenStrings.isEmpty()) {
            return ergebnis;
        }

        for (int i = 0; i < formenStrings.size(); i++) {
            String rawValue = formenStrings.get(i);
            WortTag tag = null;

            // 1. Tag anhand der Wortart und Position bestimmen
            if (art == Wortart.VERB) {
                // Basierend auf deinem JSON: [0]=Präteritum, [1]=Partizip II, [2]=Präsens
                if (i == 0) tag = WortTag.VERB_VERGANGENHEIT; // "ging"
                else if (i == 1) tag = WortTag.VERB_PARTIZIP; // "gegangen"
                else if (i == 2) tag = WortTag.VERB_PRAESENS; // "geht"
            }
            else if (art == Wortart.ADJEKTIV) {
                // Basierend auf deinem JSON: [0]=Komparativ, [1]=Superlativ
                if (i == 0) tag = WortTag.KOMPARATIV; // "schöner"
                else if (i == 1) tag = WortTag.SUPERLATIV; // "am schönsten"
            }

            // Wenn wir keinen passenden Tag gefunden haben (z.B. Liste zu lang), überspringen
            if (tag == null) continue;

            // 2. Artikel/Partikel abtrennen (wichtig für "am schönsten")
            // Nutzt dieselbe Hilfsmethode wie bei den Nomen
            String[] parts = splitArtikelWort(rawValue);
            String zusatz = parts[0]; // z.B. "am" oder null
            String wortText = parts[1]; // z.B. "schönsten"

            // 3. Tags erstellen
            Set<WortTag> tags = new HashSet<>();
            tags.add(tag);

            // 4. Wort erstellen
            Wort form = new Wort(
                    wortText,
                    art,
                    zusatz, // Hier landet das "am"
                    tags,
                    null // Hat keine eigenen Kinder
            );

            ergebnis.add(form);
        }

        return ergebnis;
    }

// --- Hilfsmethoden ---

    // Erkennt den Fall anhand des Schlüssels im JSON
    private static WortTag parseFall(String key) {
        if (key.contains("1") || key.contains("nominativ")) return WortTag.NOMINATIV;
        if (key.contains("2") || key.contains("genitiv") || key.contains("genetiv")) return WortTag.GENITIV;
        if (key.contains("3") || key.contains("dativ")) return WortTag.DATIV;
        if (key.contains("4") || key.contains("akkusativ")) return WortTag.AKKUSATIV;
        return null; // Kein spezifischer Fall erkannt
    }

    // Trennt "des Hundes" in ["des", "Hundes"]
    private static String[] splitArtikelWort(String input) {
        if (input != null && input.contains(" ")) {
            String[] parts = input.split(" ", 2);
            // Primitiver Check: Ist der erste Teil kurz genug, um ein Artikel zu sein?
            if (parts[0].length() <= 4) {
                return parts;
            }
        }
        // Falls kein Artikel gefunden wurde: [null, "Hund"]
        return new String[]{null, input};
    }
}