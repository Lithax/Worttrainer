package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele;

import java.io.InputStream;
import java.nio.file.Path;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Statistik;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 13:58:04
 */
public abstract class SpielView<
        C extends SpielController<?, C, V>,
        V extends SpielView<C, V>
> {

	public static final Path iconsPath = Path.of("");
	private C spielController;

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
		
	}

	/**
	 * 
	 * @param statistik
	 */
	public void showStatistik(Statistik statistik){

	}
}//end SpielView