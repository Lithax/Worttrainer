package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.artikeljaeger.ArtikeljaegerController;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fallDerWoerter.FallDerWoerterController;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fehlerfuchs.FehlerfuchsController;

public interface SpielListe {
    @SuppressWarnings("unchecked")
    Class<? extends Spiel>[] SPIELE = new Class[]{
        FehlerfuchsController.class,
        FallDerWoerterController.class,
        ArtikeljaegerController.class
    };
}