package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

// NEU: Tags zum Filtern der spezifischen Formen
public enum WortTag {
    BASIS,          // Die Grundform (z.B. "Hund")

    // Fälle
    NOMINATIV, GENITIV, DATIV, AKKUSATIV,

    // Numerus
    SINGULAR, PLURAL,

    // Steigerung (Adjektive)
    POSITIV, KOMPARATIV, SUPERLATIV,

    // Verbformen
    VERB_GEGENWART,VERB_VERGANGENHEIT, VERB_PARTIZIP, VERB_PRAESENS;

}
