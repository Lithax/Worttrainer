package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public interface TimeListener {

	/**
	 * 
	 * @param date
	 * @param time
	 */
	public void onUpdateTime(LocalDate date, LocalTime time);

}