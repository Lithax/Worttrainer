package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller.SpieleListener;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 14:30:08
 */
public abstract class SpielController<M extends SpielModel, V extends SpielView<? extends SpielController<M, V>>> implements Spiel {

	protected M model;
	protected V view;
	public SpieleListener m_SpieleListener;

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

	public SpielView getSpielView(){
		return null;
	}

	public void spielBeenden(){

	}

	public abstract void spielStarten();
}//end SpielController