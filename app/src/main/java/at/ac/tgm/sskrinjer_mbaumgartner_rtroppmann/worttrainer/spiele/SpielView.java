package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Statistik;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.SpielEngineView;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.StatistikDialog;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 13:58:04
 */
public abstract class SpielView<
        C extends SpielController<?, C, V>,
        V extends SpielView<C, V>
> implements SpielEngineView {

	public static final Path iconsPath = Path.of("resources/icons");
	protected C spielController;

	/**
	 * 
	 * @param spielController
	 */
	public SpielView(C spielController){

	}

	protected Path getIconFile() {
		return Path.of(spielController.getName());
	}

	/**
	 * 
	 * @param path
	 * @throws IOException 
	 */
	public InputStream loadIcon() throws IOException{
		return Files.newInputStream(iconsPath.resolve(getIconFile()));
	}

	public void neuesSpiel(){
		
	}

	/**
	 * 
	 * @param statistik
	 */
	public void showStatistik(Statistik statistik){
		StatistikDialog sDialog = new StatistikDialog(statistik);
		sDialog.setVisible(true);
	}
}//end SpielView