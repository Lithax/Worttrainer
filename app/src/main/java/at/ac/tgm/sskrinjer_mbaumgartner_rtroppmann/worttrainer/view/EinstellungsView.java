package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;


/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 14:30:06
 */
public interface EinstellungsView {

	public int getAnzahlRunden();

	public int getSchwierigkeit();

	public String getTheme();

	/**
	 * 
	 * @param anzahlRunden
	 */
	public void setAnzahlRunden(int anzahlRunden);

	/**
	 * 
	 * @param schwierigkeit
	 */
	public void setSchwierigkeit(int schwierigkeit);

	/**
	 * 
	 * @param theme
	 */
	public void setTheme(String theme);

}