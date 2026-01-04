package at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.artikeljaeger;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.formdev.flatlaf.FlatClientProperties;

import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.SwingSpielView;
import at.ac.tgm.sskrinjer_mbaumgartner_rtroppmann.worttrainer.spiele.artikeljaeger.ArtikeljaegerModel.Feedback;

/**
 * Swing-UI für Artikeljäger.
 */
public class ArtikeljaegerView extends SwingSpielView<ArtikeljaegerController, ArtikeljaegerView> {

    private final JLabel promptLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel roundLabel = new JLabel("", SwingConstants.CENTER);
    private final JLabel feedbackLabel = new JLabel("", SwingConstants.CENTER);

    private final JButton derBtn = new JButton("der");
    private final JButton dieBtn = new JButton("die");
    private final JButton dasBtn = new JButton("das");

    public ArtikeljaegerView(ArtikeljaegerController c) {
        super(c);

        mainPanel.setLayout(new BorderLayout(0, 18));
        mainPanel.setBorder(new EmptyBorder(22, 22, 22, 22));

        JPanel center = new JPanel(new BorderLayout(0, 10));
        center.setOpaque(false);

        promptLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +14");
        promptLabel.setMinimumSize(new Dimension(10, 80));

        roundLabel.putClientProperty(FlatClientProperties.STYLE, "foreground: $Label.disabledForeground; font: +1");

        feedbackLabel.putClientProperty(FlatClientProperties.STYLE, "font: bold +2");

        center.add(roundLabel, BorderLayout.NORTH);
        center.add(promptLabel, BorderLayout.CENTER);
        center.add(feedbackLabel, BorderLayout.SOUTH);

        JPanel buttons = new JPanel(new GridLayout(1, 3, 12, 0));
        buttons.setOpaque(false);

        // FlatLaf looks nicer with a consistent arc
        String btnStyle = "arc: 16; font: bold +4";
        derBtn.putClientProperty(FlatClientProperties.STYLE, btnStyle);
        dieBtn.putClientProperty(FlatClientProperties.STYLE, btnStyle);
        dasBtn.putClientProperty(FlatClientProperties.STYLE, btnStyle);

        derBtn.addActionListener(e -> c.onArtikelClicked("der"));
        dieBtn.addActionListener(e -> c.onArtikelClicked("die"));
        dasBtn.addActionListener(e -> c.onArtikelClicked("das"));

        buttons.add(derBtn);
        buttons.add(dieBtn);
        buttons.add(dasBtn);

        mainPanel.add(center, BorderLayout.CENTER);
        mainPanel.add(buttons, BorderLayout.SOUTH);
    }

    public void renderFromModel(ArtikeljaegerModel model) {
        promptLabel.setText(model.getPrompt() == null ? "" : model.getPrompt());
        feedbackLabel.setText(" ");
        feedbackLabel.setForeground(UIManager.getColor("Label.foreground"));

        int max = model.getMaxRounds();
        int round = model.getRound();
        if (max > 0) roundLabel.setText("Runde " + (round + 1) + " / " + max);
        else roundLabel.setText("Runde " + (round + 1));
    }

    public void showFeedback(Feedback feedback) {
        if (feedback == null || feedback.message() == null) {
            feedbackLabel.setText(" ");
            return;
        }
        feedbackLabel.setText(feedback.message());

        // Use Look&Feel colors if available (FlatLaf provides useful defaults)
        Color c = null;
        if (feedback.correct()) {
            c = UIManager.getColor("Component.accentColor");
        } else {
            c = UIManager.getColor("Component.error.focusedBorderColor");
            if (c == null) c = UIManager.getColor("Component.error.borderColor");
        }
        if (c != null) feedbackLabel.setForeground(c);
    }

    public void setButtonsEnabled(boolean enabled) {
        derBtn.setEnabled(enabled);
        dieBtn.setEnabled(enabled);
        dasBtn.setEnabled(enabled);
    }
}
