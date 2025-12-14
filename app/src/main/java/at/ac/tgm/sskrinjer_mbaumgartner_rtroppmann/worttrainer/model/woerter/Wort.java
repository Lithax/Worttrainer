package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

import java.util.List;
import java.util.Map;

/**
 * Matches the JSON structure provided.
 */
public record Wort(
    String wort,
    Wortart wortart,
    String artikel,
    Map<String, String> singular, // Changed from Wort[] to Map
    Map<String, String> plural,   // Changed from Wort[] to Map
    List<String> formen           // Changed from Wort[] to List<String>
) {
    public int getLength() {
        return wort != null ? wort.length() : 0;
    }

    public double getKomplexitaet() {
        return getLength() * 0.5 + (wortart == Wortart.VERB ? 2 : 0);
    }
    
    // Helper to get a clean display string
    public String getDisplayWord() {
        return wort;
    }
}