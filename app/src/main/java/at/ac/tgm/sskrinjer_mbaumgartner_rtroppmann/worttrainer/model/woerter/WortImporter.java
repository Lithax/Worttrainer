package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;

final class WortImporter {

    private WortImporter() {
        throw new IllegalStateException("Utility class");
    }

    // Hilfsklasse: Spiegelt GENAU die Struktur deines JSONs wider
    private static class RawWort {
        String wort;
        Wortart wortart;
        String artikel; // nur bei Nomen zwingend
        Map<String, String> singular;
        Map<String, String> plural;
        List<String> formen; // Für Verben & Adjektive
    }

    /**
     * Liest JSON ein, validiert die Einträge und gibt ein Ergebnis-Objekt zurück.
     * Fehlerhafte Einträge werden übersprungen und als Warnung gesammelt.
     */
    static ImportResult importJson(Reader reader) throws IOException {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<RawWort>>(){}.getType();

        List<RawWort> rawList;
        try {
            rawList = gson.fromJson(reader, listType);
        } catch (JsonSyntaxException e) {
            throw new IOException("JSON-Format ist korrupt und konnte nicht geparst werden.", e);
        }

        List<Wort> alleWoerterFlach = new ArrayList<>();
        Map<Wort, Wort> kindZuBasisMap = new HashMap<>();
        List<String> warnungen = new ArrayList<>();

        // Falls die Datei leer war
        if (rawList == null) {
            warnungen.add("Datei war leer oder enthielt kein gültiges JSON-Array.");
            return new ImportResult(Collections.emptyList(), Collections.emptyMap(), warnungen);
        }

        for (RawWort raw : rawList) {
            // --- VALIDIERUNG START ---
            try {
                validiereRawEintrag(raw);
            } catch (InvalidWortException e) {
                String name = (raw.wort != null) ? raw.wort : "Unbekanntes Wort";
                warnungen.add("Eintrag '" + name + "' übersprungen: " + e.getMessage());
                continue; // Springt zum nächsten Wort, dieses wird ignoriert
            }
            // --- VALIDIERUNG ENDE ---

            // Schritt 1: Variationen (Kinder) erstellen
            List<Wort> kinderListe = new ArrayList<>();

            // A) Nomen Variationen (Singular/Plural Maps)
            if (raw.wortart == Wortart.NOMEN) {
                kinderListe.addAll(erstelleNomenVariationen(raw.singular, raw.wortart, WortTag.SINGULAR));
                kinderListe.addAll(erstelleNomenVariationen(raw.plural, raw.wortart, WortTag.PLURAL));
            }
            // B) Verb/Adjektiv Variationen (Formen Liste)
            else if ((raw.wortart == Wortart.VERB || raw.wortart == Wortart.ADJEKTIV) && raw.formen != null) {
                kinderListe.addAll(erstelleSonstigeVariationen(raw.formen, raw.wortart));
            }

            // Schritt 2: Basis-Wort erstellen
            Set<WortTag> basisTags = new HashSet<>();
            basisTags.add(WortTag.BASIS); // Immer Basis markieren

            // Spezifische Basis-Tags je nach Wortart
            if (raw.wortart == Wortart.NOMEN) basisTags.add(WortTag.NOMINATIV);
            if (raw.wortart == Wortart.VERB) basisTags.add(WortTag.VERB_PRAESENS);
            if (raw.wortart == Wortart.ADJEKTIV) basisTags.add(WortTag.BASIS);

            Wort basis = new Wort(
                    raw.wort,
                    raw.wortart,
                    raw.artikel,
                    basisTags,
                    kinderListe // Liste wird im Record-Konstruktor kopiert (immutable)
            );

            // Schritt 3: Alles in die flache Gesamtliste und Lookup-Map packen
            alleWoerterFlach.add(basis);
            alleWoerterFlach.addAll(kinderListe);

            for (Wort kind : kinderListe) {
                kindZuBasisMap.put(kind, basis);
            }
        }

        // Fertiges Paket zurückgeben
        return new ImportResult(alleWoerterFlach, kindZuBasisMap, warnungen);
    }

    /**
     * Prüft, ob ein Raw-Eintrag logisch korrekt ist.
     * Wirft InvalidWortException, wenn Daten fehlen oder falsch sind.
     */
    private static void validiereRawEintrag(RawWort raw) throws InvalidWortException {
        if (raw == null) {
            throw new InvalidWortException("Eintrag ist null.");
        }
        if (raw.wort == null || raw.wort.trim().isEmpty()) {
            throw new InvalidWortException("Das Feld 'wort' ist leer.");
        }
        if (raw.wortart == null) {
            throw new InvalidWortException("Wortart fehlt oder ist ungültig.");
        }
        // Nomen müssen einen Artikel haben
        if (raw.wortart == Wortart.NOMEN) {
            if (raw.artikel == null || raw.artikel.trim().isEmpty()) {
                throw new InvalidWortException("Nomen benötigt zwingend einen Artikel.");
            }
        }
    }

    // --- Helper für Nomen (Map -> Wort Objekte) ---
    private static List<Wort> erstelleNomenVariationen(Map<String, String> map, Wortart art, WortTag numerus) {
        List<Wort> ergebnis = new ArrayList<>();
        if (map == null) return ergebnis;

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String rawKey = entry.getKey().toLowerCase();
            String rawValue = entry.getValue();

            // "des Hundes" -> ["des", "Hundes"]
            String[] parts = splitArtikelWort(rawValue);
            String artikel = parts[0];
            String wortText = parts[1];

            if (wortText == null || wortText.isEmpty()) continue;

            WortTag fall = parseFall(rawKey);
            Set<WortTag> tags = new HashSet<>();
            tags.add(numerus);
            if (fall != null) tags.add(fall);

            // Variation erstellen (hat selbst keine Kinder -> null oder leere Liste)
            ergebnis.add(new Wort(wortText, art, artikel, tags, null));
        }
        return ergebnis;
    }

    // --- Helper für Verben/Adjektive (List -> Wort Objekte) ---
    private static List<Wort> erstelleSonstigeVariationen(List<String> formenStrings, Wortart art) {
        List<Wort> ergebnis = new ArrayList<>();
        if (formenStrings == null) return ergebnis;

        for (int i = 0; i < formenStrings.size(); i++) {
            String rawValue = formenStrings.get(i);
            WortTag tag = null;

            // Mapping basierend auf Listen-Index
            if (art == Wortart.VERB) {
                if (i == 0) tag = WortTag.VERB_VERGANGENHEIT; // z.B. "ging"
                else if (i == 1) tag = WortTag.VERB_PARTIZIP; // z.B. "gegangen"
                else if (i == 2) tag = WortTag.VERB_PRAESENS; // z.B. "geht"
            }
            else if (art == Wortart.ADJEKTIV) {
                if (i == 0) tag = WortTag.KOMPARATIV; // z.B. "schöner"
                else if (i == 1) tag = WortTag.SUPERLATIV; // z.B. "am schönsten"
            }

            if (tag == null) continue;

            // "am schönsten" -> ["am", "schönsten"]
            String[] parts = splitArtikelWort(rawValue);
            String zusatz = parts[0];
            String wortText = parts[1];

            if (wortText == null || wortText.isEmpty()) continue;

            ergebnis.add(new Wort(wortText, art, zusatz, Set.of(tag), null));
        }
        return ergebnis;
    }

    // --- Parsing Utils ---

    private static WortTag parseFall(String key) {
        if (key.contains("1") || key.contains("nominativ")) return WortTag.NOMINATIV;
        if (key.contains("2") || key.contains("genitiv") || key.contains("genetiv")) return WortTag.GENITIV;
        if (key.contains("3") || key.contains("dativ")) return WortTag.DATIV;
        if (key.contains("4") || key.contains("akkusativ")) return WortTag.AKKUSATIV;
        return null;
    }

    private static String[] splitArtikelWort(String input) {
        if (input != null && input.contains(" ")) {
            String[] parts = input.split(" ", 2);
            // Plausibilitätscheck: Artikel/Partikel ist meist kurz (<= 4 Zeichen)
            if (parts[0].length() <= 4) {
                return parts;
            }
        }
        // Kein Artikel gefunden -> [null, "Wort"]
        return new String[]{null, input};
    }
}