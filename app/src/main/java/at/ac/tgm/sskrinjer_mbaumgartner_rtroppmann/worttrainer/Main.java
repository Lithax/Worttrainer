package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.MainModelImpl;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SpielListe;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.MainModel;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller.MainControllerImpl;

import javax.swing.SwingUtilities;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller.MainController;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.MainView;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.MainViewImpl;

/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 15:09:38
 */
public class Main {
	public Main() {
		SwingUtilities.invokeLater(() -> {
			MainController mC = new MainControllerImpl(SpielListe.SPIELE);
			MainView mV = new MainViewImpl(mC);
			MainModel mM = new MainModelImpl(mC);
			mC.setMainModel(mM);
            mC.setMainView(mV);
		});
	}
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args){
		new Main();
	}
}//end Main