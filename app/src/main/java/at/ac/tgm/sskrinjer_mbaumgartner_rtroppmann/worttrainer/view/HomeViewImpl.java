package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.JLabel;
import javax.swing.JPanel;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Tip;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:38
 */
public class HomeViewImpl extends JPanel implements HomeView {

	private JLabel datumLabel;
	private JLabel tipContent;
	private JLabel tipTitle;
	private JLabel uhrzeitLabel;
	private JLabel wochentagLabel;

	public HomeViewImpl(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 
	 * @param datum
	 */
	@Override
	public void setDatum(LocalDate datum){

	}

	/**
	 * 
	 * @param tip
	 */
	@Override
	public void setTipOfTheDay(Tip tip){

	}

	/**
	 * 
	 * @param uhrzeit
	 */
	@Override
	public void setUhrzeit(LocalTime uhrzeit){

	}
}//end HomeViewImpl