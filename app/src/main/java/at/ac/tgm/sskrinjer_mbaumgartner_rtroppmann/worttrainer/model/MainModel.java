package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model;


/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 14:30:07
 */
public interface MainModel {

	public Einstellungen getEinstellungen();

	public GameState getGameState();

	public Tip getTipOfTheDay();

	public Wortliste getWortListe();

}