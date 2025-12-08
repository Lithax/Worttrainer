package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.SpielEngineViewImpl;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.StatistikView;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Statistik;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 14:30:08
 */
public abstract class SpielView<C extends SpielController<?, ? extends SpielView<C>>> extends JPanel {

	public static final Path iconsPath;
	private C spielController;
	public SpielEngineViewImpl m_SpielEngineViewImpl;

	public SpielView(){

	}

	public void finalize() throws Throwable {
		super.finalize();
	}
	/**
	 * 
	 * @param spielController
	 */
	public SpielView(C spielController){

	}

	public abstract InputStream loadIcon();

	/**
	 * 
	 * @param path
	 */
	protected static InputStream loadIcon(Path path){
		return null;
	}

	public void neuesSpiel(){
		view.show(this);
	}

	/**
	 * 
	 * @param statistik
	 */
	public void showStatistik(Statistik statistik){

	}
}//end SpielView