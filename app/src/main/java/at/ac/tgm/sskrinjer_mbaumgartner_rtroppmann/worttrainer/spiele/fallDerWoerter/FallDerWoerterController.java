package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fallDerWoerter;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SpielController;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:38
 */
public class FallDerWoerterController extends SpielController<FallDerWoerterModel, FallDerWoerterController, FallDerWoerterView> {

	public FallDerWoerterController(){
		model = new FallDerWoerterModel();
		view = new FallDerWoerterView(this);
	}

	@Override
	public void spielBeenden() {
		super.spielBeenden();
	}

	public void spielStarten(){

	}
}//end FallDerWoerterController