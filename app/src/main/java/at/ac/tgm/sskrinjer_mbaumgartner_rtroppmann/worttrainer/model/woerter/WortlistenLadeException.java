package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

/**
 * wird geworfen wenn z.B. keine Wörter geladen wurden
 */
public class WortlistenLadeException extends RuntimeException {
    public WortlistenLadeException(String message) {
        super(message);
    }
}
