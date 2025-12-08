package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.MainModel;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.Spiel;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.MainView;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:02:18
 */
public class MainControllerImpl implements GeneralListener, SpieleListener, MainController {

	private Spiel currentSpiel;
	private MainModel mainModel;
	private MainView mainView;
	private List<Spiele> spiele;
	public MainModel m_MainModel;
	public Spiel m_Spiel;
	public MainView m_MainView;

	public MainControllerImpl(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * 
	 * @param spiele
	 */
	public MainControllerImpl(List<Spiele> spiele){

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
	public void onUpdateTime(LocalDate datum, LocalTime time){

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
}//end MainControllerImpl