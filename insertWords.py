import json

# The original data provided in the prompt
original_json = [
  {
    "wort": "Hund",
    "wortart": "Nomen",
    "artikel": "der",
    "singular": { "genitiv": "des Hundes", "dativ": "dem Hund", "akkusativ": "den Hund" },
    "plural": { "nominativ": "die Hunde", "genitiv": "der Hunde", "dativ": "den Hunden", "akkusativ": "die Hunde" }
  },
  {
    "wort": "Katze",
    "wortart": "Nomen",
    "artikel": "die",
    "singular": { "2": "der Katze", "3": "der Katze", "4": "die Katze" },
    "plural": { "1": "die Katzen", "genetiv": "der Katzen", "3": "den Katzen", "akkusativ": "die Katzen" }
  },
  {
    "wort": "Krokodil",
    "artikel": "das",
    "wortart": "Nomen",
    "singular": { "1": "das Krokodil", "2": "des Krokodils", "3": "dem Krokodil", "4": "das Krokodil" },
    "plural": { "1": "die Krokodile", "2": "der Krokodile", "3": "den Krokodilen", "4": "die Krokodile" }
  },
  { "wort": "gehen", "wortart": "Verb", "formen": ["ging", "gegangen", "geht"] },
  { "wort": "schoen", "wortart": "Adjektiv", "formen": ["schoener", "am schoensten"] },
  { "wort": "der", "wortart": "Artikel" },
  { "wort": "mein", "wortart": "Pronomen" },
  { "wort": "zwei", "wortart": "Numeral" },
  { "wort": "gestern", "wortart": "Adverb" },
  { "wort": "und", "wortart": "Konjunktion" },
  { "wort": "ach", "wortart": "Interjektion" }
]

def create_noun(word, art, gen_s, dat_s, akk_s, nom_p, gen_p, dat_p, akk_p):
    return {
        "wort": word,
        "wortart": "Nomen",
        "artikel": art,
        "singular": {
            "genitiv": gen_s,
            "dativ": dat_s,
            "akkusativ": akk_s
        },
        "plural": {
            "nominativ": nom_p,
            "genitiv": gen_p,
            "dativ": dat_p,
            "akkusativ": akk_p
        }
    }

def create_verb(word, past, part_ii, pres_3sg):
    return {
        "wort": word,
        "wortart": "Verb",
        "formen": [past, part_ii, pres_3sg]
    }

def create_adj(word, comp, sup):
    return {
        "wort": word,
        "wortart": "Adjektiv",
        "formen": [comp, sup]
    }

def create_simple(word, type_name):
    return {
        "wort": word,
        "wortart": type_name
    }

# --- DATA GENERATION ---

new_entries = []

# 1. NOMEN (25 words)
# Format: Word, Art, Gen_Sg, Dat_Sg, Akk_Sg, Nom_Pl, Gen_Pl, Dat_Pl, Akk_Pl
noun_data = [
    ("Apfel", "der", "des Apfels", "dem Apfel", "den Apfel", "die Äpfel", "der Äpfel", "den Äpfeln", "die Äpfel"),
    ("Auto", "das", "des Autos", "dem Auto", "das Auto", "die Autos", "der Autos", "den Autos", "die Autos"),
    ("Baum", "der", "des Baumes", "dem Baum", "den Baum", "die Bäume", "der Bäume", "den Bäumen", "die Bäume"),
    ("Buch", "das", "des Buches", "dem Buch", "das Buch", "die Bücher", "der Bücher", "den Büchern", "die Bücher"),
    ("Fenster", "das", "des Fensters", "dem Fenster", "das Fenster", "die Fenster", "der Fenster", "den Fenstern", "die Fenster"),
    ("Garten", "der", "des Gartens", "dem Garten", "den Garten", "die Gärten", "der Gärten", "den Gärten", "die Gärten"),
    ("Haus", "das", "des Hauses", "dem Haus", "das Haus", "die Häuser", "der Häuser", "den Häusern", "die Häuser"),
    ("Kind", "das", "des Kindes", "dem Kind", "das Kind", "die Kinder", "der Kinder", "den Kindern", "die Kinder"),
    ("Lampe", "die", "der Lampe", "der Lampe", "die Lampe", "die Lampen", "der Lampen", "den Lampen", "die Lampen"),
    ("Mann", "der", "des Mannes", "dem Mann", "den Mann", "die Männer", "der Männer", "den Männern", "die Männer"),
    ("Maus", "die", "der Maus", "der Maus", "die Maus", "die Mäuse", "der Mäuse", "den Mäusen", "die Mäuse"),
    ("Mond", "der", "des Mondes", "dem Mond", "den Mond", "die Monde", "der Monde", "den Monden", "die Monde"),
    ("Mutter", "die", "der Mutter", "der Mutter", "die Mutter", "die Mütter", "der Mütter", "den Müttern", "die Mütter"),
    ("Nacht", "die", "der Nacht", "der Nacht", "die Nacht", "die Nächte", "der Nächte", "den Nächten", "die Nächte"),
    ("Nase", "die", "der Nase", "der Nase", "die Nase", "die Nasen", "der Nasen", "den Nasen", "die Nasen"),
    ("Sonne", "die", "der Sonne", "der Sonne", "die Sonne", "die Sonnen", "der Sonnen", "den Sonnen", "die Sonnen"),
    ("Spiel", "das", "des Spiels", "dem Spiel", "das Spiel", "die Spiele", "der Spiele", "den Spielen", "die Spiele"),
    ("Stuhl", "der", "des Stuhles", "dem Stuhl", "den Stuhl", "die Stühle", "der Stühle", "den Stühlen", "die Stühle"),
    ("Tag", "der", "des Tages", "dem Tag", "den Tag", "die Tage", "der Tage", "den Tagen", "die Tage"),
    ("Tisch", "der", "des Tisches", "dem Tisch", "den Tisch", "die Tische", "der Tische", "den Tischen", "die Tische"),
    ("Topf", "der", "des Topfes", "dem Topf", "den Topf", "die Töpfe", "der Töpfe", "den Töpfen", "die Töpfe"),
    ("Uhr", "die", "der Uhr", "der Uhr", "die Uhr", "die Uhren", "der Uhren", "den Uhren", "die Uhren"),
    ("Vater", "der", "des Vaters", "dem Vater", "den Vater", "die Väter", "der Väter", "den Vätern", "die Väter"),
    ("Vogel", "der", "des Vogels", "dem Vogel", "den Vogel", "die Vögel", "der Vögel", "den Vögeln", "die Vögel"),
    ("Wald", "der", "des Waldes", "dem Wald", "den Wald", "die Wälder", "der Wälder", "den Wäldern", "die Wälder"),
]
for n in noun_data:
    new_entries.append(create_noun(*n))

# 2. VERBEN (25 words)
# Format: Word, Past(Präteritum), Participle II, Present(3. Pers. Sg)
verb_data = [
    ("arbeiten", "arbeitete", "gearbeitet", "arbeitet"),
    ("bleiben", "blieb", "geblieben", "bleibt"),
    ("denken", "dachte", "gedacht", "denkt"),
    ("essen", "aß", "gegessen", "isst"),
    ("fahren", "fuhr", "gefahren", "fährt"),
    ("fallen", "fiel", "gefallen", "fällt"),
    ("finden", "fand", "gefunden", "findet"),
    ("fragen", "fragte", "gefragt", "fragt"),
    ("geben", "gab", "gegeben", "gibt"),
    ("halten", "hielt", "gehalten", "hält"),
    ("heißen", "hieß", "geheißen", "heißt"),
    ("helfen", "half", "geholfen", "hilft"),
    ("hören", "hörte", "gehört", "hört"),
    ("kaufen", "kaufte", "gekauft", "kauft"),
    ("kennen", "kannte", "gekannt", "kennt"),
    ("kommen", "kam", "gekommen", "kommt"),
    ("lassen", "ließ", "gelassen", "lässt"),
    ("laufen", "lief", "gelaufen", "läuft"),
    ("leben", "lebte", "gelebt", "lebt"),
    ("lernen", "lernte", "gelernt", "lernt"),
    ("lesen", "las", "gelesen", "liest"),
    ("liegen", "lag", "gelegen", "liegt"),
    ("machen", "machte", "gemacht", "macht"),
    ("nehmen", "nahm", "genommen", "nimmt"),
    ("reden", "redete", "geredet", "redet"),
]
for v in verb_data:
    new_entries.append(create_verb(*v))

# 3. ADJEKTIVE (25 words)
# Format: Word, Komparativ, Superlativ
adj_data = [
    ("alt", "älter", "am ältesten"),
    ("arm", "ärmer", "am ärmsten"),
    ("blau", "blauer", "am blausten"),
    ("böse", "böser", "am bösesten"),
    ("breit", "breiter", "am breitesten"),
    ("dunkel", "dunkler", "am dunkelsten"),
    ("fern", "ferner", "am fernsten"),
    ("fest", "fester", "am festesten"),
    ("frei", "freier", "am freiesten"),
    ("froh", "froher", "am frohsten"),
    ("früh", "früher", "am frühesten"),
    ("ganz", "ganzer", "am ganzesten"),
    ("gelb", "gelber", "am gelbsten"),
    ("groß", "größer", "am größten"),
    ("gut", "besser", "am besten"),
    ("hart", "härter", "am härtesten"),
    ("hoch", "höher", "am höchsten"),
    ("jung", "jünger", "am jüngsten"),
    ("kalt", "kälter", "am kältesten"),
    ("klein", "kleiner", "am kleinsten"),
    ("lang", "länger", "am längsten"),
    ("laut", "lauter", "am lautesten"),
    ("leicht", "leichter", "am leichtesten"),
    ("neu", "neuer", "am neusten"),
    ("rot", "röter", "am rötesten"),
]
for a in adj_data:
    new_entries.append(create_adj(*a))

# 4. SIMPLE TYPES (25 words each)
simple_types = {
    "Artikel": [
        "die", "das", "ein", "eine", "einen", "einem", "einer", "eines", 
        "kein", "keine", "keinen", "keinem", "keiner", "keines", 
        "den", "dem", "des", "jener", "jene", "jenes", 
        "welcher", "welche", "welches", "manch", "solch"
    ],
    "Pronomen": [
        "ich", "du", "er", "sie", "es", "wir", "ihr", "mich", "dich", 
        "ihn", "uns", "euch", "mir", "dir", "ihm", "ihnen", "dein", 
        "sein", "ihr", "unser", "euer", "wer", "was", "jemand", "niemand"
    ],
    "Numeral": [
        "eins", "drei", "vier", "fünf", "sechs", "sieben", "acht", "neun", "zehn", 
        "elf", "zwölf", "dreizehn", "zwanzig", "dreißig", "vierzig", "fünfzig", 
        "hundert", "tausend", "erste", "zweite", "dritte", "vierte", "einmal", "zweimal", "beide"
    ],
    "Adverb": [
        "heute", "morgen", "hier", "da", "dort", "jetzt", "immer", "nie", 
        "oft", "selten", "vielleicht", "wahrscheinlich", "leider", "gern", 
        "sicher", "wirklich", "natürlich", "links", "rechts", "oben", 
        "unten", "innen", "außen", "bald", "damals"
    ],
    "Konjunktion": [
        "aber", "als", "bevor", "bis", "da", "damit", "dass", "denn", 
        "obwohl", "oder", "seit", "sobald", "sondern", "sowie", "während", 
        "weil", "wenn", "wie", "wo", "wohingegen", "zumal", "ob", 
        "entweder", "weder", "noch"
    ],
    "Interjektion": [
        "ah", "aha", "au", "aua", "autsch", "ätsch", "bäh", "bravo", "doch", 
        "hm", "hoppla", "huch", "hurra", "igitt", "ja", "naja", "nein", 
        "oh", "oho", "pfui", "psst", "so", "tja", "wow", "oje"
    ]
}

for w_type, words in simple_types.items():
    for w in words:
        new_entries.append(create_simple(w, w_type))

# Combine original list with new entries
final_list = original_json + new_entries

# Output result
json_output = json.dumps(final_list, indent=2, ensure_ascii=False)

with open('german_words_extended.json', 'w', encoding='utf-8') as f:
    f.write(json_output)

print(json_output)