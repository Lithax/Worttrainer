package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;


/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 14:30:07
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