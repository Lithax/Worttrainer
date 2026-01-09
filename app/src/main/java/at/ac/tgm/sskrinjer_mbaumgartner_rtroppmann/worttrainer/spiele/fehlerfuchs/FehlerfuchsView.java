package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.fehlerfuchs;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SwingSpielView;

public class FehlerfuchsView
		extends SwingSpielView<FehlerfuchsController, FehlerfuchsView> {

	private final JLabel wordLabel = new JLabel("", SwingConstants.CENTER);
	private final JLabel feedbackLabel = new JLabel("", SwingConstants.CENTER);

	private final JTextField inputField = new JTextField();
	private final JButton submitBtn = new JButton("Prüfen");

	public FehlerfuchsView(FehlerfuchsController c) {
		super(c);

		mainPanel.setLayout(new BorderLayout(10, 10));

		submitBtn.addActionListener(e ->
				c.onWordSubmitted(inputField.getText())
		);

		mainPanel.add(wordLabel, BorderLayout.NORTH);
		mainPanel.add(inputField, BorderLayout.CENTER);

		JPanel bottom = new JPanel(new BorderLayout());
		bottom.add(submitBtn, BorderLayout.NORTH);
		bottom.add(feedbackLabel, BorderLayout.SOUTH);

		mainPanel.add(bottom, BorderLayout.SOUTH);
	}

	public void renderFromModel(FehlerfuchsModel model) {
		wordLabel.setText(model.getFehlerhaftesWort());
		feedbackLabel.setText(" ");
		inputField.setText("");
	}

	public void showFeedback(FehlerfuchsModel.Feedback feedback) {
		feedbackLabel.setText(feedback.message());
	}

	public void setInputEnabled(boolean enabled) {
		inputField.setEnabled(enabled);
		submitBtn.setEnabled(enabled);
	}
}
