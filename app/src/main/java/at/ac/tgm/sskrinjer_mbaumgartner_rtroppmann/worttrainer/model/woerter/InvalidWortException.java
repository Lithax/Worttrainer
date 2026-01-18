package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

/**
 * Signalisiert, dass ein Eintrag im JSON logisch ungültig ist
 * (z.B. fehlender Text, Nomen ohne Artikel, unbekannte Wortart).
 */
public class InvalidWortException extends WortlistenLadeException {
    public InvalidWortException(String message) {
        super(message);
    }
}