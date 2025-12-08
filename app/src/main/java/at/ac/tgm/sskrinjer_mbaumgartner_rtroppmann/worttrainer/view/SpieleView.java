package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;


/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 15:02:19
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