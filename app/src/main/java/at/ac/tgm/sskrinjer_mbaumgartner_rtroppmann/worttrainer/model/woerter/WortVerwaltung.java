package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

import java.util.List;
import java.util.Random;

public class WortVerwaltung {

    private WortQuery query;
    private List<Wort> liste;
    private final Random random = new Random();

    public WortVerwaltung(WortQuery query) {
        this.query = query;
        refreshListe();
    }

    public void setQuery(WortQuery query) {
        this.query = query;
    }

    private void refreshListe() {
        liste = List.of(query.array());
    }

    public Wort nextWort() {
        if(liste.isEmpty()) refreshListe();
        int i = random.nextInt(0, liste.size());
        Wort w = liste.get(i);
        liste.remove(i);
        return w;
    }
}
