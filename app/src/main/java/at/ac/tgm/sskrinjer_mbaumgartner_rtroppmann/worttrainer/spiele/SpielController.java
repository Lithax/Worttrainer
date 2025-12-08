package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele;

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

	public SpielController(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * 
	 * @param spielModel
	 * @param spielView
	 */
	public SpielController(M spielModel, V spielView){

	}

	public String getDescription(){
		return "";
	}

	public InputStream getIcon(){
		return null;
	}

	public String getName(){
		return "";
	}

	public SpielEngineView getSpielEngineView(){
		return null;
	}

	public void spielBeenden(){

	}

	public abstract void spielStarten();
}//end SpielController