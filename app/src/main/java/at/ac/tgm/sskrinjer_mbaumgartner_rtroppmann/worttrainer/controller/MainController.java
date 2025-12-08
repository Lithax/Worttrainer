package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller;

import java.time.LocalDate;
import java.time.LocalTime;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.MainView;

/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 15:09:38
 */
public interface MainController {

	public void onEinstellungenSave();

	/**
	 * 
	 * @param spielName
	 */
	public void onSpielStarten(String spielName);

	/**
	 * 
	 * @param datum
	 * @param time
	 */
	public void onUpdateTime(LocalDate datum, LocalTime time);

	/**
	 * 
	 * @param mainView
	 */
	public void setMainView(MainView mainView);

}