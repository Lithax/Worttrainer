package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WortVerwaltung {

    private WortQuery query;
    private List<Wort> liste;
    private final Random random = new Random();

    public WortVerwaltung(WortQuery query) throws WortlistenLadeException {
        this.query = query;
        refreshListe();
        if(liste.isEmpty()) throw new WortlistenLadeException("Keine passenden Wörter für dieses Spiel gefunden");
    }

    public void setQuery(WortQuery query) {
        this.query = query;
    }

    private void refreshListe() {
        liste = new  ArrayList<Wort>(List.of(query.array()));
    }

    public Wort nextWort() {
        if(liste.isEmpty()) refreshListe();
        int i = random.nextInt(0, liste.size());
        Wort w = liste.get(i);
        liste.remove(i);
        return w;
    }
}
