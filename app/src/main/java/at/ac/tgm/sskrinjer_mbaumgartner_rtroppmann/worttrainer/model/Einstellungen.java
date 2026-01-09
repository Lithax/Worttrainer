package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.util.GsonIO;

/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 15:09:38
 */
public class Einstellungen {
	private int anzahlSpielrunden;
	public static final Path einstellungenPath = Path.of("settings/settings.json");
	private static final String[] schwierigkeiten = new String[]{"Leicht","Normal","Schwer", "DEMO"};
	private String schwierigkeit;
	private String theme;

	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * START AT 1 BEING THE LOWEST!
	 * @param schwierigkeit
	 * @return
	 */
	public int numerifySchwierigkeit(String schwierigkeit) {
		for(int i = 0; i < schwierigkeiten.length; i++) {
			if(schwierigkeiten[i].equals(schwierigkeit))
				return i+1;
		}
		return -1;
	}

    public int numerifySchwierigkeit() {
        return numerifySchwierigkeit(schwierigkeit);
    }

	public String[] getSchwierigkeiten() {
		return schwierigkeiten;
	}

	public int getAnzahlRunden(){
		return anzahlSpielrunden;
	}

	public String getSchwierigkeit(){
		return schwierigkeit;
	}

	public String getTheme(){
		return theme;
	}

	public void save() throws IOException{
		save(this, einstellungenPath);
	}

	/**
	 * 
	 * @param einstellungen
	 * @param path
	 */
	public static void save(Einstellungen einstellungen, Path path) throws IOException {
		GsonIO.save(einstellungen, Einstellungen.class, path);
	}

	/**
	 * 
	 * @param path
	 * @return einstellungen
	 */
	public static Einstellungen load(Path path) throws IOException {
		return GsonIO.load(path, Einstellungen.class);
	}

	/**
	 * 
	 * @param anzahlRunden
	 */
	public void setAnzahlRunden(int anzahlRunden)  {
		if(anzahlRunden < 0 || anzahlRunden > 100) 
			throw new IllegalArgumentException("Anzahl der runden darf nur im Bereich 0 - 100 sein!");
		this.anzahlSpielrunden = anzahlRunden;
	}

	/**
	 * 
	 * @param schwierigkeit
	 */
	public void setSchwierigkeit(String schwierigkeit)  {
		boolean found = false;
		for(String s : schwierigkeiten)
			if(s.equals(schwierigkeit)) found = true;
		if(!found) 
			throw new IllegalArgumentException("Schwierigkeit muss entweder 1, 2 oder 3 sein!");
		this.schwierigkeit = schwierigkeit;
	}
}//end Einstellungen