package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele;

import java.io.IOException;
import java.io.InputStream;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.SpielEngineView;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public abstract class SpielController<
        M extends SpielModel,
        C extends SpielController<M, C, V>,
        V extends SpielView<C, V>
> implements Spiel {

	protected M model;
	protected V view;
	protected SpielListener spielListener;

	public String getDescription(){
		return model.getSpielDescription();
	}

	public InputStream getIcon() throws IOException{
		return view.loadIcon();
	}

	public String getName(){
		return model.getSpielName();
	}

	public SpielEngineView newSpielEngineView(){
		return view;
	}

	public void spielBeenden(){
		view.showStatistik(model.getStatistik());
	}

	@Override
	public void setSpielListener(SpielListener l) {
		this.spielListener = l;
	}

	public abstract void spielStarten();
}//end SpielController