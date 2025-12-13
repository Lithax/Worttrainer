package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;


/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 15:09:38
 */
public interface EinstellungsView {

	public int getAnzahlRunden();

	public String getSchwierigkeit();

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
	public void setSchwierigkeit(String schwierigkeit);

	void setSchwierigkeiten(String[] schwierigkeiten);

	/**
	 * 
	 * @param theme
	 */
	public void setTheme(String theme);

	void setThemes(String[] themes);

	void setEinstellungsListener(EinstellungenListener l);
}