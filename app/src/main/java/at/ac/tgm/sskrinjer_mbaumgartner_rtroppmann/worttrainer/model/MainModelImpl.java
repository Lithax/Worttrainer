package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model;

import java.io.IOException;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.Wortliste;

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
	private UserData userData;

	private String[] wortlistenPaths = {".import/input.json"};

	/**
	 * 
	 * @param controller
	 * @throws IOException 
	 */
	public MainModelImpl() {
		timer = new TimeUpdateThread();
		timer.start();
		wortliste = Wortliste.getInstance();
	}

	@Override
	public void setTimeListener(TimeListener controller) {
		timer.setTimeListener(controller);
	}

	public Einstellungen getEinstellungen() throws IOException {
		if(einstellungen == null) einstellungen = Einstellungen.load(Einstellungen.einstellungenPath);
		return einstellungen;
	}

	public UserData getUserData() throws IOException {
		if(userData == null) userData = UserData.load(UserData.userDataPath);
		return userData;
	}

	public GameState getGameState(){
		return gameState;
	}

	public Tip getTipOfTheDay() throws IOException {
		return Tip.getTipOfTheDay();
	}

	public void loadWortliste() throws IOException {
		wortliste.ladeAusDateien(wortlistenPaths);
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