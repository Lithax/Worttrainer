package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fehlerfuchs;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Statistik;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.Wort;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.WortTag;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.WortVerwaltung;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.Wortliste;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.LinearSpielModel;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SpielListener;

public class FehlerfuchsModel extends LinearSpielModel {

    private WortVerwaltung wv;
    private Wort korrektesWort;
    private String fehlerhaftesWort;
    private Feedback lastFeedback;
    private Random random = new Random();

    private int maxRounds;

    @Override
    public void spielStarten(SpielListener l) {
        super.spielStarten(l);
        wv = new WortVerwaltung(Wortliste.getInstance().query().nomenImFall(WortTag.NOMINATIV).nomenImSingular().mitLaenge(6, 99).mitKomplexitaet(4.5 + einstellungen.numerifySchwierigkeit() * 0.6, 10));

        maxRounds = einstellungen != null ? einstellungen.getAnzahlRunden() : 10;

        lastFeedback = new Feedback("Korrigiere das Wort!", true);
        nextRound();
    }

    public void nextRound() {
        korrektesWort = wv.nextWort();
        fehlerhaftesWort = baueFehlerEin(korrektesWort, einstellungen.numerifySchwierigkeit(), random);
    }

    public void submitAnswer(String input) {
        gesamtAnzahl++;

        if (input != null && input.equalsIgnoreCase(korrektesWort.displayWord())) {
            streak++;
            highestStreak = Math.max(highestStreak, streak);
            lastFeedback = new Feedback("Richtig!", true);
        } else {
            streak = 0;
            fehlerAnzahl++;
            lastFeedback = new Feedback(
                    "Falsch – richtig wäre: " + korrektesWort.displayWord(), false);
        }
    }

    public boolean isFinished() {
        return maxRounds > 0 && gesamtAnzahl >= maxRounds;
    }

    public String getFehlerhaftesWort() {
        return fehlerhaftesWort;
    }

    public Feedback getFeedback() {
        return lastFeedback;
    }

    @Override
    public String getSpielDescription() {
        return "Teste dein Rechtschreibwissen! In Fehlerfuchs wird dir ein absichtlich falsch geschriebenes Wort angezeigt, und deine Aufgabe ist es, den Fehler zu erkennen und das Wort korrekt einzugeben. Je genauer du die Wörter verbesserst, desto besser wird deine Trefferquote. Das Spiel hilft dir, typische Rechtschreibfehler zu erkennen und deine Schreibsicherheit zu trainieren.";
    }

    @Override
    public String getSpielName() {
        return "Fehlerfuchs";
    }

    @Override
    public Statistik getStatistik() {
        Statistik s = super.getStatistik();
        s.getStatFields().put("Runden", String.valueOf(gesamtAnzahl));
        s.getStatFields().put("Fehler", String.valueOf(fehlerAnzahl));
        return s;
    }

    public record Feedback(String message, boolean correct) {
    }

    public static String baueFehlerEin(Wort wort, int schwierigkeit, Random random) {
        String original = wort.wort();
        String aktuell = original;

        int zielFehler = bestimmeFehlerAnzahl(original.length(), schwierigkeit);

        Set<String> verwendeteKategorien = new HashSet<>();

        for (int i = 0; i < zielFehler; i++) {
            Fehler fehler = findeGeeignetenFehler(
                    aktuell,
                    schwierigkeit,
                    verwendeteKategorien,
                    random
            );

            if (fehler == null) break;

            String neu = fehler.apply(aktuell, random);

            if (istKaputt(original, neu)) continue;

            aktuell = neu;
            verwendeteKategorien.add(fehler.kategorie);
        }

        return aktuell;
    }



    private static int bestimmeFehlerAnzahl(int laenge, int schwierigkeit) {
        int basis = switch (schwierigkeit) {
            case 1 -> 1;
            case 2 -> 2;
            case 3 -> 3;
            default -> 1;
        };

        if (laenge >= 8 && schwierigkeit >= 2) basis++;
        if (laenge >= 12 && schwierigkeit == 3) basis++;

        return Math.min(basis, 3);
    }


    private static Fehler findeGeeignetenFehler(
            String wort,
            int schwierigkeit,
            Set<String> verwendeteKategorien,
            Random random
    ) {
        List<Fehler> kandidaten = new ArrayList<>();

        for (Fehler f : FEHLER_LISTE) {
            if (!f.isApplicable(wort)) continue;
            if (verwendeteKategorien.contains(f.kategorie)) continue;
            if (f.prioritaet > schwierigkeit + 1) continue;
            kandidaten.add(f);
        }

        if (kandidaten.isEmpty()) return null;

        kandidaten.sort(Comparator.comparingInt(f -> f.prioritaet));
        return kandidaten.get(random.nextInt(Math.min(3, kandidaten.size())));
    }


    private static boolean istFehlerErlaubt(Fehler f, Wort wort, int schwierigkeit) {
        if (schwierigkeit == 1) {
            return f.prioritaet <= 2;
        }
        if (schwierigkeit == 2) {
            return f.prioritaet <= 3;
        }
        return true;
    }

    private static boolean istKaputt(String original, String neu) {
        if (Math.abs(neu.length() - original.length()) > 2) return true;

        if (neu.length() >= 2 && neu.charAt(0) == neu.charAt(1)) return true;

        for (String kern : List.of("sch", "ch", "ie")) {
            if (original.contains(kern) && !neu.contains(kern)) return true;
        }

        if (neu.contains("ßch") || neu.contains("tß")) return true;

        if (editDistance(original, neu) > original.length() / 3) return true;

        return false;
    }


    private static int editDistance(String a, String b) {
        int[][] dp = new int[a.length()+1][b.length()+1];
        for (int i = 0; i <= a.length(); i++) dp[i][0] = i;
        for (int j = 0; j <= b.length(); j++) dp[0][j] = j;

        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                int cost = a.charAt(i-1) == b.charAt(j-1) ? 0 : 1;
                dp[i][j] = Math.min(
                        Math.min(dp[i-1][j] + 1, dp[i][j-1] + 1),
                        dp[i-1][j-1] + cost
                );
            }
        }
        return dp[a.length()][b.length()];
    }


//    private static Fehler findeFehler(Wort wort, Random random) {
//        int randomNr = random.nextInt(FEHLER_LISTE.length);
//        for(int i =randomNr; i<FEHLER_LISTE.length + randomNr; i++) {
//            int index = i < FEHLER_LISTE.length ? i : i - FEHLER_LISTE.length;
//            if(FEHLER_LISTE[index].isApplicable(wort)) return FEHLER_LISTE[index];
//        }
//        return Fehler.BUCHSTABENTAUSCH;
//    }

    private static Fehler[] FEHLER_LISTE = Fehler.getFehlerliste();

    static class Fehler {
        final String name;
        final String kategorie;
        final int prioritaet;
        final Predicate<String> isApplicable;
        final BiFunction<String, Random, String> apply;

        Fehler(String name, String kategorie, int prioritaet,
               Predicate<String> isApplicable,
               BiFunction<String, Random, String> apply) {
            this.name = name;
            this.kategorie = kategorie;
            this.prioritaet = prioritaet;
            this.isApplicable = isApplicable;
            this.apply = apply;
        }

        public boolean isApplicable(String wort) {
            return isApplicable.test(wort);
        }

        public String apply(String wort, Random random) {
            return apply.apply(wort, random);
        }

        @Override
        public String toString() {
            return "Fehler: " + name;
        }


        public static Fehler[] getFehlerliste() {
            List<Fehler> fehler = new ArrayList<>();

            // ===== s / ss / ß =====
            fehler.add(new Fehler("ss→ß", "LAUT", 2,
                    w -> w.contains("ss"),
                    (w, r) -> w.replaceFirst("ss", "ß")));
            fehler.add(new Fehler("ß→ss", "LAUT", 2,
                    w -> w.contains("ß"),
                    (w, r) -> w.replaceFirst("ß", "ss")));
            fehler.add(new Fehler("s→ß", "LAUT", 3,
                    w -> w.contains("s"),
                    (w, r) -> w.replaceFirst("s", "ß")));
            fehler.add(new Fehler("s Doppel", "DOPPEL", 3,
                    w -> w.contains("s"),
                    (w, r) -> w.replaceFirst("s", "ss")));
            fehler.add(new Fehler("ss Vereinfachen", "DOPPEL", 2,
                    w -> w.contains("ss"),
                    (w, r) -> w.replaceFirst("ss", "s")));

            // ===== Doppelkonsonanten allgemein =====
            for (String c : List.of("m", "n", "l", "t")) {
                fehler.add(new Fehler(c + " verdoppeln", "DOPPEL", 2,
                        w -> w.contains(c),
                        (w, r) -> w.replaceFirst(c, c + c)));
                fehler.add(new Fehler(c + " vereinfachen", "DOPPEL", 2,
                        w -> w.contains(c + c),
                        (w, r) -> w.replaceFirst(c + c, c)));
            }

            // ===== Dehnungs-h =====
            fehler.add(new Fehler("h entfernen", "DEHNUNG", 2,
                    w -> w.length() > 2 && w.substring(1, w.length() - 1).contains("h"),
                    (w, r) -> w.replaceFirst("h", "")));
            fehler.add(new Fehler("h einfügen nach a/e", "DEHNUNG", 2,
                    w -> w.contains("a") || w.contains("e"),
                    (w, r) -> {
                        int i = w.indexOf("a");
                        if (i < 0) i = w.indexOf("e");
                        if (i < 0) return w;
                        return w.substring(0, i + 1) + "h" + w.substring(i + 1);
                    }));

            // ===== ie / i =====
            fehler.add(new Fehler("ie→i", "LAUT", 1,
                    w -> w.contains("ie"),
                    (w, r) -> w.replaceFirst("ie", "i")));
            fehler.add(new Fehler("i→ie", "LAUT", 2,
                    w -> w.contains("i"),
                    (w, r) -> w.replaceFirst("i", "ie")));

            // ===== ä / e =====
            fehler.add(new Fehler("ä→e", "LAUT", 2,
                    w -> w.contains("ä"),
                    (w, r) -> w.replaceFirst("ä", "e")));
            fehler.add(new Fehler("e→ä", "LAUT", 3,
                    w -> w.contains("e"),
                    (w, r) -> w.replaceFirst("e", "ä")));

            // ===== äu / eu =====
            fehler.add(new Fehler("äu→eu", "LAUT", 2,
                    w -> w.contains("äu"),
                    (w, r) -> w.replaceFirst("äu", "eu")));
            fehler.add(new Fehler("eu→äu", "LAUT", 2,
                    w -> w.contains("eu"),
                    (w, r) -> w.replaceFirst("eu", "äu")));

            // ===== ck / k =====
            fehler.add(new Fehler("ck→k", "LAUT", 2,
                    w -> w.contains("ck"),
                    (w, r) -> w.replaceFirst("ck", "k")));
            fehler.add(new Fehler("k→ck", "LAUT", 2,
                    w -> w.contains("k"),
                    (w, r) -> w.replaceFirst("k", "ck")));

            fehler.add(new Fehler("i→y", "LAUT", 2,
                    w -> w.contains("i"),
                    (w, r) -> w.replaceFirst("y", "i")));

            // ===== Endungen =====
            fehler.add(new Fehler("ung→unk", "ENDUNG", 2,
                    w -> w.endsWith("ung"),
                    (w, r) -> w.substring(0, w.length() - 3) + "unk"));
            fehler.add(new Fehler("ig→ich", "ENDUNG", 2,
                    w -> w.endsWith("ig"),
                    (w, r) -> w.substring(0, w.length() - 2) + "ich"));
            fehler.add(new Fehler("lich→lig", "ENDUNG", 2,
                    w -> w.endsWith("lich"),
                    (w, r) -> w.substring(0, w.length() - 4) + "lig"));

            // ===== Buchstabentausch zufällig =====
            fehler.add(new Fehler("zufälliger Buchstabentausch", "ZUFALL", 3,
                    w -> w.length() >= 3,
                    (w, r) -> {
                        int i = r.nextInt(w.length() - 1);
                        char[] chars = w.toCharArray();
                        char tmp = chars[i];
                        chars[i] = chars[i + 1];
                        chars[i + 1] = tmp;
                        return new String(chars);
                    }));

            return fehler.toArray(new Fehler[0]);
        }

    }


}
