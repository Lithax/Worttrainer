package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.Wort;
import java.util.List;
import java.util.Map;

// Package-private reicht, da nur Importer und Wortliste es sehen müssen
record ImportResult(
        List<Wort> woerter,
        Map<Wort, Wort> elternLookup,
        List<String> warnungen
) {}