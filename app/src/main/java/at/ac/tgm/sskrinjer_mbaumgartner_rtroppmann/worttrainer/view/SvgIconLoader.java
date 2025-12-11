package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.ImageIcon;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.image.ImageTranscoder;

public class SvgIconLoader { // chat chat chatti wurde hier verwendet (kb auf 50+ documentationen durchlesen)

    public static final Path svgPath = Path.of("icons");

    private static class BufferedImageTranscoder extends ImageTranscoder {
        private BufferedImage image;

        @Override
        public BufferedImage createImage(int w, int h) {
            return new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        }

        @Override
        public void writeImage(BufferedImage img, org.apache.batik.transcoder.TranscoderOutput out) {
            this.image = img;
        }

        public BufferedImage getImage() {
            return image;
        }
    }

    public static ImageIcon load(String name, int width, int height) {
        try (InputStream in = Files.newInputStream(svgPath.resolve(name))) {

            BufferedImageTranscoder transcoder = new BufferedImageTranscoder();
            transcoder.addTranscodingHint(ImageTranscoder.KEY_WIDTH,  (float) width);
            transcoder.addTranscodingHint(ImageTranscoder.KEY_HEIGHT, (float) height);

            TranscoderInput input = new TranscoderInput(in);
            transcoder.transcode(input, null);

            BufferedImage img = transcoder.getImage();
            return new ImageIcon(img);

        } catch (IOException | TranscoderException e) {
            e.printStackTrace();
            return null;
        }
    }
}
