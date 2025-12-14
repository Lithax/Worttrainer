package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Wortliste {
    private Wort[] alleWoerter;
    
    // --- Balancing Tools ---
    private Map<Wortart, List<Wort>> woerterNachArt;
    private LinkedList<Wortart> typeDeck = new LinkedList<>();
    private Queue<Wort> recentHistory = new LinkedList<>();
    
    // Config: How many cards of EACH type to put in the bag?
    // Higher number = Less predictable pattern, but still balanced.
    private static final int COPIES_PER_DECK_REFILL = 3; 
    
    // Config: Don't show the same exact word again for X turns
    private static final int WORD_HISTORY_SIZE = 15; 

    public static final Path woerterPath = Path.of(".import/input.json");

    private Wortliste(Wort[] woerter) {
        this.alleWoerter = woerter;
        organizeWords();
    }

    private void organizeWords() {
        this.woerterNachArt = new HashMap<>();
        for (Wort w : alleWoerter) {
            // Only add types that actually exist in the file
            woerterNachArt.computeIfAbsent(w.wortart(), k -> new ArrayList<>()).add(w);
        }
    }

    /**
     * "The Multi-Deck Approach"
     * Ensures equal distribution of Types (Nomen, Verb, etc.) over time,
     * but allows for local randomness (e.g. Nomen -> Nomen -> Verb).
     */
    public Wort getBalancedRandomWort() {
        if (alleWoerter == null || alleWoerter.length == 0) return null;

        // 1. Refill the Deck if empty
        if (typeDeck.isEmpty()) {
            refillTypeDeck();
        }

        // 2. Draw the next Word Type card
        Wortart targetType = typeDeck.poll();
        List<Wort> candidates = woerterNachArt.get(targetType);

        // Safety: If for some reason we drew a card but have no words for it (shouldn't happen)
        if (candidates == null || candidates.isEmpty()) {
            return getRandomWort(); 
        }

        // 3. Pick a unique word from that category
        Wort selectedWord = pickUniqueWordFromList(candidates);

        // 4. Remember usage
        addToHistory(selectedWord);

        return selectedWord;
    }

    private void refillTypeDeck() {
        // Get all types that we actually have words for
        List<Wortart> availableTypes = new ArrayList<>(woerterNachArt.keySet());

        // Add MULTIPLE copies of each type.
        // If we have 4 types and COPIES = 3, we create a deck of 12 cards.
        // This breaks the predictable "1 of each" cycle.
        for (int i = 0; i < COPIES_PER_DECK_REFILL; i++) {
            typeDeck.addAll(availableTypes);
        }
        
        Collections.shuffle(typeDeck);
    }

    private Wort pickUniqueWordFromList(List<Wort> candidates) {
        // Filter out words that were recently used
        List<Wort> validCandidates = candidates.stream()
                .filter(w -> !recentHistory.contains(w))
                .collect(Collectors.toList());

        // If we ran out of "fresh" words for this type, just use any of them
        if (validCandidates.isEmpty()) {
            return candidates.get(new Random().nextInt(candidates.size()));
        }

        // Pick random from the valid ones
        return validCandidates.get(new Random().nextInt(validCandidates.size()));
    }

    private void addToHistory(Wort w) {
        recentHistory.add(w);
        if (recentHistory.size() > WORD_HISTORY_SIZE) {
            recentHistory.poll(); // Remove oldest
        }
    }

    // --- Loading Logic ---
    public static Wortliste loadWortliste() {
        if (!Files.exists(woerterPath)) {
            System.err.println("File not found: " + woerterPath.toAbsolutePath());
            return new Wortliste(new Wort[0]);
        }
        return loadWortliste(woerterPath);
    }

    public static Wortliste loadWortliste(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Wort>>(){}.getType();
            List<Wort> list = gson.fromJson(reader, listType);
            list.removeIf(Objects::isNull);
            return new Wortliste(list.toArray(new Wort[0]));
        } catch (IOException e) {
            e.printStackTrace();
            return new Wortliste(new Wort[0]);
        }
    }

    public Wort getRandomWort() {
        if (alleWoerter == null || alleWoerter.length == 0) return null;
        return alleWoerter[new Random().nextInt(alleWoerter.length)];
    }
}