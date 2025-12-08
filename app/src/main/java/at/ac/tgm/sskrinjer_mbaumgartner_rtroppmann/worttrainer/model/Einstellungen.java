package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model;

import com.google.gson.Gson;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 14:30:06
 */
public class Einstellungen {

	private int anzahlSpielrunden;
	public static final Path einstellungenPath;
	private int schwierigkeit;
	private String theme;



	public void finalize() throws Throwable {

	}
	public Einstellungen(){

	}

	public int getAnzahlRunden(){
		return 0;
	}

	public int getSchwierigkeit(){
		return 0;
	}

	public String getTheme(){
		return "";
	}

	public void save(){

	}

	/**
	 * 
	 * @param einstellungen
	 * @param path
	 */
	public static void save(Einstellungen einstellungen, Path path){

	}

	/**
	 * 
	 * @param anzahlRunden
	 */
	public void setAnzahlRunden(int anzahlRunden){

	}

	/**
	 * 
	 * @param schwierigkeit
	 */
	public void setSchwierigkeit(int schwierigkeit){

	}

	/**
	 * 
	 * @param schwierigkeit
	 */
	public void setSchwierigkeit(int schwierigkeit){

	}

	/**
	 * 
	 * @param theme
	 */
	public void setTheme(String theme){

	}
}//end Einstellungen