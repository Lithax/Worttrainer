package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fehlerfuchs;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Statistik;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.LinearSpielModel;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SpielListener;

public class FehlerfuchsModel extends LinearSpielModel {

	private static final List<String> WOERTER = List.of(
			"Schule", "Freundschaft", "Computer", "Wissenschaft", "Abenteuer"
	);

	private final Random random = new Random();

	private String korrektesWort;
	private String fehlerhaftesWort;
	private Feedback lastFeedback;

	private int maxRounds;

	@Override
	public void spielStarten(SpielListener l) {
		super.spielStarten(l);

		fehlerAnzahl = 0;
		gesamtAnzahl = 0;
		streak = 0;
		highestStreak = 0;

		maxRounds = einstellungen != null ? einstellungen.getAnzahlRunden() : 10;

		lastFeedback = new Feedback("Korrigiere das Wort!", true);
		nextRound();
	}

	public void nextRound() {
		korrektesWort = WOERTER.get(random.nextInt(WOERTER.size()));
		fehlerhaftesWort = baueFehlerEin(korrektesWort);
	}

	private String baueFehlerEin(String wort) {
		if (wort.length() < 2) return wort;

		int index = random.nextInt(wort.length() - 1);
		char[] chars = wort.toCharArray();

		// Buchstaben vertauschen
		char tmp = chars[index];
		chars[index] = chars[index + 1];
		chars[index + 1] = tmp;

		return new String(chars);
	}

	public void submitAnswer(String input) {
		gesamtAnzahl++;

		if (input != null && input.equalsIgnoreCase(korrektesWort)) {
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
		return "Fehlerfuchs";
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

	public static record Feedback(String message, boolean correct) {}
}
