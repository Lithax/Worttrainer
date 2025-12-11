package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller;

import static at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.util.Propagate.propagate;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Einstellungen;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.GameState;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.MainModel;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.TimeListener;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.Spiel;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.util.Propagate;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.EinstellungenListener;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.EinstellungsView;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.MainView;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.MessageType;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.SpieleListener;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:38
 */
public class MainControllerImpl implements MainController {
	private Spiel currentSpiel;
	private MainModel mainModel;
	private MainView mainView;
	private List<Spiel> spiele;

	/**
	 * 
	 * @param spiele
	 */
	public MainControllerImpl(List<Spiel> spiele, MainModel mainModel, MainView mainView){
		this.spiele = spiele;
		this.mainModel = mainModel;
		this.mainView = mainView;

		Thread.setDefaultUncaughtExceptionHandler((t, e) -> handleException(e));
		Propagate.exceptHandler = this::handleException;

		mainModel.setTimeListener(this);
		mainView.setController(this);

		String[] themes = mainView.getAvailableThemes();

		mainView.getEinstellungsView().setThemes(themes);

		propagate(() -> mainView.setTheme(mainModel.getEinstellungen().getTheme()));

		propagate(() -> mainView.getHomeView().setTipOfTheDay(mainModel.getTipOfTheDay()));

		mainModel.setTimeListener(this);


		updateEinstellungsView();
	}

	public void onSpielBeenden(){
		if(currentSpiel == null) return;
		currentSpiel.spielBeenden();
		currentSpiel = null;
		mainModel.setGameState(GameState.GAME_ABSENT);
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

	private void updateEinstellungsView() {
		propagate(() -> {
			EinstellungsView eV = mainView.getEinstellungsView();
			Einstellungen eM = mainModel.getEinstellungen();
			eV.setAnzahlRunden(eM.getAnzahlRunden());
			eV.setSchwierigkeit(eM.getSchwierigkeit());
			eV.setTheme(eM.getTheme());
			if(!eV.getTheme().equals(eM.getTheme())) 
				mainView.setTheme(eM.getTheme());
		});
	}

	public void onEinstellungenSave()  {
		EinstellungsView eV = mainView.getEinstellungsView();
		Einstellungen eM = propagate(() -> mainModel.getEinstellungen());
		try {
			eM.setAnzahlRunden(eV.getAnzahlRunden());
			eM.setSchwierigkeit(eV.getSchwierigkeit());
			eM.setTheme(eV.getTheme());
			//if(!eM.getTheme().equals(eV.getTheme())) 
				mainView.setTheme(eM.getTheme());
			propagate(() -> eM.save());
		} catch (IllegalArgumentException e) {
			mainView.showMessage("Invalide Eingabe", e.getMessage(), MessageType.ERROR);
		}
	}

	@Override
	public void onSpielSelected(String name) {
		for(Spiel spiel : spiele)
			if(spiel.getName().endsWith(name)) {
				currentSpiel = spiel;
				mainModel.setGameState(GameState.GAME_RUNNING);
				return;
			}
		mainView.showMessage("Lade Fehler", "Spiel konnte nicht gefunden werden", MessageType.ERROR);
	}

	private void handleException(Throwable e) {
		mainView.showMessage("Internal Error", e.getMessage(), MessageType.ERROR);
		e.printStackTrace();
	}
}//end MainControllerImpl