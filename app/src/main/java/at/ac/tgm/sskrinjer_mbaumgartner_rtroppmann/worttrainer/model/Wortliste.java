package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 14:30:08
 */
public class Wortliste {

	private Wort[] alleWoerter;
	private Wort[] grundformenORersterFallWoerter;
	private Wort[] grundformenWoerter;
	public static final Path woerterPath;

	public Wortliste(){

	}

	public void finalize() throws Throwable {

	}
	/**
	 * 
	 * @param wortAnzahl
	 */
	public Wort[] getRandomBaseWoerter(int wortAnzahl){
		return null;
	}

	/**
	 * <font color="#008000">gibt nur die Woerter zurück, die fall==0 && (type == 0 ||
	 * type == 1) haben. Also alle Grundformen der Wörter</font>
	 * 
	 * @param wortAnzahl
	 * @param minBuchstaben
	 * @param maxBuchstaben
	 */
	public Wort[] getRandomBaseWoerter(int wortAnzahl, int minBuchstaben, int maxBuchstaben){
		return null;
	}

	/**
	 * 
	 * @param wortAnzahl
	 * @param predicate
	 */
	public Wort[] getRandomBaseWoerter(int wortAnzahl, Predicate<T> predicate){
		return null;
	}

	/**
	 * 
	 * @param wortAnzahl
	 */
	public Wort[] getRandomBaseWoerterWithPlural(int wortAnzahl){
		return null;
	}

	/**
	 * 
	 * @param wortAnzahl
	 * @param minBuchstaben
	 * @param maxBuchstaben
	 */
	public Wort[] getRandomBaseWoerterWithPlural(int wortAnzahl, int minBuchstaben, int maxBuchstaben){
		return null;
	}

	/**
	 * 
	 * @param wortAnzahl
	 * @param predicate
	 */
	public Wort[] getRandomBaseWoerterWithPlural(int wortAnzahl, Predicate<T> predicate){
		return null;
	}

	/**
	 * 
	 * @param wortAnzahl
	 * @param wortart
	 */
	public Wort[] getRandomWoerter(int wortAnzahl, Wortart wortart){
		return null;
	}

	/**
	 * 
	 * @param wortAnzahl
	 */
	public Wort[] getRandomWoerter(int wortAnzahl){
		return null;
	}

	/**
	 * 
	 * @param wortAnzahl
	 * @param minBuchstaben
	 */
	public Wort[] getRandomWoerter(int wortAnzahl, int minBuchstaben){
		return null;
	}

	/**
	 * 
	 * @param wordAnzahl
	 * @param minBuchstaben
	 * @param maxBuchstaben
	 */
	public Wort[] getRandomWoerter(int wordAnzahl, int minBuchstaben, int maxBuchstaben){
		return null;
	}

	/**
	 * 
	 * @param wortAnzahl
	 * @param wortArt
	 * @param minBuchstaben
	 * @param max Buchstaben
	 */
	public Wort[] getRandomWoerter(int wortAnzahl, Wortart wortArt, int minBuchstaben, int max Buchstaben){
		return null;
	}

	/**
	 * 
	 * @param wortAnzahl
	 * @param predicate
	 */
	public Wort[] getRandomWoerter(int wortAnzahl, Predicate<T> predicate){
		return null;
	}

	/**
	 * 
	 * @param wortAnzahl
	 */
	public Wort[] getRandomWoerterOhneFaelle(int wortAnzahl){
		return null;
	}

	/**
	 * 
	 * @param wortAnzahl
	 * @param minBuchstaben
	 * @param maxBuchstaben
	 */
	public Wort[] getRandomWoerterOhneFaelle(int wortAnzahl, int minBuchstaben, int maxBuchstaben){
		return null;
	}

	/**
	 * 
	 * @param wortAnzahl
	 * @param predicate
	 */
	public Wort[] getRandomWoerterOhneFaelle(int wortAnzahl, Predicate<T> predicate){
		return null;
	}

	public static Wortliste loadWortliste(){
		return null;
	}

	/**
	 * Holt sich die Wörter aus einem JSON. Baut aus einem Wort, dass mehrere Wörter
	 * enthält eigene Wörter und referenziert sie dann in den Attributen ("singular",
	 * "plural" und "formen"), damit man vom base Wort noch auf die Formen kommen kann.
	 * 
	 * 
	 * @param path
	 */
	public static Wortliste loadWortliste(Path path){
		/*
		Holt sich die Wörter aus einem JSON. 
		Baut aus einem Wort, dass mehrere Wörter enthält eigene Wörter 
		und referenziert sie dann in den Attributen ("singular", "plural" und "formen"),
		damit man vom base Wort noch auf die Formen kommen kann.
		*/
	}

	public WortListe(){

	}
}//end Wortliste