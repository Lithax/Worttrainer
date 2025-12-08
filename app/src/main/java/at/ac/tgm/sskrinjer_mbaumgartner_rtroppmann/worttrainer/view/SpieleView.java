package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import java.io.IOException;
import java.util.List;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller.MainController;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.Spiel;

/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public interface SpieleView {

	/**
	 * 
	 * @param spiele
	 */
	public void setSpiele(List<Spiel> spiele) throws IOException;

}