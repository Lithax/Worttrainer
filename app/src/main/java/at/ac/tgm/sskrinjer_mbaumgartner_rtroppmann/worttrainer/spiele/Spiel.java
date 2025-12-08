package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele;


/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 14:30:08
 */
public interface Spiel {

	public String getDescription();

	public InputStream getIcon();

	public String getName();

	public SpielView getSpielView();

	public void spielBeenden();

	public void spielStarten();

}