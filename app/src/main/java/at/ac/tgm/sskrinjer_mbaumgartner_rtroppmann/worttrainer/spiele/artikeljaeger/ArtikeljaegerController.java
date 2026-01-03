package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.artikeljaeger;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.Wortliste;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SpielController;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:37
 */
public class ArtikeljaegerController extends SpielController<ArtikeljaegerModel, ArtikeljaegerController, ArtikeljaegerView> {

	public ArtikeljaegerController(){
		model = new ArtikeljaegerModel();
		view = new ArtikeljaegerView(this);
	}

	@Override
	public void spielBeenden() {
		super.spielBeenden();
	}

	public void spielStarten(){
		
	}
}//end ArtikeljaegerController