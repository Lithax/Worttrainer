package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele;

import java.util.HashMap;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Statistik;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.Wortliste;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public abstract class SpielModel {

	protected Wortliste wortliste;

	public SpielModel(){

	}

	public abstract String getSpielDescription();

	public abstract String getSpielName();

	public Statistik getStatistik() {
        HashMap<String, String> fields = new HashMap<>();
        return new Statistik(getSpielDescription(), getSpielName() + " - Statistik", fields);
	}

	public abstract boolean hasNewRound();

	public abstract void spielStarten();
}//end SpielModel