package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.artikeljaeger;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.LinearSpielModel;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SpielListener;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Statistik;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:37
 */
public class ArtikeljaegerModel extends LinearSpielModel {

	public ArtikeljaegerModel(){

	}

	public String getSpielDescription(){
		return "Teste dein Wissen über die richtigen Artikel! In Artikeljäger bekommst du ein Wort angezeigt und musst schnell den passenden Artikel auswählen – Der, Die oder Das – indem du auf die richtigen Buttons klickst.\n" + //
						"\n" + //
						"Je schneller und genauer du die Artikel auswählst, desto mehr Punkte bekommst du. Das Spiel trainiert spielerisch dein Gespür für die deutsche Grammatik und hilft dir, die Artikel von Substantiven sicher zu erkennen.";
	}

	public String getSpielName(){
		return "Artikeljaeger";
	}

	public Statistik getStatistik(){
		return super.getStatistik();
	}

	public boolean hasNewRound(){
		return false;
	}

	

	public void spielStarten(SpielListener l){

	}
}//end Artikelj�gerModel