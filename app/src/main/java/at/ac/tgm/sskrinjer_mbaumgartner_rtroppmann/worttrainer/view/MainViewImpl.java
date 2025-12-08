package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller.MainController;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public class MainViewImpl extends JFrame implements MainView {

	private EinstellungsView einstellungsView;
	private HomeView homeView;
	private SpieleView spieleView;
	private JTabbedPane tabbedPane;
	public MainController m_MainController;
	public EinstellungsView m_EinstellungsView;
	public HomeView m_HomeView;
	public SpieleView m_SpieleView;

	public MainViewImpl(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	public void disposeSpiel(){

	}

	public EinstellungsView getEinstellungsView(){
		return null;
	}

	public HomeView getHomeView(){
		return null;
	}

	public SpieleView getSpieleView(){
		return null;
	}

	/**
	 * 
	 * @param spielEngineView
	 */
	public void setSpiel(SpielEngineView spielEngineView){

	}

	/**
	 * 
	 * @param callback
	 */
	public void runOnThread(Runnable callback){
		SwingUtilities.invokeLater(callback);
	}
}//end MainViewImpl