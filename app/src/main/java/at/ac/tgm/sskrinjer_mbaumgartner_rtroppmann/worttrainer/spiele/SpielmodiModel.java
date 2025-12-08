package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.statistik.Statistik;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 13:30:01
 */
public abstract class SpielmodiModel {

	protected Wortliste[] wortliste;

	public SpielmodiModel(){

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
}//end SpielmodiModel