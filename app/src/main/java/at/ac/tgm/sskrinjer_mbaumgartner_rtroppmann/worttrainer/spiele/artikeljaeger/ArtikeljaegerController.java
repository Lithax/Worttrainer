package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.artikeljaeger;

import javax.swing.Timer;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SpielController;

/**
 * Controller für Artikeljäger.
 */
public class ArtikeljaegerController extends SpielController<ArtikeljaegerModel, ArtikeljaegerController, ArtikeljaegerView> {

    private Timer transitionTimer;

    public ArtikeljaegerController() {
        model = new ArtikeljaegerModel();
        view = new ArtikeljaegerView(this);
    }

    @Override
    public void spielStarten() {
        model.spielStarten(spielListener);
        view.renderFromModel(model);
        view.setButtonsEnabled(true);
    }

    @Override
    public void spielBeenden() {
        if (transitionTimer != null) transitionTimer.stop();
        super.spielBeenden();
    }

    public void onArtikelClicked(String artikel) {
        // Prevent double clicks during feedback
        view.setButtonsEnabled(false);

        model.submitArtikel(artikel);
        view.showFeedback(model.getFeedback());

        // After short delay: either next round or end game
        if (transitionTimer != null) transitionTimer.stop();
        transitionTimer = new Timer(650, e -> {
            transitionTimer.stop();
            if (model.isFinished()) {
                // triggers Statistik dialog + back to main view
                if (spielListener != null) spielListener.onSpielBeenden();
                return;
            }
            model.nextRound();
            view.renderFromModel(model);
            view.setButtonsEnabled(true);
        });
        transitionTimer.setRepeats(false);
        transitionTimer.start();
    }
}
