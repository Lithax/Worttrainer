package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import java.time.LocalDate;
import java.time.LocalTime;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Tip;

/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 15:09:38
 */
public interface HomeView {

	/**
	 * 
	 * @param datum
	 */
	public void setDatum(LocalDate datum);

	/**
	 * 
	 * @param tip
	 */
	public void setTipOfTheDay(Tip tip);

	/**
	 * 
	 * @param uhrzeit
	 */
	public void setUhrzeit(LocalTime uhrzeit);

}