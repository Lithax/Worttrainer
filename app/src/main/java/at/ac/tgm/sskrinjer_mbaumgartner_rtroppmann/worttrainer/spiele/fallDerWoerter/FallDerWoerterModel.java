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
		return "Teste dein Wissen über die Wortarten! In Fall der Wörter fallen Wörter vom oberen Bildschirmrand, und deine Aufgabe ist es, sie in das richtige „Wortarten-Loch“ zu steuern – zum Beispiel „laufen“ in das Loch für Verb oder „Hund“ in das Loch für Nomen. Je schneller und genauer du die Wörter richtig einsortierst, desto mehr Punkte bekommst du. Das Spiel hilft dir, die verschiedenen Wortarten zu erkennen und dein grammatisches Verständnis zu trainieren.";
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