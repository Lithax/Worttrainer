package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

/**
 * Wird geworfen wenn z.B. auf einem Adjektiv die Methode "getArtikel" aufgerufen wird
 */
public class UnavailableWortOperationException extends RuntimeException {
    public UnavailableWortOperationException(String message) {
        super(message);
    }
}
