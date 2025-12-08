package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import java.awt.GridLayout;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JPanel;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.Spiel;

/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public class SpieleViewImpl extends JPanel implements SpieleView {
	private AtomicReference<SpieleListener> l;

	public SpieleViewImpl(SpieleListener l){
		this.l = new AtomicReference<>(l);
		setLayout(new GridLayout(0, 3, 10, 10));
	}

	/**
	 * 
	 * @param spiele
	 */
	public void setSpiele(List<Spiel> spiele){
		removeAll();
		for(Spiel s : spiele)
			add(new SpielPanel(s.getName(), s.getDescription(), s.getIcon(), l));
	}
}//end SpieleViewImpl