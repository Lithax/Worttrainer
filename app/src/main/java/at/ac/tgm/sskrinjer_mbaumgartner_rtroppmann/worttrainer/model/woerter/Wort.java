package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

import java.util.List;
import java.util.Set;

/**
 * Repräsentiert ein Wort oder eine Wortform.
 *
 * @param wort          Der Text des Wortes (z.B. "Hunde").
 * @param wortart       Die Wortart (Nomen, Verb, etc.).
 * @param artikel       Der Artikel (nur bei Nomen relevant, sonst null).
 * @param tags          Liste von Eigenschaften (SINGULAR, PLURAL, GENITIV, BASIS, etc.).
 * @param variationen   Liste der abgeleiteten Formen.
 */
public record Wort(
        String wort,
        Wortart wortart,
        String artikel,
        //int laenge
        //double komplexitat -> 0-10
        Set<WortTag> tags,
        List<Wort> variationen
) {

    /**
     * Kompakter Konstruktor für Validierung und Erstellung unveränderlicher Kopien.
     */
    public Wort {
        // Set.copyOf erstellt eine unmodifizierbare Kopie.
        // Wenn 'tags' null ist, nehmen wir ein leeres Set.
        tags = (tags == null) ? Set.of() : Set.copyOf(tags);

        // List.copyOf erstellt eine unmodifizierbare Kopie.
        // Das bedeutet: Nach der Erstellung des Wortes kann niemand mehr .add() oder .remove() aufrufen.
        variationen = (variationen == null) ? List.of() : List.copyOf(variationen);
    }

    /**
     * Komplexität von 0-10;
     * @return
     */
    public double komplexitaet() {
        return WortKomplexitaet.berechneKomplexitaet(this);
    }

    public int laenge() {
        return wort.length();
    }

    public int getFall() throws UnavailableWortOperationException {
        if(tags().contains(WortTag.NOMINATIV)) {
            return 1;
        } else if(tags().contains(WortTag.GENITIV)) {
            return 2;
        } else if(tags().contains(WortTag.DATIV)) {
            return 3;
        } else if(tags().contains(WortTag.AKKUSATIV)) {
            return 4;
        }
        throw new UnavailableWortOperationException("Kein Fall für:" + wort);
    }

    public String displayWord() {
        return switch (wortart) {
            case NOMEN -> (wort.substring(0, 1).toUpperCase() + wort.substring(1).toLowerCase());
            default -> wort.toLowerCase();
        };
    }

    private class WortKomplexitaet {
        private WortKomplexitaet() {}

        public static double berechneKomplexitaet(Wort wort) {
            String w = wort.wort().toLowerCase();
            double k = 1.0;

            // 1. Wortlänge (logarithmisch, damit lange Wörter nicht sofort explodieren)
            k += Math.log(w.length() + 1) * 1.1;

            // 2. Mehrbuchstaben-Laute
            k += count(w, "sch") * 0.8;
            k += count(w, "ch") * 0.5;
            k += count(w, "ie") * 0.6;
            k += count(w, "ck") * 0.6;
            k += count(w, "tz") * 0.6;

            // 3. Umlaute & ß
            k += count(w, "ä") * 0.4;
            k += count(w, "ö") * 0.4;
            k += count(w, "ü") * 0.4;
            k += count(w, "ß") * 0.7;

            // 4. Doppelkonsonanten
            k += countDoubleConsonants(w) * 0.6;

            // 5. Dehnungs-h
            k += countDehnungsH(w) * 0.7;

            // 6. Konsonantencluster (3 oder mehr)
            k += countConsonantClusters(w) * 0.6;

            // 7. Typische schwierige Endungen
            if (w.endsWith("ung"))  k += 0.8;
            if (w.endsWith("lich")) k += 0.8;
            if (w.endsWith("keit") || w.endsWith("heit")) k += 0.9;

            return Math.min(k, 9.999);
        }

        // ===== Helper =====

        private static int count(String w, String part) {
            int c = 0, i = 0;
            while ((i = w.indexOf(part, i)) >= 0) {
                c++;
                i += part.length();
            }
            return c;
        }

        private static int countDoubleConsonants(String w) {
            int c = 0;
            for (int i = 0; i < w.length() - 1; i++) {
                char a = w.charAt(i);
                char b = w.charAt(i + 1);
                if (a == b && isConsonant(a)) c++;
            }
            return c;
        }

        private static int countDehnungsH(String w) {
            int c = 0;
            for (int i = 1; i < w.length() - 1; i++) {
                if (w.charAt(i) == 'h' && isVowel(w.charAt(i - 1))) {
                    c++;
                }
            }
            return c;
        }

        private static int countConsonantClusters(String w) {
            int max = 0, cur = 0;
            for (char ch : w.toCharArray()) {
                if (isConsonant(ch)) {
                    cur++;
                    max = Math.max(max, cur);
                } else {
                    cur = 0;
                }
            }
            // erst ab 3 Konsonanten relevant
            return Math.max(0, max - 2);
        }

        private static boolean isVowel(char c) {
            return "aeiouäöü".indexOf(c) >= 0;
        }

        private static boolean isConsonant(char c) {
            return Character.isLetter(c) && !isVowel(c);
        }
    }

}