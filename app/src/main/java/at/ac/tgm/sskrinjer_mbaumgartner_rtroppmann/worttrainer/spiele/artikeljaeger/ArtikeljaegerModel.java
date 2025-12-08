package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.artikeljaeger;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.LinearSpielModel;
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
		return "";
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

	public void spielStarten(){

	}
}//end Artikelj�gerModel