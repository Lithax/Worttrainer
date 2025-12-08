package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import com.google.gson.Gson;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public record Tip(String text, String title) {

	public static final Path tipsPath = Path.of("");

	public void finalize() throws Throwable {
		
	}
	/**
	 * 
	 * @param date
	 * @param path
	 */
	public static Tip getTipOfTheDay(LocalDate date, Path path){
		return null;
	}

	/**
	 * 
	 * @param date
	 */
	public static Tip getTipOfTheDay(LocalDate date){
		return null;
	}

	/**
	 * 
	 * @param tipsPath
	 */
	private static List<Tip> loadTips(String tipsPath){
		return null;
	}
}//end Tip