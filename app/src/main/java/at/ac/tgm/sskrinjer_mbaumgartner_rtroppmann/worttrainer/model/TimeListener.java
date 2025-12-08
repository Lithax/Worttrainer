package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model;


/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 15:02:19
 */
public interface TimeListener {

	/**
	 * 
	 * @param date
	 * @param time
	 */
	public void onUpdateTime(LocalDate date, LocalTime time);

}