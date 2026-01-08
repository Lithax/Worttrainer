package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WortQuery {

    private Wortliste quelle;

    // Filter-Sets
    private Set<Wortart> erlaubteWortarten = new HashSet<>();
    private Set<Wortart> verboteneWortarten = new HashSet<>();

    // Ranges
    private int minLaenge = 0;
    private int maxLaenge = Integer.MAX_VALUE;
    private int minKomplexitaet = 0;
    private int maxKomplexitaet = 10;

    private int maxResults = 100;

    private boolean nurBasis = false;

    // Interne Regel-Maps
    private Map<Wortart, Set<WortTag>> pflichtRegeln = new EnumMap<>(Wortart.class);
    private Map<Wortart, Set<WortTag>> wahlRegeln = new EnumMap<>(Wortart.class);

    private List<Predicate<Wort>> customPredicates = new ArrayList<>();

    WortQuery(Wortliste quelle) {
        this.quelle = quelle;
    }

    // --- Shortcuts (unchanged semantics) ---
    public WortQuery nomenImPlural() {
        mitWortart(Wortart.NOMEN);
        return mussHaben(Wortart.NOMEN, WortTag.PLURAL);
    }

    public WortQuery nomenImSingular() {
        mitWortart(Wortart.NOMEN);
        return mussHaben(Wortart.NOMEN, WortTag.SINGULAR);
    }

    public WortQuery nomenImFall(WortTag... faelle) {
        mitWortart(Wortart.NOMEN);
        if (faelle.length == 1) {
            return mussHaben(Wortart.NOMEN, faelle[0]);
        } else {
            return einerVon(Wortart.NOMEN, faelle);
        }
    }

    public WortQuery verbImPraesens() {
        mitWortart(Wortart.VERB);
        return mussHaben(Wortart.VERB, WortTag.VERB_PRAESENS);
    }

    public WortQuery verbInVergangenheit() {
        mitWortart(Wortart.VERB);
        return mussHaben(Wortart.VERB, WortTag.VERB_VERGANGENHEIT);
    }

    public WortQuery verbInPartizip() {
        mitWortart(Wortart.VERB);
        return mussHaben(Wortart.VERB, WortTag.VERB_PARTIZIP);
    }

    public WortQuery adjektivPositiv() {
        mitWortart(Wortart.ADJEKTIV);
        return mussHaben(Wortart.ADJEKTIV, WortTag.BASIS);
    }

    public WortQuery adjektivKomparativ() {
        mitWortart(Wortart.ADJEKTIV);
        return mussHaben(Wortart.ADJEKTIV, WortTag.KOMPARATIV);
    }

    public WortQuery adjektivSuperlativ() {
        mitWortart(Wortart.ADJEKTIV);
        return mussHaben(Wortart.ADJEKTIV, WortTag.SUPERLATIV);
    }

    public WortQuery adjektivGesteigert() {
        mitWortart(Wortart.ADJEKTIV);
        return einerVon(Wortart.ADJEKTIV, WortTag.KOMPARATIV, WortTag.SUPERLATIV);
    }

    // --- Config ---
    public WortQuery nurBasis() {
        this.nurBasis = true;
        return this;
    }

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

    public WortQuery mitMaxResults(int max) {
        this.maxResults = max;
        return this;
    }

    public WortQuery check(Predicate<Wort> predicate) {
        this.customPredicates.add(predicate);
        return this;
    }

    private WortQuery mussHaben(Wortart art, WortTag... tags) {
        pflichtRegeln.computeIfAbsent(art, k -> new HashSet<>()).addAll(Arrays.asList(tags));
        return this;
    }

    private WortQuery einerVon(Wortart art, WortTag... tags) {
        wahlRegeln.computeIfAbsent(art, k -> new HashSet<>()).addAll(Arrays.asList(tags));
        return this;
    }

    // --- CORE: filteredStream (no premature limit) ---
    public Stream<Wort> filteredStream() {
        return Arrays.stream(quelle.getAlleWoerter())
                .filter(w -> !nurBasis || w.tags().contains(WortTag.BASIS))
                .filter(this::checkWortart)
                .filter(w -> w.laenge() >= minLaenge && w.laenge() <= maxLaenge)
                .filter(w -> w.komplexitaet() >= minKomplexitaet && w.komplexitaet() <= maxKomplexitaet)
                .filter(this::checkTags)
                .filter(w -> customPredicates.stream().allMatch(p -> p.test(w)));
    }

    // array() applies the limit when materializing
    public Wort[] array() {
        return filteredStream()
                .limit(maxResults)
                .toArray(Wort[]::new);
    }

    // Balanced random across Wortart buckets
    public ArrayList<Wort> randomBalancedArray() {
        // Collect full filtered pool and group by wortart
        Map<Wortart, List<Wort>> grouped = filteredStream()
                .collect(Collectors.groupingBy(Wort::wortart, () -> new EnumMap<>(Wortart.class), Collectors.toList()));

        // Ensure all expected buckets are present (so round-robin order is stable).
        Set<Wortart> expected = erlaubteWortarten.isEmpty()
                ? EnumSet.allOf(Wortart.class)
                : EnumSet.copyOf(erlaubteWortarten);

        for (Wortart a : expected) {
            grouped.putIfAbsent(a, new ArrayList<>());
        }

        // Shuffle each group with a Random instance
        Random rnd = new Random(System.nanoTime());
        grouped.values().forEach(list -> Collections.shuffle(list, rnd));

        // Round-robin pick from each group until we hit maxResults or pool exhausted
        ArrayList<Wort> result = new ArrayList<>(Math.min(maxResults, 64));
        boolean added;
        do {
            added = false;
            for (Wortart a : expected) { // stable ordering across runs
                List<Wort> list = grouped.get(a);
                if (list != null && !list.isEmpty() && result.size() < maxResults) {
                    result.add(list.remove(0));
                    added = true;
                }
            }
        } while (added && result.size() < maxResults);

        return result;
    }

    public Wort[] randomBalancedJArray() {
        return randomBalancedArray().toArray(new Wort[0]);
    }

    // --- helper checks ---
    private boolean checkWortart(Wort w) {
        if (verboteneWortarten.contains(w.wortart())) return false;
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

    public void setQuelle(Wortliste quelle) {
        this.quelle = quelle;
    }

    public Wortliste getQuelle() {
        return quelle;
    }
}
