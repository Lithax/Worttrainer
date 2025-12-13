package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.Wortliste;

import java.io.IOException;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public interface MainModel {

	public Einstellungen getEinstellungen() throws IOException;

	UserData getUserData() throws IOException;

	public GameState getGameState();

	public void setGameState(GameState g);

	public Tip getTipOfTheDay() throws IOException;

	public Wortliste getWortliste();

	public void setTimeListener(TimeListener controller);
}