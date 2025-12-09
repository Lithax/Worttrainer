package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicReference;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.Main;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller.MainController;
import static at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.util.Propagate.propagate;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public class MainModelImpl implements MainModel {

	private GameState gameState;
	private TimeUpdateThread timer;
	private Wortliste wortliste;
	private Einstellungen einstellungen;

	/**
	 * 
	 * @param controller
	 * @throws IOException 
	 */
	public MainModelImpl() {
		timer = new TimeUpdateThread();
		timer.start();
	}

	@Override
	public void setController(MainController controller) {
		timer.setTimeListener(controller);
	}

	public Einstellungen getEinstellungen() throws IOException {
		if(einstellungen == null) einstellungen = Einstellungen.load(Einstellungen.einstellungenPath);
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