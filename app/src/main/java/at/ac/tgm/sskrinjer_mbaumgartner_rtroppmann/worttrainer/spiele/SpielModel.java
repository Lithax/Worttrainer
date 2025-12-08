package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Statistik;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:02:19
 */
public abstract class SpielModel {

	protected Wortliste[] wortliste;

	public SpielModel(){

	}

	public void finalize() throws Throwable {

	}
	public abstract String getSpielDescription();

	public abstract String getSpielName();

	public Statistik getStatistik(){
		return null;
	}

	public abstract boolean hasNewRound();

	public abstract void spielStarten();
}//end SpielModel