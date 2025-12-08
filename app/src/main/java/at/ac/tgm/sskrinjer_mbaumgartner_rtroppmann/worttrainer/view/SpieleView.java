package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;


/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 14:30:08
 */
public interface SpieleView {

	/**
	 * 
	 * @param controller
	 */
	public void setController(MainController controller);

	/**
	 * 
	 * @param spiele
	 */
	public void setSpiele(List<Spiel> spiele);

}