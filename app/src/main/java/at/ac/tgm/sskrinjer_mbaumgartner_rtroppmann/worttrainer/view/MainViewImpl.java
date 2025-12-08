package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller.GeneralListener;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller.MainController;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 14:30:08
 */
public class MainViewImpl extends JFrame implements SpielEngineViewImpl, MainView {

	private EinstellungsView einstellungsView;
	private HomeView homeView;
	private SpieleView spieleView;
	private JTabbedPane tabbedPane;
	public BasicFrame m_BasicFrame;
	public GeneralListener m_GeneralListener;
	public MainController m_MainController;
	public EinstellungsView m_EinstellungsView;
	public HomeView m_HomeView;
	public SpieleView m_SpieleView;

	public MainViewImpl(){

	}

	public void finalize() throws Throwable {
		super.finalize();
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
	 * @param spielView
	 */
	public void setSpiel(SpielView spielView){

	}

	/**
	 * 
	 * @param callback
	 */
	public void runOnThread(Runnable callback){

	}

	/**
	 * 
	 * @param spielView
	 */
	public void setSpiel(SpielView spielView){

	}
}//end MainViewImpl