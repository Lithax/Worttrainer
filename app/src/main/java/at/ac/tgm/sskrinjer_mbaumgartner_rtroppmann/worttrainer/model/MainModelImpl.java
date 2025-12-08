package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model;


/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 14:30:07
 */
public class MainModelImpl implements MainModel {

	private GameState gameState;
	private TimeUpdateThread timer;
	public Wortliste m_Wortliste;
	public Einstellungen m_Einstellungen;
	public Tip m_Tip;
	public TimeUpdateThread m_TimeUpdateThread;

	public MainModelImpl(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * 
	 * @param controller
	 */
	public MainModelImpl(MainController controller){

	}

	public Einstellungen getEinstellungen(){
		return null;
	}

	public GameState getGameState(){
		return null;
	}

	public Tip getTipOfTheDay(){
		return null;
	}

	public Wortliste getWortliste(){
		return null;
	}

	public Wortliste getWortListe(){
		return null;
	}
}//end MainModelImpl