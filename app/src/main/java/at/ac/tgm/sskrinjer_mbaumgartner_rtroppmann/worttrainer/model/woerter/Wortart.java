package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

import com.google.gson.annotations.SerializedName;

public enum Wortart {
    @SerializedName("Nomen") NOMEN("Nomen"),
    @SerializedName("Verb") VERB("Verb"),
    @SerializedName("Adjektiv") ADJEKTIV("Adjektiv"),
    @SerializedName("Artikel") ARTIKEL("Artikel"),
    @SerializedName("Pronomen") PRONOMEN("Pronomen"),
    @SerializedName("Numeral") NUMERAL("Numeral"),
    @SerializedName("Adverb") ADVERB("Adverb"),
    @SerializedName("Konjunktion") KONJUNKTION("Konjunktion"),
    @SerializedName("Interjektion") INTERJEKTION("Interjektion"),
    UNKNOWN("Unbekannt");

    private final String displayName;

    Wortart(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return displayName;
    }
}

