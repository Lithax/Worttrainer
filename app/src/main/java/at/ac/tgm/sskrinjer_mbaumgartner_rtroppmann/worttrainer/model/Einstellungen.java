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

/**
 * @author mhbau
 * @version 1.0
 * @created 08-Dez-2025 15:09:38
 */
public class Einstellungen {
	private int anzahlSpielrunden;
	public static final Path einstellungenPath = Path.of("settings/settings.json");
	private int schwierigkeit;
	private String[] themes;

	public String[] getThemes() {
		return themes;
	}

	public void setThemes(String[] themes) {
		this.themes = themes;
	}

	private String theme;

	public int getAnzahlRunden(){
		return anzahlSpielrunden;
	}

	public int getSchwierigkeit(){
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
		Gson gson = new Gson();
		String jsonString = gson.toJson(einstellungen, Einstellungen.class);
		Files.writeString(path, jsonString, 
			StandardCharsets.UTF_8, 
			StandardOpenOption.CREATE, 
			StandardOpenOption.TRUNCATE_EXISTING
		);
	}

	/**
	 * 
	 * @param path
	 * @return einstellungen
	 */
	public static Einstellungen load(Path path) throws IOException {
		Gson gson = new Gson();
		String jsonString = new String(Files.readAllBytes(path));
		return gson.fromJson(jsonString, Einstellungen.class);
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
	public void setSchwierigkeit(int schwierigkeit)  {
		if(schwierigkeit < 1 || schwierigkeit > 3) 
			throw new IllegalArgumentException("Schwierigkeit muss entweder 1, 2 oder 3 sein!");
		this.schwierigkeit = schwierigkeit;
	}

	/**
	 * 
	 * @param theme
	 */
	public void setTheme(String theme) {
		for(String t : themes)
			if(t.equals(theme)) { this.theme = theme; return; }
		throw new IllegalArgumentException("Dieses Theme existiert nicht!");
	}
}//end Einstellungen