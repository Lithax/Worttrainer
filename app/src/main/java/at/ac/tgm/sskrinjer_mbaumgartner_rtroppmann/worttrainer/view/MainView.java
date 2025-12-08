package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;


/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:02:18
 */
public interface MainView {

	public EinstellungsView getEinstellungsView();

	public HomeView getHomeView();

	public SpieleView getSpieleView();

	/**
	 * 
	 * @param callback
	 */
	public void runOnThread(Runnable callback);

	/**
	 * 
	 * @param spielView
	 */
	public void setSpiel(SpielView spielView);

}