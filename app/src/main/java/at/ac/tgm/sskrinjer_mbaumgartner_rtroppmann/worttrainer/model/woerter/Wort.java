package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

import java.util.List;
import java.util.Set;

/**
 * Repräsentiert ein Wort oder eine Wortform.
 *
 * @param wort          Der Text des Wortes (z.B. "Hunde").
 * @param wortart       Die Wortart (Nomen, Verb, etc.).
 * @param artikel       Der Artikel (nur bei Nomen relevant, sonst null).
 * @param tags          Liste von Eigenschaften (SINGULAR, PLURAL, GENITIV, BASIS, etc.).
 * @param variationen   Liste der abgeleiteten Formen.
 */
public record Wort(
        String wort,
        Wortart wortart,
        String artikel,
        //int laenge
        //double komplexitat -> 0-10
        Set<WortTag> tags,
        List<Wort> variationen
) {

    /**
     * Kompakter Konstruktor für Validierung und Erstellung unveränderlicher Kopien.
     */
    public Wort {
        // Set.copyOf erstellt eine unmodifizierbare Kopie.
        // Wenn 'tags' null ist, nehmen wir ein leeres Set.
        tags = (tags == null) ? Set.of() : Set.copyOf(tags);

        // List.copyOf erstellt eine unmodifizierbare Kopie.
        // Das bedeutet: Nach der Erstellung des Wortes kann niemand mehr .add() oder .remove() aufrufen.
        variationen = (variationen == null) ? List.of() : List.copyOf(variationen);
    }

    /**
     * Komplexität von 0-10;
     * @return
     */
    public double komplexitaet() {
        // Todo: Implementierung der Komplexitätsberechnung
        return 5;
    }

    public int laenge() {
        return wort.length();
    }

    public int getFall() throws UnavailableWortOperationException {
        if(tags().contains(WortTag.NOMINATIV)) {
            return 1;
        } else if(tags().contains(WortTag.GENITIV)) {
            return 2;
        } else if(tags().contains(WortTag.DATIV)) {
            return 3;
        } else if(tags().contains(WortTag.AKKUSATIV)) {
            return 4;
        }
        throw new  UnavailableWortOperationException("Kein Fall für:" + wort);
    }
}