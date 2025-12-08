package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fallDerWoerter;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.LinearSpielModel;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Statistik;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:38
 */
public class FallDerWoerterModel extends LinearSpielModel {

	public FallDerWoerterModel(){

	}

	public String getSpielDescription(){
		return "";
	}

	public String getSpielName(){
		return "Fall der Wörter";
	}

	public Statistik getStatistik(){
		return super.getStatistik();
	}

	public boolean hasNewRound(){
		return false;
	}

	public void spielStarten(){

	}
}//end FallDerWoerterModel