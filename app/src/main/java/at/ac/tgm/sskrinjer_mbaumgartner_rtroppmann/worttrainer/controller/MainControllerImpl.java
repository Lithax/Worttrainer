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
	public MainControllerImpl(List<Spiel> spiele){
		this.spiele = spiele;

		Thread.setDefaultUncaughtExceptionHandler((t, e ) -> {

		});
	}

	public void onSpielBeenden(){

	}

	/**
	 * 
	 * @param spielName
	 */
	public void onSpielStarten(String spielName){

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
			
		}
	}

	/**
	 * 
	 * @param spielName
	 */
	public void spielStartenRequest(String spielName){
		
	}

	@Override
	public void setMainModel(MainModel mM) {
		mainModel = mM;
	}
}//end MainControllerImpl