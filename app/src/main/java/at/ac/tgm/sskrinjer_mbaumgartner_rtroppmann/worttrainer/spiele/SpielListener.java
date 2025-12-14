package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.Einstellungen;

public interface SpielListener {
    void onSpielBeenden();
    Einstellungen getEinstellungen();
}