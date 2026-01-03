package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Einstellungen;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.Wortliste;

public interface SpielListener {
    void onSpielBeenden();
    Einstellungen getEinstellungen();
    Wortliste getWortliste();
}