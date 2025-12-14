package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fehlerfuchs;

import java.io.InputStream;

import javax.swing.JPanel;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SpielView;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SwingSpielView;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.artikeljaeger.ArtikeljaegerController;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:38
 */
public class FehlerfuchsView extends SwingSpielView<FehlerfuchsController, FehlerfuchsView> {

	public FehlerfuchsView(FehlerfuchsController c){
		super(c);
	}
}//end FehlerfuchsView