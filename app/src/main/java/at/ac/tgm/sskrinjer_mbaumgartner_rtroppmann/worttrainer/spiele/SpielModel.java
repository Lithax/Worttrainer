package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele;

import java.util.*;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Einstellungen;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Statistik;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.Wort;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.WortQuery;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.Wortliste;

/**
 * @author Benutzbiber
 * @version 1.0
 * @created 08-Dez-2025 15:09:39
 */
public abstract class SpielModel {

	protected Wortliste wortliste;
    protected WortQuery query;
	protected Einstellungen einstellungen;

    private List<Wort> masterPool;

    // Der "Zieh-Stapel": Hier werden Wörter rausgenommen.
    // Wenn leer, wird er aus dem Master-Pool neu befüllt.
    private Queue<Wort> ziehStapel;

    // Das Wort, das gerade gespielt wird
    protected Wort aktuellesWort;

	public SpielModel(WortQuery query){
        this.query = query;
        setWortPool(query.excecute());
	}

	public abstract String getSpielDescription();

	public abstract String getSpielName();

	public Statistik getStatistik() {
        HashMap<String, String> fields = new HashMap<>();
		if(einstellungen != null)
			fields.put("Schwierigkeit", einstellungen.getSchwierigkeit());
        return new Statistik(getSpielDescription(), getSpielName() + " - Statistik", fields);
	}

	public abstract boolean hasNewRound();

	public void spielStarten(SpielListener l) {
		einstellungen = l.getEinstellungen();
	}


    // --- INITIALISIERUNG (Macht der Controller) ---

    /**
     * Setzt die Wörter, die in diesem Spiel verwendet werden sollen.
     * MUSS aufgerufen werden, bevor das Spiel startet.
     * * @param woerter Das Ergebnis aus deiner WortQuery.
     */
    private void setWortPool(Wort[] woerter) {
        // Master-Pool speichern (als unveränderliche Liste, zur Sicherheit)
        this.masterPool = List.of(woerter);

        // Ersten Stapel mischen und bereitstellen
        mischeStapelNeu();

        // Optional: Direkt das erste Wort ziehen, damit "aktuellesWort" nicht null ist
        nextWort();
    }


    // --- DIE MAGISCHE METHODE FÜR DIE KIND-KLASSEN ---

    /**
     * Gibt das nächste Wort zurück und sorgt automatisch für Nachschub.
     * Die Kind-Klasse muss sich um nichts kümmern.
     */
    public Wort nextWort() {
        if (masterPool == null) {
            throw new IllegalStateException("WortPool wurde noch nicht gesetzt!");
        }

        // 1. Ist der Stapel leer? -> Auffüllen & Mischen!
        if (ziehStapel.isEmpty()) {
            mischeStapelNeu();
        }

        // 2. Nächstes Wort holen
        this.aktuellesWort = ziehStapel.poll();

        // 3. Hook-Methode aufrufen (falls Kind-Klasse was berechnen muss)

        return this.aktuellesWort;
    }

    /**
     * Interne Methode: Füllt den Stapel aus dem Master-Pool auf und mischt.
     */
    private void mischeStapelNeu() {
        // Kopie erstellen, damit wir mischen können
        List<Wort> temp = new ArrayList<>(masterPool);
        Collections.shuffle(temp);
        this.ziehStapel = new LinkedList<>(temp);
    }



}//end SpielModel