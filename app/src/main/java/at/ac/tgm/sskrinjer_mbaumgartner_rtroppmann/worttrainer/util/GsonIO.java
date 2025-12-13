package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import com.google.gson.Gson;

public class GsonIO {
    public static <T> T load(Path path, Type type) throws IOException {
        Gson gson = new Gson();
		if(!Files.exists(path))
			Files.createFile(path);
		String jsonString = new String(Files.readAllBytes(path));
		return gson.fromJson(jsonString, type);  
    }

    public static <T> void save(T t, Type type, Path path) throws IOException {
		Gson gson = new Gson();
		String jsonString = gson.toJson(t, type);
		Files.writeString(path, jsonString, 
			StandardCharsets.UTF_8, 
			StandardOpenOption.CREATE, 
			StandardOpenOption.TRUNCATE_EXISTING
		);
	}
}