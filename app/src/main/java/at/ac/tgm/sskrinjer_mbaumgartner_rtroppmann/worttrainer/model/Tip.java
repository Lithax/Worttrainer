package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public record Tip(String text, String title) {
	public static final Path tipsPath = Path.of("resources/tips.json");
	private static List<Tip> tips;

	/**
	 * 
	 * @param date
	 * @param path
	 * @throws IOException 
	 */
	public static Tip getTipOfTheDay(LocalDate date, Path path) throws IOException {
		if(tips == null) loadTips(path);
		if (tips.isEmpty()) return null;

		long days = date.toEpochDay();

		int index = (int) (days % tips.size());
		if (index < 0) index += tips.size();

		return tips.get(index);
	}

	/**
	 * 
	 * @param date
	 * @throws IOException 
	 */
	public static Tip getTipOfTheDay() throws IOException{
		return getTipOfTheDay(LocalDate.now(), tipsPath);
	}

	/**
	 * 
	 * @param tipsPath
	 * @throws IOException 
	 */
	public static void loadTips(Path path) throws IOException {
		String jsonString = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
		tips = new Gson().fromJson(jsonString, new TypeToken<List<Tip>>(){}.getType());
	}
}//end Tip