package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.artikeljaeger;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.WortTag;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Statistik;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.Wort;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.Wortart;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.Wortliste;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.LinearSpielModel;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SpielListener;

/**
 * Artikeljäger
 *
 * Spielidee (gemäß Projekt-Unterlagen):
 * - Es erscheint ein Wort mit Lücke davor: "___ Hund"
 * - Der/die/das Buttons werden angeboten
 * - Nach Auswahl: kurzes Feedback, nächste Runde
 * - Nach letzter Runde: Statistikdialog (über MainController -> spielBeenden)
 */
public class ArtikeljaegerModel extends LinearSpielModel {

    public static final String[] ARTIKEL_OPTIONEN = new String[] { "der", "die", "das" };

    private final Random random = new Random();
    private List<Wort> kandidaten = new ArrayList<>();

    private Wort current;
    private String prompt;
    private Feedback lastFeedback;

    private int maxRounds; // 0 => unendlich

    public ArtikeljaegerModel() {
        // Wortliste wird in spielStarten() geladen, damit Imports/Files bereits existieren.
    }

    @Override
    public void spielStarten(SpielListener l) {
        super.spielStarten(l);
        query.mitWortart(Wortart.NOMEN).nomenImFall(WortTag.NOMINATIV);

        // Settings
        maxRounds = einstellungen != null ? einstellungen.getAnzahlRunden() : 10;

        // Load words
        loadNomenKandidaten();

        lastFeedback = new Feedback("Wähle den richtigen Artikel!", true);
        nextRound();
    }

    private void loadNomenKandidaten() {
        kandidaten.clear();
        // Prefer to use the same JSON source as Wortliste
        //if (!Files.exists(Wortliste.woerterPath)) {
        //    // Fallback: still allow game to run with empty list
        //    return;
        //}
        //try (BufferedReader reader = Files.newBufferedReader(Wortliste.woerterPath)) {
        //    Gson gson = new Gson();
        //    Type listType = new TypeToken<List<Wort>>() {}.getType();
        //    List<Wort> list = gson.fromJson(reader, listType);
        //    if (list == null) return;
//
        //    for (Wort w : list) {
        //        if (w == null) continue;
        //        if (w.wortart() != Wortart.NOMEN) continue;
        //        String art = normalizeArtikel(w.artikel());
        //        if (art == null) continue;
        //        if (isSupportedArtikel(art)) kandidaten.add(w);
        //    }
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}
    }

    private boolean isSupportedArtikel(String artikel) {
        for (String a : ARTIKEL_OPTIONEN) {
            if (a.equals(artikel)) return true;
        }
        return false;
    }

    private static String normalizeArtikel(String artikel) {
        if (artikel == null) return null;
        String a = artikel.trim().toLowerCase(Locale.ROOT);
        return a.isBlank() ? null : a;
    }

    public void nextRound() {
        kandidaten = query.randomBalancedArray();

        if (kandidaten.isEmpty()) {
            current = null;
            prompt = "Keine Nomen mit Artikel in der Wortliste gefunden.";
            return;
        }

        // Avoid repeating the same noun twice in a row
        Wort next = kandidaten.get(random.nextInt(kandidaten.size()));
        if (current != null && kandidaten.size() > 1) {
            int guard = 0;
            while (Objects.equals(next.wort(), current.wort()) && guard++ < 10) {
                next = kandidaten.get(random.nextInt(kandidaten.size()));
            }
        }

        current = next;
        prompt = "___ " + current.wort();
    }

    /**
     * @return true if answer was correct
     */
    public boolean submitArtikel(String chosenArtikel) {
        if (current == null) return false;

        String chosen = normalizeArtikel(chosenArtikel);
        String correct = normalizeArtikel(current.artikel());

        gesamtAnzahl++;
        boolean ok = chosen != null && chosen.equals(correct);

        if (ok) {
            streak++;
            if (streak > highestStreak) highestStreak = streak;
            lastFeedback = new Feedback("Richtig!", true);
        } else {
            streak = 0;
            fehlerAnzahl++;
            String corrText = (correct != null ? correct : "?") + " " + current.wort();
            lastFeedback = new Feedback("Falsch – richtig wäre: " + corrText, false);
        }

        return ok;
    }

    public boolean isFinished() {
        return maxRounds > 0 && gesamtAnzahl >= maxRounds;
    }

    public String getPrompt() {
        return prompt;
    }

    public int getRound() {
        return gesamtAnzahl;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public Feedback getFeedback() {
        return lastFeedback;
    }

    @Override
    public String getSpielDescription() {
        return "Teste dein Wissen über die richtigen Artikel! In Artikeljäger bekommst du ein Wort angezeigt und musst den passenden Artikel auswählen – der, die oder das.";
    }

    @Override
    public String getSpielName() {
        return "Artikeljaeger";
    }

    @Override
    public Statistik getStatistik() {
        Statistik s = super.getStatistik();
        int richtig = Math.max(0, gesamtAnzahl - fehlerAnzahl);
        s.getStatFields().put("Richtig", String.valueOf(richtig));
        s.getStatFields().put("Falsch", String.valueOf(fehlerAnzahl));
        if (gesamtAnzahl > 0) {
            double pct = ((double) richtig / (double) gesamtAnzahl) * 100.0;
            s.getStatFields().put("Trefferquote", String.format(Locale.ROOT, "%.1f", pct) + " %");
        }
        return s;
    }

    public static record Feedback(String message, boolean correct) {}
}
