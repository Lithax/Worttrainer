package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele;

import java.io.InputStream;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.SpielEngineView;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public interface Spiel {

	public String getDescription();

	public InputStream getIcon();

	public String getName();

	public SpielEngineView getSpielEngineView();

	public void spielBeenden();

	public void spielStarten();

}