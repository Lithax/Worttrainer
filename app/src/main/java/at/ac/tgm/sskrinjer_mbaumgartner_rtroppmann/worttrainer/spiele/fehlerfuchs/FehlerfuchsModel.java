package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fehlerfuchs;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.LinearSpielModel;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SpielListener;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Statistik;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:38
 */
public class FehlerfuchsModel extends LinearSpielModel {

	public FehlerfuchsModel(){

	}

	public String getSpielDescription(){
		return "Stelle deine Rechtschreibkenntnisse auf die Probe! In Fehlerfuchs bekommst du Wörter oder Sätze angezeigt, in denen sich kleine Rechtschreibfehler eingeschlichen haben. Deine Aufgabe ist es, diese Fehler zu erkennen und zu korrigieren – zum Beispiel „Doschen“ in „Duschen“.";
	}

	public String getSpielName(){
		return "Fehlerfuchs";
	}

	public Statistik getStatistik(){
		return super.getStatistik();
	}

	public boolean hasNewRound(){
		return false;
	}

	public void spielStarten(SpielListener l){

	}
}//end FehlerfuchsModel