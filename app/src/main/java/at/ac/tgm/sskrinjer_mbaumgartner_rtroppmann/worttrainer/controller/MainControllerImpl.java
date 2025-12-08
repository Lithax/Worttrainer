package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Einstellungen;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.GameState;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.MainModel;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.Spiel;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.EinstellungsView;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.MainView;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.MessageType;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:38
 */
public class MainControllerImpl implements MainController {
	private Spiel currentSpiel;
	private MainModel mainModel;
	private MainView mainView;
	private Class<? extends Spiel>[] spiele;

	/**
	 * 
	 * @param spiele
	 */
	public MainControllerImpl(Class<? extends Spiel>[] spiele){
		this.spiele = spiele;

		Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
			mainView.showMessage("Internal Error", e.getMessage(), MessageType.ERROR);
		});
	}

	public void onSpielBeenden(){
		
	}

	/**
	 * 
	 * @param datum
	 * @param time
	 */
	public void onUpdateTime(LocalDate datum, LocalTime time){
		mainView.getHomeView().setDatum(datum);
		mainView.getHomeView().setUhrzeit(time);
	}

	/**
	 * 
	 * @param mainView
	 */
	public void setMainView(MainView mainView){
		this.mainView = mainView;
	}

	public void onEinstellungenSave() throws IOException {
		EinstellungsView eV = mainView.getEinstellungsView();
		Einstellungen eM = mainModel.getEinstellungen();
		try {
			eM.setAnzahlRunden(eV.getAnzahlRunden());
			eM.setSchwierigkeit(eV.getSchwierigkeit());
			if(!eM.getTheme().equals(eV.getTheme())) 
				eM.setTheme(eV.getTheme());
			eM.save();
		} catch (IllegalArgumentException e) {
			mainView.showMessage("Invalide Eingabe", e.getMessage(), MessageType.ERROR);
		}
	}

	@Override
	public void setMainModel(MainModel mM) {
		mainModel = mM;
	}

	@Override
	public void onSpielSelected(String name) {
		
	}
}//end MainControllerImpl