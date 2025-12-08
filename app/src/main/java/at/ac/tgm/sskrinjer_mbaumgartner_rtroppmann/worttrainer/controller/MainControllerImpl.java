package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.MainModel;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.Spiel;
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

	public MainControllerImpl(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * 
	 * @param spiele
	 */
	public MainControllerImpl(List<Spiel> spiele){

	}

	public void onEinstellungenSave(){

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
	public void onUpdateTime(LocalDateTime datum, LocalTime time){

	}

	/**
	 * 
	 * @param mainView
	 */
	public void setMainView(MainView mainView){

	}

	public void einstellungenChanged(){

	}

	public void openEinstellungenRequest(){

	}

	/**
	 * 
	 * @param spielName
	 */
	public void spielStartenRequest(String spielName){

	}

	@Override
	public void onUpdateTime(LocalDate datum, LocalTime time) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'onUpdateTime'");
	}
}//end MainControllerImpl