package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WortQuery {

    // --- Konfiguration ---
    private final Wortliste quelle;
    private int anzahl = 1;

    // Filter-Logik (Startet mit "alles erlaubt")
    private Predicate<Wort> filter = w -> true;

    // Transformations-Logik (Was soll generiert werden?)
    private Set<Fall> gewuenschteFaelle = new HashSet<>();
    private boolean inkludiereGrundform = true;
    private boolean inkludiereVerbformen = false;

    // Konstruktor
    private WortQuery(Wortliste quelle) {
        this.quelle = quelle;
    }

    public static WortQuery create(Wortliste quelle) {
        return new WortQuery(quelle);
    }

    // --- 1. Filter-Methoden (Schränken die Auswahl ein) ---

    public WortQuery anzahl(int anzahl) {
        this.anzahl = anzahl;
        return this;
    }

    public WortQuery laenge(int min, int max) {
        this.filter = this.filter.and(w -> w.getLength() >= min && w.getLength() <= max);
        return this;
    }

    public WortQuery komplexitaet(int min, int max) {
        // Filtert nach Komplexität (1-10)
        this.filter = this.filter.and(w -> w.getKomplexitaet() >= min && w.getKomplexitaet() <= max);
        return this;
    }

    public WortQuery nurWortarten(Wortart... arten) {
        Set<Wortart> erlaubte = Set.of(arten); // Set.of ist performant (ab Java 9)
        this.filter = this.filter.and(w -> erlaubte.contains(w.wortart()));
        return this;
    }

    // --- 2. Transformations-Methoden (Bestimmen die Ausgabeformen) ---

    /**
     * Fügt bestimmte Fälle hinzu (z.B. GENITIV).
     * @param nurDiese Wenn true, wird die Grundform (Nominativ) NICHT ausgegeben,
     * sondern NUR die angeforderten Fälle.
     */
    public WortQuery mitFaellen(boolean nurDiese, Fall... faelle) {
        if (nurDiese) {
            this.inkludiereGrundform = false;
        }
        Collections.addAll(this.gewuenschteFaelle, faelle);
        return this;
    }

    public WortQuery mitVerbformen() {
        this.inkludiereVerbformen = true;
        return this;
    }

    // --- 3. Ausführung (Die eigentliche Logik) ---

    public List<String> execute() {
        // A) Erstmal passende Basis-Wörter aus der Liste holen
        List<Wort> basisWoerter = Arrays.stream(quelle.alleWoerter())
                .filter(filter)            // Unsere gesammelten Filter anwenden
                .collect(Collectors.toList());

        if (basisWoerter.isEmpty()) return Collections.emptyList();

        // B) Zufällige Auswahl treffen (wir mischen die Liste)
        Collections.shuffle(basisWoerter);

        // Wir nehmen etwas mehr als 'anzahl', falls durch Transformation mehr entsteht,
        // oder wir schneiden erst ganz am Ende ab. Hier einfachheitshalber:
        // Wir nehmen die ersten X Basiswörter und generieren daraus.
        List<Wort> auswahl = basisWoerter.stream()
                .limit(anzahl)
                .collect(Collectors.toList());

        // C) Transformation: Aus Basiswörtern die gewünschten Formen machen
        List<String> ergebnisStrings = new ArrayList<>();

//        for (Wort w : auswahl) {
//            // 1. Grundform (wenn gewünscht)
//            if (inkludiereGrundform) {
//                ergebnisStrings.add(w.wort());
//            }
//
//            // 2. Fälle generieren (nur für Nomen/Adjektive relevant)
//            if (!gewuenschteFaelle.isEmpty()) {
//                ergebnisStrings.addAll(w.getFormen(gewuenschteFaelle));
//            }
//
//            // 3. Verbformen generieren
//            if (inkludiereVerbformen && w.getWortart() == Wortart.VERB) {
//                ergebnisStrings.addAll(w.getVerbformen());
//            }
//        }

        // Optional: Falls durch die Erweiterungen mehr als 'anzahl' entstanden sind,
        // könnte man hier nochmal limitieren oder shuffeln.
        return ergebnisStrings;
    }
}
