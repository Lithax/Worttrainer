package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fehlerfuchs;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SpielController;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:38
 */
public class FehlerfuchsController extends SpielController<FehlerfuchsModel, FehlerfuchsController, FehlerfuchsView> {

	public FehlerfuchsController(){
		model = new FehlerfuchsModel();
		view = new FehlerfuchsView(this);
	}

	@Override
	public void spielBeenden() {
		super.spielBeenden();
	}

	@Override
	public void spielStarten() {
		
	}
}//end FehlerfuchsController