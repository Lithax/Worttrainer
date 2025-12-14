package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller.MainController;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.Spiel;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public interface MainView {

	public void disposeSpiel();

	public EinstellungsView getEinstellungsView();

	public HomeView getHomeView();

	public SpieleView getSpieleView();

	public void setController(MainController controller);

	/**
	 * 
	 * @param callback
	 */
	public void runOnThread(Runnable callback);

	/**
	 * 
	 * @param spielEngineView
	 */
	public void setSpiel(Spiel spiel);

	/**
	 * 
	 * @param theme
	 */
	public void setTheme(String theme);

	/**
	 * 
	 * @param theme
	 */
	public String[] getAvailableThemes();

	/**
	 * @param title
	 * @param content
	 */
	public void showMessage(String title, String content, MessageType type);
}