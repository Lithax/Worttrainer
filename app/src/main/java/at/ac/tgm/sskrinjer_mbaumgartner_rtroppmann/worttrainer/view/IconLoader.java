package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.ImageIcon;

public class IconLoader {
    public static final Path iconsPath = Path.of("icons");

    public static ImageIcon load(String name) {
        byte[] data;
        try {
            data = Files.readAllBytes(iconsPath.resolve(name));
            return new ImageIcon(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}