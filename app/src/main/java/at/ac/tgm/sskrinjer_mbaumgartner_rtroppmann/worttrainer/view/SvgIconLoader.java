package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.ImageIcon;

public class SvgIconLoader { // chat chat chatti wurde hier verwendet (kb auf 50+ documentationen durchlesen)

    public static final Path svgPath = Path.of("icons");
    public static final Path homeSVGPath = svgPath.resolve("home.svg");
    public static final Path settingsSVGPath = svgPath.resolve("settings.svg");
    public static final Path spieleSVGPath = svgPath.resolve("spiele.svg");
    public static final Path exitSVGPath = svgPath.resolve("exit.svg");

    
}
