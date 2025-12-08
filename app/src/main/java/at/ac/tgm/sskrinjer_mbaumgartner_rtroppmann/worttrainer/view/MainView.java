package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;


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

	/**
	 * 
	 * @param callback
	 */
	public void runOnThread(Runnable callback);

	/**
	 * 
	 * @param spielEngineView
	 */
	public void setSpiel(SpielEngineView spielEngineView);

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