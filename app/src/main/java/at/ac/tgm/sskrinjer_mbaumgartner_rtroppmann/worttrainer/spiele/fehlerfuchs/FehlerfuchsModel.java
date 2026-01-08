package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fehlerfuchs;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Statistik;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.Wort;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.WortVerwaltung;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.Wortliste;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.LinearSpielModel;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SpielListener;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.util.Pair;

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
        wv = new WortVerwaltung(Wortliste.getInstance().query().mitLaenge(6, 99).mitKomplexitaet(50, 99));

        fehlerAnzahl = 0;
        gesamtAnzahl = 0;
        streak = 0;
        highestStreak = 0;

        maxRounds = einstellungen != null ? einstellungen.getAnzahlRunden() : 10;

        lastFeedback = new Feedback("Korrigiere das Wort!", true);
        nextRound();
    }

    public void nextRound() {
        korrektesWort = wv.nextWort();
        fehlerhaftesWort = baueFehlerEin(korrektesWort, random);
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
                    "Falsch – richtig wäre: " + korrektesWort, false);
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

    public static record Feedback(String message, boolean correct) {
    }

    private static String baueFehlerEin(Wort wort, Random random) {
        ArrayList<Fehler> fehler = new ArrayList<>();
        return null; //#toDo
    }

    private static Fehler[] FEHLER_LISTE = Fehler.getFehlerliste();

    static class Fehler {
        Function<Wort, String> fehlerEinbauen;
        Predicate<Wort> isEinbaubar;
        String name;

        private Fehler(String name, Predicate<Wort> isEinbaubar, Function<Wort, String> fehlerEinbauen) {
            this.fehlerEinbauen = fehlerEinbauen;
            this.isEinbaubar = isEinbaubar;
            this.name = name;
        }

        public boolean istEinbaubar(Wort wort) {
            return isEinbaubar.test(wort);
        }

        public String fehlerEinbauen(Wort wort) {
            return fehlerEinbauen.apply(wort);
        }

        @Override
        public String toString() {
            return "Fehler: " + name;
        }


        public static Fehler[] getFehlerliste() {
            ArrayList<Fehler> fehler = new ArrayList<>();
            final Pair<String, String>[] aehnlicheBuchstaben = new Pair[]{new Pair("g", "k"), new Pair("d", "t"), new Pair("zz", "tz"), new Pair("f", "v")};

			return null; //#todo
        }

        private static final Random RANDOM = new Random();



        private static Predicate<Wort> contains( String part) {
            return wort -> wort.wort().toLowerCase().contains(part);
        }

        private static Predicate<Wort> containsInMiddle(String part) {
            return wort -> wort.wort().substring(1, wort.laenge() - 1).toLowerCase().contains(part);
        }

        private static Predicate<Wort> containsNotBegins(String part) {
            return wort -> wort.wort().substring(1).toLowerCase().contains(part);
        }

        private static Predicate<Wort> containsDoubleLetter(String letter) {
            return containsNotBegins(letter + letter);
        }

        private static Predicate<Wort> containsAfter(String first, String second) {
            return wort -> {
                String str = wort.wort();
                if (!str.contains(first) || !str.contains(second)) return false;
                return str.indexOf(first) < str.lastIndexOf(second);
            };
        }


        /**
         * Ersetzt **ein zufälliges Vorkommen** von target durch replacement im gegebenen String.
         * @param target      das zu ersetzende Teilstück
         * @param replacement der Ersatz
         * @return neuer String mit genau einer zufälligen Ersetzung
         */
        public static Function<Wort, String> replace(String target, String replacement) {
            return wort -> {
                String input = wort.wort();
                if (input == null || target == null || target.isEmpty()) return input;
                // Alle Start-Indizes der Vorkommen sammeln
                int index = 0;
                java.util.List<Integer> indices = new java.util.ArrayList<>();
                while ((index = input.indexOf(target, index)) >= 0) {
                    indices.add(index);
                    index += target.length();
                }
                if (indices.isEmpty()) return input; // nichts zu ersetzen
                // Zufälliges Vorkommen auswählen
                int randomIndex = indices.get(RANDOM.nextInt(indices.size()));
                // String umbauen
                StringBuilder sb = new StringBuilder();
                sb.append(input, 0, randomIndex);
                sb.append(replacement);
                sb.append(input, randomIndex + target.length(), input.length());
                return sb.toString();
            };
        }
    }


}
