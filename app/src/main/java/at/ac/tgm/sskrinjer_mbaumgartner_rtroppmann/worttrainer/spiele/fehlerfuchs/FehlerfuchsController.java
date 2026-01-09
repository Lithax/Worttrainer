package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fehlerfuchs;

import javax.swing.Timer;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SpielController;

public class FehlerfuchsController
		extends SpielController<FehlerfuchsModel, FehlerfuchsController, FehlerfuchsView> {

	private Timer transitionTimer;

	public FehlerfuchsController() {
		model = new FehlerfuchsModel();
		view = new FehlerfuchsView(this);
	}

	@Override
	public void spielStarten() {
		model.spielStarten(spielListener);
		view.renderFromModel(model);
		view.setInputEnabled(true);
	}

	public void onWordSubmitted(String userInput) {
		view.setInputEnabled(false);

		model.submitAnswer(userInput);
		view.showFeedback(model.getFeedback());

		if (transitionTimer != null) transitionTimer.stop();
		transitionTimer = new Timer(800, e -> {
			transitionTimer.stop();
			if (model.isFinished()) {
				if (spielListener != null) spielListener.onSpielBeenden();
				return;
			}
			model.nextRound();
			view.renderFromModel(model);
			view.setInputEnabled(true);
		});
		transitionTimer.setRepeats(false);
		transitionTimer.start();
	}
}
