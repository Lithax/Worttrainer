package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import com.google.common.util.concurrent.TimeLimiter;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.MainModel;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.TimeListener;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.MainView;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view.SpieleListener;

/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 15:09:38
 */
public interface MainController extends TimeListener, SpieleListener {

	public void onEinstellungenSave() throws IOException;

	/**
	 * 
	 * @param datum
	 * @param time
	 */
	public void onUpdateTime(LocalDate datum, LocalTime time);
}