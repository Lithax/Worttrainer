package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller.MainController;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public class MainModelImpl implements MainModel {

	private GameState gameState;
	private TimeUpdateThread timer;
	public Wortliste wortliste;
	public Einstellungen einstellungen;

	/**
	 * 
	 * @param controller
	 */
	public MainModelImpl(MainController controller){
		timer.setTimeListener(controller);
	}


	public Einstellungen getEinstellungen(){
		return einstellungen;
	}

	public GameState getGameState(){
		return gameState;
	}

	public Tip getTipOfTheDay() throws IOException {
		return Tip.getTipOfTheDay();
	}


	@Override
	public void setGameState(GameState g) {
		gameState = g;
	}


	@Override
	public Wortliste getWortliste() {
		return wortliste;
	}
}//end MainModelImpl