package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele;

import java.util.List;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.artikeljaeger.ArtikeljaegerController;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fallDerWoerter.FallDerWoerterController;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fehlerfuchs.FehlerfuchsController;

public interface SpielListe {
    List<Spiel> SPIELE = List.of(
        new FehlerfuchsController(),
        new ArtikeljaegerController(),
        new FallDerWoerterController()
    );
}