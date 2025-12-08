package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller.MainController;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public class MainModelImpl implements MainModel {

	private GameState gameState;
	private TimeUpdateThread timer;
	public Wortliste m_Wortliste;
	public Einstellungen m_Einstellungen;

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