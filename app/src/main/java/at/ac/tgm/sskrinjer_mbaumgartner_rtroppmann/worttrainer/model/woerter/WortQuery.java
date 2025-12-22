package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.Wort;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.WortTag;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.Wortart;

import java.util.*;
import java.util.function.Predicate;

public class WortQuery {

    private final Wortliste quelle;

    // Filter-Sets
    private Set<Wortart> erlaubteWortarten = new HashSet<>();
    private Set<Wortart> verboteneWortarten = new HashSet<>();

    // Ranges
    private int minLaenge = 0;
    private int maxLaenge = Integer.MAX_VALUE;
    private int minKomplexitaet = 0;
    private int maxKomplexitaet = 10;

    private boolean nurBasis = false;

    // Interne Regel-Maps
    private Map<Wortart, Set<WortTag>> pflichtRegeln = new EnumMap<>(Wortart.class);
    private Map<Wortart, Set<WortTag>> wahlRegeln = new EnumMap<>(Wortart.class);

    private List<Predicate<Wort>> customPredicates = new ArrayList<>();

    WortQuery(Wortliste quelle) {
        this.quelle = quelle;
    }

    // =================================================================
    // 1. Nomen Shortcuts
    // (Aktiviert NOMEN, lässt andere Wortarten aber unberührt!)
    // =================================================================

    /**
     * Konfiguriert, dass Nomen zwingend im Plural stehen müssen.
     * <br><b>Wichtig:</b> Fügt NOMEN zu den erlaubten Wortarten hinzu.
     * Andere erlaubte Wortarten (z.B. Verben) bleiben davon unberührt und werden weiterhin geladen.
     */
    public WortQuery nomenImPlural() {
        mitWortart(Wortart.NOMEN);
        return mussHaben(Wortart.NOMEN, WortTag.PLURAL);
    }

    /**
     * Konfiguriert, dass Nomen zwingend im Singular stehen müssen.
     * <br><b>Wichtig:</b> Fügt NOMEN zu den erlaubten Wortarten hinzu.
     */
    public WortQuery nomenImSingular() {
        mitWortart(Wortart.NOMEN);
        return mussHaben(Wortart.NOMEN, WortTag.SINGULAR);
    }

    /**
     * Schränkt Nomen auf bestimmte Fälle ein.
     * Bsp: nomenImFall(WortTag.NOMINATIV) -> Nur 1. Fall.
     */
    public WortQuery nomenImFall(WortTag... faelle) {
        mitWortart(Wortart.NOMEN);
        if (faelle.length == 1) {
            return mussHaben(Wortart.NOMEN, faelle[0]);
        } else {
            return einerVon(Wortart.NOMEN, faelle);
        }
    }

    // =================================================================
    // 2. Verb Shortcuts
    // (Aktiviert VERB, lässt andere Wortarten unberührt!)
    // =================================================================

    /**
     * Erlaubt Verben nur im Präsens (Gegenwart).
     * Bsp: "geht", "läuft".
     */
    public WortQuery verbImPraesens() {
        mitWortart(Wortart.VERB);
        return mussHaben(Wortart.VERB, WortTag.VERB_PRAESENS);
    }

    /**
     * Erlaubt Verben nur in der Vergangenheit (Präteritum).
     * Bsp: "ging", "lief".
     */
    public WortQuery verbInVergangenheit() {
        mitWortart(Wortart.VERB);
        return mussHaben(Wortart.VERB, WortTag.VERB_VERGANGENHEIT);
    }

    /**
     * Erlaubt Verben nur als Partizip (oft für Perfekt genutzt).
     * Bsp: "gegangen", "gelaufen".
     */
    public WortQuery verbInPartizip() {
        mitWortart(Wortart.VERB);
        return mussHaben(Wortart.VERB, WortTag.VERB_PARTIZIP);
    }

    // =================================================================
    // 3. Adjektiv Shortcuts
    // (Aktiviert ADJEKTIV, lässt andere Wortarten unberührt!)
    // =================================================================

    /**
     * Erlaubt Adjektive nur in der Grundform (Positiv).
     * Bsp: "schön", "schnell".
     */
    public WortQuery adjektivPositiv() {
        mitWortart(Wortart.ADJEKTIV);
        // Da Basis-Adjektive meist keine speziellen Tags haben außer BASIS (oder gar keinen Tag im JSON),
        // filtern wir hier am besten auf "Kein Komparativ und kein Superlativ".
        // Oder explizit auf BASIS, wenn dein Importer das setzt.
        return mussHaben(Wortart.ADJEKTIV, WortTag.BASIS);
    }

    /**
     * Erlaubt Adjektive nur im Komparativ (1. Steigerung).
     * Bsp: "schöner", "schneller".
     */
    public WortQuery adjektivKomparativ() {
        mitWortart(Wortart.ADJEKTIV);
        return mussHaben(Wortart.ADJEKTIV, WortTag.KOMPARATIV);
    }

    /**
     * Erlaubt Adjektive nur im Superlativ (höchste Steigerung).
     * Bsp: "am schönsten", "am schnellsten".
     */
    public WortQuery adjektivSuperlativ() {
        mitWortart(Wortart.ADJEKTIV);
        return mussHaben(Wortart.ADJEKTIV, WortTag.SUPERLATIV);
    }

    /**
     * Erlaubt Adjektive in jeder gesteigerten Form (Komparativ ODER Superlativ).
     */
    public WortQuery adjektivGesteigert() {
        mitWortart(Wortart.ADJEKTIV);
        return einerVon(Wortart.ADJEKTIV, WortTag.KOMPARATIV, WortTag.SUPERLATIV);
    }

    // =================================================================
    // 4. Basis Konfiguration & Ausführung
    // =================================================================

    public WortQuery nurBasis() {
        this.nurBasis = true;
        return this;
    }

    /**
     * Fügt Wortarten zur Liste der erlaubten Arten hinzu.
     * Dies ist ADDITIV. Bereits erlaubte Arten bleiben bestehen.
     */
    public WortQuery mitWortart(Wortart... arten) {
        Collections.addAll(erlaubteWortarten, arten);
        return this;
    }

    public WortQuery ohneWortart(Wortart... arten) {
        Collections.addAll(verboteneWortarten, arten);
        return this;
    }

    public WortQuery mitLaenge(int min, int max) {
        this.minLaenge = min;
        this.maxLaenge = max;
        return this;
    }

    public WortQuery mitKomplexitaet(int min, int max) {
        this.minKomplexitaet = min;
        this.maxKomplexitaet = max;
        return this;
    }

    public WortQuery check(Predicate<Wort> predicate) {
        this.customPredicates.add(predicate);
        return this;
    }

    // Private Helper für die Shortcuts
    private WortQuery mussHaben(Wortart art, WortTag... tags) {
        pflichtRegeln.computeIfAbsent(art, k -> new HashSet<>()).addAll(Arrays.asList(tags));
        return this;
    }

    private WortQuery einerVon(Wortart art, WortTag... tags) {
        wahlRegeln.computeIfAbsent(art, k -> new HashSet<>()).addAll(Arrays.asList(tags));
        return this;
    }

    public Wort[] excecute () {
        return Arrays.stream(quelle.getAlleWoerter())
                .filter(w -> !nurBasis || w.tags().contains(WortTag.BASIS))
                .filter(this::checkWortart)
                .filter(w -> w.laenge() >= minLaenge && w.laenge() <= maxLaenge)
                .filter(w -> w.komplexitaet() >= minKomplexitaet && w.komplexitaet() <= maxKomplexitaet)
                .filter(this::checkTags)
                .filter(w -> customPredicates.stream().allMatch(p -> p.test(w)))
                .toArray(Wort[]::new);
    }

    private boolean checkWortart(Wort w) {
        if (verboteneWortarten.contains(w.wortart())) return false;
        // Wenn allowlist leer ist, ist ALLES erlaubt. Sonst nur was drin steht.
        return erlaubteWortarten.isEmpty() || erlaubteWortarten.contains(w.wortart());
    }

    private boolean checkTags(Wort w) {
        Wortart art = w.wortart();
        Set<WortTag> currentTags = w.tags();

        if (pflichtRegeln.containsKey(art)) {
            if (!currentTags.containsAll(pflichtRegeln.get(art))) return false;
        }

        if (wahlRegeln.containsKey(art)) {
            Set<WortTag> optionen = wahlRegeln.get(art);
            if (Collections.disjoint(currentTags, optionen)) return false;
        }
        return true;
    }
}