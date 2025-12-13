package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.Gson;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.util.GsonIO;

public class UserData {
    public static final Path userDataPath = Path.of("userdata/userdata.json");

    private String recentlyPlayedGame;

    public void setRecentlyPlayedGame(String recentlyPlayedGame) {
        this.recentlyPlayedGame = recentlyPlayedGame;
    }

    public String getRecentlyPlayedGame() {
        return recentlyPlayedGame;
    }
    
    public static UserData load(Path path) throws IOException {
        return GsonIO.load(path, UserData.class);
    }

    public void save(Path path) throws IOException {
        GsonIO.save(this, UserData.class, path);
    }

    public void save() throws IOException {
        save(userDataPath);
    }
}