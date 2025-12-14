package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Statistik;

/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 15:09:38
 */
public abstract class LinearSpielModel extends SpielModel {

	protected int fehlerAnzahl = 0;
	protected int gesamtAnzahl = 0;
	protected int highestStreak = 0;
	protected int streak = 0;

	@Override
	public Statistik getStatistik() 
	{
		Statistik s = super.getStatistik();
		if(gesamtAnzahl!=0) {
			double quote = ((double)fehlerAnzahl / (double)gesamtAnzahl) * 100;

			s.getStatFields().put("Anzahl der gespielten Runden", String.valueOf(gesamtAnzahl));
			s.getStatFields().put("Fehlerquote", String.format("%.1f", quote) + " %");
			s.getStatFields().put("Höchster Streak", String.valueOf(highestStreak) + " 🔥");

			String msg;

			if (quote == 0) {
				msg = "Perfekt! Du hast keinen Fehler gemacht. Weiter so!";
			} else if (quote <= 10) {
				msg = "Sehr gut! Nur wenige Fehler.";
			} else if (quote <= 25) {
				msg = "Gut, aber es gibt noch Raum für Verbesserung.";
			} else if (quote <= 50) {
				msg = "Es gibt einige Fehler, übe noch ein bisschen.";
			} else {
				msg = "Viel zu viele Fehler. Konzentriere dich und übe regelmäßig!";
			}

			s.setFliessText(msg);
		}
		return s;
	}
}//end LinearSpielModel