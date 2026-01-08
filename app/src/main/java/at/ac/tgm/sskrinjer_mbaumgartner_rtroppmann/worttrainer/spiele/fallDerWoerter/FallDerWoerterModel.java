package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fallDerWoerter;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.LinearSpielModel;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SpielListener;

import java.util.Random;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.model.woerter.*;


public class FallDerWoerterModel extends LinearSpielModel {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 400;
    private static final int SPAWN_Y = 50;
    private double baseSpeed;
	private double speed;
	private double baseLateralSpeed;
	private double lateralSpeed;

	private boolean movingLeft = false;
    private boolean movingRight = false;

    private Wort currentWord;
    private double currentX = 300;
    private double currentY = SPAWN_Y;
    
    public static final Wortart[] buckets = {
        Wortart.NOMEN, 
        Wortart.VERB, 
        Wortart.ADJEKTIV, 
        Wortart.ARTIKEL,
		Wortart.PRONOMEN,
		Wortart.KONJUNKTION,
		Wortart.INTERJEKTION
    };

    private Feedback lastFeedback;
    private boolean gameRunning = false;
    private Random rand = new Random();

    @Override
    public void spielStarten(SpielListener l) {
		super.spielStarten(l);

        query.mitWortart(buckets)
             .mitLaenge(1, 16)
             .adjektivPositiv()
             .verbImPraesens()
             .nomenImSingular();

        this.gameRunning = true;

		this.movingLeft = false;
        this.movingRight = false;

		baseSpeed = 2.0;
		baseLateralSpeed = 8.0;
		speed = baseSpeed;
		lateralSpeed = baseLateralSpeed;

		lastFeedback = new Feedback("Steuerung mit Tasten A/D oder ←/→", FeedbackType.PRAISE);

		boolean custom = false;

		double multiplier = 0.0;
        switch (einstellungen.numerifySchwierigkeit()) {
            /*
             *@Marius ich hab dein fuckass switch mit 3 inlined anweisungen und breaks zu einem advanced switch gemacht
             */
            case 1 -> multiplier = 0.6;
            case 2 -> multiplier = 1.5;
            case 3 -> multiplier = 2.0;
			case 4 -> {
                custom = true;
                baseSpeed = 1; baseLateralSpeed = 7.0;
            }
            default -> multiplier = 1.0;
        }

		if(!custom) {
			this.baseSpeed = baseSpeed * multiplier;
			this.baseLateralSpeed = baseLateralSpeed * multiplier;
		}
        spawnNextWord();
    }

	public void setMovingLeft(boolean moving) { this.movingLeft = moving; }
    public void setMovingRight(boolean moving) { this.movingRight = moving; }

    public void stopGame() {
        this.gameRunning = false;
    }

    private void spawnNextWord() {
        Wort[] candidates = query.randomBalancedArray();
        
        if (candidates.length > 0) {
            currentWord = candidates[rand.nextInt(candidates.length)];
        } else {
            System.err.println("Warnung: Keine Wörter gefunden für die aktuellen Filter!");
            return;
        }
        
        currentX = WIDTH / 2.0;
        currentY = SPAWN_Y;
        
        speed = baseSpeed + Math.min(6.0, (streak * 0.1));
		lateralSpeed = baseLateralSpeed + Math.min(6.0, (streak * 0.1)) / 2.0;
    }


    public void update() {
        if (!gameRunning || currentWord == null) return;

        currentY += speed;

        if (movingLeft) {
            currentX -= lateralSpeed;
            if (currentX < 20) currentX = 20;
        }
        if (movingRight) {
            currentX += lateralSpeed;
            if (currentX > WIDTH - 20) currentX = WIDTH - 20;
        }

        if (currentY >= HEIGHT - 20) {
            checkBucketCollision();
        }
    }

    public void moveLeft() {
        if (currentX > 20) currentX -= 15;
    }

    public void moveRight() {
        if (currentX < WIDTH - 20) currentX += 15;
    }

    private void checkBucketCollision() {
        gesamtAnzahl++;
        
        int count = buckets.length;
        int bucketWidth = WIDTH / count;
        
        int bucketIndex = (int) (currentX / bucketWidth);
        
        if (bucketIndex < 0) bucketIndex = 0;
        if (bucketIndex >= count) bucketIndex = count - 1;

        Wortart hitCategory = buckets[bucketIndex];

        if (currentWord.wortart() == hitCategory) {
            streak++;
            if(streak > highestStreak)
                highestStreak = streak;
            lastFeedback = getRandomPraise();
        } else {
            streak = 0;
            fehlerAnzahl++;
            lastFeedback = getRandomScolding();
        }

        spawnNextWord();
    }

    private Feedback getRandomPraise() {
        String[] msgs = {"Fantastisch!", "Super!", "Weiter so!", "Genial!", "Korrekt!"};
        return new Feedback(msgs[rand.nextInt(msgs.length)], FeedbackType.PRAISE);
    }

    private Feedback getRandomScolding() {
        String[] msgs = {"Fast!", "Bleib dran!", "Leider nein", "Versuch's nochmal"};
        return new Feedback(msgs[rand.nextInt(msgs.length)], FeedbackType.SCOLD);
    }

	static enum FeedbackType {PRAISE,SCOLD}

	static record Feedback(String message, FeedbackType type) {}

    public double getX() { return currentX; }
    public double getY() { return currentY; }
    public String getWordText() { return currentWord != null ? currentWord.displayWord() : ""; }
    public Feedback getFeedback() { return lastFeedback; }
    public int getStreak() { return streak; }

    @Override
    public String getSpielDescription() {
        return "Teste dein Wissen über die Wortarten! In Fall der Wörter fallen Wörter vom oberen Bildschirmrand, und deine Aufgabe ist es, sie in das richtige „Wortarten-Loch“ zu steuern – zum Beispiel „laufen“ in das Loch für Verb oder „Hund“ in das Loch für Nomen. Je schneller und genauer du die Wörter richtig einsortierst, desto mehr Punkte bekommst du. Das Spiel hilft dir, die verschiedenen Wortarten zu erkennen und dein grammatisches Verständnis zu trainieren.";
    }

    @Override
    public String getSpielName() {
        return "Fall der Wörter";
    }

}